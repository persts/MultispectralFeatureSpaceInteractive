/*
** File: OriginalPicture.java
** Author(s): Roberto Garcia Yunta
** Creation Date: 2008-10-17
** Copyright (c) 2008, American Museum of Natural History. All rights reserved.
** 
** This library/program is free software; you can redistribute it 
** and/or modify it under the terms of the GNU Library General Public
** License as published by the Free Software Foundation; either
** version 2 of the License, or (at your option) any later version.
** 
** This library/program is distributed in the hope that it will be useful,
** but WITHOUT ANY WARRANTY; without even the implied warranty of
** MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
** Library General Public License for more details.
**
** This work was made possible through the support from the 
** Center For Biodiversity and Conservation and The Spanish Ministry of 
** Science and Innovation's INTEGRANTS program.
**
**/
package Imagery;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import DataProvider.EightBitProvider;
import javax.swing.*;


public class OriginalPicture extends JComponent implements java.awt.event.MouseListener,
java.awt.event.MouseMotionListener {

	  /*The polygon that contains the selected area*/
		private Polygon cvPolygon =new Polygon();
		
	  private boolean cvInitialPen        = true;
    
  
    /* Current mouse coordinates */
    private int cvMousex                = 0;
    private int cvMousey                = 0;

    /* Previous mouse coordinates */
    private int cvPrevx                 = 0;
    private int cvPrevy                 = 0;
    
    private boolean cvDragging = false;
    private boolean cvClicking = false;
    
    /*Reference to the scatter diagram*/
    private ScatterDiagram cvSDiagram;
  
    /*Matrix with the selected pixels in the original picture*/
    private boolean [][] cvSelectedPixels;
    
    /*Matrix with the selected pixels in the Scatter Diagram*/
    private boolean [] [] cvSelectedPixelsSD;
    
    /*Object used to populate the XBand and YBand matrixes*/
    private EightBitProvider cvEBP;
    
    /*Selected bands*/
    private int cvBandX = 0;
    private int cvBandY = 0;
    private int cvRedBand = 0;
    private int cvGreenBand = 0;
    private int cvBlueBand = 0;
    
    /*Boolean that indicates whether there are selected pixels in the 
     * scatter diagram or not*/
    public  boolean cvSelectedPixel = false;
    
    /*Are we in classification mode?*/
    private boolean cvClassify= false;
    
    /*Did we get correctly the data from the source?*/
    private boolean cvDataReady;
    
    /*Reference to the check box, used here to be able to deselect it*/
    JCheckBox cvJCheckWholePicture;
    
    public  OriginalPicture(ScatterDiagram theDiagram, JCheckBox theCheck)
    {
    	 this.addMouseListener(this);
       this.addMouseMotionListener(this);
       this.setPreferredSize(new Dimension(256,256));
       
       this.cvSDiagram = theDiagram;
       cvJCheckWholePicture = theCheck;
       cvEBP = new EightBitProvider();
       cvDataReady = cvEBP.read("7band_256x256_example.dat");
    }

    /**
     * Gets the selected pixels by the user
     * @return a matrix with the selected pixels
     */
    public boolean [][] getSelectedPixels()
    {
    	return cvSelectedPixels;
    	
    }
    
    /**
     * Gets the polygon the user draw
     * @return a polygon containing the points the user selected
     * @see Polygon
     */
    public Polygon getPolygon()
    {
    	return this.cvPolygon;
    }
    
    /**
     * Resets the polygon, the number of points it contains after this operation is 0
     */
    public void resetPolygon()
    {
    	this.cvPolygon.reset();
    }
    
    /**
     * Returns an array of Strings describing the bands available
     * @return  strings describing the available bands
     */
    public  String [] getBands()
    {
    	String lvBands[];
    	if(cvDataReady)
    	{
    	  lvBands =new String[cvEBP.cvData.length];
    	  for(int i=1; i<=lvBands.length;i++)
    		  lvBands[i-1]="Band "+Integer.toString(i);
    	}
    	else
    	{
    		lvBands =new String[1];
    		lvBands[0] = "No data";
    	}
    	return lvBands;
    }
    
    /**
     * Sets the boolean that indicates that there are selected pixels in the scatter diagram
     * @param thePixel boolean that indicates the if the are pixels selected in the scatter diagram or not
     */
    public void setSelectedPixel(boolean thePixel)
    {
    	this.cvSelectedPixel = thePixel;
    }
    
    /**
     * Sets the X Band selected
     * @param theBand
     */
    public void setBandX(int theBand)
    {
    	cvBandX=theBand;
    }
    
    /**
     * Sets the Y Band selected
     * @param theBand
     */
    public void setBandY(int theBand)
    {
    	cvBandY=theBand;
    }
    
    /**
     * Sets the Band that will be drawn using the red color
     * @param theBand
     */
    public void setRedBand(int theBand)
    {
    	cvRedBand = theBand;
    }
    
    /**
     * Sets the Band that will be drawn using the green color
     * @param theBand
     */
    public void setGreenBand(int theBand)
    {
    	cvGreenBand = theBand;
    }
    
    /**
     * Sets the Band that will be drawn using the blue color
     * @param theBand
     */
    public void setBlueBand(int theBand)
    {
    	cvBlueBand = theBand;
    }
    
    /**
     * Gets the band represented using the red color
     * @return an integer that represents the band
     */
    public int getRedBand()
    {
    	return cvRedBand ;
    }
    
    /**
     * Gets the band represented using the green color
     * @return an integer that represents the band
     */
    public int getGreenBand()
    {
    	return cvGreenBand;
    }
    
    /**
     * Gets the band represented using the blue color
     * @return an integer that represents the band
     */
    public int getBlueBand()
    {
    	return cvBlueBand;
    }
    
    /**
     * Sets the boolean that indicates the the application is in classification mode
     * @param theValue
     */
    public void setClassify(boolean theValue)
    {
    	cvClassify = theValue;
    }
    
    /**
     * Sets the pixels selected by the user on the original picture
     */
    public void setSelectedPixels()
    {
    	cvSelectedPixels = new boolean [getHeight()][getWidth()];
    	for(int i=0; i< this.getHeight();i++)
    		for(int j=0; j< this.getWidth();j++)
    			cvSelectedPixels[i][j]=cvPolygon.contains(j, i);		
    }
    
    /**
     * Sets the pixels that the user draw on the scatter diagram
     * @param theSelectedPixelsSD
     */
    public void setSelectedPixelsSD(boolean theSelectedPixelsSD [][])
    {
    	this.cvSelectedPixelsSD=theSelectedPixelsSD;
    }
    
    /**
     * Sets the clicking mode. The user can select single points
     */
    public void setClicking()
    {
    	this.cvClicking = true;
    	this.cvDragging = false;
    	getPolygon().reset();
    	setSelectedPixels();
   	 	repaint();
    }
    
    /**
     * Sets the dragging mode. The user can draw a polygon
     */
    public void setDragging()
    {
    	this.cvDragging = true;
    	this.cvClicking = false;
    	getPolygon().reset();
    	setSelectedPixels();
    	cvSDiagram.setSelectedPixels(this.cvSelectedPixels);
    	repaint();
    }
    
    /**
     * Performs the actions needed when the user clicks on the picture
     * @param e A mouseEvent object
     * @see MouseEvent
     */
    public void mouseClicked(MouseEvent e) 
    {	
    	 if (cvClicking)
    	 {  
    		 cvPolygon.reset();
    		 
    		 setSelectedPixels();
    		 //we create the polygon just to paint the point
    		 cvPolygon.addPoint(e.getX(), e.getY());
    		//Since a polygon with a single point contains nothing, we directly set up the selected pixels array
    		 this.cvSelectedPixels[e.getY()][e.getX()]=true;
    		 cvSDiagram.setSinglePoint(true);
      	 cvSDiagram.setSelectedPixels(this.cvSelectedPixels);
      	 
      	 cvSDiagram.setHeight(this.getHeight());
      	 cvSDiagram.setWidth(this.getWidth());
      	 cvSelectedPixel=false;
      	 repaint(); 
    	 }
    	 else if (cvDragging && !cvClassify)
    	 {
    		 if(e.getClickCount() == 2)
    		 {
    			 setSelectedPixels();
    			 for(int i=0; i< this.getHeight();i++)
    				 for(int j=0; j< this.getWidth();j++)
    					 cvSelectedPixels[i][j]=true;
    	      cvSDiagram.setSelectedPixels(this.cvSelectedPixels); 
    	      repaint();
    		 }
    	 }
    }

    /**
     * Performs the actions needed when the user press the mouse on the picture
     * @param e A mouseEvent object
     * @see MouseEvent
     */
    public void mousePressed(MouseEvent e) 
    {	
    	if (cvDragging & !cvClassify)
    	{
    		cvPolygon.reset();
    		this.cvSDiagram.resetScatterDiagramPixelsValues();
    		this.cvSDiagram.setScatterDiagramPixelsValues();
    		cvSelectedPixel=false;
    		repaint();
    	}
    }

    /**
     * Performs the actions needed when the user releases the mouse on the picture
     * @param e a mouseEvent object
     * @see MouseEvent
     */
    public void mouseReleased(MouseEvent e) {
    	if (cvDragging && !cvClassify)
    	{
    		int lvLastx =cvPolygon.npoints>0? cvPolygon.xpoints[0]: 0;
    		int lvLasty =cvPolygon.npoints>0? cvPolygon.ypoints[0]: 0;
    		int lvFirstx=cvPolygon.npoints>0? cvPolygon.xpoints[cvPolygon.npoints-1] :0;
    	  int lvFirsty=cvPolygon.npoints>0? cvPolygon.ypoints[cvPolygon.npoints-1] :0;
    	  
    	  Graphics g  = this.getGraphics();
     	 	g.setColor(Color.YELLOW);
     	 	g.drawLine(lvFirstx, lvFirsty, lvLastx, lvLasty);
     	 
     	 	releasedPen();
     	 	setSelectedPixels();
     	 	cvSDiagram.setSelectedPixels(this.cvSelectedPixels);
     	 	cvSDiagram.setHeight(this.getHeight());
     	 	cvSDiagram.setWidth(this.getWidth());
     	  this.cvSDiagram.resetScatterDiagramPixelsValues();
     	  this.cvSDiagram.setScatterDiagramPixelsValues();
     	 	cvSDiagram.repaint();
     	 	cvJCheckWholePicture.setSelected(false);
     	}	
    }

    public void mouseEntered(MouseEvent e) {
    	
    }

    public void mouseExited(MouseEvent e) {
    }

    /**
     * Performs the actions needed when the user drags on the picture
     * @param e A mouseEvent object
     * @see MouseEvent
     */
    public void mouseDragged(MouseEvent e) 
    {
    	if (cvDragging && !cvClassify)
    	{
    		cvPolygon.addPoint(e.getX(), e.getY());
      	penOperation(e);
    	}
    }

    public void mouseMoved(MouseEvent e) {
    }

    /**
     * Performs the different actions needed to paint the picture and the selected areas
     * @param  g A Graphics object used to generate 2D graphics
     * @see Graphics
     */
    public void paint(Graphics g) 
    {
    	Graphics2D g2 = (Graphics2D)g;
    	
    	//bla bla bla
    	SampleModel sample;
    	DataBufferInt data;
    	DirectColorModel colormodel;
    	WritableRaster raster;
    	
    	int[] pixels;
    	// Set up our pixel array
    	int w = 256;
    	int h = 256;
    	pixels = new int[w * h];
    	
    	//Values of the two bands
    	int [][] pictureBandX = new int[w][h];
    	int [][] pictureBandY = new int[w][h];

    	// This creates an RGBA colormodel
    	colormodel = new DirectColorModel(32, 0xff000000, 0xff0000, 0xff00, 0xff);

    	//Fill in the array pixel
    	int i = 0;
    	if (cvDataReady)
    	{
	  		for(int lvRowRunner = 0; lvRowRunner < cvEBP.cvData[0].length; lvRowRunner++) 
		    	for(int lvColumnRunner = 0; lvColumnRunner < cvEBP.cvData[0][0].length; lvColumnRunner++)	
		  		{
		    		pictureBandX[lvRowRunner][lvColumnRunner]=cvEBP.getInt(cvBandX,lvRowRunner,lvColumnRunner);
		    		pictureBandY[lvRowRunner][lvColumnRunner]=cvEBP.getInt(cvBandY,lvRowRunner,lvColumnRunner);
  				
		    		if(cvSelectedPixel == false )
		    		{					
		    			pixels[i++]=(cvEBP.getInt(cvRedBand,lvRowRunner,lvColumnRunner))<<24 | (cvEBP.getInt(cvGreenBand,lvRowRunner,lvColumnRunner)) << 16 | ((cvEBP.getInt(cvBlueBand,lvRowRunner,lvColumnRunner)) << 8) | 255;
		    		}
		    		else 
		    		{
		    			int lvPixelValue=(cvEBP.getInt(cvRedBand,lvRowRunner,lvColumnRunner))<<24 | (cvEBP.getInt(cvGreenBand,lvRowRunner,lvColumnRunner)) << 16 | ((cvEBP.getInt(cvBlueBand,lvRowRunner,lvColumnRunner)) << 8) | 255;
		    			pixels[i++] = (cvSelectedPixelsSD[pictureBandX[lvRowRunner][lvColumnRunner]][pictureBandY[lvRowRunner][lvColumnRunner]]) & !this.cvClicking?  255<<24 | 255 << 16 | (0 << 8) | 255  :lvPixelValue;
		    		}
  			}	
    	}
    	else
    		g2.drawString("No data available", 110, 125);
    	
    	// Further steps to build the image
    	sample = colormodel.createCompatibleSampleModel(w, h); 
    	data = new DataBufferInt(pixels, w * h);
    	raster = WritableRaster.createWritableRaster(sample, data, new Point(0,0));
    	//We just built the image
    	BufferedImage image = new BufferedImage(colormodel, raster, false, null);
    	g2.drawImage(image,0,0,this);
    	
    	//Drawing the polygon the user drew.
    	g2.setColor(Color.YELLOW);
    	g2.drawPolygon(this.cvPolygon);
    	//Drawing the clicked point in case we are in the clicking mode
    	if(cvClicking && cvPolygon.npoints>0)
    	{
    		g.setColor(Color.CYAN);
    		g2.drawLine(this.cvPolygon.xpoints[0]-5, this.cvPolygon.ypoints[0], this.cvPolygon.xpoints[0]+5, this.cvPolygon.ypoints[0]);
    		g2.drawLine(this.cvPolygon.xpoints[0], this.cvPolygon.ypoints[0]-5, this.cvPolygon.xpoints[0], this.cvPolygon.ypoints[0]+5);
    	}
    	if( cvSelectedPixel && cvClicking && cvDataReady)
    		for (int lvRows=0; lvRows<pictureBandX.length;lvRows++)
    			for (int lvColumns=0; lvColumns<pictureBandX[0].length;lvColumns++)
    				if(cvSelectedPixelsSD[pictureBandX[lvColumns][ lvRows]][pictureBandY[lvColumns][lvRows]])
    					{
    						g.setColor(Color.YELLOW);
    						g.fillOval(lvRows-2,  lvColumns-2, 4, 4); 
    					}	
    	
    	//Setting up the Scatter diagram,  with the two bands
    	this.cvSDiagram.setPictureBandX(pictureBandX);
    	this.cvSDiagram.setPictureBandY(pictureBandY);
    	this.cvSDiagram.resetScatterDiagramPixelsValues();
    	this.cvSDiagram.setScatterDiagramPixelsValues();
    	//updating the image
    	this.cvSDiagram.repaint();
    }

    /**
	    * Draws a line following the dragging mouse
	    * @param  e a MouseEvent object used to handle mouse events
	    * @see MouseEvent
	    */
		public void penOperation(MouseEvent e) 
		{
      Graphics2D g  = (Graphics2D)this.getGraphics();
      
      g.setColor(Color.YELLOW);
				
      if (cvInitialPen)
      {
     	 /*Reset all the variables, so we are in the begining of the draw*/
     	 cvMousex   = e.getX();
        cvMousey   = e.getY();
        cvPrevx    = e.getX();
        cvPrevy    = e.getY();
        cvInitialPen = false;
        
        g.drawLine(cvPrevx,cvPrevy,cvMousex,cvMousey);
      }

      if (mouseHasMoved(e))
      {
         cvMousex = e.getX();
         cvMousey = e.getY();

         /*draw a line from the previous mouse coordinates to the current mouse coordinates*/
         g.drawLine(cvPrevx,cvPrevy,cvMousex,cvMousey);

         /*set the current mouse coordinates to previous mouse coordinates for next time*/
         cvPrevx = cvMousex;
         cvPrevy = cvMousey;
      }
   }

		/**
	    * Specifies if the mouse has been moved 
	    * @param  e a MouseEvent object used to handle mouse events
	    * @return if the mouse has been moved
	    * @see MouseEvent
	    */
		public boolean mouseHasMoved(MouseEvent e)
   {
      return (cvMousex != e.getX() || cvMousey != e.getY());
   }
   
		/**
	    * The user has released the mouse while he/she was drawing
	    */
		public void releasedPen()
   {
      cvInitialPen = true;
   }
   

}



