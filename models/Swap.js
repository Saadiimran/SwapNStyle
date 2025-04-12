const mongoose = require("mongoose");

const SwapSchema = new mongoose.Schema({
    userA: { type: mongoose.Schema.Types.ObjectId, ref: "User", required: true },
    itemA: { type: mongoose.Schema.Types.ObjectId, ref: "Item", required: true },
    userB: { type: mongoose.Schema.Types.ObjectId, ref: "User", required: true },
    itemB: { type: mongoose.Schema.Types.ObjectId, ref: "Item", required: true },
    status: { type: String, enum: ["pending", "approved", "completed"], default: "pending" },
}, { timestamps: true });

module.exports = mongoose.model("Swap", SwapSchema);
