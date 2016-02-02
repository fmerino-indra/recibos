Ext.define('iDynamicsFront.util.SelectionDialog', {
   extend : 'Ext.window.Window',
   alias : 'widget.uxselectiondialog',

   defaultTitle : 'Diálogo de búsqueda',
   title		: null,
   maximizable 	: true,
   closable		: true,
   modal 		: true,
   resizable	: false,
   height 		: null,
   width 		: null,
   layout 		: 'fit',

   btnAcceptText 		: 'Accept',
   btnCancelText 		: 'Cancel',
   acceptBtnGlyph		: null,
   cancelBtnGlyph		: null,
   buttons 				: [],
   panelInfoWidget		: null,
   selectionWidget 		: null,
   controlItemDblClick 	: false,
   autoSearch			: false,
   autoClose			: true,
 
    /*conf adicional 
    [
        {
            field: 'estado',
            value: 'activo',
            visible: false,
            editable:false
        }
    ]
    */
   extraFields: null,


   initComponent:function(){
      var dlg=this;
      
      // Indicamos el width y el height, si no se especifica se asigna 800x600
      if(!dlg.width){dlg.width = 800;}
      if(!dlg.height){dlg.height = 500;}
      
      // comprobamos si se ha pasado title
      if(!dlg.title){dlg.title = dlg.defaultTitle;}
      
      // Construyo la vista a incrustar dentro del dialogo      
      var selectionView = Ext.widget({xtype: dlg.selectionWidget, extraFields: dlg.extraFields, searchStore: dlg.selectionStore,
         autoClose: dlg.autoClose
/*         viewModel: {  
            stores  : {
                  searchStore: { 
                     type: dlg.selectionStore 
                  }
               }            
         }*/
      }); 
      
   

      // Compruebo si hay panelInfo
      if(dlg.panelInfoWidget){
      	var panelInfoView = Ext.widget(dlg.panelInfoWidget);
      	dlg.dockedItems = panelInfoView;
      }
      
      // Incrusto la vista
      dlg.items = [selectionView];
      
      // Construyo los botones bajo el dialogo
      dlg._btnAcept = Ext.create('Ext.button.Button',{ 
         text: dlg.btnAcceptText,
         iconCls : 'icon-accept',
         cls	 : 'ux-btn-highlighted',
         glyph	 : dlg.acceptBtnGlyph,
         handler : function(){
            dlg.fireEvent('acceptselection',dlg, selectionView);
            if (dlg.autoClose){
            	dlg.close();
            }
         }
      });
      dlg._btnCancel = Ext.create('Ext.button.Button',{
         text: dlg.btnCancelText,
         iconCls : 'icon-cancel',
         cls	 : 'ux-btn',
         glyph	 : dlg.cancelBtnGlyph,
         handler : function(){
            dlg.fireEvent('cancelselection',dlg);
            dlg.close(); //hide() ?
         }         
      });
      
      var buttons = [];
      // TODO: Recoger los botones y crearlos dinamicamente para que tengan handler
      Ext.each(dlg.buttons, function(btn){
      	var btnAux = Ext.create('Ext.button.Button',{
	         text		: btn.text,
	         iconCls 	: btn.iconCls,
	         cls	 	: 'ux-btn ' + btn.cls,
	         handler 	: function(){
	            dlg.fireEvent(btn.action,dlg);
	         }         
	    })
	    buttons[buttons.length] = btnAux;
      });
      
      dlg.buttons = buttons.concat([{xtype : 'tbfill'}, dlg._btnAcept, dlg._btnCancel]); //Construyo la botonera
      dlg.fbar = dlg.buttons;
      
      dlg.callParent();
//      dlg.addEvents( 'acceptselection', 'cancelselection' ); //Eventos definidos por convenio para este componente
      

      //Completar serachForm con extraFields
      



      dlg.show(); //muestro el dialogo

      // Realizamos una busqueda inicial
      if(dlg.autoSearch){
      	var btn = dlg.down('button[action=search]');
      	btn.fireEvent('click', btn);
      }
   }

});