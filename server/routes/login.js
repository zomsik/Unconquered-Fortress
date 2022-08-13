const router = require("express").Router()
const { User } = require("../models/user")
const bcrypt = require("bcrypt")
const Joi = require("joi")

router.post("/", async (req, res) => {
    try {
        
        const dataToValidate  = {login: req.headers.login, password: req.headers.password}

        const { error } = validate(dataToValidate);
        if (error)
            return res.send( error.details[0].message )
        
        

        const user = await User.findOne({ login: req.headers.login })
        
        if (!user)
            return res.send({ status: 400, message: "ResponseNoUserWithThisLogin" })
        
        const validPassword = await bcrypt.compare(req.headers.password,user.password)
        
        if (!validPassword)
            return res.send({ status: 401, message: "ResponseWrongPassword" })

        const token = user.generateAuthToken();
        res.send({ status: 200, token: token, message: "ResponseLoggedSuccessfully" })
        console.log('Zalogowano')

    } catch (error) {
        res.status(500).send({ status: 500, message: "ResponseInternalServerError" })
    }
})

const validate = (data) => {
    console.log(data)
    const schema = Joi.object({
        login: Joi.string().required().label("Login"),
        password: Joi.string().required().label("Password"),
    })
    return schema.validate(data)
}
module.exports = router