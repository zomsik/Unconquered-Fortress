const router = require("express").Router()
const { Save } = require("../models/save")

router.post("/", async (req, res) => {
    try {

        const dataFromRequest  = {login: req.headers.login , profileNumber: req.headers.profilenumber, seed: req.headers.seed, difficulty: req.headers.difficulty, finishedMaps: req.headers.finishedmaps, wave: req.headers.wave, gold: req.headers.gold, diamonds: req.headers.diamonds, terrainModifications: JSON.parse(req.headers.terrainmodifications)}

        const save = await Save.exists({ login: dataFromRequest.login, profileNumber: dataFromRequest.profileNumber });
        
        if (save)
        {
            await Save.findOneAndUpdate({ login: dataFromRequest.login, profileNumber: dataFromRequest.profileNumber }, dataFromRequest)
            return res.send({status: 200, message: "ResponseSuccessfullySaved" })
        }
        else 
        {
            await Save({ ...dataFromRequest }).save()
            return res.send({status: 200, message: "ResponseSuccessfullyCreatedSave" })
        }
    } catch (error) {
        res.send({status: 500, message: "ResponseInternalServerError" })
    }
})
module.exports = router