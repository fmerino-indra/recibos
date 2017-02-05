Ext.define('recibosWeb.view.cabecera.master.CabeceraMaster', {
    extend  : 'Ext.panel.Panel',
    alias   : 'widget.cabecera_cabeceramaster',
    cls     : 'card master',
    layout  : {
        type: 'border'
    },
    glyph     : Glyphs.getIcon('square'),
    border  : false,
    requires: [
        'recibosWeb.view.cabecera.master.CabeceraSearchForm',
        'recibosWeb.view.cabecera.master.CabeceraGrid',
        'Ext.layout.container.Border',
        'recibosWeb.view.cabecera.master.CabeceraMasterCtrl',
        'recibosWeb.view.cabecera.master.CabeceraMasterVM'
    ],

    controller: 'cabecera-master',
    viewModel : {
        type: 'cabecera-master'
    },

    initComponent: function () {
        this.title = "Lista de Cabeceras"//t('cabecera.dataList.title');
        this.items = [
            {
                xtype    : 'cabecera_cabecerasearchform',
                reference: 'cabecera_cabecerasearchform',
                region   : 'west',
                split    : true
//                height: 155
            },
            {
                xtype    : 'cabecera_cabeceragrid',
                reference: 'cabecera_cabeceragrid',
                bind     : {
                    store: '{cabeceras}',
                    title: 'Listado de cabeceras [{cabeceras.totalCount}]'
                },
                region   : 'center',
                border   : false,
                listeners: {
                    selectionchange: 'onSelectionChange'
                }
            }
        ];
        this.callParent();
    },
    getSelection : function () {
        var me = this, table, selModel;
        table = me.down('grid');
        selModel = table.getSelectionModel();
        return selModel.getSelection();
    }
})
;
