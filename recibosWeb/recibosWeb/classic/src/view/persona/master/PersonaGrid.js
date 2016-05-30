Ext.define('recibosWeb.view.persona.master.PersonaGrid', {
    requires     : [
        'Ext.toolbar.Paging'
    ],
    extend       : 'Ext.grid.Panel',
    alias        : 'widget.persona_personagrid',
    cls          : 'data-list usuarios',
    multiSelect  : false,
/*
    viewModel : {
        type: 'persona-master'
    },
*/
    bind     : {
        store: '{personas}'
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
	                header   : t('persona.items.nif'),
	                dataIndex: 'nif',
	                flex     : 1
	            }
	            ,
                {
                    header   : t('persona.items.nombre'),
                    dataIndex: 'nombre',
                    flex     : 1
                }
                ,
                {
                    header   : t('persona.items.domicilio'),
                    dataIndex: 'domicilio',
                    flex     : 1
                }
                ,
                {
                    header   : t('persona.items.cp'),
                    dataIndex: 'cp',
                    flex     : 1
                }
                ,
                {
                    header   : t('persona.items.tfno'),
                    dataIndex: 'tfno',
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
                    store: '{personas}'
                }
            }];

        this.callParent();
    }
/* TODO FMM
    ,


    translate: function () {
        var me = this;
        me.setTitle(t('listado.de.datos'));
        console.log('persona_personagrid.translate.ini');
        me.down('#codigo').setText(t('persona.items.codigo'));
        me.down('#nombre').setText(t('persona.items.nombre'));
        me.down('#frecuencia').setText(t('persona.items.frecuencia'));
        me.down('#anticipacion').setText(t('persona.items.anticipacion'));
        me.down('pagingtoolbar').setLocale(AppConfig.getLocale());
        console.log('persona_personagrid.translate.fin');
    }
    */
})
;
