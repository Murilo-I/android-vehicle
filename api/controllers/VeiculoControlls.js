const express = require('express');
const router = express.Router();
const Veiculo = require('../models/Veiculo');

router.get('/', async (req, res) => {
    const veiculos = await Veiculo.findAll();
    res.status(200).json(veiculos);
});

router.post('/', async (req, res) => {
    const { placa } = req.body;
    const { ano } = req.body;
    const { mensalidade } = req.body;
    const { fk_proprietario } = req.body;
    const newEdit = await Veiculo.create({
        placa, ano, mensalidade,
        fk_proprietario
    })
    res.status(200).json({ message: 'Cadastrado com sucesso' });
});

router.get('/:id', async (req, res) => {
    const veiculo = await Veiculo.findByPk(req.params.id);
    res.status(200).json(veiculo);
});

router.delete('/:id', async (req, res) => {
    await Veiculo.destroy({
        where: { id_veiculo: req.params.id },
    });
    res.status(200).json({ message: 'Excluído com sucesso' })
});

router.put('/:id', async (req, res) => {
    const { placa } = req.body;
    const { ano } = req.body;
    const { mensalidade } = req.body;
    const { fk_proprietario } = req.body;
    await Veiculo.update(
        { placa, ano, mensalidade, fk_proprietario },
        {
            where: { id_veiculo: req.params.id },
        }
    );
    res.status(200).json({ message: 'Atualizado com sucesso' });
});

module.exports = router;