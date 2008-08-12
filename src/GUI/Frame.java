package GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Imagery.*;


public class Frame extends JFrame 
{
	
	String cvXAxis[];
	String cvYAxis[];
	
	JPanel cvContentPane;
  JMenuBar cvJMenuBar1 = new JMenuBar();
  JMenu cvJMenuFile = new JMenu();
  JMenuItem cvJMenuFileExit = new JMenuItem();
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
 	JComboBox cvJComboBoxX; 
 	JComboBox cvJComboBoxY;
 	
 	JPanel cvJPanelXSelection = new JPanel();
 	JPanel cvJPanelYSelection = new JPanel();
 	JPanel cvJPanelMinimumDistance = new JPanel();
 	JPanel cvJPanelMaximumLikelihood = new JPanel();
 	JPanel cvJPanelParallelepiped = new JPanel();
 	
 	JPanel cvJPanelSelectMethod = new JPanel();
 	
 	JSpinner cvJSpinnerDistanceSD = new JSpinner();
 	JSpinner cvJSpinnerDistanceMean = new JSpinner();
 	JSpinner cvJSpinnerRectangleSize = new JSpinner();
 	
  
 	ScatterDiagram cvSDiagram= new ScatterDiagram();
	OriginalPicture cvOpic= new OriginalPicture(cvSDiagram);
	
  
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
  	
  	//We have to show the bands in the combo boxes
  	cvXAxis=cvOpic.getBands();
  	cvYAxis=cvOpic.getBands();
  	cvJComboBoxX= new JComboBox(cvXAxis); 
   	cvJComboBoxY= new JComboBox(cvYAxis);
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
    this.setSize(new Dimension(685, 747));
    this.setTitle("Feature Space Interactive");
    cvStatusBar.setBorder(BorderFactory.createEtchedBorder());
    cvStatusBar.setText("Not ready yet... ");
    cvJMenuFile.setText("File");
    cvJMenuFileExit.setText("Exit");
    cvJMenuFileExit.addActionListener(new Frame_jMenuFileExit_ActionAdapter(this));
    cvJMenuHelp.setText("Help");
    cvJMenuHelpAbout.setText("About...");
    cvJMenuHelpAbout.addActionListener(new Frame_jMenuHelpAbout_ActionAdapter(this));
    cvJPanel1.setBackground(Color.white);
    cvJPanel1.setDebugGraphicsOptions(0);
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
    cvJMenuHelp.add(cvJMenuHelpAbout);
    cvJMenuBar1.add(cvJMenuFile);
    cvJMenuBar1.add(cvJMenuHelp);
    this.setJMenuBar(cvJMenuBar1);
  
    cvContentPane.add(cvStatusBar, BorderLayout.SOUTH);
    cvContentPane.add(cvJPanel1,  BorderLayout.CENTER);
    cvJPanel1.add(cvJSplitPane1, null);
    cvJSplitPane1.add(cvJPanel2, JSplitPane.LEFT);
    cvJSplitPane1.add(cvJPanel3, JSplitPane.RIGHT);
   
    cvJSplitPane1.setDividerLocation(500);
    cvJPanel2.setBorder(BorderFactory.createEtchedBorder());
    
    cvJPanelXSelection.setLayout(new BorderLayout());
    cvJPanelXSelection.setBorder(new TitledBorder("X axis band selection"));
    
    cvJPanelYSelection.setLayout(new BorderLayout());
    cvJPanelYSelection.setBorder(new TitledBorder("Y axis band selection"));
    
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
 		
 		cvJRadioButtonPS.addActionListener(new Frame_jRadioButtonPS_ActionAdapter(this));
 		cvJRadioButtonDrag.addActionListener(new Frame_jRadioButtonDrag_ActionAdapter(this));
 	  cvJRadioButtonParallel.addActionListener(new Frame_jRadioButtonParallel_ActionAdapter(this));
 		cvJRadioButtonMinDist.addActionListener(new Frame_jRadioButtonMinDist_ActionAdapter(this));
  	cvJRadioButtonMaxLike.addActionListener(new Frame_jRadioButtonMaxLike_ActionAdapter(this));
 	  
 		
 		cvJPanelXSelection.add(cvJComboBoxX);
 		
 		cvJPanelYSelection.add(cvJComboBoxY);
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
 		
 		
 		cvJPanelXSelection.setBackground(Color.white);
    cvJPanelYSelection.setBackground(Color.white);
    cvJPanelSelectMethod.setBackground(Color.white);
    cvJPanel2.add(cvJPanelXSelection);
    
    cvJPanelXSelection.setBounds(10,320,120,53);
    cvJPanel2.add(cvJPanelYSelection);
    cvJPanelYSelection.setBounds(140,320,120,53);
    
    cvJPanel2.add(cvJPanelSelectMethod);
    cvJPanelSelectMethod.setBounds(300,320,240,53);
    
   	cvJSpinnerDistanceMean.addChangeListener(new Frame_jSpinnerDistanceMean_ActionAdapter(this));
    cvJSpinnerDistanceSD.addChangeListener(new Frame_jSpinnerDistanceSD_ActionAdapter(this));
   	cvJSpinnerRectangleSize.addChangeListener(new Frame_jSpinnerRectangleSize_ActionAdapter(this));
    
