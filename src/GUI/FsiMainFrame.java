package GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Imagery.*;


public class FsiMainFrame extends JFrame implements ActionListener, ChangeListener, ItemListener
{
	/* Current Version Number */
	private static final String version = "1.0.0";
	
	String vcBandList[];
	
 	JComboBox cvJComboBoxX; 	
 	JComboBox cvJComboBoxY;
 	
  JMenuBar cvJMenuBar1 = new JMenuBar();
  JMenu cvJMenuFile = new JMenu();
  JMenuItem cvJMenuFileExit = new JMenuItem();
  JMenuItem cvJMenuBandsSelection = new JMenuItem();
  JMenu cvJMenuHelp = new JMenu();
  JMenuItem cvJMenuHelpAbout = new JMenuItem();
  JLabel cvStatusBar = new JLabel();
  JSplitPane cvJSplitPane1 = new JSplitPane();
  TitledBorder cvTitledBorder1;
  JPanel cvJPanel3 = new JPanel();
  JRadioButton cvJRadioButtonPS= new JRadioButton();  
 	JRadioButton cvJRadioButtonDrag= new JRadioButton(); 
 	JRadioButton cvJRadioButtonMinDist= new JRadioButton();  
 	JRadioButton cvJRadioButtonMaxLike= new JRadioButton(); 
 	JRadioButton cvJRadioButtonParallel= new JRadioButton();
 	
 	JSpinner cvJSpinnerDistanceSD = new JSpinner();
 	JSpinner cvJSpinnerDistanceMean = new JSpinner();
 	JSpinner cvJSpinnerRectangleSize = new JSpinner();
 	JCheckBox cvJCheckBoxWholeImage = new  JCheckBox("Plot whole image");
 	JCheckBox cvJCheckBoxClassify = new  JCheckBox("Classify");
 	
 	ScatterDiagram cvScatterDiagram= new ScatterDiagram();
	public OriginalPicture cvOpic= new OriginalPicture(cvScatterDiagram, cvJCheckBoxWholeImage);
	
  
  /**
   * Build the main window
   */

