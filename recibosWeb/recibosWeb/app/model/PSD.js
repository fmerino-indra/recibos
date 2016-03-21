Ext.define('recibosWeb.model.PSD', {
    extend: 'recibosWeb.model.Base',
    idProperty : 'id',
    fields: [
        {name:'idDomiciliacion'}
        ,{name:'idSuscripcion'}
        ,{name:'fechaInicio', type: 'date', dateFormat: 'time'}
        ,{name:'fechaBaja', type: 'date', dateFormat: 'time'}
        ,{name:'idPantiguo', type:"int", useNull:true}
        ,{name:'idSantiguo', type:"int", useNull:true}
        ,{name:'idDantiguo', type:"int", useNull:true}
        ,{name:'fechaFirma', type: 'date', dateFormat: 'time'}
        ,{name:'id', type:"int", useNull:true}
    ]
});
