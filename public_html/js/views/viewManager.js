define([
    'backbone',
    'underscore',
], function(
    Backbone,
    _
){

    var View = Backbone.View.extend({
   
        add: function (views) {
            this.views = views;
            var page = $(".page");
            _.forEach(this.views, function(val, i) {
                page.append(val.$el)
                this.listenTo(val, "show", this.hide_and_show);
            }, this)
        },
        initialize: function () {
            this.views = {};
        },
        hide_and_show: function(arg) {
            _.forEach(this.views, function (arg) {
                arg.hide();
            });
            
        }
    });

    return new View();
});