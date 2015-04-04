define([
    'backbone'
], function(
    Backbone
){

    var User = Backbone.Model.extend({
    	defaults: {
    		"Name": "",
    		"Score": 0
    	}
    });

    return User;
});