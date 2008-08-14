package GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Imagery.*;


public class Frame extends JFrame implements ActionListener, ChangeListener
{
	
	String cvXAxis[];
	String cvYAxis[];
	
	JPanel cvContentPane;
  JMenuBar cvJMenuBar1 = new JMenuBar();
  JMenu cvJMenuFile = new JMenu();
  JMenuItem cvJMenuFileExit = new JMenuItem();
  JMenuItem cvJMenuBandsSelection = new JMenuItem();
  JMenu cvJMenuHelp = new JMenu();
  JMenuItem cvJMenuHelpAbout = new JMenuItem();
  JLabel cvStatusBar = new JLabel();
  JPanel cvJPanel2 = new JPanel();
  BorderLayout cvBorderLayout1 = new BorderLayout();
  JPanel cvJPanel1 = new JPanel();
  GridLayout cvGridLayout1 = new GridLayout();
  JSplitPane cvJSplitPane1 = new JSplitPane();
  TitledBorder cvTitledBorder1;
  JPanel cvJPanel3 = new JPanel();
  JRadioButton cvJRadioButtonPS= new JRadioButton();  
 	JRadioButton cvJRadioButtonDrag= new JRadioButton(); 
 	JRadioButton cvJRadioButtonMinDist= new JRadioButton();  
 	JRadioButton cvJRadioButtonMaxLike= new JRadioButton(); 
 	JRadioButton cvJRadioButtonParallel= new JRadioButton();

 	
 	
 	JComboBox cvRedComboBox;
 	JComboBox cvGreenComboBox;
 	JComboBox cvBlueComboBox;
 	
 	JPanel cvJPanelMinimumDistance = new JPanel();
 	JPanel cvJPanelMaximumLikelihood = new JPanel();
 	JPanel cvJPanelParallelepiped = new JPanel();
 	
 	JPanel cvJPanelSelectMethod = new JPanel();
 	
 	JSpinner cvJSpinnerDistanceSD = new JSpinner();
 	JSpinner cvJSpinnerDistanceMean = new JSpinner();
 	JSpinner cvJSpinnerRectangleSize = new JSpinner();
 	
  
 	ScatterDiagram cvSDiagram= new ScatterDiagram();
	public OriginalPicture cvOpic= new OriginalPicture(cvSDiagram);
	
  
  /**
   * Build the frame
   */

  public Frame() 
  {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try 
    {
      jbInit();
    }
    catch(Exception e) 
    {
      e.printStackTrace();
    }
    this.cvSDiagram.setOPicture(this.cvOpic);
  }

  /**
   * Setting up the components
   * @throws java.lang.Exception
   */

