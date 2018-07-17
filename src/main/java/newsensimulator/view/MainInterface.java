package newsensimulator.view;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.Date;
import java.util.logging.*;
import javax.swing.*;
import newsensimulator.control.Controller;
import newsensimulator.model.ElectricalNetwork;
import newsensimulator.model.JADEplatform.JadeRuntime;
import newsensimulator.model.fileIO.FileIO;
import newsensimulator.model.problem.esslocationsize.RunnerFX;
import newsensimulator.model.problem.restorationservice.CentralizedServiceRestoration;
import newsensimulator.model.problem.restorationservice.ResultsPanel;
import newsensimulator.view.console.ConsolaAppender;
import newsensimulator.view.dialogwindows.*;

/**
 *
 * @author hvargas
 */
public final class MainInterface extends JFrame {//De redefine como final por Rodrigo

    private JTextArea TextConsola;
    private JPanel panelConsola;

    private static MainInterface mainInterface;
//    private FileFilter MyCustomFilter;
    private File file;
    private Options options;
    private int progress = 0;
    //public ControladorConsola Consola;
    public org.apache.log4j.Logger LoggerConsola = org.apache.log4j.Logger.getLogger("Loger de prueba");

    private String problemLabel = "Working in: ";

    private MainInterface() {

        super("SEN Simulator");
        initComponents();

        //this.jButton9.setVisible(false);
        //this.jButton10.setVisible(false);
        String aux = problemLabel + this.getProblemName() + ". Target: " + this.getMethodologyName();
        //Se ubica un label en la parte inferior del la ventana
        currentProblemLabel.setText(aux);

        AreaSimulacion.getAreaSimulacion().addPostRenderPaintable(new VisualizationViewer.Paintable() {
            int x;
            int y;
            Font font;
            FontMetrics metrics;
            int swidth;
            int sheight;
            String str = "Beta version... Smart Energy Networks Simulator (SENSimulator)";

            @Override
            public void paint(Graphics g) {
                Dimension d = AreaSimulacion.getAreaSimulacion().getSize();
                if (font == null) {
                    font = new Font(g.getFont().getName(), Font.BOLD, 20);
                    metrics = g.getFontMetrics(font);
                    swidth = metrics.stringWidth(str);
                    sheight = metrics.getMaxAscent() + metrics.getMaxDescent();
                    x = (d.width - swidth) / 2;
                    y = (int) (d.height - sheight * 1.5);
                }
                g.setFont(font);
                Color oldColor = g.getColor();
                g.setColor(Color.lightGray);
                //g.drawString(str, x, y);
                g.drawString(str, 10, 30);
                g.setColor(oldColor);
            }

            @Override
            public boolean useTransform() {
                return false;
            }
        });

        add(AreaSimulacion.getAreaSimulacion(), BorderLayout.CENTER);
        setConsole();
        setLocationRelativeTo(null);

        try {
            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.updateComponentTreeUI(fileChooser);
        SwingUtilities.updateComponentTreeUI(this);

    }

    public static MainInterface getMainInterface() {
        if (mainInterface == null) {
            mainInterface = new MainInterface();
        }
        return mainInterface;
    }

    public void setStateCheckBoxDataTable(boolean stateCheckBoxDataTable) {
        this.dataTableCheckBox.setSelected(stateCheckBoxDataTable);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    @Override
    public void setVisible(final boolean b) {
        super.setVisible(b);
    }

    public void actualizar() {
        AreaSimulacion.getAreaSimulacion().repaint();
    }

    public String getProblemName() {
        Component comp[] = this.selectOperation.getMenuComponents();
        for (int i = 0; i < comp.length; i++) {
            JMenu menu = (JMenu) comp[i];
            if (menu.getForeground().getGreen() == 255) {
                return menu.getText();
            }
        }
        return null;
    }

    public String getMethodologyName() {
        switch (getProblemName()) {
            case "Planning":
                Component comp1[] = this.planningMenu.getMenuComponents();
                for (int i = 0; i < comp1.length; i++) {
                    JCheckBoxMenuItem cb1 = (JCheckBoxMenuItem) comp1[i];
                    if (cb1.isSelected()) {
                        return cb1.getText();
                    }
                }
                break;
            case "Operation":
                Component comp2[] = this.operationMenu.getMenuComponents();
                for (int i = 0; i < comp2.length; i++) {
                    JCheckBoxMenuItem cb1 = (JCheckBoxMenuItem) comp2[i];
                    if (cb1.isSelected()) {
                        return cb1.getText();
                    }
                }
                break;
            case "Other":
                Component comp3[] = this.otherMenu.getMenuComponents();
                for (int i = 0; i < comp3.length; i++) {
                    JCheckBoxMenuItem cb1 = (JCheckBoxMenuItem) comp3[i];
                    if (cb1.isSelected()) {
                        return cb1.getText();
                    }
                }
                break;
            default:
                break;
        }
        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        jToolBar1 = new javax.swing.JToolBar();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jToolBar3 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        jButton13 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jToolBar4 = new javax.swing.JToolBar();
        jButtonPlay = new javax.swing.JButton();
        jButtonStep = new javax.swing.JButton();
        jButtonStop = new javax.swing.JButton();
        jToolBar5 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jToolBar6 = new javax.swing.JToolBar();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        currentProblemLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu7 = new javax.swing.JMenu();
        consoleCheckBox = new javax.swing.JCheckBoxMenuItem();
        dataTableCheckBox = new javax.swing.JCheckBoxMenuItem();
        resultspanelcheck = new javax.swing.JCheckBoxMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        selectOperation = new javax.swing.JMenu();
        planningMenu = new javax.swing.JMenu();
        DailyLoad = new javax.swing.JCheckBoxMenuItem();
        BESSLocationSize = new javax.swing.JCheckBoxMenuItem();
        PowerFlowNR_cb = new javax.swing.JCheckBoxMenuItem();
        PowerFlowGS_cb = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
        operationMenu = new javax.swing.JMenu();
        RestorationServiceMAS_cb = new javax.swing.JCheckBoxMenuItem();
        RestorationServiceAG_cb = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem5 = new javax.swing.JCheckBoxMenuItem();
        otherMenu = new javax.swing.JMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();

        fileChooser.setCurrentDirectory(new java.io.File("C:\\Users\\Jose Muñoz Parra\\Desktop"));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SEN Simulator ... Beta Version ");
        setIconImage(getIconImage());
        setModalExclusionType(null);
        setPreferredSize(new java.awt.Dimension(700, 800));

        jToolBar1.setBorder(null);
        jToolBar1.setFloatable(false);
        jToolBar1.setPreferredSize(new java.awt.Dimension(324, 38));

        jPanel1.setPreferredSize(new java.awt.Dimension(324, 30));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jToolBar3.setRollover(true);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newsensimulator/view/icons/file.gif"))); // NOI18N
        jButton1.setToolTipText("New");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setMargin(new java.awt.Insets(14, 2, 14, 2));
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar3.add(jButton1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newsensimulator/view/icons/openSmall.gif"))); // NOI18N
        jButton2.setToolTipText("Open");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar3.add(jButton2);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newsensimulator/view/icons/saveSmall.gif"))); // NOI18N
        jButton4.setToolTipText("Save");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar3.add(jButton4);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newsensimulator/view/icons/saveAsSmall.gif"))); // NOI18N
        jButton3.setToolTipText("Save as");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar3.add(jButton3);

        jPanel2.add(jToolBar3);

        jToolBar2.setRollover(true);

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newsensimulator/view/icons/logosmall.jpg"))); // NOI18N
        jButton13.setFocusable(false);
        jButton13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton13.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton13);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newsensimulator/view/icons/dummyagent.gif"))); // NOI18N
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton5);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newsensimulator/view/icons/sniffer.gif"))); // NOI18N
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton6);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newsensimulator/view/icons/Introspector.gif"))); // NOI18N
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton7);

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newsensimulator/view/icons/dfsmall.gif"))); // NOI18N
        jButton8.setFocusable(false);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton8);

        jPanel2.add(jToolBar2);

        jPanel1.add(jPanel2, java.awt.BorderLayout.WEST);

        jToolBar4.setFloatable(false);
        jToolBar4.setRollover(true);

        jButtonPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newsensimulator/view/icons/play1normal-icono-9007-32.png"))); // NOI18N
        jButtonPlay.setToolTipText("Play simulation");
        jButtonPlay.setEnabled(!Controller.getController().isRunningSimulation());
        jButtonPlay.setFocusable(false);
        jButtonPlay.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonPlay.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonPlay.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jButtonPlayStateChanged(evt);
            }
        });
        jButtonPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPlayActionPerformed(evt);
            }
        });
        jToolBar4.add(jButtonPlay);

        jButtonStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newsensimulator/view/icons/stepforwardnormalblue-icono-8857-32.png"))); // NOI18N
        jButtonStep.setToolTipText("Play one step");
        jButtonStep.setFocusable(false);
        jButtonStep.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonStep.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar4.add(jButtonStep);

        jButtonStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newsensimulator/view/icons/stop1normalblue-icono-5844-32.png"))); // NOI18N
        jButtonStop.setToolTipText("Stop simulation");
        jButtonStop.setEnabled(Controller.getController().isRunningSimulation());
        jButtonStop.setFocusable(false);
        jButtonStop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonStop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStopActionPerformed(evt);
            }
        });
        jToolBar4.add(jButtonStop);

        jPanel1.add(jToolBar4, java.awt.BorderLayout.EAST);

        jToolBar5.setRollover(true);

        jLabel1.setText("    ");
        jToolBar5.add(jLabel1);

        jButton9.setText("save");
        jButton9.setFocusable(false);
        jButton9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jToolBar5.add(jButton9);

        jButton10.setText("load");
        jButton10.setFocusable(false);
        jButton10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jToolBar5.add(jButton10);

        jPanel1.add(jToolBar5, java.awt.BorderLayout.CENTER);

        jToolBar1.add(jPanel1);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.NORTH);

        jToolBar6.setBorder(null);
        jToolBar6.setRollover(true);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());
        jPanel3.add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel5.setLayout(new java.awt.BorderLayout());

        currentProblemLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        currentProblemLabel.setText("            ");
        currentProblemLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel5.add(currentProblemLabel, java.awt.BorderLayout.LINE_START);

        jPanel3.add(jPanel5, java.awt.BorderLayout.SOUTH);

        jToolBar6.add(jPanel3);

        getContentPane().add(jToolBar6, java.awt.BorderLayout.SOUTH);

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newsensimulator/view/icons/file.gif"))); // NOI18N
        jMenuItem1.setText("New");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator1);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newsensimulator/view/icons/openSmall.gif"))); // NOI18N
        jMenuItem3.setText("Open");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newsensimulator/view/icons/saveSmall.gif"))); // NOI18N
        jMenuItem8.setText("Save");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem8);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newsensimulator/view/icons/saveAsSmall.gif"))); // NOI18N
        jMenuItem7.setText("Save As");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);
        jMenu1.add(jSeparator2);

        jMenuItem2.setText("Exit");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenu7.setText("View");

        consoleCheckBox.setSelected(true);
        consoleCheckBox.setText("Console");
        consoleCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consoleCheckBoxActionPerformed(evt);
            }
        });
        jMenu7.add(consoleCheckBox);

        dataTableCheckBox.setText("Data Table");
        dataTableCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataTableCheckBoxActionPerformed(evt);
            }
        });
        jMenu7.add(dataTableCheckBox);

        resultspanelcheck.setText("Results Panel");
        resultspanelcheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resultspanelcheckActionPerformed(evt);
            }
        });
        jMenu7.add(resultspanelcheck);

        jMenuBar1.add(jMenu7);

        jMenu6.setText("Tools");

        jMenu4.setText("JADE");

        jMenuItem10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newsensimulator/view/icons/logosmall.jpg"))); // NOI18N
        jMenuItem10.setText("Start RMA Agent");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem10);

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newsensimulator/view/icons/dummyagent.gif"))); // NOI18N
        jMenuItem4.setText("Start Dummy Agent");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem4);

        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newsensimulator/view/icons/sniffer.gif"))); // NOI18N
        jMenuItem6.setText("Start Sniffer Agent");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem6);

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newsensimulator/view/icons/Introspector.gif"))); // NOI18N
        jMenuItem5.setText("Start Introspector Agent");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem5);

        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newsensimulator/view/icons/dfsmall.gif"))); // NOI18N
        jMenuItem9.setText("Start DF");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem9);

        jMenu6.add(jMenu4);

        selectOperation.setText("Problem...");

        planningMenu.setText("Planning");
        planningMenu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        planningMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                planningMenuActionPerformed(evt);
            }
        });

        buttonGroup1.add(DailyLoad);
        DailyLoad.setText("Daily Load");
        DailyLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DailyLoadActionPerformed(evt);
            }
        });
        planningMenu.add(DailyLoad);

        buttonGroup1.add(BESSLocationSize);
        BESSLocationSize.setText("BESS Optimal Size & Location");
        BESSLocationSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BESSLocationSizeActionPerformed(evt);
            }
        });
        planningMenu.add(BESSLocationSize);

        buttonGroup1.add(PowerFlowNR_cb);
        PowerFlowNR_cb.setText("Power Flow (Newton-Raphson)");
        PowerFlowNR_cb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PowerFlowNR_cbActionPerformed(evt);
            }
        });
        planningMenu.add(PowerFlowNR_cb);

        buttonGroup1.add(PowerFlowGS_cb);
        PowerFlowGS_cb.setText("Power Flow (Gauss-Seidel)");
        PowerFlowGS_cb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PowerFlowGS_cbActionPerformed(evt);
            }
        });
        planningMenu.add(PowerFlowGS_cb);

        buttonGroup1.add(jCheckBoxMenuItem2);
        jCheckBoxMenuItem2.setText("other...");
        jCheckBoxMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem2ActionPerformed(evt);
            }
        });
        planningMenu.add(jCheckBoxMenuItem2);

        selectOperation.add(planningMenu);

        operationMenu.setForeground(java.awt.Color.green);
        operationMenu.setText("Operation");
        operationMenu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        operationMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                operationMenuActionPerformed(evt);
            }
        });

        buttonGroup2.add(RestorationServiceMAS_cb);
        RestorationServiceMAS_cb.setSelected(true);
        RestorationServiceMAS_cb.setText("Restoration Service (MAS)");
        RestorationServiceMAS_cb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RestorationServiceMAS_cbActionPerformed(evt);
            }
        });
        operationMenu.add(RestorationServiceMAS_cb);

        buttonGroup2.add(RestorationServiceAG_cb);
        RestorationServiceAG_cb.setText("Restoration Service (AG)");
        RestorationServiceAG_cb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RestorationServiceAG_cbActionPerformed(evt);
            }
        });
        operationMenu.add(RestorationServiceAG_cb);

        buttonGroup2.add(jCheckBoxMenuItem5);
        jCheckBoxMenuItem5.setText("other...");
        jCheckBoxMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem5ActionPerformed(evt);
            }
        });
        operationMenu.add(jCheckBoxMenuItem5);

        selectOperation.add(operationMenu);

        otherMenu.setText("Other");
        otherMenu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        buttonGroup4.add(jCheckBoxMenuItem1);
        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("Other...");
        jCheckBoxMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem1ActionPerformed(evt);
            }
        });
        otherMenu.add(jCheckBoxMenuItem1);

        selectOperation.add(otherMenu);

        jMenu6.add(selectOperation);

        jMenuBar1.add(jMenu6);

        jMenu3.setText("Help");

        jMenuItem11.setText("Options");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem11);

        jMenuItem12.setText("About...");
        jMenu3.add(jMenuItem12);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        optionPaneLimpiar();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        JadeRuntime.getJadeRuntime().startDummyAgent();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        optionPaneLimpiar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        JadeRuntime.getJadeRuntime().startRMA();
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        System.out.println("MainInterface has been closed");
        System.exit(0);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        JadeRuntime.getJadeRuntime().startSnifferAgent();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        JadeRuntime.getJadeRuntime().startInstrospectorAgent();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        JadeRuntime.getJadeRuntime().startRMA();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        JadeRuntime.getJadeRuntime().startDummyAgent();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        JadeRuntime.getJadeRuntime().startSnifferAgent();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        JadeRuntime.getJadeRuntime().startInstrospectorAgent();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        fileChooserSave();
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        fileChooserOpen();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        fileChooserSaveAs();
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        fileChooserSaveAs();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        fileChooserOpen();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        fileChooserSave();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButtonPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPlayActionPerformed
        // TODO add your handling code here:
        if (ElectricalNetwork.getElectricalNetwork().cantidadNodos() > 0) {
            switch (getProblemName()) {
                case "Planning":
                    switch (getMethodologyName()) {
                        case "TF Location (AG)":
                            Controller.getController().runTFLocationAGAlgorithm();
                            break;
                        case "Power Flow (Newton-Raphson)":
                            break;
                        case "Power Flow (Gauss-Seidel)":
                            break;
                        case "other...":
                            break;
                    }
                    break;
                case "Operation":
                    switch (getMethodologyName()) {
                        case "Restoration Service (MAS)":
                            Controller.getController().startSimulacion();
                            this.jButtonPlay.setEnabled(false);//AGREGADO POR RODRIGO
                            this.jButtonStep.setEnabled(false);//AGREGADO POR RODRIGO
                            this.jButtonStop.setEnabled(true);//AGREGADO POR RODRIGO
                            break;
                        case "Restoration Service (AG)":
                            Controller.getController().startSimulacion();
                            this.jButtonPlay.setEnabled(false);
                            this.jButtonStep.setEnabled(false);
                            this.jButtonStop.setEnabled(true);

                            //Agregado por Rodrigo/ Lisa
                            break;
                        case "other...":
                            break;
                    }
                    break;
                case "Other":
                    break;
            }
            /*
             if (getOperationMode() == 1) {
             Controller.getController().startSimulacion();

             this.jButtonPlay.setEnabled(false);
             this.jButtonStep.setEnabled(false);
             this.jButtonStop.setEnabled(true);
             }
             else if (getOperationMode() == 0) {
             this.jButtonPlay.setEnabled(false);
             this.jButtonStep.setEnabled(false);
             this.jButtonStop.setEnabled(false);
             }*/

        } else {
            JOptionPane.showMessageDialog(this, "No existe una red para simular !!!");
        }
    }//GEN-LAST:event_jButtonPlayActionPerformed

    public void changeStateButtons() {
        this.jButtonPlay.setEnabled(true);
        this.jButtonStep.setEnabled(true);
        this.jButtonStop.setEnabled(false);

    }

    private void jButtonStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStopActionPerformed
        // TODO add your handling code here:
        System.out.println("Simulacion detenida");
        Logger.getLogger(getClass().getName()).log(Level.INFO, "Simulacion detenida. ", new Date());

        Controller.getController().stopSimulacion();
        this.jButtonPlay.setEnabled(true);
        this.jButtonStep.setEnabled(true);
        this.jButtonStop.setEnabled(false);
        //AreaSimulacion.getAreaSimulacion().setBackground(new Color(200, 215, 255));
    }//GEN-LAST:event_jButtonStopActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:
        options = new Options();
        options.setLocationRelativeTo(this);
        options.setVisible(true);
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        JadeRuntime.getJadeRuntime().startDummyAgent();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jCheckBoxMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem2ActionPerformed
        setActiveProblem("Planning");
        String aux = problemLabel + this.getProblemName() + ". Target: " + this.getMethodologyName();
        currentProblemLabel.setText(aux);
    }//GEN-LAST:event_jCheckBoxMenuItem2ActionPerformed

    private void RestorationServiceMAS_cbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RestorationServiceMAS_cbActionPerformed
        setActiveProblem("Operation");
        String aux = problemLabel + this.getProblemName() + ". Target: " + this.getMethodologyName();
        currentProblemLabel.setText(aux);

        JOptionPane.showMessageDialog(this, "RS con MAS");

        Controller.getController().setNodeActiveInSimulation("Bus");
    }//GEN-LAST:event_RestorationServiceMAS_cbActionPerformed

    private void PowerFlowGS_cbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PowerFlowGS_cbActionPerformed
        setActiveProblem("Planning");
        String aux = problemLabel + this.getProblemName() + ". Target: " + this.getMethodologyName();
        currentProblemLabel.setText(aux);

        options = new Options();
        options.setLocationRelativeTo(this);
        options.setVisible(true);
    }//GEN-LAST:event_PowerFlowGS_cbActionPerformed

    private void RestorationServiceAG_cbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RestorationServiceAG_cbActionPerformed
        setActiveProblem("Operation");
        String aux = problemLabel + this.getProblemName() + ". Target: " + this.getMethodologyName();
        currentProblemLabel.setText(aux);
        CentralizedServiceRestoration.getInstance().buildFrame();
