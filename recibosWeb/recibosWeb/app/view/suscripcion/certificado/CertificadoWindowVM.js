Ext.define('recibosWeb.view.suscripcion.certificado.CertificadoWindowVM', {
    extend  : 'Ext.app.ViewModel',
    alias   : 'viewmodel.certificado-window',
    requires: [
        'recibosWeb.model.CertificadoDTO'
    ],
//    data : {
//    	xtype : 'suscripcion',
//    	name : 'suscripcion'
//    },
    selectedSuscription : {
    	xtype : 'recibosWeb.model.Suscripcion'
    },
    stores: {
        certificados: {
            model       : 'recibosWeb.model.CertificadoDTO',
            filterOnLoad: false,
            remoteSort  : true,
            pageSize    : 50,
            remoteFilter: true
//            ,
//            listeners: { datachanged: 'certificadoDataChanged' }
        }
    }
});