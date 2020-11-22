package net.cuddlebat.foggers.mixin;

import java.awt.Color;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.mojang.blaze3d.systems.RenderSystem;

import net.cuddlebat.foggers.FloatColor;
import net.cuddlebat.foggers.FogApi;
import net.minecraft.client.render.BackgroundRenderer;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin
{
	// com/mojang/blaze3d/systems/RenderSystem.clearColor(FFFF)V
	
	@ModifyVariable(
		at = @At("HEAD"),
		method = "applyFog(Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/BackgroundRenderer$FogType;FZ)V",
		index = 2) // index obtained with 'print = true', or @Inject with local capture
	private static float modifyViewDistance(float viewDistance) // could append applyFog's args
	{
		return (float) FogApi.EFFECTS.stream()
			.filter(e -> e.enabled())
			.mapToDouble(e -> e.viewCap())
			.filter(a -> a < viewDistance)
			.min().orElse(viewDistance);
    }
	
	@ModifyConstant(
		constant = @Constant(floatValue = 0.75f),
		method = "applyFog(Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/BackgroundRenderer$FogType;FZ)V",
		allow = 1) // Hard fail if accidentally more constants were changed
	private static float modifyStartFraction(float proximity) // could append applyFog's args
	{
		return (float) FogApi.EFFECTS.stream()
			.filter(e -> e.enabled())
			.mapToDouble(e -> e.proximity())
			.reduce((a, b) -> a*b)
			.orElse(1.0);
    }
	
	// Redirects are generally considered  incompatible, and here it could be
	// replaced by three modifyvars. If anyone wants to write and test it, sure.
	// Also, setFogBlack()V is incorrectly mapped.
	@Redirect(
		at = @At(
			value = "INVOKE", // targetting a method invoke instruction
			target = "com/mojang/blaze3d/systems/RenderSystem.fog(IFFFF)V"),
		method = "setFogBlack()V")
	private static void applyTint(int pname, float r, float g, float b, float a)
	{
		FloatColor col = tint(r, g, b);
		RenderSystem.fog(pname, col.getRed(), col.getGreen(), col.getBlue(), a);
	}
	
	// Redirects are still not advisable but sh.
	@Redirect(
		at = @At(
			value = "INVOKE", // targetting a method invoke instruction
			target = "com/mojang/blaze3d/systems/RenderSystem.clearColor(FFFF)V"),
		method = "render(Lnet/minecraft/client/render/Camera;FLnet/minecraft/client/world/ClientWorld;IF)V")
	private static void applyTint(float r, float g, float b, float a)
	{
		FloatColor col = tint(r, g, b);
		RenderSystem.clearColor(col.getRed(), col.getGreen(), col.getBlue(), a);
	}
	
	// just a helper method.
	private static FloatColor tint(float r, float g,float b)
	{
		return FogApi.EFFECTS.stream()
			.filter(e -> e.enabled())
			.map(e -> e.tint())
			.reduce(FloatColor::multiply)
			.orElse(FloatColor.of(Color.WHITE))
			.multiply(new FloatColor(r, g, b));
	}

}
