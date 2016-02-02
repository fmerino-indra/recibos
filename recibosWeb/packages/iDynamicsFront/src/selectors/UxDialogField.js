/**
 * Componente de seleccion simple
 */
Ext.define('iDynamicsFront.selectors.UxDialogField', {
    extend : 'Ext.form.field.Trigger',
    alias : 'widget.uxdialogfield',
    //requires : ["Ux.utils.UxSelectionDialog"],
    editable : false,
    
    dialogWidget : null,  
    valueField: null,
    displayField: null,
    onTriggerCustom: null,
    onAcceptselectionCustom: null,
    
    onTriggerClick : function(){
        var me=this;
        var dlg = Ext.widget(me.dialogWidget); //Construyo el dialogo
        //Capturo los eventos definidos por convenio, acceptselection y cancelselection
        dlg.on({
            acceptselection: {fn: function(dialog, selectionModel){
                var me=this;
                var selected=selectionModel.getSelection();
                if (selected.length>0){
                    //debugger
                    me.setRecord(selected[0]);
                }
                //Me quedo solo con el primero 
            }, scope: this},
            cancelselection: {fn: function(dialog){
               //alert("Cancelado")
            }, scope: this}
        });
        
        if (me.onTriggerCustom!=null){
            me.onTriggerCustom(me, dlg)
        }
        dlg.show(); //Lo mostramos
    },
    
    setRecord: function(record){
        //debugger
        //Esperamos en record un Ext.data.Model
        var me=this;
        me._record=record;
        if (me.displayField!=null){
            me.setValue(record.get(me.valueField), record.get(me.displayField))
        }else{
            me.setValue(record.get(me.valueField))
        }
    },
    
    displValBuffer: {},
    
    setValue: function(value, displayValue) {
        //displayValue es opcional (valor a mostrar)
        var me=this, objRec={};
        if (displayValue==null){
            if (me.displValBuffer[value]!=null)
               displayValue=me.displValBuffer[value];
            else
               displayValue=value;
        } else {
            //Guardamos el valor por si en el futuro repetimos (ej: al hacer reset)
            me.displValBuffer[value] = displayValue;
        }
        // Set the value of this field.
        me.value = value;
        me.setRawValue(displayValue);
        
        //debugger
        objRec[me.displayField ] = displayValue;
        objRec[me.valueField ] = value;
        me.fireEvent('addRecord', me, objRec); //record.get(me.valueField)

        //esto para que lance por ejemplo el evento onchange
        return me.mixins.field.setValue.call(me, value);
        //return me;
    },
    
    getValue: function(value) {
      var me=this;
      if (me.value!=null)
         return me.value;
      else
         return me.getRawValue();
    },
    
    getSubmitValue: function() {
        return this.getValue();
    },
    
    getRecord: function (){
      var me=this;
      if (me._record !=null){
         return me._record;
      }
    },
    setDinamicExtraFields: function(fields) {
        var me = this;
        me.selectionWidget.dinamicExtraFields = fields;
    }
});