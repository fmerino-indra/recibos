Ext.define('recibosWeb.view.periodo.master.PeriodoGrid', {
    requires     : [
        'Ext.toolbar.Paging'
    ],
    extend       : 'Ext.grid.Panel',
    alias        : 'widget.periodo_periodogrid',
    cls          : 'data-list usuarios',
    multiSelect  : true,
/*
    viewModel : {
        type: 'periodo-master'
    },
*/
    bind     : {
        store: '{periodos}'
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
                    header   : t('periodo.items.codigo'),
                    dataIndex: 'codigo',
                    flex     : 1
                }
                ,
                {
                    header   : t('periodo.items.nombre'),
                    dataIndex: 'nombre',
                    flex     : 1
                }
                ,
                {
                    header   : 'Frecuencia',//t('periodo.items.frecuencia'),
                    dataIndex: 'frecuencia',
                    flex     : 1
                }
                ,
                {
                    header   : 'Anticipacion',//t('periodo.items.anticipacion'),
                    dataIndex: 'anticipacion',
                    flex     : 1
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
                    store: '{periodos}'
                }
            }];

        this.callParent();
    }
/* TODO FMM
    ,


    translate: function () {
        var me = this;
        me.setTitle(t('listado.de.datos'));
        console.log('periodo_periodogrid.translate.ini');
        me.down('#codigo').setText(t('periodo.items.codigo'));
        me.down('#nombre').setText(t('periodo.items.nombre'));
        me.down('#frecuencia').setText(t('periodo.items.frecuencia'));
        me.down('#anticipacion').setText(t('periodo.items.anticipacion'));
        me.down('pagingtoolbar').setLocale(AppConfig.getLocale());
        console.log('periodo_periodogrid.translate.fin');
    }
    */
})
;
