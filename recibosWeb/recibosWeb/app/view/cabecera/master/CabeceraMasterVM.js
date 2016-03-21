Ext.define('recibosWeb.view.cabecera.master.CabeceraMasterVM', {
    extend  : 'Ext.app.ViewModel',
    alias   : 'viewmodel.cabecera-master',
    requires: [
        'recibosWeb.model.Cabecera',
        'recibosWeb.model.Emision',
        'recibosWeb.model.PSD',
        'recibosWeb.model.Suscripcion',
        'recibosWeb.model.Persona'
    ],
    stores: {
        cabeceras: {
            model       : 'recibosWeb.model.Cabecera',
            filterOnLoad: false,
            remoteSort  : true,
            pageSize    : 16,
            remoteFilter: true,
            listeners: { datachanged: 'cabecerasDataChanged' }
        }
    }
});