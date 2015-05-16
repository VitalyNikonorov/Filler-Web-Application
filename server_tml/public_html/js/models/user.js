define([
    'backbone',
    'syncs/user_sync'
], function(
    Backbone,
    usync
){

    var User = Backbone.Model.extend({
    	sync: usync, 
        initialize: function () {
            this.fetch();
            this.trigger("socket:open");
        }, 
    	signup: function(data) {
            this.logout();
    		this.set(data);
    		this.save();
    	}, 
        login: function (data) {
            data.logining=true;
            this.set(data);
            this.save();
        },
        logout: function () {
            console.log("destroy")
            this.destroy();
        }
    });

    return new User();
});