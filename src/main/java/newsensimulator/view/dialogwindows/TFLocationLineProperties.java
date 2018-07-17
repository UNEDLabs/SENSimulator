/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.view.dialogwindows;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import newsensimulator.control.Controller;
import newsensimulator.model.networkelements.Edge;
import newsensimulator.model.networkelements.Vertex;
import newsensimulator.view.MainInterface;

/**
 *
 * @author Jose Muñoz Parra
 */
public class TFLocationLineProperties extends javax.swing.JDialog {

    private final ImageIcon busIn = new ImageIcon(getClass().getResource("/newsensimulator/view/icons/posteMadera2.png"));
    private final ImageIcon busOut = new ImageIcon(getClass().getResource("/newsensimulator/view/icons/posteMadera2Gris.png"));
    private final ImageIcon busSB = new ImageIcon(getClass().getResource("/newsensimulator/view/icons/posteMaderaSlack2.png"));

    private boolean creando = false;

    private Edge edge;

    public TFLocationLineProperties(Edge edge) {
        initComponents();
        
        setLocationRelativeTo(null);
        
        this.setTitle("Properties "+edge.getEdgeAsTFLocationLine().getName());
        
        this.edge = edge;

        this.TFLineNameLabel.setText("  " + edge.getEdgeAsTFLocationLine().getName());

        this.lineNameSchematic.setText(edge.getEdgeAsTFLocationLine().getName());
        
        this.sourceNodeLabel.setText(edge.getEdgeAsTFLocationLine().getSourceVertex().getVertexAsTFLocationNode().getName());
        this.targetNodeLabel.setText(edge.getEdgeAsTFLocationLine().getTargetVertex().getVertexAsTFLocationNode().getName());
        setIconVertex(edge.getEdgeAsTFLocationLine().getSourceVertex(), edge.getEdgeAsTFLocationLine().getTargetVertex());

        this.faultTimeText.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getFaultTime()));
        this.recoveryTimeText.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getRecoveryTime()));
        this.handlingTimeText.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getHandlingTime()));
        this.lineDistanceText.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getElectricalConductorDistanceInKilometers()));

        this.conductorComboBox.addItem("-------------");
        for (int i = 0; i < edge.getEdgeAsTFLocationLine().getConductor().getDefaultConductorList().size(); i++) {
            this.conductorComboBox.addItem(" Default Conductor "+i);
        }
        conductorComboBox.setSelectedIndex(0);
        
        this.resistanceText.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getResistance()));
        this.reactanceText.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getReactance()));
        this.maxCurrentText.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getMaxCurrent()));
        this.fText.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getF()));
        this.nText.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getN()));
        this.rnText.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getRn()));
        this.xnText.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getXn()));
        
        
        this.dist_NA.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getDistanceBetweenConductor("N", "A")));
        this.dist_NB.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getDistanceBetweenConductor("N", "B")));
        this.dist_NC.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getDistanceBetweenConductor("N", "C")));
        
        this.dist_AN.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getDistanceBetweenConductor("A", "N")));
        this.dist_AB.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getDistanceBetweenConductor("A", "B")));
        this.dist_AC.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getDistanceBetweenConductor("A", "C")));
        
        this.dist_BN.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getDistanceBetweenConductor("B", "N")));
        this.dist_BA.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getDistanceBetweenConductor("B", "A")));
        this.dist_BC.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getDistanceBetweenConductor("B", "C")));
        
        this.dist_CN.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getDistanceBetweenConductor("C", "N")));
        this.dist_CA.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getDistanceBetweenConductor("C", "A")));
        this.dist_CB.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getDistanceBetweenConductor("C", "B")));
        
    }

    public TFLocationLineProperties(Edge edge, boolean creando) {
        initComponents();
        
        setLocationRelativeTo(null);

        this.setTitle("Properties "+edge.getEdgeAsTFLocationLine().getName());
        
        this.edge = edge;
        this.creando = creando;

        this.TFLineNameLabel.setText("  " + edge.getEdgeAsTFLocationLine().getName());

        this.lineNameSchematic.setText(edge.getEdgeAsTFLocationLine().getName());
        
        this.sourceNodeLabel.setText(edge.getEdgeAsTFLocationLine().getSourceVertex().getVertexAsTFLocationNode().getName());
        this.targetNodeLabel.setText(edge.getEdgeAsTFLocationLine().getTargetVertex().getVertexAsTFLocationNode().getName());
        setIconVertex(edge.getEdgeAsTFLocationLine().getSourceVertex(), edge.getEdgeAsTFLocationLine().getTargetVertex());

        this.faultTimeText.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getFaultTime()));
        this.recoveryTimeText.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getRecoveryTime()));
        this.handlingTimeText.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getHandlingTime()));
        this.lineDistanceText.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getElectricalConductorDistanceInKilometers()));

        this.conductorComboBox.addItem("-------------");
        for (int i = 0; i < edge.getEdgeAsTFLocationLine().getConductor().getDefaultConductorList().size(); i++) {
            this.conductorComboBox.addItem(" Default Conductor "+i);
        }
        conductorComboBox.setSelectedIndex(0);
        
        this.resistanceText.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getResistance()));
        this.reactanceText.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getReactance()));
        this.maxCurrentText.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getMaxCurrent()));
        this.fText.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getF()));
        this.nText.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getN()));
        this.rnText.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getRn()));
        this.xnText.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getXn()));
        
        
        this.dist_NA.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getDistanceBetweenConductor("N", "A")));
        this.dist_NB.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getDistanceBetweenConductor("N", "B")));
        this.dist_NC.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getDistanceBetweenConductor("N", "C")));
        
        this.dist_AN.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getDistanceBetweenConductor("A", "N")));
        this.dist_AB.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getDistanceBetweenConductor("A", "B")));
        this.dist_AC.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getDistanceBetweenConductor("A", "C")));
        
        this.dist_BN.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getDistanceBetweenConductor("B", "N")));
        this.dist_BA.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getDistanceBetweenConductor("B", "A")));
        this.dist_BC.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getDistanceBetweenConductor("B", "C")));
        
        this.dist_CN.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getDistanceBetweenConductor("C", "N")));
        this.dist_CA.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getDistanceBetweenConductor("C", "A")));
        this.dist_CB.setText(String.valueOf(edge.getEdgeAsTFLocationLine().getConductor().getDistanceBetweenConductor("C", "B")));
        
    }

    private void setIconVertex(Vertex vs, Vertex vt) {
        this.iconSourceLabel.setHorizontalAlignment(JLabel.CENTER);
        this.iconSourceLabel.setVerticalAlignment(JLabel.CENTER);
        this.iconTargetLabel.setHorizontalAlignment(JLabel.CENTER);
        this.iconTargetLabel.setVerticalAlignment(JLabel.CENTER);

        if (vs.getVertexAsTFLocationNode().isTransformerNode()) {
            this.iconSourceLabel.setIcon(busSB);
        } else {
            this.iconSourceLabel.setIcon(busIn);
        }

        if (vt.getVertexAsTFLocationNode().isTransformerNode()) {
            this.iconTargetLabel.setIcon(busSB);
        } else {
            this.iconTargetLabel.setIcon(busIn);
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

        TFLineNameLabel = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        busIconPanel1 = new javax.swing.JPanel();
        iconSourceLabel = new javax.swing.JLabel();
        busIconPanel2 = new javax.swing.JPanel();
        iconTargetLabel = new javax.swing.JLabel();
        sourceNodeLabel = new javax.swing.JLabel();
        targetNodeLabel = new javax.swing.JLabel();
        lineNameSchematic = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel6 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        faultTimeLabel = new javax.swing.JLabel();
        recoveryTimeLabel = new javax.swing.JLabel();
        handlingTimeLabel = new javax.swing.JLabel();
        faultTimeText = new javax.swing.JTextField();
        recoveryTimeText = new javax.swing.JTextField();
        lineDistanceText = new javax.swing.JTextField();
        handlingTimeText = new javax.swing.JTextField();
        handlingTimeLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        conductorComboBox = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        resistanceText = new javax.swing.JTextField();
        reactanceText = new javax.swing.JTextField();
        maxCurrentText = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        fText = new javax.swing.JTextField();
        nText = new javax.swing.JTextField();
        rnText = new javax.swing.JTextField();
        xnText = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        dist_NA = new javax.swing.JTextField();
        dist_NB = new javax.swing.JTextField();
        dist_NC = new javax.swing.JTextField();
        dist_AN = new javax.swing.JTextField();
        dist_BN = new javax.swing.JTextField();
        dist_CN = new javax.swing.JTextField();
        dist_CA = new javax.swing.JTextField();
        dist_CB = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        dist_BC = new javax.swing.JTextField();
        dist_AC = new javax.swing.JTextField();
        dist_AB = new javax.swing.JTextField();
        jTextField21 = new javax.swing.JTextField();
        dist_BA = new javax.swing.JTextField();
        jTextField23 = new javax.swing.JTextField();

        setMaximumSize(new java.awt.Dimension(350, 400));
        setMinimumSize(new java.awt.Dimension(350, 400));
        setPreferredSize(new java.awt.Dimension(350, 400));
        setResizable(false);
        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        layout.rowHeights = new int[] {0, 5, 0, 5, 0};
        getContentPane().setLayout(layout);

        TFLineNameLabel.setToolTipText("");
        TFLineNameLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        TFLineNameLabel.setMaximumSize(new java.awt.Dimension(60, 18));
        TFLineNameLabel.setMinimumSize(new java.awt.Dimension(60, 18));
        TFLineNameLabel.setPreferredSize(new java.awt.Dimension(60, 18));
        TFLineNameLabel.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        getContentPane().add(TFLineNameLabel, gridBagConstraints);

        jLabel10.setText("Line Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        getContentPane().add(jLabel10, gridBagConstraints);

        java.awt.GridBagLayout jPanel7Layout = new java.awt.GridBagLayout();
        jPanel7Layout.columnWidths = new int[] {0, 5, 0};
        jPanel7Layout.rowHeights = new int[] {0};
        jPanel7.setLayout(jPanel7Layout);

        jButton3.setText("OK");
        jButton3.setMaximumSize(new java.awt.Dimension(65, 23));
        jButton3.setMinimumSize(new java.awt.Dimension(65, 23));
        jButton3.setPreferredSize(new java.awt.Dimension(65, 23));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 10);
        jPanel7.add(jButton3, gridBagConstraints);

        jButton4.setText("Cancel");
        jButton4.setPreferredSize(new java.awt.Dimension(70, 23));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel7.add(jButton4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        getContentPane().add(jPanel7, gridBagConstraints);

        jPanel2.setLayout(new java.awt.BorderLayout());

        java.awt.GridBagLayout jPanel1Layout = new java.awt.GridBagLayout();
        jPanel1Layout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        jPanel1Layout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0};
        jPanel1.setLayout(jPanel1Layout);

        busIconPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        busIconPanel1.setMaximumSize(new java.awt.Dimension(65, 65));
        busIconPanel1.setMinimumSize(new java.awt.Dimension(65, 65));
        busIconPanel1.setName(""); // NOI18N
        busIconPanel1.setPreferredSize(new java.awt.Dimension(65, 65));
        busIconPanel1.setLayout(new java.awt.BorderLayout());
        busIconPanel1.add(iconSourceLabel, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 25;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 0);
        jPanel1.add(busIconPanel1, gridBagConstraints);

        busIconPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        busIconPanel2.setMaximumSize(new java.awt.Dimension(65, 65));
        busIconPanel2.setMinimumSize(new java.awt.Dimension(65, 65));
        busIconPanel2.setName(""); // NOI18N
        busIconPanel2.setPreferredSize(new java.awt.Dimension(65, 65));
        busIconPanel2.setLayout(new java.awt.BorderLayout());
        busIconPanel2.add(iconTargetLabel, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 25;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 10, 10);
        jPanel1.add(busIconPanel2, gridBagConstraints);

        sourceNodeLabel.setText("Source Node");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel1.add(sourceNodeLabel, gridBagConstraints);

        targetNodeLabel.setText("Target Node");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 20;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 10);
        jPanel1.add(targetNodeLabel, gridBagConstraints);

        lineNameSchematic.setText("----------");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 25;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 29, 0);
        jPanel1.add(lineNameSchematic, gridBagConstraints);

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 19;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.ipadx = 220;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 9, 0);
        jPanel1.add(jSeparator1, gridBagConstraints);

        jPanel2.add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel6.setLayout(new java.awt.BorderLayout());

        java.awt.GridBagLayout jPanel5Layout = new java.awt.GridBagLayout();
        jPanel5Layout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        jPanel5Layout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0};
        jPanel5.setLayout(jPanel5Layout);

        faultTimeLabel.setText("Fault time:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel5.add(faultTimeLabel, gridBagConstraints);

        recoveryTimeLabel.setText("Recovery time:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel5.add(recoveryTimeLabel, gridBagConstraints);

        handlingTimeLabel.setText("Line Distance:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 10, 0);
        jPanel5.add(handlingTimeLabel, gridBagConstraints);

        faultTimeText.setMaximumSize(new java.awt.Dimension(60, 20));
        faultTimeText.setMinimumSize(new java.awt.Dimension(60, 20));
        faultTimeText.setName(""); // NOI18N
        faultTimeText.setPreferredSize(new java.awt.Dimension(60, 20));
        faultTimeText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                faultTimeTextActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        jPanel5.add(faultTimeText, gridBagConstraints);

        recoveryTimeText.setMaximumSize(new java.awt.Dimension(60, 20));
        recoveryTimeText.setMinimumSize(new java.awt.Dimension(60, 20));
        recoveryTimeText.setName(""); // NOI18N
        recoveryTimeText.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 2;
        jPanel5.add(recoveryTimeText, gridBagConstraints);

        lineDistanceText.setMaximumSize(new java.awt.Dimension(60, 20));
        lineDistanceText.setMinimumSize(new java.awt.Dimension(60, 20));
        lineDistanceText.setName(""); // NOI18N
        lineDistanceText.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel5.add(lineDistanceText, gridBagConstraints);

        handlingTimeText.setMaximumSize(new java.awt.Dimension(60, 20));
        handlingTimeText.setMinimumSize(new java.awt.Dimension(60, 20));
        handlingTimeText.setName(""); // NOI18N
        handlingTimeText.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 4;
        jPanel5.add(handlingTimeText, gridBagConstraints);

        handlingTimeLabel1.setText("Handling time:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
        jPanel5.add(handlingTimeLabel1, gridBagConstraints);

        jLabel2.setText("[hours]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel5.add(jLabel2, gridBagConstraints);

        jLabel3.setText("[hours]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel5.add(jLabel3, gridBagConstraints);

        jLabel4.setText("[hours]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel5.add(jLabel4, gridBagConstraints);

        jLabel5.setText("[km]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel5.add(jLabel5, gridBagConstraints);

        jPanel6.add(jPanel5, java.awt.BorderLayout.WEST);

        jPanel2.add(jPanel6, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Line Parameters", jPanel2);

        java.awt.GridBagLayout jPanel3Layout = new java.awt.GridBagLayout();
        jPanel3Layout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        jPanel3Layout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0};
        jPanel3.setLayout(jPanel3Layout);

        jLabel1.setText("Select conductor:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 50, 0);
        jPanel3.add(jLabel1, gridBagConstraints);

        conductorComboBox.setMaximumSize(new java.awt.Dimension(200, 20));
        conductorComboBox.setMinimumSize(new java.awt.Dimension(200, 20));
        conductorComboBox.setPreferredSize(new java.awt.Dimension(200, 20));
        conductorComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                conductorComboBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 50, 0);
        jPanel3.add(conductorComboBox, gridBagConstraints);

        jLabel6.setText("Resistance:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel3.add(jLabel6, gridBagConstraints);

        jLabel7.setText("Reactance:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel3.add(jLabel7, gridBagConstraints);

        jLabel8.setText("Max. current:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel3.add(jLabel8, gridBagConstraints);

        resistanceText.setMaximumSize(new java.awt.Dimension(60, 20));
        resistanceText.setMinimumSize(new java.awt.Dimension(60, 20));
        resistanceText.setName(""); // NOI18N
        resistanceText.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        jPanel3.add(resistanceText, gridBagConstraints);

        reactanceText.setMaximumSize(new java.awt.Dimension(60, 20));
        reactanceText.setMinimumSize(new java.awt.Dimension(60, 20));
        reactanceText.setName(""); // NOI18N
        reactanceText.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        jPanel3.add(reactanceText, gridBagConstraints);

        maxCurrentText.setMaximumSize(new java.awt.Dimension(60, 20));
        maxCurrentText.setMinimumSize(new java.awt.Dimension(60, 20));
        maxCurrentText.setName(""); // NOI18N
        maxCurrentText.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        jPanel3.add(maxCurrentText, gridBagConstraints);

        jLabel11.setText("N:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel3.add(jLabel11, gridBagConstraints);

        jLabel12.setText("F:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel3.add(jLabel12, gridBagConstraints);

        jLabel13.setText("Rn:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel3.add(jLabel13, gridBagConstraints);

        jLabel14.setText("Xn:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 0);
        jPanel3.add(jLabel14, gridBagConstraints);

        jLabel15.setText("[Ω/km]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel3.add(jLabel15, gridBagConstraints);

        jLabel16.setText("[Ω/km]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel3.add(jLabel16, gridBagConstraints);

        jLabel17.setText("[kA]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel3.add(jLabel17, gridBagConstraints);

        fText.setMaximumSize(new java.awt.Dimension(60, 20));
        fText.setMinimumSize(new java.awt.Dimension(60, 20));
        fText.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel3.add(fText, gridBagConstraints);

        nText.setMaximumSize(new java.awt.Dimension(60, 20));
        nText.setMinimumSize(new java.awt.Dimension(60, 20));
        nText.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel3.add(nText, gridBagConstraints);

        rnText.setMaximumSize(new java.awt.Dimension(60, 20));
        rnText.setMinimumSize(new java.awt.Dimension(60, 20));
        rnText.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel3.add(rnText, gridBagConstraints);

        xnText.setMaximumSize(new java.awt.Dimension(60, 20));
        xnText.setMinimumSize(new java.awt.Dimension(60, 20));
        xnText.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel3.add(xnText, gridBagConstraints);

        jLabel20.setText("[Ω/km]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 10);
        jPanel3.add(jLabel20, gridBagConstraints);

        jLabel21.setText("[Ω/km]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel3.add(jLabel21, gridBagConstraints);

        jLabel22.setText("[Ω/km]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel3.add(jLabel22, gridBagConstraints);

        jLabel23.setText("[Ω/km]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel3.add(jLabel23, gridBagConstraints);

        jTabbedPane1.addTab("Line Conductor", jPanel3);

        java.awt.GridBagLayout jPanel4Layout = new java.awt.GridBagLayout();
        jPanel4Layout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0};
        jPanel4Layout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0};
        jPanel4.setLayout(jPanel4Layout);

        jLabel9.setText("Neutral");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 9, 0, 0);
        jPanel4.add(jLabel9, gridBagConstraints);

        jLabel24.setText("Phase A");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 9, 0, 0);
        jPanel4.add(jLabel24, gridBagConstraints);

        jLabel25.setText("Phase B");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(0, 9, 0, 0);
        jPanel4.add(jLabel25, gridBagConstraints);

        jLabel26.setText("Phase C");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(0, 9, 9, 0);
        jPanel4.add(jLabel26, gridBagConstraints);

        jLabel27.setText("Phase C");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 10);
        jPanel4.add(jLabel27, gridBagConstraints);

        jLabel28.setText("Neutral");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel4.add(jLabel28, gridBagConstraints);

        jLabel29.setText("Phase A");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel4.add(jLabel29, gridBagConstraints);

        jLabel30.setText("Phase B");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel4.add(jLabel30, gridBagConstraints);

        jTextField4.setEditable(false);
        jTextField4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField4.setText("-----");
        jTextField4.setMaximumSize(new java.awt.Dimension(60, 20));
        jTextField4.setMinimumSize(new java.awt.Dimension(60, 20));
        jTextField4.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        jPanel4.add(jTextField4, gridBagConstraints);

        dist_NA.setMaximumSize(new java.awt.Dimension(60, 20));
        dist_NA.setMinimumSize(new java.awt.Dimension(60, 20));
        dist_NA.setPreferredSize(new java.awt.Dimension(60, 20));
        dist_NA.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                dist_NAFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        jPanel4.add(dist_NA, gridBagConstraints);

        dist_NB.setMaximumSize(new java.awt.Dimension(60, 20));
        dist_NB.setMinimumSize(new java.awt.Dimension(60, 20));
        dist_NB.setPreferredSize(new java.awt.Dimension(60, 20));
        dist_NB.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                dist_NBFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        jPanel4.add(dist_NB, gridBagConstraints);

        dist_NC.setMaximumSize(new java.awt.Dimension(60, 20));
        dist_NC.setMinimumSize(new java.awt.Dimension(60, 20));
        dist_NC.setPreferredSize(new java.awt.Dimension(60, 20));
        dist_NC.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                dist_NCFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel4.add(dist_NC, gridBagConstraints);

        dist_AN.setEditable(false);
        dist_AN.setMaximumSize(new java.awt.Dimension(60, 20));
        dist_AN.setMinimumSize(new java.awt.Dimension(60, 20));
        dist_AN.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        jPanel4.add(dist_AN, gridBagConstraints);

        dist_BN.setEditable(false);
        dist_BN.setMaximumSize(new java.awt.Dimension(60, 20));
        dist_BN.setMinimumSize(new java.awt.Dimension(60, 20));
        dist_BN.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        jPanel4.add(dist_BN, gridBagConstraints);

        dist_CN.setEditable(false);
        dist_CN.setMaximumSize(new java.awt.Dimension(60, 20));
        dist_CN.setMinimumSize(new java.awt.Dimension(60, 20));
        dist_CN.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 9, 0);
        jPanel4.add(dist_CN, gridBagConstraints);

        dist_CA.setEditable(false);
        dist_CA.setMaximumSize(new java.awt.Dimension(60, 20));
        dist_CA.setMinimumSize(new java.awt.Dimension(60, 20));
        dist_CA.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 9, 0);
        jPanel4.add(dist_CA, gridBagConstraints);

        dist_CB.setEditable(false);
        dist_CB.setMaximumSize(new java.awt.Dimension(60, 20));
        dist_CB.setMinimumSize(new java.awt.Dimension(60, 20));
        dist_CB.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 9, 0);
        jPanel4.add(dist_CB, gridBagConstraints);

        jTextField17.setEditable(false);
        jTextField17.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField17.setText("-----");
        jTextField17.setMaximumSize(new java.awt.Dimension(60, 20));
        jTextField17.setMinimumSize(new java.awt.Dimension(60, 20));
        jTextField17.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 9, 10);
        jPanel4.add(jTextField17, gridBagConstraints);

        dist_BC.setMaximumSize(new java.awt.Dimension(60, 20));
        dist_BC.setMinimumSize(new java.awt.Dimension(60, 20));
        dist_BC.setPreferredSize(new java.awt.Dimension(60, 20));
        dist_BC.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                dist_BCFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel4.add(dist_BC, gridBagConstraints);

        dist_AC.setMaximumSize(new java.awt.Dimension(60, 20));
        dist_AC.setMinimumSize(new java.awt.Dimension(60, 20));
        dist_AC.setPreferredSize(new java.awt.Dimension(60, 20));
        dist_AC.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                dist_ACFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel4.add(dist_AC, gridBagConstraints);

        dist_AB.setMaximumSize(new java.awt.Dimension(60, 20));
        dist_AB.setMinimumSize(new java.awt.Dimension(60, 20));
        dist_AB.setPreferredSize(new java.awt.Dimension(60, 20));
        dist_AB.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                dist_ABFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        jPanel4.add(dist_AB, gridBagConstraints);

        jTextField21.setEditable(false);
        jTextField21.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField21.setText("-----");
        jTextField21.setMaximumSize(new java.awt.Dimension(60, 20));
        jTextField21.setMinimumSize(new java.awt.Dimension(60, 20));
        jTextField21.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        jPanel4.add(jTextField21, gridBagConstraints);

        dist_BA.setEditable(false);
        dist_BA.setMaximumSize(new java.awt.Dimension(60, 20));
        dist_BA.setMinimumSize(new java.awt.Dimension(60, 20));
        dist_BA.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        jPanel4.add(dist_BA, gridBagConstraints);

        jTextField23.setEditable(false);
        jTextField23.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField23.setText("-----");
        jTextField23.setMaximumSize(new java.awt.Dimension(60, 20));
        jTextField23.setMinimumSize(new java.awt.Dimension(60, 20));
        jTextField23.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        jPanel4.add(jTextField23, gridBagConstraints);

        jTabbedPane1.addTab("Distances Between Conductors", jPanel4);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 17;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jTabbedPane1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        edge.getEdgeAsTFLocationLine().setFaultTime(Double.parseDouble(this.faultTimeText.getText()));
        edge.getEdgeAsTFLocationLine().setHandlingTime(Double.parseDouble(this.handlingTimeText.getText()));
        edge.getEdgeAsTFLocationLine().setRecoveryTime(Double.parseDouble(this.recoveryTimeText.getText()));
        edge.getEdgeAsTFLocationLine().getConductor().setElectricalConductorDistanceInKilometers(Double.parseDouble(this.faultTimeText.getText()));
        
        edge.getEdgeAsTFLocationLine().getConductor().setResistance(Double.parseDouble(this.resistanceText.getText()));
        edge.getEdgeAsTFLocationLine().getConductor().setReactance(Double.parseDouble(this.reactanceText.getText()));
        edge.getEdgeAsTFLocationLine().getConductor().setMaxCurrent(Double.parseDouble(this.maxCurrentText.getText()));
        edge.getEdgeAsTFLocationLine().getConductor().setF(Integer.parseInt(this.fText.getText()));
        edge.getEdgeAsTFLocationLine().getConductor().setN(Integer.parseInt(this.nText.getText()));
        edge.getEdgeAsTFLocationLine().getConductor().setRn(Double.parseDouble(this.rnText.getText()));
        edge.getEdgeAsTFLocationLine().getConductor().setXn(Double.parseDouble(this.xnText.getText()));

        edge.getEdgeAsTFLocationLine().getConductor().setDistanceBetweenConductor("N", "A", Double.parseDouble(this.dist_NA.getText()));
        edge.getEdgeAsTFLocationLine().getConductor().setDistanceBetweenConductor("N", "B", Double.parseDouble(this.dist_NB.getText()));
        edge.getEdgeAsTFLocationLine().getConductor().setDistanceBetweenConductor("N", "C", Double.parseDouble(this.dist_NC.getText()));
        edge.getEdgeAsTFLocationLine().getConductor().setDistanceBetweenConductor("A", "N", Double.parseDouble(this.dist_AN.getText()));
        edge.getEdgeAsTFLocationLine().getConductor().setDistanceBetweenConductor("A", "B", Double.parseDouble(this.dist_AB.getText()));
        edge.getEdgeAsTFLocationLine().getConductor().setDistanceBetweenConductor("A", "C", Double.parseDouble(this.dist_AC.getText()));
        edge.getEdgeAsTFLocationLine().getConductor().setDistanceBetweenConductor("B", "N", Double.parseDouble(this.dist_BN.getText()));
        edge.getEdgeAsTFLocationLine().getConductor().setDistanceBetweenConductor("B", "A", Double.parseDouble(this.dist_BA.getText()));
        edge.getEdgeAsTFLocationLine().getConductor().setDistanceBetweenConductor("B", "C", Double.parseDouble(this.dist_BC.getText()));
        edge.getEdgeAsTFLocationLine().getConductor().setDistanceBetweenConductor("C", "N", Double.parseDouble(this.dist_CN.getText()));
        edge.getEdgeAsTFLocationLine().getConductor().setDistanceBetweenConductor("C", "A", Double.parseDouble(this.dist_CA.getText()));
        edge.getEdgeAsTFLocationLine().getConductor().setDistanceBetweenConductor("C", "B", Double.parseDouble(this.dist_CB.getText()));
        
        
        
        MainInterface.getMainInterface().actualizar();
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if (creando) {
            Controller.getController().borrarLinea(edge);
        }
        MainInterface.getMainInterface().actualizar();
        this.dispose();

    }//GEN-LAST:event_jButton4ActionPerformed

    private void faultTimeTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_faultTimeTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_faultTimeTextActionPerformed

    private void dist_NAFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dist_NAFocusLost
        // TODO add your handling code here:
        this.dist_AN.setText(this.dist_NA.getText());
    }//GEN-LAST:event_dist_NAFocusLost

    private void dist_NBFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dist_NBFocusLost
        // TODO add your handling code here:
        this.dist_BN.setText(this.dist_NB.getText());
    }//GEN-LAST:event_dist_NBFocusLost

    private void dist_NCFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dist_NCFocusLost
        // TODO add your handling code here:
        this.dist_CN.setText(this.dist_NC.getText());
    }//GEN-LAST:event_dist_NCFocusLost

    private void dist_ABFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dist_ABFocusLost
        // TODO add your handling code here:
        this.dist_BA.setText(this.dist_AB.getText());
    }//GEN-LAST:event_dist_ABFocusLost

    private void dist_ACFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dist_ACFocusLost
        // TODO add your handling code here:
        this.dist_CA.setText(this.dist_AC.getText());
    }//GEN-LAST:event_dist_ACFocusLost

    private void dist_BCFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dist_BCFocusLost
        // TODO add your handling code here:
        this.dist_CB.setText(this.dist_BC.getText());
    }//GEN-LAST:event_dist_BCFocusLost

    private void conductorComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_conductorComboBoxItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_conductorComboBoxItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel TFLineNameLabel;
    private javax.swing.JPanel busIconPanel1;
    private javax.swing.JPanel busIconPanel2;
    private javax.swing.JComboBox conductorComboBox;
    private javax.swing.JTextField dist_AB;
    private javax.swing.JTextField dist_AC;
    private javax.swing.JTextField dist_AN;
    private javax.swing.JTextField dist_BA;
    private javax.swing.JTextField dist_BC;
    private javax.swing.JTextField dist_BN;
    private javax.swing.JTextField dist_CA;
    private javax.swing.JTextField dist_CB;
    private javax.swing.JTextField dist_CN;
    private javax.swing.JTextField dist_NA;
    private javax.swing.JTextField dist_NB;
    private javax.swing.JTextField dist_NC;
    private javax.swing.JTextField fText;
    private javax.swing.JLabel faultTimeLabel;
    private javax.swing.JTextField faultTimeText;
    private javax.swing.JLabel handlingTimeLabel;
    private javax.swing.JLabel handlingTimeLabel1;
    private javax.swing.JTextField handlingTimeText;
    private javax.swing.JLabel iconSourceLabel;
    private javax.swing.JLabel iconTargetLabel;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField lineDistanceText;
    private javax.swing.JLabel lineNameSchematic;
    private javax.swing.JTextField maxCurrentText;
    private javax.swing.JTextField nText;
    private javax.swing.JTextField reactanceText;
    private javax.swing.JLabel recoveryTimeLabel;
    private javax.swing.JTextField recoveryTimeText;
    private javax.swing.JTextField resistanceText;
    private javax.swing.JTextField rnText;
    private javax.swing.JLabel sourceNodeLabel;
    private javax.swing.JLabel targetNodeLabel;
    private javax.swing.JTextField xnText;
    // End of variables declaration//GEN-END:variables
}
