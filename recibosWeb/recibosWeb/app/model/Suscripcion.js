Ext.define('recibosWeb.model.Suscripcion', {
    extend: 'recibosWeb.model.Base',
    requires   : [
      'iDynamicsFront.data.Proxy'
    ],

    requires: ['Ext.data.proxy.Rest'],
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
        ,{name:'idSuscripcion', type:"int", useNull:false}
        ,{name: 'nombrePersona', 
        	calculate: function (data) {
        		if (data.persona)
        			return data.persona.nombre;
        		else
        			return '';
        	}
        }
    ],
    proxy      : {
        type     : 'idfproxy',
        name     : 'recibosWeb.proxy.Suscripcion',
        mockupUrl: 'data/suscripcionesListado.json',
        remoteUrl: 'suscripciones'
//        ,
//        url      : '/periodos'
    }
    
});
