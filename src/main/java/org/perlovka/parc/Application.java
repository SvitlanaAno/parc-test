package org.perlovka.parc;

import org.perlovka.parc.merchant.MerchantGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Application {

  private static final Logger LOG = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) throws Exception {
    LOG.debug("Application started.");
    MerchantGenerator.get().generateMerchants();
    LOG.debug("Application finished.");
  }


}
