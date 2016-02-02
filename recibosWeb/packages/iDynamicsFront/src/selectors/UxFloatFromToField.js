Ext.define('iDynamicsFront.selectors.UxFloatFromToField', {
   extend : 'Ext.form.FieldContainer',
   componentCls : 'ux-floatFromTofield',
   alias : 'widget.uxfloatfromtofield',
   requires : ['iDynamicsFront.selectors.UxFloatField'],

   label : "Cantidad", //Alias de fieldLabel
   name : "cantidad", //Creará dos campos, xxxx --> un fromXxxx y unToXxxx 
   fieldsExtraConfig : {}, //Opcional

   initComponent : function(){
      var me = this;
      if (me.fieldLabel==null) {
         me.fieldLabel=me.label
      }      
      me.layout='hbox',
      me.defaults = Ext.merge({ xtype:'uxfloatfield', flex:.5,  cls:'search-element' }, me.fieldsExtraConfig);
      me.items = [
         {
            itemId:"fromFloat", cls:'ux-fromFloat', emptyText:'Desde', name:'from'+me.name.charAt(0).toUpperCase()+ me.name.slice(1),
            listeners: {
               change:function (field){
                  var from = field.getValue(),
                     toField = me.down("#toFloat"),
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
         },
         {
            itemId:"toFloat", cls:'ux-toFloat', emptyText:'Hasta', name:'to'+me.name.charAt(0).toUpperCase()+ me.name.slice(1),
            listeners: {
               focus:function (field){
                  setTimeout(function(){ //Pongo un timeout para dejar que ocurran eventos en el otro campo (que termine de establecer su valor, onblur, etc...
                     var fromField = me.down("#fromFloat"),
                        fromValue = fromField.getValue();
                     if(fromValue!=null){
                        field.setMinValue( fromValue );
                        field.validate();
                     }
                  }, 1);
               }
            }
         }
      ]
      me.callParent();
   }

});
