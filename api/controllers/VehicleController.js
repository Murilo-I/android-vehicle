const express = require('express');
const router = express.Router();
const Vehicle = require('../models/Vehicle');

router.get('/', async (req, res) => {
    const vehicles = await Vehicle.findAll();
    res.status(200).json(vehicles);
});

router.post('/', async (req, res) => {
    const { plate } = req.body;
    const { year } = req.body;
    const { rent } = req.body;
    const { fk_proprietario } = req.body;
    const newEdit = await Vehicle.create({
        plate, year, rent,
        fk_proprietario
    })
    res.status(200).json({ message: 'Cadastrado com sucesso' });
});

router.get('/:id', async (req, res) => {
    const veiculo = await Vehicle.findByPk(req.params.id);
    res.status(200).json(veiculo);
});

router.delete('/:id', async (req, res) => {
    await Vehicle.destroy({
        where: { id_veiculo: req.params.id },
    });
    res.status(200).json({ message: 'Excluído com sucesso' });
});

router.put('/:id', async (req, res) => {
    const { plate } = req.body;
    const { year } = req.body;
    const { rent } = req.body;
    const { fk_proprietario } = req.body;
    await Vehicle.update(
        { plate, year, rent, fk_proprietario },
        {
            where: { id_veiculo: req.params.id },
        }
    );
    res.status(200).json({ message: 'Atualizado com sucesso' });
});

module.exports = router;