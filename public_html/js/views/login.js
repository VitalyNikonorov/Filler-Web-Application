define([
    'backbone',
    'tmpl/login',
    'jquery'
], function(
    Backbone,
    tmpl,
    $
){

    var View = Backbone.View.extend({

        template: tmpl,
        className: "login-view",
        events: {'submit': 'submit'},
        submit: function (event) {
            event.preventDefault()
            var name = $(".login-form__email").val();
            var pass = $(".login-form__password").val();
            var res = {"name": name, "password": pass}
            $.post('api/v1/auth/signin', JSON.stringify(res));
                $//.ajax({
                   //                 url:'api/v1/auth/signin'
                     //               , type:'POST'
                       //             , data: $.toJSON(res)
                        //        });
                setTimeout("document.location.href='/#game'", 200);
            

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
        },
        hide: function () {
            this.$el.hide();
        }

    });

    return new View();
});