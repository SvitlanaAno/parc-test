import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

/**
 * Created by sanoshchenko on 2/1/16.
 */
public class PostRequest{

    private randomData data = new randomData();
    private double lat;
    private  double lon;

    public PostRequest(double lat, double lon){
        this.lat = lat;
        this.lon = lon;

    }

    public void sendPost() throws Exception {
        String mName = data.getname();
        String mCategory = data.getcategory();
        int mStations = data.getStations();
        JSONObject obj = new JSONObject();

        obj.put("name", mName);
        obj.put("address", "Fake street !@#$%6, Tel: +38 044 331 2531");
        obj.put("category", mCategory);
        JSONObject location = new JSONObject();
        location.put("lat", this.lat);
        location.put("lon", this.lon);

        obj.put("location", location);
        JSONArray nearbyStations = new JSONArray();

        JSONObject idS = new JSONObject();
        idS.put("id", mStations);
        nearbyStations.add(idS);
        obj.put("nearbyStations", nearbyStations);

        String body = obj.toJSONString();
        //System.out.println("\nSending 'POST' request : "+ body);

        try {
            String url = "http://offers-parc.cogniance.com:8080/merchants/admin";

    CloseableHttpClient client = HttpClients.createDefault();
    HttpPost post = new HttpPost(url);

    StringEntity input = new StringEntity(body);
    input.setContentType("application/json");
    post.setEntity(input);


    CloseableHttpResponse response = client.execute(post);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatusLine().getStatusCode());
        }
        try{
        System.out.println("\nSending 'POST' request to URL : "+url);
        System.out.println("Post parameters : "+post.getEntity());
        System.out.println("Response Code : "+
        response.getStatusLine().getStatusCode());

        BufferedReader rd=new BufferedReader(
        new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result=new StringBuffer();
        String line="";
        while((line=rd.readLine())!=null){
        result.append(line);
        }

        System.out.println(result.toString());
        }finally {
        response.close();
            client.close();
        }
        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}
