const express = require("express");
const authMiddleware = require("../middleware/authMiddleware");
const Item = require("../models/Item");
const bcrypt = require("bcryptjs");

const router = express.Router();

// Create Item


//{
//    "type": "Shirt",
//    "size": "L",
//    "condition": "Good",
//    "category": "Casual",
//    "description": "A comfortable casual shirt."
//  }
router.post("/", authMiddleware, async (req, res) => {
    const { type, size, condition, category, description } = req.body;
    try {
        const newItem = new Item({
            userId: req.user.userId, // Use req.user.userId here
            type,
            size,
            condition,
            category,
            description,
        });
        await newItem.save();
        res.status(201).json(newItem);
    } catch (error) {
        res.status(500).json({ message: "Server error", error });
    }
});


// Get All Items
router.get("/", async (req, res) => {
    try {
        const items = await Item.find();
        res.json(items);
    } catch (error) {
        res.status(500).json({ message: "Server error", error });
    }
});

router.get("/:id", async (req, res) => {
    try {
        const item = await Item.findById(req.params.id); // Fetch item by ID
        if (!item) return res.status(404).json({ message: "Item not found" });
        res.json(item);
    } catch (error) {
        res.status(500).json({ message: "Server error", error });
    }
});

router.put("/:id", authMiddleware, async (req, res) => {
    const { type, size, condition, category, description } = req.body;
    try {
        const item = await Item.findById(req.params.id); // Find the item by ID
        if (!item) return res.status(404).json({ message: "Item not found" });

        // Ensure the user can only update their own items
        if (item.userId.toString() !== req.user.userId) {
            return res.status(403).json({ message: "Unauthorized to update this item" });
        }

        // Update the item
        item.type = type || item.type;
        item.size = size || item.size;
        item.condition = condition || item.condition;
        item.category = category || item.category;
        item.description = description || item.description;

        await item.save();
        res.json(item);
    } catch (error) {
        res.status(500).json({ message: "Server error", error });
    }
});


// Delete Item
router.delete("/:id", authMiddleware, async (req, res) => {
    try {
        console.log("Attempting to delete item with ID:", req.params.id); // Log the item ID being deleted

        const item = await Item.findByIdAndDelete(req.params.id);

        if (!item) {
            return res.status(404).json({ message: "Item not found" });
        }

        res.status(200).json({ message: "Item deleted successfully" });
    } catch (error) {
        console.error("Error during item deletion:", error); // Log the detailed error
        res.status(500).json({ message: "Server error", error: error.message });
    }
});

module.exports = router;
