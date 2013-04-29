var idKey = 'id';

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
});

chrome.runtime.onSuspend.addListener(function() { 
	notification = webkitNotifications.createNotification('', 'Alert!', 'tchaaaaau...');
	notification.show();
});