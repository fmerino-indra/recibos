Ext.define('recibosWeb.view.preemision.PreemisionViewport', {
    extend    : 'Ext.container.Container',
    alias     : 'widget.preemision_preemisionviewport',
    requires  : ['iDynamicsFront.utils.UxMainToolbar',
        'recibosWeb.view.preemision.master.PreemisionMaster',
        'recibosWeb.view.preemision.detail.PreemisionDetailMaster',
        'recibosWeb.view.preemision.PreemisionViewportCtrl',
        'recibosWeb.view.preemision.PreemisionViewportVM'],
    layout    : 'card',
    cls       : 'preemision viewport',
    controller: 'preemision-viewport',
    viewModel : {
        type: 'preemision-viewport'
    },
    items: [
//        { 
//            xtype      : 'preemision_preemisioncriteria',
//            reference  : 'preemision_preemisioncriteria'
//        },
        {
            xtype      : 'preemision_preemisionmaster',
            reference  : 'preemision_preemisionmaster',
            dockedItems: [
                {
                    xtype: 'ux-maintoolbar',
                    dock : 'top',
                    cls  : 'main-toolbar',
                    scale: 'medium',
                    items: [
                        {text: 'Emitir', action: 'emitir', bind: {disabled: '{!canAnyEmitted}'}, glyph : Glyphs.getIcon('money')},
                        '-',
                        {text: 'Generar XML', action: 'generar', bind: {disabled: '{!canAnyGenerate}'}, glyph : Glyphs.getIcon('code')},
                        '-',
                        'excel',
                        'pdf'
                    ]
                }
            ]
        },
        {
            xtype    : 'preemision_preemisiondetailmaster',
            reference: 'preemision_preemisiondetailmaster'
        }
    ],
    html: '<div class="top-shadow"></div>'
});
