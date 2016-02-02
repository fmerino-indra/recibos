Ext.define('iDynamicsFront.messaging.Transport', {
    singleton: true,
    prefix   : '',
    urlSocket: null,
    headers  : null,


    init: function (config) {
        this.urlSocket = config.urlSocket;
        this.headers = config.headers;
    },


    connect: function () {
        var me = this, socket;
        socket = new SockJS(me.urlSocket);
        this.connected = false;
        this.stompClient = Stomp.over(socket);
        return new RSVP.Promise(function (resolve, failed) {
            me.stompClient.connect(me.headers, function () {
                me.connected = true;
                resolve();
            }, failed);
        });
    },

    publish: function (topic, message) {
        var me = this, topic = this.applyPrefix(topic);
        me.stompClient.send(topic, {}, JSON.stringify(message));
    },

    subscribe: function (topic) {
        var me = this;
        topic = me.applyPrefix(topic);
        return Rx.Observable.create(function (obs) {

            me.stompClient.subscribe(topic, function (data) {
                data = JSON.parse(data.body);
                //TODO check errors
                // if(data.success) {
                obs.onNext(data);
                //} else {
                //   obs.onError(data);
                //}
            });

            // Dispose function
            return function () {

            };
        });
    },

    isConnected: function () {
        return this.connected;
    },

    applyPrefix: function (topic) {
        return topic.indexOf('/') == 0 ? this.prefix + topic : this.prefix + '/' + topic;
    }
});