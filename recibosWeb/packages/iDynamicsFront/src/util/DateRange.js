Ext.define('iDynamicsFront.util.DateRange', {
			extend 	: 'Ext.container.Container',
			alias 	: 'widget.uxdaterange',
			layout 	: 'hbox',
			requires: ['Ext.form.field.Date'],
			fieldLabel : '',
			startName: 'fromDate',
			endName: 'toDate',
			format : 'd/m/Y',
			startCls: 'ux-fromDate',
			endCls: 'ux-toDate',

			initComponent : function() {
				var me = this;

				me.initDate = Ext.create('Ext.form.field.Date',{
							xtype 		: 'datefield',
							fieldLabel 	: me.fieldLabel,
							name 		: me.startName,
							cls 		: me.startCls,
							minWidth	: 120,
							//flex 		: 0.5,
							emptyText 	: t('commons.searchForm.desde'),
							format 		: me.format
						});
				me.endDate = Ext.create('Ext.form.field.Date',{
							xtype 		: 'datefield',
							fieldLabel 	: '&nbsp;',
							name 		: me.endName,
							cls 		: me.endCls,
							minWidth	: 120,
							//flex 		: 0.5,
							emptyText 	: t('commons.searchForm.hasta'),
                            format      : me.format
						});

				Ext.applyIf(me.initDate,{vtype:'daterange'});
				Ext.applyIf(me.endDate,{vtype:'daterange'});
	
				me.items = [me.initDate, me.endDate];
				me.initDate.on('change',me.onInitDateChange,me);
				me.endDate.on('change',me.onEndDateChange,me);
				me.callParent();
			},
			
			onInitDateChange: function (dateField,newValue,oldValue) {
				var me = this;
				me.endDate.setMinValue(newValue);
			},

			onEndDateChange: function (dateField,newValue,oldValue) {
				var me = this;
				me.initDate.setMaxValue(newValue);
			}
		})