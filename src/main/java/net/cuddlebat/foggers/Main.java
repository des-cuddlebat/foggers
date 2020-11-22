package net.cuddlebat.foggers;

import java.util.Optional;

import net.cuddlebat.foggers.demo.DemoEffect;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;

public class Main implements ModInitializer
{
	@Override
	public void onInitialize()
	{
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

//		FogApi.EFFECTS.add(new DemoEffect());
	}
}
