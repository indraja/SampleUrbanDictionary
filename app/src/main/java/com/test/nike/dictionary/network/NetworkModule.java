package com.test.nike.dictionary.network;

import java.io.IOException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.util.concurrent.TimeUnit.SECONDS;

public class NetworkModule {

    private static final String API_HEADER_1 = "x-rapidapi-host";
    private static final String API_HEADER_2 = "x-rapidapi-key";
    private static final String API_HEADER_HOST_VALUE = "mashape-community-urban-dictionary.p.rapidapi.com";
    private static final String API_HEADER_KEY_VALUE = "38f3e80241msh71a0a94b9e61d15p1b22f7jsn3efd1de9f4d4";
    private static Retrofit retrofit;

    //Define the base URL
    private static final String BASE_URL = "https://mashape-community-urban-dictionary.p.rapidapi.com";

    /**
     * Create trust manager to access secured network layers
     *
     * @return
     */
    private static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create the Retrofit instance
     *
     * @return
     */
    public static Retrofit getRetrofitInstance() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder()
                        .addHeader(API_HEADER_1, API_HEADER_HOST_VALUE)
                        .addHeader(API_HEADER_2, API_HEADER_KEY_VALUE);
                return chain.proceed(builder.build());
            }
        };
        OkHttpClient.Builder httpClient2 = getUnsafeOkHttpClient();
        httpClient2.addNetworkInterceptor(interceptor);
        httpClient2.readTimeout(45, SECONDS);
        httpClient2.writeTimeout(45, SECONDS);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient2.addInterceptor(logging);
        OkHttpClient client = httpClient2.build();

        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
