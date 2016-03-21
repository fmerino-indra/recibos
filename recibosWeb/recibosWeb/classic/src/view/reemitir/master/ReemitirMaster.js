Ext.define('recibosWeb.view.reemitir.master.ReemitirMaster', {
    extend  : 'Ext.panel.Panel',
    alias   : 'widget.reemitir_reemitirmaster',
    cls     : 'card master',
    layout  : {
        type: 'border'
    },
    glyph     : Glyphs.getIcon('square'),
    border  : false,
    requires: [
        'recibosWeb.view.reemitir.master.ReemitirSearchForm',
        'recibosWeb.view.reemitir.master.ReemitirGrid',
        'Ext.layout.container.Border',
        'recibosWeb.view.reemitir.master.ReemitirMasterCtrl',
        'recibosWeb.view.reemitir.master.ReemitirMasterVM'
    ],

    controller: 'reemitir-master',
    viewModel : {
        type: 'reemitir-master'
    },

    initComponent: function () {
        this.title = "Lista de Cabeceras a reemitir"//t('reemitir.dataList.title');
        this.items = [
            {
                xtype    : 'reemitir_reemitirsearchform',
                reference: 'reemitir_reemitirsearchform',
                region   : 'west',
                split    : true
//                height: 155
            },
            {
                xtype    : 'reemitir_reemitirgrid',
                reference: 'reemitir_reemitirgrid',
                bind     : {
                    store: '{reemitirs}',
                    title: 'Listado de usuarios [{usuarios.totalCount}]'
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
