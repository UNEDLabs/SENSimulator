/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.view.console;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Hector
 */
public class VentanaConsola extends JFrame {
  private int width;

  private int height;

  private JTextArea textArea = null;

  private JScrollPane pane = null;

  public VentanaConsola(String title, int width, int height) {
    super(title);
    setSize(width, height);
    textArea = new JTextArea();
    textArea.setEditable(false);
    pane = new JScrollPane(textArea);
    getContentPane().add(pane);
    setVisible(true);
  }
  
  
  /**
   * This method appends the data to the text area.
   * 
   * @param data
   *            the Logging information data
   */
  public void showInfo(String data) {
    textArea.append(data+"\n");
    this.getContentPane().validate();
  }
  
  
}
