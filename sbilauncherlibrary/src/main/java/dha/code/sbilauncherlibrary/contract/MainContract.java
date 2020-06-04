package dha.code.sbilauncherlibrary.contract;

import android.content.Context;
import android.content.Intent;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface MainContract {

    interface WebResponseView {
        void onSuccessShareSosmed(List<Intent> targetedShareIntents, String title);
        void onError();
    }

    interface UploadImageView{
        void isProgress();
        void isFailure();
        void isSuccess();
    }

    interface UploadImagePresenter{
        void setUploadImage(Context context, File patch, String msisdn);
    }

    interface Intractor {

        interface OnUploadListener {
            void onProgress();
            void onFailure();
            void onSuccess();
        }

        void uploadImageFile(Context context, final MultipartBody.Part file, RequestBody msisdn, OnUploadListener onUploadListener);

    }

}
