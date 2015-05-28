define([
    'jquery',
	'backbone',
], function($, Backbone) {
	return function(method, model, options) {
		if (model.has('logining')) {
			method = "update"
		}
		var method_map = {
			'create': {
				send: function() {
					$.ajax({
						context: this,
						type: "POST",
		                contentType: "application/json; charset=utf-8",
		                url: "api/v1/auth/signup",
		                data: JSON.stringify(model.toJSON()),
		                dataType: 'json'
					}).done(function (data){
						if (data.status == 200) {
							model.login(data);
							model.trigger("user:load");
						} else {
							model.clear();
							model.trigger("user:error:create", data)
						}
					})
				}
			},
			'read': {
				send: function() {
					$.ajax({
						type: "GET",
						url: "api/v1/auth/check",
						dataType: "json",
						async: false
					}).done(function (data){
						if (data.status == 200) {
							model.clear();
							model.set(data.body);
							model.trigger("user:load")
						} else {
							model.clear()
						}
					})
				}

			},
			'update': {
				send: function () {
					$.ajax({
						context: this,
						type: "POST",
						contentType: "application/json; charset=utf-8",
						url: "api/v1/auth/signin",
						data: JSON.stringify(model.toJSON()),
						dataType: "json"
					}).done(function (data) {
						if (data.status == 200) {
							model.clear();
							model.set(data.body);
							model.trigger("user:load")
						} else {
							model.clear();
							model.trigger("user:error:login", data);
						}
					});
				}
			},
			'delete': {
				send: function () {
					$.ajax({
						type: "POST",
						url: "/logout"
					}).done(function(data){
						model.clear();
						model.trigger("user:logout");
					})
				}
			}
		}
		return method_map[method].send()
	}
})