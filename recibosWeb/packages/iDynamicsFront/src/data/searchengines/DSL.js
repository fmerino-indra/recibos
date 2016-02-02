/**
 * Created by mavegaa on 02/06/2015.
 */
Ext.define('iDynamicsFront.data.searchengines.DSL', {
    singleton         : true,
    alternateClassName: 'DSLQuery',
    getAll            : function () {
        return ejs.Request()
            .query(ejs.MatchAllQuery());
    }

});