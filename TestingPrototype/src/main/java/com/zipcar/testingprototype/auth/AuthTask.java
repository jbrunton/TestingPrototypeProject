package com.zipcar.testingprototype.auth;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

class AuthTask extends AsyncTask<Void, Void, Void> {

    private AuthProvider authProvider;
    private String username, password;

    private int responseCode;
    private AuthResponse response;

    public AuthTask(final AuthProvider authProvider, final String username, final String password) {
        this.authProvider = authProvider;
        this.username = username;
        this.password = password;
    }

    @Override
    protected void onPostExecute(Void param) {
        if ( responseCode == HttpsURLConnection.HTTP_OK ) {
            authProvider.postAuthResponse(response);
        }
//        else if ( responseCode == HttpsURLConnection.HTTP_UNAUTHORIZED ) {
//            activity.onAuthFailure();
//        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        OkHttpClient httpClient = new OkHttpClient();
        try {
            HttpsURLConnection connection = (HttpsURLConnection) httpClient.open(new URL("https://" + "api.zipcar.com" + "/api/1.0.2/json/login"));
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Basic "
                    + Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP));

            responseCode = connection.getResponseCode();

            if ( responseCode == HttpsURLConnection.HTTP_OK ) {
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                response = new Gson().fromJson(responseReader, AuthResponse.class);

            } else {
                Log.e("ZipcarAndroidLogCat", "Received status: " + connection.getResponseCode());
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
