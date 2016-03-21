Ext.define('recibosWeb.view.reemitir.detail.ReemitirDetailMasterVM', {
    extend  : 'Ext.app.ViewModel',
    alias   : 'viewmodel.reemitir-detailmaster',
    requires: [
        'recibosWeb.model.CabeceraDTO',
        'recibosWeb.model.EmisionDTO'
    ],
    data: {
        hasAnyReturned  : false,
        hasAnyPaid: false
    },

    stores: {
        reemitirs: {
            model       : 'recibosWeb.model.CabeceraDTO',
            filterOnLoad: false,
            remoteSort  : true,
            pageSize    : 16,
            remoteFilter: true,
            listeners: { datachanged: 'reemitirsDataChanged' },
            proxy      : {
                type     : 'idfproxy',
                name     : 'recibosWeb.proxy.ReemitirDTO',
                mockupUrl: 'data/reemitirListado.json',
                remoteUrl: 'cabeceras'
// Activar esto cuando esté hecho el modelo emision
//        ,
//        rootProperty : "data"
            }
        }
    }
});