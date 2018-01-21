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
            //"https://webcamstravel.p.mashape.com/webcams/list/continent=NA?lang=en&show=webcams%3Aimage%2Clocation%2Clive%2Cstatistics%2Curl%2Cuser"
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
