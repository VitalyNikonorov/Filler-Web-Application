define([
    'backbone',
    'tmpl/btn',
    'jquery'
], function(
    Backbone,
    tmpl,
    $
){
    var View = Backbone.View.extend({
        template: tmpl,
        className: "game-button",
        events: {
            "click": "send"
        },
        initialize: function () {
            this.$el.addClass("game-button_"+this.model.get("id"));
            this.listenTo(this.model, "change", this.setDisabled)
            this.render();
        },
        render: function () {
            this.$el.html(this.template());
        },
        setDisabled: function() {
            if (this.model.get("disabled")) {
                this.$el.addClass("game-button_disabled");
            } else {
                this.$el.removeClass("game-button_disabled");
            }
        },
        send: function (){
            this.trigger("button:clicked", this.model.get("id"));
        }
    });
    return View;
});