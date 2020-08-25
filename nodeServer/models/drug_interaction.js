
module.exports = (sequelize, DataTypes) => {
	return sequelize.define('drug_interaction', {
		id: {
			type: DataTypes.INTEGER,
			autoIncrement: true,
			primaryKey: true,
		},
		drug_name1: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
		drug_name2: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
		effect: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
	}, {
		timestamps: false,
	});
};
