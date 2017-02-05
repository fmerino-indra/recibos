Ext.define('recibosWeb.view.suscripcion.master.SuscripcionMasterVM', {
    extend  : 'Ext.app.ViewModel',
    alias   : 'viewmodel.suscripcion-master',
    requires: [
        'recibosWeb.model.Suscripcion'
    ],
    
    stores: {
        suscripcions: {
            model       : 'recibosWeb.model.Suscripcion',
            filterOnLoad: false,
            remoteSort  : true,
            pageSize    : 20,
            remoteFilter: true,
            listeners: { datachanged: 'suscripcionsDataChanged' }
        }
    }
});