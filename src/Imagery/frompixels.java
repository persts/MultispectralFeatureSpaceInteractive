package Imagery;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.MemoryImageSource;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JComponent;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

public class frompixels extends JComponent{

	 public void paint(Graphics g) {
   	
   	Graphics2D g2 = (Graphics2D)g;
   	
   	
   	try {
   		InputStream in = getClass().getResourceAsStream("colors.jpg");
   		JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);
   		BufferedImage image = decoder.decodeAsBufferedImage();
   		g2.drawImage(image,0,0,this);
   		in.close();
   		}
   		catch (IOException e) {e.printStackTrace();}
   		catch (ImageFormatException e) {e.printStackTrace();}

     g2.drawRect(0,0,90,25);
     g2.drawRect(0,25,90,25);
     g2.drawRect(0,50,90,25);
     g2.drawString("testasdf",42,18);
     
     
     
    
     MemoryImageSource source;
   	SampleModel sample;
   	DataBufferInt data;
   	DirectColorModel colormodel;
   	WritableRaster raster;
   	BufferedImage buffer;
   	int[] pixels;

   	// Set up our pixel array
   	int w = 255;
   	int h = 255;
   	pixels = new int[w * h];

   	// This creates an RGBA colormodel... I think.
   	colormodel = new DirectColorModel(32, 0xff000000, 0xff0000, 0xff00, 0xff);

   	// Fill in some data. It doesn't really matter what.
   	int i = 0;
   	for(int y = 0; y < h; y++)
   	{
   		for(int x = 0; x < w; x++)
   		{
   			pixels[i++] = (x << 24) | (255 << 16) | (255 << 8) | 255;
   		}
   	}

   	// Set up our MemoryImageSource with the RGBA colormodel and data
   	source = new MemoryImageSource(w, h, colormodel, pixels, 0, w);

   	// And then, some magical steps which I don't understand
   	sample = colormodel.createCompatibleSampleModel(w, h);
   	data = new DataBufferInt(pixels, w * h);
   	raster = WritableRaster.createWritableRaster(sample, data, new Point(0,0));
   	BufferedImage image = new BufferedImage(colormodel, raster, false, null);
   	
   	g2.drawImage(image,0,0,this);
     
   }
	//
}
