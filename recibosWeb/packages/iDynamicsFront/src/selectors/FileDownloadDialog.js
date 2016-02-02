Ext.define('iDynamicsFront.selectors.FileDownloadDialog', {
   extend: 'Ext.window.Window',
   alias: 'widget.filedownloaddialog',
   requires: ['iDynamicsFront.selectors.FileDownloader'],
   title : 'Descarga en Curso',
   maximizable : false,
   resizable : false,
   modal : false,
   closable : false,
   draggable : true, 
   layout : 'fit',
   
   width: 360,
   height: 70,
   componentCls :"x-filedownloaddialog",
   
   //argumentos: 
   fileDownloader: null,
   getDownloadUrlFn: null,

   initComponent : function(){
      var me=this;

      me.items = [
         {
            itemId: "FDDInternalContainer",
            layout: "fit",
            html:'<div class="x-mask-msg x-layer x-mask-msg-default"><div id="loadmask-1264-msgEl" style="position:relative" class="x-mask-loading">Por favor, espere mientras se procesa la descarga en curso...</div></div>'
         },{
           xtype: 'filedownloader'
         }
      ]
      /*
      me.fbar = [
         {xtype : 'tbfill'},
         {
            itemId : 'cancelBtn',        
            xtype : 'button',
            text : "Cancelar",
            cls : 'btn-ux',
            iconCls : 'btnIcon-cancelar'            
         },
         {
            itemId : 'saveBtn',
            xtype : 'button',
            text : me.aceptarTxt,
            disabled : true,
            cls : 'btn-ux',
            iconCls : 'btnIcon-aceptar'            
         }
      ] */ 
      
      me.callParent(arguments);
   },
   

   requestFileCreation: function (args){
      //alert("requestFileCreation")
      //debugger
      
      var me=this, 
         requestArguments=args.requestArguments,
         getDownloadUrlFn = args.getDownloadUrlFn,
         openInNewWindow = args.openInNewWindow,
         onBeforeDownload = args.onBeforeDownload,
         fileDownloader = me.fileDownloader,
         FDDInternalContainer = me.down("#FDDInternalContainer");
      if (me.descargando){
         //alert("ya estaba descargando")
         return ;
      }
      if (!getDownloadUrlFn){
         getDownloadUrlFn = me.getDownloadUrlFn
      }
      me.descargando=true;
      if (fileDownloader==null){
         fileDownloader = me.down("filedownloader")
      }
      me.show();
      //FDDInternalContainer.setLoading({msg:"Por favor, espere mientras se procesa la descarga en curso..."})
      requestArguments = Ext.Object.merge({
         success : function (response, operation) {
            var d_response = Ext.decode(response.responseText), 
               datos = d_response.datos,
               dwlUrl = getDownloadUrlFn(datos)
            me.hide();
            //FDDInternalContainer.setLoading(false)
            if (onBeforeDownload){
               onBeforeDownload();
            }
            if (fileDownloader!=null && !openInNewWindow){
               fileDownloader.load({ url: dwlUrl, params:null })  
            } else {
               ww=window.open("", "" , 'width=800,height=600,menubar=0,titlebar=0,toolbar=0,location=0', true);
               ww.location.href = dwlUrl;
            }
            me.descargando=false;
         },
         failure : function(response) { 
            me.hide();
            me.descargando=false;
         }     
      }, requestArguments);
      Ext.Ajax.request(requestArguments);
   }

});