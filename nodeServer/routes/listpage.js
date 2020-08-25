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
	
router.post('/', upload.array('files'), function(req, res, next) {
	try {
		const files = req.files;
		let locations = req.body.locations.split(', ');
		let originalName = '';
		let fileName = '';
		let mimeType = '';
		let size = 0;
		var options = {
			mode: 'text',
			pythonPath: '',
			pythonOptions: ['-u'],
			scriptPath: '',
			args: [locations[0], locations[1], locations[2], locations[3], locations[4], locations[5], locations[6], locations[7]]
		};
		console.log(req.headers);
		console.log(req.files);
		//for(var i=0; i< locations.length; i++) {
		//	console.log(locations[i]);
		//}
		if(Array.isArray(files)) {
			originalName = files.originalname;
			fileName = files.filename;
			mimeType = files.mimetype;
			size = files.size;
		} else {
			//console.log('files is not array~');
			originalName = files.originalname;
			fileName = files.filename;
			mimeType = files.mimetype;
			size = files.size;
		}
		var test = new PythonShell('color.py', options);
		test.on('message', function(message) {
			var split_msg = message.split(',');
		
			console.log(split_msg[0]);
			console.log(split_msg[1]);
			console.log(split_msg[2]);
			var find_color = split_msg[0];
			var find_shape = split_msg[1];
			var find_print = split_msg[2];
			if(find_color == 'White')
				find_color = '하양';
			else if(find_color == 'Yellow')
				find_color = '노랑';
			else if(find_color == 'Orange')
				find_color = '주황';
			else if(find_color == 'Pink')
				find_color = '분홍';
			else if(find_color == 'Red')
				find_color = '빨강';
			else if(find_color == 'Brown')
				find_color = '갈색';
			else if(find_color == 'Light green')
				find_color = '연두';
			else if(find_color == 'Green')
				find_color = '초록';
			else if(find_color == 'Turquoise')
				find_color = '청록';
			else if(find_color == 'Blue')
				find_color = '파랑';
			else if(find_color == 'Indigo')
				find_color = '남색';
			else if(find_color == 'Light purple')
				find_color = '자주';
			else if(find_color == 'Purple')
				find_color = '보라';
			else if(find_color == 'Grey')
				find_color = '회색';
			else if(find_color == 'Black')
				find_color = '검정';
			if(find_shape == 'oval')
				    find_shape = '타원형';
			else if(find_shape == 'semicircular')
				   find_shape = '반원형';
			else if(find_shape == 'circle')
				   find_shape = '원형';
			else if(find_shape == 'triangle')
				   find_shape = '삼각형';
			else if(find_shape == 'square')
				   find_shape = '사각형';
			else if(find_shape == 'rhombus')
				   find_shape = '마름모형';
			else if(find_shape == 'oblong')
				   find_shape = '장방형';
			else if(find_shape == 'pentagon')
				   find_shape = '오각형';
			else if(find_shape == 'hexagon')
				   find_shape = '육각형';
			else if(find_shape == 'octagon')
				   find_shape = '팔각형';
			else if(find_shape == 'other')
				   find_shape = '기타';
                        if(find_color == '빨강' || find_color == '갈색'|| find_color == '주황' && find_shape == '장방형') {
				sequelize.query('select drug_name, drug_code, imgidfy_code, print_front from (select * from drug_lists where color like :FIND_COLOR1 or color like :FIND_COLOR2 or color like :FIND_COLOR3) as A where (drug_shape=:FIND_SHAPE1 or drug_shape=:FIND_SHAPE2) and replace(print_front," ","") like :FIND_PRINT', {replacements: {FIND_COLOR1: '%빨강%',FIND_COLOR2: '%갈색%',FIND_COLOR3: '%주황%', FIND_SHAPE1: '장방형', FIND_SHAPE2: '타원형', FIND_PRINT: '%'+find_print+'%'}, type: sequelize.QueryTypes.SELECT}).then(function(rawResult) {
					console.log(rawResult);
					res.json(rawResult);
				}).catch(function(err) {
					console.log('error ~ red ');
				});
			}else if(find_color == '빨강' || find_color == '갈색'|| find_color == '주황' && find_shape == '원형') {
				sequelize.query('select drug_name, drug_code, imgidfy_code, print_front from (select * from drug_lists where color like :FIND_COLOR1 or color like :FIND_COLOR2 or color like :FIND_COLOR3) as A where drug_shape=:FIND_SHAPE and replace(print_front," ","") like :FIND_PRINT', {replacements: {FIND_COLOR1: '%빨강%',FIND_COLOR2: '%갈색%',FIND_COLOR3: '%주황%', FIND_SHAPE: find_shape , FIND_PRINT: '%'+find_print+'%'}, type: sequelize.QueryTypes.SELECT}).then(function(rawResult) {
					console.log(rawResult);
					res.json(rawResult);
				}).catch(function(err) {
					console.log('error9');
				});
			}
			if(find_color == '연두' || find_color == '초록' && find_shape == '장방형') {
				sequelize.query('select drug_name, drug_code, imgidfy_code, print_front from (select * from drug_lists where color like :FIND_COLOR1 or color like :FIND_COLOR2) as A where (drug_shape=:FIND_SHAPE1 or drug_shape=:FIND_SHAPE2) and replace(print_front," ","") like :FIND_PRINT', {replacements: {FIND_COLOR1: '%초록%',FIND_COLOR2: '%연두%', FIND_SHAPE1: '장방형', FIND_SHAPE2: '타원형', FIND_PRINT: '%'+find_print+'%'}, type: sequelize.QueryTypes.SELECT}).then(function(rawResult) {
					console.log(rawResult);
					res.json(rawResult);
				}).catch(function(err) {
					console.log('error222');
				});
			}else if(find_color == '연두' || find_color == '초록' && find_shape == '원형') {
				sequelize.query('select drug_name, drug_code, imgidfy_code, print_front from (select * from drug_lists where color like :FIND_COLOR1 or color like :FIND_COLOR2) as A where drug_shape=:FIND_SHAPE and replace(print_front," ","") like :FIND_PRINT', {replacements: {FIND_COLOR1: '%초록%',FIND_COLOR2: '%연두%', FIND_SHAPE: find_shape, FIND_PRINT: '%'+find_print+'%'}, type: sequelize.QueryTypes.SELECT}).then(function(rawResult) {
					console.log(rawResult);
					res.json(rawResult);
				}).catch(function(err) {
					console.log('error');
				});
			}
                        if(find_color == '노랑' || find_color == '주황' || find_color == '갈색' && find_shape == '장방형') {
				sequelize.query('select drug_name, drug_code, imgidfy_code, print_front from (select * from drug_lists where color like :FIND_COLOR1 or color like :FIND_COLOR2 or color like :FIND_COLOR3) as A where (drug_shape=:FIND_SHAPE1 or drug_shape=:FIND_SHAPE2) and replace(print_front," ","") like :FIND_PRINT', {replacements: {FIND_COLOR1: '%노랑%', FIND_COLOR2: '%주황%', FIND_COLOR3: '%갈색%', FIND_SHAPE1: '장방형', FIND_SHAPE2: '타원형', FIND_PRINT: '%'+find_print+'%'}, type: sequelize.QueryTypes.SELECT}).then(function(rawResult) {
					console.log(rawResult);
					res.json(rawResult);
				}).catch(function(err) {
					console.log('error12');
				});
			}else if(find_color == '노랑' || find_color == '주황' || find_color == '갈색' && find_shape == '원형') {
				sequelize.query('select drug_name, drug_code, imgidfy_code, print_front from (select * from drug_lists where color like :FIND_COLOR1 or color like :FIND_COLOR2 or color like :FIND_COLOR3) as A where drug_shape=:FIND_SHAPE and replace(print_front," ","") like :FIND_PRINT', {replacements: {FIND_COLOR1: '%노랑%',FIND_COLOR2: '%주황%', FIND_COLOR3: '%갈색%', FIND_SHAPE: find_shape, FIND_PRINT: '%'+find_print+'%'}, type: sequelize.QueryTypes.SELECT}).then(function(rawResult) {
					console.log(rawResult);
					res.json(rawResult);
				}).catch(function(err) {
					console.log('error4');
				});
			}
                        if(find_color == '분홍' || find_color == '자주' || find_color == '보라' && find_shape == '장방형') {
				sequelize.query('select drug_name, drug_code, imgidfy_code, print_front from (select * from drug_lists where color like :FIND_COLOR1 or color like :FIND_COLOR2 or color like :FIND_COLOR3) as A where (drug_shape=:FIND_SHAPE1 or drug_shape=:FIND_SHAPE2) and replace(print_front," ","") like :FIND_PRINT', {replacements: {FIND_COLOR1: '%분홍%',FIND_COLOR2: '%자주%', FIND_COLOR3: '%보라%', FIND_SHAPE1: '장방형', FIND_SHAPE2: '타원형', FIND_PRINT: '%'+find_print+'%'}, type: sequelize.QueryTypes.SELECT}).then(function(rawResult) {
					console.log(rawResult);
					res.json(rawResult);
				}).catch(function(err) {
					console.log('error');
				});
			}else if(find_color == '분홍' || find_color == '자주' || find_color == '보라' && find_shape == '원형') {
				sequelize.query('select drug_name, drug_code, imgidfy_code, print_front from (select * from drug_lists where color like :FIND_COLOR1 or color like :FIND_COLOR2 or color like :FIND_COLOR3) as A where drug_shape=:FIND_SHAPE and replace(print_front," ","") like :FIND_PRINT', {replacements: {FIND_COLOR1: '%분홍%',FIND_COLOR2: '%자주%', FIND_COLOR3: '%보라%', FIND_SHAPE: find_shape, FIND_PRINT: '%'+find_print+'%'}, type: sequelize.QueryTypes.SELECT}).then(function(rawResult) {
					console.log(rawResult);
					res.json(rawResult);
				}).catch(function(err) {
					console.log('error');
				});
			}
                        if(find_color == '청록' || find_color == '파랑' || find_color == '남색'|| find_color == '검정' && find_shape == '장방형') {
				sequelize.query('select drug_name, drug_code, imgidfy_code, print_front from (select * from drug_lists where color like :FIND_COLOR1 or color like :FIND_COLOR2 or color like :FIND_COLOR3 or color like :FIND_COLOR4) as A where (drug_shape=:FIND_SHAPE1 or drug_shape=:FIND_SHAPE2) and replace(print_front," ","") like :FIND_PRINT', {replacements: {FIND_COLOR1: '%'+'청록'+'%',FIND_COLOR2: '%'+'파랑'+'%', FIND_COLOR3: '%'+'남색'+'%',FIND_COLOR4: '%'+'검정'+'%', FIND_SHAPE1: '장방형', FIND_SHAPE2: '타원형', FIND_PRINT: '%'+find_print+'%'}, type: sequelize.QueryTypes.SELECT}).then(function(rawResult) {
					console.log(rawResult);
					res.json(rawResult);
				}).catch(function(err) {
					console.log('error6');
				});
			}else if(find_color == '청록' || find_color == '파랑' || find_color == '남색'|| find_color == '검정' && find_shape == '원형') {
				sequelize.query('select drug_name, drug_code, imgidfy_code, print_front from (select * from drug_lists where color like :FIND_COLOR1 or color like :FIND_COLOR2 or color like :FIND_COLOR3 or color like :FIND_COLOR4) as A where drug_shape=:FIND_SHAPE and replace(print_front," ","") like :FIND_PRINT', {replacements: {FIND_COLOR1: '%'+'청록'+'%',FIND_COLOR2: '%'+'파랑'+'%', FIND_COLOR3: '%'+'남색'+'%',FIND_COLOR4: '%'+'검정'+'%', FIND_SHAPE: find_shape, FIND_PRINT: '%'+find_print+'%'}, type: sequelize.QueryTypes.SELECT}).then(function(rawResult) {
					console.log(rawResult);
					res.json(rawResult);
				}).catch(function(err) {
					console.log('error');
				});
			}
                        if(find_color == '하양' || find_color == '회색' && find_shape == '장방형') {
				sequelize.query('select drug_name, drug_code, imgidfy_code, print_front from (select * from drug_lists where color like :FIND_COLOR1 or color like :FIND_COLOR2) as A where (drug_shape=:FIND_SHAPE1 or drug_shape=:FIND_SHAPE2) and replace(print_front," ","") like :FIND_PRINT', {replacements: {FIND_COLOR1: '%하양%', FIND_COLOR2: '%회색%', FIND_SHAPE1: '장방형', FIND_SHAPE2: '타원형', FIND_PRINT: '%'+find_print+'%'}, type: sequelize.QueryTypes.SELECT}).then(function(rawResult) {
					console.log(rawResult);
					res.json(rawResult);
				}).catch(function(err) {
					console.log('error');
				});
			}else if(find_color == '하양' || find_color == '회색' && find_shape == '원형') {
				sequelize.query('select drug_name, drug_code, imgidfy_code, print_front from (select * from drug_lists where color like :FIND_COLOR1 or color like :FIND_COLOR2) as A where drug_shape=:FIND_SHAPE  and replace(print_front," ","") like :FIND_PRINT', {replacements: {FIND_COLOR1: '%하양%', FIND_COLOR2: '%회색%', FIND_SHAPE: find_shape, FIND_PRINT: '%'+find_print+'%'}, type: sequelize.QueryTypes.SELECT}).then(function(rawResult) {
					console.log(rawResult);
					res.json(rawResult);
				}).catch(function(err) {
					console.log('error');
				});
			}
		
		//	res.status(200).send(message);
		});
	} catch(err) {
		res.status(404).send("File is Not Found..");
		console.dir(err.stack);
	}
});
module.exports = router;
