Ext.define('recibosWeb.model.CabeceraDTO', {
    extend: 'recibosWeb.model.Base',
    requires   : [
        'iDynamicsFront.data.Proxy'
    ],

    requires: ['Ext.data.proxy.Rest'],

    idProperty : 'id',
    fields: [
        {name:'id', type:"int", useNull:false}
        ,{name:'fechaEmision', type:"date", dateFormat: 'time', useNull:false}
        ,{name:'codigoMes'}
        ,{name:'periodo'}
        ,{name:'fechaEnvio', type:"date", dateFormat: 'time',useNull:false}
        ,{name:'importe', type:"number", useNull:false}
        ,{name:'importeDevuelto', type:"number", useNull:false}
        ,{name:'concepto', type:"string", useNull:false}
    ],
    proxy      : {
        type     : 'idfproxy',
        name     : 'recibosWeb.proxy.CabeceraDTO',
        mockupUrl: 'data/cabecerasListado.json',
        remoteUrl: 'cabeceras'
// Activar esto cuando esté hecho el modelo emision
//        ,
//        rootProperty : "data"
    }

});
