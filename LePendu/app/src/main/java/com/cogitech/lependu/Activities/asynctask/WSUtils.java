package com.cogitech.lependu.Activities.asynctask;

import android.util.Log;

import com.cogitech.lependu.Activities.Models.Terminal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Response;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class WSUtils {


    private static final String URL_TERMINAL="http://192.168.43.145:12345/Terminal/?";
    private static final Gson gson = new Gson();

    public static Terminal getTerminal(Terminal terminal ) throws Exception {
        String url = URL_TERMINAL+"phone="+terminal.getPhone()+"&password="+terminal.getPassword();
        Response response = OkHttpUtils.sendGetOkHttpRequest(url);
        if (response.code() != HttpURLConnection.HTTP_OK){
            throw new Exception("reponse du service incorrect :"+response.code());
        }else {

            InputStreamReader inputStreamReader= new InputStreamReader(response.body().byteStream());
            Terminal terminalRecu= gson.fromJson(inputStreamReader , new TypeToken<Terminal >(){}.getType() );

            System.out.println("reponses : "+ inputStreamReader.toString() );

            Log.v("TAG", inputStreamReader.toString());
           inputStreamReader.close();

            return  terminalRecu;
        }
    }

}
