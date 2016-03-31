package org.perlovka.parc;

import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
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
  private final String session;

  public ApiRequestSender(String url, String endpoint, String session) {
    this.fullUrl = url + (url.endsWith("/") ? "" : "/") + endpoint;
    this.session = session;
  }

  public JSONObject postEntity(String entityStr) {
    try {
      BasicCookieStore cookieStore = new BasicCookieStore();
      BasicClientCookie sessionCookie = new BasicClientCookie("JSESSIONID", session);
      sessionCookie.setDomain(".cogniance.com");
      sessionCookie.setPath("/");
      cookieStore.addCookie(sessionCookie);

      CloseableHttpClient client = HttpClients.custom()
          .setDefaultCookieStore(cookieStore)
          .build();

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
