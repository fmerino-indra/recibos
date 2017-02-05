Ext.define('recibosWeb.view.suscripcion.certificado.CertificadoWindowCtrl', {
    //extend: 'Ext.app.ViewController',
    extend: 'iDynamicsFront.util.ViewController',
    alias : 'controller.certificado-window',

    control: {
        'suscripcion_certificadowindow': {
            beforerender: 'certificadoLoad'
        }
    },

    /**
     * @autogenerated
     *
     */
    certificadoLoad: function () {
        var me = this;
        // TODO FMM
        me.certificadosSearch();
    },

    onAccept: function () {
    	var me = this, selectionModel, selection, store, idPersona, idCertificado;
    	selectionSuscription = me.getViewModel().get('selectedSuscription');
		idPersona = selectionSuscription.getData().persona.id;
		selection = me.getView().down('gridpanel').getSelection();
		if (selection)
			idCertificado = selection[0].getId();
		

        
        
        Ext.Ajax.request({
            url    : Ext.util.Format.format('{0}/certificados/{1}/{2}', Environment.getBaseUrl(), idPersona, idCertificado),
            params : [{idPersona: idPersona},{idCertificado:idCertificado}],
            method : 'GET',
            
            success: function (response, opts) {
				var pdfWin= window.open('data:application/pdf;base64,' + response.responseText, '_blank', 'resizable=no, status=no, location=no, scrollbars=no, height=650, width=840');
            },
            failure: function (response) {
                console.log(response);
                //me.error(response);
            }
        });
        
//        store = me.getViewModel().getStore('certificados');
//        store.filter([
//            {
//            	property:'id', 
//        		value : idPersona
//        	},
//        	{
//            	property:'idCertificado', 
//        		value : idCertificado.getId()
//        	}
//        ]);
//        
//        
//        store.load({
//            callback: function (records, operation, success) {
//                if (!success) {
//                    if (operation.getError().code === null) {
//                        me.error(operation.getError());
//                    }
//                } else {
//                    //TODO ..
//                }
//            }
//        });
        
    },


    /**
     * @autogenerated
     * Load parameters after the search button has been pressed
     * add parameters to a hashmap, and the last step, launches search
     *
     * @param searchBtn button pressed
     *
     */
    certificadosSearch: function () {
    	var me = this, selectionModel, selection, store, idPersona;
    	
    	selectionSuscription = me.getViewModel().get('selectedSuscription');
//		id = selectionSuscription.get('id');
		idPersona = selectionSuscription.getData().persona.id;
		
        store = me.getViewModel().getStore('certificados');
        store.filter('id', idPersona);
        store.loadPage(1, {
            callback: function (records, operation, success) {
                if (!success) {
                    if (operation.getError().code === null) {
                        me.error(operation.getError());
                    }
                } else {
                    //TODO ..
                }
            }
        });
    },

    suscripcionsDataChanged : function (suscripcions) {
        this.getViewModel().set('suscripcionsCount', suscripcions.getCount() > 0 ? suscripcions.getTotalCount() : 0);
    }

});
