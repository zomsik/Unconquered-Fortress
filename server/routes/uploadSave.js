const router = require("express").Router()
const { Save } = require("../models/save")

router.post("/", async (req, res) => {
    try {

        const dataFromRequest  = {login: req.headers.login , profileNumber: req.headers.profileNumber, seed: req.headers.seed, difficulty: req.headers.difficulty, finishedMaps: req.headers.finishedMaps, wave: req.headers.wave, gold: req.headers.gold, diamonds: req.headers.diamonds}

        console.log("Zapisywanie")
        const saveData = await new Save({ dataFromRequest })

        const save = await Save.findOneAndReplace({ login: dataFromRequest.login, profileNumber: dataFromRequest.profileNumber }, saveData)


        if (save)
            return res.send({status: 200, message: "ResponseSuccessfullySaved" })
        else 
        {
            saveData.save()
            return res.send({status: 200, message: "ResponseSuccessfullyCreatedSave" })
        }
    } catch (error) {
        res.send({status: 500, message: "ResponseInternalServerError" })
    }
})
module.exports = router