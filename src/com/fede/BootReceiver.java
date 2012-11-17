/*
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package com.fede;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import com.fede.Utilities.GeneralUtils;
import com.fede.Utilities.PrefUtils;

public class BootReceiver extends BroadcastReceiver {	
	@Override
	public void onReceive(Context c, Intent intent) {
		if(PrefUtils.homeAloneEnabled(c)){
			GeneralUtils.notifyEvent(c.getString(R.string.active_state), c.getString(R.string.active_state), DroidContentProviderClient.EventType.COMMAND, c);

            PackageManager pm = c.getPackageManager();
            ComponentName myReceiverName = new ComponentName(c, IncomingCallReceiver.class);
            pm.setComponentEnabledSetting(myReceiverName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		}
	}
	
}
