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
 * Created by sanoshchenko on 2/3/16.
 */
public class PostOffers {
    private randomData data = new randomData();
    private double start = 1451606400000;
    private double end = 1467331200000;
    private int merchant;
    private String[] mDayPart;
    private String category;
    private String[] day;



    public PostOffers(int merchant, String category){
        this.merchant = merchant;
        this.category = category;
    }
    public void sendPost() throws Exception {

        String[] mDayPart = data.getDayPart();
        String text = "Offer with category: "+ category + "and dayPart = " + mDayPart.toString() + "and dayWeek: " + day.toString();

        JSONObject obj = new JSONObject();

        obj.put("text",text);
        obj.put("offerType", );
        obj.put("quantity", 1000);
        obj.put("minCostToApply", 39.99);
        obj.put("itemBonusShortText", null );
        obj.put("savings", 43);
        obj.put("imageLocation","https://img.grouponcdn.com/deal/ch53eiVdHsQfwqYUQwcb/NP-2048x1229/v1/c312x189.jpg");
        obj.put("activationInstant", start);
        obj.put("expirationInstant", end);

        JSONArray daysOfWeek = new JSONArray();
        daysOfWeek.add(day);
        obj.put("daysOfWeek", daysOfWeek);
        obj.put("startLocalTime", "09:00");
        obj.put("startLocalTime", "23:00");
        JSONArray dayParts = new JSONArray();
        dayParts.add(mDayPart);
        obj.put("dayParts", dayParts);


        JSONObject merchant1 = new JSONObject();
        merchant1.put("id", this.merchant);
        obj.put("merchant", merchant1);


        JSONArray Interest = new JSONArray();

        JSONObject idInterest = new JSONObject();
        idInterest.put("id", mInterest1);
        idInterest.put("id", mInterest2);
        Interest.add(idInterest);
        obj.put("interests", Interest);

        String body = obj.toJSONString();
        //System.out.println("\nSending 'POST' request : "+ body);

        try {
            String url = "http://offers-parc.cogniance.com:8080/offers/admin";

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
