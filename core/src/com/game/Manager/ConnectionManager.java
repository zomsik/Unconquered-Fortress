package com.game.Manager;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
public class ConnectionManager {
    private Socket socket;

    final private String hostname = "http://localhost";
    final private int PORT = 9000;

    public ConnectionManager()  {
    }


    public JSONObject requestSend(JSONObject dataToSend) throws IOException {

        try {
            URL url = new URL("http://localhost:9000/api/login");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");

            //con.setRequestProperty("Data", dataToSend.toString());

            for (String keyStr : dataToSend.keySet()) {
                Object keyValue = dataToSend.get(keyStr);
                con.setRequestProperty(keyStr, keyValue.toString());

                //Print key and value
                //System.out.println("key: "+ keyStr + " value: " + keyValue);

                //for nested objects iteration if required
                //if (keyvalue instanceof JSONObject)
                //    printJsonObject((JSONObject)keyvalue);
            }


            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            //JSONObject responseObject = new JSONObject(response.toString().replaceAll("\"","\\\""));

            return new JSONObject(response.toString().replaceAll("\"","\\\""));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
