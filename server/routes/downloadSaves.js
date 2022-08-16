const router = require("express").Router()
const { Save } = require("../models/save")


router.post("/", async (req, res) => {
    try {

        const dataFromRequest  = {login: req.headers.login }


        const save = await Save.find({ login: dataFromRequest.login })

        if (save)
            return res.send({status: 200, message: save })
        else 
        return res.send({status: 201, message: "NoSaves" })
    } catch (error) {
        res.send({status: 500, message: "ResponseInternalServerError" })
    }
})
module.exports = router