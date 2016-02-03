package org.perlovka.parc.merchant;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.perlovka.parc.offer.OfferGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.Random;

/**
 * @author Ivan Kaplin
 */
public class MerchantGenerator {

  private static final Logger LOG = LoggerFactory.getLogger(MerchantGenerator.class);

  private static final double INITIAL_LONGITUDE = 30.46664719d;
  private static final double LONGITUDE_STEP = 0.0004901157d;
  private static final double INITIAL_LATITUDE = 50.38929919d;
  private static final double LATITUDE_STEP = 0.0003122835d;

  private static final String MERCHANT_URL = "http://offers-parc.cogniance.com:8080/merchants/admin";

  private OfferGenerator offerGenerator;

  public MerchantGenerator() {
    this.offerGenerator = OfferGenerator.get();
  }

  public static MerchantGenerator get() {
    return new MerchantGenerator();
  }

  public void generateMerchants() {
    for (int latIndex = 0; latIndex < 10; latIndex++) {
      double lat = INITIAL_LATITUDE + (latIndex * LATITUDE_STEP);
      for (int lonIndex = 0; lonIndex < 10; lonIndex++) {
        double lon = INITIAL_LONGITUDE + (lonIndex * LONGITUDE_STEP);
        // Generate merchant
        JSONObject sourceMerchantJson = generateMerchant(lat, lon);
        LOG.debug("Merchant: " + sourceMerchantJson.toString());
        JSONObject responseMerchantJson = sendMerchant(sourceMerchantJson.toString());
        // Generate offer for this merchant
        JSONObject offerJson = offerGenerator.generateOffer((Long) responseMerchantJson.get("id"), (Long) responseMerchantJson.get("category"));
        LOG.debug("Offer: " + offerJson.toString());

        offerGenerator.sendOffer(offerJson.toJSONString());
      }
    }
  }

  public JSONObject generateMerchant(double latitude, double longitude) {
    JSONObject merchantJson = new JSONObject();
    merchantJson.put("name", getName());
    merchantJson.put("address", "Fake street !@#$%6, Tel: +38 044 331 2531");
    merchantJson.put("category", getCategory());

    JSONObject location = new JSONObject();
    location.put("lat", latitude);
    location.put("lon", longitude);
    merchantJson.put("location", location);

    JSONArray nearbyStations = new JSONArray();
    JSONObject idS = new JSONObject();
    idS.put("id", getStations());
    nearbyStations.add(idS);
    merchantJson.put("nearbyStations", nearbyStations);

    return merchantJson;
  }

  public JSONObject sendMerchant(String merchant) {
    try {
      CloseableHttpClient client = HttpClients.createDefault();
      HttpPost post = new HttpPost(MERCHANT_URL);

      StringEntity input = new StringEntity(merchant);
      input.setContentType("application/json");
      post.setEntity(input);

      CloseableHttpResponse response = client.execute(post);
      if (response.getStatusLine().getStatusCode() != 200) {
        throw new RuntimeException("Failed : HTTP error code : "
                                   + response.getStatusLine().getStatusCode());
      }
      try {
        LOG.debug("Sending 'POST' request to URL : " + MERCHANT_URL);
        LOG.debug("Post parameters : " + post.getEntity());
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

  private static final Random random = new Random();

  private static final String[] MERCHANT_TYPES = {
      "Store", "Restaurant", "Bar", "Spa", "Mall", "Cinema", "Theater", "Coffee shop", "Shop",
      "Movie",
      "Pub", "Cafe", "Hotel", "Massage", "Lounge bar", "Asian food", "Chinese foood",
      "Ukrainian food",
      "American food", "Greek food", "French food", "Italian food", "Viennese food", "Jewish food",
      "Latin food", "Russian food", "British food", "German food", "Netherlands food",
      "Sweden food",
      "Switzerland food", "Portugal food", "Spain food", "Belgium food"
  };

  private static final String[] MERCHANT_NAMES = {
      "Ben", "Aaron", "Omar", "Life", "Abdul", "Thomas", "Martin", "Hall", "Allen", "Smith",
      "Paul", "Donald", "Charles", "David", "Edward", "Jason", "Sarah", "Deborah",
      "Ruth", "Betty", "Laura", "Michelle", "Richard", "Joseph",
      "Brian", "Kenneth", "Mark", "Garcia", "Young", "Taylor",
      "Flower", "Bird", "Spoon", "Fun"
  };

  private static final String[] CATEGORIES = {
      "Service", "Food", "Shopping", "Groceries", "Entertainment"
  };

  private String getName() {
    int mTypeIndex = random.nextInt(MERCHANT_TYPES.length);
    int mNameIndex = random.nextInt(MERCHANT_NAMES.length);
    return MERCHANT_TYPES[mTypeIndex] + " " + MERCHANT_NAMES[mNameIndex];
  }

  private String getCategory() {
    int cIndex = random.nextInt(CATEGORIES.length);
    return CATEGORIES[cIndex];
  }

  private int getStations() {
    return random.nextInt(5) + 1;
  }

}

