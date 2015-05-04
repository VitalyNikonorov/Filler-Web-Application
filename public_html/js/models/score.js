define([
    'backbone'
], function(
    Backbone
){

    var Score = Backbone.Model.extend({
    	defaults: {
    		"Name": "",
    		"Score": 0
    	}
    });

    return Score;
});