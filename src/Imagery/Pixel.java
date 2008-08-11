package Imagery;

public class Pixel {

	private int x;
	private int y;
	
	public Pixel(int x, int  y)
	{
		this.x=x;
		this.y=y;
	}
	
	public boolean equals(Pixel thePixel)
	{
		return (this.x ==thePixel.x && this.y == thePixel.y);
	}
}
