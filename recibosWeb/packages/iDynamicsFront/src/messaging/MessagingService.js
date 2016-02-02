Ext.define('iDynamicsFront.messaging.MessagingService', {
    alternateClassName: 'MessagingService',
    requires          : ['iDynamicsFront.messaging.Transport'],
    singleton         : true,

    sleepToGetConnectionInstance: 500,
    responseTopic               : null,
    requestIdLength             : 14,

    constructor: function () {
        this.transport = iDynamicsFront.messaging.Transport;
    },

    init: function (config) {
        this.responseTopic = config.responseTopic;
        iDynamicsFront.messaging.Transport.init(config);
    },


    requestResponse: function (requestQueue, payload) {
        var me = this;
        return Rx.Observable.create(function (observer) {
            var disposable;
            me.getConnection().then(function (transport) {
                var requestId = me.generateRequestId();
                payload = payload || {};
                if (!payload) {
                    payload = {}
                }
                payload.requestId = requestId;
                disposable = transport.subscribe(me.responseTopic)
                    //TODO filtrar por requestId
                    .where(function (message) {
                    	console.log(message);
                        return message.requestId === requestId;
                    })
                    .take(1)
                    .subscribe({
                        onNext     : function (data) {
                            observer.onNext(data);
                            observer.onCompleted();
                        },
                        onCompleted: function () {
                            observer.onCompleted();
                        },
                        onError    : function (error) {
                            observer.onError(error);
                        }
                    });

                transport.publish(requestQueue, payload);
            });

            return function () {
                disposable.dispose();
            };
        });
    },

    subscribe: function (topic) {
        var me = this;
        return Rx.Observable.create(function (observer) {
            var disposable;
            me.getConnection().then(function (transport) {
                disposable = transport.subscribe(topic)
                    .subscribe({
                        onNext     : function (data) {
                            observer.onNext(data);
                        },
                        onCompleted: function () {
                            observer.onCompleted();
                        },
                        onError    : function (error) {
                            observer.onError(error);
                        }
                    });
            });
            return function () {
                disposable.dispose();
            };
        });
    },

    getConnection: function () {
        var me = this, getConnectionInstance;

        return new RSVP.Promise(function (resolve, failed) {
            getConnectionInstance = function () {
                if (me.transport.isConnected()) {
                    me.connecting = false;
                    resolve(me.transport);
                } else {
                    setTimeout(getConnectionInstance, me.sleepToGetConnectionInstance);
                }
            };
            if (!me.transport.isConnected() && !me.connecting) {
                me.connecting = true;
                return me.transport.connect().then(function () {
                    me.connecting = false;
                    resolve(me.transport);
                });
            } else {
                getConnectionInstance();
            }
        });
    },

    generateRequestId: function () {
        var i, requestId = "", possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (i = 0; i < this.requestIdLength; i++) {
            requestId += possible.charAt(Math.floor(Math.random() * possible.length));
        }

        return requestId;
    }
});