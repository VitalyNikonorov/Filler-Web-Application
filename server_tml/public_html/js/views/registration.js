define([
    'backbone',
    'tmpl/registration',
    'models/user',
    'jquery'
], function(
    Backbone,
    tmpl,
    usermodel,
    $
){

    var View = Backbone.View.extend({
        model: usermodel,
        template: tmpl,
        className: "registration-view",
        events: {
            "change .signup-form__password": "validatePassword",
            "keyup .signup-form__password_confirm": "validatePassword",
            "click .submit": "submit",
            "change": "localstorage"
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
            $(".signup-form__email").val(localStorage["email"])
            $(".signup-form__name").val(localStorage["name"])
        },
        hide: function () {
            this.$el.hide();
        },
        validatePassword: function () {
            var password = (".signup-form__password")
                , confirm_password = $(".signup-form__password_confirm");
            if ( password.value != confirm_password.value ) {
                confirm_password.setCustomValidity("Passwords Don't Match");
            } else {
                confirm_password.setCustomValidity('');
            }
        },
        submit: function () {
            var result = $(".signup-form").serializeObject();
            this.model.signup(result)
            localStorage.removeItem("email");
            localStorage.removeItem("name");
        },
        localstorage: function() {
            localStorage["email"] = $(".signup-form__email").val()
            localStorage["name"] = $(".signup-form__name").val()
        }

    });

    return new View();
});