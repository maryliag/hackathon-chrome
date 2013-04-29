window.onload = function() {
	chrome.storage.local.get(idKey, function(items) {
		var id = items[idKey];
		loadQRCode(id);
	});
};

function loadQRCode(id) {
	var xhr = new XMLHttpRequest();
	xhr.open('GET', 'http://api.qrserver.com/v1/create-qr-code/?data='+id+'&size=250x250', true);
	xhr.responseType = 'blob';
	xhr.onload = function(e) {
	  var img = document.createElement('img');
	  img.src = window.webkitURL.createObjectURL(this.response);

	  var p = document.getElementById('qr_code');
	  p.appendChild(img);
	};

	xhr.send();
}