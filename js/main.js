var idKey = 'id';
// var server_path = 'ws://localhost:8080';
var server_path = 'ws://23.22.100.228:8080';
var ws;
var notification;

chrome.app.runtime.onLaunched.addListener(function() {
	init();
});

chrome.runtime.onInstalled.addListener(function() { 
	var id = guid();
	chrome.storage.local.set({'id': id});

	chrome.app.window.create('html/first_run.html', {
		'width': 400,
		'height': 500
	});

	init();
});

function init() { 
	chrome.storage.local.get(idKey, function(items) {
		var id = items[idKey];

		if (id == null) {
			id = guid();
			chrome.storage.local.set({idKey: id});
		}

		initWebSocket(id);
	});
}

function initWebSocket(id) {
	ws = new WebSocket(server_path);

	ws.onopen = function(event) {
		console.log(id);
		ws.send(id);
	}

	ws.onmessage = function (event) {
		if (notification != null) {
			notification.cancel();
		}

		notification = webkitNotifications.createNotification('', 'Alert!', unescape(event.data));
		notification.show();
	};
}

setInterval(function() {
	if (ws != null) {
		chrome.storage.local.get(idKey, function(items) {
			var id = items[idKey];
			ws.send(id);
		});
	}
}, 5000);