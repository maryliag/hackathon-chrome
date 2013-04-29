var server_path = 'ws://localhost:8080';
// var server_path = 'ws://23.22.100.228:8080';
var ws;

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

setInterval(function() {
	if (ws != null) {
		chrome.storage.local.get(idKey, function(items) {
			var id = items[idKey];
			ws.send(id);
		});
	}
}, 5000);

function cancelNotification(notification) {
	if (notification != null) {
		setTimeout(function() {
			notification.cancel();
		}, 3500);
	}
}

function initWebSocket(id) {
	ws = new WebSocket(server_path);

	ws.onopen = function(event) {
		console.log(id);
		ws.send(id);
	}

	ws.onmessage = function (event) {
		notification = webkitNotifications.createNotification('', 'Alert!', unescape(event.data));
		notification.ondisplay = cancelNotification(notification);
		notification.show();
	};
}