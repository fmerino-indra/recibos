Ext.define('recibosWeb.view.suscripcion.SuscripcionViewport', {
    extend    : 'Ext.container.Container',
    alias     : 'widget.suscripcion_suscripcionviewport',
    requires  : ['iDynamicsFront.utils.UxMainToolbar',
        'recibosWeb.view.suscripcion.master.SuscripcionMaster',
        'recibosWeb.view.suscripcion.detail.SuscripcionDetailForm',
        'recibosWeb.view.suscripcion.SuscripcionViewportCtrl',
        'recibosWeb.view.suscripcion.SuscripcionViewportVM'],
    layout    : 'card',
    cls       : 'suscripcion viewport',
    controller: 'suscripcion-viewport',
    viewModel : {
        type: 'suscripcion-viewport'
    },
    items: [
        {
            xtype      : 'suscripcion_suscripcionmaster',
            reference  : 'suscripcion_suscripcionmaster',
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
            xtype    : 'suscripcion_suscripciondetailform',
            reference: 'suscripcion_suscripciondetailform'
        }
    ],
    html: '<div class="top-shadow"></div>'
});
