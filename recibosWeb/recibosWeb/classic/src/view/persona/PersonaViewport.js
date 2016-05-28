Ext.define('recibosWeb.view.persona.PersonaViewport', {
    extend    : 'Ext.container.Container',
    alias     : 'widget.persona_personaviewport',
//    requires  : ['iDynamicsFront.utils.UxMainToolbar',
//        'recibosWeb.view.persona.master.PersonaMaster',
//        'recibosWeb.view.persona.detail.PersonaDetailForm',
//        'recibosWeb.view.persona.PersonaViewportCtrl',
//        'recibosWeb.view.persona.PersonaViewportVM'],
    layout    : 'card',
    cls       : 'persona viewport',
    controller: 'persona-viewport',
    viewModel : {
        type: 'persona-viewport'
    },
    items: [
        {
            xtype      : 'persona_personamaster',
            reference  : 'persona_personamaster',
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
                        '-',
                        'excel',
                        'pdf'
                    ]
                }
            ]
        },
        {
            xtype    : 'persona_personadetailform',
            reference: 'persona_personadetailform'
        }

    ],
    html: '<div class="top-shadow"></div>'
});
