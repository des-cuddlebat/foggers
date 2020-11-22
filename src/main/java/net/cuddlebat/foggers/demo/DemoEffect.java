package net.cuddlebat.foggers.demo;

import java.util.Optional;

import net.cuddlebat.foggers.FloatColor;
import net.cuddlebat.foggers.FogApi;
import net.cuddlebat.foggers.FogEffect;
import net.minecraft.client.world.ClientWorld;

public class DemoEffect implements FogEffect
{
	@Override
	public boolean enabled()
	{
		return FogApi.maybeGetWorld().isPresent();
	}

	@Override
	public float viewCap()
	{
		Optional<ClientWorld> world = FogApi.maybeGetWorld(); 
		long time = world.isPresent() ? world.get().getTimeOfDay() : 6000;
		// Midnight is 18000: Map time to 6000-30000
		if(time < 6000)
		{
			time += 24000;
		}
		// Midnightness: 0 at midnight, 1 at noon, linear between
		float midnightness = Math.abs(time - 18000) / 12000f;
		return 8.0f + 24.0f * midnightness * midnightness;
	}

	@Override
	public float proximity()
	{
		return 0.1f;
	}

	@Override
	public FloatColor tint()
	{
		Optional<ClientWorld> world = FogApi.maybeGetWorld(); 
		long time = world.isPresent() ? world.get().getTimeOfDay() : 6000;
		// Midnight is 18000: Map time to 6000-30000
		if(time < 6000)
		{
			time += 24000;
		}
		// Midnightness: 0 at midnight, 1 at noon, linear between
		float midnightness = Math.abs(time - 18000) / 12000f;
		return FloatColor.ofGrayscale(midnightness/* * midnightness*/);
	}
}
