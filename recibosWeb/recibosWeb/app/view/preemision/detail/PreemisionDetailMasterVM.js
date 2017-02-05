Ext.define('recibosWeb.view.preemision.detail.PreemisionDetailMasterVM', {
    extend  : 'Ext.app.ViewModel',
    alias   : 'viewmodel.preemision-detailmaster',
    requires: [
        'recibosWeb.model.CabeceraDTO',
        'recibosWeb.model.EmisionDTO'
    ],
    data: {
        hasAnyReturned  : false,
        hasAnyPaid: false
    },

    stores: {
        preemisions: {
            model       : 'recibosWeb.model.CabeceraDTO',
            filterOnLoad: false,
            remoteSort  : true,
            pageSize    : 16,
            remoteFilter: true,
            listeners: { datachanged: 'preemisionsDataChanged' }
        },
        preemisionsShorted: {
            source      : '{cabecera}',
            filters      : [{
                property : 'id',
                value    : true,
                operator : '='
            }]
        }

    }
});