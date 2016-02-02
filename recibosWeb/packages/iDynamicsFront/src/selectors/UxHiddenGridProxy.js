Ext.define('iDynamicsFront.selectors.UxHiddenGridProxy',{
	extend: 'Ext.form.field.Hidden',
	alias: 'widget.uxhiddengridproxy',

	//config: 
   associatedGridSelector : null,
   
   /*initComponent:function(){
      var me=this;
      me.callParent();
   },*/
   
   setValue : function (value){
       var me=this;
         parentForm = me.up("form");
       if (parentForm!=null){
           var associatedGrid = parentForm.down(me.associatedGridSelector),
             associatedGridStore = associatedGrid.getStore();
           me.addProxyListeners (associatedGrid);
           //debugger
           if (value){
               associatedGridStore.loadData(value);
           }else{
               associatedGridStore.removeAll();
           }
           //alert("setValue!");
           /*if (associatedGrid.isEditable && associatedGridStore.count() == 0){
           }*/
       }
       
   },
   getValue : function (){
       //alert("getValue!");
       var me=this;
         parentForm = me.up("form");
       if (parentForm!=null){
           var associatedGrid = parentForm.down(me.associatedGridSelector),
             associatedGridStore = associatedGrid.getStore(),
             fields = associatedGridStore.model.getFields(),
             records = associatedGridStore.getRange(),
             ret = [];
           //Solo meto records si sus campos no son todos vacíos
           //debugger
           if (records.length>0){
              for (var i=0; i<records.length; i++){
                  //eachKey( fn, [scope] )1
                  var crear = false,
                     data = records[i].getData();
                  for (var j=0; j<fields.length && !crear; j++){
                     var value = data[fields[j].name];
                     if (value!=null && value !=""){
                        crear = true
                     }
                  }
                  if (crear) {
                     ret[ret.length] = records[i].getData();
                  }
              }
           }
           me.addProxyListeners (associatedGrid);
           //debugger
           return ret;
           //debugger
           //associatedGridStore.loadData(value);
       }  
   },
   
   //Esto para que el cambio de datos en la lista provoque un onchange
   addProxyListeners : function (associatedGrid) {
      var me=this,
         associatedGridStore = associatedGrid.getStore();
      if (!associatedGridStore.hasListener("datachanged")){
         associatedGridStore.removeListener("datachanged",me.onAssocGridDataChanged);
         associatedGridStore.on({
             "datachanged": me.onAssocGridDataChanged,
             scope : me
         })
      }
      if (!associatedGrid.hasListener("edit")){
         associatedGrid.removeListener("edit",me.onAssocGridDataChanged);
         associatedGrid.on({
             "edit": me.onAssocGridDataChanged,
             scope : me
         })
      }
      
   },
   
   onAssocGridDataChanged : function () {
      var me=this;
      //debugger
      me.fireEvent( "change", me ); // ,newValue, oldValue, eOpts)
      //me.fireEvent( "onchange", me ); // ,newValue, oldValue, eOpts)
      //me.onchange();
   }
      
   
});