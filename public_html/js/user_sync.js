define([
    'jquery',
	'backbone',
    'sender'
], function($, Backbone, Sender) {
	var sender = new Sender('api/v1/auth')

	return function(method, model, options) {
		if (model.has('logining')) {
			method = "update"
		}
		console.log(method)
		var method_map = {
			'create': {
				send: function() {
					var resp = sender.send("/signup", "post", model.toJSON());
					model.clear();
					if (resp.status == 200) {
						model.set({"id": resp.body.id,
								   "name": resp.body.name,
								   "email": resp.body.email,
									"is_logined": true});
					} else {
						console.log(resp.status)
						console.log(resp.error)
					}
				}
			},
			'read': {
				send: function() {
					console.log(model.toJSON())
				}

			},
			'update': {
				send: function () {
					var resp;
					resp = sender.send("/signin", "post", model.toJSON())
					if (resp.status == 200) {
						model.clear()
						model.set({"id": resp.body.id,
	                        'name': resp.body.name,
	                        'email': resp.body.email || "",
	                        'is_logined': true
	                    });
                	}
				}
			},
			'delete': {
				send: function () {
					var sender = new Sender('')
					var resp;
					model.clear();
					resp = sender.send("/logout", "post", model.toJSON())
				}
			}
		}
		return method_map[method].send()
	}
})