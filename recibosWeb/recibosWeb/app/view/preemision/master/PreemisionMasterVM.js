Ext.define('recibosWeb.view.preemision.master.PreemisionMasterVM', {
    extend  : 'Ext.app.ViewModel',
    alias   : 'viewmodel.preemision-master',
    requires: [
        'recibosWeb.model.ResumenDTO'
    ],
    stores: {
        preemisions: {
            model       : 'recibosWeb.model.ResumenDTO',
            filterOnLoad: false,
            remoteSort  : true,
            pageSize    : 20,
            remoteFilter: true,
            listeners: { datachanged: 'preemisionsDataChanged' }
        }
    }
});