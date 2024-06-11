const db = require('./db');

const Vehicle = db.sequelize.define('vehicle', {
    vehicle_id: {
        type: db.Sequelize.INTEGER,
        autoIncrement: true,
        allowNull: false,
        primaryKey: true
    },
    plate: {
        type: db.Sequelize.TEXT
    },
    year: {
        type: db.Sequelize.INTEGER
    },
    rent: {
        type: db.Sequelize.DECIMAL(10, 2)
    },
    fk_owner: {
        type: db.Sequelize.INTEGER,
        references: { model: 'Owner', key: 'owner_id' },
        onDelete: 'CASCADE',
        allowNull: false,
    }
}, { freezeTableName: true });

module.exports = Vehicle;