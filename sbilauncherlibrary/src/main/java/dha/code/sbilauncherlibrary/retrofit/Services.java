package dha.code.sbilauncherlibrary.retrofit;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface Services {

    @Multipart
    @POST("uploadPhoto")
    Call<APIResponse> updatePhoto(
            @Header("Authorization")String token,
            @Part("msisdn") RequestBody msisdn,
            @Part MultipartBody.Part image
    );

}