  public FsiMainFrame() 
  {
  	try {
  		String lvLookAndFeel = UIManager.getSystemLookAndFeelClassName();
  		//GTK is broken on linux right now so let's default to something else
  		if(lvLookAndFeel == "com.sun.java.swing.plaf.gtk.GTKLookAndFeel")
  		{
  			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
  		}
  		else //Use the system look and feel
  		{
  			UIManager.setLookAndFeel(lvLookAndFeel);
  		}
      SwingUtilities.updateComponentTreeUI(this);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    
    init();
    this.cvScatterDiagram.setOPicture(this.cvOpic);
  }

  /**
   * Setting up the components
   */
  private void init()
  {
  	this.setSize(600, 600);
  	//Min width is basically the size of the scatter plot and original image
    this.setMinimumSize(new Dimension(550,550));
    
    this.setTitle("Feature Space Interactive");
    cvStatusBar.setBorder(BorderFactory.createEtchedBorder());
    cvStatusBar.setText("Not ready yet... ");
    
    cvJMenuFile.setText("File");
    
    cvJMenuBandsSelection.setText("Band Selection");
    cvJMenuBandsSelection.addActionListener(this);
    cvJMenuFile.add(cvJMenuBandsSelection);
    
    cvJMenuFileExit.setText("Exit");
    cvJMenuFileExit.addActionListener(this);
    cvJMenuFile.add(new JSeparator());
    cvJMenuFile.add(cvJMenuFileExit);
    
    cvJMenuHelp.setText("Help");
    cvJMenuHelpAbout.setText("About...");
    cvJMenuHelpAbout.addActionListener(this);
    

    cvJMenuHelp.add(cvJMenuHelpAbout);
    
    cvJMenuBar1.add(cvJMenuFile);
    cvJMenuBar1.add(cvJMenuHelp);
    this.setJMenuBar(cvJMenuBar1);
  
    this.getContentPane().add(cvStatusBar, BorderLayout.SOUTH);
    JPanel lvCenterPanel = new JPanel(); //This panel basically hold of the components
    this.getContentPane().add(lvCenterPanel,  BorderLayout.CENTER);
    
    
    /*
     * Start the Layout of the center area
     */
    lvCenterPanel.setLayout(new GridBagLayout());
  	GridBagConstraints lvConstraints = new GridBagConstraints();
  	lvCenterPanel.setBackground(Color.white);
    
  	/*
  	 * Add the ScatterPlot
  	 */
  	lvConstraints.fill = GridBagConstraints.HORIZONTAL;
  	lvConstraints.anchor = GridBagConstraints.NORTH;
  	lvConstraints.gridwidth = 2;
  	lvConstraints.gridheight = 2;
  	lvConstraints.gridx = 0;
  	lvConstraints.gridy = 0;
  	lvCenterPanel.add(cvScatterDiagram, lvConstraints);
  	
  	
  	/*
  	 * Add the Original Image
  	 */
  	lvConstraints.fill = GridBagConstraints.HORIZONTAL;
  	lvConstraints.anchor = GridBagConstraints.NORTH;
  	lvConstraints.gridwidth = 2;
  	lvConstraints.gridheight = 2;
    lvConstraints.gridx = 2;
  	lvConstraints.gridy = 0;
  	lvCenterPanel.add(cvOpic,lvConstraints);
  	
  	/*
  	 * Set up the scatter plot display controls and combo boxes
  	 */
  	JPanel lvJPanelXSelection = new JPanel();
   	JPanel lvJPanelYSelection = new JPanel();
   	
		//We have to show the bands in the combo boxes
  	//TODO: need to make sure cvOpic has real data
  	vcBandList = this.cvOpic.getBands();
	  cvJComboBoxX= new JComboBox(vcBandList); 
	  cvJComboBoxY= new JComboBox(vcBandList);
	  
		cvJComboBoxX.setBackground(Color.WHITE);
		cvJComboBoxY.setBackground(Color.WHITE);
		
		cvJComboBoxX.addItemListener(this);
		cvJComboBoxY.addItemListener (this);
		
	  if(this.cvOpic.getBands().length > 2)
	  {
	  	cvJComboBoxY.setSelectedIndex(1);
	    this.cvScatterDiagram.setBandY(cvJComboBoxY.getSelectedIndex());
	    this.cvOpic.setBandY(cvJComboBoxY.getSelectedIndex());  	
	  }
	  
	  lvJPanelXSelection.setLayout(new BorderLayout());
	  lvJPanelXSelection.setBorder(new TitledBorder("X axis"));
	  lvJPanelXSelection.setBackground(Color.WHITE);
	  
	  lvJPanelYSelection.setLayout(new BorderLayout());
	  lvJPanelYSelection.setBorder(new TitledBorder("Y axis"));
	  lvJPanelYSelection.setBackground(Color.WHITE);
	  
		lvJPanelXSelection.add(cvJComboBoxX);
		lvJPanelYSelection.add(cvJComboBoxY);
		
		lvConstraints.fill = GridBagConstraints.HORIZONTAL;
  	lvConstraints.anchor = GridBagConstraints.NORTH;
  	lvConstraints.gridwidth = 1;
  	lvConstraints.gridheight = 1;
		lvConstraints.gridx = 0;
  	lvConstraints.gridy = 3;
  	lvJPanelXSelection.setPreferredSize(new Dimension(cvScatterDiagram.getPreferredSize().width/2, lvJPanelXSelection.getPreferredSize().height));
  	lvCenterPanel.add(lvJPanelXSelection, lvConstraints);
  	
  	lvConstraints.fill = GridBagConstraints.HORIZONTAL;
  	lvConstraints.anchor = GridBagConstraints.NORTH;
  	lvConstraints.gridwidth = 1;
  	lvConstraints.gridheight = 1;
		lvConstraints.gridx = 1;
  	lvConstraints.gridy = 3;
  	lvCenterPanel.add(lvJPanelYSelection, lvConstraints);
		
  	/*
  	 * Selection Method
  	 */
  	cvJRadioButtonPS.setText("Point");
    cvJRadioButtonPS.setBackground(Color.white);
    
    cvJRadioButtonDrag.setText("Polygon");
    cvJRadioButtonDrag.setBackground(Color.white);
 		cvJRadioButtonPS.addActionListener(this);
 		cvJRadioButtonDrag.addActionListener(this);
 		
    JPanel lvJPanelSelectionMethod = new JPanel();
    lvJPanelSelectionMethod.setBackground(Color.WHITE);
    lvJPanelSelectionMethod.setLayout(new GridLayout(1,1));
    lvJPanelSelectionMethod.setBorder(new TitledBorder("Selection method"));
    lvJPanelSelectionMethod.add(cvJRadioButtonPS);
    lvJPanelSelectionMethod.add(cvJRadioButtonDrag);
    
    lvConstraints.fill = GridBagConstraints.HORIZONTAL;
  	lvConstraints.anchor = GridBagConstraints.NORTH;
  	lvConstraints.gridwidth = 2;
  	lvConstraints.gridheight = 1;
		lvConstraints.gridx = 2;
  	lvConstraints.gridy = 3;
  	lvCenterPanel.add(lvJPanelSelectionMethod, lvConstraints);
  	
  	

 
  	/*
  	 * Scatter plot display type
  	 */
  	cvJCheckBoxWholeImage.setBackground(Color.white);
  	cvJCheckBoxWholeImage.addItemListener(this);
  	lvConstraints.fill = GridBagConstraints.HORIZONTAL;
  	lvConstraints.anchor = GridBagConstraints.NORTH;
  	lvConstraints.gridwidth = 1;
  	lvConstraints.gridheight = 1;
		lvConstraints.gridx = 0;
  	lvConstraints.gridy = 2;
  	lvCenterPanel.add(cvJCheckBoxWholeImage, lvConstraints);
  	
  	/*Lower Pane*/
  	JPanel lvJPanelSelectionAlgorithm = new JPanel();
  	lvJPanelSelectionAlgorithm.setBackground(Color.LIGHT_GRAY);
  	lvJPanelSelectionAlgorithm.setLayout(new GridBagLayout());
    lvConstraints.fill = GridBagConstraints.HORIZONTAL;
  	lvConstraints.anchor = GridBagConstraints.NORTH;
  	lvConstraints.gridwidth = 5;
  	lvConstraints.gridheight = 1;
		lvConstraints.gridx = 0;
  	lvConstraints.gridy = 4;
  	lvCenterPanel.add(lvJPanelSelectionAlgorithm, lvConstraints);
  	
  	
  	/*Classify checkbox*/
  	cvJCheckBoxClassify.addItemListener(this);
  	cvJCheckBoxClassify.setBackground(Color.LIGHT_GRAY);
	  cvJCheckBoxClassify.addItemListener(this);
	  //cvJCheckBoxClassify.setHorizontalAlignment(SwingConstants.RIGHT);
	  lvConstraints.fill = GridBagConstraints.HORIZONTAL;
	  lvConstraints.anchor = GridBagConstraints.WEST;
	  lvConstraints.gridwidth = 1;
	  lvConstraints.gridheight = 1; 
		lvConstraints.gridx = 0;
	  lvConstraints.gridy = 6;
	  lvJPanelSelectionAlgorithm.add(cvJCheckBoxClassify, lvConstraints);
  	
		/*JSeparator*/
//  	lvConstraints.fill = GridBagConstraints.HORIZONTAL;
//  	lvConstraints.anchor = GridBagConstraints.SOUTH;
//  	lvConstraints.gridwidth = 0;
//  	lvConstraints.gridheight = 1;
//		lvConstraints.gridx = 0;
//  	lvConstraints.gridy = 5;
//  	JSeparator lvSeparator = new JSeparator(JSeparator.HORIZONTAL);
//  	lvCenterPanel.add(lvSeparator,lvConstraints);
//  	lvSeparator.setPreferredSize(new Dimension(500,10));
//  	 //lvCenterPanel.getPreferredSize().height)

  	
		
		

			
			/*Radio button minimum distance*/
		cvJRadioButtonMinDist.setText("Minimum distance");
		cvJRadioButtonMinDist.setBackground(Color.LIGHT_GRAY);
		cvJRadioButtonMinDist.addActionListener(this);
		cvJRadioButtonMinDist.setSelected(true);
		lvConstraints.fill = GridBagConstraints.HORIZONTAL;
		lvConstraints.anchor = GridBagConstraints.NORTH;
		lvConstraints.gridwidth = 1;
		lvConstraints.gridheight = 1;
		lvConstraints.gridx = 0;
		lvConstraints.gridy = 7;
		lvJPanelSelectionAlgorithm.add(cvJRadioButtonMinDist, lvConstraints);
	    
		/*Spinner for minimum distance*/
		JPanel lvJPanelMinimumDistance = new JPanel();	
	  cvJSpinnerDistanceMean.addChangeListener(this);
	  cvJSpinnerDistanceMean.setValue(new Integer(15));
	  lvJPanelMinimumDistance.setLayout(new BoxLayout(lvJPanelMinimumDistance, BoxLayout.Y_AXIS));
	  lvJPanelMinimumDistance.setBorder(new TitledBorder("Distance from mean"));
	 	lvJPanelMinimumDistance.setBackground(Color.LIGHT_GRAY);
	 	lvJPanelMinimumDistance.add(cvJSpinnerDistanceMean);
	  lvConstraints.fill = GridBagConstraints.HORIZONTAL;
	  lvConstraints.anchor = GridBagConstraints.NORTH;
	  lvConstraints.gridwidth = 1;
	  lvConstraints.gridheight = 1;
		lvConstraints.gridx = 0;
	  lvConstraints.gridy = 8;
	  lvJPanelSelectionAlgorithm.add(lvJPanelMinimumDistance, lvConstraints);
	    	
	    
	  	
	  /*radio button maximum likelihood*/
	  cvJRadioButtonMaxLike.addActionListener(this);
	  cvJRadioButtonMaxLike.setText("Maximum likelihood");
	  cvJRadioButtonMaxLike.setBackground(Color.LIGHT_GRAY);
	  lvConstraints.fill = GridBagConstraints.HORIZONTAL;
	  lvConstraints.anchor = GridBagConstraints.NORTH;
	  lvConstraints.gridwidth = 1;
	  lvConstraints.gridheight = 1;
		lvConstraints.gridx = 1;
	  lvConstraints.gridy = 7;
	  lvJPanelSelectionAlgorithm.add(cvJRadioButtonMaxLike, lvConstraints);
	  	
	  /*jspinner maximum likelihood*/
	  JPanel lvJPanelMaximumLikelihood = new JPanel();
	  lvJPanelMaximumLikelihood.add(cvJSpinnerDistanceSD);
	  lvJPanelMaximumLikelihood.setBackground(Color.LIGHT_GRAY);
	  cvJSpinnerDistanceSD.addChangeListener(this);
	  lvJPanelMaximumLikelihood.setBorder(new TitledBorder("Distance from SD"));
	  lvJPanelMaximumLikelihood.setLayout(new BoxLayout(lvJPanelMaximumLikelihood, BoxLayout.Y_AXIS));
	  lvConstraints.fill = GridBagConstraints.HORIZONTAL;
	  lvConstraints.anchor = GridBagConstraints.NORTH;
	  lvConstraints.gridwidth = 1;
	  lvConstraints.gridheight = 1;
		lvConstraints.gridx = 1;
	  lvConstraints.gridy = 8;
	  lvJPanelSelectionAlgorithm.add(lvJPanelMaximumLikelihood, lvConstraints);
	  	
	  /*radio button parallelepiped*/
	  cvJRadioButtonParallel.addActionListener(this);
	  cvJRadioButtonParallel.setText("Parallelepiped");
	  cvJRadioButtonParallel.setBackground(Color.LIGHT_GRAY);
	  lvConstraints.fill = GridBagConstraints.HORIZONTAL;
	  lvConstraints.anchor = GridBagConstraints.NORTH;
	  lvConstraints.gridwidth = 1;
	  lvConstraints.gridheight = 1;
		lvConstraints.gridx = 2;
	  lvConstraints.gridy = 7;
	  lvJPanelSelectionAlgorithm.add(cvJRadioButtonParallel, lvConstraints);
	  	
	  /*spinner parallelepiped size*/
	  JPanel lvJPanelParallelepiped = new JPanel();
	  lvJPanelParallelepiped.setLayout(new BorderLayout());
	 	lvJPanelParallelepiped.setBorder(new TitledBorder("Rectangle size"));
	 	lvJPanelParallelepiped.setBackground(Color.LIGHT_GRAY);
	 	lvJPanelParallelepiped.add(cvJSpinnerRectangleSize);
	  cvJSpinnerRectangleSize.addChangeListener(this);
	 	lvConstraints.fill = GridBagConstraints.HORIZONTAL;
	  lvConstraints.anchor = GridBagConstraints.NORTH;
	  lvConstraints.gridwidth = 1;
	  lvConstraints.gridheight = 1;
		lvConstraints.gridx = 2;
	  lvConstraints.gridy = 8;
	  lvJPanelSelectionAlgorithm.add(lvJPanelParallelepiped, lvConstraints);
	  	
	  
	 		
	  	/*
   
    cvJSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
    cvJSplitPane1.setBorder(null);
    cvJSplitPane1.setDividerSize(6);
    cvJSplitPane1.setOneTouchExpandable(true);
    lvJPanelScatterPlotControls.setLayout(null);
    lvJPanelScatterPlotControls.setBackground(Color.white);
    lvJPanelScatterPlotControls.addMouseListener(new Frame_jPanel2_mouseAdapter(this));
    
    cvContentPane.setBorder(null);
  
    

    cvJPanel1.add(cvJSplitPane1, null);
    cvJSplitPane1.add(lvJPanelScatterPlotControls, JSplitPane.LEFT);
    cvJSplitPane1.add(cvJPanel3, JSplitPane.RIGHT);
   
    cvJSplitPane1.setDividerLocation(400);
    lvJPanelScatterPlotControls.setBorder(BorderFactory.createEtchedBorder());

    cvJPanelSelectMethod.setBackground(Color.white);
    
    lvJPanelScatterPlotControls.add(cvJPanelSelectMethod);
    cvJPanelSelectMethod.setBounds(305,325,250,53);
   
   	
    
   	
   	*/
  }

  public void actionPerformed(ActionEvent evt) {
      Object obj = evt.getSource();
      
      if(obj == cvJRadioButtonMaxLike)
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
  
  public ScatterDiagram getScatterDiagram()
  {
  	return this.cvScatterDiagram;
  }
  
  public void itemStateChanged(ItemEvent e)
  {
	  Object obj = e.getSource();
	  
	  if(obj == cvJComboBoxX)
	  {
		  jComboBoxX_mouseClicked( e);
	  }
	  else if(obj == cvJComboBoxY)
	  {
		  jComboBoxY_mouseClicked(e);
	  }
	  else if(obj == cvJCheckBoxWholeImage)
	  {
		  jCheckBoxWholeImage_mouseClicked(e);
	  }
	  else if (obj == cvJCheckBoxClassify)
	  {
		  jCheckBoxClassify_mouseClicked(e);
	  }
  }
  
  void jCheckBoxClassify_mouseClicked(ItemEvent e)
  {
	  if(cvJCheckBoxClassify.isSelected())
	  {
		  this.cvScatterDiagram.setClassify(true);
		  this.cvOpic.setClassify(true);
		  this.cvScatterDiagram.repaint();
		  this.cvJRadioButtonPS.setEnabled(false);
		  this.cvJRadioButtonDrag.setEnabled(false);
	  }
	  else
	  {
	  	this.cvJRadioButtonPS.setEnabled(true);
	  	this.cvJRadioButtonDrag.setEnabled(true);
		  this.cvOpic.getPolygon().reset();
  		this.cvScatterDiagram.resetScatterDiagramPixelsValues();
  		this.cvScatterDiagram.setScatterDiagramPixelsValues();
  		this.cvOpic.cvSelectedPixel=false;
  		repaint();
		  this.cvScatterDiagram.setClassify(false);
		  this.cvOpic.setClassify(false);
		  this.cvScatterDiagram.repaint();
	  }
  }
  
  void jCheckBoxWholeImage_mouseClicked(ItemEvent e)
  {
	  if (cvJCheckBoxWholeImage.isSelected())
	  {
//		  boolean [][] lvSelectedPixels = new boolean [cvOpic.getHeight()][cvOpic.getWidth()];
//		  for(int i=0; i< cvOpic.getHeight();i++)
//			  for(int j=0; j< cvOpic.getWidth();j++)
//		    	lvSelectedPixels[i][j]=true;
//	   	 
//		  cvScatterDiagram.setSelectedPixels(lvSelectedPixels);
//   	 	cvScatterDiagram.setHeight(this.cvOpic.getHeight());
//   	 	cvScatterDiagram.setWidth(this.cvOpic.getWidth());
//   	  this.cvScatterDiagram.resetScatterDiagramPixelsValues();
//   	  this.cvScatterDiagram.setScatterDiagramPixelsValues();
//   	 	cvScatterDiagram.repaint(); 
	  	 
	  	 this.cvScatterDiagram.setSinglePoint(true);
	  	 //this.cvOpic.setSelectedPixel(true);
  		 this.cvScatterDiagram.setSelectedPixelsSD();
  		 
  		 
	  	cvScatterDiagram.setWidth(this.cvOpic.getWidth());
	  	cvScatterDiagram.setHeight(this.cvOpic.getHeight());
	  	this.cvScatterDiagram.setWholePicture(true);
	  	this.cvScatterDiagram.resetWholePicture();
	  	this.cvScatterDiagram.updateWholePicture();
	  	
	  	//cvScatterDiagram.repaint(); 
	  	this.cvOpic.repaint();
	  }
	  else
	  {
//		  cvScatterDiagram.setSelectedPixels(this.cvOpic.getSelectedPixels());
//	   	this.cvScatterDiagram.resetScatterDiagramPixelsValues();
//	   	this.cvScatterDiagram.setScatterDiagramPixelsValues();
//	   	cvScatterDiagram.repaint();
	  	this.cvScatterDiagram.setWholePicture(false);
	  	cvScatterDiagram.repaint(); 
	  } 
  }

  void jComboBoxX_mouseClicked(ItemEvent e) 
  {
  	this.cvScatterDiagram.setBandX(cvJComboBoxX.getSelectedIndex());
  	this.cvOpic.setBandX(cvJComboBoxX.getSelectedIndex());
  	
  	//this.cvScatterDiagram.resetWholePicture();
  	
  	this.cvOpic.repaint();
  	
  	//this.cvScatterDiagram.updateWholePicture();
  	//cvScatterDiagram.repaint(); 	 
  }
  
  void jComboBoxY_mouseClicked(ItemEvent e) 
  {  
  	this.cvScatterDiagram.setBandY(cvJComboBoxY.getSelectedIndex());
 
  	this.cvOpic.setBandY(cvJComboBoxY.getSelectedIndex());
  	
  	//this.cvScatterDiagram.resetWholePicture();
  	
  	this.cvOpic.repaint();
  	
  	//this.cvScatterDiagram.updateWholePicture();
  	//cvScatterDiagram.repaint(); 	
  }
  
  public void jMenuFileExit_actionPerformed(ActionEvent e) 
  {
    System.exit(0);
  }
  
  public void jMenuBandsSelection_actionPerformed(ActionEvent evt)
  {
	  
	  FsiRGBBandSelectionFrame frmSel = new FsiRGBBandSelectionFrame(this);
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
  	FsiAboutFrame dlg = new FsiAboutFrame(this, version);
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
  		
  		this.cvScatterDiagram.setClicking();
  	  cvOpic.setClicking();
  	  cvJRadioButtonDrag.setSelected(false); 
  	}
  }
  	
  	
  void jRadioButtonDrag_ActionPerformed(ActionEvent e)
  {
  	if (cvJRadioButtonDrag.isSelected())
  	{
  		this.cvScatterDiagram.setDragging();
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
  		this.cvScatterDiagram.setAlgorithm(3);
  		cvScatterDiagram.repaint();
  	}
  }
  
  void jRadioButtonMaxLike_ActionPerformed (ActionEvent e)
  {
  	if(cvJRadioButtonMaxLike.isSelected())
  	{
  		this.cvJRadioButtonParallel.setSelected(false);
  		this.cvJRadioButtonMinDist.setSelected(false);
  		this.cvScatterDiagram.setAlgorithm(2);
  		cvScatterDiagram.repaint();
  	}
  }
  
  void jRadioButtonMinDist_ActionPerformed(ActionEvent e)
  {
  	if(cvJRadioButtonMinDist.isSelected())
  	{
  		this.cvJRadioButtonMaxLike.setSelected(false);
  		this.cvJRadioButtonParallel.setSelected(false);
  		this.cvScatterDiagram.setAlgorithm(1);
  		cvScatterDiagram.setRadium(Integer.parseInt(cvJSpinnerDistanceMean.getValue().toString()));
  		cvScatterDiagram.repaint();
  	
  	}
  }

  void jSpinnerDistanceMean_ActionPerformed(ChangeEvent e)
  {
  	cvScatterDiagram.setRadium(Integer.parseInt(cvJSpinnerDistanceMean.getValue().toString()));
  	if (Integer.parseInt(cvJSpinnerDistanceMean.getValue().toString())<0)
  		this.cvJSpinnerDistanceMean.setValue(new Integer(0));
  	cvScatterDiagram.repaint();
  }
  
  void jSpinnerRectangleSize_ActionPerformed(ChangeEvent e)
  {
  	cvScatterDiagram.setRectangleSize(Integer.parseInt(cvJSpinnerRectangleSize.getValue().toString()));
  	cvScatterDiagram.repaint();
  }

  void jSpinnerDistanceSD_ActionPerformed(ChangeEvent e)
  {
  	cvScatterDiagram.setDistanceSD(Integer.parseInt(cvJSpinnerDistanceSD.getValue().toString()));
  	cvScatterDiagram.repaint();
  }
}


class Frame_jPanel2_mouseAdapter extends java.awt.event.MouseAdapter 
{
  FsiMainFrame adaptee;

  Frame_jPanel2_mouseAdapter(FsiMainFrame adaptee) 
  {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) 
  {
    adaptee.jPanel2_mouseClicked(e);
  }
}



