define([
    'backbone',
    'tmpl/board',
    'models/board',
    'jquery'
], function(
    Backbone,
    tmpl,
    boardModel,
    $
){

    var View = Backbone.View.extend({
        template: tmpl,
        className: "game-board",
        board: boardModel,
        initialize: function () {

        },
        render: function () {
            this.$el.html(this.template(this.board.toJSON()));
            return this;
        }
    });

    return new View();
});