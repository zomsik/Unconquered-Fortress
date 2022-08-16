const router = require("express").Router()
const { Save } = require("../models/save")

router.post("/", async (req, res) => {
    try {

        const dataFromRequest  = {login: req.headers.login , profileNumber: req.headers.profilenumber}


        const save = await Save.exists({ login: dataFromRequest.login, profileNumber: dataFromRequest.profileNumber });
        
        if (save)
        {

            await Save.findOneAndDelete({ login: dataFromRequest.login, profileNumber: dataFromRequest.profileNumber })
            return res.send({status: 200, message: "ResponseSuccessfullyDeletedSave" })
        }
        else 
        {
            return res.send({status: 200, message: "ResponseSaveDoesntExist" })
        }
    } catch (error) {
        res.send({status: 500, message: "ResponseInternalServerError" })
    }
})
module.exports = router