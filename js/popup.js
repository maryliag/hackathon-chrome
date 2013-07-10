$(function() {
	$("#lbl_show_notification").html(chrome.i18n.getMessage("lblActivateNotification"));
	$("#lbl_settings").html(chrome.i18n.getMessage("lblSettings"));
	$("#lbl_current_sync_id").html(chrome.i18n.getMessage("lblCurrentSyncId"));
	$("#lbl_or").html(chrome.i18n.getMessage("lblOr"));

	chrome.storage.local.get(['active', 'id'], function(items) {
		var active = items['active'];
		$('#ckbx_show_notification').prop('checked', active);

		var id = items['id'];
		$('#qr_code').qrcode({
			render: 'canvas',
			width: 190,
			height: 190,
			text: id
		});
	});

	$("#btn_save").html(chrome.i18n.getMessage("btnSave"));
	$("#btn_save").click(function() {
		var showNotifications = $("#ckbx_show_notification").is(':checked');
		chrome.storage.local.set({'active': showNotifications});

		toggleBrowserActionStatus(showNotifications);
	});

	$("#btn_cancel").html(chrome.i18n.getMessage("btnCancel"));
	$("#btn_cancel").click(function() {
		window.close();
	});
});