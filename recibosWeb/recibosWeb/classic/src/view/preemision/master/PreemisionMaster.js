Ext.define('recibosWeb.view.preemision.master.PreemisionMaster', {
    extend  : 'Ext.panel.Panel',
    alias   : 'widget.preemision_preemisionmaster',
    cls     : 'card master',
    layout  : {
        type: 'border'
    },
    glyph     : Glyphs.getIcon('square'),
    border  : false,
    requires: [
        'recibosWeb.view.preemision.master.PreemisionSearchForm',
        'recibosWeb.view.preemision.master.PreemisionGrid',
        'Ext.layout.container.Border',
        'recibosWeb.view.preemision.master.PreemisionMasterCtrl',
        'recibosWeb.view.preemision.master.PreemisionMasterVM'
    ],

    controller: 'preemision-master',
    viewModel : {
        type: 'preemision-master'
    },

    initComponent: function () {
        this.title = "Lista de Cabeceras (a emitir)"//t('preemision.dataList.title');
        this.items = [
            {
                xtype    : 'preemision_preemisionsearchform',
                reference: 'preemision_preemisionsearchform',
                region   : 'west',
                split    : true
//                height: 155
            },
            {
                xtype    : 'preemision_preemisiongrid',
                reference: 'preemision_preemisiongrid',
                bind     : {
                    store: '{preemisions}',
                    title: 'Dashboard  [{usuarios.totalCount}]'
                },
                region   : 'center',
                border   : false
// Se hace mediante control en el controler del viewport
//                ,
//                listeners: {
//                    selectionchange: 'onSelectionChange'
//                }
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
