//
//  index.js
//  play-install-referrer-react-native
//
//  Created by Uglješa Erceg (@ugi) on 24th April 2020.
//  Copyright (c) 2020 Uglješa Erceg. All rights reserved.
//

'use strict';

import { 
    NativeModules,
} from 'react-native';

const modulePlayInstallReferrer = NativeModules.PlayInstallReferrer;

var PlayInstallReferrer = {};

PlayInstallReferrer.getInstallReferrerInfo = function(callback) {
	modulePlayInstallReferrer.getInstallReferrerInfo(callback);
};

module.exports = { PlayInstallReferrer }
