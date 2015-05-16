define([
    'jquery',
	'backbone',
	'underscore'
], function($, Backbone, _) {
	return function(method, collection, options) {
		var method_map = {
			'read': function () {
				$.ajax({
					url: '/api/v1/scores',
					dataType: 'json',
					method: 'GET',
					async: false
				}).done(function(data) {
					if (data.status == 200) {
						var result = [];
						_.forEach(data.users, function (val) {
							result.push({
								"name": val[0],
								"score": val[1]
							})	
						})
						collection.reset(result);
						console.log(collection.toJSON())
					}
				})
			}
		}
		

		return method_map[method]()
	}
})