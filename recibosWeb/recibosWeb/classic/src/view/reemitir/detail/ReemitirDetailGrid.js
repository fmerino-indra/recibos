Ext.define('recibosWeb.view.reemitir.detail.ReemitirDetailGrid', {
    requires     : [
        'Ext.toolbar.Paging',
    ],
    extend       : 'Ext.grid.Panel',
    alias        : 'widget.reemitir_reemitirdetailgrid',
    cls          : 'data-list usuarios',
    multiSelect  : false,
    model        : 'recibosWeb.model.EmisionDTO',
    columns    : [
        {
            header   : t('emision.items.id'),
            dataIndex: 'id',
            flex     : 1
        }
        ,
        {
            header   : t('emision.items.nombre'),
            dataIndex: 'titular',
            flex     : 3
        }
        ,
        {
            header   : t('reemitir.items.devuelto'),
            dataIndex: 'devuelto',
            flex     : 1
        }
        ,
        {
            header   : t('reemitir.items.importe'),
            dataIndex: 'importe',
            flex     : 2,
            xtype    : 'numbercolumn',
            format   : '0,000.00'
        }
        ,
        {
            header   : t('reemitir.items.divisa'),
            dataIndex: 'divisa',
            flex     : 1
        }
    ]
})
;
