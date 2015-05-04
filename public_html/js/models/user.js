define([
    'backbone',
    'user_sync'
], function(
    Backbone,
    usync
){

    var User = Backbone.Model.extend({
    	sync: usync, 
   
        initialize: function () {
            this.fetch();
        }, 
    	signup: function(data) {
            this.logout()
    		this.set(data);
    		this.save();
    	}, 
        login: function (data) {
            data.logining=true;
            this.set(data);
            this.save();
        },
        logout: function () {
            this.destroy();
        }
    });

    return new User();
});