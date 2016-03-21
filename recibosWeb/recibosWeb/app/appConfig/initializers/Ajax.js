Ext.define('recibosWeb.appConfig.initializers.Ajax', {
    run: function () {
/*
        var tokenMetaElement = Ext.query('meta[name=csrf-token]')[0],
            url = $AC.getWebPath() + ($AC.isLocalDataMode() ? '/data/documentsListado.json' : '/sessionMonitor'),
            urlLogout = $AC.getWebPath() + '/logout';
        if (tokenMetaElement) {
            Ext.Ajax.setDefaultHeaders({
                'X-CSRF-Token': tokenMetaElement.getAttribute('content')
            });
        }
*/
        //captura excepcones de error
        Ext.Ajax.on('requestexception', function (conn, response, options) {
                /* if (response.status === 901) {
                 var location = response.getResponseHeader('location');
                 //TODO ver reset total: stores, variables
                 //recargo aplicación sin cargar de la caché
                 //location.reload(true);
                 window.location.href = location;
                 }
                 else if (response.status === 400) {
                 var message = response.statusText;
                 Ext.MessageBox.show({
                 title  : 'Error',
                 msg    : message,
                 buttons: Ext.MessageBox.OK,
                 icon   : Ext.MessageBox.ERROR
                 })
                 }
                 else if (response.status === 404) {
                 var message = 'Recurso no encontrado.';
                 Ext.MessageBox.show({
                 title  : 'Error de localización',
                 msg    : message,
                 buttons: Ext.MessageBox.OK,
                 icon   : Ext.MessageBox.ERROR
                 });
                 }
                 else if (response.status >= 400) {
                 var message = 'Se ha producido un error inesperado. \nSi el problema persiste, por favor pongase en contacto con el administrador.';
                 Ext.MessageBox.show({
                 title  : 'Error inesperado',
                 msg    : message,
                 buttons: Ext.MessageBox.OK,
                 icon   : Ext.MessageBox.ERROR
                 });
                 }*/
                var obj = {
                    901  : function (response) {
                        var location = response.getResponseHeader('location');
                        //TODO ver reset total: stores, variables
                        //recargo aplicación sin cargar de la caché
                        //location.reload(true);
                        window.location.href = location;
                    },
                    400 :function (response) {
                        var msgs, responseDecoded, msg = '';
                        responseDecoded = Ext.decode(response.responseText);
                        if (responseDecoded.info) {
                            msgs = [responseDecoded.info.messages];
                        } else {
                            msgs = new Array();
                            msgs.push(responseDecoded.error + ' (' + responseDecoded.status +')');
                            msgs.push(responseDecoded.message);
                        }
                        Ext.Array.each(msgs, function (value) {
                            msg = msg + value + '\n';
                        });
                        this.msg('Error', msg);
                    },
/* FMM Old method
                    400  : function (response) {
                        var msgs = [Ext.decode(response.responseText).info.messages],
                            msg = '';
                        Ext.Array.each(msgs, function (value) {
                            msg = msg + value + '\n';
                        });
                        this.msg('Error', msg);
                    },
*/
                    404  : function (response) {
                        if (response.responseText.indexOf("<html>") === 0) {
                            this.msg('Error de localización', 'Recurso no encontrado.');
                        } else {
                            this.msg('Error de localización', response.responseText);
                        }
                    },
                    other: function (response) {
                        this.msg('Error inesperado', 'Se ha producido un error inesperado. \nSi el problema persiste, por favor pongase en contacto con el administrador.');
                    },
                    msg  : function (title, msg) {
                        Ext.MessageBox.show({
                            title  : title,
                            msg    : msg,
                            buttons: Ext.MessageBox.OK,
                            icon   : Ext.MessageBox.ERROR
                        });
                    }
                }, responseStatus = response.status;
                if (responseStatus >= 400 && (responseStatus !== 400 && responseStatus !== 404 && responseStatus !== 901 )) {
                    responseStatus = 'other';
                }
                obj[responseStatus](response);
            }
        );

        //introduce url para el ping de renovar sesion
//        sessionMonitor.setRenoveSessionUrl(url);
        //introduce url para finalizar la sesion
//        sessionMonitor.setLogout(urlLogout);
        //se imprimen los mensajes por la consola
//        sessionMonitor.verbose = true;

        Ext.Ajax.on("requestcomplete", function (conn, response, options, eOpts) {

            if (response && response.getResponseHeader != null) {
                var timeout = parseInt(response.getResponseHeader("timeout"));
                //para las pruebas en local
                //timeout = (10 * 60 * 1000);
                if (timeout) {
                    //inicializamos timer de Timeout
//                    sessionMonitor.setUpTimeout(timeout);
                }
            }
        });

    }
});
