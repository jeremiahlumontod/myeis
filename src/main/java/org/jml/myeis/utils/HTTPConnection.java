package org.jml.myeis.utils;

import com.google.gson.JsonObject;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;

import javax.net.ssl.*;

public class HTTPConnection {


    public static final String USER_AGENT = "Mozilla/5.0";


    public static void main(String[] args) throws Exception {


        new HTTPConnection().getHttpResponseSSL("https://localhost:8443/ssomyeis/oauth/token?grant_type=password&username=user&password=user","jml-client-id","12345");
//        new HTTPConnection().getHttpResponse("http://localhost:8080/ssomyeis/oauth/token?grant_type=password&username=user&password=user","jml-client-id","12345");
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


    public String getHttpResponse(String address, String username, String password) throws Exception {
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
        JSONObject json = new JSONObject(sb.toString());
        String access_token = (String) json.get("access_token");
        String refresh_token = (String) json.get("refresh_token");
        System.out.println("access_token: " + access_token + ", refresh_token: " + refresh_token);
        input.close();
        return sb.toString();
    }

    public String getHttpResponseSSL(String address, String username, String password) throws Exception {

        HostnameVerifier nullHostNameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        //any client app that has this ssl info will be allowed to communicate
        HttpsURLConnection.setDefaultHostnameVerifier(nullHostNameVerifier);

        // Load the self-signed server certificate
        char[] passphrase = "123456".toCharArray();
        KeyStore keyStore = KeyStore.getInstance("JKS");
//        FileInputStream in = new FileInputStream("/tomcatclient.jks");
        InputStream is  = this.getClass().getClassLoader().getResourceAsStream("tomcatclient.jks");
        keyStore.load(is, passphrase);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(keyStore, passphrase);
        KeyManager[] keyManagers = kmf.getKeyManagers();
        //tomcatclienttruststore.jks

        KeyStore trustStore = KeyStore.getInstance("JKS");
        ClassLoader classLoader = getClass().getClassLoader();
//        in = new FileInputStream("tomcatclienttruststore.jks");
        is  = this.getClass().getClassLoader().getResourceAsStream("tomcatclienttruststore.jks");
        trustStore.load(is, passphrase);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        tmf.init(trustStore);
        TrustManager[] trustManagers = tmf.getTrustManagers();

        // Create a SSLContext with the certificate
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        //sslContext.init(null, new X509TrustManager[]{new NullX509TrustManager()}, new SecureRandom());
        sslContext.init(keyManagers, trustManagers, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());


        URL url = new URL(address);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setConnectTimeout(30000); // 30 seconds time out

        conn.setSSLSocketFactory(sslContext.getSocketFactory());

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
        JSONObject json = new JSONObject(sb.toString());
        String access_token = (String) json.get("access_token");
        String refresh_token = (String) json.get("refresh_token");
        System.out.println("access_token: " + access_token + ", refresh_token: " + refresh_token);
        input.close();
        return sb.toString();
    }

}

