define([
    'backbone',
    'tmpl/game'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({

        template: tmpl,
        className: "game-view",

        initialize: function () {
            this.render();
            this.hide();
        },
        render: function () {
            this.$el.html( this.template({'score': "one billion"}) );
        },
        show: function () {
            this.trigger("show", this);
            this.$el.show();
        },
        hide: function () {
            this.$el.hide();
        }
    });

    return new View();
});