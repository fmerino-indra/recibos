Ext.define('recibosWeb.model.Header', {
    extend: 'recibosWeb.model.Base',
    requires   : [
        'iDynamicsFront.data.Proxy'
//        'recibosWeb.model.Emision'
    ],

    requires: ['Ext.data.proxy.Rest'],
/*
    requires  : [
        'Ext.data.proxy.Rest',
        'Ext.data.reader.Json'
             ],
*/
    idProperty : 'id',
    fields: [
        {name:'id', type:"int", useNull:false}
        ,{name:'fechaEmision', type:"date", dateFormat: 'time', useNull:false}
        ,{name:'codigoMes'}
        ,{name:'periodo'}
        ,{name:'fechaEnvio', type:"date", dateFormat: 'time',useNull:false}
        ,{name:'importe', type:"number", useNull:false}
        ,{name:'concepto', type:"string", useNull:false}
    ],
//    hasMany: {model: 'Emision', name: 'emisions', associationKey: 'emisions'},
/*
    proxy     : {
        type     : 'rest',
        url      : '/recibosWeb/periodos',
        reader   : {
            type : 'json'
        }
    }
*/

    proxy      : {
        type     : 'idfproxy',
        name     : 'recibosWeb.proxy.Header',
        mockupUrl: 'data/headerListado.json',
        remoteUrl: 'headers'
// Activar esto cuando esté hecho el modelo emision
//        ,
//        rootProperty : "data"
    }

});
