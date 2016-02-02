Ext.define('iDynamicsFront.selectors.UxDateTimePicker', {
   extend 			: 'Ext.panel.Panel',
   alias 			: 'widget.uxdatetimepicker',
   requires 		: ["iDynamicsFront.selectors.DatePickerZulu"],
   
   //Estos son configuración:
   timefieldCfg 	: null,
   datepickerCfg 	: null, 
   btnAcceptText 	: 'Accept',
   btnNowText 		: 'Now',
   labelTimeText 	: 'Time',
   cls				: 'ux-datetime-picker',

   //Estos son privados: 
   datepickerobj 	: null,
   timefieldobj 	: null,
   
   //Eventos emitidos:
   //> "accept" ( [Ext.Component] this, [Date] datepickervalue )

   
   getDatePicker:function(){
      return this.datepickerobj;
   },
   
   getTimeField:function(){
      return this.timefieldobj;
   },
   
   setValue:function(value){
      var picker=this;
      picker.datepickerobj.setValue(value);
      picker.timefieldobj.setValue(value);
   },
   
   getValue:function(){
      var picker=this, btnAccept;
      //debugger
      //Tomo la fecha y la hora y las uno en un único Date()
      //var datepickervalue = picker.datepickerobj.getValue();
      var datepickervalue = picker.datepickerobj.getActive(); //Ojo, este método es privado! cambiar de versión de EXTjs podría hacer que esto dejara de funcionar
      var timefieldvalue = picker.timefieldobj.getValue();
      if (timefieldvalue!=null){ //vale null cuando el valor esta vacío o no es correcto
         datepickervalue.setHours(timefieldvalue.getHours());
         datepickervalue.setMinutes(timefieldvalue.getMinutes());
      }else{
         datepickervalue = Ext.Date.clearTime(datepickervalue);
      }
      return datepickervalue
   },
   
   initComponent : function(){
      var picker = this;

      //var selectionView = Ext.widget(dlg.selectionWidget); // ----- Construyo la vista a incrustar dentro del dialogo

      var datepickeritem = {
         //xtype : 'datepicker',
         xtype : 'datepickerzulu', //ZULU
         border : 0,
         //minDate : new Date(),
         showToday : false
         //anchor : '100%'
         
         /*,
         handler : function(picker, date){
            // do something with the selected
            // date
            alert(123)
         }
         */
      }
      
      btnAccept = picker.btnAccept = Ext.widget('button',{
            text    : picker.btnAcceptText,
            //scale : 'medium',
            iconCls : 'icon-small-tb-aceptar',
            scale   : 'small',
            handler : function(){
                
               //debugger
                
               var datetimepickervalue=picker.getValue();
               if(picker.fireEvent('beforeaccept', picker, datetimepickervalue) !== false) {
                   picker.fireEvent('accept', picker, datetimepickervalue);
                   picker.collapse();
               }
            }
         })

      var timefielditem = {
         xtype 		: 'timefield',
         labelAlign : 'left',
         fieldLabel : picker.labelTimeText+':',
         labelWidth : 50,
         format 	: 'H:i',
         // minValue: '0:00',
         // maxValue: '12:00 PM',
         increment 	: 30,
         width		: 166,
         labelWidth : 30,
         cls		: 'ux-fieldTime',
         listeners  : {
            validitychange : function (timefield, isValid) {
                btnAccept.setDisabled(!isValid);
            }
         }
         //anchor : '100%'
      }

      if (picker.datepickerCfg!=null){
         datepickeritem = Ext.Object.merge(datepickeritem, picker.datepickerCfg);
      }      
      if (picker.timefieldCfg!=null){
         timefielditem = Ext.Object.merge(timefielditem, picker.timefieldCfg);
      }

      picker.datepickerobj = Ext.widget(datepickeritem);
      if(datepickeritem.value) {
        picker.datepickerobj.setValue(datepickeritem.value);
      }
      picker.timefieldobj = Ext.widget(timefielditem);

      picker.items = [picker.datepickerobj,picker.timefieldobj]

      picker.dockedItems = [{
         dock 	: 'bottom',
         xtype 	: 'toolbar',
         items 	: [{
            xtype 	: 'button',
            text 	: picker.btnNowText,
            hidden  : true,
            scale	: 'medium',
            handler : function(){
               //picker.datepicker.selectToday()
               //var clearDate=Ext.Date.clearTime(new Date());
               //var now = new Date();
               var now = Ux.utils.Date.getUTCDate(); //ZULU
               picker.setValue(now);
            }
         },{
            xtype : 'tbfill'
         },btnAccept]
      }]

      picker.callParent(); //Necesario siempre
      picker.addEvents('accept'); //Eventos definidos por convenio para este componente
   },
   
   show : function () {
   		var me = this, collapseIf = me.collapseIf;
   		me.callParent();
	    me.mon(Ext.getDoc(), {
            mousewheel: collapseIf,
            mousedown: collapseIf,
            scope: me
        });
   		return this;
   },
   
   collapseIf: function(e) {
        var me = this;

        if (!me.isDestroyed && !e.within(me.bodyEl, false, true) && !e.within(me.el, false, true) && !e.within(me.timefieldobj.getPicker().el, false, true)) {
            me.collapse();
        }
    },
    
    collapse : function () {
    	var me = this, doc = Ext.getDoc(), collapseIf = me.collapseIf;
    	doc.un('mousewheel', collapseIf, me);
        doc.un('mousedown', collapseIf, me);
        me.destroy();
    }


});