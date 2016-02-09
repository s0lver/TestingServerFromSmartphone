package backgroundengine.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import backgroundengine.servicios.ServicioCentral;

public class BootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, ServicioCentral.class);
        context.startService(i);
        Log.i("BootBroadcastReceiver", "Boot Service started");
    }
}
