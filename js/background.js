var idKey = 'id';
// var server_path = 'ws://localhost:8080';
var server_path = 'ws://23.22.100.228:8080';
var ws;
var notification;

var active = true;

chrome.runtime.onStartup.addListener(function() {
	init();
});

chrome.runtime.onInstalled.addListener(function(details) {
	if (details.reason == 'install') {
		var id = guid();
		var width = 400;
		var height = 400;
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
	}
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

	chrome.storage.local.get('active', function(items) {
		active = items['active'];

		if (active == null) {
			active = true;
			chrome.storage.local.set({'active': active});
		}

		toggleBrowserActionStatus(active);
	});
}

function initWebSocket(id) {
	ws = new WebSocket(server_path);

	ws.onopen = function(event) {
		console.log(id);
		ws.send(id);
	};

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