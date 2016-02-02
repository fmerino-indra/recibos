Ext.define('recibosWeb.view.Viewer', {
    extend  : 'Ext.container.Container',
    alias   : 'widget.viewer',
    requires: [
//        'APP.view.monitorizacion.MonitorizacionViewport'
        'recibosWeb.view.periodo.PeriodoViewport'
    ],
    cls     : 'viewer-cnt',
    layout  : 'card',
    items   : [{
        xtype: 'periodo_periodoviewport'

    }, {
        xtype: 'panel',
        html : 'vacio'
    }, {
        xtype: 'panel',
        html : 'vacio'
    }, {
        xtype: 'panel',
        html : 'vacio'
    }, {
        xtype: 'panel',
        html : 'vacio'
    }]
});