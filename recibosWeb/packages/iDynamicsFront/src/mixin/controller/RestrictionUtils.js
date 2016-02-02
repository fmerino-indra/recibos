//Utilidades para modificar vistas desde viewControllers

Ext.define('iDynamicsFront.mixin.controller.RestrictionUtils', {
   //extend: 'Ext.Mixin',
  
   //Los métodos de esta clase podrían empezar por rum (restrictionutils mixin) para evitar colisiones.
  
   //Requisito: debe existir SessionManager.userHasAnyPermission(...)

   // Esto es una constante para usar con applyRestrictions
   rum_restrActions: {
      disable: function(viewItem){ viewItem.setDisabled(true) },
      readonly: function(viewItem){ viewItem.setReadOnly(true) },
      hide: function(viewItem){ viewItem.hide() }
   },  
  
   rum_applyRestrictions : function (parentView, restrictions){ //Modificar una vista segun roles. [MOD]. 
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
               } else if (me.rum_restrActions[restr.action]) {
                  fnToApply = me.rum_restrActions[restr.action];
               } else {
                  fnToApply = me.rum_restrActions["hide"]; //Por defecto la función es "hide"
               }
               for (var j=0; j<views.length; j++){
                  fnToApply(views[j]);
               }                
            }
         }
      }
   }    

});
