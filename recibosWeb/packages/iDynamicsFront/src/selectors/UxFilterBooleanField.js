Ext.define('iDynamicsFront.selectors.UxFilterBooleanField',{
   extend: 'Ext.form.field.ComboBox',
	//extend: 'Ext.form.field.Checkbox',
	alias: 'widget.uxfilterbooleanfield',
	componentCls : 'ux-combo',

   editable:false,
   displayField : 'text',
   valueField : 'value',
   store: Ext.create('APP.store.Booleanos')
   
});