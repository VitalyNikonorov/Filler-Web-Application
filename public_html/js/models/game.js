define([
    'backbone'
], function(
    Backbone
){
    var self;
    var Game = Backbone.Model.extend({
    	initialize: function () {
            this.data = {};
            console.log("gamemodel")
            console.log(this)
            self = this;
    	},
        start: function () {
            this.ws = new WebSocket("ws://91.215.138.197:8080/gameplay");
            this.ws.onopen = this.open;
            this.ws.onmessage = this.message;
            this.ws.onclose = this.console;
            console.log("WebSocket opened");
            console.log(this.ws)
            
        },
        open: function() {
            console.log("gamemodel")
            console.log(self)
            console.log("gamemodel stop")
            console.log("open");
            console.log();
            self.trigger('socket:open');
        },
        close: function() {
            console.log("close");
            console.log("data");
            self.trigger("socket:close")
        },
        message: function(data) {
            //console.log("message");
            //console.log(data);
            self.data = JSON.parse(data.data);
            self.trigger("socket:message");
        },
        send_color: function(color) {
            this.ws.send(JSON.stringify({"color": color}))
        }
     });

    return new Game();
});