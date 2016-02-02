
//Requisito: que exista AppPermissionsConfig.getPermissions() y que exista SessionManager.userHasAnyPermission()

Ext.define('iDynamicsFront.mixin.controller.PermissionsApplier', {
   extend: 'Ext.Mixin',
  
   mixinConfig: {
      after: {
         init: 'pam_afterinit'
      }
   }, 
   
   pam_afterinit: function(){ //Vamos a aplicar los permisos AUTOMÁTICAMENTE
      var me=this,
         view = me.getView(),
         selfClassName = Ext.getClass(me).getName(),
         viewClassName = view ? Ext.getClass(view).getName() : null,
         //restrictions = Hn.view.pacientes.detalle.EntityViewportRestrictions.getRestrictions();
         permissions = AppPermissionsConfig.getPermissions(),
         permToApply = ( permissions[selfClassName] ? permissions[selfClassName] : (permissions[viewClassName] ? permissions[viewClassName] : null));
      if (view && permToApply){
         //me.pam_applyRestrictions ( view , restrToApply);
         me.pam_applyViewPermissions ( view , permToApply);
      }
   },
   
   //Los métodos de esta clase podrían empezar por rum (restrictionutils mixin) para evitar colisiones.
  
   //Requisito: debe existir SessionManager.userHasAnyPermission(...)

   // Esto es una constante para usar con applyRestrictions
   pam_restrActions: {
      disable: function(viewItem){ viewItem.setDisabled(true) },
      readonly: function(viewItem){ viewItem.setReadOnly(true) },
      hide: function(viewItem){ viewItem.hide() }
   },  
  
   pam_applyViewPermissions : function (parentView, permissions){ //Modificar una vista segun roles. [MOD].
   //pam_applyRestrictions : function (parentView, restrictions){ //Modificar una vista segun roles. [MOD]. 
      var me=this, 
         rl=permissions.length,
         view,restr,roles, fnToApply;
      for (var i=0; i<rl; i++){
         var perm = permissions[i],
            permissionsReq = Ext.isArray(perm.permission) ? perm.permission : [perm.permission]; //permission podría ser un array y valdría con tener uno de ellos. 
         if(permissionsReq==null || !SessionManager.userHasAnyPermission(permissionsReq) ){  //Si no tiene al menos unos de los permisos aplico la restircción
            views = parentView.query(perm.view);
            if (views!=null){
               if (perm.action && Ext.isFunction(perm.action)) {
                  fnToApply = perm.action; //action puede ser una función
               } else if (me.pam_restrActions[perm.action]) {
                  fnToApply = me.pam_restrActions[perm.action];
               } else {
                  fnToApply = me.pam_restrActions["hide"]; //Por defecto la función es "hide"
               }
               for (var j=0; j<views.length; j++){
                  fnToApply(views[j]);
               }                
            }
         }
      }
   }    

});
