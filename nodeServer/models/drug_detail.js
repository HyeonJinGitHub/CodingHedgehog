module.exports = (sequelize, DataTypes) => {
	return sequelize.define('drug_detail',{
		idx: {
			type: DataTypes.INTEGER,
			autoIncrement: true,
			primaryKey: true,
		},
		drug_code: {
			type: DataTypes.Sequelize.TEXT('medicum'),
			allowNull: false,
		},
		drug_name: {
			type: DataTypes.Sequelize.TEXT('medium'),
			allowNull: false,
		},
		drug_enm: {
			type: DataTypes.Sequelize.TEXT('medium'),
			allowNull: false,
		},
		upso_name_kfda: {
			type: DataTypes.Sequelize.TEXT('medium'),
			allowNull: false,
		},
		cls_name: {
			type: DataTypes.Sequelize.TEXT('medium'),
			allowNull: false,
		},
		item_ingr_type: {
			type: DataTypes.Sequelize.TEXT('medium'),
			allowNull: false,
		},
		package: {
			type: DataTypes.Sequelize.TEXT('medium'),
			allowNull: false,
		},
		drug_box: {
			type: DataTypes.Sequelize.TEXT('medium'),
			allowNull: false,
		},
		charact: {
			type: DataTypes.Sequelize.TEXT('medium'),
			allowNull: false,
		},
		sunb: {
			type: DataTypes.Sequelize.TEXT('medium'),
			allowNull: false,
		},
		effect: {
			type: DataTypes.Sequelize.TEXT('medium'),
			allowNull: false,
		},
		dosage: {
			type: DataTypes.Sequelize.TEXT('medium'),
			allowNull: false,
		},
		caution: {
			type: DataTypes.Sequelize.TEXT('medium'),
			allowNull: false,
		},
		mediguide: {
			type: DataTypes.Sequelize.TEXT('medium'),
			allowNull: false,
		},
		stmt: {
			type: DataTypes.Sequelize.TEXT('medium'),
			allowNull: false,
		},
		additives: {
			type: DataTypes.Sequelize.TEXT('medium'),
			allowNull: false,
		},
	}, {
		timestamps: false,
	});
};
