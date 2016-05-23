Ext.define('recibosWeb.view.cabecera.detail.CabeceraDetailGrid', {
    requires     : [
        'Ext.toolbar.Paging'
    ],
    extend       : 'Ext.grid.Panel',
    alias        : 'widget.cabecera_cabeceradetailgrid',
    cls          : 'data-list usuarios',
    multiSelect  : false,
    // This attribute is not from ExtJS
    model        : 'recibosWeb.model.EmisionDTO',
    initComponent: function () {
        this.columns = [
            {
                header: t('emision.items.id'),
                dataIndex: 'id',
                flex: 1
            }
            ,
            {
                header: t('emision.items.nombre'),
                dataIndex: 'titular',
                flex: 3
            }
            ,
            {
                header: t('cabecera.items.devuelto'),
                dataIndex: 'devuelto',
                flex: 1
            }
            ,
            {
                header: t('cabecera.items.importe'),
                dataIndex: 'importe',
                flex: 2,
                xtype: 'numbercolumn',
                format: '0,000.00'
            }
            ,
            {
                header: t('cabecera.items.divisa'),
                dataIndex: 'divisa',
                flex: 1
            }
        ];
        this.callParent();
    }
});
