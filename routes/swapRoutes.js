const express = require("express");
const authMiddleware = require("../middleware/authMiddleware");
const Swap = require("../models/Swap");

const router = express.Router();

// Create Swap Request
router.post("/", authMiddleware, async (req, res) => {
    const { itemA, userB, itemB } = req.body;
    try {
        const swapRequest = new Swap({ userA: req.user.id, itemA, userB, itemB, status: "pending" });
        await swapRequest.save();
        res.status(201).json(swapRequest);
    } catch (error) {
        res.status(500).json({ message: "Server error", error });
    }
});

// Get All Swaps
router.get("/", authMiddleware, async (req, res) => {
    try {
        const swaps = await Swap.find();
        res.json(swaps);
    } catch (error) {
        res.status(500).json({ message: "Server error", error });
    }
});

module.exports = router;
