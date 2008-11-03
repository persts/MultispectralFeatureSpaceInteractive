/*
** File: ScatterDiagram.java
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
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import java.awt.geom.*;
import Jama.*;


public class ScatterDiagram extends JComponent implements java.awt.event.MouseListener, java.awt.event.MouseMotionListener
{

	private boolean cvInitialPen        = true;
   
	 /*1 -> Minimum Distance
	  *2 -> Maximum Likelihood
	  *3 -> Parallelepiped
	  */
  private int cvAlgorithm = 1;
  
  /*Parameters for the thre different algorithms*/
	private int cvRadium;
	private int cvRectangleSize; 
	private int cvDistanceSD;
	
	/*Polygon that describes the selected area*/
	private Polygon cvPolygon =new Polygon();
	  
   /* Current mouse coordinates */
  private int cvMousex                = 0;
  private int cvMousey                = 0;

   /* Previous mouse coordinates */
  private int cvPrevx                 = 0;
  private int cvPrevy                 = 0;

	private int cvPictureBandX [][];
	private int cvPictureBandY [][];
	
	private int cvPictureBandXMax;
	private int cvPictureBandXMin;
	private int cvPictureBandYMax;
	private int cvPictureBandYMin;
	
	public int cvScatterDiagramPixelsValues [][] = new int[256][256];
	public int cvSDPixelsValuesWholePicture [][] = new int[256][256];
	
	private boolean cvSelectedPixels[][];
	
	private boolean cvSelectedPixelsSD [][];
	
	private int cvHeight;
	private int cvWidth;
	private int cvBandX;
	private int cvBandY;
	
	private boolean cvClicking;
	private boolean cvDragging;
	
	private OriginalPicture cvOPic;
	private boolean cvClassify = false;
	private boolean cvWholePicture = false;
	private boolean cvSinglePoint =false;
	
	
  public  ScatterDiagram()
  {
    this.addMouseListener(this);
    this.addMouseMotionListener(this);
    this.setPreferredSize(new Dimension(275,275));
    
    cvBandX=1;
    cvBandY=1;
    	
    for (int i=0; i<256; i++)
    	for (int j=0; j<256; j++)
    	{
    		cvScatterDiagramPixelsValues[i][j]=0;
    	}
   }

  /**
   * Sets the reference to the original picture
   * @param  thePicture  an absolute URL giving the base location of the image
   * @see         OriginalPicture
   */
   public void setOPicture(OriginalPicture thePicture)
   {
    this.cvOPic = thePicture;
   }
   
   /**
    * Sets the Classify mode
    * @param  theValue indicates if the classify mode is on or off
    */
   public void setClassify(boolean theValue)
   {
	   this.cvClassify = theValue;
   }
   
   /**
    * Sets clicking mode (the user can select single points)
    */
   public void setClicking()
   {
     this.cvClicking = true;
     this.cvDragging = false;
    	
     cvPolygon.reset();
     setSelectedPixelsSD();
     cvOPic.setSelectedPixelsSD(this.cvSelectedPixelsSD);
     repaint();
   }
   
   /**
   * Gets the polygon
   * @return the polygon containing the selected area by the user
   * @see         Polygon
   */
   public Polygon getPolygon()
   {
     return this.cvPolygon;
   }
   
   /**
    * Sets the drawing area method
    */
   public void setDragging ()
   {
     cvDragging = true;
     cvClicking =false;
     cvPolygon.reset();
     setSelectedPixelsSD();
     cvOPic.setSelectedPixelsSD(this.cvSelectedPixelsSD);
     repaint();
    }
    
   /**
    * Sets the classification algorithim
    * @param  theAlgorithim  an integer that specifies the algorithm to use
    */
   public void setAlgorithm(int theAlgorithm)
   {
     cvAlgorithm = theAlgorithm;
   }
   
   /**
    * Sets the radium of the circle drawn in case the minimum distance algorithim is selected
    * @param  theRadium an integer containin the longitude of the radium
    */
   public void setRadium(int theRadium)
   {
     cvRadium = theRadium;
   }
    
   /**
    * Sets the X Band selected
    * @param  theBand  an integer containing the number of band
    */
   public void setBandX(int theBand)
   {
     this.cvBandX=theBand+1;
   }
    
   /**
    * Sets the Y Band selected
    * @param  theBand  an integer containing the number of band
    */
   public void setBandY(int theBand)
   {
     this.cvBandY=theBand+1;
   }
    
   /**
    * Sets the Width of the scatter diagram
    * @param  theWidth an integer containing the width
    */
   public void setWidth(int theWidth)
   {
     this.cvWidth= theWidth;
   }
    
   /**
    * Sets the height of the scatter diagram
    * @param  height an integer containing the width
    */
   public void setHeight(int theHeight)
   {
     this.cvHeight= theHeight;
   }
    
   /**
    * Sets the Width of the scatter diagram
    * @param  theWidth an integer containing the width
    */
   public void setRectangleSize(int theSize)
   {
     this.cvRectangleSize = theSize;
   }
   
   /**
    * Sets the distance to the standard deviation (Maximum likelihood algorithm)
    * @param  theDistance an integer containing the distance
    */
   public void setDistanceSD(int theDistance)
   {
     this.cvDistanceSD= theDistance;
   }
   
   /**
    * Sets the Matrix that contains the selected pixels in the original picture
    * @param  theSelectedPixels a matrix of booleans where each position indicates whether the
    * pixel has been selected or not
    */
   public void setSelectedPixels(boolean theSelectedPixels[][])
   {
     this.cvSelectedPixels=theSelectedPixels;	
   }
   
   /**
    * Sets the boolean that indicates if the whole picture has to be drawn or not
    * @param  theSelection a boolean that indicates if the whole picture has to be drawn or not
    */
   public void setWholePicture(boolean theSelection)
   {
     this.cvWholePicture = theSelection;
   }
    
   /**
    * Sets the boolean that indicates that only one single point has to be drawn
    * @param  thePoint a boolean that indicates that only one single point has to be drawn
    */
   public void setSinglePoint(boolean thePoint)
   {
     this.cvSinglePoint= thePoint;
   }
    
   /**
    * Sets the Matrix that indicates which pixels are selected by the user in the scatter
    * Diagram
    */
   public void setSelectedPixelsSD()
   {
     cvSelectedPixelsSD = new boolean [256][256];
     for(int i=0; i< 256;i++)
       for(int j=0; j< 256;j++)
    	   cvSelectedPixelsSD[i][255-j]= cvPolygon.contains(i+15,j);
   }
    
   /**
    * Resets the matrix that describes how "red" is each pixel
    */
   public void resetScatterDiagramPixelsValues()
   {
     for (int i=0; i<256; i++)
       for (int j=0; j<256; j++)
    	 {
    	   this.cvScatterDiagramPixelsValues[i][j]= 0;
    	 }	
   }
   
   /**
    * Sets the values of the matrix that describes how "red" is each pixel
    * This function reads the values from the matrixes that contain the pixel data of the two
    * selected bands
    */
   public void setScatterDiagramPixelsValues()
   {
   	cvPictureBandXMax = 0;
   	cvPictureBandXMin = 255;
   	cvPictureBandYMax = 0;
   	cvPictureBandYMin = 255;
   	
   	 for(int i=0; i<cvWidth; i++)
       	for (int j=0; j<cvHeight; j++)
       	{
       		if(cvSelectedPixels!= null)
       			
       			if(cvSelectedPixels[i][j])
       			{
       				//&& cvScatterDiagramPixelsValues[i][j]<13
       				cvScatterDiagramPixelsValues[cvPictureBandX[i][j]][cvPictureBandY[i][j]]++;
       				if(this.cvClicking & cvSinglePoint)
       				{
       					cvSDPixelsValuesWholePicture[cvPictureBandX[i][j]][cvPictureBandY[i][j]]= -10;
       					cvSinglePoint =false;
       				}
       				 if(cvPictureBandX[i][j] > cvPictureBandXMax)
       					 cvPictureBandXMax = cvPictureBandX[i][j];
       				 if(cvPictureBandX[i][j] < cvPictureBandXMin)
       					 cvPictureBandXMin = cvPictureBandX[i][j];
       				 
       				 if(cvPictureBandY[i][j] > cvPictureBandYMax)
       					 cvPictureBandYMax = cvPictureBandY[i][j];
       				 if(cvPictureBandY[i][j] < cvPictureBandYMin)
       					 cvPictureBandYMin = cvPictureBandY[i][j];
       			}	
       	} 	
   }
   
   /**
    * Sets the matrix that contains the data of the X band
    * @param  thePicture Matrix of integers containg pixel data
    */
   public void setPictureBandX(int thePicture [][])
   {
   	this.cvPictureBandX=thePicture;
   	resetWholePicture();
   	updateWholePicture();
   }
   
   /**
    * Sets the matrix that contains the data of the Y band
    * @param  thePicture Matrix of integers containg pixel data
    */
   public void setPictureBandY(int thePicture [][])
   {
   	this.cvPictureBandY=thePicture;
   	resetWholePicture();
   	updateWholePicture();
   }
   
   /**
    * Sets a matrix that contains the pixel values on the scatter diagram from the whole original picture
    */
   public void updateWholePicture()
   {
   	if(cvPictureBandX!=null && cvPictureBandY!=null)
   		for(int i=0; i<cvWidth; i++)
   			for (int j=0; j<cvHeight; j++)
   				cvSDPixelsValuesWholePicture[cvPictureBandX[i][j]][cvPictureBandY[i][j]]++;	
   }
   
   /**
    * Resets a matrix that contains the pixel values on the scatter diagram from the whole original picture
    */
   public void resetWholePicture()
   {
   	if(cvPictureBandX!=null && cvPictureBandY!=null)
   		for(int i=0; i<256; i++)
   			for (int j=0; j<256; j++)
   				cvSDPixelsValuesWholePicture[i][j]=0;
   }
    
   /**
    * Performs the different actions needed to paint the scatter diagram and the selected areas
    * @param  g A Graphics object used to generate 2D graphics
    * @see Graphics
    */
   public void paint(Graphics g) 
   {
   	int lvTheCrossX= -1;
   	int lvTheCrossY= -1;
   	Graphics2D g2 = (Graphics2D)g;
   	int lvMeanX = 0;
   	int lvMeanY = 0;
   	int lvNPoints = 1;
   	BasicStroke lvStroke2 = new BasicStroke(2);
   	//Outer rectangle      
     //g2.drawRect(15,0,255,255);
     g2.setColor(Color.LIGHT_GRAY);
     g2.fillRect(15,0,255,255);
     g2.drawString("0",15,270);
     g2.drawString("255",248,270);
     //Y Axis
     g2.rotate(Math.PI/-2.0);
     g2.drawString("255",-25,12);
     g2.drawString("0",-254,12);
     //X Axis
     g2.rotate(Math.PI/2.0);
     g2.setColor(Color.blue);
     
     //Writing the name of the bands
     String lvSBandX= "Band "+Integer.toString(cvBandX);
     String lvSBandY= "Band "+Integer.toString(cvBandY);
     g2.setFont(new Font("Arial", Font.ITALIC, 16));
     g2.drawString(lvSBandX,(255/2)-((lvSBandX.length()*10)/2)+15,270);
     //Y Axis
     g2.rotate(Math.PI/-2.0);
     g2.drawString(lvSBandY,((255/2)+((lvSBandY.length()*10)/2))*-1,12);
     //X Axis
     g2.rotate(Math.PI/2.0);
     
     //Drawing the pixel values

     if(cvWholePicture)
 		{
     	lvTheCrossX= -1;
     	lvTheCrossY= -1;
     	for(int i=0; i<256; i++)
       	for (int j=0; j<256; j++)
       	{
       		if(cvSDPixelsValuesWholePicture[i][j] != 0)
     			{
       			
     				if(cvClicking)
     				{
     					if(cvSDPixelsValuesWholePicture[i][j]== -10)
     					{
     						lvTheCrossX=i;
     						lvTheCrossY=j;	
     					}
     					else
     					{
     					 g2.setColor(new Color(Color.HSBtoRGB(0.6f+((float)cvSDPixelsValuesWholePicture[i][j]/30), 1,1)));
        				 g2.drawLine(i+15,255-j,i+15,255-j);
        				 lvMeanX+= i*cvScatterDiagramPixelsValues[i][j];
        				 lvMeanY+= j*cvScatterDiagramPixelsValues[i][j];
        				 lvNPoints+=cvScatterDiagramPixelsValues[i][j];
     					}
     	    	}
     				else
     				{
     				 g2.setColor(new Color(Color.HSBtoRGB(0.6f+((float)cvSDPixelsValuesWholePicture[i][j]/30), 1,1)));
     				 g2.drawLine(i+15,255-j,i+15,255-j);
     				 lvMeanX+= i*cvScatterDiagramPixelsValues[i][j];
     				 lvMeanY+= j*cvScatterDiagramPixelsValues[i][j];
     				 lvNPoints+=cvScatterDiagramPixelsValues[i][j];
     				}
     			}
       	}
 		}
 		else //not whole picture
 		{
 			if(cvSelectedPixels!= null)
 				for(int i=0; i<256; i++)
 					for (int j=0; j<256; j++)
 					{
 						if(cvScatterDiagramPixelsValues[i][j] != 0)
 						{
 								g2.setColor(new Color(Color.HSBtoRGB(0.6f+((float)cvScatterDiagramPixelsValues[i][j]/30), 1,1)));
 								g2.drawLine(i+15,255-j,i+15,255-j);
 								lvMeanX+= i*cvScatterDiagramPixelsValues[i][j];
 								lvMeanY+= j*cvScatterDiagramPixelsValues[i][j];
 								lvNPoints+=cvScatterDiagramPixelsValues[i][j];
 								
 								if(cvClicking)
 	      				{
 	      					if(cvSDPixelsValuesWholePicture[i][j]== -10)
 	      					{
 	      						lvTheCrossX=i;
 	      						lvTheCrossY=j;	
 	      					}
 	      				}	
 						}
 					}  
 			}
     
    	if(lvTheCrossX!=-1)
   	{
    		g2.setStroke(lvStroke2);
    		g2.setColor(Color.WHITE);
   		g2.drawLine(lvTheCrossX+15-5, 255-lvTheCrossY, lvTheCrossX+20, 255-lvTheCrossY);
   		g2.drawLine(lvTheCrossX+15, 255-lvTheCrossY-5, lvTheCrossX+15, 255-lvTheCrossY+5);
   	}

     
     //Drawing the polygon the user draw
    	if(cvPolygon.npoints>0)
    	{
    	 g2.setColor(Color.WHITE);
       g2.setStroke(lvStroke2);
       g2.drawPolygon(this.cvPolygon);
     }
     //
     if(cvClicking && cvPolygon.npoints>0)
   	{	
  		  g2.setColor(Color.RED);
  		  for (int cont=0; cont<cvPolygon.npoints; cont++)
  			  g2.fillOval(this.cvPolygon.xpoints[cont]-2, this.cvPolygon.ypoints[cont]-2, 4, 4);
   	}
     //
     lvMeanX= lvMeanX/lvNPoints;

			lvMeanY= lvMeanY/lvNPoints;
			
			if(cvClassify && cvDragging)	
				switch(cvAlgorithm){
					case 1: /*Minimum Distance */
					{
						if(lvMeanX!=0 && lvMeanY!=0)
						{
							g2.setColor(Color.RED);
							g2.drawLine(lvMeanX+15-5, 255-lvMeanY, lvMeanX+20, 255-lvMeanY);
							g2.drawLine(lvMeanX+15, 255-lvMeanY-5, lvMeanX+15, 255-lvMeanY+5);
							g2.setStroke(lvStroke2);
							g2.translate(lvMeanX+15, 255-lvMeanY);
							Shape shape = new Ellipse2D.Float(0-cvRadium, 0-cvRadium, cvRadium*2+2, cvRadium*2+2);
							g2.draw(shape);
							
							for(int i=0; i< 256;i++)
				    		for(int j=0; j< 256;j++)
				    			cvSelectedPixelsSD[i][j]= shape.contains(i-lvMeanX, lvMeanY-j);
							cvOPic.setSelectedPixelsSD(this.cvSelectedPixelsSD);
							this.cvOPic.setSelectedPixel(true);
			     	  cvOPic.repaint();  
						}
					}
					break;
					case 2: /* Maximum Likelihood*/
					{
						double lvVarX=0.0;
						double lvVarY=0.0;
						double lvCoVarXY=0.0;  
						for(int i=0; i<cvWidth; i++)
							for (int j=0; j<cvHeight; j++)
							{
								if(cvSelectedPixels!= null)
									if(  cvSelectedPixels[i][j] == true)
									{
										/*Variance of X and Y*/
										lvVarX+= Math.pow((cvPictureBandX[i][j]-lvMeanX),2); 
										lvVarY+= Math.pow((cvPictureBandY[i][j]-lvMeanY),2); 
										/*Covariance*/
										lvCoVarXY+=(cvPictureBandX[i][j]-lvMeanX)*(cvPictureBandY[i][j]-lvMeanY);
									}
							}
						lvVarX/=lvNPoints;
						lvVarY/=lvNPoints;
						lvCoVarXY/=lvNPoints;
						double [][]lvCovarianceMatrix={{lvVarX,lvCoVarXY},{lvCoVarXY,lvVarY}};
						/*Short and long eigen values*/
						double lvLambdaL= (0.5)*(lvCovarianceMatrix[0][0]+lvCovarianceMatrix[1][1]+Math.sqrt(Math.pow(lvCovarianceMatrix[0][0]-lvCovarianceMatrix[1][1], 2)+4*lvCovarianceMatrix[0][1]*lvCovarianceMatrix[0][1]));
						double lvLambdaS= (0.5)*(lvCovarianceMatrix[0][0]+lvCovarianceMatrix[1][1]-Math.sqrt(Math.pow(lvCovarianceMatrix[0][0]-lvCovarianceMatrix[1][1], 2)+4*lvCovarianceMatrix[0][1]*lvCovarianceMatrix[0][1]));

     	
						double lvRoot=4*Math.sqrt(lvLambdaL)+cvDistanceSD;
						double lvRootShort=4*Math.sqrt(lvLambdaS)+cvDistanceSD;
     	
						Matrix A = new Matrix(lvCovarianceMatrix);
						EigenvalueDecomposition e = A.eig();
						Matrix V = e.getV();
						/*Array of eigen vectors*/
						double[][]v=V.getArray();

						/*The rotation of the ellipse is given by the eigen vectors*/
						double rotation = -Math.abs(Math.atan2(v[0][0],v[0][1]));
     	 
						g2.translate(lvMeanX+15, 255-lvMeanY);
						g2.rotate(rotation);
    		
						g2.setStroke(lvStroke2);
						g2.setColor(Color.RED);
						/*The size of the two axis of the ellipse are given by the short and long eigenvalues*/
						Shape shape = new Ellipse2D.Float(-(int)lvRoot/2, -(int)lvRootShort/2, (int)lvRoot+2, (int)lvRootShort+2);

						g2.draw(shape);
						for(int i=0; i< 256;i++)
			    		for(int j=0; j< 256;j++)
			    			cvSelectedPixelsSD[i][j]= shape.contains((i-lvMeanX)*Math.cos(rotation)+(lvMeanY-j)*Math.sin(rotation), (i-lvMeanX)*-Math.sin(rotation)+(lvMeanY-j)*Math.cos(rotation));
						cvOPic.setSelectedPixelsSD(this.cvSelectedPixelsSD);
						this.cvOPic.setSelectedPixel(true);
		     	  cvOPic.repaint();
					}
					break;
					case 3: /* Parallelepiped */
					{
						g2.setColor(Color.RED);
						g2.setStroke(lvStroke2);
						g2.translate(cvPictureBandXMin+15 - cvRectangleSize, (255-cvPictureBandYMax) - cvRectangleSize);
					  Shape shape= new Rectangle2D.Float(0, 0, cvPictureBandXMax-cvPictureBandXMin + cvRectangleSize*2+2, cvPictureBandYMax-cvPictureBandYMin + cvRectangleSize*2+2);
					  g2.draw(shape);
					  for(int i=0; i< 256;i++)
			    		for(int j=0; j< 256;j++)
			    			cvSelectedPixelsSD[i][j]= shape.contains(i-cvPictureBandXMin+ cvRectangleSize, cvPictureBandYMax + cvRectangleSize-j);
						cvOPic.setSelectedPixelsSD(this.cvSelectedPixelsSD);
						this.cvOPic.setSelectedPixel(true);
		     	  cvOPic.repaint();
					}
					break;
					default:{
     	
					}
				}
   	}

   /**
    * Performs the needed actions when the user clicks on the scatter diagram
    * @param  e a MouseEvent object used to handle mouse events
    * @see MouseEvent
    */
		public void mouseClicked(MouseEvent e) 
		{
			 if (cvClicking)
    	 {   
				 cvSinglePoint=true;
    		 cvPolygon.reset();
    		 cvPolygon.addPoint(e.getX(), e.getY());
    		 this.cvOPic.setSelectedPixel(true);
    		 setSelectedPixelsSD();
    		 
    		 if(e.getX()>15 && e.getX()<=270 && e.getY()<256)
    		 {
    			 cvSelectedPixelsSD[e.getX()-15][255-e.getY()]= true;
    			 cvOPic.setSelectedPixelsSD(this.cvSelectedPixelsSD);
    			 cvOPic.repaint();
    		 }
    	 }
		}

		public void mouseEntered(MouseEvent arg0) 
		{
			// TODO Auto-generated method stub
			
		}

		public void mouseExited(MouseEvent arg0) 
		{
			// TODO Auto-generated method stub
			
		}

		public void mousePressed(MouseEvent arg0) 
		{
			// TODO Auto-generated method stub
			if (cvDragging)
    	{
    		cvPolygon.reset();
    		repaint();
    	}
		}

		/**
	   * Performs the needed actions when the user release the mouse on the scatter diagram
	   * @param  e a MouseEvent object used to handle mouse events
	   * @see MouseEvent
	   */
		public void mouseReleased(MouseEvent arg0) 
		{
			if (cvDragging && !cvClassify)
    	{
				/*When we release we draw a line from the last to the first point*/
    		int lvLastx =cvPolygon.npoints>0? cvPolygon.xpoints[0]: 0;
    		int lvLasty =cvPolygon.npoints>0? cvPolygon.ypoints[0]: 0;
    		int lvFirstx=cvPolygon.npoints>0? cvPolygon.xpoints[cvPolygon.npoints-1] :0;
    	  int lvFirsty=cvPolygon.npoints>0? cvPolygon.ypoints[cvPolygon.npoints-1] :0;
    	  

    	  Graphics2D g  = (Graphics2D)this.getGraphics();
     	 	g.setColor(Color.WHITE);
     	 	g.setStroke(new BasicStroke(2));
     	 	/*Straight line joining first and last point of the polygon*/

     	 	g.drawLine(lvFirstx, lvFirsty, lvLastx, lvLasty);
     	 
     	 	releasedPen();
     	  setSelectedPixelsSD();
     	 
     	  cvOPic.setSelectedPixelsSD(this.cvSelectedPixelsSD);
     	  cvOPic.repaint();
     	 
     	  this.cvOPic.setSelectedPixel(true);
     	}	
		}

		/**
	    * Performs the needed actions when the user drags on the scatter diagram
	    * @param  e a MouseEvent object used to handle mouse events
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
		
		public void mouseMoved(MouseEvent arg0) 
		{
			// TODO Auto-generated method stub
			
		}


		/**
	    * Draws a line following the dragging mouse
	    * @param  e a MouseEvent object used to handle mouse events
	    * @see MouseEvent
	    */
		public void penOperation(MouseEvent e) 
    {
       Graphics2D g  = (Graphics2D)this.getGraphics();
       g.setStroke(new BasicStroke(2));
       g.setColor(Color.WHITE);
				
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




