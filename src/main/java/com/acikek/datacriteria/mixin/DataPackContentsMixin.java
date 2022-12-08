package com.acikek.datacriteria.mixin;

import com.acikek.datacriteria.load.ParameterLoader;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.server.DataPackContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mixin(DataPackContents.class)
public class DataPackContentsMixin {

    private final ParameterLoader datacriteria$parameterLoader = new ParameterLoader();

    @Inject(method = "getContents", cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD, at = @At("RETURN"))
    private void datacriteria$prependParameterLoader(CallbackInfoReturnable<List<ResourceReloader>> cir) {
        List<ResourceReloader> reloaders = new ArrayList<>(cir.getReturnValue());
        reloaders.add(0, datacriteria$parameterLoader);
        cir.setReturnValue(Collections.unmodifiableList(reloaders));
    }
}
