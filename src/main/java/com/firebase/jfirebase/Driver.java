package com.firebase.jfirebase;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: reiz
 * Date: 4/21/12
 * Time: 11:43 AM
 */
public class Driver implements IDriver {

    private String channel = "YOUR_CHANNEL";
    private String key = "YOUR_TOKEN_KEY";
    private final static String QUATA = "\"";

    public boolean write(Map<String, String> map){
        try{
            StringBuffer sb = new StringBuffer();
            sb.append("{");
            for (String key: map.keySet()){
                sb.append(QUATA);
                sb.append(key);
                sb.append(QUATA);
                sb.append(" : ");
                sb.append(QUATA);
                sb.append(map.get(key));
                sb.append(QUATA);
                sb.append(",");
            }
            String data = sb.toString();
            data = data.substring(0, data.length() -1);
            String node = data + "}";
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(getChannelUrl());
            StringEntity entity = new StringEntity( node );
            httppost.setEntity( entity );
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");
            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == 200)
                return true;
            else
                return false;
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public Reader read(String uri) {
        try{
            StringBuffer sb = new StringBuffer();
            sb.append(channel);
            sb.append("/");
            sb.append(uri);
            sb.append(".json");
            addKey(sb);
            String url = sb.toString();
            return getResultReader(url);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean delete(String uri) {
        try{
            StringBuffer sb = new StringBuffer();
            sb.append(channel);
            sb.append("/");
            sb.append(uri);
            sb.append(".json");
            addKey(sb);
            String url = sb.toString();
            HttpDelete delete = new HttpDelete(url);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(delete);
            if (response.getStatusLine().getStatusCode() == 200)
                return true;
            else
                return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public String getChannelUrl(){
        StringBuffer sb = new StringBuffer();
        sb.append(channel);
        sb.append(".json");
        addKey(sb);
        return sb.toString();
    }

    private void addKey(StringBuffer sb){
        if (key != null) {
            sb.append("?key=");
            sb.append(key);
        }
    }

    public Reader getResultReader(String resource) throws Exception {
        URL url = new URL(resource);
        URLConnection connection = url.openConnection();
        connection.setConnectTimeout(30000);
        return new InputStreamReader(connection.getInputStream());
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}