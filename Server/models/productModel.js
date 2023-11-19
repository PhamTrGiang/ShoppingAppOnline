const mongoose = require('mongoose');

const productSchema = mongoose.Schema({
    name:{
        type:'String',
        require: true,
    },
    price:{
        type: 'number',
        default:0,
    },
    quantity:{
        type: 'number',
        default:0,
    },
    image:{
        type:'String',
        require: true,
    },
    brand:{
        type:'String',
        require: true,
    },

});

const productModel = mongoose.model('product',productSchema);
module.exports = productModel;