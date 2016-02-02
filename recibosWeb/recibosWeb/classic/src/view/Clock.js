Ext.define('recibosWeb.view.Clock', {
    extend: 'Ext.container.Container',
    alias : 'widget.clock',

    cls      : 'clockContainer',
    layout   : 'fit',
    listeners: {
        afterrender: 'runnerClock'
    },
    reference:'clock',
    items    : [{
        xtype: 'toolbar',
        layout:{
            pack: 'center'
        },
        items: [{
            xtype         : 'displayfield',
            cls           : 'clock',
            fieldLabel    : Ext.Date.format(new Date(), 'D, m/Y'),
            value         : Ext.Date.format(new Date(), 'H:i:s'),
            labelSeparator: '',
            labelCls      : 'dateClock'
        }]
    }]

});