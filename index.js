import {Platform} from 'react-native';

if (Platform.OS === 'android') {
    module.exports = require('./index.android')
} else {
    module.exports = require('./index.ios')
}
