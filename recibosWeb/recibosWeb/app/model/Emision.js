Ext.define('recibosWeb.model.Emision', {
    extend: 'recibosWeb.model.Base',
    requires   : [
        'iDynamicsFront.data.Proxy',
        'Ext.data.proxy.Rest'
    ],

    idProperty : 'id',
    fields: [
        {name:'id', type:"int", useNull:true}
        ,{name:'idCabecera'}
        ,{name:'idSuscripcion'}
        ,{name:'devuelto', type:"boolean", useNull:true}
        ,{name:'importe', type:"number", useNull:true}
        ,{name:'divisa', type:"string", useNull:true}
        ,{name:'reenviado', type:"boolean", useNull:true}
        ,{name:'cabeceraId', reference:'Cabecera'}
        ,{name:'suscripcionId', reference: 'PSD'}

    ]
//    belongsTo: 'recibosWeb.model.Cabecera',
/*
    proxy : {
        type : 'idfproxy',
        name : 'recibosWeb.proxy.Emision',
        mockupUrl : 'data/emisionsListado.json',
        remoteUrl : 'emisions'
    }
*/
});
