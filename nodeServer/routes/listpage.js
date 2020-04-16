const express  = require('express');
const router = express.Router();
const multer = require('multer');
//const fs = require('fs');
//const ejs = require('ejs');
var {PythonShell} = require('python-shell');

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
			console.log(message);
			res.status(200).send(message);
		});
	} catch(err) {
		res.status(404).send("File is Not Found..");
		console.dir(err.stack);
	}
});
module.exports = router;
