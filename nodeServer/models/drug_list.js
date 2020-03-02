
module.exports = (sequelize, DataTypes) => {
	                return sequelize.define('drug_list', {
				                                        idx: {
										                                                                        type: DataTypes.INTEGER,
										                                                                        autoIncrement: true,
										                                                                        primaryKey: true,
										                                                                },
				                                        drug_code: {
										                                                                        type: DataTypes.Sequelize.TEXT('medium'),
										                                                                        allowNull: false,
										                                                                },
				                                        imgidyf_code: {
										                                                                        type: DataTypes.Sequelize.TEXT('medium'),
										                                                                        allowNull: false,
										                                                                },
				                                        print_front: {
										                                                                        type: DataTypes.Sequelize.TEXT('medium'),
										                                                                        allowNull: false,
										                                                                },
				                                        print_back: {
										                                                                        type: DataTypes.Sequelize.TEXT('medium'),
										                                                                        allowNull: false,
										                                                                },
				                                        print_back: {
										                                                                        type: DataTypes.Sequelize.TEXT('medium'),
										                                                                        allowNull: false,
										                                                                },
				                                        drug_name: {
										                                                                        type: DataTypes.Sequelize.TEXT('medium'),
										                                                                        allowNull: false,
										                                                                },
				                                        upso_name_kfda: {
										                                                                        type: DataTypes.Sequelize.TEXT('medium'),
										                                                                        allowNull: false,
										                                                                },
				                                }, {
									                                                        timestamps: false,
									                                                });
};
