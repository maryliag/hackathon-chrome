var app = require('express')()
	, server = require('http').createServer(app)
	, WebSocketServer = require('ws').Server
  	, wss = new WebSocketServer({port: 8080});

var ws_browser;

app.listen(80);

app.get('/', function(req, res) {
	res.sendfile(__dirname + '/index.html');
});

app.get('/send_notification', function(req, res) {
	console.log(req);

	if (ws_browser != null) {
		ws_browser.send(req['query']['notification']);
		res.send('ok');
	} else {
		res.send('fail :(');
	}
	
});

wss.on('connection', function(ws) {
	ws_browser = ws;
    ws.on('message', function(message) {
        console.log('received: %s', message);
    });
    ws.send('connected');
});