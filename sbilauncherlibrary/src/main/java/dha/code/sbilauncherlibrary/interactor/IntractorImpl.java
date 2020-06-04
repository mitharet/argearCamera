package dha.code.sbilauncherlibrary.interactor;

import android.content.Context;
import dha.code.sbilauncherlibrary.contract.MainContract;
import dha.code.sbilauncherlibrary.retrofit.APIResponse;
import dha.code.sbilauncherlibrary.retrofit.ServicesFactory;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class IntractorImpl implements MainContract.Intractor {

    @Override
    public void uploadImageFile(Context context, final MultipartBody.Part file, RequestBody msisdn, final OnUploadListener onUploadListener) {
        onUploadListener.onProgress();
        Call<APIResponse> call = ServicesFactory.getService().updatePhoto("eyJhcHBfYWxpYXMiOiJ1dGVuZ2dvIiwiYXBwX25hbWUiOiJVbGFyIFRlbmdnbyIsImNwX2lkIjoxfQ==aGeWlvxnnXLxKA7fMKH1",
                msisdn, file);
        call.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccessful()){
                    onUploadListener.onSuccess();
                    return;
                }
                onUploadListener.onFailure();
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                onUploadListener.onFailure();
            }
        });
    }

}
