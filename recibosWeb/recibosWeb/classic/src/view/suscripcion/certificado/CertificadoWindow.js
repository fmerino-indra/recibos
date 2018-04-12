/**
 *
 */
Ext.define('recibosWeb.view.suscripcion.certificado.CertificadoWindow', {
	extend : 'Ext.window.Window',
	xtype : 'certificadoWindow',
    alias        : 'widget.suscripcion_certificadowindow',
	requires : ['Ext.grid.Panel','recibosWeb.view.suscripcion.certificado.CertificadoWindowCtrl',
	            'recibosWeb.view.suscripcion.certificado.CertificadoWindowVM'],

    controller: 'certificado-window',
    viewModel : {
        type: 'certificado-window'
    },

    
	cls : 'certificadoWindow',
	//reference: 'commandWindow',
//	controller : 'commandWindow',
//	viewModel : 'commandWindow',
	height : 375,
	width : 690,
	title : t('certificadoWindow.title'), //redefined later
	// : ,
	scrollable : true,
	resizable : false,
	//bodyPadding : 10,
	constrain : true,
	closable : true,

//	listeners : {
//		beforeshow : 'onBeforeShow'
//	},
	bbar : ['->', {
		text : t('button.accept'),
		width: 75,
		height:30,
		handler : 'onAccept'
	},{
		text : t('button.cancel'),
		width: 75,
		height:30,
		handler: function () { this.up('window').close(); }
	},{
		text : 'PDF',
		width: 75,
		height:30,
		handler: 'onAcceptBase64'
	}],
	layout : {
		type : 'fit',
		pack : 'start',
		align : 'stretch'
	},
	items : [{
	         xtype: 'gridpanel',
	         multiSelect  : false,	
	         bind     : {
	             store: '{certificados}'
	         },
	         scrollable : true,
	         columns: [
	   	            {
		                header   : 'Año',
		                dataIndex: 'year',
		                flex     : 1
		            },{
		                header   : 'Importe',
		                dataIndex: 'sumImporte',
		                flex     : 1		            	
		            },{
		                header   : '#',
		                dataIndex: 'count',
		                flex     : 1		            	
		            }
	                   ]}
	]
});

