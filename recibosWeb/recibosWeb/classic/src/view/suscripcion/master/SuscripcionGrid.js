Ext.define('recibosWeb.view.suscripcion.master.SuscripcionGrid', {
    requires     : [
        'Ext.toolbar.Paging'
    ],
    extend       : 'Ext.grid.Panel',
    alias        : 'widget.suscripcion_suscripciongrid',
    cls          : 'data-list usuarios',
    reference    : 'suscripciongrid',
    multiSelect  : false,
/*
    viewModel : {
        type: 'suscripcion-master'
    },
*/
    bind     : {
        store: '{suscripcions}'
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
	                header   : t('suscripcion.items.id'),
	                dataIndex: 'idSuscripcion',
	                flex     : 1
	            }
	            ,
                {
                    header   : t('suscripcion.items.fechaInicio'),
                    dataIndex: 'fechaInicio',
                    flex     : 1 ,
                    renderer: Ext.util.Format.dateRenderer('Y-m-d')
//                    renderer: Ext.Date.format('Y-m-d')
                }
                ,
                {
                    header   : t('suscripcion.items.euros'),
                    dataIndex: 'euros',
                    flex     : 1,
                    xtype    : 'numbercolumn',
                    format   : '0,000.00'
                }
                ,
                {
                    header   : t('suscripcion.items.nombrePersona'),
                    dataIndex: 'nombrePersona',
                    flex     : 1
                }
                ,
                {
                    header   : t('suscripcion.items.activo'),
                    dataIndex: 'activo',
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
                    store: '{suscripcions}'
                }
            }];

        this.callParent();
    }
/* TODO FMM
    ,


    translate: function () {
        var me = this;
        me.setTitle(t('listado.de.datos'));
        console.log('suscripcion_suscripciongrid.translate.ini');
        me.down('#codigo').setText(t('suscripcion.items.codigo'));
        me.down('#nombre').setText(t('suscripcion.items.nombre'));
        me.down('#frecuencia').setText(t('suscripcion.items.frecuencia'));
        me.down('#anticipacion').setText(t('suscripcion.items.anticipacion'));
        me.down('pagingtoolbar').setLocale(AppConfig.getLocale());
        console.log('suscripcion_suscripciongrid.translate.fin');
    }
    */
})
;
