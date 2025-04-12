const mongoose = require('mongoose')
const mongoURL = 'mongodb://localhost:27017/SwapNStyles'
mongoose.connect(mongoURL)
const db = mongoose.connection

db.on('connected',()=>{console.log("MongoDB is connected")})
db.on('error',()=>{console.log("MongoDB error")})
db.on('disconnected',()=>{console.log("MongoDB is disconnected")})
module.exports = db;