Ext.define('recibosWeb.view.suscripcion.detail.SuscripcionDetailVM', {
    extend  : 'Ext.app.ViewModel',
    alias   : 'viewmodel.suscripcion-detail',
    requires: [
        'recibosWeb.model.SuscripcionDTO'
    ],
    stores: {
        suscripcionDTOs: {
            model       : 'recibosWeb.model.SuscripcionDTO',
            filterOnLoad: false,
            remoteSort  : true,
            pageSize    : 22,
            remoteFilter: true
        }
    }
});