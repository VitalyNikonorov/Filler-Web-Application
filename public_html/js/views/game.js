define([
    'backbone',
    'tmpl/game',
    'models/game',
    'views/board',
    'views/btns',
    'jquery'
], function(
    Backbone,
    tmpl,
    gameModel,
    boardView,
    buttonsView,
    $
){

    var View = Backbone.View.extend({

        template: tmpl,
        className: "game-view",
        board: boardView,
        game: gameModel,
        buttons: buttonsView,
        events: {
            "click .ready": 'ready'
        },
        initialize: function () {
            this.listenTo(this.game, "game:updated", this.step)
            this.listenTo(this.buttons, "color:selected", this.game.step)
            this.listenTo(this.game, "game:stop", this.stop)
            this.render();
            
            //this.hide();
            this.board.hide();
            this.buttons.hide();
        },
        stop: function (msg) {
            if(msg["win"]) {
                alert("win");
            } else {
                alert("lose")
            }
            $(".ready").show(); 
            this.board.hide(); 
            this.buttons.hide();
        },
        ready: function () {
            alert("ready")
            $.ajax({
                method: 'POST',
                url: "/game.html",
                dataType: 'json',
                context: this
            }).done(function(data) {
                console.log(data)
                this.game.connect();
            }).fail(function(data) {
                console.log(data);
                this.game.connect();
            });
        },
        render: function () {
            this.$el.html( this.template() );
            this.boardDOM = this.$(".board");
            this.buttonsDOM = this.$(".buttons");
            this.buttonsDOM.html(this.buttons.$el)
            this.boardDOM.html(this.board.render().$el);
        },
        step: function () {
            $(".ready").hide();
            this.board.show();
            this.buttons.show();
            this.board.render();
            
        },
        show: function () {
            this.trigger("show", this);
            this.$el.show();
        },
        hide: function () {
            this.$el.hide();
        }
    });

    return new View();
});