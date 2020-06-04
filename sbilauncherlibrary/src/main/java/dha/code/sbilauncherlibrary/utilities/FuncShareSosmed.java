package dha.code.sbilauncherlibrary.utilities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.provider.Telephony;
import java.util.ArrayList;
import java.util.List;

public class FuncShareSosmed {

    private final Context mContext;
    private final ShareSosmedDelegate mListener;

    public FuncShareSosmed(Context context, ShareSosmedDelegate listener){
        this.mContext = context;
        this.mListener = listener;
    }

    public void actionShareSosmed(String packageName, String link, String shareBody) {
        try {
            if (packageName.equals("sms")){
                packageName = Telephony.Sms.getDefaultSmsPackage(mContext);
            }
            List<Intent> targetedShareIntents = new ArrayList<>();
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");
            List<ResolveInfo> resInfo = mContext.getPackageManager().queryIntentActivities(share, 0);
            if (resInfo.size() > 0) {
                for (ResolveInfo info : resInfo) {
                    Intent targetedShare = new Intent(android.content.Intent.ACTION_SEND);
                    if (packageName.contains(info.activityInfo.packageName.toLowerCase())) {
                        targetedShare.setType("text/plain");
                        targetedShare.putExtra(Intent.EXTRA_TEXT, shareBody+" "+link);
                        targetedShare.setPackage(info.activityInfo.packageName.toLowerCase());
                        targetedShareIntents.add(targetedShare);
                    }
                }

                if (targetedShareIntents.size() > 0) {
                    mListener.onShareSelected(targetedShareIntents);
                } else {
                    mListener.onFailureResponse();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            mListener.onFailureResponse();
        }
    }

    public interface ShareSosmedDelegate{
        void onShareSelected(List<Intent> targetedShareIntents);
        void onFailureResponse();
    }

}
