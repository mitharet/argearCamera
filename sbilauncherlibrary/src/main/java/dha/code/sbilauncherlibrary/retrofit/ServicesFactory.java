package dha.code.sbilauncherlibrary.retrofit;

import java.util.concurrent.TimeUnit;
import dha.code.sbilauncherlibrary.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Yudha Pratama Putra on 15/08/18.
 */
public class ServicesFactory {
    private static boolean ENABLE_LOGGING = BuildConfig.DEBUG;

    /**
     * Staging
     */
    public static Services getServiceStaging() {
        //Staging
        String STAGING_URL = "";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(STAGING_URL)
                .client(generateClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(Services.class);
    }

    /**
     * Production Local
     *
     */
    public static Services getService() {
        /** production **/
        //Production
        String PRODUCTION_URL = "http://117.54.201.46:12080/core_games/api/v1/arcade/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PRODUCTION_URL)
                .client(generateClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(Services.class);

        /** staging **/
//        return getServiceStaging();
    }

    private static OkHttpClient generateClient() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);

        if (ENABLE_LOGGING) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(loggingInterceptor);
        }
        return clientBuilder.build();
    }
}
