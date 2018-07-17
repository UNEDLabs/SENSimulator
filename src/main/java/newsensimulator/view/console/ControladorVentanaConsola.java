/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.view.console;

import java.util.logging.*;
import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.LoggingEvent;

/**
 *
 * @author Hector
 */
public  class ControladorVentanaConsola implements Appender {
  //the window to which the logging is done
  private VentanaConsola window = null;

  private Formatter formatter = null;

  private Level level = null;

  //the singleton instance
  private static ControladorVentanaConsola handler = null;

  /**
   * private constructor, preventing initialization
   */
  private ControladorVentanaConsola() {
    configure();

    if (window == null){
      window = new VentanaConsola("Console", 200, 500);
      window.setLocation(50, 112);
    }
  }

  /**
   * The getInstance method returns the singleton instance of the
   * WindowHandler object It is synchronized to prevent two threads trying to
   * create an instance simultaneously. @ return WindowHandler object
   */

  public static synchronized ControladorVentanaConsola getInstance() {

    if (handler == null) {
      handler = new ControladorVentanaConsola();
    }
    return handler;
  }

  /**
   * This method loads the configuration properties from the JDK level
   * configuration file with the help of the LogManager class. It then sets
   * its level, filter and formatter properties.
   */
  private void configure() {
    LogManager manager = LogManager.getLogManager();
    String className = this.getClass().getName();
    String level = manager.getProperty(className + ".level");
    String filter = manager.getProperty(className + ".filter");
    String formatter = manager.getProperty(className + ".formatter");

    //accessing super class methods to set the parameters


  }

  /**
   * private method constructing a Filter object with the filter name.
   *
   * @param filterName
   *            the name of the filter
   * @return the Filter object
   */
  private Filter makeFilter(String filterName) {
    Class c = null;
    Filter f = null;
    try {
      c = Class.forName(filterName);
      f = (Filter) c.newInstance();
    } catch (Exception e) {
      System.out.println("There was a problem to load the filter class: "
          + filterName);
    }
    return f;
  }

  /**
   * private method creating a Formatter object with the formatter name. If no
   * name is specified, it returns a SimpleFormatter object
   *
   * @param formatterName
   *            the name of the formatter
   * @return Formatter object
   */
  private Formatter makeFormatter(String formatterName) {
    Class c = null;
    Formatter f = null;

    try {
      c = Class.forName(formatterName);
      f = (Formatter) c.newInstance();
    } catch (Exception e) {
      f = new SimpleFormatter();
    }
    return f;
  }

  /**
   * This is the overridden publish method of the abstract super class
   * Handler. This method writes the logging information to the associated
   * Java window. This method is synchronized to make it thread-safe. In case
   * there is a problem, it reports the problem with the ErrorManager, only
   * once and silently ignores the others.
   *
   * @record the LogRecord object
   *
   */

    public void flush() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() throws SecurityException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addFilter(org.apache.log4j.spi.Filter filter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public org.apache.log4j.spi.Filter getFilter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clearFilters() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doAppend(LoggingEvent le) {

       //window.showInfo(le.getRenderedMessage());
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setErrorHandler(ErrorHandler eh) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ErrorHandler getErrorHandler() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLayout(Layout layout) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Layout getLayout() {
       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public void setName(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean requiresLayout() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void setVisible(boolean b) {
        this.window.setVisible(b);
    }
}
