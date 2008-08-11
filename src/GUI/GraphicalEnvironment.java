package GUI;

import javax.swing.UIManager;
import java.awt.*;



public class GraphicalEnvironment {
  boolean packFrame = false;

  public GraphicalEnvironment() {
    Frame frame = new Frame();
    //Validar marcos que tienen tamaños preestablecidos
    //Empaquetar marcos que cuentan con información de tamaño preferente útil. Ej. de su diseño.
    if (packFrame) {
      frame.pack();
    }
    else {
      frame.validate();
    }
    //Centrar la ventana
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    
    frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    frame.setVisible(true);
  }


  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    new GraphicalEnvironment();
  }
}