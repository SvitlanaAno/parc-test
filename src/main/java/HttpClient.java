import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by sanoshchenko on 1/31/16.
 */
public class HttpClient {
    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {
        //HttpClient http = new HttpClient();
        //System.out.println("Testing 1 - Send Http GET request");
        //http.sendGet();
        int i,j;
        double lat,lon;
        System.out.println("\nTesting 2 - Send Http POST request");
        for(i=0; i<10; i++){
          for(j=0; j<10; j++) {
              lon = 30.46664719 + (j*0.0004901157);
              lat = 50.38929919 + (i*0.0003122835);
              PostRequest postRequest = new PostRequest(lat, lon);
              postRequest.sendPost();
            }
        }
        //http.sendPost();

        System.out.println("HttpClient!");
    }


    }
