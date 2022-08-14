require('dotenv').config()


const express = require('express')
const app = express()
const cors = require('cors')
const connection = require('./db')
const registerRoutes = require("./routes/register")
const loginRoutes = require("./routes/login")

app.post('/', function(req, res){
    console.log("req")
    console.log(req.headers.data)
    res.send('Szkielet programistyczny Express!')
   })

connection()
app.use(express.json())
app.use(cors())



// routes
app.use("/api/register", registerRoutes)
app.use("/api/login", loginRoutes)

const port = process.env.PORT || 9000
app.listen(port, () => console.log(`Nasłuchiwanie na porcie ${port}`))