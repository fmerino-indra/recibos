Ext.define('iDynamicsFront.selectors.UxGridColumnFiller',{
	extend			: 'Ext.grid.column.Column',
	alias			: 'widget.uxgridcolumnfiller',
	header    		: '',
	flex		 	: 1,
	sealed	 		: true,
	sortable	 	: false,
	tdCls	 		: 'ux-fillCell',
	menuDisabled	: true,
	hideable	 	: false,
	draggable 		: false 
});