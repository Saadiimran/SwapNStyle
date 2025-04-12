const express = require("express");
const connectDB = require("./db");

const userRoutes = require("./routes/userRoutes");
const itemRoutes = require("./routes/itemRoutes");
const swapRoutes = require("./routes/swapRoutes");

const app = express();
app.use(express.json());



app.use("/api/users", userRoutes);
app.use("/api/items", itemRoutes);


const PORT = 3000;
app.listen(PORT, () => console.log(`Server running on port ${PORT}`));
