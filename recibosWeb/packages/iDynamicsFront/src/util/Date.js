Ext.define('iDynamicsFront.util.Date', {
    
    singleton: true,
    
    patterns : {
	    ISO8601Long:"Y-m-d H:i:s",
	    ISO8601Short:"Y-m-d",
	    ShortDate: "n/j/Y",
	    LongDate: "l, F d, Y",
	    FullDateTime: "l, F d, Y g:i:s A",
	    MonthDay: "F d",
	    ShortTime: "g:i A",
	    LongTime: "g:i:s A",
	    SortableDateTime: "Y-m-d\\TH:i:s",
	    UniversalSortableDateTime: "Y-m-d H:i:sO",
	    YearMonth: "F, Y"
	},

    getUTCDate: function (date){
      if (date==null){
         date=new Date();
      } 
      return new Date(date.toUTCString().substring(0,25));
    },
    
   // Métodos de utilidad general ------------------------------
   
   /*getMockupStore: function ( items ){
      return  Ext.create('Ext.data.Store', {
         storeId:'simpsonsStore',
         fields:[ {name:'fInicProg',type:'date'} ],
         data:{'items':items},
         proxy: {
            type: 'memory',
            reader: {
                  type: 'json',
                  rootProperty: 'items'
            }
         }
      }); 
   },*/

   //msegEnUnDia: (1000 * 60 * 60 * 24 ),
   diasDeDiferencia:function (fInic, fFin){
      //Se crea porque Ext.Date.diff va mal
      //Ext.Date.diff( Ext.Date.parse("2015-03-23","Y-m-d"), Ext.Date.parse("2015-03-29","Y-m-d") , Ext.Date.DAY )
      //   y   Ext.Date.diff( Ext.Date.parse("2015-03-23","Y-m-d"), Ext.Date.parse("2015-03-30","Y-m-d") , Ext.Date.DAY )
      //   Retornan los mismo (6) si hay un cambio de hora el dia 30 

      //Le voy a añadir el offset horario para evitar este problema (del 29 al 30 no pasa un dia entero si no 23 horas, por eso se vuelve loco)
      var f1 = Ext.Date.add(fInic, Ext.Date.MINUTE, -fInic.getTimezoneOffset( )),
         f2 = Ext.Date.add(fFin, Ext.Date.MINUTE, -fFin.getTimezoneOffset( ));
      return  Ext.Date.diff( f1, f2, Ext.Date.DAY )
      
      //Alternativa: 
      //var utc1 = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate());
      //var utc2 = Date.UTC(b.getFullYear(), b.getMonth(), b.getDate());
      //return Math.floor((utc2 - utc1) / _MS_PER_DAY);      
   },
   
   horasDeDiferencia:function (fInic, fFin){
      var f1 = Ext.Date.add(fInic, Ext.Date.MINUTE, -fInic.getTimezoneOffset( )),
         f2 = Ext.Date.add(fFin, Ext.Date.MINUTE, -fFin.getTimezoneOffset( ));
      return  Ext.Date.diff( f1, f2, Ext.Date.HOUR )
   },
   
   esMismoDia: function (f1, f2){ //f1==f2? Compara dia, mes y año pero NO la hora
      if (f1.getDate()!=f2.getDate() || f1.getMonth()!=f2.getMonth() ||  f1.getFullYear()!=f2.getFullYear() ){
        return false;
      }
      return true;
   },
   
   esMismoDiaOAnterior: function(f1, f2){ //f1<=f2? Compara dia, mes y año pero NO la hora
      return this.diasDeDiferencia(f1,f2)>=0
   }

});