Ext.define('recibosWeb.view.periodo.PeriodoViewport', {
    extend    : 'Ext.container.Container',
    alias     : 'widget.periodo_periodoviewport',
    requires  : ['iDynamicsFront.utils.UxMainToolbar',
        'recibosWeb.view.periodo.master.PeriodoMaster',
        'recibosWeb.view.periodo.detail.PeriodoDetailForm',
        'recibosWeb.view.periodo.PeriodoViewportCtrl',
        'recibosWeb.view.periodo.PeriodoViewportVM'],
    layout    : 'card',
    cls       : 'periodo viewport',
    controller: 'periodo-viewport',
    viewModel : {
        type: 'periodo-viewport'
    },
    items: [
        {
            xtype      : 'periodo_periodomaster',
            reference  : 'periodo_periodomaster',
            dockedItems: [
                {
                    xtype: 'ux-maintoolbar',
                    dock : 'top',
                    cls  : 'main-toolbar',
                    scale: 'medium',
                    items: [
                        'add',
                        'edit',
                        '-',
                        'delete',
                        '-',
/*
                        {text: 'desactivar', action: 'disable', bind: {disabled: '{!hasAnyActive}'}, glyph : Glyphs.getIcon('lock')},
                        {text: 'activar', action: 'enable', bind: {disabled: '{!hasAnyInactive}'}, glyph : Glyphs.getIcon('unlock_alt')},
                        {text: 'resetear contraseña', action: 'resetPass', glyph : Glyphs.getIcon('refresh')},
*/
                        '-',
                        'excel',
                        'pdf'
                    ]
                }
            ]
        },
        {
            xtype    : 'periodo_periododetailform',
            reference: 'periodo_periododetailform'
        }
    ],
    html: '<div class="top-shadow"></div>'
});
