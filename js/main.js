chrome.app.runtime.onLaunched.addListener(function() {
	init();
});

chrome.runtime.onInstalled.addListener(function() { 
	var id = guid();
	chrome.storage.local.set({'id': id});

	chrome.app.window.create('html/first_run.html', {
		'width': 400,
		'height': 500
	}, function(window) {
		window.contentWindow.onload = firstRunSetup(id, window.contentWindow);
	});
});

chrome.runtime.onSuspend.addListener(function() { 
	notification = webkitNotifications.createNotification('', 'Alert!', 'tchaaaaau...');
	notification.show();
});

function firstRunSetup(id, window) {
	var xhr = new XMLHttpRequest();
	xhr.open('GET', 'http://api.qrserver.com/v1/create-qr-code/?data='+id+'&size=250x250', true);
	xhr.responseType = 'blob';
	xhr.onload = function(e) {
	  var img = window.document.createElement('img');
	  img.src = window.webkitURL.createObjectURL(this.response);

	  var p = window.document.getElementById('qr_code');
	  p.appendChild(img);
	};

	xhr.send();
}