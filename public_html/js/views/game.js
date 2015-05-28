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
            // this.render();
            this.$el.html( this.template() );
            this.boardDOM = this.$(".board");
            this.buttonsDOM = this.$(".buttons");
            this.buttonsDOM.append(this.buttons.$el)
            this.boardDOM.html(this.board.render().$el)
            this.hide();
        },
        stop: function () {
            $(".ready").show();  
        },
        ready: function () {
            $.ajax({
                method: 'POST',
                url: "/game.html",
                dataType: 'json',
                context: this
            }).done(function() {
                this.game.connect();
            }).fail(function(data) {
                this.game.connect();
            });
        },
        render: function () {
            
        },
        step: function () {
            $(".ready").hide();
            this.boardDOM.html(this.board.render().$el);
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