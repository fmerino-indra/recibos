Ext.define('iDynamicsFront.selectors.DatePickerZulu', {
   extend : 'Ext.picker.Date',
   alias : 'widget.datepickerzulu',
   requires : ["iDynamicsFront.util.Date"],

   monthYearText :undefined,
   
   initComponent : function(){
      var me = this , clearTime = Ext.Date.clearTime;

      me.selectedCls = me.baseCls + '-selected';
      me.disabledCellCls = me.baseCls + '-disabled';
      me.prevCls = me.baseCls + '-prevday';
      me.activeCls = me.baseCls + '-active';
      me.nextCls = me.baseCls + '-prevday';
      me.todayCls = me.baseCls + '-today';
      me.dayNames = me.dayNames.slice(me.startDay).concat(me.dayNames.slice(0, me.startDay));

      me.listeners = Ext.apply(me.listeners || {}, {
         mousewheel : {
            element : 'eventEl',
            fn : me.handleMouseWheel,
            scope : me
         },
         click : {
            element : 'eventEl',
            fn : me.handleDateClick,
            scope : me,
            delegate : 'a.' + me.baseCls + '-date'
         }
      });
      this.callParent();

      //me.value = me.value ? clearTime(me.value, true) : clearTime(new Date());
      me.value = me.value ? clearTime(me.value, true) : clearTime( Ux.utils.Date.getUTCDate() ); //ZULU

      me.addEvents(
      /**
       * @event select
       * Fires when a date is selected
       * @param {Ext.picker.Date} this DatePicker
       * @param {Date} date The selected date
       */
      'select');

      me.initDisabledDays();
   },

   beforeRender : function(){
      /*
       * days array for looping through 6 full weeks (6 weeks * 7 days)
       * Note that we explicitly force the size here so the template creates
       * all the appropriate cells.
       */

      var me = this,
         days = new Array(me.numDays),
         //today = Ext.Date.format(new Date(), me.format);
         today = Ext.Date.format( Ux.utils.Date.getUTCDate() , me.format); //ZULU

      // If there's a Menu among our ancestors, then add the menu class.
      // This is so that the MenuManager does not see a mousedown in this Component as a document mousedown, outside the Menu
      if (me.up('menu')) {
         me.addCls(Ext.baseCSSPrefix + 'menu');
      }

      me.monthBtn = new Ext.button.Split({
         ownerCt : me,
         ownerLayout : me.getComponentLayout(),
         text : '',
         tooltip : me.monthYearText,
         listeners : {
            click : me.showMonthPicker,
            arrowclick : me.showMonthPicker,
            scope : me
         }
      });

      if (this.showToday) {
         me.todayBtn = new Ext.button.Button({
            ownerCt : me,
            ownerLayout : me.getComponentLayout(),
            text : Ext.String.format(me.todayText, today),
            tooltip : Ext.String.format(me.todayTip, today),
            tooltipType : 'title',
            handler : me.selectToday,
            scope : me
         });
      }

      me.callParent();

      Ext.applyIf(me, {
         renderData : {}
      });

      Ext.apply(me.renderData, {
         dayNames : me.dayNames,
         showToday : me.showToday,
         prevText : me.prevText,
         nextText : me.nextText,
         days : days
      });
   },

   selectToday : function(){
      var me = this , btn = me.todayBtn , handler = me.handler;

      if (btn && !btn.disabled) {
         //me.setValue(Ext.Date.clearTime(new Date()));
         me.setValue(Ext.Date.clearTime( Ux.utils.Date.getUTCDate() )); //ZULU
         me.fireEvent('select', me, me.value);
         if (handler) {
            handler.call(me.scope || me, me, me.value);
         }
         me.onSelect();
      }
      return me;
   },
   
   fullUpdate: function(date){
        var me = this,
            cells = me.cells.elements,
            textNodes = me.textNodes,
            disabledCls = me.disabledCellCls,
            eDate = Ext.Date,
            i = 0,
            extraDays = 0,
            visible = me.isVisible(),
            sel = +eDate.clearTime(date, true),
            //today = +eDate.clearTime(new Date()), 
            today = +eDate.clearTime( Ux.utils.Date.getUTCDate() ), //ZULU
            min = me.minDate ? eDate.clearTime(me.minDate, true) : Number.NEGATIVE_INFINITY,
            max = me.maxDate ? eDate.clearTime(me.maxDate, true) : Number.POSITIVE_INFINITY,
            ddMatch = me.disabledDatesRE,
            ddText = me.disabledDatesText,
            ddays = me.disabledDays ? me.disabledDays.join('') : false,
            ddaysText = me.disabledDaysText,
            format = me.format,
            days = eDate.getDaysInMonth(date),
            firstOfMonth = eDate.getFirstDateOfMonth(date),
            startingPos = firstOfMonth.getDay() - me.startDay,
            previousMonth = eDate.add(date, eDate.MONTH, -1),
            longDayFormat = me.longDayFormat,
            prevStart,
            current,
            disableToday,
            tempDate,
            setCellClass,
            html,
            cls,
            formatValue,
            value;

        if (startingPos < 0) {
            startingPos += 7;
        }

        days += startingPos;
        prevStart = eDate.getDaysInMonth(previousMonth) - startingPos;
        current = new Date(previousMonth.getFullYear(), previousMonth.getMonth(), prevStart, me.initHour);

        if (me.showToday) {
            //tempDate = eDate.clearTime(new Date());
         tempDate = eDate.clearTime( Ux.utils.Date.getUTCDate() ); //ZULU
            disableToday = (tempDate < min || tempDate > max ||
                (ddMatch && format && ddMatch.test(eDate.dateFormat(tempDate, format))) ||
                (ddays && ddays.indexOf(tempDate.getDay()) != -1));

            if (!me.disabled) {
                me.todayBtn.setDisabled(disableToday);
                me.todayKeyListener.setDisabled(disableToday);
            }
        }

        setCellClass = function(cell){
            value = +eDate.clearTime(current, true);
            cell.title = eDate.format(current, longDayFormat);
            // store dateValue number as an expando
            cell.firstChild.dateValue = value;
            if(value == today){
                cell.className += ' ' + me.todayCls;
                cell.title = me.todayText;
            }
            if(value == sel){
                cell.className += ' ' + me.selectedCls;
                me.fireEvent('highlightitem', me, cell);
                if (visible && me.floating) {
                    Ext.fly(cell.firstChild).focus(50);
                }
            }
            // disabling
            if(value < min) {
                cell.className = disabledCls;
                cell.title = me.minText;
                return;
            }
            if(value > max) {
                cell.className = disabledCls;
                cell.title = me.maxText;
                return;
            }
            if(ddays){
                if(ddays.indexOf(current.getDay()) != -1){
                    cell.title = ddaysText;
                    cell.className = disabledCls;
                }
            }
            if(ddMatch && format){
                formatValue = eDate.dateFormat(current, format);
                if(ddMatch.test(formatValue)){
                    cell.title = ddText.replace('%0', formatValue);
                    cell.className = disabledCls;
                }
            }
        };

        for(; i < me.numDays; ++i) {
            if (i < startingPos) {
                html = (++prevStart);
                cls = me.prevCls;
            } else if (i >= days) {
                html = (++extraDays);
                cls = me.nextCls;
            } else {
                html = i - startingPos + 1;
                cls = me.activeCls;
            }
            textNodes[i].innerHTML = html;
            cells[i].className = cls;
            current.setDate(current.getDate() + 1);
            setCellClass(cells[i]);
        }

        me.monthBtn.setText(Ext.Date.format(date, me.monthYearFormat));
    }
   
   
});