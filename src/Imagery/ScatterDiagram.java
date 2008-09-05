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
  
  /*
   * */
	private int cvRadium;
	private int cvRectangleSize; 
	private int cvDistanceSD;
	
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


   public void setOPicture(OriginalPicture thePicture)
   {
    this.cvOPic = thePicture;
   }
    
   public void setClassify(boolean theValue)
   {
	   this.cvClassify = theValue;
   }
    public void setClicking()
    {
    	this.cvClicking = true;
    	this.cvDragging = false;
    	
    	cvPolygon.reset();
    	setSelectedPixelsSD();
    	cvOPic.setSelectedPixelsSD(this.cvSelectedPixelsSD);
    	repaint();
    }
    
    public void setDragging ()
    {
    	cvDragging = true;
    	cvClicking =false;
    	cvPolygon.reset();
    	setSelectedPixelsSD();
    	cvOPic.setSelectedPixelsSD(this.cvSelectedPixelsSD);
    	repaint();
    }
    
    public void setAlgorithm(int theAlgorithm)
    {
    	cvAlgorithm = theAlgorithm;
    }
    
    public void setRadium(int theRadium)
    {
    	cvRadium = theRadium;
    }
    
    public void setBandX(int theBand)
    {
    	this.cvBandX=theBand+1;
    }
    
    public void setBandY(int theBand)
    {
    	this.cvBandY=theBand+1;
    }
    
    public void setWidth(int theWidth)
    {
    	this.cvWidth= theWidth;
    }
    
    public void setHeight(int theHeight)
    {
    	this.cvHeight= theHeight;
    }
    
    public void setRectangleSize(int theSize)
    {
    	this.cvRectangleSize = theSize;
    }
    
    public void setDistanceSD(int theDistance)
    {
    	this.cvDistanceSD= theDistance;
    }
    
    public void setSelectedPixels(boolean theSelectedPixels[][])
    {
    	this.cvSelectedPixels=theSelectedPixels;
    	
    }
    public void setWholePicture(boolean theSelection)
    {
    	this.cvWholePicture = theSelection;
    }
    
    public void setSinglePoint(boolean thePoint)
    {
    	this.cvSinglePoint= thePoint;
    }
    public void setSelectedPixelsSD()
    {
    	cvSelectedPixelsSD = new boolean [256][256];
    	for(int i=0; i< 256;i++)
    		for(int j=0; j< 256;j++)
    			cvSelectedPixelsSD[i][255-j]= cvPolygon.contains(i+15, j);
    }
    
    public void resetScatterDiagramPixelsValues()
    {
    	for (int i=0; i<256; i++)
    		for (int j=0; j<256; j++)
    		{
    			this.cvScatterDiagramPixelsValues[i][j]= 0;
    		}	
    }
    
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
        			
        			if(  cvSelectedPixels[i][j] == true)
        			{
        				//&& cvScatterDiagramPixelsValues[i][j]<13
        				cvScatterDiagramPixelsValues[cvPictureBandX[i][j]][cvPictureBandY[i][j]]++;
        				if(this.cvWholePicture & this.cvClicking & cvSinglePoint)
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
    
    public void setPictureBandX(int thePicture [][])
    {
    	this.cvPictureBandX=thePicture;
    	resetWholePicture();
    	updateWholePicture();
    }
    
    public void setPictureBandY(int thePicture [][])
    {
    	this.cvPictureBandY=thePicture;
    	resetWholePicture();
    	updateWholePicture();
    }
    
    public void updateWholePicture()
    {
    	if(cvPictureBandX!=null && cvPictureBandY!=null)
    		for(int i=0; i<cvWidth; i++)
    			for (int j=0; j<cvHeight; j++)
    				cvSDPixelsValuesWholePicture[cvPictureBandX[i][j]][cvPictureBandY[i][j]]++;	
    }
    
    public void resetWholePicture()
    {
    	if(cvPictureBandX!=null && cvPictureBandY!=null)
    		for(int i=0; i<256; i++)
    			for (int j=0; j<256; j++)
    				cvSDPixelsValuesWholePicture[i][j]=0;
    }
    
  
    public void paint(Graphics g) 
    {
    	
    	Graphics2D g2 = (Graphics2D)g;
    	
    	int lvMeanX = 0;
    	int lvMeanY = 0;
    	int lvNPoints = 1;
    	
    	//Outer rectangle      
      g2.drawRect(15,0,255,255);
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
      	int lvTheCrossX= -1;
      	int lvTheCrossY= -1;
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
      						g.setColor(Color.CYAN);
      						g.fillOval(i+15-2, 255-j-2, 4, 4);
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
      				//g2.drawLine(cvPictureBandX[i][j]+15, 255-cvPictureBandY[i][j], cvPictureBandX[i][j]+15, 255-cvPictureBandY[i][j]);
      			}
        		
        	}
      	if(lvTheCrossX!=-1)
      	{
      		g2.setColor(Color.RED);
      		//g2.drawLine(i+15,255-j,100,100);
      		g2.drawLine(lvTheCrossX+15-5, 255-lvTheCrossY, lvTheCrossX+20, 255-lvTheCrossY);
      		g2.drawLine(lvTheCrossX+15, 255-lvTheCrossY-5, lvTheCrossX+15, 255-lvTheCrossY+5);
      	}
      	
  		}
  		else
  		{
  			if(cvSelectedPixels!= null)
  				for(int i=0; i<256; i++)
  					for (int j=0; j<256; j++)
  					{
  						if(cvScatterDiagramPixelsValues[i][j] != 0)
  						{
  							if(cvClicking)
  							{
  								g.setColor(Color.CYAN);
  								g.fillOval(i+15-2, 255-j-2, 4, 4);
  							}
  							else
  							{
  								g2.setColor(new Color(Color.HSBtoRGB(0.6f+((float)cvScatterDiagramPixelsValues[i][j]/30), 1,1)));
  								g2.drawLine(i+15,255-j,i+15,255-j);
  								lvMeanX+= i*cvScatterDiagramPixelsValues[i][j];
  								lvMeanY+= j*cvScatterDiagramPixelsValues[i][j];
  								lvNPoints+=cvScatterDiagramPixelsValues[i][j];
  							}
      				//g2.drawLine(cvPictureBandX[i][j]+15, 255-cvPictureBandY[i][j], cvPictureBandX[i][j]+15, 255-cvPictureBandY[i][j]);
      			}
      	
      	}  
  		}
      //Drawing the polygon the user draw
      g.setColor(Color.BLACK);
      g2.drawPolygon(this.cvPolygon);
      
      if(cvClicking && cvPolygon.npoints>0)
    	{
   		 g.setColor(Color.RED);
   		 for (int cont=0; cont<cvPolygon.npoints; cont++)
   			 g.fillOval(this.cvPolygon.xpoints[cont]-2, this.cvPolygon.ypoints[cont]-2, 4, 4);
    	}
      
      lvMeanX= lvMeanX/lvNPoints;
			lvMeanY= lvMeanY/lvNPoints;
			
			if(cvClassify && cvDragging)	
				switch(cvAlgorithm){
					case 1: /*Minimum Distance */
					{
						//cvRadium
						if(lvMeanX!=0 && lvMeanY!=0)
						{
							g2.setColor(Color.RED);
							g2.drawLine(lvMeanX+15-5, 255-lvMeanY, lvMeanX+20, 255-lvMeanY);
							g2.drawLine(lvMeanX+15, 255-lvMeanY-5, lvMeanX+15, 255-lvMeanY+5);
							g2.setStroke(new BasicStroke(2));
							g2.drawOval(lvMeanX+15-cvRadium, 255-lvMeanY-cvRadium, cvRadium*2, cvRadium*2);
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
										lvVarX+= Math.pow((cvPictureBandX[i][j]-lvMeanX),2);
										lvVarY+= Math.pow((cvPictureBandY[i][j]-lvMeanY),2);
										lvCoVarXY+=(cvPictureBandX[i][j]-lvMeanX)*(cvPictureBandY[i][j]-lvMeanY);
									}
							}
						lvVarX/=lvNPoints;
						lvVarY/=lvNPoints;
						lvCoVarXY/=lvNPoints;
						double [][]lvCovarianceMatrix={{lvVarX,lvCoVarXY},{lvCoVarXY,lvVarY}};
      	
						double lvLambdaL= (0.5)*(lvCovarianceMatrix[0][0]+lvCovarianceMatrix[1][1]+Math.sqrt(Math.pow(lvCovarianceMatrix[0][0]-lvCovarianceMatrix[1][1], 2)+4*lvCovarianceMatrix[0][1]*lvCovarianceMatrix[0][1]));
						double lvLambdaS= (0.5)*(lvCovarianceMatrix[0][0]+lvCovarianceMatrix[1][1]-Math.sqrt(Math.pow(lvCovarianceMatrix[0][0]-lvCovarianceMatrix[1][1], 2)+4*lvCovarianceMatrix[0][1]*lvCovarianceMatrix[0][1]));

      	
						double lvRoot=4*Math.sqrt(lvLambdaL)+cvDistanceSD;
						double lvRootShort=4*Math.sqrt(lvLambdaS)+cvDistanceSD;
      	
						Matrix A = new Matrix(lvCovarianceMatrix);
						EigenvalueDecomposition e = A.eig();
						Matrix V = e.getV();
						double[][]v=V.getArray();

      	 
						g2.translate(lvMeanX+15, 255-lvMeanY);
						g2.rotate(-Math.abs(Math.atan2(v[0][0],v[0][1])));
     		
						g2.setStroke(new BasicStroke(2));
						g2.setColor(Color.RED);
						
						Shape shape = new Ellipse2D.Float(-(int)lvRoot/2, -(int)lvRootShort/2, (int)lvRoot, (int)lvRootShort);

						g2.draw(shape);
      	

					}
					break;
					case 3: /* Parallelepiped */
					{
						g2.setColor(Color.RED);
						g2.setStroke(new BasicStroke(2));
						g2.drawRect(cvPictureBandXMin+15 - cvRectangleSize, (255-cvPictureBandYMax) - cvRectangleSize, cvPictureBandXMax-cvPictureBandXMin + cvRectangleSize*2, cvPictureBandYMax-cvPictureBandYMin + cvRectangleSize*2);	
					}
					break;
					default:{
      	
					}
				}
    	}

		public void mouseClicked(MouseEvent e) 
		{
			 if (cvClicking)
    	 {   
				 this.cvOPic.setSelectedPixel(true);
    		 cvPolygon.reset();
    		 cvPolygon.addPoint(e.getX(), e.getY());
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


		
		public void mouseReleased(MouseEvent arg0) 
		{
			if (cvDragging)
    	{
    		int lvLastx =cvPolygon.npoints>0? cvPolygon.xpoints[0]: 0;
    		int lvLasty =cvPolygon.npoints>0? cvPolygon.ypoints[0]: 0;
    		int lvFirstx=cvPolygon.npoints>0? cvPolygon.xpoints[cvPolygon.npoints-1] :0;
    	  int lvFirsty=cvPolygon.npoints>0? cvPolygon.ypoints[cvPolygon.npoints-1] :0;
    	  
    	  Graphics g  = this.getGraphics();
     	 	g.setColor(Color.BLACK);
     	 	g.drawLine(lvFirstx, lvFirsty, lvLastx, lvLasty);
     	 
     	 	releasedPen();
     	  setSelectedPixelsSD();
     	 
     	  cvOPic.setSelectedPixelsSD(this.cvSelectedPixelsSD);
     	  cvOPic.repaint();
     	 
     	  this.cvOPic.setSelectedPixel(true);
     	}	
		}


		
		public void mouseDragged(MouseEvent e) 
		{
			
			if (cvDragging)
    	{
				cvPolygon.addPoint(e.getX(), e.getY());
      	penOperation(e);
    	}
			
		}


		@Override
		public void mouseMoved(MouseEvent arg0) 
		{
			// TODO Auto-generated method stub
			
		}

		 public void penOperation(MouseEvent e) 
	    {
	       Graphics g  = this.getGraphics();
	      
	       /*
	         In initial state setup default values
	         for mouse coordinates
	       */
	       if (cvInitialPen)
	       {
	          setGraphicalDefaults(e);
	          cvInitialPen = false;
	          g.drawLine(cvPrevx,cvPrevy,cvMousex,cvMousey);
	       }

	       /*
	         Make sure that the mouse has actually
	         moved from its previous position.
	       */
	       if (mouseHasMoved(e))
	       {
	          /*
	             set mouse coordinates to
	             current mouse position
	          */
	          cvMousex = e.getX();
	          cvMousey = e.getY();

	          /*
	             draw a line from the previous mouse coordinates
	             to the current mouse coordinates
	          */
	          g.drawLine(cvPrevx,cvPrevy,cvMousex,cvMousey);

	          /*
	             set the current mouse coordinates to
	             previous mouse coordinates for next time
	          */
	          cvPrevx = cvMousex;
	          cvPrevy = cvMousey;
	       }

	    }


	    public void setGraphicalDefaults(MouseEvent e)
	    {
	       cvMousex   = e.getX();
	       cvMousey   = e.getY();
	       cvPrevx    = e.getX();
	       cvPrevy    = e.getY();
	    }


	    public boolean mouseHasMoved(MouseEvent e)
	    {
	       return (cvMousex != e.getX() || cvMousey != e.getY());
	    }


	    public void releasedPen()
	    {
	       cvInitialPen = true;
	    }

 
}




