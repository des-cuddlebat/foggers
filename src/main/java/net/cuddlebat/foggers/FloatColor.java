package net.cuddlebat.foggers;

import java.awt.Color;

public class FloatColor
{
	private final float r;
	private final float g;
	private final float b;
	
	public FloatColor(float r, float g, float b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public static FloatColor of(Color col)
	{
		return new FloatColor(
			col.getRed  () / 255.0f,
			col.getGreen() / 255.0f,
			col.getBlue () / 255.0f);
	}
	
	public static FloatColor ofGrayscale(float g)
	{
		return new FloatColor(g, g, g);
	}
	
	public FloatColor multiply(FloatColor other)
	{
		return new FloatColor(r * other.r, g * other.g, b * other.b);
	}

	public float getRed()
	{
		return r;
	}

	public float getGreen()
	{
		return g;
	}

	public float getBlue()
	{
		return b;
	}
}
