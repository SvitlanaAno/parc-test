package org.perlovka.parc.merchant;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.perlovka.parc.ApiRequestSender;
import org.perlovka.parc.offer.OfferGenerator;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MerchantGenerator {

  private double minLat;
  private double minLon;
  private double maxLat;
  private double maxLon;

  public MerchantGenerator(double minLat, double minLon, double maxLat, double maxLon) {
    this.minLat = minLat;
    this.minLon = minLon;
    this.maxLat = maxLat;
    this.maxLon = maxLon;
  }

  public JSONObject generateMerchant() {

    double latitude = ThreadLocalRandom.current().nextDouble(minLat, maxLat);
    double longitude = ThreadLocalRandom.current().nextDouble(minLon, maxLon);

    JSONObject merchantJson = new JSONObject();
    merchantJson.put("name", getName());
    merchantJson.put("address", "Fake street !@#$%6, Tel: +38 044 331 2531");
    merchantJson.put("category", getCategory());

    JSONObject location = new JSONObject();
    location.put("lat", latitude);
    location.put("lon", longitude);
    merchantJson.put("location", location);

    int stationId = getStations();

    if (stationId != 0) {
      JSONArray nearbyStations = new JSONArray();
      JSONObject idS = new JSONObject();
      idS.put("id", stationId);
      nearbyStations.add(idS);
      merchantJson.put("nearbyStations", nearbyStations);
    }

    return merchantJson;
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
    int typeIndex = random.nextInt(MERCHANT_TYPES.length);
    int nameIndex = random.nextInt(MERCHANT_NAMES.length);
    return MERCHANT_TYPES[typeIndex] + " " + MERCHANT_NAMES[nameIndex];
  }

  private String getCategory() {
    int index = random.nextInt(CATEGORIES.length);
    return CATEGORIES[index];
  }

  private int getStations() {
    Random rand = new Random();
    int stationId = rand.nextInt(6);
    return stationId;
  }


}

