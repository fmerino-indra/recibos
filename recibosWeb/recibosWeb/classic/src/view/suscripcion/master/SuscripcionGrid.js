Ext.define('recibosWeb.view.suscripcion.master.SuscripcionGrid', {
    requires     : [
        'Ext.toolbar.Paging'
    ],
    extend       : 'Ext.grid.Panel',
    alias        : 'widget.periodo_periodogrid',
    cls          : 'data-list usuarios',
    multiSelect  : true,
/*
    viewModel : {
        type: 'suscripcion-master'
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
                    header   : t('suscripcion.items.codigo'),
                    dataIndex: 'codigo',
                    flex     : 1
                }
                ,
                {
                    header   : t('suscripcion.items.nombre'),
                    dataIndex: 'nombre',
                    flex     : 1
                }
                ,
                {
                    header   : 'Frecuencia',//t('suscripcion.items.frecuencia'),
                    dataIndex: 'frecuencia',
                    flex     : 1
                }
                ,
                {
                    header   : 'Anticipacion',//t('suscripcion.items.anticipacion'),
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
        me.down('#codigo').setText(t('suscripcion.items.codigo'));
        me.down('#nombre').setText(t('suscripcion.items.nombre'));
        me.down('#frecuencia').setText(t('suscripcion.items.frecuencia'));
        me.down('#anticipacion').setText(t('suscripcion.items.anticipacion'));
        me.down('pagingtoolbar').setLocale(AppConfig.getLocale());
        console.log('periodo_periodogrid.translate.fin');
    }
    */
})
;
