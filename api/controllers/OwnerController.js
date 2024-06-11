const express = require('express');
const router = express.Router();
const Owner = require('../models/Owner');

router.get('/', async (req, res) => {
    const owners = await Owner.findAll();
    res.status(200).json(owners);
});

router.post('/', async (req, res) => {
    const { nome } = req.body;
    const { cpf } = req.body;
    const newEdit = await Owner.create({ nome, cpf });
    res.status(200).json({ message: 'Cadastrado com sucesso' });
});

router.get('/:id', async (req, res) => {
    const proprietario = await Owner.findByPk(req.params.id);
    res.status(200).json(proprietario);
});

router.delete('/:id', async (req, res) => {
    await Owner.destroy({
        where: {
            owner_id: req.params.id,
        },
    });
    res.status(200).json({ message: 'Excluído com sucesso' });
});

router.put('/:id', async (req, res) => {
    const { nome } = req.body;
    const { cpf } = req.body;
    await Owner.update(
        { nome, cpf },
        {
            where: { owner_id: req.params.id },
        }
    );
    res.status(200).json({ message: 'Atualizado com sucesso' });
});

module.exports = router;