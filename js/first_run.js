window.onload = function() {
	chrome.storage.local.get('id', function(items) {
		var id = items['id'];
		$('#qr_code').qrcode(id);
		$('#id').html(id);
	});
};