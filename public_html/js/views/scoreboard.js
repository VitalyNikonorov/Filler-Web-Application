define([
    'backbone',
    'tmpl/scoreboard',
    'collections/scores'
], function(
    Backbone,
    tmpl,
    Scores
){
    var View = Backbone.View.extend({
        template: tmpl,
        scores: Scores,
        className: "scoreboard-view",
        initialize: function () {
            this.render();
            this.hide();
        },
        render: function () {
            this.scores.fetch();
            this.$el.html( this.template(this.scores.toJSON()));
        },
        show: function () {
            this.render();
            this.trigger('show', this);
            this.$el.show();
        },
        hide: function () {
            this.$el.hide();
        }
    });
    return new View();
});