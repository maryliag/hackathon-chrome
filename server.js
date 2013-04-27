var app = require('express')()
	, server = require('http').createServer(app)
	, WebSocketServer = require('ws').Server
  	, wss = new WebSocketServer({port: 8080});

var ws1;

// app.listen(process.env.VCAP_APP_PORT || 3000);

app.get('/', function(req, res) {
	res.sendfile(__dirname + '/index.html');
});

app.get('/teste', function(req, res) {
	if (ws1 != null) {
		ws1.send('oláááááá');
		res.send('ok');
	} else {
		res.send('fail :(');
	}
	
});

wss.on('connection', function(ws) {
	ws1 = ws;
    ws.on('message', function(message) {
        console.log('received: %s', message);
    });
    ws.send('something');
});