/**
 * @author madelavega@indra.es
 * @class Ux.util.ValidationForm
 */
Ext.define('iDynamicsFront.util.ValidationForm', {
    extend: 'Ext.form.Panel',
    alias: 'widget.validationform',
    requires: ['Ext.util.MixedCollection'],
    /**
     * botón destinado al guardado del formulario. Es <b>OGLIGATORIO</b>
     * pasarlo en el constructor.
     *
     * @type Ext.button.Button
     */
    saveButton: null,

    //model: null,
    //bindModelValidations: true,
    errorCls: 'with-error',

    /**
     * Inicializador del objeto. Define los objetos necesarios para el control de errores, así como añadir
     * los eventos necesarios y preparar la suscripción de eventos del botón destinado al guardado de los
     * datos del formulario. En esta clase, este botón está definido como null, y es <b>OBLIGATORIO</b>
     * definirlo en los distintos objetos que extiendan de este. por ejemplo :
     *      me.saveButton = Ext.create('Ext.button.Button', {
     *        text	:'Guardar'
     *   });
     *
     *   De no ser así, el método lanzará un error.
     *
     */
    initComponent: function() {
        var me = this;

        me.callParent();

        //Se inicia el array que contendrá errores de primer nivel
        me.activeErrors = [];
        //Se inicia el array que contendrá  errores de segundo nivel
        me.externalActiveErrors = [];
        //controla los errores vinculados a campos (de primer nivel) 
        //para su refresco
        me.errorFields = Ext.create('Ext.util.MixedCollection');

        if (me.saveButton === null) {
            Ext.Error.raise('Es obligatorio definir un objeto Ext.button.Button dentro de la propiedad saveButton.');
        }

        /*
    	me.addEvents(
	    	/ **
	    	 * Se lanza antes del evento save, para poder añadir aquí las 
	    	 * validaciones de 2º nivel. Si devuelve false no se lanza el 
	    	 * evento save (por defecto true)
	    	 * @param instancia del objeto
	    	 * @event
	    	 * /
	    	'beforesaveform',
	    	/ **
	    	 * Este evento se lanzará cuando el proceso de validación 
	    	 * haya pasado los niveles 1 y 2
	    	 * @param instancia del objeto
	    	 * @event
	    	 * /
	    	'saveform'
    	);
		*/
        me.on('fielderrorchange', me.onFieldErrorChange, me);
        me.saveButton.on({
            click: me.saveButtonClick,
            scope: me
        });
    },

    /**
     * Se dispara cuando se lanza el evento fielderrorchange del formulario.
     * Se encarga de comprobar los distintos errores vinculados a los campos
     * del formulario, por si uno de estos puede tener más de una validación de
     * nivel 1, los actualiza en la {@link Ext.util.MixedCollection}
     * errorFields, para poder refrescar la tooltip del botón de guardado
     * @param {Ext.panel.Panel} ancestor
     * @param {Ext.form.field.Base} field
     * @param {String} error
     */
    onFieldErrorChange: function(ancestor, field, error) {
        var me = this,
            errorFields = me.errorFields;

        if (field !== undefined && Ext.isObject(field)) {
            fieldId = field.getId();
            var errorField = errorFields.get(fieldId);
            if (error !== '') {
                var activeErrors = [(field.fieldLabel || field.labelText) + ': '].concat(field.getActiveErrors());
                if (errorField === null) {
                    errorFields.add(fieldId, activeErrors);
                } else {
                    errorFields.replace(fieldId, activeErrors);
                }
            } else {
                if (errorField !== null) {
                    errorFields.removeAtKey(fieldId);
                }
            }
        }

        //Si existe el errorsTip (ya se ha clickeado sobre el botón guardar) 
        //actualizamos su contenido
        if (me.errorsTip) {
            me.activeErrors.length = 0;
            me.getFormActiveErrors();
            if (me.activeErrors.length > 0 || me.externalActiveErrors.length > 0) {
                me.errorsTip.update(me.formatActiveErrors());
            } else {
                me.errorsTip.destroy();
                delete me.errorsTip;
            }
        }
    },

    /**
     * Devuelve la lista de errores
     * @return {String[]} lista de errores
     */
    getFormActiveErrors: function() {
        var me = this,
            errorFields = me.errorFields;
        if (errorFields.getCount() > 0) {
            errorFields.each(function(errorField) {
                me.activeErrors = me.activeErrors.concat(errorField);
            });
        } else {
            me.activeErrors.length = 0;
        }

        return me.activeErrors;
    },

    /**
     * @public
     * Añade un error o un array de errores de segundo nivel. Este es el método al que habrá
     * que llamar externamente para añadir estos errores.
     * @param {String/String[]} error/errores
     * @return {String[]} Array de errores
     */
    addFormActiveErrors: function(errores) {
        var me = this;
        if (!Ext.isArray(errores)) {
            errores = [errores];
        }
        me.externalActiveErrors = errores;
    },

    /**
     * Se ejecuta cuando se dispara el evento click del botón del objeto saveButton. Se encarga de:
     * - Ejecutar una primera validación del formulario para los errores de primer nivel.
     * - Lanza el evento beforesave, por si se quiere capturar antes de lanzarse el evento save para
     * controlar, por ejemplo, errores de segundo nivel.
     * - En caso de que no se interrumpa la ejecucación y no haya errores, se lanza el evento save
     * - Crea el {@link Ext.tip.ToolTip} para mostrar errores
     * @param {Ext.button.Button} btn
     */
    saveButtonClick: function(btn) {
        var me = this,
            fields, len, form, field, isValid;
        me.activeErrors.length = 0;

        form = me.getForm();
        fields = form.getFields().items;
        len = fields.length;
        while (len--) {
            field = fields[len];
            isValid = field.validate();
            if (!isValid) {
                me.onFieldErrorChange(me, field, 'error');
            }
        }
        me.getFormActiveErrors(); //AÑADIMOS LOS ERRORES DE PRIMER NIVEL (CAMPOS INDIVIDUALES) A LA LISTA DE ERRORES ACTIVOS DEL FORMULARIO 
        // SE LANZA LA VALIDACIÓN DE 2º NIVEL 
        
        if (me.fireEvent('beforesaveform', me) !== false && me.activeErrors.length === 0) {
            //SI NO HAY ERRORES DE TIPO 1 NI DE TIPO 2, SE LANZA EL EVENTO QUE PERMITE ENVIAR AL SERVIDOR LA LLAMADA PARA SALVAR LOS DATOS 
            me.fireEvent('saveform', btn, me);
            return;
        }

        //MOSTRAMOS EL TOOLTIP DE ERRORES SI ES NECESARIO
        if (me.activeErrors.length > 0 || me.externalActiveErrors.length > 0) {
            if (!me.errorsTip || me.errorsTip === null) {
                //me.saveButton.addCls(me.errorCls);
                me.errorsTip = Ext.create('Ext.tip.ToolTip', {
                    target: me.saveButton.getEl(),
                    //anchor		: 'top',
                    title: t('commons.erroresValidacion'),
                    autoHide: false,
                    renderTo: me.getEl(),
                    html: me.formatActiveErrors(),
                    closeAction: 'destroy',
                    width: 300,
                    //		            height		: 200,
                    closable: true,
                    draggable: true,
                    cls: 'form-validation-popup'
                });

                me.errorsTip.on('destroy', function() {
                    me.errorsTip = null;
                });

                me.errorsTip.show();
                me.errorsTip.alignTo(me.saveButton.getEl(), 'br-tr', [0, -10]);
            } else {
                me.errorsTip.update(me.formatActiveErrors());
            }
        } else {
            //si no hay erores, destruimos el tooltip
            if (me.errorsTip) {
                me.errorsTip.destroy();
                delete me.errorsTip;
                me.saveButton.removeCls(me.errorCls);
            }
        }
    },

    /**
     * Formateo de errores
     *
     */
    formatActiveErrors: function() {

        var me = this;

        //var activeErrorsCollection = me.activeErrors.concat(me.externalActiveErrors);
        var errorItemTemplate = new Ext.XTemplate(
            '<div class="error-list">',
            '<div class="level1">',
            '<tpl for="activeErrors">',
            '<tpl if="xindex % 2 != 0">',
            '<div class="bloque">',
            '</tpl>',
            '<div class="{[xindex % 2 === 0 ? "error" : "campo"]}">{.}</div>',
            '<tpl if="xindex % 2 === 0">',
            '</div>',
            '</tpl>',
            '</tpl>',
            '</div>',
            '<div class="level2">',
            '<tpl for="externalActiveErrors">',
            '<div class="{[xindex % 2 === 0 ? "error" : "campo"]}">{.}</div>',
            '</tpl>',
            '</div>',
            '</div>'
        );

        var html = errorItemTemplate.applyTemplate({
            activeErrors: me.activeErrors,
            externalActiveErrors: me.externalActiveErrors

        });
        return html;
    },

    /**
     * @override
     * @param {Ext.form.field.Base} field
     */
    onFieldAdded: function(field) {
        var me = this;
        //console.debug(field.name);
        me.mon(field, 'validitychange', me.handleFieldValidityChange, me);
/*        if (me.bindModelValidations && me.model !== null) {
            me.model = Ext.ModelManager.getModel(me.model);
            me.applyModelValidation(field);
        }*/
    }

    /**
     * Aplica validaciones sobre un field del formulario
     * @param {Ext.form.field.Base} field
     */
    // applyModelValidation: function(field) {
    //     var me = this,
    //         validations = me.model.prototype.validations,
    //         len = validations.length,
    //         validationsForField, name = field.name; 

    //     if (name !== undefined && name !== '') {
    //         while (len--) {
    //             if (validations[len].name === name) {
    //                 me.setFieldValidationByModel(field, validations[len]);
    //             }
    //         }
    //     }
    // },

    // setFieldValidationByModel: function(field, validation) {
    //     var type = validation.type;

    //     if (type === 'presence') {
    //         field.allowBlank = false;
    //     } else if (type === 'length') {
    //         var isNumeric = Ext.isDefined(field.maxValue) || Ext.isDefined(field.minValue),
    //             target = isNumeric ? 'Value' : 'Length';
    //         if (validation.min) {
    //             field['min' + target] = validation.min;
    //         }
    //         if (validation.max) {
    //             field['max' + target] = validation.max;
    //         }
    //     } else if (type === 'email') {
    //         field.vtype = 'email';
    //     } else if (type === 'format') {
    //         field.regex = validation.matcher;
    //     }
    // }
});
