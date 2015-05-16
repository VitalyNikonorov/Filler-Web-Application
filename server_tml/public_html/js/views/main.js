define([
    'backbone',
    'tmpl/main',
    'models/user'
], function(
    Backbone,
    tmpl,
    usermodel
){

    var View = Backbone.View.extend({
        model: usermodel,
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
            var name = this.model.get("name");
            console.log(name)
            if (name === undefined) {

                console.log("undefined")
                name = false;
            }
            this.$el.html(this.template(name));
        },
        show: function () {
            this.trigger('show', this);
            this.$el.show();
        },
        hide: function () {
            this.$el.hide();
        },
        logout: function () {
            console.log('logout')
            this.model.logout();
        }

    });

    return new View();
});