package org.jml.myeis.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;
public class HTTPConnection {


    public static final String USER_AGENT = "Mozilla/5.0";


    public static void main(String[] args) throws Exception {
//        HTTPConnection.sendGet();
//        HTTPConnection.getHttpResponse("https://localhost:8443/ssomyeis/oauth/token?grant_type=password&username=user&password=user","user","user");
        HTTPConnection.getHttpResponse("http://localhost:8080/ssomyeis/oauth/token?grant_type=password&username=user&password=user","jml-client-id","12345");
//        HTTPConnection.getHttpResponse("http://localhost:8080/ssomyeis/oauth/token","user","user");
    }

    // HTTP GET request
    public static void sendGet() throws Exception {

        //String url = "https://search-socialstream-hackathon-kvm657kxizekayldaiioajol4i.us-east-1.es.amazonaws.com/socialstream/artist/_search";
        String url = "https://www.google.com";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

    // HTTP POST request
//    private void sendPost() throws Exception {
//
//        String url = "https://selfsolve.apple.com/wcResults.do";
//        URL obj = new URL(url);
//        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
//
//        //add reuqest header
//        con.setRequestMethod("POST");
//        con.setRequestProperty("User-Agent", USER_AGENT);
//        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
//        con.setH
//        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
//
//        // Send post request
//        con.setDoOutput(true);
//        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//        wr.writeBytes(urlParameters);
//        wr.flush();
//        wr.close();
//
//        int responseCode = con.getResponseCode();
//        System.out.println("\nSending 'POST' request to URL : " + url);
//        System.out.println("Post parameters : " + urlParameters);
//        System.out.println("Response Code : " + responseCode);
//
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(con.getInputStream()));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();
//
//        //print result
//        System.out.println(response.toString());
//
//    }
//


    public static String getHttpResponse(String address, String username, String password) throws Exception {
        URL url = new URL(address);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(30000); // 30 seconds time out

        if (username != null && password != null){
            String user_pass = username + ":" + password;
            String encoded = Base64.encodeBase64String( user_pass.getBytes() );
            conn.setRequestProperty("Authorization", "Basic " + encoded);
        }
        conn.setRequestMethod("POST");
        String line = "";
        StringBuffer sb = new StringBuffer();
        BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()) );
//        System.out.println("response: " + input.toString());
        while((line = input.readLine()) != null) {
            sb.append(line);
        }
        System.out.println("response: " + sb.toString());
        input.close();
        return sb.toString();
    }
}

