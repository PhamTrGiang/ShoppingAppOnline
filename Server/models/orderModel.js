const mongoose = require("mongoose");

const orderSchema = mongoose.Schema({
  userId: {
    type: "String",
    require: true,
  },
  product: [
    {
      name: {
        type: "String",
        require: true,
      },
      price: {
        type: "number",
        default: 0,
      },
      quantity: {
        type: "number",
        default: 0,
      },
      image: {
        type: "String",
        require: true,
      },
    },
  ],
  address: {
    type: "String",
    require: true,
  },
  status:{
    type: "number",
    default:0,
  }
});

const orderModel = mongoose.model("order", orderSchema);
module.exports = orderModel;
