package com.acikek.datacriteria;

import com.acikek.datacriteria.load.ParameterLoader;
import com.acikek.datacriteria.predicate.JsonPredicates;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.NewRegistryEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(DataCriteria.ID)
public class DataCriteria {

    public static final String ID = "datacriteria";

    public static final Logger LOGGER = LogManager.getLogger(ID);

    public static ResourceLocation id(String path) {
        return new ResourceLocation(ID, path);
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Initializing Data Criteria...");
        JsonPredicates.register();
        event.enqueueWork(() -> {
            JsonPredicates.register();
        });
    }

    @SubscribeEvent
    public static void addListener(AddReloadListenerEvent event) {
        event.addListener(new ParameterLoader());
    }

    @SubscribeEvent
    public static void onRegistryCreate(NewRegistryEvent event) {
        JsonPredicates.createRegistry(event);
    }
}
