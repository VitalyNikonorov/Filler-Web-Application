define([
    'backbone',
    'models/score'
], function(
    Backbone,
    Score
){

    var UserCollection = Backbone.Collection.extend({
    	model: Score,
        comparator: function(score) {
            return -score.get("score")
        }
    });

    return new UserCollection();
});