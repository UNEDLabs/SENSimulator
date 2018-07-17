package newsensimulator.view.dialogwindows;

import javax.swing.*;
import newsensimulator.control.Controller;
import newsensimulator.model.networkelements.*;
import newsensimulator.view.MainInterface;
import newsensimulator.view.utils.VertexIconTransformer;

/**
 *
 * @author Jose Muñoz Parra
 */
public class Properties extends javax.swing.JDialog {

    private Vertex vertex;
    private Edge line;
    private boolean creando;
    private int initialType;

    private final ImageIcon busIn = new ImageIcon(getClass().getResource("/newsensimulator/view/icons/posteMadera2.png"));
    private final ImageIcon busOut = new ImageIcon(getClass().getResource("/newsensimulator/view/icons/posteMadera2Gris.png"));
    private final ImageIcon busSB = new ImageIcon(getClass().getResource("/newsensimulator/view/icons/posteMaderaSlack2.png"));
    private final ImageIcon faultIn = new ImageIcon(getClass().getResource("/newsensimulator/view/icons/fault.png"));
    private final ImageIcon faultOut = new ImageIcon(getClass().getResource("/newsensimulator/view/icons/noFault.png"));

    private final ImageIcon loadIn = new ImageIcon(getClass().getResource("/newsensimulator/view/icons/load.png"));
    private final ImageIcon loadOut = new ImageIcon(getClass().getResource("/newsensimulator/view/icons/loadOut.png"));
    private final ImageIcon residentialLoadIn = new ImageIcon(getClass().getResource("/newsensimulator/view/icons/loadHouse.png"));
    private final ImageIcon residentialLoadOut = new ImageIcon(getClass().getResource("/newsensimulator/view/icons/loadHouseOut.png"));
    private final ImageIcon commercialLoadIn = new ImageIcon(getClass().getResource("/newsensimulator/view/icons/shopping.png"));
    private final ImageIcon commercialLoadOut = new ImageIcon(getClass().getResource("/newsensimulator/view/icons/shoppingOut.png"));
    private final ImageIcon industrialLoadIn = new ImageIcon(getClass().getResource("/newsensimulator/view/icons/Industry.png"));
    private final ImageIcon industrialLoadOut = new ImageIcon(getClass().getResource("/newsensimulator/view/icons/IndustryOut.png"));
    private final ImageIcon generatorIn = new ImageIcon(VertexIconTransformer.class.getResource("/newsensimulator/view/icons/windpower-icon.png"));
    private final ImageIcon generatorIn2 = new ImageIcon(VertexIconTransformer.class.getResource("/newsensimulator/view/icons/solarpanel-icon.png"));

    private final ImageIcon generatorOut = new ImageIcon(getClass().getResource("/newsensimulator/view/icons/batteryOut.png"));
    private final ImageIcon vehicleIn = new ImageIcon(getClass().getResource("/newsensimulator/view/icons/car.png"));
    private final ImageIcon vehicleOut = new ImageIcon(getClass().getResource("/newsensimulator/view/icons/carOut.png"));
    private final ImageIcon error = new ImageIcon(getClass().getResource("/newsensimulator/view/icons/attention.png"));
    private final ImageIcon batteryIn = new ImageIcon(getClass().getResource("/newsensimulator/view/icons/battery.png"));
    private final ImageIcon batteryOut = new ImageIcon(getClass().getResource("/newsensimulator/view/icons/batteryOut.png"));

    /**
     * Creates new form intento
     */
    public Properties(Vertex nodo) {
        creando = false;
        setLocationRelativeTo(null);
        vertex = nodo;
        initComponents();
        busPanel.setVisible(false);
        loadPanel.setVisible(false);
        faultPanel.setVisible(false);
        linePanel.setVisible(false);
        generatorPanel.setVisible(false);
        EVPanel.setVisible(false);
        batteryPanel.setVisible(false);

        setEnabledPanel(nodo.getVertexType());

        setIconVertex(nodo);

        switch (nodo.getVertexType()) {
            case 0:
                busParameters(nodo);
                break;
            case 1:
                faultParameters(nodo);
                break;
            case 2:
                loadParameters(nodo);
                break;
            case 3:
                generatorParameters(nodo);
                break;
            case 4:
                electricVehicleParameters(nodo);
                break;
            case 6:
                batteryParameters(nodo);
                break;
        }

        setVisible(true);
    }

    public Properties(Vertex nodo, boolean creando) {
        this.creando = creando;
        setLocationRelativeTo(MainInterface.getMainInterface());
        vertex = nodo;
        initComponents();
        busPanel.setVisible(false);
        loadPanel.setVisible(false);
        faultPanel.setVisible(false);
        linePanel.setVisible(false);
        generatorPanel.setVisible(false);
        EVPanel.setVisible(false);
        batteryPanel.setVisible(false);

        setEnabledPanel(nodo.getVertexType());

        setIconVertex(nodo);

        switch (nodo.getVertexType()) {
            case 0:
                busParameters(nodo);
                break;
            case 1:
                faultParameters(nodo);
                break;
            case 2:
                loadParameters(nodo);
                break;
            case 3:
                generatorParameters(nodo);
                break;
            case 4:
                electricVehicleParameters(nodo);
                break;
            case 6:
                batteryParameters(nodo);
                break;
        }

        setVisible(true);
    }

    public Properties(Edge line) {
        creando = false;
        setLocationRelativeTo(null);
        this.line = line;
        initComponents();
        busPanel.setVisible(false);
        loadPanel.setVisible(false);
        faultPanel.setVisible(false);
        linePanel.setVisible(true);
        generatorPanel.setVisible(false);
        EVPanel.setVisible(false);
        batteryPanel.setVisible(false);
        lineParameters(line);
        setVisible(true);
    }

    private void setIconVertex(Vertex v) {
        this.iconLabel.setHorizontalAlignment(JLabel.CENTER);
        this.iconLabel.setVerticalAlignment(JLabel.CENTER);
        this.iconLabel.setHorizontalAlignment(JLabel.CENTER);
        this.iconLabel.setVerticalAlignment(JLabel.CENTER);

        switch (v.getVertexTypeName()) {
            case "Bus":
                if (v.getVertexAsBus().getBusCode() == 0) {
                    //LoadBus
                    iconLabel.setIcon(busIn);
                } else if (v.getVertexAsBus().getBusCode() == 1) {
                    //Slack Bus
                    iconLabel.setIcon(busSB);
                } else {
                    iconLabel.setIcon(busOut);
                }

                break;
            case "Fault":
                break;
            case "Load":
                switch (v.getVertexAsLoad().getLoadType()) {
                    case 0:
                        iconLoadLabel.setIcon(loadIn);
                        break;
                    case 1:
                        iconLoadLabel.setIcon(residentialLoadIn);
                        break;
                    case 2:
                        iconLoadLabel.setIcon(commercialLoadIn);
                        break;
                    case 3:
                        iconLoadLabel.setIcon(industrialLoadIn);
                        break;

                }
                break;
            case "ElectricVehicle":

                break;
            case "Generator":
                switch (v.getVertexAsGenerator().getGeneratorType()) {
                    case 0:
                        iconGeneratorLabel.setIcon(generatorIn2);
                        break;
                    case 1:
                        iconGeneratorLabel.setIcon(generatorIn);
                        break;
                }
                break;
            case "Battery":
                iconBatteryLabel.setIcon(batteryIn);

            default:
                break;
        }

    }

