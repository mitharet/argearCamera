package dha.code.sbilauncherlibrary.presenters;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import java.io.File;

import dha.code.sbilauncherlibrary.contract.MainContract;
import dha.code.sbilauncherlibrary.utilities.ProgressRequestBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadImagePresenterImpl implements MainContract.UploadImagePresenter, MainContract.Intractor.OnUploadListener {

    private MainContract.UploadImageView mUploadImageView;
    private MainContract.Intractor mIntractor;

    public UploadImagePresenterImpl(MainContract.UploadImageView uploadImageView, MainContract.Intractor intractor){
        this.mUploadImageView = uploadImageView;
        this.mIntractor = intractor;
    }

    @Override
    public void setUploadImage(Context context, File patch, String msisdn) {
        if (context != null && patch != null && !TextUtils.isEmpty(msisdn)){
            setDataMultipart(context,patch, msisdn);
        }else {
            mUploadImageView.isFailure();
        }
    }

    @Override
    public void onProgress() {
        mUploadImageView.isProgress();
    }

    @Override
    public void onFailure() {
        mUploadImageView.isFailure();
    }

    @Override
    public void onSuccess() {
        mUploadImageView.isSuccess();
    }

    private void setDataMultipart(Context context, File mproPicPath, final String msisdn) {
        // multipart
        MultipartBody.Part _file;
        if (mproPicPath != null) {
            File file = new File(String.valueOf(mproPicPath));
            // ini progress listener
            ProgressRequestBody.ProgressListener progressListener = (num, transferred, totalSize) -> {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                });
            };
            // init request body
            ProgressRequestBody requestFileBody = new ProgressRequestBody(file, "multipart/form-data", progressListener);
            _file = MultipartBody.Part.createFormData("image", file.getName(), requestFileBody);
            RequestBody _msisdn = RequestBody.create(MediaType.parse("text/plain"), msisdn);
            mIntractor.uploadImageFile(context, _file, _msisdn, this);
        }
    }

}
