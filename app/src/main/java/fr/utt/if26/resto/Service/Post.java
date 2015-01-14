package fr.utt.if26.resto.Service;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import fr.utt.if26.resto.Resto;

/**
 * Created by soedjede on 11/01/15 for Resto
 */
public class Post {
    public static String postData(String endpoint, List<NameValuePair> params) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(Resto.server_address + endpoint);

        try {
            httppost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == 401){
                return null;
            }
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line;
            String json = "";
            while ((line = rd.readLine()) != null) {
                json += line;
            }
            return json;
        } catch (ClientProtocolException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public static String postData(String endpoint, List<NameValuePair> params, String token) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(Resto.server_address + endpoint);
        httppost.addHeader("x-access-token", token);
        try {
            httppost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == 401){
                return null;
            }
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line;
            String json = "";
            while ((line = rd.readLine()) != null) {
                json += line;
            }
            return json;
        } catch (ClientProtocolException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }
}
