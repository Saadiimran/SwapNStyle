const express = require("express");
const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");
const User = require("../models/User");
const authMiddleware = require("../middleware/authMiddleware");


const router = express.Router();
const JWT_SECRET = "yourhardcodedsecret";

// Register User


//{
//    "name": "Zahid Hussain",
//    "email": "zahid@example.com",
//    "password": "password123"
//  }
router.post("/register", async (req, res) => {
    const { name, email, password } = req.body;
    try {
        let user = await User.findOne({ email });
        if (user) return res.status(400).json({ message: "User already exists" });

        user = new User({ name, email, password });
        await user.save();

        res.status(201).json({ message: "User registered successfully" });
    } catch (error) {
        res.status(500).json({ message: "Server error", error });
    }
});

// Login User
router.post("/login", async (req, res) => {
    const { email, password } = req.body;
    try {
        const user = await User.findOne({ email });
        if (!user) return res.status(400).json({ message: "Invalid credentials" });

        const isMatch = await bcrypt.compare(password, user.password);
        if (!isMatch) return res.status(400).json({ message: "Invalid credentials" });

        const token = jwt.sign(
            { userId: user._id, email: user.email }, "yourSecretKey", { expiresIn: "1h" });

        res.json({ token, userId: user._id, role: user.role });
    } catch (error) {
        res.status(500).json({ message: "Server error", error });
    }
});


// Update User Profile
router.put("/:id", authMiddleware, async (req, res) => {
    const { name, email, password } = req.body;
    try {
        const user = await User.findById(req.params.id);
        if (!user) return res.status(404).json({ message: "User not found" });

        // Update user profile fields
        user.name = name || user.name;
        user.email = email || user.email;
        user.password = password ? await bcrypt.hash(password, 10) : user.password;

        await user.save();
        res.json(user);
    } catch (error) {
        res.status(500).json({ message: "Server error", error });
    }
});

module.exports = router;
