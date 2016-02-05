package org.perlovka.parc;

import org.json.simple.JSONObject;
import org.perlovka.parc.merchant.MerchantGenerator;
import org.perlovka.parc.offer.OfferGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class Application {

  private static final Logger LOG = LoggerFactory.getLogger(Application.class);
  private static String[] inputArgs;


  public static void main(String[] args) throws Exception {
    LOG.debug("Params: endpointUrl numberOfOffers minLat minLon maxLat maxLon numberOfInterests.");
    LOG.debug("Application started.");
    inputArgs = args;

    String url = arg(1, "http://offers-parc.cogniance.com:8080/");
    int numberOfOffers = Integer.parseInt(arg(2, "10000"));

    double minLat = Double.parseDouble(arg(3, "50.38929919"));
    double minLon = Double.parseDouble(arg(4, "30.46664719"));

    double maxLat = Double.parseDouble(arg(5, "50.42052754"));
    double maxLon = Double.parseDouble(arg(6, "30.51565876"));

    int numberOfInterests = Integer.parseInt(arg(7, "14"));

    ApiRequestSender merchantSender = new ApiRequestSender(url, "merchants/admin");
    ApiRequestSender offerSender = new ApiRequestSender(url, "offers/admin");

    MerchantGenerator merchantGenerator = new MerchantGenerator(minLat, minLon, maxLat, maxLon);
    OfferGenerator offerGenerator = new OfferGenerator(numberOfInterests);

    Stream.generate(merchantGenerator::generateMerchant)
            .map(JSONObject::toString)
            .map(merchantSender::postEntity)
            .map(merchantJsonObject -> offerGenerator.generateOffer((Long) merchantJsonObject.get("id"), (Long) merchantJsonObject.get("category")))
            .map(JSONObject::toString)
            .limit(numberOfOffers)
            .forEach(offerSender::postEntity);

    LOG.debug("Application finished all.");
  }

  private static String arg(int argIndex, String defaultValue) {
    return inputArgs.length < argIndex ? defaultValue : inputArgs[argIndex-1];
  }


}
