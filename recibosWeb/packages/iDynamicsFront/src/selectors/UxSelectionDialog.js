/**
 * Ventana de dialogo 
 */
Ext.define('iDynamicsFront.selectors.UxSelectionDialog', {
   extend : 'Ext.window.Window',
   alias : 'widget.uxselectiondialog2',

   title : 'Diálogo de búsqueda',

   maximizable : true,
   //modal : true,
   height : 500,
   width : 800,
   layout : 'fit',
   constrainHeader : true,

   btnAcceptText : 'Accept',
   btnCancelText : 'Cancel',
   acceptBtnGlyph: null,
   cancelBtnGlyph: null,
   buttons : [],
   /**
    * Ventana de seleccion, que incluira un searchform {@link iDynamicsFront.util.SearchFormAuto} y 
    * el grid de seleccion
    * @type {Ext.widget} 
    */
   selectionWidget : null,
   controlItemDblClick : false,
   autoClose : true,
   aceptDisabledIfNoSelection : false,

   initComponent : function() {
      var dlg = this, store;
      
      //var selectionView = Ext.widget('localizacionsearch', { });
      selectionView = Ext.widget(dlg.selectionWidget);
      // ----- Construyo la vista a incrustar dentro del dialogo

      //xtype: dataview ?
      var injectedtablepanel = (selectionView.is('tablepanel') ? selectionView : selectionView.down('tablepanel'));
      // ----- Busco el objeto descendiente con selectionModel
      if (injectedtablepanel === undefined || injectedtablepanel === null) {
         Ext.Error.raise('Es necesario un componente que extienda de "Ext.panel.Table" dentro del diálogo');
      }

      //injectedtablepanel.view.loadMask = false;

      var selModel = injectedtablepanel.getSelectionModel();

      dlg.injectedtablepanel = injectedtablepanel;
      dlg.selModel = selModel;

      dlg.items = [selectionView];
      // Incrusto la vista
      dlg._btnAcept = Ext.create('Ext.button.Button', {// ----- Construyo los botones bajo el dialogo
         text : dlg.btnAcceptText,
         cls : 'btn-ux',
         iconCls : 'btnIcon-aceptar',
         glyph	 : dlg.acceptBtnGlyph,
         handler : function() {
            dlg.fireEvent('acceptselection', dlg, selModel);
            if (dlg.autoClose) {
               dlg.close();
            }
         }
      });
      dlg._btnCancel = Ext.create('Ext.button.Button', {
         text : dlg.btnCancelText,
         cls : 'btn-ux',
         iconCls : 'btnIcon-cancelar',
         glyph	 : dlg.cancelBtnGlyph,
         handler : function() {
            dlg.fireEvent('cancelselection', dlg);
            if (dlg.autoClose) {
               dlg.close();
               //hide() ?
            }
         }
      });

      if (dlg.aceptDisabledIfNoSelection) {
         dlg._btnAcept.setDisabled(true);
         injectedtablepanel.on("selectionchange", function(selectionmodel) {
            var selection = selectionmodel.getSelection();
            dlg._btnAcept.setDisabled(selection == null || selection.length == 0);
         });
      }

      dlg.buttons = dlg.buttons.concat([{
         xtype : 'tbfill'
      }, dlg._btnAcept, dlg._btnCancel]);
      //Construyo la botonera
      dlg.fbar = dlg.buttons;
      dlg.cls = 'ux-selection-dialog ' + dlg.cls;
      dlg.callParent();
      //Necesario siempre
//      dlg.addEvents('acceptselection', 'cancelselection');     //FMA
      //Eventos definidos por convenio para este componente

      if (dlg.controlItemDblClick) {
         injectedtablepanel.on("itemdblclick", function(obj, record, item, index, e, eOpts) {
            if (selModel.getSelection().length > 0) {
               dlg.fireEvent('acceptselection', dlg, selModel);
               if (dlg.autoClose) {
                  dlg.close();
               }
            }
         });
      }
      
	  dlg.on("beforeclose", dlg.close);
      //dlg.show(); //muestro el dialogo
   },
	
	close: function(window){
    	var me = this;
    	//debugger
    	Ext.getBody().focus();
    	me.doClose();
    },
    
   /**
    * @private
    * @return {[type]}
    */
   afterRender : function() {
      this.callParent();
   }
}); 