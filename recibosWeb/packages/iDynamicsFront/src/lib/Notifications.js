Ext.define('iDynamicsFront.lib.Notifications', {
   singleton: true,

   //toastNotification : Esto genera una caja que aparece desde arriba por unos segundos
   //    info: obligatorio, es el texto a mostrar
   //    delay: opcional, el numero de milisegundos a mostrarlo 
   
   toastNotification: function(info, delay) { 
      var msgCt,
         m;
      if (delay == null) { //si no se informa delay estimamos un tiempo
         delay = 33*(info.length);
         if (delay<1000){
           delay=1000;
         }        
      }
      function createBox(msg) {
         return '<div class="msg"><h3>' + msg + '</h3></div>';
      }
      if (!Ext.get('toast-div')) {
         msgCt = Ext.DomHelper.insertFirst(document.body, {
            id: 'toast-div'
         }, true);
      } else {
         msgCt = Ext.get('toast-div');
      }
      m = Ext.DomHelper.append(msgCt, createBox(info), true);
      m.hide();
      m.slideIn('t').ghost("t", {
         delay: delay, //TODO: el delay que dependa del tamaño del mensaje
         remove: true
      });
   }
   
});