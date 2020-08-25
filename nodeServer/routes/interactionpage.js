const express  = require('express');
const router = express.Router();
const multer = require('multer');
//const fs = require('fs');
//const ejs = require('ejs');

var sequelize = require('../models').sequelize;
var {PythonShell} = require('python-shell');
var Drug_list = require('../models').Drug_list;
var Drug_detail = require('../models').Drug_detail;
var Guide = require('../models').Guide;
var Drug_interaction = require('../models').Drug_interaction;

const storage = multer.diskStorage({
	destination(req, file, callback) {
		callback(null, 'images');
	},
	filename(req, file, callback) {
		callback(null, file.originalname);
	}
});

const upload = multer({
	storage,
	limits: {
		files: 10,
		fileSize: 1024*1024*1024*1024,
	}
});

router.post('/', function(req, res, next) {
	try {
	    let drugName = req.body.drug_name;
		var drugArray = drugName.split(',');
		var drugCount = 1;
		for(var i=0;i<drugName.length;i++) {
			if(drugName[i] == ',')
				drugCount++;
		}
		/*
        var resCount = 0;
        var drugList = new Array(drugCount);
		console.log(drugName);
	    console.log('dfdfdf  '+drugCount);

		var sql = 'select exists(select * from drug_interactions where (drug_name1 like :DRUG_NAME1 or drug_name2 like :DRUG_NAME2)) as result';
		console.log('where are you');
		console.log(sql);
		for(var i=0;i<drugCount;i++) {
			console.log('help me please~');
		    sequelize.query(sql, {replacements: {DRUG_NAME1: '%'+drugArray[i]+'%', DRUG_NAME2: '%'+drugArray[i]+'%'}, type: sequelize.QueryTypes.SELECT}).then(function(rawResult) {
			    setTimeout(function() {
			    var tmp = JSON.stringify(rawResult);

			    console.log(typeof(tmp));
			    console.log('tmp : '+tmp);
			    if(tmp == '[{"result":1}]') {
				    console.log('i:' + i + 'array: '+drugArray[i]);
		            drugList.push(drugArray[i]);

		            resCount++;
				console.log('도어      '+resCount);
		        }
			    },2000);
		    }).catch(function(err) {
		        console.log('error1~~~@#');
		    });
		}
		setTimeout(function() {
		console.log(drugList[0]);
		console.log('Hi!! '+drugList[1]);
			console.log('hello~~~');
		console.log('asd   '+resCount);
		resCount = 3;
		*/
		var result = new Array();
		for(var i=0;i<drugCount;i++) {
            for(var j=i+1;j<drugCount;j++) {
            sql = 'select distinct drug_name1, drug_name2, effect from drug_interactions where ((drug_name1 like :DRUG_NAME1 and drug_name2 like :DRUG_NAME2) or (drug_name1 like :DRUG_NAME2 and drug_name2 like :DRUG_NAME1))';
              sequelize.query(sql, {replacements: {DRUG_NAME1: '%'+drugArray[i]+'%', DRUG_NAME2: '%'+drugArray[j]+'%'}, type: sequelize.QueryTypes.SELECT}).then(function(rawResult) {
		      var tmp = JSON.stringify(rawResult);
		     
		      if(tmp != '[]') {  
		      console.log(rawResult);
			 var t = { data: rawResult};
			result.push(t);
		        }
		    }).catch(function(err) {
		        console.log('error2~~~@#');
		    });
            }
	}setTimeout(function() {
	//var tmp = JSON.stringify(result);
	res.json(result);
	},2000);
	} catch(err) {
		res.status(404).send("interaction page error!");
		console.dir(err.stack);
	}

});
module.exports = router;
