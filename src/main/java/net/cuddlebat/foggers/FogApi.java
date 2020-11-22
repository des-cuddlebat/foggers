package net.cuddlebat.foggers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;

public final class FogApi
{
	private FogApi() { }
	
	public static final List<FogEffect> EFFECTS = new ArrayList<>();
	
	public static Optional<ClientWorld> maybeGetWorld()
	{
		MinecraftClient client = MinecraftClient.getInstance();
		if(client == null)
			return Optional.empty();
		ClientWorld world = client.world;
		if(world == null)
			return Optional.empty();
		return Optional.of(world);
	}
	
	public static Optional<Entity> maybeGetCameraEntity()
	{
		MinecraftClient client = MinecraftClient.getInstance();
		if(client == null)
			return Optional.empty();
		Entity entity = client.cameraEntity;
		if(entity == null)
			return Optional.empty();
		return Optional.of(entity);
	}
	
	public static Optional<ClientPlayerEntity> maybeGetPlayer()
	{
		MinecraftClient client = MinecraftClient.getInstance();
		if(client == null)
			return Optional.empty();
		ClientPlayerEntity player = client.player;
		if(player == null)
			return Optional.empty();
		return Optional.of(player);
	}
}
