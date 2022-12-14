package com.acikek.datacriteria;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataCriteria implements ModInitializer {

    public static final String ID = "datacriteria";

    public static final Logger LOGGER = LogManager.getLogger(ID);

    public static Identifier id(String path) {
        return new Identifier(ID, path);
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Data Criteria...");
    }
}