  public ScatterDiagram getScatterDiagram(){
  	return this.cvSDiagram;
  	
  }
  private void jbInit() throws Exception  
  {
  	
   	
   	//Add Band selections to the frame
   	//PJE: Trying to keep your basic layout method the same 
   	//but setting all these bounds is a bit ugly.
   	JPanel lvBandSelection = new JPanel();
   	lvBandSelection.setLayout(new GridLayout(3,2));
   	lvBandSelection.setBackground(Color.white);
   	lvBandSelection.setBorder(new TitledBorder("Band Selection"));
   	lvBandSelection.setBounds(575, 50, 200, 100);
   	lvBandSelection.add(new JLabel("Red"));
   	
   	cvXAxis=cvOpic.getBands();
   	cvRedComboBox = new JComboBox(cvXAxis);
   	cvGreenComboBox = new JComboBox(cvXAxis);
   	cvBlueComboBox = new JComboBox(cvXAxis);
    
   	cvRedComboBox.addActionListener(this); //////////////////////////
   	lvBandSelection.add(cvRedComboBox);
   	lvBandSelection.add(new JLabel("Green"));
   	
   	cvGreenComboBox.addActionListener(this);
   	lvBandSelection.add(cvGreenComboBox);
   	lvBandSelection.add(new JLabel("Blue"));
   	
   	cvBlueComboBox.addActionListener(this);
   	lvBandSelection.add(cvBlueComboBox);
   	cvJPanel2.add(lvBandSelection);
   	
  	//Painting the original picture
  	cvJPanel2.add(cvOpic);
  	cvOpic.setBounds(300, 50, 256, 256);
    cvJPanel2.validate();
    //painting the scatter diagram
    cvJPanel2.add(cvSDiagram);
    cvSDiagram.setBounds(15, 50, 271, 271);
    cvJPanel2.validate();
  	
    cvContentPane = (JPanel) this.getContentPane();
    cvTitledBorder1 = new TitledBorder("");
    cvContentPane.setLayout(cvBorderLayout1);
    this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    //
    this.setSize(new Dimension(830, 600)); //747
    this.setTitle("Feature Space Interactive");
    cvStatusBar.setBorder(BorderFactory.createEtchedBorder());
    cvStatusBar.setText("Not ready yet... ");
    cvJMenuFile.setText("File");
    cvJMenuFileExit.setText("Exit");
    cvJMenuFileExit.addActionListener(this);
    cvJMenuBandsSelection.setText("Band Selection");
    cvJMenuBandsSelection.addActionListener(this);
    cvJMenuHelp.setText("Help");
    cvJMenuHelpAbout.setText("About...");
    cvJMenuHelpAbout.addActionListener(this);
    cvJPanel1.setBackground(Color.white);
    cvJPanel1.setDebugGraphicsOptions(0);
    
    ///my baby dont care...
    cvJPanel1.setMaximumSize(new Dimension(32767, 32767));
    cvJPanel1.setVerifyInputWhenFocusTarget(true);
    cvJPanel1.setLayout(cvGridLayout1);
    
    cvJSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
    cvJSplitPane1.setBorder(null);
    cvJSplitPane1.setDividerSize(6);
    cvJSplitPane1.setOneTouchExpandable(true);
    cvJPanel2.setLayout(null);
    cvJPanel2.setBackground(Color.white);
    cvJPanel2.addMouseListener(new Frame_jPanel2_mouseAdapter(this));
    
    cvContentPane.setBorder(null);
  
    cvJMenuFile.add(cvJMenuFileExit);
    cvJMenuFile.add(cvJMenuBandsSelection);
    //select bands
    cvJMenuHelp.add(cvJMenuHelpAbout);
    cvJMenuBar1.add(cvJMenuFile);
    cvJMenuBar1.add(cvJMenuHelp);
    this.setJMenuBar(cvJMenuBar1);
  
    cvContentPane.add(cvStatusBar, BorderLayout.SOUTH);
    cvContentPane.add(cvJPanel1,  BorderLayout.CENTER);
    cvJPanel1.add(cvJSplitPane1, null);
    cvJSplitPane1.add(cvJPanel2, JSplitPane.LEFT);
    cvJSplitPane1.add(cvJPanel3, JSplitPane.RIGHT);
   
    cvJSplitPane1.setDividerLocation(400);
    cvJPanel2.setBorder(BorderFactory.createEtchedBorder());

    
    cvJPanel3.setLayout(new GridLayout(2,5)); 
 		
    cvJRadioButtonPS.setText("Point selection");
    cvJRadioButtonPS.setBackground(Color.white);
    
    cvJRadioButtonDrag.setText("Area drawing");
    cvJRadioButtonDrag.setBackground(Color.white);
    
    cvJRadioButtonMinDist.setText("Minimum distance");
    cvJRadioButtonMaxLike.setText("Maximum likelihood");
    cvJRadioButtonParallel.setText("Parallelepiped");
    
 		cvJPanel3.add(cvJRadioButtonMinDist, 0); 
 		cvJPanel3.add(cvJRadioButtonMaxLike, 1); 
 		cvJPanel3.add(cvJRadioButtonParallel, 2); 
 		
 		
 		////////////////////////////////////////////////////////////////////////////////////////////////
 		cvJRadioButtonPS.addActionListener(this);
 		cvJRadioButtonDrag.addActionListener(this);
 	  cvJRadioButtonParallel.addActionListener(this);
 		cvJRadioButtonMinDist.addActionListener(this);
  	cvJRadioButtonMaxLike.addActionListener(this);
  	
 		
 		/*!!!!!Let's change the layout and hopefully it will work on ned's computer!!!!!!*/
 		cvJPanelSelectMethod.setLayout(new GridLayout());
 		cvJPanelSelectMethod.add(cvJRadioButtonPS);
 		cvJPanelSelectMethod.add(cvJRadioButtonDrag);
 		cvJPanelSelectMethod.setBorder(new TitledBorder("Selection method"));
 		
 		cvJPanelMinimumDistance.setLayout(new BorderLayout());
 		cvJPanelMinimumDistance.setBorder(new TitledBorder("Distance from mean"));
 		cvJRadioButtonMinDist.setSelected(true);
 		cvJPanel3.add(cvJPanelMinimumDistance,3);
 		cvJPanelMinimumDistance.add(cvJSpinnerDistanceMean);
 		
 		cvJPanelMaximumLikelihood.setLayout(new BorderLayout());
 		cvJPanelMaximumLikelihood.setBorder(new TitledBorder("Distance from SD"));
 		cvJPanel3.add(cvJPanelMaximumLikelihood,4);
 		cvJPanelMaximumLikelihood.add(cvJSpinnerDistanceSD);
 		
 		
 		cvJPanelParallelepiped.setLayout(new BorderLayout());
 		cvJPanelParallelepiped.setBorder(new TitledBorder("Rectangle size"));
 		cvJPanel3.add(cvJPanelParallelepiped,5);
 		cvJPanelParallelepiped.add(cvJSpinnerRectangleSize);
 		
 	
    cvJPanelSelectMethod.setBackground(Color.white);
    
    
   
    
    cvJPanel2.add(cvJPanelSelectMethod);
    cvJPanelSelectMethod.setBounds(575,150,240,53);
    
   	cvJSpinnerDistanceMean.addChangeListener(this);
    cvJSpinnerDistanceSD.addChangeListener(this);
   	cvJSpinnerRectangleSize.addChangeListener(this);
   	
  	
   	
  }