//        System.out.println("Running Centralized Restoration");//Agregado por Rodrigo

    }//GEN-LAST:event_RestorationServiceAG_cbActionPerformed

    private void planningMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_planningMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_planningMenuActionPerformed

    private void PowerFlowNR_cbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PowerFlowNR_cbActionPerformed
        setActiveProblem("Planning");
        String aux = problemLabel + this.getProblemName() + ". Target: " + this.getMethodologyName();
        currentProblemLabel.setText(aux);
    }//GEN-LAST:event_PowerFlowNR_cbActionPerformed

    private void jCheckBoxMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem5ActionPerformed
        setActiveProblem("Operation");
        String aux = problemLabel + this.getProblemName() + ". Target: " + this.getMethodologyName();
        currentProblemLabel.setText(aux);
    }//GEN-LAST:event_jCheckBoxMenuItem5ActionPerformed

    private void jCheckBoxMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem1ActionPerformed
        setActiveProblem("Other");
        String aux = problemLabel + this.getProblemName() + ". Target: " + this.getMethodologyName();
        currentProblemLabel.setText(aux);
    }//GEN-LAST:event_jCheckBoxMenuItem1ActionPerformed

    private void consoleCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consoleCheckBoxActionPerformed
        if (consoleCheckBox.isSelected()) {
            panelConsola.setVisible(true);
            //this.Consola.setVisible(true);
        } else {
            panelConsola.setVisible(false);
            //this.Consola.setVisible(false);
        }
    }//GEN-LAST:event_consoleCheckBoxActionPerformed

    private void dataTableCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataTableCheckBoxActionPerformed
        if (dataTableCheckBox.isSelected()) {
            DataTable.getDataTable().setVisible(true);
            DataTable.getDataTable().setBounds(getX() + getWidth(), getY(), DataTable.getDataTable().getWidth(), DataTable.getDataTable().getHeight());
            //this.Consola.setVisible(true);
        } else {
            DataTable.getDataTable().setVisible(false);
            //this.Consola.setVisible(false);
        }
    }//GEN-LAST:event_dataTableCheckBoxActionPerformed

    private void operationMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_operationMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_operationMenuActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        //FileModelIO f = new FileModelIO();
        //f.saveModelOnHDD();
        FileIO f = new FileIO();
        f.writeXMLFile(null);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        /*
         FileModelIO f = new FileModelIO();
         try {
         f.loadModelFromHDD();
         } catch (IOException ex) {
         Logger.getLogger(MainInterface.class.getName()).log(Level.SEVERE, null, ex);
         } catch (ClassNotFoundException ex) {
         Logger.getLogger(MainInterface.class.getName()).log(Level.SEVERE, null, ex);
         }
         */

        FileIO f = new FileIO();
        f.readXMLFile(null);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void resultspanelcheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resultspanelcheckActionPerformed

        List res = CentralizedServiceRestoration.getInstance().getResults();
        ResultsPanel pan = new ResultsPanel(res);
        pan.runresults();

    }//GEN-LAST:event_resultspanelcheckActionPerformed

    private void jButtonPlayStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jButtonPlayStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonPlayStateChanged

    private void BESSLocationSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BESSLocationSizeActionPerformed
        setActiveProblem("Planning");
        String aux = problemLabel + this.getProblemName() + ". Target: " + this.getMethodologyName();
        currentProblemLabel.setText(aux);
        RunnerFX initFrame = new RunnerFX();
        initFrame.run();
        // Aquí agregar acceso a FRAME y luego a algoritmo de ubicación y tamaño [.getInstance().buildFrame()];
    }//GEN-LAST:event_BESSLocationSizeActionPerformed

    private void DailyLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DailyLoadActionPerformed
        setActiveProblem("Planning");
        String aux = problemLabel + this.getProblemName() + ". Target: " + this.getMethodologyName();
        currentProblemLabel.setText(aux);
        // Aquí agregar acceso a FRAME y luego a daily load [.getInstance().buildFrame()];
        RunnerFX initFrame = new RunnerFX();
        initFrame.run();

    }//GEN-LAST:event_DailyLoadActionPerformed

    private void setActiveProblem(String problem) {
        Component comp[] = this.selectOperation.getMenuComponents();
        for (int i = 0; i < comp.length; i++) {
            JMenu menu = (JMenu) comp[i];
            if (menu.getText().equals(problem)) {
                menu.setForeground(Color.GREEN);
            } else {
                menu.setForeground(Color.BLACK);
            }
        }
    }

    private void setConsole() {
        /**
         * * para la consola con log4j
         */
        TextConsola = new JTextArea();
        TextConsola.setEditable(false);
        TextConsola.setVisible(true);
        JScrollPane ScrolPanelConsola = new JScrollPane(TextConsola);
        ScrolPanelConsola.setVisible(true);
        ScrolPanelConsola.setAutoscrolls(true);
        ScrolPanelConsola.setEnabled(false);

        panelConsola = new JPanel();
        panelConsola.setLayout(new BorderLayout());
        panelConsola.add(new JLabel("Console"), BorderLayout.NORTH);
        panelConsola.add(ScrolPanelConsola, BorderLayout.CENTER);
        // panelConsola.setAutoscrolls(true);
        panelConsola.setPreferredSize(new Dimension(getWidth(), 100));
        panelConsola.setVisible(this.consoleCheckBox.isSelected());
        panelConsola.setEnabled(false);
        jPanel4.add(panelConsola, BorderLayout.NORTH);

        TextConsola.append("Started...\n");

        // configuracion del logger
        LoggerConsola.addAppender(new ConsolaAppender());

        //bloquar panel
        for (Component c : jPanel4.getComponents()) {
            c.setEnabled(false);
        }
        this.currentProblemLabel.setEnabled(true);
        jPanel4.setEnabled(false);
    }

    public static synchronized void SENSimulatorConsolePrint(String msj) {
        mainInterface.TextConsola.append(msj + "\n");
        mainInterface.getContentPane().validate();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem BESSLocationSize;
    private javax.swing.JCheckBoxMenuItem DailyLoad;
    private javax.swing.JCheckBoxMenuItem PowerFlowGS_cb;
    private javax.swing.JCheckBoxMenuItem PowerFlowNR_cb;
    private javax.swing.JCheckBoxMenuItem RestorationServiceAG_cb;
    private javax.swing.JCheckBoxMenuItem RestorationServiceMAS_cb;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JCheckBoxMenuItem consoleCheckBox;
    private javax.swing.JLabel currentProblemLabel;
    private javax.swing.JCheckBoxMenuItem dataTableCheckBox;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButtonPlay;
    private javax.swing.JButton jButtonStep;
    private javax.swing.JButton jButtonStop;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JToolBar jToolBar4;
    private javax.swing.JToolBar jToolBar5;
    private javax.swing.JToolBar jToolBar6;
    private javax.swing.JMenu operationMenu;
    private javax.swing.JMenu otherMenu;
    private javax.swing.JMenu planningMenu;
    private javax.swing.JCheckBoxMenuItem resultspanelcheck;
    private javax.swing.JMenu selectOperation;
    // End of variables declaration//GEN-END:variables

    class MyCustomFilter extends javax.swing.filechooser.FileFilter {

        @Override
        public boolean accept(File _file) {
            // Allow only directories, or files with ".graphml" extension
            //return file.isDirectory() || file.getAbsolutePath().endsWith(".graphml");
            return _file.isDirectory() || _file.getAbsolutePath().endsWith(".xml");
        }

        @Override
        public String getDescription() {
            // This description will be displayed in the dialog,
            // hard-coded = ugly, should be done via I18N
            return "Text documents (*.xml)";
        }
    }

    public void fileChooserSaveAs() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        fileChooser.setSelectedFile(new File(""));// limpia el campo tipo archivo del fileChooser
        if (ElectricalNetwork.getElectricalNetwork().cantidadNodos() > 0) {
            int returnVal = fileChooser.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                if (file.getAbsolutePath().endsWith(".xml")) {
                    //FileIO.getFileIO().saveFile(file.getAbsolutePath());
                    //FileIOTest.getFileIO().saveFile2(file.getAbsolutePath());
                    FileIO.getFileIO().writeXMLFile(file.getAbsolutePath());
                } else {
                    // FileIO.getFileIO().saveFile(file.getAbsolutePath() + ".graphml");
                    FileIO.getFileIO().writeXMLFile(file.getAbsolutePath() + ".xml");
                }

            } else {
                System.out.println("File access cancelled by user.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No hay red para guardar");
        }
    }

    public void fileChooserSave() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (file != null) {
            if (file.getAbsolutePath().endsWith(".xml")) {
                //FileIO.getFileIO().saveFile(file.getAbsolutePath());
                FileIO.getFileIO().writeXMLFile(file.getAbsolutePath());
            } else {
                // FileIO.getFileIO().saveFile(file.getAbsolutePath() + ".graphml");
                FileIO.getFileIO().writeXMLFile(file.getAbsolutePath() + ".xml");
            }
        } else {
            fileChooserSaveAs();
        }
    }

    public void fileChooserOpen() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        int i = JOptionPane.YES_OPTION;
        if (ElectricalNetwork.getElectricalNetwork().cantidadNodos() > 0) {
            i = JOptionPane.showConfirmDialog(this, "Los cambios no guardados se perderan, ¿Desea continuar?", "Seleccione una opcion", JOptionPane.YES_NO_OPTION);
        }

        if (i == JOptionPane.YES_OPTION) {
            fileChooser.setSelectedFile(new File(""));//limpia el campo archivo a seleccionar del fileChooser
            int returnVal = fileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                Controller.getController().limpiar();
                file = fileChooser.getSelectedFile();
                try {
                    System.out.println("Abriendo archivo ...");
                    FileIO.getFileIO().readXMLFile(file.getAbsolutePath());
                    System.out.println("Archivo cargado correctamente.");
                    // actualizar();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                System.out.println("File access cancelled by user.");
            }
        } else if (i == JOptionPane.NO_OPTION) {
            System.out.println("operacion cancelada");
        }
    }

    public void optionPaneLimpiar() {
        if (ElectricalNetwork.getElectricalNetwork().cantidadNodos() > 0) {
            int i = JOptionPane.showConfirmDialog(this, "¿Esta seguro que desea limpiar la zona de trabajo?", "Seleccione una opcion", JOptionPane.YES_NO_OPTION);
            if (i == JOptionPane.YES_OPTION) {
                Controller.getController().stopSimulacion();
                Controller.getController().limpiar();
            } else if (i == JOptionPane.NO_OPTION) {
                System.out.println("operacion cancelada");
            }
        } else {
            System.out.println("no hay red");
        }
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.class.getResource("/newsensimulator/view/icons/images_logo2.jpg"));
        return retValue;
    }

    public double getOperationMode() {
        switch (getProblemName()) {
            case "Planning":
                Component comp1[] = this.planningMenu.getMenuComponents();
                for (int i = 0; i < comp1.length; i++) {
                    JCheckBoxMenuItem cb1 = (JCheckBoxMenuItem) comp1[i];
                    if (cb1.isSelected()) {
                        return 0 + 0.1 * i;
                    }
                }
                break;
            case "Operation":
                Component comp2[] = this.operationMenu.getMenuComponents();
                for (int i = 0; i < comp2.length; i++) {
                    JCheckBoxMenuItem cb1 = (JCheckBoxMenuItem) comp2[i];
                    if (cb1.isSelected()) {
                        return 1 + 0.1 * i;
                    }
                }
                break;
            case "Other":
                Component comp3[] = this.otherMenu.getMenuComponents();
                for (int i = 0; i < comp3.length; i++) {
                    JCheckBoxMenuItem cb1 = (JCheckBoxMenuItem) comp3[i];
                    if (cb1.isSelected()) {
                        return 2 + 0.1 * i;
                    }
                }
                break;
            default:
                break;
        }
        return -1;
    }
}
