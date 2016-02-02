/*
 * Este Componente es un SearchForm con 3 partes diferenciadas
 *  -Contadores
 *  -Configuración
 *  -Avanzada
 * Se crea una toolbar con los botones de search y reset
 *
 * Se exponen los eventos de search y reset para que sean recogidos por el controller,
 * el search es lanzado tanto con el boton de la toolbar como con los contadores que realizarian una busqueda directa
 * Hay que tener en cuenta que estos eventos mandan como parametro el propio searchForm y el boton que ha lanzado el evento
 *
 * */
Ext.define("iDynamicsFront.util.SearchFormAutoExt5", {
	extend : "Ext.form.Panel",
	alias : "widget.uxsearchformautoext5",
	//controller: 'idf-searchform',
	referenceHolder: true,
	reference: 'searchformext5',
	requires : ['iDynamicsFront.util.Counter'],
	cls : 'search-form',
	title : 'FILTRO',
	border : false,
	collapsible : true,
	store: null,
	loadOnCounterClick : true,
	autoScroll : true,
	layout : {
		type : 'accordion',
		titleCollapse : true,
		multi : true,
		fill : false
	},
	listeners: {
        search: 'onAfterSearch'
    },
	defaults : {
		anchor : '100%'
	},
	fieldDefaults : {
		labelAlign : 'top',
		labelPad : 1,
		labelSeparator : ''
	},
	
	busquedaAvanzada : false,
	model : null,
	baseEntity:null,
	hasCounters: true,
		
	counters : [{
		xtype : 'panel',
		layout : 'anchor',
		name : 'counters',
		cls : 'counters',
		reference: 'counters',
		items : [] 
	}],		
	criteria : [{
		xtype : 'panel',
		layout : 'anchor',
		name : 'criteria',
		cls : 'conf avanzada',
		reference: 'criteria',
		items : [] // Aqui hay que ir montando los items del modelo del store
	}],
			
	configuration : [{
		xtype : 'panel',
		layout : 'anchor',
		cls : 'conf',
		reference: 'configuration',
		items : []
	}],

	setStore: function(stre) {
		var me = this;
		me.store = stre;
	},
	getStore: function() {
		var me = this;
		return me.store;
	},
	initComponent : function() {
		var me = this, searchButton, resetButton;

		if (!me.getStore()) {
			Ext.Error.raise("Es obligatorio disponer del store para construir los campos de busqueda avanzada un store");
		}
		
		// establezco una variable general relativa al objeto de la busqueda, para internacionalizacion....
		me.model = me.store.model;
		var arrMcn = me.model.getName().split(".");
		me.baseEntity=arrMcn[arrMcn.length];
		
		me._createCriteria();

		// div para la sombra de la derecha
		me.html = '<div class="searchForm-right-shadow"></div>';
		// me.counters = me.counters ? me.counters : [];
		// if (me.counters.length == 0) { 
		// 	me.hasCounters = false; 
		// } else {
		// 	me.counters[0].reference = 'counters';
		// 	me.counters[0].title = t('commons.searchForm.contadores');
		// }
		searchButton = me.searchButton = Ext.create("Ext.button.Button", {
			cls : 'ux-btn-highlighted',
			text : t('commons.button.buscar') ,
			scale : 'medium',
			iconCls : 'icon-search',
			handler : 'onSearchButtonClick'
		});

		resetButton = me.resetButton = Ext.create("Ext.button.Button", {
			cls : 'ux-btn',
			text : t('commons.button.limpiar') ,
			scale : 'medium',
			iconCls : 'icon-delete',
			handler : 'onResetButtonClick'
		});

		me.dockedItems = [{
			dock : 'bottom',
			xtype : 'toolbar',
			items : [resetButton, '->', searchButton]
		}];

		me._makeconfiguration();

		if (me.hasCounters) {
			me._createCounter();
		}
	
		//me.items = me.counters.concat(me.criteria, me.configuration);
		me.items.concat(me.counters, me.criteria, me.configuration);

		me.callParent();

	},

	// beforeRender : function() {
	// 	var me = this;
	// 	me.callParent();
	// 	me._loadCountersStore();
	// },
	
	afterRender : function() {
		var me = this;
		me.callParent();
		me._loadComboAgrupacion();
		me._loadComboOrdenacion();
	},
	




	_unselectCounters : function() {
		var me = this;
		
		var vCont = me.lookupReference( 'counters' );
		
		vCont.view.setSelection(null);
		/*
		var contadoresSelected = Ext.dom.Query.select('.x-item-selected');
		if (contadoresSelected.length > 0) {
			contadoresSelected[0].setAttribute('class', 'ux-view-item');
		}
		*/
	},
	
	_createCounter : function() {
		var me = this, counters = me.counters, generatedCounters = [], ct;
		
		Ext.Array.each(counters, function(counter) {
			// if (!counter.elstore) {
			// 	Ext.Error.raise("Es obligatorio que los counters tengan un store");
			// }
			//TODO eliminar xtype del objeto counter si lo hubiese
			ct = Ext.create("iDynamicsFront.util.Counter", counter);
			if (me.loadOnCounterClick) {
				ct.on("counterclick", me.onCounterClicked, me);
			}
			generatedCounters[generatedCounters.length] = ct;
		});
//		me.criteria[0].items[me.criteria[0].items.length] = it;
		me.counters[0].items = [];
		me.counters[0].items.push(generatedCounters);
		//me.counters = generatedCounters;
	},

	onCounterClicked : function(view, record) {
		var me = this;
		me.busquedaAvanzada=false;
		me._search(record.get('cdcontador'));
		//Lanzo evento por si en el controlador se quiere hacer algo
		//me.fireEvent("search", me, record);
	},

	getCounters : function() {
		return this.counters;
	},

	setCounterClicked : function(activar) {
		var me = this;

		if (counters.length > 0) {
			Ext.Array.each(counters, function(counter) {
				if (activar) {
					counter.resumeEvent('counterclick');
				} else {
					counter.suspendEvent('counterclick');
				}
			});
		}
	},

	// // metodo que carga el store de contadores
	// _loadCountersStore : function() {
	// 	var me = this;
	// 	if (me.counters[0])
	// 		me.counters[0].getStore().load();
	// },

	_createCriteria : function() {
		var me = this;
		
		me.criteria[0].title = t('commons.searchForm.busquedaAvanzada');
		me.criteria[0].items = [];

		//los campos para el filtro
		var fieldNames = me.searchFields.filter(function(f) {
			if (f.filtrable) {
				return true;
			}
			return false;
		});
		
		Ext.Array.forEach(fieldNames, function(f) {
			Ext.Array.forEach(me.model.getFields(), function(modelfield) {
				if (f.name===modelfield.name) {
					//console.log(modelfield.name + ' de ' + modelfield.type);
					var it = me._createItem(f, modelfield);
					me.criteria[0].items[me.criteria[0].items.length] = it;
				} 
			});
			//console.info(' Ya tengo ' + me.criteria[0].items.length);
			
		});		
			
	},
	
	_makeconfiguration: function() {
		var me = this;
		me.configuration[0].title = t('commons.searchForm.configuracion'),
		me.configuration[0].items = [];
		
		// Agrupadores
		// Create the combo box, attached to the states data store
		var cbGroupBy = Ext.create('Ext.form.ComboBox', {
			forceSelection : true,
			fieldLabel : t('commons.searchForm.agruparPor'),
			cls : 'combo',
			anchor : '95%',
			reference: 'groupby',
			labelAlign : 'top',
			queryMode : 'local',
			name : 'campoAgrupacion',
			valueField : 'fieldName',
			displayField : 'fieldDesc',
			store : Ext.create("Ext.data.Store", {
				fields : [{	name : 'fieldName'	}, {name : 'fieldDesc'	}]
			})			
		});	
		var cbSortBy = Ext.create('Ext.form.ComboBox', {
			forceSelection : true,
			anchor : '75%',
			fieldLabel : t('commons.searchForm.ordenarPor'),
			cls : 'combo',
			labelAlign : 'top',
			reference: 'orderby',
			queryMode : 'local',
			name : 'campoOrdenacion',
			valueField : 'fieldName',
			displayField : 'fieldDesc',
			store : Ext.create("Ext.data.Store", {
				fields : [{
					name : 'fieldName'
				}, {
					name : 'fieldDesc'
				}]
			})
		});	

		var fcSortDirection = Ext.create('Ext.form.FieldContainer', {
			cls : 'radio',
			anchor : '100%',
			defaultType : 'radiofield',
			reference: 'orderDirection',			
			layout : 'hbox',
			items : [{
				name : 'sortDirection',
				reference: 'sortDirectionASC',
				inputValue : 'ASC',
				boxLabel  : 'ASC',
				cls : 'ascendente',
				checked : true
			}, {
				name : 'sortDirection',
				reference: 'sortDirectionDESC',
				inputValue : 'DESC',
				boxLabel  : 'DESC',
				cls : 'descendente'
			}]			
		});

		
		me.configuration[0].items[0] = cbGroupBy;
		me.configuration[0].items[1] = cbSortBy;
		me.configuration[0].items[2] = fcSortDirection;

	},






	/*
	 items   : [{
	 xtype           : 'combobox',
	 forceSelection	: true,
	 fieldLabel      : t('planes.searchForm.empresa'),
	 labelAlign      : 'top',
	 anchor          : '95%',
	 name			: 'empresa',
	 cls             : 'combo',
	 queryMode       : 'local',
	 valueField		: 'cdempcsc',
	 displayField	: 'dsempcsc',
	 store			: Ext.create('store.EmpresasFilter')
	 }]
	 
	 de un Locator
{
   xtype : 'uxlocatorfield',
   fieldLabel : t('plan.items.revisor'),
   name : 'revisor',
   valueField: 'id',
   displayField : 'nombre',
   selectionWidget: 'empleadosmaster'                                                 
}	 
	 
	 

filtrable : {}, Si esta vacio y tengo validationList:inclusion, monto una combo con la lista

filtrable : {
	xtype           : 'combobox'
	forceSelection	: true,
	queryMode       : 'local',
	valueField		: 'cdempcsc',
	displayField	: 'dsempcsc',
	store			: Ext.create('store.EmpresasFilter')
	}

filtrable : {
	xtype : 'uxlocatorfield',
	valueField: 'id',
	displayField : 'nombre',
	selectionWidget: 'empleadosmaster'                                                 
}	 	 
	 
	
 
	 
Tipos posibles   http://docs-origin.sencha.com/extjs/4.2.2/#!/api/Ext.data.Field-cfg-type

    auto (Default, implies no conversion)
    string
    int
    float
    boolean
    date
	 
TODO: incorporar validaciones del Modelo  http://docs-origin.sencha.com/extjs/4.2.2/#!/api/Ext.data.validations
validations: [
        {type: 'presence',  field: 'age'},
        {type: 'length',    field: 'name',     min: 2},
        {type: 'inclusion', field: 'gender',   list: ['Male', 'Female']},
        {type: 'exclusion', field: 'username', list: ['Admin', 'Operator']},
        {type: 'format',    field: 'username', matcher: /([a-z]+)[0-9]{2,3}/}
    ]
    	 
	 */
	_createItem : function(searchField, modelField) {
		var me=this;
		//La especificacion del filtro puede venir establecida, si no es asi se monta a partir del modelo
		var it = searchField.filtrable || {};

		if (!it.xtype) {
			if (modelField.type === 'string') {
				it.xtype = 'textfield';
			} else if (modelField.type === 'date') {
				it.xtype = 'datefield';
			} else if (modelField.type === 'int') {
				it.xtype = 'textfield';
			} 
		}
		

		it.fieldLabel = searchField.filtrable.fieldLabel || searchField.label;//t(me.baseEntity + '.searchForm.' + field.name);
		it.labelAlign = 'top';
		it.anchor = '95%';
		it.name = modelField.name;
		it.bind = '{' + modelField.name +'}';
		// it.queryMode = 'local';
	    // it.valueField	= 'cdempcsc';
	    // it.displayField = 'dsempcsc';
		return it;
	},


	_loadComboAgrupacion : function() {
		var me = this;
		//los campos para el filtro
		var fieldNames = me.searchFields.filter(function(f) { return f.agrupable;	});
		if (fieldNames.length == 0) return;

		var comboAgrupacion = me.lookupReference( 'groupby' );
		
		var storeAgrupacion = comboAgrupacion.getStore();
		// si cambia la agrupación, saco la columna de la lista de ordenar
//		comboAgrupacion.on("change", me._loadComboOrdenacion, me);

		Ext.Array.forEach(fieldNames, function(columna, index) {
			storeAgrupacion.add({
				fieldName : columna.name,
				fieldDesc : columna.label
			});
		});

//		comboAgrupacion.store = storeAgrupaciones;

	},
	
	/**
	 * Recarga la combo de ordenacion con los campos especificados, eliminando si es necesario el campo por el que se agrupa
	 */
	_loadComboOrdenacion : function(cbAgrupacion, groupByField) {
		var me = this; 
		//los campos para la ordenacion
		var fieldNames = me.searchFields.filter(function(f) { return f.ordenable; });		    
		if (fieldNames.length == 0) return;

		var comboOrdenacion = me.lookupReference( 'orderby' );
		var storeOrdenacion = comboOrdenacion.getStore();
		
		storeOrdenacion.removeAll();
		comboOrdenacion.setValue('');

		Ext.Array.forEach(fieldNames, function(columna, index) {
			var desc;
			if (columna.textOrden != undefined || columna.textOrden != null) {
				desc = columna.textOrden;
			} else {
				desc = columna.label;
			}

			if (groupByField !== columna.name) {
				storeOrdenacion.add({
					fieldName : columna.name,
					fieldDesc : desc
				});
			}
		});
	}




	
	
}); 