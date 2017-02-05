Ext.define('recibosWeb.model.ResumenDTO', {
    extend: 'recibosWeb.model.Base',
    requires   : [
        'iDynamicsFront.data.Proxy'
    ],

    requires: ['Ext.data.proxy.Rest'],

    idProperty : 'id',
    fields: [
        {name:'id', type:"int", useNull:false}
        ,{name:'codigoMes'}
        ,{name:'mes'}
        ,{name:'fechaMes', type:"date", dateFormat: 'time', useNull:false}
        ,{name:'importeMes', type:"number", useNull:false}
        ,{name:'devueltoMes', type:"number", useNull:false}
        ,{name:'fechaTrimestre', type:"date", dateFormat: 'time', useNull:false}
        ,{name:'importeTrimestre', type:"number", useNull:false}
        ,{name:'devueltoTrimestre', type:"number", useNull:false}
        ,{name:'fechaSemestre', type:"date", dateFormat: 'time', useNull:false}
        ,{name:'importeSemestre', type:"number", useNull:false}
        ,{name:'devueltoSemestre', type:"number", useNull:false}
        ,{name:'fechaAnyo', type:"date", dateFormat: 'time', useNull:false}
        ,{name:'importeAnyo', type:"number", useNull:true}
        ,{name:'devueltoAnyo', type:"number", useNull:true}
    ],
    proxy      : {
        type     : 'idfproxy',
        name     : 'recibosWeb.proxy.ResumenDTO',
        mockupUrl: 'data/cabecerasListado.json',
        remoteUrl: 'resumen'
    }

});
