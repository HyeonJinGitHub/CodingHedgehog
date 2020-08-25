
module.exports = (sequelize, DataTypes) => {
	return sequelize.define('drug_list', {
		idx: {
			type: DataTypes.INTEGER,
			autoIncrement: true,
			primaryKey: true,
		},
		drug_name: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
		drug_code: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
		imgidyf_code: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
		print_front: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
		print_back: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
		drug_shape: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
		color: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
			},
		upso_name_kfda: {
			type: DataTypes.TEXT('medium'),
			allowNull: false,
		},
	}, {
		timestamps: false,
	});
};
