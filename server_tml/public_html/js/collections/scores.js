define([
    'backbone',
    'models/score',
    'syncs/score_sync'
], function(
    Backbone,
    Score,
    score_sync
){

    var UserCollection = Backbone.Collection.extend({
    	model: Score,
        sync: score_sync,
        comparator: function(score) {
            return -score.get("score")
        },
        
    });

    return new UserCollection();
});