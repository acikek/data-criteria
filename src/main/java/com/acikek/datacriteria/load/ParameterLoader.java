package com.acikek.datacriteria.load;

import com.acikek.datacriteria.DataCriteria;
import com.acikek.datacriteria.advancement.DataCriterion;
import com.acikek.datacriteria.mixin.CriteriaAccess;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParameterLoader extends JsonDataLoader implements IdentifiableResourceReloadListener {

    public static List<DataCriterion> loaded = new ArrayList<>();

    public ParameterLoader() {
        super(new Gson(), "criteria");
    }

    @Override
    public Identifier getFabricId() {
        return DataCriteria.id("criteria");
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
        // Remove existing criteria from registry
        for (DataCriterion existing : loaded) {
            CriteriaAccess.getValues().values().remove(existing);
        }
        loaded.clear();
        // Load new criteria
        int successful = 0;
        for (var file : prepared.entrySet()) {
            JsonObject obj = file.getValue().getAsJsonObject();
            try {
                DataCriterion criterion = DataCriterion.fromJson(file.getKey(), obj);
                loaded.add(criterion);
                CriteriaAccess.getValues().put(file.getKey(), criterion);
                successful++;
            }
            catch (Exception e) {
                DataCriteria.LOGGER.error("Error in criterion '" + file.getKey() + "': ", e);
            }
        }
        DataCriteria.LOGGER.info("Loaded " + successful + " data criteria");
    }
}
