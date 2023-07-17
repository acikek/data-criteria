package com.acikek.datacriteria.mixin;

import com.acikek.datacriteria.load.ParameterLoader;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.server.DataPackContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mixin(DataPackContents.class)
public class DataPackContentsMixin {

    @Unique
    private final ParameterLoader datacriteria$parameterLoader = new ParameterLoader();

    @ModifyReturnValue(method = "getContents", at = @At("RETURN"))
    private List<ResourceReloader> datacriteria$prependParameterLoader(List<ResourceReloader> reloaders) {
        List<ResourceReloader> mutable = new ArrayList<>(reloaders);
        mutable.add(0, datacriteria$parameterLoader);
        return Collections.unmodifiableList(mutable);
    }
}
