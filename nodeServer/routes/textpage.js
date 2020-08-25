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
		let searchText = req.body.searchText;
		let Shape = req.body.shape;
		let Color = req.body.color;
		var colorArray = Color.split(',');
	    var shapeArray = Shape.split(',');
		var colorCount = 0;
		var shapeCount = 0;
		for(var i=0;i<Color.length;i++) {
			if(Color[i] == ',')
				colorCount++;
				
		}
		for(var i=0;i<Shape.length;i++) {
			if(Shape[i] == ',')
				shapeCount++;
		}
	//	console.log(Color);
	//	console.log(Shape)
	//	console.log(colorCount);
	//	console.log(shapeCount);

		var sql = 'select drug_name, drug_code, imgidfy_code, print_front from drug_lists ';
		var check = 0;
	//	console.log(drugName);
	//	console.log(searchText);
	//	console.log(Shape);
	//	console.log(Color);
		if(Color != '') {
		    if(check == 1) {
			    sql += ' and (';
		        for(var i in colorArray) {
		            sql += 'color like ("%'+colorArray[i]+'%")';
		            if(i != colorCount)
		                sql += ' or ';
				else
					sql += ')';
		        }
		    } else {
		    sql += 'where (';
		     for(var i in colorArray) {
		            sql += 'color like ("%'+colorArray[i]+'%")';
		            if(i != colorCount) 
		                sql += ' or ';
			     else
				     sql += ')';
		        }
		    check = 1;
		    }
		}
		if(Shape != '') {
		    if(check == 1) {
			sql += ' and (';
		        for(var i in shapeArray) {
		            sql += 'drug_shape like ("%'+shapeArray[i]+'%")';
		            if(i != shapeCount)
		                sql += ' or ';
				else
					sql += ')';
		        }
		    }else {
		    sql += 'where (';
		    for(var i in shapeArray) {
		            sql += 'drug_shape like ("%'+shapeArray[i]+'%")';
		            if(i != shapeCount)
		                sql += ' or ';
			    else
				    sql += ')';
		        }
             check = 1;
		    }
		}
		if(drugName != '') {
		    if(check == 1) {
		    sql += ' and (replace(drug_name," ","") like :FIND_NAME)';
		    }else {
		        sql += 'where (replace(drug_name," ","") like :FIND_NAME)';
             check = 1;
		    }
		}
		if(searchText != '') {
		    if(check ==1) {
		    sql += ' and (replace(print_front," ","") like :FIND_TEXT)';
		    }else {
		    sql += 'where (replace(print_front," ","") like :FIND_TEXT)';
             check = 1;
		    }
		}
	//	console.log(sql);
		    sequelize.query(sql, {replacements: {FIND_NAME: '%'+drugName+'%', FIND_TEXT: '%'+searchText+'%'}, type: sequelize.QueryTypes.SELECT}).then(function(rawResult) {
                        console.log(rawResult);
                        res.json(rawResult);
                    }).catch(function(err) {
                        console.log('error');
                    });
	} catch(err) {
		res.status(404).send("text page error!");
		console.dir(err.stack);
	}
});
module.exports = router;
