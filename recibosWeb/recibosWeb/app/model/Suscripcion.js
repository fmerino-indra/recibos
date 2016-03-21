Ext.define('recibosWeb.model.Suscripcion', {
    extend: 'recibosWeb.model.Base',
    idProperty : 'idSuscripcion',
    fields: [
        {name:'secuenciaAdeudo'}
        ,{name:'persona', reference:"Persona"}
        ,{name:'fechaInicio', type: 'date', dateFormat: 'time'}
        ,{name:'fechaBaja', type: 'date', dateFormat: 'time'}
        ,{name:'pago', type:"string", useNull:true}
        ,{name:'pesetas', type:"number", useNull:true}
        ,{name:'divisa', type:"string", useNull:true}
        ,{name:'periodo', type:"string", useNull:true}
        ,{name:'devolucion', type:"string", useNull:true}
        ,{name:'activo', type:"boolean", useNull:true}
        ,{name:'ultimaEmision', type: 'date', dateFormat: 'time'}
        ,{name:'idTitular', type:"int", useNull:true}
        ,{name:'euros', type:"number", useNull:true}
        ,{name:'idAntigua', type:"int", useNull:true}
        ,{name:'concepto', type:"string", useNull:true}
        ,{name:'idSuscripcion', type:"int", useNull:true}
    ]
});
