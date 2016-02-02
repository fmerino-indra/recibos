Ext.define('recibosWeb.view.ViewportCtrl', {
    extend: 'Ext.app.ViewController',
    alias : 'controller.viewport',

    requires: [
//        'APP.services.SubSystemsService',
//        'APP.services.Alarm',
//        'APP.store.MyTreeStore'
    ],
    local   : true,
    init    : function () {
    },
    runnerClock   : function () {
        var me = this;
        setInterval(Ext.bind(me.updateClock, me), 1000);
    },
    updateClock   : function () {
        var date = Ext.Date.format(new Date(), 'D, m/Y'),
            time = Ext.Date.format(new Date(), 'H:i:s');
        this.lookupReference('clock').down('displayfield').setFieldLabel(date);
        this.lookupReference('clock').down('displayfield').setValue(time);
    }

});
