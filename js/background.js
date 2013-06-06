var idKey = 'id';
// var server_path = 'ws://localhost:8080';
var server_path = 'ws://23.22.100.228:8080';
var ws;
var notification;

var active = true;

chrome.runtime.onStartup.addListener(function() {
	init();
});

chrome.runtime.onInstalled.addListener(function() { 
	var id = guid();
	var width = 400;
	var height = 500;
	var left = (screen.width/2)-(width/2);
	var top = (screen.height/2)-(height/2);

	chrome.storage.local.set({'id': id});

	chrome.windows.create({
		url:'html/first_run.html', 
		type:'popup',
		width: width,
		height: height,
		left: left,
		top: top
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

	chrome.browserAction.onClicked.addListener(function(tab) {
		var icon_path = active ? 'img/icon_disabled.png' : 'img/icon_enabled.png';
		active = !active;

		chrome.browserAction.setIcon({
			path: icon_path
		});
	});
}

function initWebSocket(id) {
	ws = new WebSocket(server_path);

	ws.onopen = function(event) {
		console.log(id);
		ws.send(id);
	}

	ws.onmessage = function (event) {
		if (active) {
			if (notification != null) {
				notification.cancel();
			}

			notification = webkitNotifications.createNotification('', 'Alert!', unescape(event.data));
			notification.show();
		}
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