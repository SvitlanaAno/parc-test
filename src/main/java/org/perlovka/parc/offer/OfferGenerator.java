package org.perlovka.parc.offer;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class OfferGenerator {

  private static final Logger LOG = LoggerFactory.getLogger(OfferGenerator.class);
  public static final String[] offerTypes = new String[]{"PercentageDiscount", "MoneyOff", "ItemBonus"};
  public static final String[][] times = new String[][]{{"09:00", "13:00"}, {"09:00", "17:00"},
          {"12:00", "20:00"}, {"22:00", "05:00"}, {"18:00", "22:00"},
          {"15:00", "18:00"}, {"06:00", "11:00"}};

  private static final String OFFER_START = "1451606400000";
  private static final String OFFER_END = "1467331200000";

  private int numberOfInterests;

  public OfferGenerator(int numberOfInterests) {
    this.numberOfInterests = numberOfInterests;
  }

  public JSONObject generateOffer(Long merchantId, Long category){
    String[] weekDays = generateWeekDays();
    JSONArray daysOfWeek = new JSONArray();
    daysOfWeek.addAll(Arrays.asList(weekDays));

    String[] dayParts = getDayPart();
    JSONArray dayPartJson = new JSONArray();
    dayPartJson.addAll(Arrays.asList(dayParts));

    List<Integer> interests = IntStream.rangeClosed(1, numberOfInterests).mapToObj(i -> i).collect(Collectors.toList());
    Collections.shuffle(interests);

    List<Integer> interestIds = interests.stream().limit(random.nextInt(numberOfInterests) + 1).collect(Collectors.toList());

    JSONArray interestsJson = interestIds.stream().map(interestId -> new JSONObject() {{
      put("id", interestId);
    }}).collect(Collectors.toCollection(JSONArray::new));

    String text = "category: " + category + " , dayPart = " + Arrays.toString(dayParts) + " ,dayWeek: "
            + Arrays.toString(weekDays) + " with Interests: " + interestIds;

    JSONObject obj = new JSONObject();

    obj.put("interests", interestsJson);
    obj.put("text",text);
    obj.put("offerType", getOfferType());
    obj.put("quantity", random.nextInt(1000) + 1);
    obj.put("minCostToApply", random.nextInt(20) + 15);
    obj.put("itemBonusShortText", "Get prize");
    obj.put("savings", random.nextInt(10) + 5);
    obj.put("imageLocation","https://img.grouponcdn.com/deal/ch53eiVdHsQfwqYUQwcb/NP-2048x1229/v1/c312x189.jpg");
    obj.put("activationInstant", OFFER_START);
    obj.put("expirationInstant", OFFER_END);

    obj.put("daysOfWeek", daysOfWeek);

    String[] time = times[random.nextInt(times.length)];
    obj.put("startLocalTime", time[0]);
    obj.put("endLocalTime", time[1]);
    obj.put("dayParts", dayPartJson);

    JSONObject merchant = new JSONObject();
    merchant.put("id", merchantId);
    obj.put("merchant", merchant);

    return obj;
  }

  private String[][] dayParts = {{"Morning"}, {"Daytime"}, {"Evening"}, {"Morning","Daytime","Evening"}};

  public String[] getDayPart(){
    return dayParts[random.nextInt(4)];
  }

  private static final String[][] weekDaysOptions = {
      {"MONDAY", "TUESDAY", "WEDNESDAY"},
      {"THURSDAY", "FRIDAY", "SATURDAY"},
      {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"}
  };

  private String[] generateWeekDays() {
    return weekDaysOptions[random.nextInt(3)];
  }

  private String getOfferType() {
    return offerTypes[random.nextInt(3)];
  }

  private static final Random random = new Random();
  private int getInterest() {
    return random.nextInt(14) + 1;
  }


}
