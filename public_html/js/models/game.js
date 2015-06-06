define([
    'backbone',
    'collections/btns',
    'models/board'
], function(
    Backbone,
    buttonsCollection,
    boardModel
){
    var self;
    var Game = Backbone.Model.extend({
        board: boardModel,
        buttons: buttonsCollection,
        initialize: function () {
            self = this;
            this.listenTo(this.board, "board:updated", this.gameUpdated);
            this.listenTo(this.buttons, "buttons:checked", this.gameUpdated);
        },
        gameUpdated: function () {
            this.trigger("game:updated");
        },
        connect: function () {
            if (this.socket === undefined) {
                this.socket = new WebSocket("ws://localhost:8080/gameplay");
            }
            this.socket.onopen = this.open;
            this.socket.onmessage = this.message;
            this.socket.onclose = this.close;         
        },
        open: function() {
            self.trigger("socket:open");
        },
        close: function() {
            self.trigger("socket:close")
            self.socket = undefined;
        },
        message: function(msg) {
            var data = JSON.parse(msg.data);
            if (data.status == "start") {
                self.trigger("game:start")
                self.board.setBoard(data);
                self.buttons.check(data);
            } else if (data.status == "finish") {
                self.board.stop();
                self.trigger("game:stop", data);

            } else if (data.status !== undefined) {
                self.board.step(data);
                self.buttons.check(data);
                self.trigger("game:move");
            }
        },
        step: function(color) {
            self.socket.send(JSON.stringify({"color": color}))
        }
     });

    return new Game();
});