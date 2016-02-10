package org.perlovka.parc;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

/**
 * Created by sanoshchenko on 2/5/16.
 */
public class ApiRequestSender {

    private static final Logger LOG = LoggerFactory.getLogger(ApiRequestSender.class);
    private final String fullUrl;

    public ApiRequestSender(String url, String endpoint) {
        this.fullUrl = url + (url.endsWith("/") ? "" : "/") + endpoint;
    }

    public JSONObject postEntity(String entityStr) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(fullUrl);

            StringEntity input = new StringEntity(entityStr);
            input.setContentType("application/json");
            post.setEntity(input);

            CloseableHttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }
            try {
                //LOG.debug("Sending 'POST' request to URL : " + fullUrl);
                //LOG.debug("Post parameters : " + post.getEntity());
                LOG.debug("Response Code : " + response.getStatusLine().getStatusCode());

                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }

                JSONObject merchantJsonResponse = null;
                try {
                    merchantJsonResponse = (JSONObject) new JSONParser().parse(result.toString());
                    LOG.debug(merchantJsonResponse.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return merchantJsonResponse;
            } finally {
                response.close();
                client.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Something wrong.");
    }

}
