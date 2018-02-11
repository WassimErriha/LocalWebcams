package com.example.wassim.localwebcams;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class FetchWebcams extends AsyncTask<String, Void, String> {

    OkHttpClient client;

    @Override
    protected void onPreExecute() {
        client = new OkHttpClient();
    }

    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        try {
            response = run(strings[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .header("X-Mashape-Key", "UEULR7QTCKmshXW602gxzrlePd2gp1jlMGHjsnWnkXI0Gmt3IX")
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
