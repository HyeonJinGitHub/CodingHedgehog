
module.exports = (sequelize, DataTypes) => {
	return sequelize.define('guide', {
		id: {
			type: DataTypes.INTEGER,
			autoIncrement: true,
			primaryKey: true,
		},
		drug_code: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
		picto_img: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
		medititle: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
		mediguide: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
	}, {
		timestamps: false,
	});
};
