Ext.define('recibosWeb.view.persona.detail.PersonaDetailVM', {
    extend  : 'Ext.app.ViewModel',
    alias   : 'viewmodel.persona-detail',
    requires: [
        'recibosWeb.model.Persona'
    ],
    stores: {
        personaDTOs: {
            model       : 'recibosWeb.model.Persona',
            filterOnLoad: false,
            remoteSort  : true,
            pageSize    : 22,
            remoteFilter: true
        }
    }
});