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
            this.scores.add([
                {Name: "Name1", score: 25},
                {Name: "Name2", score: 40},
                {Name: "Name3", score: 27},
                {Name: "Name4", score: 28},
                {Name: "Name5", score: 29},
                {Name: "Name6", score: 50},
                {Name: "Name7", score: 31},
                {Name: "Name8", score: 32},
                {Name: "Name9", score: 33}
            ]);
            this.render();
            this.hide();
        },
        render: function () {
            this.$el.html( this.template(this.scores.toJSON()));
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