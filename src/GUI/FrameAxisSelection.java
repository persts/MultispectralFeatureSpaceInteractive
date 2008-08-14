package GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;



public class FrameAxisSelection extends JDialog implements ActionListener, ItemListener {
	
	Frame parent;
	
	String cvXAxis[];
	String cvYAxis[];
	
	//
 	JComboBox cvJComboBoxX; 
 	//
 	JComboBox cvJComboBoxY;
 	//
 	JPanel cvJPanelXSelection = new JPanel();
 	//
 	JPanel cvJPanelYSelection = new JPanel();

  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  JPanel insetsPanel1 = new JPanel();
  JPanel insetsPanel2 = new JPanel();
  JPanel insetsPanel3 = new JPanel();
  JButton button1 = new JButton();
  JLabel imageLabel = new JLabel();
  JLabel label1 = new JLabel();
  JLabel label2 = new JLabel();
  JLabel label3 = new JLabel();
  JLabel label4 = new JLabel();
  ImageIcon image1 = new ImageIcon();
  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  FlowLayout flowLayout1 = new FlowLayout();
  GridLayout gridLayout1 = new GridLayout();
 
  
  public FrameAxisSelection(Frame theparent) {
    super(theparent);
    parent = theparent;
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Inicializaci�n de componentes
   * @throws java.lang.Exception
   */

  private void jbInit() throws Exception  {
	  
	  //
	  	//We have to show the bands in the combo boxes
	  	cvXAxis=parent.cvOpic.getBands();
	  	cvYAxis=parent.cvOpic.getBands();
	  	cvJComboBoxX= new JComboBox(cvXAxis); 
	   	cvJComboBoxY= new JComboBox(cvYAxis);
	   	
	   	
	   	
	    //
	    cvJPanelXSelection.setLayout(gridLayout1);
	    cvJPanelXSelection.setPreferredSize(new Dimension(150,50));
	    cvJPanelXSelection.setBorder(new TitledBorder("X axis band selection"));
	    //
	   cvJPanelYSelection.setLayout(gridLayout1);
	   cvJPanelYSelection.setPreferredSize(new Dimension(150,50));
	    cvJPanelYSelection.setBorder(new TitledBorder("Y axis band selection"));
	    
	    //
 		cvJPanelXSelection.add(cvJComboBoxX);
 		//
 		cvJPanelYSelection.add(cvJComboBoxY);
 		
 		
 		//cvJPanelXSelection.setBackground(Color.white);
    //cvJPanelYSelection.setBackground(Color.white);
    
    
    //
    cvJComboBoxX.addItemListener(this);
    cvJComboBoxY.addItemListener (this);
    this.setTitle("Select band for each axis");
    panel1.setLayout(borderLayout1);
    insetsPanel1.setLayout(flowLayout1);
    
    
    button1.setText("OK");
    button1.addActionListener(this);
   
    this.getContentPane().add(panel1, null);
    insetsPanel1.add(cvJPanelXSelection);
    insetsPanel1.add(cvJPanelYSelection);
    insetsPanel1.add(button1, null);
    panel1.add(insetsPanel1, BorderLayout.SOUTH);
    
    
    
    setResizable(true);
  }

  /**
   * Modificado para poder salir cuando se cierra la ventana
   * @param e
   */

  protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      cancel();
    }
    super.processWindowEvent(e);
  }

  /**
   * Cerrar el cuadro de di�logo
   */

  void cancel() {
    dispose();
  }

  /**
   * Cerrar el cuadro de di�logo tras un suceso de un bot�n
   * @param e
   */

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == button1) {
      cancel();
    }
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
  }
  
  //
  void jComboBoxX_mouseClicked(ItemEvent e) 
  {
  	this.parent.cvSDiagram.setBandX(cvJComboBoxX.getSelectedIndex());
  	this.parent.cvOpic.setBandX(cvJComboBoxX.getSelectedIndex());
  	this.parent.cvOpic.repaint();
  }
  //
  void jComboBoxY_mouseClicked(ItemEvent e) 
  {  
  	this.parent.cvSDiagram.setBandY(cvJComboBoxY.getSelectedIndex());
  	this.parent.cvOpic.setBandY(cvJComboBoxY.getSelectedIndex());
  	this.parent.cvOpic.repaint();
  }
}