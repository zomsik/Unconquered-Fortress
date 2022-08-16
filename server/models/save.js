const mongoose = require("mongoose")

const saveSchema = new mongoose.Schema({
    login: { type: String, required: true },
    profileNumber: {type: Number, required: true},
    seed: { type: Number, required: true },
    difficulty: { type: String, required: true },
    finishedMaps: { type: Number, required: true },
    wave: { type: Number, required: true },
    gold: { type: Number, required: true },
    diamonds: {type: Number, required: true}
})

const Save = mongoose.model("Save", saveSchema)

module.exports = { Save }