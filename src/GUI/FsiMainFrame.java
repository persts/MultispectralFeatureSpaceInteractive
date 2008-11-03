/*
** File: FsiMainFrame.java
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
    cvStatusBar.setText("amnh");
    
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
	  lvConstraints.fill = GridBagConstraints.HORIZONTAL;
	  lvConstraints.anchor = GridBagConstraints.WEST;
	  lvConstraints.gridwidth = 1;
	  lvConstraints.gridheight = 1; 
		lvConstraints.gridx = 0;
	  lvConstraints.gridy = 6;
	  lvJPanelSelectionAlgorithm.add(cvJCheckBoxClassify, lvConstraints);

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
	  	
  }
  
  /**
   * Actions performed by the different buttons on the fram
   * @param evt
   * @see ActionEvent
   */
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

  /**
   * Actions performed when some components change the state
   * @param evt
   * @see ChangeEvent
   */
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
  
  /**
   * Resets the polygon, the number of points it contains after this operation is 0
   * @return A ScatterDiagram object
   */
  public ScatterDiagram getScatterDiagram()
  {
  	return this.cvScatterDiagram;
  }
  
  /**
   * Actions performed when some components change the state
   * @param evt
   * @see ChangeEvent
   */
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
  
  /**
   * Event handler for jCheckBoxClassify
   * @param e
   * @see ItemEvent
   */
  void jCheckBoxClassify_mouseClicked(ItemEvent e)
  {
	  if(cvJCheckBoxClassify.isSelected())
	  {
		  this.cvScatterDiagram.setClassify(true);
		  this.cvOpic.setClassify(true);
		  /*Delete the polygon in case it was drawn before*/
		  this.cvOpic.getPolygon().reset();
		  this.cvScatterDiagram.getPolygon().reset();
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
  
  /**
   * Event handler for jCheckBoxWholeImage
   * @param e
   * @see ItemEvent
   */
  void jCheckBoxWholeImage_mouseClicked(ItemEvent e)
  {
	  if (cvJCheckBoxWholeImage.isSelected())
	  {
	  	this.cvScatterDiagram.setSinglePoint(true);
  		this.cvScatterDiagram.setSelectedPixelsSD();
	  	cvScatterDiagram.setWidth(this.cvOpic.getWidth());
	  	cvScatterDiagram.setHeight(this.cvOpic.getHeight());
	  	this.cvScatterDiagram.setWholePicture(true);
	  	this.cvScatterDiagram.resetWholePicture();
	  	this.cvScatterDiagram.updateWholePicture();
	  	this.cvOpic.repaint();
	  }
	  else
	  {
	  	this.cvScatterDiagram.setWholePicture(false);
	  	cvScatterDiagram.repaint(); 
	  } 
  }

  /**
   * Event handler for jComboBoxX
   * @param e
   * @see ItemEvent
   */
  void jComboBoxX_mouseClicked(ItemEvent e) 
  {
  	this.cvScatterDiagram.setBandX(cvJComboBoxX.getSelectedIndex());
  	this.cvOpic.setBandX(cvJComboBoxX.getSelectedIndex());
  	this.cvOpic.repaint(); 
  }
  
  /**
   * Event handler for jComboBoxY
   * @param e
   * @see ItemEvent
   */
  void jComboBoxY_mouseClicked(ItemEvent e) 
  {  
  	this.cvScatterDiagram.setBandY(cvJComboBoxY.getSelectedIndex());
  	this.cvOpic.setBandY(cvJComboBoxY.getSelectedIndex());
  	this.cvOpic.repaint();
  }
  
  /**
   * Event handler form jMenuFileExit
   * @param e
   * @see ItemEvent
   */
  public void jMenuFileExit_actionPerformed(ActionEvent e) 
  {
  	this.dispose();
    //System.exit(0);
  }
  
  /**
   * Event handler for jMenuBandsSelection
   * @param evt
   * @see ItemEvent
   */
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

  /**
   * Event handler for jMenuHelpAbout
   * @param e
   * @see ItemEvent
   */
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
  
  /**
   * Event handler for a WindowEvent
   * @param e
   * @see WindowEvent
   */
  protected void processWindowEvent(WindowEvent e) 
  {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) 
    {
      jMenuFileExit_actionPerformed(null);
    }
  }

  /**
   * Event handler for jRadioButtonPS
   * @param e
   * @see ActionEvent
   */
  void jRadioButtonPS_ActionPerformed(ActionEvent e)
  {
  	if (cvJRadioButtonPS.isSelected())
  	{
  		
  		this.cvScatterDiagram.setClicking();
  	  cvOpic.setClicking();
  	  cvJRadioButtonDrag.setSelected(false); 
  	}
  }
  	
  /**
   * Event handler for jRadioButtonDrag
   * @param e
   * @see ActionEvent
   */
  void jRadioButtonDrag_ActionPerformed(ActionEvent e)
  {
  	if (cvJRadioButtonDrag.isSelected())
  	{
  		this.cvScatterDiagram.setDragging();
  		cvOpic.setDragging();
  	  cvJRadioButtonPS.setSelected(false);
  	}
  }
  
  /**
   * Event handler for jRadioButtonParallel
   * @param e
   * @see ActionEvent
   */
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
  
  /**
   * Event handler for jRadioButtonMaxLike
   * @param e
   * @see ActionEvent
   */
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
  
  /**
   * Event handler for jRadioButtonMinDist
   * @param e
   * @see ActionEvent
   */
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

  /**
   * Event handler for jSpinnerDistanceMean
   * @param e
   * @see ChangeEvent
   */
  void jSpinnerDistanceMean_ActionPerformed(ChangeEvent e)
  {
  	cvScatterDiagram.setRadium(Integer.parseInt(cvJSpinnerDistanceMean.getValue().toString()));
  	if (Integer.parseInt(cvJSpinnerDistanceMean.getValue().toString())<0)
  		this.cvJSpinnerDistanceMean.setValue(new Integer(0));
  	cvScatterDiagram.repaint();
  }
  
  /**
   * Event handler for jSpinnerRectangleSize
   * @param e
   * @see ChangeEvent
   */
  void jSpinnerRectangleSize_ActionPerformed(ChangeEvent e)
  {
  	cvScatterDiagram.setRectangleSize(Integer.parseInt(cvJSpinnerRectangleSize.getValue().toString()));
  	cvScatterDiagram.repaint();
  }

  /**
   * Event handler for jSpinnerDistanceSD
   * @param e
   * @see ChangeEvent
   */
  void jSpinnerDistanceSD_ActionPerformed(ChangeEvent e)
  {
  	cvScatterDiagram.setDistanceSD(Integer.parseInt(cvJSpinnerDistanceSD.getValue().toString()));
  	cvScatterDiagram.repaint();
  }
}

