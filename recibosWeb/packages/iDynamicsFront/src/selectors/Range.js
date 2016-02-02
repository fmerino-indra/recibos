/**
 * Componente que permite acotar por abajo y por arriba un conjunto secuencial. 
 * Soporta un rango de enteros y de fechas.
 * La modificacion de uno de los limites delimita a su vez el otro, por arriba o por abajo.
 * 
 * TODO: 
 * Configuracion para enviar un rango abierto, por arriba o por abajo
 * Extensible para validar los campos, recibiriamos un vtype
 * Para su utilizacion en un formulario normal, independiente del SearchFormAuto:
 *     Incluir propiedad Name
 *     Creariamos un elemento hidden con el miso name, sobreescribiendo los metodos getValue(), setValue()
 *     
 *      @example omo elemento de un searchFormAuto
 *       {
 *            xtype : 'idf-range',
 *            type  : 'datefield',
 *            format : t('date.grid'),
 *            includedValue : true,
 *            minValue      : new Date() ,
 *            maxValue      : new Date('10/1/2015')           
 *       },
 *       {
 *            xtype : 'idf-range',
 *            type  : 'numberfield',
 *            includedValue : false,
 *            minValue      : 5 ,
 *            maxValue      : 25           
 *       },
 *
 */
Ext.define('iDynamicsFront.selectors.Range', {
    extend: 'Ext.form.FieldSet',
    alias: 'widget.idf-range',

    width: '100%',
    //layout: 'column',
    //columnWidth: 0.5,
    padding : 0,
	layout: 'hbox',
    title: 'Rango',
    collapsible: false,
    cls: '',
	border: 1,
	style: {
	    borderColor: '#aeaeae',
	    borderStyle: 'solid'
	},

    startCls: 'range-from',
    endCls: 'range-to',

    /**
     * Determina si los valores seleccionados estan incluidos en el rango o no
     * @type {Boolean}
     */
    includedValue: true,

    /**
     * Formato del campo, solo valido para datefield
     * @type {[type]}
     */
    formato : null,

    /**
     * Valor inicial del rango
     * @type {[type]}
     */
    maxValue: null,

    /**
     * Valor final de rango
     * @type {[type]}
     */
    minValue: null,

    initComponent: function() {
        var me = this;

        //Validar el type, debe extender de Ext.form.field.Base
 		if(!Ext.widget(me.type) instanceof Ext.form.field.Base) {
            Ext.Error.raise( Ext.String.format('El tipo {0} no es un componente de formulario valido', me.type) );
        }

        me.min = Ext.widget(me.type, {
            submitValue: false,
            //fieldLabel: 'Minimo',
            format: me.format,
            emptyText: t('commons.searchForm.desde'),
            value: me.minValue,
            minValue: me.minValue,
            maxValue: me.maxValue,
             width: '50%',
            cls: me.startCls
        });


        me.max = Ext.widget(me.type, {
            submitValue: false,
            //fieldLabel: 'Máximo',
            format: me.format,
            emptyText: t('commons.searchForm.hasta'),
            value: me.maxValue,
            minValue: me.minValue,
            maxValue: me.maxValue,
            width: '50%',
            cls: me.endCls
        });

        me.items = [me.min, me.max];
        me.min.on('change', me.onMinChange, me);
        me.max.on('change', me.onMaxChange, me);

        me.callParent();
    },


    onMinChange: function(spin, newValue, oldValue) {
        var me = this;
        me.max.setMinValue(newValue);
    },
    onMaxChange: function(spin, newValue, oldValue) {
        var me = this;
        me.min.setMaxValue(newValue);
    }


})
