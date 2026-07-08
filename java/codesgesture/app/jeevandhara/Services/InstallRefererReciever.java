package codesgesture.app.jeevandhara.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class InstallRefererReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                String referral = extras.getString("referrer");
              //  Logger.e("ReferCode --> ", referral);
                if (referral != null) {
                    if (!referral.equals("utm_source=google-play&utm_medium=organic")) {
                        if (!referral.equals("com.android.chrome")) {
                            String referralCode = referral; // Store in sharedpreferences
                            UserUtil.ShowMsg(referralCode,context);
                        }
                    }
                }
            }
        }
    }


}