const mongoose = require('mongoose');

const brandSchema = mongoose.Schema({
    name:{
        type:'String',
        require: true,
    },
    image:{
        type:'String',
        require: true,
    }
});

const brandModel = mongoose.model('brand',brandSchema);
module.exports = brandModel;