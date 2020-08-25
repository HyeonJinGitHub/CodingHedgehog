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
      let drugCode = req.body.drug_code;
      Drug_detail.findOne({where:{drug_code: drugCode}}).then(function(data)
      {
         if(data == null || data == undefined) {
            console.log('detail에 존재하지 않는 약입니다.');
            var response = 'Fail';
            res.json(response);
            return;
         }
         else {
            console.log(data.drug_name);
            Guide.findOne({where:{drug_code: drugCode}}).then(function(result)
            {
            if(result == null || result == undefined) {
               console.log('guides에 존재하지 않는 약입니다.');
               var response = 'Fail';
               res.json(response);
               return;
            }else {
            var response = {
		  detail: data, 
                  guide: result
	    };
              res.json(response);
              }
         }).catch(function(err) {
            console.log('error~~~~!');
         })
         }
     }).catch(function(err) {
        console.log('error!!!!');
        })
    }catch(err){
      console.log("drug_code error");
    }
});
module.exports = router;
