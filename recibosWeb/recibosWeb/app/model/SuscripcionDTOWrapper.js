Ext.define('recibosWeb.model.SuscripcionDTO', {
    extend: 'recibosWeb.model.Base',
    requires   : [
      'iDynamicsFront.data.Proxy'
    ],

    requires: ['Ext.data.proxy.Rest'],
    idProperty : 'id',
    fields: [
        {name:'id', type: 'int', useNull:false}
        ,{name:'fechaInicio', type: 'date', dateFormat: 'time'}
        ,{name:'fechaBaja', type: 'date', dateFormat: 'time'}
        ,{name: 'nombre', type:"string", useNull:false}
        ,{name:'euros', type:"number", useNull:true}
        ,{name:'divisa', type:"string", useNull:true}
        ,{name:'periodo', type:"string", useNull:true}
        ,{name:'activo', type:"boolean", useNull:true}
        ,{name:'concepto', type:"string", useNull:true}
        ,{name:'iban', type:"string", useNull:true}
    ],
    proxy      : {
        type     : 'idfproxy',
        name     : 'recibosWeb.proxy.SuscripcionDTO',
        mockupUrl: 'data/suscripcionesListado.json',
        remoteUrl: 'suscripciones'
//        ,
//        url      : '/periodos'
    }
    
});
