Ext.define('iDynamicsFront.selectors.UxIntegerField',{
	extend: 'Ext.form.field.Number',
	alias: 'widget.uxintegerfield',
	
	componentCls : 'ux-integerfield',
    //decimalSeparator : t('decimalSeparator'),
    //decimalPrecision: $AC.getDecimalPrecision()
	allowDecimals:false,
   minValue : 0,
   //maxLength : 7,
   maxValue : 9999999,
   //enforceMaxLength : true
   
   listeners : {
      blur : function(field, ev, eOpts){
         if (field.getValue() > field.maxValue) {
            field.setValue(field.maxValue)
         }
      }
   }

});