const express = require('express');
var http = require("http");
var request = require("request");

const router = express.Router();

/* GET api listing. */
router.get('/', (req, res) => {
  res.send('api works');
});


router.post('/user/register', (req, res)=>{
  console.log(req.body)
  var options = { 
    method: 'POST',
    url: 'http://localhost:8000/api/user/register/',
    headers: 
    { 
      'cache-control': 'no-cache',
      'content-type': 'application/json' 
    },
    body: req.body,
    json: true 
  };

  request(options, function (error, response, body) {
    if (error) throw new Error(error);
    console.log(body);
    res.status(200).send(body);
  });

});

module.exports = router;