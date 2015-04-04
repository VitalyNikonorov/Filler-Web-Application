define([
    'backbone',
    'underscore',
], function(
    Backbone,
    _
){

    var View = Backbone.View.extend({
   
        add: function (view) {
            this.listenTo(view, "show", this.hide_and_show)
            this.views.push(view);
        },
        initialize: function () {
            this.views = [];
        },
        hide_and_show: function(arg) {
            _.forEach(this.views, function (arg) {
                arg.hide();
            });
            arg.$el.show();
        }
    });

    return new View();
});