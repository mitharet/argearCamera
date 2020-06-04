package dha.code.sbilauncherlibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import java.util.List;
import dha.code.sbilauncherlibrary.contract.MainContract;
import dha.code.sbilauncherlibrary.utilities.FileImageSelector;
import dha.code.sbilauncherlibrary.utilities.FuncShareSosmed;
import dha.code.sbilauncherlibrary.utilities.PreferenceLibrary;


public class WebAppInterface {

    private Context mContext;
    private final Activity mActivity;
    private MainContract.WebResponseView mWebResponseView;

    /** Instantiate the interface and set the context */
    public WebAppInterface(Context c, Activity activity, MainContract.WebResponseView webResponseView) {
        this.mContext = c;
        this.mActivity = activity;
        this.mWebResponseView = webResponseView;
    }

    @JavascriptInterface
    public void getPlayerData(String callback) {

    }

    @JavascriptInterface
    public void getPhoneNumber(String callback) {

    }

    @JavascriptInterface
    public void adjustEventTracker(String token, String event) { // Adjust Tracker

    }

    @JavascriptInterface
    public void gaScreenTracker(String screenName){ // GA Tracker

    }

    @JavascriptInterface
    public void gaEventScreenTracker(String screenName, String category, String action, String label){ // GA Tracker

    }

    @JavascriptInterface
    public void firebaseEventTracker(String event){ // Firebase Tracker

    }

    @JavascriptInterface
    public void openPhoneBook(String limit, String callback) {

    }

    @JavascriptInterface
    public void shareSosmed(String packagename, final String title, String body, String link){
      if (!TextUtils.isEmpty(packagename) && !TextUtils.isEmpty(title) && !TextUtils.isEmpty(body) && !TextUtils.isEmpty(link)) {

          new FuncShareSosmed(mContext, new FuncShareSosmed.ShareSosmedDelegate() {
              @Override
              public void onShareSelected(List<Intent> targetedShareIntents) {
                  mWebResponseView.onSuccessShareSosmed(targetedShareIntents, title);
              }

              @Override
              public void onFailureResponse() {
                  mWebResponseView.onError();
              }
          }).actionShareSosmed(packagename,link,body);

      }else {
          mWebResponseView.onError();
      }
    }

    @JavascriptInterface
    public void composeMessage(String sdc, String content) {

    }

    @JavascriptInterface
    public void permissionsApp(){

    }

    @JavascriptInterface
    public void purchasesUmb(String umb){

    }

    @JavascriptInterface
    public void showKeyboard(String type) {

    }

    @JavascriptInterface
    public void hideKeyboard() {

    }

    @JavascriptInterface
    public void shareTrialUrl(String trialId, String wording) {

    }

    @JavascriptInterface
    public void purchasePopUp(String title, String des_coin, String coin_value, String desc_info, String bottom_ppn) {

    }

    @JavascriptInterface
    public void purchaseCompose() {

    }


    @JavascriptInterface
    public void gpayOnSubscribe(String productid) {

    }

    @JavascriptInterface
    public void gpayOnPurchase(String type, String productid) {

    }

    @JavascriptInterface
    public void clearPreference() {

    }

    @JavascriptInterface
    public void updatePreference(String data) {
        PreferenceLibrary.getEditor(mContext).putString(PreferenceLibrary.PREF_MSISDN,data).apply();
    }

    @JavascriptInterface
    public void selectImage(String parsing) {
        FileImageSelector mImageSelector = FileImageSelector.create(mActivity, uri -> {});
        if (!TextUtils.isEmpty(parsing)) {
            if (parsing.equalsIgnoreCase("gallery")) {
                mImageSelector.openGallery();
                return;
            }else{
                mImageSelector.captureImage();
                return;
            }
        }
        mWebResponseView.onError();

    }

    @JavascriptInterface
    public void fetchAllContacts(String callback) {

    }


}
