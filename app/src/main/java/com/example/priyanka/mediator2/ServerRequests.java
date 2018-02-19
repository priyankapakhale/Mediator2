package com.example.priyanka.mediator2;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;

import com.example.priyanka.mediator2.Models.User;

import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by priyanka on 1/23/18.
 */

public class ServerRequests {
    ProgressDialog progressDialog;
    private static final int CONNECTION_TIMEOUT = 15000;
    private static final String SERVER_ADDRESS = "https://mediator12345.000webhostapp.com/";

    public ServerRequests(Context context)
    {}

    public void storeDataInBackground(User user, GetUserCallback callback)
    {

    }

    public class StoreDataAsyncTask extends AsyncTask<Void, Void, Void> {

        User user;
        GetUserCallback callback;

        public StoreDataAsyncTask(User user, GetUserCallback callback)
        {
            this.user = user;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(SERVER_ADDRESS+"register.php")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                Log.d("jsonresponse",response.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }
    }
}