    private void setEnabledPanel(int panelType) {
        switch (panelType) {
            case 0:
                busPanel.setVisible(true);
                loadPanel.setVisible(false);
                faultPanel.setVisible(false);
                linePanel.setVisible(false);
                generatorPanel.setVisible(false);
                EVPanel.setVisible(false);
                batteryPanel.setVisible(false);
                break;
            case 1:
                busPanel.setVisible(false);
                loadPanel.setVisible(false);
                faultPanel.setVisible(true);
                linePanel.setVisible(false);
                generatorPanel.setVisible(false);
                EVPanel.setVisible(false);
                batteryPanel.setVisible(false);
                break;
            case 2:
                busPanel.setVisible(false);
                loadPanel.setVisible(true);
                faultPanel.setVisible(false);
                linePanel.setVisible(false);
                generatorPanel.setVisible(false);
                EVPanel.setVisible(false);
                batteryPanel.setVisible(false);
                break;
            case 3:
                busPanel.setVisible(false);
                loadPanel.setVisible(false);
                faultPanel.setVisible(false);
                linePanel.setVisible(false);
                generatorPanel.setVisible(true);
                EVPanel.setVisible(false);
                batteryPanel.setVisible(false);
                break;
            case 4:
                busPanel.setVisible(false);
                loadPanel.setVisible(false);
                faultPanel.setVisible(false);
                linePanel.setVisible(false);
                generatorPanel.setVisible(false);
                EVPanel.setVisible(true);
                batteryPanel.setVisible(false);
                break;
            case 6:
                busPanel.setVisible(false);
                loadPanel.setVisible(false);
                faultPanel.setVisible(false);
                linePanel.setVisible(false);
                generatorPanel.setVisible(false);
                EVPanel.setVisible(false);
                batteryPanel.setVisible(true);
        }
        setVisible(true);
    }

    private void busParameters(Vertex nodoBus) {
        initialType = nodoBus.getVertexAsBus().getBusCode();
        this.setTitle("Properties " + nodoBus.getVertexAsBus().getName());
        // Extrayendo la info del nodo y colocandola en la ventana
        //this.busNameLabel.setEnabled(false);
        this.busNameLabel.setText(nodoBus.getVertexAsBus().getName());
        this.busTypeComboBox.setSelectedIndex(nodoBus.getVertexAsBus().getBusCode());
        this.voltageBusText.setText(String.valueOf(nodoBus.getVertexAsBus().getVoltageBus()));
        this.angleBusText.setText(String.valueOf(nodoBus.getVertexAsBus().getAngleBus()));
    }

    private void faultParameters(Vertex nodoFault) {
        this.setTitle("Properties " + nodoFault.getVertexAsFault().getName());
        //this.faultNameLabel.setEnabled(false);
        this.faultNameLabel.setText(nodoFault.getVertexAsFault().getName());
        this.faultTypeComboBox.setSelectedIndex(nodoFault.getVertexAsFault().getFaultType());
        this.faultLocation.setText(nodoFault.getVertexAsFault().getLocation().getEdgeAsSimpleLine().getName());
    }

    private void loadParameters(Vertex nodoLoad) {
        this.setTitle("Properties " + nodoLoad.getVertexAsLoad().getName());
        //this.loadNameLabel.setEnabled(false);
        this.loadNameLabel.setText(nodoLoad.getVertexAsLoad().getName());
        this.loadTypeComboBox.setSelectedIndex(nodoLoad.getVertexAsLoad().getLoadType());
        this.loadPriorityComboBox.setSelectedIndex(nodoLoad.getVertexAsLoad().getLoadPriority());
        this.loadMWText.setText(String.valueOf(nodoLoad.getVertexAsLoad().getLoadMW()));
        this.loadMVarText.setText(String.valueOf(nodoLoad.getVertexAsLoad().getLoadMVar()));

        if (nodoLoad.getVertexAsLoad().getLoadState()) {
            this.closedRadioButton.setSelected(true);
        } else {
            this.openedRadioButton.setSelected(true);
        }
    }

    private void batteryParameters(Vertex nodoBattery) {
        this.setTitle("Properties "+ nodoBattery.getVertexAsBattery().getName());

        this.batteryNameLabel.setText(nodoBattery.getVertexAsBattery().getName());
        this.typeBatComboBox.setSelectedIndex(nodoBattery.getVertexAsBattery().getBatteryType());
        this.MWBatteryText.setText(String.valueOf(nodoBattery.getVertexAsBattery().getBatteryMW()));
        this.MWhBatteryText.setText(String.valueOf(nodoBattery.getVertexAsBattery().getBatteryMWh()));
        this.efficBatteryText.setText(String.valueOf(nodoBattery.getVertexAsBattery().getEfficiency()));
        this.cyclesBatteryText.setText(String.valueOf(nodoBattery.getVertexAsBattery().getCycles()));

    }

    private void generatorParameters(Vertex nodoG) {
        this.setTitle("Properties " + nodoG.getVertexAsGenerator().getName());

        this.generatorNameLabel.setText(nodoG.getVertexAsGenerator().getName());
        this.typeGenComboBox.setSelectedIndex(nodoG.getVertexAsGenerator().getGeneratorType());
        this.MWGenText.setText(String.valueOf(nodoG.getVertexAsGenerator().getMWGenerator()));
        this.MVarGenText.setText(String.valueOf(nodoG.getVertexAsGenerator().getMVarGenerator()));
        this.maxMVarGenText.setText(String.valueOf(nodoG.getVertexAsGenerator().getMaxMVarGenerator()));
        this.minMVarGenText.setText(String.valueOf(nodoG.getVertexAsGenerator().getMinMVarGenerator()));
    }

    private void electricVehicleParameters(Vertex nodoEV) {
        this.setTitle("Properties " + nodoEV.getVertexAsElectricVehicle().getName());

        this.EVNameLabel.setText(nodoEV.getVertexAsElectricVehicle().getName());
        this.EVTypeComboBox.setSelectedIndex(nodoEV.getVertexAsElectricVehicle().getElectricVehicleType());
    }

