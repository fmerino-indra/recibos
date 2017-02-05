Ext.define('iDynamicsFront.selectors.UxPagingToolbar', {
   extend : 'Ext.toolbar.Paging',
   alias : 'widget.uxpagingtoolbar',
   componentCls : 'ux-pagingtoolbar',
   requires:"iDynamicsFront.selectors.FileDownloadDialog",
   
   title:null,   
   
   // Podemos sobreescribir estas propiedades o directamente sobreescribir los 
   // métodos getPrintUrl(grid), getPrintMethod(grid) y onResponseSucess(response, operation, grid, type)
   printUrl : "",
   printMethod : "POST",
   printDwlUrl : "",
   
   //al pulsar imprimir primero se hace un ajax.request a la printUrl pasando las columnas como json, los extraParams y el parametro format (pdf/xls)
   //A la vuelta se ejecuta onResponseSucess() que por defecto espera un id y abre una ventana a printDwlUrl añadiendo el "/{ID}" al final (para descargar el fichero generado)
   
   initComponent : function(){
      var me = this,
         newButtons;
      
      //Modifico la propiedad buttons
      newButtons = [
         {
            xtype : 'tbspacer',
            width : 8
         },{
            xtype : 'button',
            text : 'PDF',
            tooltip : 'Imprimir listado en formato PDF',
            //action      : 'exportar_grid_pdf',
            listeners : { click : me.exportar_grid_pdf, scope: this },
            iconCls : 'icon-small-tb-exportar-pdf',
            scale : "small",
            height : 24
         },{
            xtype : 'button',
            text : 'Excel',
            tooltip : 'Imprimir listado en formato Excel',
            //action      : 'exportar_grid_xls',
            listeners : { click : me.exportar_grid_xls, scope: this },  
            iconCls : 'icon-small-tb-exportar-excel',
            scale : 'small',
            height : 24
         },{
            xtype : 'tbspacer',
            width : 8
         }
      ];
      if (me.items) {
         me.items = newButtons.concat(me.items);
      } else if (me.buttons) {
         me.buttons = newButtons.concat(me.buttons);
      } else {
         me.buttons = newButtons;
      }
      me.callParent();
   },
   
   
   
   exportar_grid_pdf: function(btn){
      var me=this; 
      me.exportar_grid(btn, "pdf");
   },
   
   exportar_grid_xls: function(btn){
      var me=this; 
      me.exportar_grid(btn, "xls");
   },   
   
   exportar_grid: function(btn, type){
      var me=this,
         grid = btn.up("grid"),
         store = grid.getStore(),
         proxy = store.getProxy(), //Presuponemos un uxproxy
         sorters = Ext.clone(store.getSorters()),
         printUrl = me.getPrintUrl(grid, proxy),
         method = me.getPrintMethod(grid, proxy),
         extraParams = Ext.clone(proxy.extraParams),
         colArr = grid.columns,
         colDataArray = [];
debugger;
      extraParams = ( extraParams!=null ? extraParams : {} )
      //Añadimos los parámetros adicionales necesarios a los parámetros de búsqueda
      extraParams.format = type; 
      if (me.title){
          extraParams.title=me.title;
      }
      if(sorters.length > 0) {
         extraParams.sort = Ext.encode([{property: sorters[0].property, direction: sorters[0].direction}]);
      }
      for (var i=0; i<colArr.length; i++){
         if (colArr[i].dataIndex !=null && !colArr[i].hidden) {
            colDataArray[colDataArray.length] = { 
               value: colArr[i].text, 
               columnwidth: colArr[i].width,
               field:( colArr[i].printingDataIndex ? colArr[i].printingDataIndex : colArr[i].dataIndex )
            }
         }
      }
      var filedownloaddialog = me.getFileDownloadDialog();
      filedownloaddialog.requestFileCreation({  //Pedimos la creacion del fichero
        requestArguments: {
          url : printUrl,
          timeout : 600000,
          method : method,
          jsonData : colDataArray,
          params : extraParams
        },
        getDownloadUrlFn: me.getDownloadUrlFn
        //onBeforeDownload: function (){
          //me.getController('MenuController').info("El fichero ha sido correctamente generado, en breve empezará la descarga.")
        //},
      })
   },
   
   
   //Estos métodos son para sobreescribirlos según convenga : 
   
   getFileDownloadDialog: function (){
     var filedownloaddialog = Ext.widget({
       xtype:'filedownloaddialog'
     });
     return filedownloaddialog;
   },
   
   getPrintUrl : function (grid, proxy){
      var me = this;
      return me.printUrl;
      
   },
   
   getPrintMethod : function (grid, proxy){
      var me = this;
      return me.printMethod;
   },
   
   
   getDownloadUrlFn: function(datos, proxy){ //Lo podemos sobreescribir
     var documentoId = Ext.isString( datos )? datos : datos[0],  //SE ESPERA QUE SEA UN ARRAY
         printDwlUrl = me.printDwlUrl+"/"+documentoId;
     return printDwlUrl;
   }
   

});



