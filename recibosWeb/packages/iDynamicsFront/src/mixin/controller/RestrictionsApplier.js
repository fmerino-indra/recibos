
//Requisito: que exista RestrictionsCfg.getRestrictions() y que exista SessionManager.userHasAnyPermission()

Ext.define('iDynamicsFront.mixin.controller.RestrictionsApplier', {
   extend: 'Ext.Mixin',
  
   mixinConfig: {
      after: {
          init: 'ram_afterinit'
      }
   }, 
   
   ram_afterinit: function(){ //Vamos a aplicar los permisos AUTOMÁTICAMENTE
      var me=this,
         view = me.getView(),
         selfClassName = Ext.getClass(me).getName(),
         viewClassName = view ? Ext.getClass(view).getName() : null,
         //restrictions = Hn.view.pacientes.detalle.EntityViewportRestrictions.getRestrictions();
         restrictions = RestrictionsCfg.getRestrictions(),
         restrToApply = ( restrictions[selfClassName] ? restrictions[selfClassName] : (restrictions[viewClassName] ? restrictions[viewClassName] : null));
      if (view && restrToApply){
         me.ram_applyRestrictions ( view , restrToApply);
      }
   },
   
   //Los métodos de esta clase podrían empezar por rum (restrictionutils mixin) para evitar colisiones.
  
   //Requisito: debe existir SessionManager.userHasAnyPermission(...)

   // Esto es una constante para usar con applyRestrictions
   ram_restrActions: {
      disable: function(viewItem){ viewItem.setDisabled(true) },
      readonly: function(viewItem){ viewItem.setReadOnly(true) },
      hide: function(viewItem){ viewItem.hide() }
   },  
  
   ram_applyRestrictions : function (parentView, restrictions){ //Modificar una vista segun roles. [MOD]. 
      var me=this, 
         rl=restrictions.length,
         view,restr,roles, fnToApply;
      for (var i=0; i<rl; i++){
         var restr = restrictions[i],
            permissionsReq = restr.permissions;
         if(permissionsReq==null || !SessionManager.userHasAnyPermission(permissionsReq) ){  //Si no tiene al menos unos de los permisos aplico la restircción
            views = parentView.query(restr.view);
            if (views!=null){
               if (restr.action && Ext.isFunction(restr.action)) {
                  fnToApply = restr.action; //action puede ser una función
               } else if (me.ram_restrActions[restr.action]) {
                  fnToApply = me.ram_restrActions[restr.action];
               } else {
                  fnToApply = me.ram_restrActions["hide"]; //Por defecto la función es "hide"
               }
               for (var j=0; j<views.length; j++){
                  fnToApply(views[j]);
               }                
            }
         }
      }
   }    

});
