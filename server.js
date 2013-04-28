var app = require('express')()
	, server = require('http').createServer(app)
	, WebSocketServer = require('ws').Server
  	, wss = new WebSocketServer({port: 8080});

var ws_browser;

app.listen(8338);

app.get('/', function(req, res) {
	res.sendfile(__dirname + '/index.html');
});

app.post('/send_notification', function(req, res) {
	var msg = req.query['notification'];
	var id = req.query['id'];

	if (ws_browser != null) {
		try {
			ws_browser.send(msg);
		} catch (e) {
			setTimeout(function() {
				ws_browser.send(msg);
			}, 5000);
		}

		res.send('ok');
	} else {
		res.send('fail :(');
	}
	
});

wss.on('connection', function(ws) {
	ws_browser = ws;
    ws.send('connected');
});