define([
	'jquery'
], function($) {
	var Sender = function(base_url) {
		this.url = base_url || "";
		this._send = function (method, params) {
			console.log(params||{})
			var result;
			$.ajax({
				async: false,
				context: this,
				type: method,
                contentType: "application/json; charset=utf-8",
                url: this.send_url,
                data: JSON.stringify(params||{}),
                dataType: 'json'
			}).done(function (data){
				result = data
			})
			return result

		}
		this.send = function(url, method, params) {
			this.send_url = this.url + url;
			return this._send(method, params)
		}
	}
	
	return Sender
})
