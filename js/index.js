// var server_path = 'ws://localhost:8080';
var server_path = 'ws://23.22.100.228:8080';

var ws = new WebSocket(server_path);
ws.onmessage = function (event) {
	notification = webkitNotifications.createNotification('', 'Alert!', event.data);
	notification.ondisplay = cancelNotification(notification);

	notification.show();
};

setInterval(function() {
	if (ws != null && ws.readyState != WebSocket.OPEN) {
		ws = new WebSocket(server_path);
		console.log('reconnected');
	}
}, 10000);

function cancelNotification(notification) {
	if (notification != null) {
		setTimeout(function() {
			notification.cancel();
		}, 3500);
	}
}