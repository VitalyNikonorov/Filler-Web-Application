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

            // this.set({
            //     'board': [[1,2,3,4,5,4,2,3,1],
            //               [5,4,2,1,2,3,4,2,5],
            //               [2,3,4,1,3,4,5,3,2],
            //               [2,3,5,5,1,1,2,3,4],
            //               [1,2,4,5,2,3,3,2,1],
            //               [4,2,1,2,3,4,5,5,3],
            //               [5,5,5,3,2,3,1,3,4],
            //               [3,4,5,5,4,3,1,2,3],
            //               [3,4,5,2,3,1,3,3,1]],
            //     'width': 9,
            //     'height': 9
            // })            
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
                "playing": true
            });
            this.trigger("board:updated");
        }
    });

    return new Board();
});