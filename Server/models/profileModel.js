const mongoose = require('mongoose');

const profileSchema = mongoose.Schema({
    userId:{
        type:'String',
        require: true,
    },
    name:{
        type:'String',
        require: true,
    },
    phone:{
        type:'String',
        require: true,
    },
    address:{
        type:'String',
        require: true,
    },

});

const profileModel = mongoose.model('profile',profileSchema);
module.exports = profileModel;