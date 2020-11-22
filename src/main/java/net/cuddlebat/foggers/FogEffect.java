package net.cuddlebat.foggers;

public interface FogEffect
{
	// whether this is considered at all
	public boolean enabled();
	
	// how many blocks before 100% dense fog
	public float viewCap();
	
	// 0-1, radius to clear vision as a frac of viewcap
	public float proximity();
	
	// currently treated multiplicatively
	public FloatColor tint(); 
	
//	public float tintAlpha();
}