  //PJE: Implementing the actionlistener in the class can greatly simplify things
  //If you have more than one or two lines of code make a function call
  public void actionPerformed(ActionEvent evt) {
      Object obj = evt.getSource();
      if(obj == cvRedComboBox) 
      {
    	this.cvOpic.setRedBand(cvRedComboBox.getSelectedIndex());
	  	this.cvOpic.repaint();
      }
      else if(obj == cvGreenComboBox) 
      {
    	this.cvOpic.setGreenBand(cvGreenComboBox.getSelectedIndex());
  	  	this.cvOpic.repaint();
      }
      else if(obj == cvBlueComboBox) 
      {
    	this.cvOpic.setBlueBand(cvBlueComboBox.getSelectedIndex());
  	  	this.cvOpic.repaint();
      }
      else if(obj == cvJRadioButtonMaxLike)
      {
    	  jRadioButtonMaxLike_ActionPerformed(evt);
      }
      else if(obj == cvJRadioButtonMinDist)
      {
    	  jRadioButtonMinDist_ActionPerformed(evt);
      }
      else if(obj == cvJRadioButtonParallel)
      {
    	  jRadioButtonParallel_ActionPerformed(evt);
      }
      else if(obj == cvJMenuHelpAbout)
      {
    	  jMenuHelpAbout_actionPerformed(evt);
      }
      else if(obj == cvJMenuFileExit)
      {
    	  jMenuFileExit_actionPerformed(evt);
      }
      else if(obj == cvJRadioButtonPS)
      {
    	  jRadioButtonPS_ActionPerformed(evt);
      }
      else if(obj == cvJRadioButtonDrag)
      {
    	  jRadioButtonDrag_ActionPerformed(evt);
      }
      else if(obj == cvJMenuBandsSelection)
      {
    	  jMenuBandsSelection_actionPerformed(evt);
      }
     // papi papi
      
  }


  
  public void stateChanged(ChangeEvent evt) 
	{
	  Object obj = evt.getSource();
	  if(obj == cvJSpinnerDistanceSD)
	  {
		jSpinnerDistanceSD_ActionPerformed(evt);
	  }
	  else if(obj == cvJSpinnerRectangleSize)
	  {
		  jSpinnerRectangleSize_ActionPerformed(evt);
	  }
	  else if(obj == cvJSpinnerDistanceMean)
	  {
		  jSpinnerDistanceMean_ActionPerformed(evt);
	  }
	}
  
