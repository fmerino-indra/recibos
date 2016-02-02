Ext.define('iDynamicsFront.selectors.UxFloatField', {
   extend : 'Ext.form.field.Number',
   alias : 'widget.uxfloatfield',
   componentCls : 'ux-floatfield',
   
   decimalSeparator : t('decimalSeparator'),
   decimalPrecision : $AC.getDecimalPrecision(),
   
   //maxLength : 7,
   //enforceMaxLength : true,
   //No tiene sentido usar un maxLength para un float. Lo hago con maxValue
   maxValue : 9999999.99,

   minValue : 0,

   listeners : {
      blur : function(field, ev, eOpts){
         if (field.getValue() > field.maxValue) {
            field.setValue(field.maxValue)
         }
      }
   }

});