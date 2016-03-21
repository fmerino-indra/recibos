Ext.define('recibosWeb.view.cabecera.detail.CabeceraDetailMasterVM', {
    extend  : 'Ext.app.ViewModel',
    alias   : 'viewmodel.cabecera-detailmaster',
    requires: [
        'recibosWeb.model.CabeceraDTO',
        'recibosWeb.model.EmisionDTO'
    ],
    data: {
        hasAnyReturned  : false,
        hasAnyPaid: false
    },

    stores: {
        cabeceras: {
            model       : 'recibosWeb.model.CabeceraDTO',
            filterOnLoad: false,
            remoteSort  : true,
            pageSize    : 16,
            remoteFilter: true,
            listeners: { datachanged: 'cabecerasDataChanged' }
        },
        cabecerasShorted: {
            source      : '{cabeceras}',
            filters      : [{
                property : 'devuelto',
                value    : true,
                operator : '='
            }]
        }

    }
});