Ext.define('recibosWeb.view.reemitir.master.ReemitirMasterVM', {
    extend  : 'Ext.app.ViewModel',
    alias   : 'viewmodel.reemitir-master',
    requires: [
        'recibosWeb.model.Cabecera',
        'recibosWeb.model.Emision',
        'recibosWeb.model.PSD',
        'recibosWeb.model.Suscripcion',
        'recibosWeb.model.Persona'
    ],
    stores: {
        reemitirs: {
            model       : 'recibosWeb.model.Cabecera',
            filterOnLoad: false,
            remoteSort  : true,
            pageSize    : 16,
            remoteFilter: true,
            listeners: { datachanged: 'reemitirsDataChanged' }
        }
    }
});