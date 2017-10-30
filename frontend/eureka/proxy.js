var request = require('request');

module.exports = {
    pipeTo: function(app, endpoint, url) {
        app.use(endpoint, function(req,res) {

            var headers = {
                'content-type': 'application/json'
            };
            if (req.headers['authorization'])
                headers.authorization = req.headers['authorization'];

            request({
            method: req.method,
            url: url + req.baseUrl + '/',
            headers: headers,
            body:  req.body,
            json: true
            }, function (error, response, body) {
                res.status(error ? 500 : response.statusCode).send(body);
            });
        });
    }
}