define([
    'backbone',
    'tmpl/game',
    'models/game',
    'jquery'
], function(
    Backbone,
    tmpl,
    game,
    $
){

    var View = Backbone.View.extend({

        template: tmpl,
        className: "game-view",
        game: game,
        events: {
            "click .ready": 'ready',
            "click .game-button_1": 'click_red',
            "click .game-button_2": 'click_green',
            "click .game-button_3": 'click_blue',
            "click .game-button_4": 'click_yellow',
            "click .game-button_5": 'click_pink'
        },
        ready: function () {
            $.ajax({
                method: 'POST',
                url: "/game.html",
                dataType: 'json',
                context: this
            }).done(function() {
                this.game.start();
                console.log("gamestart");
            }).fail(function(data) {
                console.log("fail");
                console.log(data);
                this.game.start();
                console.log("gamestart");
            });
        },
        click_red: function () {
            this.game.send_color(1)
        },
        click_green: function () {
            this.game.send_color(2)
        },
        click_blue: function () {
            this.game.send_color(3)
        },
        click_yellow: function () {
            this.game.send_color(4)
        },
        click_pink: function () {
            this.game.send_color(5)
        },
        initialize: function () {
            this.render();
            this.hide();
            this.listenTo(this.game, "socket:open", this.start_game);
            this.listenTo(this.game, "socket:close", this.end_game);
            this.listenTo(this.game, "socket:message", this.message);
        },
        render: function (data) {
            if (data === undefined) {
                data = {
                'score': "one billion", 
                'field': [[1,2,3,4,5,4,2,3,1],
                          [5,4,2,1,2,3,4,2,5],
                          [2,3,4,1,3,4,5,3,2],
                          [2,3,5,5,1,1,2,3,4],
                          [1,2,4,5,2,3,3,2,1],
                          [4,2,1,2,3,4,5,5,3],
                          [5,5,5,3,2,3,1,3,4],
                          [3,4,5,5,4,3,1,2,3],
                          [3,4,5,2,3,1,3,3,1]],
                'h':9,
                'w':9}
            }
            this.$el.html( this.template(data) );
        },
        show: function () {
            this.trigger("show", this);
            this.$el.show();
        },
        hide: function () {
            this.$el.hide();
        },
        start_game: function() {
            //alert(this.game.data);
        },
        end_game: function() {

        },
        message: function() {
            console.log("message func")
            console.log(this.game.data)
            console.log(this.game.data.status)
            if (this.game.data.status == "start") {
                var obj = {
                    'score': 0,
                    'field': this.game.data.field,
                    'h': this.game.data.field.length,
                    'w': this.game.data.field[0].length
                }
                console.log(obj)
                this.render(obj)
            } 
            if (this.game.data.status == "move") {
                this.render({
                    'myScore': this.game.data.myScore,
                    'enemyScore': this.game.data.enemyScore,
                    'field': this.game.data.field,
                    'h': this.game.data.field.length,
                    'w': this.game.data.field[0].length
                })
            }
            if (this.game.data.status == "finish") {
                this.game.ws.close();
                var str;
                if (this.game.data.win) {
                    str = "game over winner";
                } else {
                    str = "game over looser";
                }
                alert(str)
                
            }
        }
    });

    return new View();
});