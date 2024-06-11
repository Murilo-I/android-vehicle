const db = require('./db');

const Owner = db.sequelize.define('owner', {
    owner_id: {
        type: db.Sequelize.INTEGER,
        autoIncrement: true,
        allowNull: false,
        primaryKey: true
    },
    nome: {
        type: db.Sequelize.TEXT
    },
    cpf: {
        type: db.Sequelize.TEXT
    }
}, { freezeTableName: true });

module.exports = Owner;