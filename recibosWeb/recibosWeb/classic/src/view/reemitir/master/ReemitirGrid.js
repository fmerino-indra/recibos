Ext.define('recibosWeb.view.reemitir.master.ReemitirGrid', {
    requires     : [
        'Ext.toolbar.Paging'
    ],
    extend       : 'Ext.grid.Panel',
    alias        : 'widget.reemitir_reemitirgrid',
    cls          : 'data-list usuarios',
    multiSelect  : true,
/*
    viewModel : {
        type: 'reemitir-master'
    },
*/
    bind     : {
        store: '{reemitirs}'
//        title: 'Listado de usuarios [{usuarios.totalCount}]'
    },


//    glyph      : Glyphs.getIcon('bars'),
//    viewConfig   : {
//        getRowClass: function (record) {
//            return record.get('enabled') ? "enabled" : "disabled";
//        }
//    },
    initComponent: function () {
        this.columns =
            [
                {
                    header   : t('cabecera.items.fechaEmision'),
                    dataIndex: 'fechaEmision',
                    flex     : 2,
                    renderer: Ext.util.Format.dateRenderer('d-m-Y')
                }
                ,
                {
                    header   : t('cabecera.items.codigoMes'),
                    dataIndex: 'codigoMes',
                    flex     : 1
                }
                ,
                {
                    header   : t('cabecera.items.periodo'),
                    dataIndex: 'periodo',
                    flex     : 1
                }
                ,
                {
                    header   : t('cabecera.items.fechaEnvio'),
                    dataIndex: 'fechaEnvio',
                    flex     : 2,
                    renderer: Ext.util.Format.dateRenderer('d-m-Y')
                }
                ,
                {
                    header   : t('cabecera.items.importe'),
                    dataIndex: 'importe',
                    flex     : 2,
                    xtype    : 'numbercolumn',
                    format   : '0,000.00'
                }
            ];
        /* TODO FMM */
        this.bbar = [
            '->'
            ,
            {
                xtype      : 'pagingtoolbar',
                displayInfo: true,
                cls        : 'paging-toolbar',
                bind       : {
                    store: '{reemitirs}'
                }
            }];

        this.callParent();
    }
/* TODO FMM
    ,


    translate: function () {
        var me = this;
        me.setTitle(t('listado.de.datos'));
        console.log('reemitir_reemitirgrid.translate.ini');
        me.down('#codigo').setText(t('reemitir.items.codigo'));
        me.down('#nombre').setText(t('reemitir.items.nombre'));
        me.down('#frecuencia').setText(t('reemitir.items.frecuencia'));
        me.down('#anticipacion').setText(t('reemitir.items.anticipacion'));
        me.down('pagingtoolbar').setLocale(AppConfig.getLocale());
        console.log('reemitir_reemitirgrid.translate.fin');
    }
    */
})
;
