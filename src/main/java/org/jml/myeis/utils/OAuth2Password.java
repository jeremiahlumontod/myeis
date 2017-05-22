package org.jml.myeis.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.lang.reflect.Type;
import javax.net.ssl.HttpsURLConnection;

// Google Gson Libraries used for Json Parsing
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Very basic console app that demonstrates how to make an access token request on
 * behalf of a LearningStudio user via the OAuth 2.0 Password grant type
 */
public class OAuth2Password {

    public static void main( final String[] args) throws Exception
    {
        // Setup the variables necessary to make the Request
        String grantType = "password";
//        String applicationID = "{applicationId}";
//        String clientString = "{clientString}";
//        String username = "{username}";
//        String password = "{password}";
//        String url = "https://api.learningstudio.com/token";
        String applicationID = "jml-client-id";
        String clientString = "12345";
        String username = "user";
        String password = "user";
//        String url = "http://localhost:8080/oauth/token?grant_type=password&username=user&password=user";
//        String url = "https://localhost:8080/ssomyeis/oauth/token?grant_type=password&username=user&password=user";
        String url = "https://localhost:8443/ssomyeis/oauth/token";
        HttpsURLConnection httpConn = null;
//        HttpURLConnection httpConn = null;
        BufferedReader in = null;

        try {

            // Create the data to send
            StringBuilder data = new StringBuilder();
            data.append("grant_type=" + URLEncoder.encode(grantType, "UTF-8"));
            data.append("&amp;client_id=" + URLEncoder.encode(applicationID, "UTF-8"));
            data.append("&amp;username=" + URLEncoder.encode(clientString + "\\" + username, "UTF-8"));
            data.append("&amp;password=" + URLEncoder.encode(password, "UTF-8"));

            // Create a byte array of the data to be sent
            byte[] byteArray = data.toString().getBytes("UTF-8");

            // Setup the Request
            URL request = new URL(url);
            httpConn = (HttpsURLConnection)request.openConnection();
//            httpConn = (HttpURLConnection)request.openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConn.setRequestProperty("Content-Length", "" + byteArray.length);
            httpConn.setDoOutput(true);

            // Write data
            OutputStream postStream = httpConn.getOutputStream();
            postStream.write(byteArray, 0, byteArray.length);
            postStream.close();

            // Send Request &amp; Get Response
            InputStreamReader reader = new InputStreamReader(httpConn.getInputStream());
            in = new BufferedReader(reader);

            // Get the Json reponse containing the Access Token
            String json = in.readLine();
            System.out.println("Json String = " + json);

            // Parse the Json response and retrieve the Access Token
            Gson gson = new Gson();
            Type mapType  = new TypeToken<Map<String,String>>(){}.getType();
            Map<String,String> ser = gson.fromJson(json, mapType);
            String accessToken = ser.get("access_token");
            System.out.println("Access Token = " + accessToken);

        } catch (java.io.IOException e) {

            // This exception will be raised if the server didn't return 200 - OK
            // Retrieve more information about the error
            System.out.println(e.getMessage());

        } finally {

            // Be sure to close out any resources or connections
            if (in != null) in.close();
            if (httpConn != null) httpConn.disconnect();
        }
    }
}
