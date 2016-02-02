Ext.define('iDynamicsFront.selectors.UxDateField',{
	extend: 'Ext.form.field.Date',
	requires:["iDynamicsFront.util.Date", "iDynamicsFront.selectors.DatePickerZulu"],
	componentCls : 'ux-datefield',
	alias: 'widget.uxdatefield',
   format: t('dateFormat'),
   
   //todo lo que sigue es para ZULU: 
   
   onExpand: function() {
        var value = this.getValue();
        //this.picker.setValue(Ext.isDate(value) ? value : new Date());
        this.picker.setValue(Ext.isDate(value) ? value : Ux.utils.Date.getUTCDate() );  //Ext.Date.add(new Date(),Ext.Date.DAY,-2)
   },
   
   //ZULU: 
   createPicker: function() {
        var me = this,
            format = Ext.String.format;
        //return new Ext.picker.Date({
        return new iDynamicsFront.selectors.DatePickerZulu({ //ZULU
            pickerField: me,
            ownerCt: me.ownerCt,
            renderTo: document.body,
            floating: true,
            hidden: true,
            focusOnShow: true,
            minDate: me.minValue,
            maxDate: me.maxValue,
            disabledDatesRE: me.disabledDatesRE,
            disabledDatesText: me.disabledDatesText,
            disabledDays: me.disabledDays,
            disabledDaysText: me.disabledDaysText,
            format: me.format,
            showToday: me.showToday,
            startDay: me.startDay,
            minText: format(me.minText, me.formatDate(me.minValue)),
            maxText: format(me.maxText, me.formatDate(me.maxValue)),
            listeners: {
                scope: me,
                select: me.onSelect
            },
            keyNavConfig: {
                esc: function() {
                    me.collapse();
                }
            }
        });
    }
});