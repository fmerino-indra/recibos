Ext.define('recibosWeb.view.periodo.master.PeriodoMaster', {
    extend  : 'Ext.panel.Panel',
    alias   : 'widget.periodo_periodomaster',
    cls     : 'card master',
    layout  : {
        type: 'border'
    },
    glyph     : Glyphs.getIcon('square'),
    border  : false,
    requires: [
        'recibosWeb.view.periodo.master.PeriodoSearchForm',
        'recibosWeb.view.periodo.master.PeriodoGrid',
        'Ext.layout.container.Border',
        'recibosWeb.view.periodo.master.PeriodoMasterCtrl',
        'recibosWeb.view.periodo.master.PeriodoMasterVM'
    ],

    controller: 'periodo-master',
    viewModel : {
        type: 'periodo-master'
    },

    initComponent: function () {
        this.title = "Lista de Periodos"//t('periodo.dataList.title');
        this.items = [
            {
                xtype    : 'periodo_periodosearchform',
                reference: 'periodo_periodosearchform',
                region   : 'west'
                /*,
                split    : true*/
                /*,
                height: 155*/
            },
            {
                xtype    : 'periodo_periodogrid',
                reference: 'periodo_periodogrid',
                bind     : {
                    store: '{periodos}',
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
