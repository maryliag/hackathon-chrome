$(function() {
	document.title = chrome.i18n.getMessage("txtWelcome");

	$("#txt_welcome").html(chrome.i18n.getMessage("txtWelcome"));
	$("#txt_scan_code").html(chrome.i18n.getMessage("txtScanCode"));
	$("#txt_download_app").html(chrome.i18n.getMessage("txtDownloadApp"));
	$("#google_play_badge").attr('src', chrome.i18n.getMessage("urlGooglePlayBadge"));

	chrome.storage.local.get('id', function(items) {
		var id = items['id'];
		$('#qr_code').qrcode(id);
	});
});