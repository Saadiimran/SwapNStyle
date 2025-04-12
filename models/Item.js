const mongoose = require("mongoose");

const ItemSchema = new mongoose.Schema({
    userId: { type: mongoose.Schema.Types.ObjectId, ref: "User", required: true },
    type: { type: String, required: true },
    size: { type: String, required: true },
    condition: { type: String, required: true },
    category: { type: String, required: true },
    description: { type: String },
});

const Item = mongoose.model("Item", ItemSchema,"Items");

module.exports = Item;
