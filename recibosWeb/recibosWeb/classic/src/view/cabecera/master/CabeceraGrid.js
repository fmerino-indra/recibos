Ext.define('recibosWeb.view.cabecera.master.CabeceraGrid', {
    requires     : [
        'Ext.toolbar.Paging'
    ],
    extend       : 'Ext.grid.Panel',
    alias        : 'widget.cabecera_cabeceragrid',
    cls          : 'data-list usuarios',
    multiSelect  : true,
/*
    viewModel : {
        type: 'cabecera-master'
    },
*/
    bind     : {
        store: '{cabeceras}'
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
                ,
                {
                    header   : t('cabecera.items.importeDevuelto'),
                    dataIndex: 'importeDevuelto',
                    flex     : 2,
                    xtype    : 'numbercolumn',
                    format   : '0,000.00'
                }
                ,
                {
                    header   : t('cabecera.items.devueltos'),
                    dataIndex: 'devueltos',
                    flex     : 1,
                    xtype    : 'numbercolumn',
                    format   : '0,000'
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
                    store: '{cabeceras}'
                }
            }];

        this.callParent();
    }
/* TODO FMM
    ,


    translate: function () {
        var me = this;
        me.setTitle(t('listado.de.datos'));
        console.log('cabecera_cabeceragrid.translate.ini');
        me.down('#codigo').setText(t('cabecera.items.codigo'));
        me.down('#nombre').setText(t('cabecera.items.nombre'));
        me.down('#frecuencia').setText(t('cabecera.items.frecuencia'));
        me.down('#anticipacion').setText(t('cabecera.items.anticipacion'));
        me.down('pagingtoolbar').setLocale(AppConfig.getLocale());
        console.log('cabecera_cabeceragrid.translate.fin');
    }
    */
})
;
