Ext.define('iDynamicsFront.lib.Logger', {
    singleton         : true,
    alternateClassName: 'Logger',
    levels            : {
        all  : log4javascript.Level.ALL,
        debug: log4javascript.Level.DEBUG,
        info : log4javascript.Level.INFO,
        error: log4javascript.Level.ERROR,
        war  : log4javascript.Level.WARN,
        fatal: log4javascript.Level.FATAL
    },

    constructor: function () {
        var debug, params;
        this.log = log4javascript.getDefaultLogger();
        this.log.setLevel(log4javascript.Level.OFF);
        params = window.location.href.match(/\?(.*)#|\?(.*)/);
        if (params) {
            params = params[1] || params[2];
            debug = Ext.Object.fromQueryString(params).debug;
            if (debug !== undefined) {
                this.log.setLevel(Ext.isEmpty(debug) ? this.levels.all : this.levels[debug.toLowerCase()]);
            } else {
                this.log.setLevel(log4javascript.Level.OFF);
            }
        }
    },

    debug: function () {
        this.log.debug.apply(this.log, arguments);
    },

    trace: function () {
        this.log.trace.apply(this.log, arguments);
    },

    error: function () {
        this.log.error.apply(this.log, arguments);
    },

    fatal: function () {
        this.log.fatal.apply(this.log, arguments);
    },

    info: function () {
        this.log.info.apply(this.log, arguments);
    },

    warn: function () {
        this.log.warn.apply(this.log, arguments);
    }
});

