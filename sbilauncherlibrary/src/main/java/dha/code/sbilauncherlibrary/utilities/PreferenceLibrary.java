package dha.code.sbilauncherlibrary.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;


public class PreferenceLibrary {

    @SuppressLint("StaticFieldLeak")
    private static PreferenceLibrary instance;
    private Context context;

    private PreferenceLibrary(Context context) {
        this.context = context;
    }

    public static PreferenceLibrary init(Context context){
        if (instance == null){
            instance = new PreferenceLibrary(context);
        }
        return instance;
    }

    public static PreferenceLibrary getInstance(){
        return instance;
    }


    private Context mContext;
    private static final String NAME = "sbi_library_preference";

    //SETUP
    public static final String PREF_MSISDN = "msisdn";


    public static SharedPreferences getPref(Context context){
        return context.getSharedPreferences(TextUtils.isEmpty(NAME)?"":NAME, 0);
    }

    public static SharedPreferences.Editor getEditor(Context context){
        return context.getSharedPreferences(TextUtils.isEmpty(NAME)?"":NAME, Context.MODE_PRIVATE).edit();
    }
}
