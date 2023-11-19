const mongoose = require('mongoose');

const userSchema = mongoose.Schema({
    email:{
        type:'String',
        require: true,
    },
    password:{
        type:'String',
        require: true,
    },
    status:{
        type:'number',
        default:0,
    }
});

const userModel = mongoose.model('user',userSchema);
module.exports = userModel;