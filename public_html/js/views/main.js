define([
    'backbone',
    'tmpl/main',
    'models/user'
], function(
    Backbone,
    tmpl,
    userm
){

    var View = Backbone.View.extend({
        model: userm,
        template: tmpl,
        className: "main-view",
        events: {
            "click .button-logout": "logout"
        },
        initialize: function () {
            this.render();
            this.hide();
        },
        render: function () {
            this.$el.html( this.template() );
        },
        show: function () {
            this.trigger('show', this);
            this.$el.show();
        },
        hide: function () {
            this.$el.hide();
        },
        logout: function () {
            this.model.logout();
        }

    });

    return new View();
});