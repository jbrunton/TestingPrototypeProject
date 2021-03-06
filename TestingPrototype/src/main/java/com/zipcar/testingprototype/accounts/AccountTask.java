package com.zipcar.testingprototype.accounts;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.zipcar.testingprototype.models.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

class AccountTask extends AsyncTask<Void, Void, Void> {

    private AccountProvider accountProvider;
    private Session session;

    private int responseCode;
    private AccountResponse response;

    public AccountTask(final AccountProvider accountProvider, final Session session) {
        this.accountProvider = accountProvider;
        this.session = session;
    }

    @Override
    protected void onPostExecute(Void param) {
        if ( responseCode == HttpsURLConnection.HTTP_OK ) {
            accountProvider.postResponse(response);
        }
        else {
            accountProvider.postError(responseCode);
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        OkHttpClient httpClient = new OkHttpClient();
        try {
            HttpsURLConnection connection = (HttpsURLConnection) httpClient.open(new URL("https://" + "api.zipcar.com" + "/api/1.0.2/json//member/accounts"));
            connection.setRequestMethod("POST");
            connection.setRequestProperty("X-User-Session", session.getToken());

            responseCode = connection.getResponseCode();

            if ( responseCode == HttpsURLConnection.HTTP_OK ) {
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                response = new Gson().fromJson(responseReader, AccountResponse.class);

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
