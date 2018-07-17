/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.view.console;

import org.apache.log4j.Logger;

/**
 *
 * @author Hector
 */
public class ControladorConsola {
    private ControladorVentanaConsola handler = null;

  private org.apache.log4j.Logger logger = null;

  public ControladorConsola() {
    handler = ControladorVentanaConsola.getInstance();
    //obtaining a logger instance and setting the handler
    logger = Logger.getLogger("loggerTest");
    logger.addAppender(handler);
  }

  /**
   * This method publishes the log message
   */
  public void logMessage(String mensaje) {
    logger.info(mensaje);
  }

    public void setVisible(boolean b) {
      handler.setVisible(b);
    }
}
