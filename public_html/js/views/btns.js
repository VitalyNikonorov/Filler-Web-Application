define([
    'backbone',
    'tmpl/btns',
    'views/btn',
    'collections/btns',
    'jquery',
    'underscore'
], function(
    Backbone,
    tmpl,
    btnView,
    btnCollection,
    $,
    _
){
    var View = Backbone.View.extend({
        template: tmpl,
        className: "game-button-container",
        buttons: btnCollection,
        initialize: function () {
            this.views = [];
            _.forEach(this.buttons.models, function(val) {
                var view = new btnView({"model": val});
                this.listenTo(view, "button:clicked", this.recive)
                this.views.push(view)
            }, this)
            this.render();
        },
        recive: function (data) {
            this.trigger("color:selected", data);
        },
        render: function () {
            this.$el.html(this.template());
            _.forEach(this.views, function(val) {
                this.$el.append(val.$el);
            }, this)
        }
    });
    return new View ();
});