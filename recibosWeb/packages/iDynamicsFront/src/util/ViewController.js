/**
 * Created by mavegaa on 20/04/2015.
 */
Ext.define('iDynamicsFront.util.ViewController', {
    extend: 'Ext.app.ViewController',

    errorCodes: {
        JSON_ERR_INVALID_SESSION: '1',
        JSON_ERR_MISSING_PARAM  : '2',
        JSON_ERR_BAD_PARAM      : '3',
        JSON_ERR_SRVC_NOT_FOUND : '4',
        JSON_ERR_LOGIN_ERROR    : '5',
        JSON_ERR_DB_ERROR       : '6',
        JSON_ERR_GENERIC        : '7'
    },

    /**
     * Variable with the names of the controllers
     * Needless indicate the prefix  "APP.controller."
     */
    controllerNames: {
		//
        //ActionController         : 'web.action.ActionController',
        //RolController            : 'web.rol.RolController',
        //RolActionController      : 'web.rolaction.RolActionController',
        //UsuarioController        : 'web.usuario.UsuarioController',
        //UsuarioIntentosController: 'web.usuariointentos.UsuarioIntentosController'
        /*-CONT- detail:##,controllerNames:Action,controllerNames:Rol,controllerNames:RolAction,controllerNames:Usuario,controllerNames:UsuarioIntentos,*/
    },
    /**
     * Muestra logs en la consola
     *
     * @param {String}
     *            msg Mensaje de log
     */
        //TODO
    log            : function (msg) {
        //var me = this;
        //if (Editran.lib.Logger) {
        //    Editran.lib.Logger.info('[' + me.self.getName() + ' :: '
        //        + Ext.Date.format(new Date(), 'H:i:s') + '] ' + msg);
        //}
    },

    //TODO
    warn: function (msg) {
        //var me = this;
        //if (Editran.lib.Logger) {
        //    Editran.lib.Logger.warn('[' + me.self.getName() + ' :: '
        //        + Ext.Date.format(new Date(), 'H:i:s') + '] ' + msg);
        //}
    },

    /**
     * Muestra ventanas de información
     *
     * @param {String}
     *            title Título de la ventana
     * @param {Array}
     *            msgs Array de Strings con los mensajes a mostrar
     * @param {Number}
     *            buttons botones a mostrar {@link Ext.MessageBox}
     * @param {Number}
     *            icon icono a mostrar {@link Ext.MessageBox}
     */
    error: function (errorObj, fn) {
        var me = this, prop, errorTitle = null;
        var errorCode = errorObj.codigo;
        var msgs = errorObj.messages;
        if (Ext.isArray(msgs)) {
            msgs.join('<br>');
        }

        for (prop in me.errorCodes) {
            if (errorCode === me.errorCodes[prop]) {
                errorTitle = $AC.commons.errorCodes[prop];
                break;
            }
        }

        Ext.MessageBox.show({
            title  : errorTitle !== null
                ? errorTitle
                : t('commons.msgs.atencion'),
            msg    : msgs || t('commons.msgs.unexpectedError'),
            buttons: Ext.MessageBox.OK,
            icon   : Ext.MessageBox.ERROR,
            fn     : function () {
                if (errorCode === me.errorCodes.JSON_ERR_INVALID_SESSION) {
                    me.log('sin sesión');
                    window.location.reload();
                } else {
                    if (typeof fn === 'function') {
                        fn();
                    }
                }
            }
        });
    },

    /**
     * Muestra ventanas de warning
     *
     * @param {OBject}
     *            warnObj objeto que tiene:
     *            title titulo de la ventana
     *            message mensaje a mostrar
     */
    warning: function (warnObj) {
        var me = this, warnTitle = warnObj.title;
        var msgs = warnObj.messages;

        Ext.MessageBox.show({
            title  : warnTitle !== null
                ? warnTitle
                : t('commons.msgs.warning'),
            msg    : msgs || t('commons.msgs.unexpectedWarning'),
            buttons: Ext.MessageBox.OK,
            icon   : Ext.MessageBox.WARNING
        });
    },

    onAction: function (btn) {
        var me = this, action = btn.action;
        me.executeAction(action, btn);
    },

    executeAction: function (action) {
        var me = this;
        if (action) {
            if (typeof me[action] === 'function') {
                var args = Ext.toArray(arguments);
                me[action].apply(me, args.slice(1, args.length));
            } else {
                me.warn('Action "' + action + '" is not defined.');
            }
        } else {
            me.warn('No action defined to this event: ' + action);
        }
    },

    info: function (info) {
        var msgCt, m;

        function createBox(msg) {
            return '<div class="msg"><h3>' + msg + '</h3></div>';
        }

        if (!Ext.get('msg-div')) {
            msgCt = Ext.DomHelper.insertFirst(document.body, {
                id: 'msg-div'
            }, true);

        } else {
            msgCt = Ext.get('msg-div');
        }
        m = Ext.DomHelper.append(msgCt, createBox(info), true);
        m.hide();

        m.slideIn('t').ghost("t", {
            delay : 1000,
            remove: true
        });
    }
});