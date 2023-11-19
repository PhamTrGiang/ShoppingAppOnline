const express = require("express");
const mongoose = require("mongoose");
const expressHbs = require('express-handlebars');

const app = express();

const useController = require('./controllers/controller');

// Cấu hình để chạy file .hbs
app.engine('.hbs', expressHbs.engine({
    defaultLayout: null,
    extname: '.hbs',
    runtimeOptions: {
        allowProtoPropertiesByDefault: true,
        allowProtoMethodsByDefault: true,
    },
}));

app.set('view engine', '.hbs');

app.use(express.json());

app.use(express.urlencoded({ extended: true }));

// connect to MongoDB
mongoose.connect('mongodb://127.0.0.1:27017/AppShopingOnline', {
  useNewUrlParser: true,
  useUnifiedTopology: true,
})
  .then(() => {
    console.log('Connected to MongoDB');
    // Run the server after connect is successful
    app.listen(3000, () => {
      console.log('Server started on port 3000');
    });
  })
  .catch((error) => {
    console.error('Failed to connect to MongoDB:', error);
  });


app.use(useController);
