Ext.define('recibosWeb.view.periodo.master.PeriodoMasterVM', {
    extend  : 'Ext.app.ViewModel',
    alias   : 'viewmodel.periodo-master',
    requires: [
        'recibosWeb.model.Periodo'
    ],
    stores: {
        periodos: {
            model       : 'recibosWeb.model.Periodo',
            filterOnLoad: false,
            remoteSort  : true,
            pageSize    : 25,
            remoteFilter: true,
            listeners: { datachanged: 'periodosDataChanged' }
        }
    }
});