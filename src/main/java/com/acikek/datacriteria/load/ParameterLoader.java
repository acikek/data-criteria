package com.acikek.datacriteria.load;

import com.acikek.datacriteria.DataCriteria;
import com.acikek.datacriteria.advancement.DataCriterion;
import com.acikek.datacriteria.mixin.CriteriaTriggersAccess;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParameterLoader extends SimpleJsonResourceReloadListener {

    public static List<DataCriterion> loaded = new ArrayList<>();

    public ParameterLoader() {
        super(new Gson(), "criteria");
    }

    /*@Override
    public Identifier getFabricId() {
        return DataCriteria.id("criteria");
    }*/

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> prepared, ResourceManager manager, ProfilerFiller profiler) {
        // Remove existing criteria from registry
        for (DataCriterion existing : loaded) {
            CriteriaTriggersAccess.getValues().values().remove(existing);
        }
        loaded.clear();
        // Load new criteria
        int successful = 0;
        for (var file : prepared.entrySet()) {
            JsonObject obj = file.getValue().getAsJsonObject();
            try {
                DataCriterion criterion = DataCriterion.fromJson(file.getKey(), obj);
                loaded.add(criterion);
                CriteriaTriggersAccess.getValues().put(file.getKey(), criterion);
                successful++;
            }
            catch (Exception e) {
                DataCriteria.LOGGER.error("Error in criterion '" + file.getKey() + "': ", e);
            }
        }
        // Log
        if (successful > 0) {
            DataCriteria.LOGGER.info("Loaded " + successful + " data criteri" + (successful != 1 ? "a" : "on"));
        }
    }
}
