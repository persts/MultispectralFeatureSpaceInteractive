package GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;



public class FrameAxisSelection extends JDialog implements ActionListener {
	
	Frame parent;
	String cvXAxis[];
	JComboBox cvRedComboBox;
 	JComboBox cvGreenComboBox;
 	JComboBox cvBlueComboBox;

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

 
    this.setTitle("Select band for each axis");
    panel1.setLayout(flowLayout1);
    insetsPanel1.setLayout(flowLayout1);
    JPanel lvBandSelection = new JPanel();
   	lvBandSelection.setLayout(new GridLayout(3,2));
   	
   	lvBandSelection.setBorder(new TitledBorder("Band Selection"));
   	lvBandSelection.setBounds(575, 50, 200, 100);
   	lvBandSelection.add(new JLabel("Red"));
   	
   	cvXAxis=parent.cvOpic.getBands();
   	
   	///
   	cvRedComboBox = new JComboBox(parent.cvXAxis);
   	cvGreenComboBox = new JComboBox(parent.cvXAxis);
   	cvBlueComboBox = new JComboBox(parent.cvXAxis);
    
   	cvRedComboBox.addActionListener(this); //////////////////////////
   	///
   	lvBandSelection.add(cvRedComboBox);
   	lvBandSelection.add(new JLabel("Green"));
   	
   	cvGreenComboBox.addActionListener(this);
   	lvBandSelection.add(cvGreenComboBox);
   	lvBandSelection.add(new JLabel("Blue"));
   	
   	cvBlueComboBox.addActionListener(this);
   	lvBandSelection.add(cvBlueComboBox);
   	insetsPanel1.add(lvBandSelection,null);
    
    button1.setText("OK");
    button1.addActionListener(this);
   
    this.getContentPane().add(panel1, null);

    insetsPanel1.add(button1,null);
    panel1.add(insetsPanel1, BorderLayout.SOUTH);
    
    
    
    setResizable(true);
    
    cvRedComboBox.setSelectedIndex(parent.cvOpic.getRedBand());
    cvGreenComboBox.setSelectedIndex(parent.cvOpic.getGreenBand());
    cvBlueComboBox.setSelectedIndex(parent.cvOpic.getBlueBand());
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
	  Object obj = e.getSource();
    if (e.getSource() == button1) {
      cancel();  
    }
    else if(obj == cvRedComboBox) 
    {
    	parent.cvOpic.setRedBand(cvRedComboBox.getSelectedIndex());
    	parent.cvOpic.repaint();
    	
    }
    else if(obj == cvGreenComboBox) 
    {
       parent.cvOpic.setGreenBand(cvGreenComboBox.getSelectedIndex());
       parent.cvOpic.repaint();
       
    }
    else if(obj == cvBlueComboBox) 
    {
       parent.cvOpic.setBlueBand(cvBlueComboBox.getSelectedIndex());
      parent.cvOpic.repaint();
      
    }
    
  }
  

}