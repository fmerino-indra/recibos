Ext.define('recibosWeb.view.Viewer', {
    extend  : 'Ext.container.Container',
    alias   : 'widget.viewer',
    requires: [
        'recibosWeb.view.persona.PersonaViewport',
        'recibosWeb.view.periodo.PeriodoViewport',
        'recibosWeb.view.cabecera.CabeceraViewport',
        'recibosWeb.view.reemitir.ReemitirViewport',
        'recibosWeb.view.suscripcion.SuscripcionViewport'
    ],
    cls     : 'viewer-cnt',
    layout  : 'card',
    items   : [
    {
        xtype: 'persona_personaviewport'
    }, {
        xtype: 'suscripcion_suscripcionviewport'
    }, {
        xtype: 'periodo_periodoviewport'
    }, {
        xtype: 'cabecera_cabeceraviewport'
    }, {
        xtype: 'reemitir_reemitirviewport'
    }, {
        xtype: 'panel',
        html : 'vacio'
    }, {
        xtype: 'panel',
        html : 'vacio'
    }]
});