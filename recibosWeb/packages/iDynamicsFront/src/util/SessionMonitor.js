/**
 * Session Monitor task, alerts the user that their session will expire in 60 seconds and provides
 * the options to continue working or logout.  If the count-down timer expires,  the user is automatically
 * logged out.
 */
Ext.define('iDynamicsFront.util.SessionMonitor', {
    singleton         : true,
    alternateClassName: 'sessionMonitor',
    config            : {
        renoveSessionUrl: null,
        logout          : null,
        timeout         : 1000 * 60 * 10, // default 10 minutes of user´s inactivity.
        maxInactive     : null// default 9 minutes of inactivity with server .
    },
    lastActive        : null,
    lastActiveSerer   : null,
    remaining         : 0,
    ui                : Ext.getBody(),
    verbose           : false,
    //beforeSessionFinish: null, // tiempo que debe pasar sin realizar peticiones al servidor para comprobar la actividad del usuario


    /**
     * Dialog to display expiration message and count-down timer.
     */

    window: Ext.create('Ext.window.Window', {
        bodyPadding: 5,
        closable   : false,
        closeAction: 'hide',
        modal      : true,
        resizable  : false,
        title      : 'Pronto expirará la sesión',
        width      : 325,
        items      : [{
            xtype : 'container',
            layout: 'fit',
            html  : ''
        }, {
            xtype    : 'label',
            reference: 'timer',
            text     : ''
        }],
        buttons    : [{
            text   : 'Continuar trabajando',
            handler: function () {
                Ext.TaskManager.stop(sessionMonitor.countDownTask);
                sessionMonitor.window.hide();
                sessionMonitor.start();
                // 'poke' the server-side to update your session.
                Ext.Ajax.request({
                    url: sessionMonitor.getRenoveSessionUrl()
                });
                sessionMonitor.resetTimeout();
            }
        }, {
            text   : 'Logout',
            action : 'logout',
            handler: function () {
                Ext.TaskManager.stop(sessionMonitor.countDownTask);
                sessionMonitor.window.hide();
                // find and invoke your app's "Logout" button.
                // var btn = Ext.ComponentQuery.query('button#logout')[0];
                // btn.fireEvent('click', btn);
                sessionMonitor.endAction();
            }
        }]
    }),


    /**
     * Sets up a timer task to monitor for mousemove/keydown events and
     * a count-down timer task to be used by the 60 second count-down dialog.
     */
    constructor    : function (config) {
        var me = this;
        // esta función lanza la comprobación de si hay actividad o no segun el tiempo estipulado en el setUpTimeout
        this.sessionTask = {
            run     : me.monitorUI,
            interval: null,//me.interval,
            scope   : me
        };
        // esta funcion se ejecuta cada segundo y es la que hace que caambie los seg de la ventana
        this.countDownTask = {
            run     : me.countDown,
            interval: 1000,  // 1 seg
            scope   : me
        };
    }
    ,
    setUpTimeout   : function (timeout) {
        var interval = Math.floor(timeout * 0.85),
            maxTimeWithoutUserActivity = Math.floor(timeout * 0.15),
            timeWindow = Math.floor((timeout / 1000) * 0.10);// 90% del tiempo en seg que muestra la ventana
        //this.beforeSessionFinish = interval;
        this.setTimeout(timeout); //inserta el tiempo al timeout
        this.lastActiveSerer = new Date();//actualiza el tiempo de la ultima vez que se hizo la peticion
        this.sessionTask.interval = interval; //cuándo se realiza la comprobación
        this.setMaxInactive(/*maxTimeWithoutUserActivity*/interval);//15% timeout maximo tiempo sin interactuar con la app
        this.remaining = Math.min(60, timeWindow);// segundos que mostrará la ventana.
        if (this.verbose) {
            console.log('Finalizará la sesión en: ' + (timeout / 1000) + ' seg');
            console.log('La comprobación se realizará en: ' + (interval / 1000) + ' seg');
            console.log('El tiempo máximo de inactividad con la app es: ' + (maxTimeWithoutUserActivity / 1000) + ' seg');
            console.log('El tiempo que aparecerá en la cuenta atrás de la ventana es: ' + (Math.min(60, timeWindow)) + ' seg');
        }
    },
    /**
     * Simple method to register with the mousemove and keydown events.
     */
    captureActivity: function (eventObj, el, eventOptions) {
        this.lastActive = new Date();
    }
    ,

    /**
     *  Monitors the UI to determine if you've exceeded the inactivity threshold.
     */
    monitorUI: function () {
        // se realiza esta comprobación ya que esta función se ejecuta antes del setUpTimeout(), una vez ejecutada, no vuelve a entrar
        if (this.sessionTask.interval === null) {
            return null;
        }

        this.window.down('container').html = 'Su sesión finalizará tras ' + (this.getTimeout() / (1000 * 60)) + ' minutos de inactividad. Si su sesión expira, no se guardarán los datos y su sesión finalizará. </br></br>Si desea continuar, pulse el botón "Continuar trabajando".</br></br>';

        //TODO revisar este método
        var now = new Date();
        var inactive = (now - this.lastActive),
            inactiveServer = (now - this.lastActiveSerer);
        if (this.verbose) {
            console.log('Precaución pronto finalizará  su sesion');
            console.log('Quedan ' + (this.getMaxInactive() - inactive) / 1000 + 'seg. para finalizar actividad de usuario');
            console.log('Quedan ' + (this.getTimeout() - inactiveServer) / 1000 + 'seg. para finalizar actividad con servidor');
        }


        /*//Restan 30 segundos para finalizar la sesion automaticamente por no actividad en app
         if (inactive >= this.getMaxInactive() - (0.5 * 1000 * 60)) {
         console.log('precaución en 30 seg finalizará automaticamente su sesion a no ser que mueva el raton');
         }*/
        /*if (inactive >= this.getMaxInactive()) {
         console.log('usuario no interctua: logout');
         this.stop();
         this.window.show();
         this.remaining = 60; // seconds remaining.
         Ext.TaskManager.start(this.countDownTask);
         }*/
        //Si para la finalización de la sesion falta el tiempo marcado en beforeSessionFinish,
        //se comprueba la actividad del usuario en la pantalla, si en los últimos x min no ha tenido actividad
        // muestro ventana, sino renuevo automáticamente la sesión
        //if (inactiveServer >= this.beforeSessionFinish) {


        //Si la hay menos tiempo de inactividad del usuario que lo que hemos tomado como maximo
        //se realiza ping automatico
        if (inactive < this.getMaxInactive()) {
            this.verbose ? console.log('Hay actividad de usuario. Renovación de sesion') : null;
            Ext.Ajax.request({
                url: sessionMonitor.getRenoveSessionUrl()
            });
            this.resetTimeout();
        }
        //si no, se muestra una ventana con un tiempo, que si vence, se finalizará la sesion
        // los botones de la ventana renuevan o finalizan la sesion
        else {
            this.verbose ? console.log('Muestro ventana, porque no se interactua con la app') : null;
            this.stop();
            this.window.show();
            Ext.TaskManager.start(this.countDownTask);
        }
        //}
    }
    ,

    /**
     * Starts the session timer task and registers mouse/keyboard activity event monitors.
     */
    start       : function () {

        this.lastActive = new Date();
        this.ui = Ext.getBody();

        this.ui.on('mousemove', this.captureActivity, this);
        this.ui.on('keydown', this.captureActivity, this);

        Ext.TaskManager.start(this.sessionTask);
    }
    ,
    resetTimeout: function () {
        if (this.verbose) {
            console.log('Contador timeout reiniciado');
        }
        this.lastActiveSerer = new Date();
    }
    ,

    /**
     * Stops the session timer task and unregisters the mouse/keyboard activity event monitors.
     */
    stop: function () {
        Ext.TaskManager.stop(this.sessionTask);
        this.ui.un('mousemove', this.captureActivity, this);
        //  always wipe-up after yourself...
        this.ui.un('keydown', this.captureActivity, this);
    }
    ,

    /**
     * Countdown function updates the message label in the user dialog which displays
     * the seconds remaining prior to session expiration.  If the counter expires, you're logged out.
     */
    countDown: function () {
        this.window.down('label').update('Tu sesión expirará en  ' + this.remaining + ' segundo' + ((this.remaining == 1) ? '.' : 's.'));
        --this.remaining;

        if (this.remaining < 0) {
            // var btn = Ext.ComponentQuery.query('button#logout')[0];
            // btn.fireEvent('click', btn);
            sessionMonitor.endAction();
        }
    }
    ,

    endAction: function () {
        Ext.TaskManager.stop(sessionMonitor.countDownTask);
        sessionMonitor.window.hide();
        window.location.href = sessionMonitor.getLogout();
    }
})
;
