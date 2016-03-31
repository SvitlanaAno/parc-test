package org.perlovka.parc;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Authenticator {

  private static final Logger LOG = LoggerFactory.getLogger(Authenticator.class);
  private final String fullUrl;

  public Authenticator(String url) {
    this.fullUrl = url + (url.endsWith("/") ? "" : "/") + "login";
  }

  public String login() {
    try {
      CloseableHttpClient client = HttpClients.createDefault();
      HttpPost post = new HttpPost(fullUrl);

      List<NameValuePair> urlParameters = new ArrayList<>();
      urlParameters.add(new BasicNameValuePair("username", "omp"));
      urlParameters.add(new BasicNameValuePair("password", "K1evSF16"));
      urlParameters.add(new BasicNameValuePair("submit", "Login"));

      post.setEntity(new UrlEncodedFormEntity(urlParameters));

      CloseableHttpResponse response = client.execute(post);
      Header[] headers = response.getHeaders("Set-Cookie");
      String[] cookieValues = headers[0].getValue().split(";");
      return cookieValues[0].split("=")[1]; // returns session cookie

    } catch (Exception e) {
      throw new RuntimeException("LoginException. Failed to login.", e);
    }
  }

}
