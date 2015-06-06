define([
    'backbone',
    'models/btn',
    'models/game',
    'underscore',
    'models/user'
], function(
    Backbone,
    Btn,
    game,
    _,
    user
){

    var BtnCollection = Backbone.Collection.extend({
    	model: Btn,
        gameModel: game,
        initialize: function () {
            this.add([
                {
                    "color": "red",
                    "id": 1,
                    "disabled": false
                },
                {
                    "color": "green",
                    "id": 2,
                    "disabled": false
                },
                {
                    "color": "blue",
                    "id": 3,
                    "disabled": false
                },
                {
                    "color": "yellow",
                    "id": 4,
                    "disabled": false
                },
                {
                    "color": "pink",
                    "id": 5,
                    "disabled": false
                }])
        },
        check: function (data) {
            _.forEach(this.models, function (val) {
                if (val.get("id") == data.color1 ||
                    val.get("id") == data.color2 || 
                    data.turn != user.get("name")) {
                    val.set({"disabled": true});
                } else {
                    val.set({"disabled": false});
                }
            }, this)
            this.trigger("buttons:checked");
        }

    });

    return new BtnCollection();
});