  public void jMenuFileExit_actionPerformed(ActionEvent e) 
  {
    System.exit(0);
  }
  
  public void jMenuBandsSelection_actionPerformed(ActionEvent evt)
  {
	  
	  FrameAxisSelection frmSel = new FrameAxisSelection(this);
	  Dimension dlgSize = frmSel.getPreferredSize();
	    Dimension frmSize = getSize();
	    Point loc = getLocation();
	    frmSel.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
	    frmSel.setModal(true);
	    frmSel.pack();
	    frmSel.setVisible(true);
  }


  public void jMenuHelpAbout_actionPerformed(ActionEvent e) 
  {

  	FrameAbout dlg = new FrameAbout(this);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.pack();
    dlg.setVisible(true);
  }
  
  


  protected void processWindowEvent(WindowEvent e) 
  {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) 
    {
      jMenuFileExit_actionPerformed(null);
    }
  }


  void jPanel2_mouseClicked(MouseEvent e) 
  {
  	
  }


  void jRadioButtonPS_ActionPerformed(ActionEvent e)
  {
  	if (cvJRadioButtonPS.isSelected())
  	{
  		this.cvSDiagram.setClicking();
  	  cvOpic.setClicking();
  	  cvJRadioButtonDrag.setSelected(false); 
  	}
  }
  	
  	
  void jRadioButtonDrag_ActionPerformed(ActionEvent e)
  {
  	if (cvJRadioButtonDrag.isSelected())
  	{
  		this.cvSDiagram.setDragging();
  		cvOpic.setDragging();
  	  cvJRadioButtonPS.setSelected(false);
  	}
  }
  
  void jRadioButtonParallel_ActionPerformed(ActionEvent e)
  {
  	if(cvJRadioButtonParallel.isSelected())
  	{
  		this.cvJRadioButtonMaxLike.setSelected(false);
  		this.cvJRadioButtonMinDist.setSelected(false);
  		this.cvSDiagram.setAlgorithm(3);
  		cvSDiagram.repaint();
  	}
  }
  
 
  void jRadioButtonMaxLike_ActionPerformed (ActionEvent e)
  {
  	if(cvJRadioButtonMaxLike.isSelected())
  	{
  		this.cvJRadioButtonParallel.setSelected(false);
  		this.cvJRadioButtonMinDist.setSelected(false);
  		this.cvSDiagram.setAlgorithm(2);
  		cvSDiagram.repaint();
  	}
  }
  void jRadioButtonMinDist_ActionPerformed(ActionEvent e)
  {
  	if(cvJRadioButtonMinDist.isSelected())
  	{
  		this.cvJRadioButtonMaxLike.setSelected(false);
  		this.cvJRadioButtonParallel.setSelected(false);
  		this.cvSDiagram.setAlgorithm(1);
  		cvSDiagram.setRadium(Integer.parseInt(cvJSpinnerDistanceMean.getValue().toString()));
  		cvSDiagram.repaint();
  	
  	}
  }

  
  void jSpinnerDistanceMean_ActionPerformed(ChangeEvent e)
  {
  	cvSDiagram.setRadium(Integer.parseInt(cvJSpinnerDistanceMean.getValue().toString()));
  	cvSDiagram.repaint();
  }
  
  
  void jSpinnerRectangleSize_ActionPerformed(ChangeEvent e)
  {
  	cvSDiagram.setRadium(Integer.parseInt(cvJSpinnerRectangleSize.getValue().toString()));
  	cvSDiagram.repaint();
  }

  void jSpinnerDistanceSD_ActionPerformed(ChangeEvent e)
  {
  	cvSDiagram.setRadium(Integer.parseInt(cvJSpinnerDistanceSD.getValue().toString()));
  	cvSDiagram.repaint();
  }


}

//////**************************************************************************************************/////////
class Frame_jPanel2_mouseAdapter extends java.awt.event.MouseAdapter 
{
  Frame adaptee;

  Frame_jPanel2_mouseAdapter(Frame adaptee) 
  {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) 
  {
    adaptee.jPanel2_mouseClicked(e);
  }
}



