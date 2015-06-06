define([
    'backbone',
    'models/game'
], function(
    Backbone,
    gameModel
){

    var Board = Backbone.Model.extend({
        initialize: function () {
            this.set({
                "board": [[]],
                "width": 0,
                "height": 0
            })

            this.set({
                "playing": false
            })
       
        },
        stop: function () {
            this.clear();
            this.set({
                "board": [[]],
                "width": 0,
                "height": 0,
                "playing": false
            });
        },
        step: function (data) {
            this.set({
                "board": data.field,
                "user1": data.user1,
                "user2": data.user2,
                "score1": data.score1,
                "score2": data.score2
            });
            this.trigger("board:updated");
        },
        setBoard: function (data) {
            this.set({
                "board": data.field,
                "width": data.field[0].length,
                "height": data.field.length,
                "playing": true,
                "user1": data.user1,
                "user2": data.user2,
            });
            this.trigger("board:updated");
        }
    });

    return new Board();
});