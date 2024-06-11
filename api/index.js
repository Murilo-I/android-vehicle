const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors')
const app = express();
const port = 8081;
const owner = require('./controllers/OwnerController.js');
const vehicle = require('./controllers/VehicleController.js');
app.use(bodyParser.json());
app.use(cors());
app.get('/', (req, res) => res.send('Estou aqui'));
app.use('/owner', owner);
app.use('/vehicle', vehicle);
app.listen(port, () => console.log(`Servidor rodando porta ${port}!`));