/*
 * Este Componente es un SearchForm con 3 partes diferenciadas
 *  -Contadores
 *  -Configuraci√≥n
 *  -Avanzada
 * Se crea una toolbar con los botones de search y reset
 *
 * Se exponen los eventos de beforeSearch, afterSearch y resetSearch para que sean recogidos por el controller,
 * el search es lanzado tanto con el boton de la toolbar como con los contadores que realizarian una busqueda directa
 * Hay que tener en cuenta que estos eventos mandan como parametro el propio searchForm y el boton que ha lanzado el evento
 *
 * */
Ext.define("iDynamicsFront.util.SearchFormAuto", {
	extend : "Ext.form.Panel",
	alias : "widget.uxsearchformauto",
	referenceHolder: true,
	reference: 'searchform',
	requires : ['iDynamicsFront.util.Counter'],
	title : 'FILTRO',
	border : false,
	collapsible : true,
	loadOnCounterClick : true,
	autoScroll : true,
	DATE:null,
	layout : {
		type : 'accordion',
		titleCollapse : true,
		multi : true,
		fill : false
	},

	// es un tanto oscuro escuchar el propio evento aqui...
	//listeners: {
    //    search: 'onAfterSearch' // funcion a implementar en el controlador
    //},
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
	
	//Grid de datos donde se muestra el resultado de la busqueda
	datalist : null,
	cmpDatalist : null,
	/**
	 * campos adicionales/parametrizados (fuera del modelo)
	 * @type {Array}
	 * {
	 * 	field: 'estado',
	 * 	value: 'activo',
	 * 	visible: false,
	 * 	editable:false
	 * }
	 */
	extraFields: null,


	counters : [],	
	criteria : [{
		xtype : 'panel',
		layout : 'anchor',
		name : 'criteria',
		cls : 'avanzada',
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

	initComponent : function() {
		var me = this, searchButton, resetButton;
		
		// concateno los cls (el básico del componente y el que se define en cada instancia)
		me.cls = 'search-form ' + me.cls; 

		/* TODO: volver sobre esto, si tengo un datalist ya puedo obtener el store
		if (me.datalist) {
				me.cmpSearch = me.up().down(reference=me.datalist);	
		}
		if (me.store==null) {
			me.store = me.cmpSearch.getStore();
		}
		*/
		if (!me.store) {
			Ext.Error.raise("Es obligatorio disponer del store para construir los campos de busqueda avanzada un store");
		}

		// establezco una variable general relativa al objeto de la busqueda, para internacionalizacion....
		me.model = me.store.model;
		var arrMcn = me.model.getName().split(".");
		me.baseEntity=arrMcn[arrMcn.length];
		
		me._createCriteria();

		// div para la sombra de la derecha
		me.html = '<div class="searchForm-right-shadow"></div>';
		me.counters = me.counters ? me.counters : [];
		if (me.counters.length == 0) { 
			me.hasCounters = false; 
		} else {
			// Ver si es necesario tener una reference. De momento no hace falta
			// En el caso de que hiciera falta, habría que darsela a cada bloque de counter que puede existir mas de uno
			//me.counters[0].reference = 'counters';
			//me.counters[0].title = t('commons.searchForm.contadores');
		}
		searchButton = me.searchButton = Ext.create("Ext.button.Button", {
			cls 	: 'hightlighted',
			text 	: t('commons.button.buscar') ,
			//scale : 'medium',
			glyph	: Glyphs.getIcon('search')
		});

		resetButton = me.resetButton = Ext.create("Ext.button.Button", {
			//cls : 'ux-btn',
			text : t('commons.button.limpiar') ,
			//scale : 'medium',
			glyph	: Glyphs.getIcon('eraser')
		});

		me.dockedItems = [{
			dock : 'bottom',
			xtype : 'toolbar',
			/*defaults : {
				scale : "medium",
				disabled : false
			}, */
			items : [resetButton, '->', searchButton]
		}];
	
		//TODO: filter es IE8>
		me.existFieldsInOrder = me.searchFields.filter(function(f) { return f.ordenable; }).length;
		me.existFieldsInGroup = me.searchFields.filter(function(f) { return f.agrupable; }).length;;	

		me._makeconfiguration();

		if (!me.existFieldsInOrder && !me.existFieldsInGroup) {
			me.configuration[0].hidden= true
		}

		if (me.hasCounters) {
			me._createCounter();
		}
	
	
		me.items = me.counters.concat(me.criteria, me.configuration);
		//me.items = me.filtro.concat(me.counters, me.criteria, me.configuration);

		me.callParent();

		//me.addEvents("search", "reset");

		searchButton.on("click", me.onSearchButtonClick, me);
		resetButton.on("click", me.onResetButtonClick, me);
	},

	/**
	 * Api del componente para realizar la busqueda
	 */
	doSearch: function(cont) {
		var me = this;
		me.busquedaAvanzada = (cont===undefined?true:false);
		me._search(cont);
	},


	beforeRender : function() {
		var me = this;
		me.callParent();
		
		me._loadCountersStore();
		me._loadComboAgrupacion();
		me._loadComboOrdenacion();
	},
	
	afterRender : function() {
		var me = this;
		me.callParent();
	},
	
	onSearchButtonClick : function(btn) {
		var me = this;
		me.busquedaAvanzada=true;
		me._search();
	},

	onResetButtonClick : function(btn) {
		var me = this;
		me.getForm().reset();
		me._unselectCounters();
		//Lanzo evento por si en el controlador se quiere hacer algo
		me.fireEvent("resetSearch", me, btn);
	},

	// Método que deselecciona los contadores
	// view : Si recibe una view, debe deseleccionar todas menos la que recibe que es la que se ha clicado
	_unselectCounters : function(viewNoUnselect) {
		var me = this;
		if (me.counters.length == 0) { 
			return; 
		}
		
		//var vCont = me.lookupReference( 'counters' );
		Ext.Array.each(me.counters, function(item){
			if(!viewNoUnselect || (viewNoUnselect && viewNoUnselect !== item.view)){
				item.view.setSelection(null);
			}
		});
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
			if (!counter.store) {
				Ext.Error.raise("Es obligatorio que los counters tengan un store");
			}
			//TODO eliminar xtype del objeto counter si lo hubiese
			ct = Ext.create("iDynamicsFront.util.Counter", counter);
			if (me.loadOnCounterClick) {
				ct.on("counterclick", me.onCounterClicked, me);
			}
			generatedCounters[generatedCounters.length] = ct;
		});
		me.counters = generatedCounters;
	},

	onCounterClicked : function(view, record) {
		var me = this;
		
		me._unselectCounters(view);
		me.busquedaAvanzada=false;
		me._search(record);
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

	// metodo que carga el store de contadores
	_loadCountersStore : function() {
		var me = this;
		if(me.counters){
			Ext.Array.each(me.counters, function(item){
				item.getStore().load();
			})
		}
	},

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


		/* Trato campos adicionales/parametrizados (fuera del modelo)
		{
            field: 'estado',
            value: 'activo',
            visible: false,
            editable:false
        }
		*/
		
		if (me.extraFields)	{
			Ext.Array.forEach(me.extraFields, function(ef) {
				var it = {};
				it.xtype = 'textfield';
				it.name = ef.field;
				it.value = ef.value;
				it.hidden = !ef.visible || true;
				it.readOnly = !ef.editable || true;
				me.criteria[0].items[me.criteria[0].items.length] = it;
			});
		}
			
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
		it.title = it.fieldLabel; // el rango es un fieldset, necesita un titulo
		it.labelAlign = 'top';
		it.anchor = '95%';
		it.reference = searchField.name;
		it.name = modelField.name;
		it.bind = '{' + modelField.name +'}';
		it.operator = searchField.operator;
		return it;
	},


	_loadComboAgrupacion : function() {
		var me = this;
		//los campos para el filtro
		var fieldNames = me.searchFields.filter(function(f) { return f.agrupable;	});
		
		var comboAgrupacion = me.lookupReference( 'groupby' );

		if (fieldNames.length == 0) {
			comboAgrupacion.hide();
			comboAgrupacion.disable(true);
		}
		
		var storeAgrupacion = comboAgrupacion.getStore();
		// si cambia la agrupaci√≥n, saco la columna de la lista de ordenar
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
	 * Busco el componente donde mostrar los resultados
	 */
	getBoundComponent: function() {
		var me = this;
		if(!me.datalist) return null;
		return me.up().down(reference=me.datalist);
	},
	
	/** 
	 Realiza la busqueda, componiendo los parametros necesarios 
	*/
	_search: function(cont) {
		var me = this;
		var formValues = me.getForm().getValues(),
			mistore = me.store,
			list = me.getBoundComponent();

		// TODO: Lanzo evento por si en el controlador se quiere hacer algo
		//if (list) {list.setLoading(true);} ESTO no hace falta, el store gestiona solo la mascara de loading
		me.fireEvent("beforeSearch", me);

		/* Nuevo json a enviar */
		var json = {responseType: 'list'};
		
		if (cont) {
			json.contador = cont.get('cdcontador');
			// Si busco con los contadores, limpio los filtros
			me.reset();
		} else {
			var fieldCriteria = me.lookupReference( 'criteria' );

			json.where = [];
			fieldCriteria.items.items.forEach(function(f, idx, arr) {
				//console.info(f.name + ' de ' + f.xtype)
				if (f.xtype === "uxdaterange") {
					if(f.initDate.isValid() && f.endDate.isValid()){
						if (f.initDate.getValue()) {
							var initDate = DATE.getUTC(f.initDate.getValue());//new Date(f.initDate.getValue()).getTime();
							json.where.push( {name: f.name, value : [initDate], operator: 'gt'} );
						}
						if (f.endDate.getValue()) {
							var endDate = DATE.getUTC(f.endDate.getValue());//new Date(f.endDate.getValue()).getTime();
							json.where.push( {name: f.name, value : [endDate], operator: 'lt'} );
						}
					} else {
						f.initDate.setValue(null);
						f.endDate.setValue(null);
					}
				} else if (f.xtype === "idf-range") {
					
					var opInf = f.includedValue ? 'gte': 'gt', 
					    opSup = f.includedValue ? 'lte': 'lt';  

					if(f.min.isValid() && f.max.isValid()){
						if (f.min.getValue()) {
							json.where.push( {name: f.name, value : [f.min.getValue()], operator: opInf} );
						}
						if (f.max.getValue()) {
							json.where.push( {name: f.name, value : [f.max.getValue()], operator: opSup} );
						}
					} else {
						f.min.reset();
						f.max.reset();
					}
				} else if (f.xtype === "combobox") {
					
					//console.info('                   ' + f.name + '='+ f.getValue() + ', '+ f.getRawValue());
					if (f.getRawValue()) {
						var operator = 'eq';
						if(f.operator){operator = f.operator}
						json.where.push( {name: f.name, value : [f.getValue()], operator: operator} );
					}

				} else {

					if (f.getValue()) {
						var operator = 'eq';
						if(f.operator){operator = f.operator}
						json.where.push( {name: f.name, value : [f.getValue()], operator: operator} );
					}
				}

			});

			var fieldGroupBy = me.lookupReference( 'groupby' );
			if (fieldGroupBy.getValue()) {
				json.groupby = {name: fieldGroupBy.getValue()};
			}		

			var fieldOrderBy = me.lookupReference( 'orderby' );
			var fieldOrderDirection = me.lookupReference( 'sortDirectionASC' );
			
			if (fieldOrderBy.getValue()) {
				json.orderby = {name: fieldOrderBy.getValue(),
						   		direction: (fieldOrderDirection.checked?'ASC':'DESC')};	
			}	
		}
		

		//Logger.log(Ext.JSON.encode(json));
	
		/* fin Nuevo json a enviar */

		var panelConfiguracion = Ext.ComponentQuery.query('panel')[1];
		// Panel de configuracion
		// Necesitamos tener habilitado el panel para la busqueda
		panelConfiguracion.enable();
		
		//Deshalitamos el searchForm durante el proceso de b√∫squeda
		me.disable();
		//si es busqueda avanzada, deselecciono los contadores.
		if (me.busquedaAvanzada) {
			//borro el counter seleccionado
			me._unselectCounters();
			//marco el seleccionado
			//me.selectCounterTodas();
			//desmarco los valores de estado, y dejo TODAS
			formValues['cdcontador'] = 'todos';
		} else {
			//si es busqueda por contadores, limpio la avanzada, SOLO CRITERIA
			me.clearCriteria();
			//me.getForm().reset();
		}

		
		/*
		
		//Se crea un objeto filtro que se envia en formato JSON al servicio REST
		var filtro = new Object();
		//campos del filtro
		Ext.Array.each(me.model.getFields(), function(field) {
			filtro[field.name] = formValues[field.name];
		});		
		//campos de configuracion
		filtro['columnaOrden'] = formValues['campoOrdenacion'];
		filtro['tipoOrden'] = formValues['orientacionOrder'];
		filtro['groupby'] = formValues['campoAgrupacion'];
*/
		mistore.getProxy().setExtraParam( 'filtroJson', Ext.encode(json));
		//Callback en el load para modificar titulo grid

		mistore.loadPage( 1 , {
			callback : function(records, options, success) {
				if (!success) {
					//if (list) {list.setLoading(false);}
					Ext.Msg.show({
						msg : t('alert.error.sin_respuesta.descripcion'),
						title : t('alert.error.sin_respuesta.titulo'),
						cls : 'ux-mb',
						buttons : Ext.Msg.OK,
						icon : Ext.Msg.INFO
					});
				} else {		
					me.enable();
					/*Bug ext5
					http://www.sencha.com/forum/showthread.php?295910-5.1.0.107-setDisabled%28true%29-on-formpanel-doesn-t-enable-buttons
					*/
					me.searchButton.unmask();
					me.resetButton.unmask();
					
					var fieldGroupBy = me.lookupReference( 'groupby' );
					
					//Agrupacion 
					
					if (fieldGroupBy.getValue()) {
						mistore.group(  fieldGroupBy.getValue() );
					} else {
						mistore.group(  null );
					}

					//if (list) {list.setLoading(false);}
					// TODO: Lanzo evento por si en el controlador se quiere hacer algo
					me.fireEvent("afterSearch", cont);				
				}
			}
		});		
	},
	
	/**
	 * resetea el componente.. TODO: pendiente verificar
	 * @return {[type]} [description]
	 */
	resetCriteria: function() {
		var me = this;
		me.clearCriteria();
	},

	privates : {


		/**
		 * Limpio los criterios de busqueda
		 */
		clearCriteria: function () {
			me = this, frm=me.getForm();
			//Primero me guardo los datos de configuracion (orden y agrupacion)
			//var ag = frm.findField('campoAgrupacion').getValue()
			var fieldGroupBy = me.lookupReference( 'groupby' ).getValue();
			var fieldOrderBy = me.lookupReference( 'orderby' ).getValue();
			var fieldOrderDirectionASC = me.lookupReference( 'sortDirectionASC' ).getValue();
			var fieldOrderDirectionDESC = me.lookupReference( 'sortDirectionDESC' ).getValue();
			frm.reset();
			//Vuelvo a poner los valores a la configuracion de la busqueda
			me.lookupReference( 'groupby' ).setValue(fieldGroupBy);
			me.lookupReference( 'orderby' ).setValue(fieldOrderBy);
			me.lookupReference( 'sortDirectionASC' ).setValue(fieldOrderDirectionASC);
			me.lookupReference( 'sortDirectionDESC' ).setValue(fieldOrderDirectionDESC);
			
		},


		/**
		 * Recarga la combo de ordenacion con los campos especificados, eliminando si es necesario el campo por el que se agrupa
		 * Returns a unique ID for use in HTML id attribute.
		 * @cbAgrupacion {combobox} Combobox que aloja todos los capos pa¬°or los que ordenar
		 * @groupByField {formfield} Campo por el que agrupamos, para sacarlo de la agrupacion
		 */
		_loadComboOrdenacion : function(cbAgrupacion, groupByField) {
			var me = this; 
			//los campos para la ordenacion
			var fieldNames = me.searchFields.filter(function(f) { return f.ordenable; });		    
			
			var comboOrdenacion = me.lookupReference( 'orderby' );
			var fieldOrderDirection = me.lookupReference( 'orderDirection' );
			if (fieldNames.length == 0) {
				comboOrdenacion.hide();
				comboOrdenacion.disable(true);
				fieldOrderDirection.hide();
				fieldOrderDirection.disable(true);
			}

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
	}
	
}); 