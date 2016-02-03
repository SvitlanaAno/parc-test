package org.perlovka.parc;

import org.perlovka.parc.merchant.MerchantGenerator;
import org.perlovka.parc.offer.OfferGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sanoshchenko on 1/31/16.
 */
public class Application {

  private static final Logger LOG = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) throws Exception {
    LOG.debug("Application started.");
    MerchantGenerator.get().generateMerchants();
//    OfferGenerator offerGenerator = OfferGenerator.get();
//    for (int i = 0; i < 10; i++) {
//      LOG.debug(offerGenerator.generateOffer(i, "FOOD"));
//    }

    LOG.debug("Application finished.");
  }


}
