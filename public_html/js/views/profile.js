define([
    'backbone',
    'tmpl/profile',
    'jquery',
    'models/user'
], function(
    Backbone,
    tmpl,
    $,
    userm
){

    var View = Backbone.View.extend({
        model: userm,
        template: tmpl,
        className: "profile-view",
        initialize: function () {
            this.render();
            this.hide();
        },
        render: function (args) {
            if (typeof(args)==='undefined') args = {name:'NO NAME'};
            this.$el.html( this.template(args) );
        },
        show: function () {
            this.trigger('show', this);
            this.render(this.model.toJSON())
            this.$el.show();
        },
        hide: function () {
            this.$el.hide();
        }

    });

    return new View();
});