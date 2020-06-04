package dha.code.sbilauncherlibrary.retrofit;

import com.google.gson.annotations.SerializedName;


public abstract class BaseResponse {
    @SerializedName("code")
    public String code;
    public String error_message;
    public boolean isSuccessful(){
        return code.equalsIgnoreCase("200");
    }

}