    private void lineParameters(Edge line) {
        this.setTitle("Properties " + line.getEdgeAsSimpleLine().getName());

        this.LineNameLabel.setText(line.getEdgeAsSimpleLine().getName());
        this.reactanceText.setText(String.valueOf(line.getEdgeAsSimpleLine().getReactance()));
        this.resistanceText.setText(String.valueOf(line.getEdgeAsSimpleLine().getResistance()));
        this.maxCurrentLine.setText(String.valueOf(line.getEdgeAsSimpleLine().getMaxCurrent()));
        if (line.getEdgeAsSimpleLine().getEstiloLinea() == true) {
            this.closeRadioButtonLine.setSelected(true);
        } else {
            this.openRadioButtonLine.setSelected(true);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        mainContainer = new javax.swing.JLayeredPane();
        batteryPanel = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        batteryNameLabel = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        typeBatComboBox = new javax.swing.JComboBox();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        MWhBatteryText = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        iconBatteryLabel = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JSeparator();
        MWBatteryText = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        efficBatteryText = new javax.swing.JTextField();
        cyclesBatteryText = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        busPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        busNameLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        busTypeComboBox = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        angleBusText = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        busIconPanel = new javax.swing.JPanel();
        iconLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        voltageBusText = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        loadPanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        loadNameLabel = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        loadMVarText = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        iconLoadLabel = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        loadMWText = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        loadTypeComboBox = new javax.swing.JComboBox();
        loadPriorityComboBox = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        openedRadioButton = new javax.swing.JRadioButton();
        closedRadioButton = new javax.swing.JRadioButton();
        faultPanel = new javax.swing.JPanel();
        faultNameLabel = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        faultLocation = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        faultTypeComboBox = new javax.swing.JComboBox();
        jLabel40 = new javax.swing.JLabel();
        linePanel = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        LineNameLabel = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        resistanceText = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        reactanceText = new javax.swing.JTextField();
        maxCurrentLine = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        openRadioButtonLine = new javax.swing.JRadioButton();
        closeRadioButtonLine = new javax.swing.JRadioButton();
        jLabel26 = new javax.swing.JLabel();
        generatorPanel = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        generatorNameLabel = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        typeGenComboBox = new javax.swing.JComboBox();
        jLabel28 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        MVarGenText = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        iconGeneratorLabel = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        isolatedComboBox = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JSeparator();
        MWGenText = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        minMVarGenText = new javax.swing.JTextField();
        maxMVarGenText = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        EVPanel = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        EVNameLabel = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JSeparator();
        jTextField12 = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        EVTypeComboBox = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.TOOLKIT_EXCLUDE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.FlowLayout());

        mainContainer.setAlignmentX(0.0F);
        mainContainer.setAlignmentY(0.0F);
        mainContainer.setMaximumSize(new java.awt.Dimension(250, 300));
        mainContainer.setMinimumSize(new java.awt.Dimension(250, 300));
        mainContainer.setOpaque(true);

        batteryPanel.setAlignmentX(0.0F);
        batteryPanel.setAlignmentY(0.0F);
        batteryPanel.setMaximumSize(new java.awt.Dimension(250, 300));
        batteryPanel.setMinimumSize(new java.awt.Dimension(250, 300));
        batteryPanel.setName(""); // NOI18N
        batteryPanel.setPreferredSize(new java.awt.Dimension(250, 300));
        batteryPanel.setLayout(null);

        jLabel21.setText("Battery name:");
        batteryPanel.add(jLabel21);
        jLabel21.setBounds(10, 20, 83, 16);

        batteryNameLabel.setToolTipText("");
        batteryNameLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        batteryNameLabel.setRequestFocusEnabled(false);
        batteryPanel.add(batteryNameLabel);
        batteryNameLabel.setBounds(110, 20, 60, 20);

        jLabel35.setText("Battery state:");
        batteryPanel.add(jLabel35);
        jLabel35.setBounds(10, 50, 76, 16);

        typeBatComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Charge", "Discharge" }));
        typeBatComboBox.setMaximumSize(new java.awt.Dimension(50, 20));
        typeBatComboBox.setMinimumSize(new java.awt.Dimension(50, 20));
        typeBatComboBox.setName(""); // NOI18N
        typeBatComboBox.setPreferredSize(new java.awt.Dimension(50, 20));
        typeBatComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeBatComboBoxActionPerformed(evt);
            }
        });
        batteryPanel.add(typeBatComboBox);
        typeBatComboBox.setBounds(110, 50, 81, 20);

        jLabel54.setText("MW:");
        batteryPanel.add(jLabel54);
        jLabel54.setBounds(20, 80, 25, 16);

        jLabel55.setText("MWh:");
        batteryPanel.add(jLabel55);
        jLabel55.setBounds(20, 110, 32, 16);

        MWhBatteryText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MWhBatteryTextActionPerformed(evt);
            }
        });
        batteryPanel.add(MWhBatteryText);
        MWhBatteryText.setBounds(60, 110, 69, 24);

        jLabel56.setText("Icon");
        batteryPanel.add(jLabel56);
        jLabel56.setBounds(190, 90, 24, 16);

        jPanel23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel23.setPreferredSize(new java.awt.Dimension(52, 52));

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconBatteryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(iconBatteryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        batteryPanel.add(jPanel23);
        jPanel23.setBounds(180, 113, 52, 52);

        jPanel24.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel24.setForeground(new java.awt.Color(0, 0, 255));
        jPanel24.setMaximumSize(new java.awt.Dimension(141, 16));
        jPanel24.setMinimumSize(new java.awt.Dimension(141, 16));
        jPanel24.setName(""); // NOI18N

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        batteryPanel.add(jPanel24);
        jPanel24.setBounds(40, 210, 200, 50);

        jButton13.setText("Cancel");
        jButton13.setPreferredSize(new java.awt.Dimension(70, 23));
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        batteryPanel.add(jButton13);
        jButton13.setBounds(100, 260, 70, 23);

        jButton14.setText("OK");
        jButton14.setMaximumSize(new java.awt.Dimension(65, 23));
        jButton14.setMinimumSize(new java.awt.Dimension(65, 23));
        jButton14.setPreferredSize(new java.awt.Dimension(65, 23));
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        batteryPanel.add(jButton14);
        jButton14.setBounds(180, 260, 65, 23);
        batteryPanel.add(jSeparator7);
        jSeparator7.setBounds(20, 200, 220, 10);

        MWBatteryText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MWBatteryTextActionPerformed(evt);
            }
        });
        batteryPanel.add(MWBatteryText);
        MWBatteryText.setBounds(60, 80, 69, 24);

        jLabel58.setText("[MW]");
        batteryPanel.add(jLabel58);
        jLabel58.setBounds(130, 80, 40, 20);

        jLabel59.setText("[MWh]");
        batteryPanel.add(jLabel59);
        jLabel59.setBounds(130, 110, 40, 30);

        jLabel60.setText("Effic:");
        batteryPanel.add(jLabel60);
        jLabel60.setBounds(20, 140, 28, 16);

        jLabel61.setText("Cycles");
        batteryPanel.add(jLabel61);
        jLabel61.setBounds(20, 170, 38, 16);

        efficBatteryText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                efficBatteryTextActionPerformed(evt);
            }
        });
        batteryPanel.add(efficBatteryText);
        efficBatteryText.setBounds(60, 140, 69, 24);

        cyclesBatteryText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cyclesBatteryTextActionPerformed(evt);
            }
        });
        batteryPanel.add(cyclesBatteryText);
        cyclesBatteryText.setBounds(60, 170, 69, 24);

        jLabel62.setText("%");
        batteryPanel.add(jLabel62);
        jLabel62.setBounds(130, 140, 20, 20);

        jLabel63.setText("[-]");
        batteryPanel.add(jLabel63);
        jLabel63.setBounds(130, 170, 20, 20);

        busPanel.setAlignmentX(0.0F);
        busPanel.setAlignmentY(0.0F);
        busPanel.setAutoscrolls(true);
        busPanel.setMaximumSize(new java.awt.Dimension(250, 300));
        busPanel.setMinimumSize(new java.awt.Dimension(250, 300));
        busPanel.setName(""); // NOI18N
        busPanel.setPreferredSize(new java.awt.Dimension(250, 300));
        java.awt.GridBagLayout jPanel3Layout = new java.awt.GridBagLayout();
        jPanel3Layout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0};
        jPanel3Layout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        busPanel.setLayout(jPanel3Layout);

        jLabel1.setText("Bus name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        busPanel.add(jLabel1, gridBagConstraints);

        busNameLabel.setToolTipText("");
        busNameLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        busNameLabel.setMaximumSize(new java.awt.Dimension(60, 18));
        busNameLabel.setMinimumSize(new java.awt.Dimension(60, 18));
        busNameLabel.setPreferredSize(new java.awt.Dimension(60, 18));
        busNameLabel.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        busPanel.add(busNameLabel, gridBagConstraints);

        jLabel2.setText("Bus type:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        busPanel.add(jLabel2, gridBagConstraints);

        busTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Load Bus", "Slack Bus", "Regulated Bus" }));
        busTypeComboBox.setMaximumSize(new java.awt.Dimension(50, 20));
        busTypeComboBox.setMinimumSize(new java.awt.Dimension(50, 20));
        busTypeComboBox.setName(""); // NOI18N
        busTypeComboBox.setPreferredSize(new java.awt.Dimension(50, 20));
        busTypeComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                busTypeComboBoxItemStateChanged(evt);
            }
        });
        busTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                busTypeComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 31;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 14);
        busPanel.add(busTypeComboBox, gridBagConstraints);

        jLabel4.setText("Voltage:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        busPanel.add(jLabel4, gridBagConstraints);

        jLabel5.setText("Angle:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        busPanel.add(jLabel5, gridBagConstraints);

        angleBusText.setMaximumSize(new java.awt.Dimension(60, 20));
        angleBusText.setMinimumSize(new java.awt.Dimension(60, 20));
        angleBusText.setPreferredSize(new java.awt.Dimension(60, 20));
        angleBusText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                angleBusTextActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        busPanel.add(angleBusText, gridBagConstraints);

        jLabel7.setText("Icon");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 9, 0, 9);
        busPanel.add(jLabel7, gridBagConstraints);

        busIconPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        busIconPanel.setPreferredSize(new java.awt.Dimension(52, 52));
        busIconPanel.setLayout(new java.awt.GridLayout(1, 1));
        busIconPanel.add(iconLabel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridheight = 3;
        busPanel.add(busIconPanel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.ipadx = 219;
        busPanel.add(jSeparator1, gridBagConstraints);

        voltageBusText.setMaximumSize(new java.awt.Dimension(60, 20));
        voltageBusText.setMinimumSize(new java.awt.Dimension(60, 20));
        voltageBusText.setPreferredSize(new java.awt.Dimension(60, 20));
        voltageBusText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                voltageBusTextActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        busPanel.add(voltageBusText, gridBagConstraints);

        jLabel3.setText("[pu]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        busPanel.add(jLabel3, gridBagConstraints);

        jLabel8.setText("°");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        busPanel.add(jLabel8, gridBagConstraints);

        loadPanel.setAlignmentX(0.0F);
        loadPanel.setAlignmentY(0.0F);
        loadPanel.setMaximumSize(new java.awt.Dimension(250, 300));
        loadPanel.setMinimumSize(new java.awt.Dimension(250, 300));
        loadPanel.setName(""); // NOI18N
        loadPanel.setPreferredSize(new java.awt.Dimension(250, 300));
        java.awt.GridBagLayout jPanel6Layout = new java.awt.GridBagLayout();
        jPanel6Layout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0};
        jPanel6Layout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        loadPanel.setLayout(jPanel6Layout);

        jLabel9.setText("Load name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        loadPanel.add(jLabel9, gridBagConstraints);

        loadNameLabel.setToolTipText("");
        loadNameLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        loadNameLabel.setPreferredSize(new java.awt.Dimension(20, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 41;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        loadPanel.add(loadNameLabel, gridBagConstraints);

        jLabel10.setText("Load type:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        loadPanel.add(jLabel10, gridBagConstraints);

        loadMVarText.setMaximumSize(new java.awt.Dimension(60, 18));
        loadMVarText.setMinimumSize(new java.awt.Dimension(60, 18));
        loadMVarText.setPreferredSize(new java.awt.Dimension(60, 18));
        loadMVarText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadMVarTextActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        loadPanel.add(loadMVarText, gridBagConstraints);

        jLabel13.setText("Icon");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 11, 0, 11);
        loadPanel.add(jLabel13, gridBagConstraints);

        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel12.setPreferredSize(new java.awt.Dimension(52, 52));
        jPanel12.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel12.add(iconLoadLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridheight = 3;
        loadPanel.add(jPanel12, gridBagConstraints);

        jPanel13.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel13.setForeground(new java.awt.Color(0, 0, 255));
        jPanel13.setMaximumSize(new java.awt.Dimension(167, 59));
        jPanel13.setMinimumSize(new java.awt.Dimension(167, 59));
        jPanel13.setName(""); // NOI18N
        jPanel13.setLayout(new java.awt.BorderLayout());

        jLabel14.setText("Reservo esto para futuro");
        jPanel13.add(jLabel14, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.ipady = 60;
        loadPanel.add(jPanel13, gridBagConstraints);

        jButton3.setText("Cancel");
        jButton3.setPreferredSize(new java.awt.Dimension(70, 23));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 25, 0, 0);
        loadPanel.add(jButton3, gridBagConstraints);

        jButton4.setText("OK");
        jButton4.setMaximumSize(new java.awt.Dimension(65, 23));
        jButton4.setMinimumSize(new java.awt.Dimension(65, 23));
        jButton4.setPreferredSize(new java.awt.Dimension(65, 23));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 22;
        loadPanel.add(jButton4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.ipadx = 219;
        loadPanel.add(jSeparator2, gridBagConstraints);

        loadMWText.setMaximumSize(new java.awt.Dimension(60, 18));
        loadMWText.setMinimumSize(new java.awt.Dimension(60, 18));
        loadMWText.setPreferredSize(new java.awt.Dimension(60, 18));
        loadMWText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadMWTextActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        loadPanel.add(loadMWText, gridBagConstraints);

        jLabel15.setText("[MW]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        loadPanel.add(jLabel15, gridBagConstraints);

        jLabel16.setText("[MVar]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        loadPanel.add(jLabel16, gridBagConstraints);

        loadTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Constant", "Residential", "Commercial", "Industrial" }));
        loadTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadTypeComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        loadPanel.add(loadTypeComboBox, gridBagConstraints);

        loadPriorityComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0: None", "1: Lower", "2: Middle", "3: Upper" }));
        loadPriorityComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadPriorityComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        loadPanel.add(loadPriorityComboBox, gridBagConstraints);

        jLabel17.setText("Priority:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        loadPanel.add(jLabel17, gridBagConstraints);

        jLabel18.setText("Load MW:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        loadPanel.add(jLabel18, gridBagConstraints);

        jLabel19.setText("Load MVar:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        loadPanel.add(jLabel19, gridBagConstraints);

        jLabel43.setText("State:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        loadPanel.add(jLabel43, gridBagConstraints);

        buttonGroup2.add(openedRadioButton);
        openedRadioButton.setText("Opened");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        loadPanel.add(openedRadioButton, gridBagConstraints);

        buttonGroup2.add(closedRadioButton);
        closedRadioButton.setText("Closed");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        loadPanel.add(closedRadioButton, gridBagConstraints);

        faultPanel.setAlignmentX(0.0F);
        faultPanel.setAlignmentY(0.0F);
        faultPanel.setMaximumSize(new java.awt.Dimension(250, 300));
        faultPanel.setMinimumSize(new java.awt.Dimension(250, 300));
        faultPanel.setName(""); // NOI18N
        faultPanel.setPreferredSize(new java.awt.Dimension(250, 300));
        java.awt.GridBagLayout jPanel4Layout = new java.awt.GridBagLayout();
        jPanel4Layout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0};
        jPanel4Layout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        faultPanel.setLayout(jPanel4Layout);

        faultNameLabel.setToolTipText("");
        faultNameLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        faultNameLabel.setPreferredSize(new java.awt.Dimension(20, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 41;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        faultPanel.add(faultNameLabel, gridBagConstraints);

        jLabel22.setText("Icon");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 9, 0, 9);
        faultPanel.add(jLabel22, gridBagConstraints);

        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel14.setPreferredSize(new java.awt.Dimension(52, 52));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridheight = 3;
        faultPanel.add(jPanel14, gridBagConstraints);

        jPanel15.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel15.setForeground(new java.awt.Color(0, 0, 255));
        jPanel15.setMaximumSize(new java.awt.Dimension(167, 59));
        jPanel15.setMinimumSize(new java.awt.Dimension(167, 59));
        jPanel15.setName(""); // NOI18N
        jPanel15.setLayout(new java.awt.BorderLayout());

        jLabel23.setText("Reservo esto para futuro");
        jPanel15.add(jLabel23, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.gridheight = 7;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.ipady = 105;
        faultPanel.add(jPanel15, gridBagConstraints);

        jButton5.setText("Cancel");
        jButton5.setPreferredSize(new java.awt.Dimension(70, 23));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        faultPanel.add(jButton5, gridBagConstraints);

        jButton6.setText("OK");
        jButton6.setMaximumSize(new java.awt.Dimension(65, 23));
        jButton6.setMinimumSize(new java.awt.Dimension(65, 23));
        jButton6.setPreferredSize(new java.awt.Dimension(65, 23));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        faultPanel.add(jButton6, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.ipadx = 219;
        faultPanel.add(jSeparator3, gridBagConstraints);

        faultLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                faultLocationActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 55;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 31);
        faultPanel.add(faultLocation, gridBagConstraints);

        jLabel38.setText("Fault name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        faultPanel.add(jLabel38, gridBagConstraints);

        jLabel39.setText("Fault type:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        faultPanel.add(jLabel39, gridBagConstraints);

        faultTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0 None", "1 None", "2 None", "..." }));
        faultTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                faultTypeComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        faultPanel.add(faultTypeComboBox, gridBagConstraints);

        jLabel40.setText("Location:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        faultPanel.add(jLabel40, gridBagConstraints);

        linePanel.setAlignmentX(0.0F);
        linePanel.setAlignmentY(0.0F);
        linePanel.setMaximumSize(new java.awt.Dimension(250, 300));
        linePanel.setMinimumSize(new java.awt.Dimension(250, 300));
        linePanel.setName(""); // NOI18N
        linePanel.setPreferredSize(new java.awt.Dimension(250, 300));
        java.awt.GridBagLayout jPanel16Layout = new java.awt.GridBagLayout();
        jPanel16Layout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0};
        jPanel16Layout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        linePanel.setLayout(jPanel16Layout);

        jLabel11.setText("Line name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        linePanel.add(jLabel11, gridBagConstraints);

        LineNameLabel.setToolTipText("");
        LineNameLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        LineNameLabel.setPreferredSize(new java.awt.Dimension(20, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 41;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        linePanel.add(LineNameLabel, gridBagConstraints);

        jLabel20.setText("Icon");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 11, 0, 11);
        linePanel.add(jLabel20, gridBagConstraints);

        jPanel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel17.setPreferredSize(new java.awt.Dimension(52, 52));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridheight = 3;
        linePanel.add(jPanel17, gridBagConstraints);

        jButton7.setText("Cancel");
        jButton7.setPreferredSize(new java.awt.Dimension(70, 23));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 1);
        linePanel.add(jButton7, gridBagConstraints);

        jButton8.setText("OK");
        jButton8.setMaximumSize(new java.awt.Dimension(65, 23));
        jButton8.setMinimumSize(new java.awt.Dimension(65, 23));
        jButton8.setPreferredSize(new java.awt.Dimension(65, 23));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 18;
        linePanel.add(jButton8, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.ipadx = 219;
        linePanel.add(jSeparator4, gridBagConstraints);

        jLabel24.setText("[Ω]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        linePanel.add(jLabel24, gridBagConstraints);

        jLabel25.setText("[kA]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        linePanel.add(jLabel25, gridBagConstraints);

        jLabel29.setText("Resistance");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        linePanel.add(jLabel29, gridBagConstraints);

        resistanceText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resistanceTextActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 48;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        linePanel.add(resistanceText, gridBagConstraints);

        jLabel30.setText("Reactance:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        linePanel.add(jLabel30, gridBagConstraints);

        reactanceText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reactanceTextActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 48;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        linePanel.add(reactanceText, gridBagConstraints);

        maxCurrentLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maxCurrentLineActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 48;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        linePanel.add(maxCurrentLine, gridBagConstraints);

        jLabel31.setText("Max. current:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        linePanel.add(jLabel31, gridBagConstraints);

        jLabel32.setText("Line State:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        linePanel.add(jLabel32, gridBagConstraints);

        buttonGroup1.add(openRadioButtonLine);
        openRadioButtonLine.setText("Open");
        openRadioButtonLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openRadioButtonLineActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        linePanel.add(openRadioButtonLine, gridBagConstraints);

        buttonGroup1.add(closeRadioButtonLine);
        closeRadioButtonLine.setText("Close");
        closeRadioButtonLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeRadioButtonLineActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        linePanel.add(closeRadioButtonLine, gridBagConstraints);

        jLabel26.setText("[Ω]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        linePanel.add(jLabel26, gridBagConstraints);

        generatorPanel.setAlignmentX(0.0F);
        generatorPanel.setAlignmentY(0.0F);
        generatorPanel.setMaximumSize(new java.awt.Dimension(250, 300));
        generatorPanel.setMinimumSize(new java.awt.Dimension(250, 300));
        generatorPanel.setName(""); // NOI18N
        generatorPanel.setPreferredSize(new java.awt.Dimension(250, 300));
        java.awt.GridBagLayout generatorPanelLayout = new java.awt.GridBagLayout();
        generatorPanelLayout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0};
        generatorPanelLayout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        generatorPanel.setLayout(generatorPanelLayout);

        jLabel12.setText("Generator name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        generatorPanel.add(jLabel12, gridBagConstraints);

        generatorNameLabel.setToolTipText("");
        generatorNameLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        generatorNameLabel.setPreferredSize(new java.awt.Dimension(20, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        generatorPanel.add(generatorNameLabel, gridBagConstraints);

        jLabel27.setText("Generator type:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        generatorPanel.add(jLabel27, gridBagConstraints);

        typeGenComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Solar", "Eolic" }));
        typeGenComboBox.setMaximumSize(new java.awt.Dimension(50, 20));
        typeGenComboBox.setMinimumSize(new java.awt.Dimension(50, 20));
        typeGenComboBox.setName(""); // NOI18N
        typeGenComboBox.setPreferredSize(new java.awt.Dimension(50, 20));
        typeGenComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeGenComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 31;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 14);
        generatorPanel.add(typeGenComboBox, gridBagConstraints);

        jLabel28.setText("MW:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        generatorPanel.add(jLabel28, gridBagConstraints);

        jLabel33.setText("MVar:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        generatorPanel.add(jLabel33, gridBagConstraints);

        MVarGenText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MVarGenTextActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 55;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        generatorPanel.add(MVarGenText, gridBagConstraints);

        jLabel34.setText("Icon");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 9, 0, 9);
        generatorPanel.add(jLabel34, gridBagConstraints);

        jPanel19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel19.setPreferredSize(new java.awt.Dimension(52, 52));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(iconGeneratorLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(iconGeneratorLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridheight = 5;
        generatorPanel.add(jPanel19, gridBagConstraints);

        jPanel20.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel20.setForeground(new java.awt.Color(0, 0, 255));
        jPanel20.setMaximumSize(new java.awt.Dimension(141, 16));
        jPanel20.setMinimumSize(new java.awt.Dimension(141, 16));
        jPanel20.setName(""); // NOI18N
        jPanel20.setPreferredSize(new java.awt.Dimension(141, 16));

        isolatedComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Synchronized", "Isolated" }));
        isolatedComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isolatedComboBoxActionPerformed(evt);
            }
        });

        jLabel6.setText("Operation Mode:");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(isolatedComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(isolatedComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.gridheight = 9;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.ipady = 70;
        generatorPanel.add(jPanel20, gridBagConstraints);

        jButton9.setText("Cancel");
        jButton9.setPreferredSize(new java.awt.Dimension(70, 23));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        generatorPanel.add(jButton9, gridBagConstraints);

        jButton10.setText("OK");
        jButton10.setMaximumSize(new java.awt.Dimension(65, 23));
        jButton10.setMinimumSize(new java.awt.Dimension(65, 23));
        jButton10.setPreferredSize(new java.awt.Dimension(65, 23));
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        generatorPanel.add(jButton10, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.ipadx = 220;
        generatorPanel.add(jSeparator5, gridBagConstraints);

        MWGenText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MWGenTextActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 55;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        generatorPanel.add(MWGenText, gridBagConstraints);

        jLabel36.setText("[MW]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        generatorPanel.add(jLabel36, gridBagConstraints);

        jLabel37.setText("[MVar]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        generatorPanel.add(jLabel37, gridBagConstraints);

        jLabel44.setText("Q min:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        generatorPanel.add(jLabel44, gridBagConstraints);

        jLabel47.setText("Q max:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        generatorPanel.add(jLabel47, gridBagConstraints);

        minMVarGenText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minMVarGenTextActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.ipadx = 55;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        generatorPanel.add(minMVarGenText, gridBagConstraints);

        maxMVarGenText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maxMVarGenTextActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.ipadx = 55;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        generatorPanel.add(maxMVarGenText, gridBagConstraints);

        jLabel48.setText("[Mvar]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        generatorPanel.add(jLabel48, gridBagConstraints);

        jLabel53.setText("[Mvar]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        generatorPanel.add(jLabel53, gridBagConstraints);

        EVPanel.setAlignmentX(0.0F);
        EVPanel.setAlignmentY(0.0F);
        EVPanel.setMaximumSize(new java.awt.Dimension(250, 300));
        EVPanel.setMinimumSize(new java.awt.Dimension(250, 300));
        EVPanel.setName(""); // NOI18N
        EVPanel.setPreferredSize(new java.awt.Dimension(250, 300));
        java.awt.GridBagLayout jPanel7Layout = new java.awt.GridBagLayout();
        jPanel7Layout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        jPanel7Layout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        EVPanel.setLayout(jPanel7Layout);

        jLabel41.setText("Electric  Vehicle name:");
        jLabel41.setAutoscrolls(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        EVPanel.add(jLabel41, gridBagConstraints);

        EVNameLabel.setToolTipText("");
        EVNameLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        EVNameLabel.setPreferredSize(new java.awt.Dimension(20, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 41;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        EVPanel.add(EVNameLabel, gridBagConstraints);

        jLabel42.setText("Electric Vehicle type:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        EVPanel.add(jLabel42, gridBagConstraints);

        jTextField11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField11ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 55;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        EVPanel.add(jTextField11, gridBagConstraints);

        jLabel45.setText("Icon");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 9, 0, 9);
        EVPanel.add(jLabel45, gridBagConstraints);

        jPanel21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel21.setPreferredSize(new java.awt.Dimension(52, 52));

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridheight = 3;
        EVPanel.add(jPanel21, gridBagConstraints);

        jPanel22.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel22.setForeground(new java.awt.Color(0, 0, 255));
        jPanel22.setMaximumSize(new java.awt.Dimension(167, 59));
        jPanel22.setMinimumSize(new java.awt.Dimension(167, 59));
        jPanel22.setName(""); // NOI18N
        jPanel22.setLayout(new java.awt.BorderLayout());

        jLabel46.setText("Reservo esto para futuro");
        jPanel22.add(jLabel46, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 13;
        gridBagConstraints.gridheight = 7;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.ipady = 105;
        EVPanel.add(jPanel22, gridBagConstraints);

        jButton11.setText("Cancel");
        jButton11.setPreferredSize(new java.awt.Dimension(70, 23));
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
        EVPanel.add(jButton11, gridBagConstraints);

        jButton12.setText("OK");
        jButton12.setMaximumSize(new java.awt.Dimension(65, 23));
        jButton12.setMinimumSize(new java.awt.Dimension(65, 23));
        jButton12.setPreferredSize(new java.awt.Dimension(65, 23));
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        EVPanel.add(jButton12, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 13;
        gridBagConstraints.ipadx = 219;
        EVPanel.add(jSeparator6, gridBagConstraints);

        jTextField12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField12ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 55;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        EVPanel.add(jTextField12, gridBagConstraints);

        jLabel49.setText("?????:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        EVPanel.add(jLabel49, gridBagConstraints);

        jLabel50.setText("?????:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        EVPanel.add(jLabel50, gridBagConstraints);

        jLabel51.setText("[??]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        EVPanel.add(jLabel51, gridBagConstraints);

        jLabel52.setText("[??]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        EVPanel.add(jLabel52, gridBagConstraints);

        EVTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Type X", "Type Y", "Type Z" }));
        EVTypeComboBox.setMaximumSize(new java.awt.Dimension(50, 20));
        EVTypeComboBox.setMinimumSize(new java.awt.Dimension(50, 20));
        EVTypeComboBox.setName(""); // NOI18N
        EVTypeComboBox.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 23;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        EVPanel.add(EVTypeComboBox, gridBagConstraints);

        mainContainer.setLayer(batteryPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        mainContainer.setLayer(busPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        mainContainer.setLayer(loadPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        mainContainer.setLayer(faultPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        mainContainer.setLayer(linePanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        mainContainer.setLayer(generatorPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        mainContainer.setLayer(EVPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout mainContainerLayout = new javax.swing.GroupLayout(mainContainer);
        mainContainer.setLayout(mainContainerLayout);
        mainContainerLayout.setHorizontalGroup(
            mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainContainerLayout.createSequentialGroup()
                .addComponent(batteryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 18, Short.MAX_VALUE))
            .addGroup(mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainContainerLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(busPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainContainerLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(loadPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainContainerLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(faultPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainContainerLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(linePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(mainContainerLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(generatorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(mainContainerLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(EVPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        mainContainerLayout.setVerticalGroup(
            mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainContainerLayout.createSequentialGroup()
                .addComponent(batteryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 26, Short.MAX_VALUE))
            .addGroup(mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(mainContainerLayout.createSequentialGroup()
                    .addComponent(busPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(mainContainerLayout.createSequentialGroup()
                    .addComponent(loadPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 11, Short.MAX_VALUE)))
            .addGroup(mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(mainContainerLayout.createSequentialGroup()
                    .addComponent(faultPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 26, Short.MAX_VALUE)))
            .addGroup(mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(mainContainerLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(linePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(34, Short.MAX_VALUE)))
            .addGroup(mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(mainContainerLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(generatorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(mainContainerLayout.createSequentialGroup()
                    .addComponent(EVPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 5, Short.MAX_VALUE)))
        );

        getContentPane().add(mainContainer);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void angleBusTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_angleBusTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_angleBusTextActionPerformed

    private void voltageBusTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_voltageBusTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_voltageBusTextActionPerformed

    private void loadMVarTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadMVarTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_loadMVarTextActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (creando) {
            Controller.getController().borrarNodo(vertex);
        }
        MainInterface.getMainInterface().actualizar();
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        vertex.getVertexAsLoad().setLoadMVar(Double.parseDouble(this.loadMVarText.getText()));
        vertex.getVertexAsLoad().setLoadMW(Double.parseDouble(this.loadMWText.getText()));
        vertex.getVertexAsLoad().setLoadPriority(this.loadPriorityComboBox.getSelectedIndex());
        vertex.getVertexAsLoad().setLoadType(this.loadTypeComboBox.getSelectedIndex());
        if (this.openRadioButtonLine.isSelected()) {
            vertex.getVertexAsLoad().setLoadState(false);
        } else {
            vertex.getVertexAsLoad().setLoadState(true);
        }
        MainInterface.getMainInterface().actualizar();
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void loadMWTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadMWTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_loadMWTextActionPerformed

    private void loadTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadTypeComboBoxActionPerformed
        // TODO add your handling code here:
        //Residencial
        if (this.loadTypeComboBox.getSelectedIndex() == 1) {
            double[] percent = {0.30, 0.26, 0.24, 0.22, 0.20, 0.30, 0.38, 0.50, 0.55, 0.55, 0.58, 0.60, 0.55, 0.50, 0.48, 0.50, 0.70, 1.00, 0.95, 0.90, 0.82, 0.75, 0.60, 0.40};
            vertex.getVertexAsLoad().setPercentOfPeakLoad(percent);
            vertex.getVertexAsLoad().setLoadType(1);
        }
        //Comercial
        if (this.loadTypeComboBox.getSelectedIndex() == 2) {
            double[] percent = {0.20, 0.20, 0.19, 0.18, 0.20, 0.22, 0.25, 0.4, 0.7, 0.85, 0.91, 0.93, 0.89, 0.93, 0.94, 0.95, 1.00, 0.90, 0.75, 0.70, 0.65, 0.55, 0.30, 0.20};
            vertex.getVertexAsLoad().setPercentOfPeakLoad(percent);
            vertex.getVertexAsLoad().setLoadType(2);
        }
        //Industrial
        if (this.loadTypeComboBox.getSelectedIndex() == 3) {
            double[] percent = {0.55, 0.53, 0.51, 0.50, 0.55, 0.59, 0.70, 0.80, 0.92, 1.00, 0.98, 0.95, 0.94, 0.98, 0.90, 0.85, 0.80, 0.73, 0.73, 0.71, 0.70, 0.70, 0.65, 0.60};
            vertex.getVertexAsLoad().setPercentOfPeakLoad(percent);
            vertex.getVertexAsLoad().setLoadType(3);
        }

    }//GEN-LAST:event_loadTypeComboBoxActionPerformed

    private void loadPriorityComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadPriorityComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_loadPriorityComboBoxActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if (creando) {
            Controller.getController().borrarNodo(vertex);
        }
        MainInterface.getMainInterface().actualizar();
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        vertex.getVertexAsFault().getLocation().getEdgeAsSimpleLine().setEstiloLinea(false);
        vertex.getVertexAsFault().getLocation().getEdgeAsSimpleLine().setSwitchStatus(0);
        MainInterface.getMainInterface().actualizar();
        dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void faultLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_faultLocationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_faultLocationActionPerformed

    private void faultTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_faultTypeComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_faultTypeComboBoxActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        if (creando) {
            Controller.getController().borrarNodo(vertex);
        }
        MainInterface.getMainInterface().actualizar();
        this.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed

        line.getEdgeAsSimpleLine().setReactance(Double.parseDouble(this.reactanceText.getText()));
        line.getEdgeAsSimpleLine().setResistance(Double.parseDouble(this.resistanceText.getText()));
        line.getEdgeAsSimpleLine().setMaxCurrent(Double.parseDouble(this.maxCurrentLine.getText()));
        line.getEdgeAsSimpleLine().setReactance(Double.parseDouble(this.reactanceText.getText()));

        if (this.closeRadioButtonLine.isSelected()) {
            line.getEdgeAsSimpleLine().setEstiloLinea(true);
        } else {
            line.getEdgeAsSimpleLine().setEstiloLinea(false);
        }

        dispose();

    }//GEN-LAST:event_jButton8ActionPerformed

    private void resistanceTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resistanceTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_resistanceTextActionPerformed

    private void reactanceTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reactanceTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_reactanceTextActionPerformed

    private void maxCurrentLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maxCurrentLineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maxCurrentLineActionPerformed

    private void openRadioButtonLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openRadioButtonLineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_openRadioButtonLineActionPerformed

    private void closeRadioButtonLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeRadioButtonLineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_closeRadioButtonLineActionPerformed

    private void MVarGenTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MVarGenTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MVarGenTextActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        if (creando) {
            Controller.getController().borrarNodo(vertex);
        }
        MainInterface.getMainInterface().actualizar();
        this.dispose();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        vertex.getVertexAsGenerator().setGeneratorState(true);
        vertex.getVertexAsGenerator().setGeneratorType(this.typeGenComboBox.getSelectedIndex());
        vertex.getVertexAsGenerator().setMWGenerator(Double.parseDouble(this.MWGenText.getText()));
        vertex.getVertexAsGenerator().setMVarGenerator(Double.parseDouble(this.MVarGenText.getText()));
        vertex.getVertexAsGenerator().setMinMVarGenerator(Double.parseDouble(this.minMVarGenText.getText()));
        vertex.getVertexAsGenerator().setMaxMVarGenerator(Double.parseDouble(this.maxMVarGenText.getText()));

        MainInterface.getMainInterface().actualizar();
        this.dispose();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void MWGenTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MWGenTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MWGenTextActionPerformed

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        vertex.getVertexAsElectricVehicle().setElectricVehicleState(true);

        MainInterface.getMainInterface().actualizar();
        this.dispose();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12ActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowActivated

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        if (creando) {
            Controller.getController().borrarNodo(vertex);
        }
        MainInterface.getMainInterface().actualizar();
        this.dispose();
    }//GEN-LAST:event_formWindowClosing

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        if (creando) {
            Controller.getController().borrarNodo(vertex);
        }
        MainInterface.getMainInterface().actualizar();
        this.dispose();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void minMVarGenTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minMVarGenTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_minMVarGenTextActionPerformed

    private void maxMVarGenTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maxMVarGenTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maxMVarGenTextActionPerformed

    private void busTypeComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_busTypeComboBoxItemStateChanged
        // TODO add your handling code here:
        if (busTypeComboBox.getSelectedIndex() == 1) {
            voltageBusText.setText("1.0");
            angleBusText.setText("0.0");
        }
    }//GEN-LAST:event_busTypeComboBoxItemStateChanged

    private void busTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_busTypeComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_busTypeComboBoxActionPerformed

    private void typeGenComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeGenComboBoxActionPerformed
        vertex.getVertexAsGenerator().setGeneratorType(this.typeGenComboBox.getSelectedIndex());
        setIconVertex(vertex);
    }//GEN-LAST:event_typeGenComboBoxActionPerformed

    private void isolatedComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isolatedComboBoxActionPerformed
        if (this.isolatedComboBox.getSelectedIndex() == 1) {
            vertex.getVertexAsGenerator().setIsolateState(true);
        } else {
            vertex.getVertexAsGenerator().setIsolateState(false);
        }
    }//GEN-LAST:event_isolatedComboBoxActionPerformed

    private void typeBatComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeBatComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_typeBatComboBoxActionPerformed

    private void MWhBatteryTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MWhBatteryTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MWhBatteryTextActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        vertex.getVertexAsBattery().setBatteryState(true);
        vertex.getVertexAsBattery().setBatteryType(this.typeBatComboBox.getSelectedIndex());
        vertex.getVertexAsBattery().setBatteryMW(Double.parseDouble(this.MWBatteryText.getText()));
        vertex.getVertexAsBattery().setBatteryMWh(Double.parseDouble(this.MWhBatteryText.getText()));
        vertex.getVertexAsBattery().setEfficiency(Double.parseDouble(this.efficBatteryText.getText()));
        vertex.getVertexAsBattery().setCycles(Integer.parseInt(this.cyclesBatteryText.getText()));

        MainInterface.getMainInterface().actualizar();
        this.dispose();

    }//GEN-LAST:event_jButton14ActionPerformed

    private void MWBatteryTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MWBatteryTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MWBatteryTextActionPerformed

    private void efficBatteryTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_efficBatteryTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_efficBatteryTextActionPerformed

    private void cyclesBatteryTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cyclesBatteryTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cyclesBatteryTextActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel EVNameLabel;
    private javax.swing.JPanel EVPanel;
    private javax.swing.JComboBox EVTypeComboBox;
    private javax.swing.JLabel LineNameLabel;
    private javax.swing.JTextField MVarGenText;
    private javax.swing.JTextField MWBatteryText;
    private javax.swing.JTextField MWGenText;
    private javax.swing.JTextField MWhBatteryText;
    private javax.swing.JTextField angleBusText;
    private javax.swing.JLabel batteryNameLabel;
    private javax.swing.JPanel batteryPanel;
    private javax.swing.JPanel busIconPanel;
    private javax.swing.JLabel busNameLabel;
    private javax.swing.JPanel busPanel;
    private javax.swing.JComboBox busTypeComboBox;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JRadioButton closeRadioButtonLine;
    private javax.swing.JRadioButton closedRadioButton;
    private javax.swing.JTextField cyclesBatteryText;
    private javax.swing.JTextField efficBatteryText;
    private javax.swing.JTextField faultLocation;
    private javax.swing.JLabel faultNameLabel;
    private javax.swing.JPanel faultPanel;
    private javax.swing.JComboBox faultTypeComboBox;
    private javax.swing.JLabel generatorNameLabel;
    private javax.swing.JPanel generatorPanel;
    private javax.swing.JLabel iconBatteryLabel;
    private javax.swing.JLabel iconGeneratorLabel;
    private javax.swing.JLabel iconLabel;
    private javax.swing.JLabel iconLoadLabel;
    private javax.swing.JComboBox isolatedComboBox;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JPanel linePanel;
    private javax.swing.JTextField loadMVarText;
    private javax.swing.JTextField loadMWText;
    private javax.swing.JLabel loadNameLabel;
    private javax.swing.JPanel loadPanel;
    private javax.swing.JComboBox loadPriorityComboBox;
    private javax.swing.JComboBox loadTypeComboBox;
    private javax.swing.JLayeredPane mainContainer;
    private javax.swing.JTextField maxCurrentLine;
    private javax.swing.JTextField maxMVarGenText;
    private javax.swing.JTextField minMVarGenText;
    private javax.swing.JRadioButton openRadioButtonLine;
    private javax.swing.JRadioButton openedRadioButton;
    private javax.swing.JTextField reactanceText;
    private javax.swing.JTextField resistanceText;
    private javax.swing.JComboBox typeBatComboBox;
    private javax.swing.JComboBox typeGenComboBox;
    private javax.swing.JTextField voltageBusText;
    // End of variables declaration//GEN-END:variables
}
