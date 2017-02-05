Ext.define('recibosWeb.view.preemision.detail.PreemisionDetailMaster', {
    extend  : 'Ext.panel.Panel',
    alias   : 'widget.preemision_preemisiondetailmaster',
    cls     : 'card master',
    layout  : {
        type: 'border'
    },
    glyph     : Glyphs.getIcon('square'),
    border  : false,
    requires: [
        'Ext.layout.container.Border',
        'recibosWeb.view.preemision.detail.PreemisionDetailMasterCtrl',
// TODO FMM        'recibosWeb.view.preemision.detail.PreemisionDetailFormCtrl',
        'recibosWeb.view.preemision.detail.PreemisionDetailForm',
        'recibosWeb.view.preemision.detail.PreemisionDetailGrid',
        'recibosWeb.view.preemision.detail.PreemisionDetailMasterVM'

    ],

    controller   : 'preemision-detailmaster',
    viewModel : {
        type: 'preemision-detailmaster'
    },

//    title     : t('preemision.detail.title'),
    header : false,
    items: [

        {
            xtype    : 'preemision_preemisiondetailform',
            reference: 'preemision_preemisiondetailform',
            region   : 'north',
            split    : true
        }
        ,
        {
            xtype    : 'preemision_preemisiondetailgrid',
            reference: 'preemision_preemisiondetailgrid',
            region   : 'center',
            border   : false,
            listeners: {
                selectionchange: 'onSelectionChange'
            }

        }
    ]
})
;
