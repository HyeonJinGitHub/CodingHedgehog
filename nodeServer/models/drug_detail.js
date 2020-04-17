module.exports = (sequelize, DataTypes) => {
	return sequelize.define('drug_detail',{
		idx: {
			type: DataTypes.INTEGER,
			autoIncrement: true,
			primaryKey: true,
		},
		drug_code: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
		drug_name: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
		drug_enm: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
		upso_name_kfda: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
		cls_name: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
		item_ingr_type: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
		package: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
		drug_box: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
		charact: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
		sunb: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
		effect: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
		dosage: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
		caution: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
		mediguide: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
		stmt: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
		additives: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
	}, {
		timestamps: false,
	});
};