    cvJComboBoxX.addItemListener(new Frame_jComboBoxX_mouseAdapter(this));
    cvJComboBoxY.addItemListener (new Frame_jComboBoxY_mouseAdapter(this));
  }


  public void jMenuFileExit_actionPerformed(ActionEvent e) 
  {
    System.exit(0);
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
  
  void jComboBoxX_mouseClicked(ItemEvent e) 
  {
  	this.cvSDiagram.setBandX(cvJComboBoxX.getSelectedIndex());
  	this.cvOpic.setBandX(cvJComboBoxX.getSelectedIndex());
  	this.cvOpic.repaint();
  }
  
  void jComboBoxY_mouseClicked(ItemEvent e) 
  {  
  	this.cvSDiagram.setBandY(cvJComboBoxY.getSelectedIndex());
  	this.cvOpic.setBandY(cvJComboBoxY.getSelectedIndex());
  	this.cvOpic.repaint();
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

class Frame_jRadioButtonDrag_ActionAdapter implements ActionListener 
{
	Frame adaptee;
	
	Frame_jRadioButtonDrag_ActionAdapter(Frame adaptee)
	{
		this.adaptee= adaptee;
	}
	public void actionPerformed(ActionEvent e) 
	{
		adaptee.jRadioButtonDrag_ActionPerformed(e);
	}
}


class Frame_jRadioButtonPS_ActionAdapter implements ActionListener 
{
	Frame adaptee;
	
	Frame_jRadioButtonPS_ActionAdapter(Frame adaptee)
	{
		this.adaptee= adaptee;
	}
	public void actionPerformed(ActionEvent e) 
	{
		adaptee.jRadioButtonPS_ActionPerformed(e);
	}
}


class Frame_jMenuFileExit_ActionAdapter implements ActionListener 
{
  Frame adaptee;

  Frame_jMenuFileExit_ActionAdapter(Frame adaptee) 
  {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) 
  {
    adaptee.jMenuFileExit_actionPerformed(e);
  }
}

class Frame_jMenuHelpAbout_ActionAdapter implements ActionListener 
{
  Frame adaptee;

  Frame_jMenuHelpAbout_ActionAdapter(Frame adaptee) 
  {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) 
  {
    adaptee.jMenuHelpAbout_actionPerformed(e);
  }
}


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


class Frame_jComboBoxX_mouseAdapter  implements ItemListener
{
  Frame adaptee;

  Frame_jComboBoxX_mouseAdapter(Frame adaptee) 
  {
    this.adaptee = adaptee;
  }

	public void itemStateChanged(ItemEvent e) 
	{
		adaptee.jComboBoxX_mouseClicked(e);
	}
}

class Frame_jComboBoxY_mouseAdapter implements ItemListener 
{
  Frame adaptee;

  Frame_jComboBoxY_mouseAdapter(Frame adaptee) 
  {
    this.adaptee = adaptee;
  }
 

	public void itemStateChanged(ItemEvent e) 
	{
		adaptee.jComboBoxY_mouseClicked(e);
	}
}

class Frame_jRadioButtonParallel_ActionAdapter implements ActionListener
{

	Frame adaptee;
	
	Frame_jRadioButtonParallel_ActionAdapter(Frame adaptee)
	{
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{

		adaptee.jRadioButtonParallel_ActionPerformed(e);
		
	}
	
}


class Frame_jRadioButtonMinDist_ActionAdapter implements ActionListener
{

	Frame adaptee;
	
	Frame_jRadioButtonMinDist_ActionAdapter(Frame adaptee)
	{
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{

		adaptee.jRadioButtonMinDist_ActionPerformed(e);
		
	}
	
}

class Frame_jRadioButtonMaxLike_ActionAdapter implements ActionListener
{
	Frame adaptee;
	
	Frame_jRadioButtonMaxLike_ActionAdapter(Frame adaptee)
	{
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{

		adaptee.jRadioButtonMaxLike_ActionPerformed(e);
		
	}
}


class Frame_jSpinnerDistanceMean_ActionAdapter implements ChangeListener
{
	Frame adaptee;
	
	Frame_jSpinnerDistanceMean_ActionAdapter(Frame adaptee)
	{
		this.adaptee = adaptee;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) 
	{

		adaptee.jSpinnerDistanceMean_ActionPerformed(e);
		
	}

	
}

class Frame_jSpinnerRectangleSize_ActionAdapter implements ChangeListener
{
	Frame adaptee;
		
	Frame_jSpinnerRectangleSize_ActionAdapter(Frame adaptee)
	{
		this.adaptee = adaptee;
	}
		
	@Override
	public void stateChanged(ChangeEvent e) 
	{

		adaptee.jSpinnerRectangleSize_ActionPerformed(e);
			
	}		
}

class Frame_jSpinnerDistanceSD_ActionAdapter implements ChangeListener
{
	Frame adaptee;
	
	Frame_jSpinnerDistanceSD_ActionAdapter(Frame adaptee)
	{
		this.adaptee = adaptee;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) 
	{
		adaptee.jSpinnerDistanceSD_ActionPerformed(e);
	}
}




























