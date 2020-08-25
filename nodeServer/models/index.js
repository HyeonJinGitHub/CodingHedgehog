const path = require('path');
const Sequelize = require('sequelize');
const env = process.env.NODE_ENV || 'development';
const config = require(path.join(__dirname, '..', 'config', 'config.json'))[env];
const db = {};

const sequelize = new Sequelize(config.database, config.username, config.password, config);

db.sequelize = sequelize;
db.Sequelize = Sequelize;

db.Drug_list = require('./drug_list')(sequelize, Sequelize);
db.Drug_detail = require('./drug_detail')(sequelize, Sequelize);
db.Guide = require('./guide')(sequelize, Sequelize);

db.Drug_list.belongsToMany(db.Drug_detail, {through: 'list_detail'});
db.Drug_detail.belongsToMany(db.Drug_list, {through: 'list_detail'});

module.exports = db;
