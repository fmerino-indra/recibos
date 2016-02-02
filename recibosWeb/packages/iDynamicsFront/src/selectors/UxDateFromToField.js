Ext.define('iDynamicsFront.selectors.UxDateFromToField', {
   //extend : 'Ext.container.Container',
   extend : 'Ext.form.FieldContainer',
   componentCls : 'ux-dateFromTofield',
   alias : 'widget.uxdatefromtofield',
   //requires : ['iDynamicsFront.selectors.UxDateField'],

   label : "Fecha", //Alias de fieldLabel
   name : "Date", //Creará dos campos, xxxx --> un fromXxxx y unToXxxx 
   fieldsExtraConfig : {}, //Opcional
   
   separator: null, //Puede ser un elemento, por ejemplo { xtype:... }
   fromFieldConfig: {}, 
   toFieldConfig: {},

   initComponent : function(){
      var me = this,
         fromFieldBaseConfig, toFieldBaseConfig, fromFieldFinalConfig, toFieldFinalConfig;

      if (me.fieldLabel==null) {
         me.fieldLabel=me.label
      }      
      me.layout='hbox',
      me.defaults={
         //xtype : 'uxdatefield',
         xtype : 'datefield',
         flex  : .5,
         cls   : 'search-element'
      };
      me.defaults = Ext.merge(me.defaults, me.fieldsExtraConfig);
      
      fromFieldBaseConfig =  {
         itemId:"fromDate", cls:'ux-fromDate', emptyText:'Desde', name:'from'+me.name.charAt(0).toUpperCase()+ me.name.slice(1),
         listeners: {
            change:function (field){
               var from = field.getValue(),
                  toField = me.down("#toDate"),
                  to = toField.getValue();
               if (from!=null) {
                  if (to!=null && from>to){
                     toField.setValue(from);
                  }                      
                  toField.setMinValue( from );
                  toField.validate();                  
               } else {
                  toField.setMinValue( undefined );
                  toField.validate();         
               }
            }
         }            
      };
      toFieldBaseConfig =  {
         itemId:"toDate", cls:'ux-toDate', emptyText:'Hasta', name:'to'+me.name.charAt(0).toUpperCase()+ me.name.slice(1),
         listeners: {
            focus:function (field){
               setTimeout(function(){ //Pongo un timeout para dejar que ocurran eventos en el otro campo (que termine de establecer su valor, onblur, etc...
                  var fromField = me.down("#fromDate"),
                     fromValue = fromField.getValue();
                  if(fromValue!=null){
                     field.setMinValue( fromValue );
                     field.validate();
                  }
               }, 1);
            }
         }
      }
      
      fromFieldFinalConfig = Ext.merge(fromFieldBaseConfig, me.fromFieldConfig);
      toFieldFinalConfig = Ext.merge(toFieldBaseConfig, me.toFieldConfig);
      
      me.items = me.separator ? [ fromFieldFinalConfig, me.separator, toFieldFinalConfig ] : [ fromFieldFinalConfig, toFieldFinalConfig ]
      me.callParent();
   }

});
