define([
    'backbone',
    'tmpl/login',
    'jquery',
    'models/user'
], function(
    Backbone,
    tmpl,
    $,
    usermodel
){

    var View = Backbone.View.extend({
        model: usermodel,
        template: tmpl,
        className: "login-view",
        events: {'click .submit': 'submit'},
        submit: function (event) {
            event.preventDefault();
            var result = $(".login-form").serializeObject();
            this.model.login(result);
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
        }

    });

    return new View();
});