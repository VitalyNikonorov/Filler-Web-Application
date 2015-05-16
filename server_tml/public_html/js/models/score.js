define([
    'backbone'
], function(
    Backbone
){

    var Score = Backbone.Model.extend({
    	initialize: function () {
    		this.name = "";
    		this.id = "";
    		this.score = "";
    	}
    });

    return Score;
});