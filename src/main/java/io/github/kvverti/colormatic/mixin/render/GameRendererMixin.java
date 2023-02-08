package io.github.kvverti.colormatic.mixin.render;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.kvverti.colormatic.Lightmaps;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;

/***
 * 
 * @author heiko
 *
 */
@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

	@Shadow
	@Final
	private MinecraftClient client;

	@Shadow
	@Final
	private LightmapTextureManager lightmapTextureManager;

	@Inject(method = "renderWorld", at = @At("HEAD"))
	private void onRenderWorldHead(final CallbackInfo info) {
		Lightmaps.setWorldRenderFinished(false);
	}

	@Inject(method = "renderWorld", at = @At("RETURN"))
	private void onRenderWorldReturn(final CallbackInfo info) {
		Lightmaps.setWorldRenderFinished(true);

		if (Lightmaps.get(client.world) != null) {
			lightmapTextureManager.update(0f);
		}
	}
}
