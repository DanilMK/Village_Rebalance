package net.smok.villagerebalance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Values {

    public static final String MOD_ID = "village_rebalance";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        Debug.log("Values are initialized.");
    }
}
