function toggleBrowserActionStatus(active, callback) {
	var icon_path = active ? '/img/icon_enabled.png' : '/img/icon_disabled.png';
	chrome.browserAction.setIcon({ path: icon_path }, function() {
		typeof callback == "function" && callback();
	});

	var title = 'James' + (active ? '' : ' ('+chrome.i18n.getMessage("txtDisabled")+')');
	chrome.browserAction.setTitle({title: title});
}