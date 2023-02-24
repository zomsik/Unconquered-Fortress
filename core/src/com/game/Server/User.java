package com.game.Server;


import com.game.Entity.Base;
import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCrypt;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;

@Document("users")
public class User {

    @Id
    private String id;
    private String login;
    private String email;
    private String password;

    public User() {
    }

    public User(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(10));

    }

    public boolean isCorrectPassword(String password, String stored_password) {
        if (BCrypt.checkpw(password, stored_password))
            return true;
        else
            return false;
    }

    public String getPassword() {
        return password;
    }

    private String encode(JSONObject j) {
        return Base64.getEncoder().encodeToString(j.toString().getBytes(StandardCharsets.UTF_8));
    }

    public String generateAuthToken() {
        JSONObject header = new JSONObject();
        JSONObject payload = new JSONObject();

        header.put("alg", "HS256");
        header.put("typ", "JWT");

        payload.put("_id", this.id);
        payload.put("login", this.login);

        LocalDateTime ldt = LocalDateTime.now();
        payload.put("iat", ldt.toEpochSecond(ZoneOffset.UTC));

        LocalDateTime ldt2 = LocalDateTime.now().plusDays(7);
        payload.put("exp", ldt2.toEpochSecond(ZoneOffset.UTC));

        encode(header);
        String secret = "abcdefg";
        byte[] secretByteArray = Base64.getEncoder().encode(secret.getBytes());

        Mac sha256_HMAC = null;
        try {
            sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secretByteArray, "HmacSHA256");
            sha256_HMAC.init(secret_key);
            String hash = Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(payload.toString().getBytes()));
            return encode(header) + "." + encode(payload) + "." + hash;
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }

        return "";


    }
}