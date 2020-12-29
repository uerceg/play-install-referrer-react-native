//
//  index.js
//  play-install-referrer-react-native
//  version: 1.1.6
//
//  Created by Uglješa Erceg (@uerceg) on 24th April 2020.
//  Copyright (c) 2020 uerceg. All rights reserved.
//

'use strict';

import { 
    NativeModules,
    NativeEventEmitter,
} from 'react-native';

let modulePlayInstallReferrer = NativeModules.PlayInstallReferrer;
let modulePlayInstallReferrerEmitter = new NativeEventEmitter(NativeModules.PlayInstallReferrer);

var PlayInstallReferrer = {};

PlayInstallReferrer.getInstallReferrerInfo = function(callback) {
	// subscribe to get play install referrer value if successfully read
	const subscriptionValue = modulePlayInstallReferrerEmitter.addListener('play_install_referrer_value', (playInstallReferrerValue) => {
		callback(playInstallReferrerValue, null);
		// clean up subscriptions if they exist
		if (subscriptionValue != null) {
			subscriptionValue.remove();
		}
		if (subscriptionError != null) {
			subscriptionError.remove();
		}
	});
	// subscribe to get error in case play install referrer value reading failed
	const subscriptionError = modulePlayInstallReferrerEmitter.addListener('play_install_referrer_error', (error) => {
		callback(null, error);
		// clean up subscriptions if they exist
		if (subscriptionValue != null) {
			subscriptionValue.remove();
		}
		if (subscriptionError != null) {
			subscriptionError.remove();
		}
	});
	// invoke native API
	modulePlayInstallReferrer.getInstallReferrerInfo();
};

module.exports = { PlayInstallReferrer }
