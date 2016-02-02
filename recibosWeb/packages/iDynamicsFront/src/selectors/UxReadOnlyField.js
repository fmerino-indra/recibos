Ext.define('iDynamicsFront.selectors.UxReadOnlyField', {
   extend : 'Ext.form.FieldContainer',
   componentCls : 'ux-readOnlyField',
   alias : 'widget.uxreadonlyfield',
   //requires : ['iDynamicsFront.selectors.UxDateField'],
   layout : 'hbox', //Si no, no coloca correctamente la etiqueta encima

   name : null,
   displayName : null,

   initComponent : function(){
      var me=this;
      me.items = [{
         xtype: 'displayfield',
         name : me.displayName,
         flex: 1
      },{
         xtype: 'hiddenfield',
         name : me.name
      }]
      //me.name=me.name+"_container"
      me.callParent();
   }

});


/*{
              xtype: 'fieldcontainer',
              fieldLabel : tlb('admin.incidencia.Detail.operationId'),
              layout: 'hbox', //Si no, no coloca correctamente la etiqueta encima
              items: [{
                  xtype: 'displayfield',
                  name : 'operationName',
                  flex: 1
              },{
                  xtype: 'hiddenfield',
                  name : "operationId"
              }]
         },*/
