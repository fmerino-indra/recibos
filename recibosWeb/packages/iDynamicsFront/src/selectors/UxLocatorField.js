/**
 * Componente de seleccio  simple. Abre una ventana de dialogo para seleccionar
 *
 *  @example
 *  {
 *       xtype : 'uxlocatorfield2',
 *       valueField: 'cdCliente',
 *       displayField : 'dsCliente',
 *       selectionWidget : {
 *          xtype : 'clientesmaster',
 *          multiSelect : false
 *       }                   
 *  }
 */
Ext.define('iDynamicsFront.selectors.UxLocatorField', {
    extend : 'iDynamicsFront.selectors.UxDialogField',
    alias : 'widget.uxlocatorfield2',
    
    componentCls : 'ux-locatorfield',

    requires : ['iDynamicsFront.selectors.UxDialogField', 'iDynamicsFront.selectors.UxSelectionDialog'],
    
    //parametros obligatorios
    valueField : null,
    displayField : null,    
    selectionWidget : null,

    /**
     * función para actuar sobre los campos de busqueda.
     * Podemos inicializarlos ...
     */    
    onLocatorOpen: null,
    
    //Opcionales
    dialogWidth: null,
    dialogHeight: null,
    
    // ver http://www.eekboom.de/ClearButton.html
    //plugins: ['clearbutton'],
    
    initComponent:function(){

        var me = this;
        if(me.valueField === null) {
            Ext.Error.raise('No se ha definido la propiedad obligatorio valueField.');
        }
        if(me.selectionWidget === null) {
            Ext.Error.raise('No se ha definido la propiedad obligatorio selectionWidget.');
        }        
        me.dialogWidget = {
            xtype:'uxselectiondialog2',
            cls: 'locatorDialog',
            btnAcceptText : t('commons.button.acceptText'),
            btnCancelText : t('commons.button.cancelText'),
            controlItemDblClick : true, 
            selectionWidget: me.selectionWidget
        }
        if (me.dialogWidth)
            me.dialogWidget.width = me.dialogWidth;
        if (me.dialogHeight)
            me.dialogWidget.height = me.dialogHeight;

        /*
        var customizeWidget = function(oDialogField, dialogWidget){
            alert(123)
            var tablepanel = dialogWidget.down(tablepanel);
            if (tablepanel!=null){
                var gridcolumns = tablepanel.query["headercontainer[hideOnLocator=true]"]
                debugger
                if (gridcolumns!=null) {
                  alert(gridcolumns.length)
                }
            }
        }*/
        
        if (me.onLocatorOpen!=null){
            /*
            me.onTriggerCustom = function(oDialogField, dialogWidget) {
               me.onLocatorOpen(oDialogField, dialogWidget);
            }*/
            me.onTriggerCustom = me.onLocatorOpen;
            /*me.onTriggerCustom = function(oDialogField, dialogWidget) {
               customizeWidget(oDialogField, dialogWidget)
               me.onLocatorOpen(oDialogField, dialogWidget);
            }        */    
        /*} else {
            me.onTriggerCustom = customizeWidget;*/
        }
        
        me.callParent();
        
       
    }
 
});