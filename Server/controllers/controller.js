const express = require("express");
const app = express();

const productModel = require("../models/productModel");
const brandModel = require("../models/brandModel");
const userModel = require("../models/userModel");
const cartModel = require("../models/cartModel");
const profileModel = require("../models/profileModel");
const orderModel = require("../models/orderModel");

app.get("/", async (req, res) => {
  try {
    const products = await productModel.find();
    const productDetail = [];
    for (const product of products) {
      const brand = await brandModel.findById(product.brand);
      newArr = {
        _id: product._id,
        name: product.name,
        price: product.price,
        quantity: product.quantity,
        image: product.image,
        brand: brand.name,
      };
      productDetail.push(newArr);
    }
    res.render("home", { arrProduct: productDetail });
  } catch (err) {
    res.status(500).send("Server Error");
  }
});

//api account

app.post("/login", async (req, res) => {
  const { email, password } = req.body;
  try {
    const account = await userModel.findOne({ email });
    if (!account) {
      res.status(404).json({ message: "Tài khoản không tồn tại" });
    } else {
      if (password !== account.password) {
        res.status(401).json({ message: "Sai mật khẩu" });
      } else {
        res.json(account._id);
      }
    }
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
});

app.post("/register", async (req, res) => {
  const { email, password } = req.body;
  try {
    const account = await userModel.findOne({ email });
    if (account) {
      res.status(404).message("Tài khoản đã tồn tại");
    } else {
      const user = new userModel({
        email,
        password,
      });
      await user.save();
      const account = await userModel.findOne({ email });
      res.json(account._id);
    }
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
});

app.put("/changepassword", async (req, res) => {
  const { _id, password, newPassword } = req.body;

  try {
    const account = await userModel.findOne({ _id });

    if (!account) {
      res.status(404).json({ message: "Tài khoản không tồn tại" });
    } else {
      if (password !== account.password) {
        res.status(401).json({ message: "Sai mật khẩu cũ" });
      } else {
        account.password = newPassword;
        await account.save();
        res.sendStatus(200);
      }
    }
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
});

app.get("/user", async (req, res) => {
  try {
    const user = await userModel.find();
    res.json(user);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
});

app.put("/changeStatus", async (req, res) => {
  const { _id, status } = req.body;

  try {
    const account = await userModel.findOne({ _id });

    if (!account) {
      res.status(404).json({ message: "Tài khoản không tồn tại" });
    } else {
      account.status = status;
      await account.save();
      res.json("success");
    }
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
});

app.get("/status/:id", async (req, res) => {
  const _id = req.params.id;
  try {
    const account = await userModel.findOne({ _id });
    const status = account.status;
    res.json(status);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
});

app.get("/profile/:id", async (req, res) => {
  const id = req.params.id;
  try {
    const profile = await profileModel.findOne({ userId: id });
    res.json(profile);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
});

app.post("/profile/add", async (req, res) => {
  const { userId, name, phone, address } = req.body;
  console.log(userId, name, phone, address);
  try {
    const profileMo = await profileModel.findOne({ userId });
    console.log(profileMo);
    if (profileMo) {
      await profileModel.updateOne(
        { _id: profileMo._id },
        { name, phone, address }
      );
    } else {
      const profile = new profileModel({ userId, name, phone, address });
      await profile.save();
    }
    const profile1 = await profileModel.findOne({ userId });
    res.json(profile1);
  } catch (error) {
    console.log(error.message);
    res.status(500).json({ message: error.message });
  }
});

//api product

app.get("/product", async (req, res) => {
  try {
    const products = await productModel.find();
    const productDetail = [];
    for (const product of products) {
      const brand = await brandModel.findById(product.brand);
      newArr = {
        _id: product._id,
        name: product.name,
        price: product.price,
        quantity: product.quantity,
        image: product.image,
        brand: brand.name,
      };
      productDetail.push(newArr);
    }
    res.json(productDetail);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
});
app.post("/product/add", async (req, res) => {
  const { name, price, quantity, image, brand } = req.body;

  const product = new productModel({
    name,
    price,
    quantity,
    image,
    brand,
  });

  try {
    await product.save();

    const products = await productModel.find();
    const productDetail = [];
    for (const product of products) {
      const brand = await brandModel.findById(product.brand);
      newArr = {
        _id: product._id,
        name: product.name,
        price: product.price,
        quantity: product.quantity,
        image: product.image,
        brand: brand.name,
      };
      productDetail.push(newArr);
    }
    res.json(productDetail);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
});
app.put("/product/edit/:id", async (req, res) => {
  const id = req.params.id;
  const { name, price, quantity, image, brand } = req.body;

  try {
    const result = await productModel.updateOne(
      { _id: id },
      {
        name,
        price,
        quantity,
        image,
        brand,
      }
    );
    const products = await productModel.find();
    const productDetail = [];
    for (const product of products) {
      const brand = await brandModel.findById(product.brand);
      newArr = {
        _id: product._id,
        name: product.name,
        price: product.price,
        quantity: product.quantity,
        image: product.image,
        brand: brand.name,
      };
      productDetail.push(newArr);
    }
    res.json(productDetail);
  } catch (err) {
    console.error("Lỗi khi sửa dữ liệu:", err);
    res.sendStatus(500);
  }
});

app.delete("/product/delete/:id", async (req, res) => {
  const id = req.params.id;

  try {
    await productModel.deleteOne({ _id: id });
    const products = await productModel.find();
    const productDetail = [];
    for (const product of products) {
      const brand = await brandModel.findById(product.brand);
      newArr = {
        _id: product._id,
        name: product.name,
        price: product.price,
        quantity: product.quantity,
        image: product.image,
        brand: brand.name,
      };
      productDetail.push(newArr);
    }
    res.json(productDetail);
  } catch (err) {
    console.error("Lỗi khi xoá dữ liệu:", err);
    res.sendStatus(500);
  }
});

app.get("/product/:id", async (req, res) => {
  const id = req.params.id;

  try {
    const product = await productModel.findOne({ _id: id });

    const brand = await brandModel.findById(product.brand);
    newArr = {
      _id: product._id,
      name: product.name,
      price: product.price,
      quantity: product.quantity,
      image: product.image,
      brand: brand.name,
    };
    res.json(newArr);
  } catch (err) {
    console.error("Lỗi khi xoá dữ liệu:", err);
    res.sendStatus(500);
  }
});

//api brand
app.get("/brand", async (req, res) => {
  try {
    const brand = await brandModel.find();
    res.json(brand);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
});
app.post("/brand/add", async (req, res) => {
  try {
    const { name, image } = req.body;

    const brand = new brandModel({
      name: name,
      image: image,
    });

    await brand.save();
    const arrBrand = await brandModel.find();
    res.json(arrBrand);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
});
app.put("/brand/edit/:id", async (req, res) => {
  try {
    const id = req.params.id;
    const { name, image } = req.body;
    const result = await brandModel.updateOne(
      { _id: id },
      {
        name,
        image,
      }
    );
    const arrBrand = await brandModel.find();
    res.json(arrBrand);
  } catch (err) {
    console.error("Lỗi khi sửa dữ liệu:", err);
    res.sendStatus(500);
  }
});
app.delete("/brand/delete/:id", async (req, res) => {
  try {
    const id = req.params.id;
    await brandModel.deleteOne({ _id: id });
    const arrBrand = await brandModel.find();
    res.json(arrBrand);
  } catch (err) {
    console.error("Lỗi khi xoá dữ liệu:", err);
    res.sendStatus(500);
  }
});

//api cart
app.get("/cart/:id", async (req, res) => {
  const id = req.params.id;
  try {
    const carts = await cartModel.find({ userId: id });
    const cartDetail = [];
    for (const cart of carts) {
      const product = await productModel.findOne({ _id: cart.productId });
      cartDetail.push({
        _id: cart._id,
        userId: cart.userId,
        productId: cart.productId,
        name: product.name,
        price: product.price,
        quantity: cart.quantity,
        image: product.image,
      });
    }
    res.json(cartDetail);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
});

app.post("/cart/add", async (req, res) => {
  try {
    const { userId, productId, quantity } = req.body;

    const cart = new cartModel({
      userId,
      productId,
      quantity,
    });
    await cart.save();
    res.json("success");
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
});

app.delete("/cart/delete/:userId/:id", async (req, res) => {
  const id = req.params.id;
  const userId = req.params.userId;
  try {
    await cartModel.deleteOne({ _id: id });
    const carts = await cartModel.find({ userId: userId });
    const cartDetail = [];
    for (const cart of carts) {
      const product = await productModel.findOne({ _id: cart.productId });
      cartDetail.push({
        _id: cart._id,
        userId: cart.userId,
        productId: cart.productId,
        name: product.name,
        price: product.price,
        quantity: cart.quantity,
        image: product.image,
      });
    }
    res.json(cartDetail);
  } catch (err) {
    console.error("Lỗi khi xoá dữ liệu:", err);
    res.sendStatus(500);
  }
});
//api order
app.get("/order/", async (req, res) => {
    try {
      const order = await orderModel.find();
      res.json(order);
    } catch (error) {
      res.status(500).json({ message: error.message });
    }
  });

app.get("/order/:id", async (req, res) => {
  const id = req.params.id;
  try {
    const order = await orderModel.find({ userId: id });
    res.json(order);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
});

app.post("/order/add", async (req, res) => {
    const { userId, product, address } = req.body;
  try {
    const order = new orderModel({
      userId,
      product,
      address,
    });
    for(item of product){
        const model = await productModel.findOne({name:item.name});
        model.quantity -= item.quantity;
        await model.save();
    }
    await order.save();
    res.json("Order successfull");
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
});

app.put("/changeOrder", async (req, res) => {
    const { _id, status } = req.body;
  
    try {
      const order = await orderModel.findOne({ _id });
  
      if (!order) {
        res.status(404).json({ message: "Đơn hàng không tồn tại" });
      } else {
        order.status = status;
        await order.save();
        res.json("success");
      }
    } catch (error) {
      res.status(500).json({ message: error.message });
    }
  });

//api statictical
module.exports = app;
