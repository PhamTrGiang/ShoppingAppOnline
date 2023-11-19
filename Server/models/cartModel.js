const mongoose = require("mongoose");

const cartSchema = mongoose.Schema({
  userId: {
    type: "String",
    require: true,
  },
  productId: {
    type: "String",
    require: true,
  },
  quantity: {
    type: "number",
    default: 1,
  },
});

const cartModel = mongoose.model("cart", cartSchema);
module.exports = cartModel;
