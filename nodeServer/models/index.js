const path = require('path');
const Sequelize = require('sequelize');

const env = process.env.NODE_ENV || 'development';
const config = require(__dirname + '/../config/config.json')[env];
const db = {};

const sequelize = new Sequelize(config.database, config.username, config.password, config);

db.sequelize = sequelize;
db.Sequelize = Sequelize;

db.drug_list = require('./drug_list')(sequelize, Sequelize);
db.drug_detail = require('./drug_detail')(sequelize, Sequelize);

//db.drug_list.belongsToMany(db.drug_detail, { through: 'drug_connect' });
//db.drug_detail.belongsToMany(db.drug_list, { through: 'drug_connect' });

module.exports = db;
