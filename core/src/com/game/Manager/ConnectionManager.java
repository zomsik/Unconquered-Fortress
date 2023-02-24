package com.game.Manager;

import com.game.Main;
import com.game.Server.Save;
import com.game.Server.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ConnectionManager {
    private Main game;
    public ConnectionManager(Main game)  {
        this.game = game;
    }

    public JSONObject ping()
    {

        try {
            if (game.getUsersRepository().count() >= 0)
                return new JSONObject().put("status", 200).put("message", "ResponseSuccessfulPing");
            else
                return new JSONObject().put("status", 500).put("message", "ResponseInternalServerError");
        }
        catch (Error | Exception e)
        {
            return new JSONObject().put("status", 500).put("message", "ResponseInternalServerError");
        }

    }

    public JSONObject login(JSONObject dataToSend)
    {

        try {
            User u = game.getUsersRepository().findFirstByLogin(dataToSend.getString("login"));
            if (u==null)
                return new JSONObject().put("status", 400).put("message", "ResponseNoUserWithThisLogin");

            if (!u.isCorrectPassword(dataToSend.getString("password"), u.getPassword()))
                return new JSONObject().put("status", 401).put("message", "ResponseWrongPassword");

            String token = u.generateAuthToken();

            return new JSONObject().put("status", 200).put("token",token).put("message", "ResponseLoggedSuccessfully");
        }
        catch (Error | Exception e)
        {
            System.out.println(e);
            return new JSONObject().put("status", 500).put("message", "ResponseInternalServerError");
        }
    }

    public JSONObject register(JSONObject dataToSend)
    {

        try {
            if (game.getUsersRepository().findFirstByLogin(dataToSend.getString("login"))!=null)
                return new JSONObject().put("status", 409).put("message", "ResponseLoginTaken");

            if (game.getUsersRepository().findFirstByEmail(dataToSend.getString("mail"))!=null)
                return new JSONObject().put("status", 410).put("message", "ResponseMailTaken");

            game.getUsersRepository().save(new User(dataToSend.getString("login"), dataToSend.getString("mail"), dataToSend.getString("password")));
            return new JSONObject().put("status", 200).put("message", "ResponseUserCreated");
        }
        catch (Error | Exception e)
        {
            System.out.println(e);
            return new JSONObject().put("status", 500).put("message", "ResponseInternalServerError");
        }
    }

    public JSONObject downloadSaves(JSONObject dataToSend)
    {
        try {
            System.out.println(dataToSend);
            List<Save> userSav = game.getSavesRepository().findByLogin(dataToSend.getString("login"));

            JSONArray userSaves = convertResponseToJsonArray(game.getSavesRepository().findByLogin(dataToSend.getString("login")));
            if (userSaves.length() == 0)
                return new JSONObject().put("status", 201).put("message", "ResponseNoExistingSaves");
            else
                return new JSONObject().put("status", 200).put("message", "ResponseSuccessfullyLoadedSaves").put("loadedData", userSaves);
        }
        catch (Error | Exception e)
        {
                System.out.println(e);
                return new JSONObject().put("status", 500).put("message", "ResponseInternalServerError");
        }
    }



    public JSONObject deleteSave(JSONObject dataToSend)
    {
        try {
            Save userSaveToDelete = game.getSavesRepository().findOneByLoginAndProfileNumber(dataToSend.getString("login"),dataToSend.getInt("profileNumber"));

            if (userSaveToDelete != null) {
                game.getSavesRepository().delete(userSaveToDelete);
                return new JSONObject().put("status", 200).put("message", "ResponseSuccessfullyDeletedSave");
            }
            else
                return new JSONObject().put("status", 210).put("message", "ResponseSaveDoesntExist");
        }
        catch (Error | Exception e)
        {
            System.out.println(e);
            return new JSONObject().put("status", 500).put("message", "ResponseInternalServerError");
        }

    }

    public JSONObject uploadSave(JSONObject dataToSend)
    {
        try {

            Save userSave = game.getSavesRepository().findOneByLoginAndProfileNumber(dataToSend.getString("login"),dataToSend.getInt("profileNumber"));

            if (userSave!=null) {
                game.getSavesRepository().save(new Save(userSave.getId(), dataToSend));
                return new JSONObject().put("status", 200).put("message", "ResponseSuccessfullySaved");
            }
            else
            {
                game.getSavesRepository().save(new Save(dataToSend));
                return new JSONObject().put("status", 200).put("message", "ResponseSuccessfullyCreatedSave");

            }
      }
        catch (Error | Exception e)
        {
            System.out.println(e);
            return new JSONObject().put("status", 500).put("message", "ResponseInternalServerError");
        }
    }


    private JSONArray convertResponseToJsonArray(List<Save> saves) {
        JSONArray loadedData = new JSONArray();

        for (Save save : saves)
            loadedData.put(save.getAsJsonObject());

        return loadedData;

    }


    public JSONObject requestSend(JSONObject dataToSend, String Url) {

        JSONObject response;

        switch (Url)
        {
            case "api/ping" -> {
                response = ping();
            }
            case "api/login" -> {
                response = login(dataToSend);
            }
            case "api/register" -> {
                response = register(dataToSend);
            }
            case "api/downloadSaves" -> {
                response = downloadSaves(dataToSend);
            }
            case "api/deleteSave" -> {
                response = deleteSave(dataToSend);
            }
            case "api/uploadSave" -> {
                response = uploadSave(dataToSend);
            }
            default -> {
                response = new JSONObject().put("status",110).put("message","ResponseErrorConnectionWithServer");
            }
        }

        return response;


    }

}
