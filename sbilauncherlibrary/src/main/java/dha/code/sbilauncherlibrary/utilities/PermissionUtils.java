package dha.code.sbilauncherlibrary.utilities;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;


public class PermissionUtils {

    static boolean hashPermission(Context context, String... permissions){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if(ContextCompat.checkSelfPermission(context, permission) != PermissionChecker.PERMISSION_GRANTED)
                    return false;
            }
        }
        return true;
    }

    static void requestPermission(final Activity activity, final String[] permissions, final int request){

        boolean needExplanation = false;
        for(String perm:permissions){
            needExplanation = ActivityCompat.shouldShowRequestPermissionRationale(activity, perm);
            if(needExplanation)
                break;
        }

        if(needExplanation){
            new AlertDialog.Builder(activity)
                    .setMessage("This application needs some permission")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(activity, permissions, request);
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create()
                    .show();
        }
        else
            ActivityCompat.requestPermissions(activity, permissions, request);
    }

    public static void checkNotificationPolicyAccess(final Context ctx){
        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        assert notificationManager != null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !notificationManager.isNotificationPolicyAccessGranted()) {
            new AlertDialog.Builder(ctx)
                    .setMessage("This application needs to allow Notification Policy")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                            ctx.startActivity(intent);
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create()
                    .show();
        }
    }

}
