Ext.define('recibosWeb.view.MenuList', {
    extend    : 'Ext.container.Container',
    alias     : 'widget.menulist',
    requires  : [
        'Ext.view.View'
    ],
    cls       : 'menulist',
    layout    : 'fit',
    controller: 'menulist',
    viewModel : {
        type: 'menulist'
    },
    items     : [{
        xtype: 'toolbar',
        cls  : 'toolbarmenulist',
        items: [{
            xtype: 'tbspacer'
        }, {
            xtype         : 'displayfield',
            fieldLabel    : 'demo',
            labelSeparator: '',
            cls           : 'title'
        }, {
            xtype        : 'segmentedbutton',
            cls          : 'differentView',
            allowMultiple: false,
            items        : [{
                xtype    : 'button',
                text     : 'Monitorizacion',
                index    : 0,
                reference: 'monitorizacion',
                pressed  : true,
                handler  : 'onChangeCard'
            }, {
                xtype    : 'button',
                text     : 'Informes',
                index    : 1,
                reference: 'informes',
                handler  : 'onChangeCard'
            }, {
                xtype    : 'button',
                text     : 'Métricas',
                index    : 2,
                reference: 'metricas',
                handler  : 'onChangeCard'
            }, {
                xtype    : 'button',
                text     : 'Paneles',
                index    : 3,
                reference: 'paneles',
                handler  : 'onChangeCard'
            }, {
                xtype    : 'button',
                text     : 'Administración',
                index    : 4,
                reference: 'administracion',
                handler  : 'onChangeCard'
            }]
        }, {
            xtype: 'tbfill'
        }, {
            xtype    : 'button',
            ui       : 'alarm',
            cls      : 'reset',
            scale    : 'medium',
            reference: 'reset',
            iconCls  : 'fa fa-refresh',
            handler  : 'resetSystem'
        }, {
            xtype    : 'button',
            ui       : 'alarm',
            cls      : 'sileceAllAlarms',
            scale    : 'medium',
            reference: 'sileceAllAlarms',
            iconCls  : 'fa fa-bell-slash-o',
            handler  : 'silenceAllAlarm'
        }, {
            xtype       : 'button',
            ui          : 'alarm',
            cls         : 'allAlarms',
            disabled    : true,
            enableToggle: true,
            width       : 68,
            scale       : 'medium',
            reference   : 'allAlarm',
            handler     : 'alarmFirter',
            iconCls     : 'fa fa-bell-o'
        }, {
            xtype        : 'segmentedbutton',
            cls          : 'alarmsSummary',
            reference    : 'alarmsSummary',
            allowMultiple: true,
            items        : [{
                xtype    : 'button',
                ui       : 'alarm',
                cls      : 'error',
                scale    : 'medium',
                disabled : true,
                reference: 'alarmError',
                handler  : 'alarmFirter',
                iconCls  : 'fa fa-bell-o'
            }, {
                xtype    : 'button',
                ui       : 'alarm',
                cls      : 'warning',
                scale    : 'medium',
                disabled : true,
                reference: 'alarmWarning',
                handler  : 'alarmFirter',
                iconCls  : 'fa fa-bell-o'
            }, {
                xtype    : 'button',
                ui       : 'alarm',
                cls      : 'degraded',
                scale    : 'medium',
                disabled : true,
                reference: 'alarmDegrade',
                handler  : 'alarmFirter',
                iconCls  : 'fa fa-bell-o'
            }]
        }, {
            xtype    : 'button',
            cls      : 'user',
            reference: 'userMenuItem',
            text     : 'rjsmitg',
            iconCls  : "fa fa-user",
            handler  : 'onClickUser',
            iconAlign: 'rigth'
        }, {
            xtype    : 'button',
            reference: 'loggedUserMenuItem',
            cls      : 'btnradius logout',
            //handler  : 'onClickUser',
            autoEl : {
				tag : 'a',
				href	 :'http://localhost:8080/logout',
				html : 'Logout'
			},
            iconCls  : "fa fa-power-off"
        }]
    }]
});