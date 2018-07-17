/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.view.dialogwindows;

import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import newsensimulator.control.Controller;
import newsensimulator.view.MainInterface;

/**
 *
 * @author Jose Muñoz Parra
 */
public class DataTable extends javax.swing.JDialog {

    private static DataTable dt;
    private final int[] widthColumnBusTable = {50, 40, 50, 50, 50, 60, 60, 60, 50, 50, 50, 60, 60, 60, 30};
    private final int numNodeIndex = 0;
    private final int typeNodeIndex = 1;
    private final int VAIndex = 2;
    private final int VBIndex = 3;
    private final int VCIndex = 4;
    private final int AngleAIndex = 5;
    private final int AngleBIndex = 6;
    private final int AngleCIndex = 7;
    private final int PAIndex = 8;
    private final int PBIndex = 9;
    private final int PCIndex = 10;
    private final int QAIndex = 11;
    private final int QBIndex = 12;
    private final int QCIndex = 13;
    private final int nCliIndex = 14;

    private final int[] busHiddenColumnIndex = {VBIndex, VCIndex, AngleBIndex, AngleCIndex, PBIndex, PCIndex, QBIndex, QCIndex, nCliIndex};

    private final int[] widthColumnLineTable = {50, 30, 30, 30, 50, 70, 70, 70, 70, 80, 30, 30, 30, 30, 60, 60, 60};

     private final int numLineIndex = 0;
    private final int NSIndex = 1;
    private final int NTIndex = 2;
    private final int rIndex = 3;
    private final int xIndex = 4;
    private final int distIndex = 5;
    private final int rKmIndex = 6;
    private final int xKmIndex = 7;
    private final int iMaxIndex = 8;
    private final int condTypeIndex = 9;
    private final int fIndex = 10;
    private final int nIndex = 11;
    private final int rnIndex = 12;
    private final int xnIndex = 13;
    private final int fTimeIndex = 14;
    private final int rTimeIndex = 15;
    private final int hTimeIndex = 16;

    private final int[] simpleLineHiddenColumnIndex = {distIndex, rKmIndex, xKmIndex, condTypeIndex, fIndex, nIndex, rnIndex, xnIndex, fTimeIndex, rTimeIndex, hTimeIndex};

    private final int[] tfLocationLineHiddenColumnIndex = {rIndex, xIndex,};

    public static DataTable getDataTable() {

        if (dt == null) {
            dt = new DataTable();
        }
        return dt;
    }

    public DataTable() {
        initComponents();
        switch (Controller.getController().getNodeActiveInSimulation()) {
            case "Bus":
                this.setPreferredSize(new Dimension(410, 810) );
                break;
            case "TFLocationNode":
                this.setPreferredSize(new Dimension(870, 810));
                break;
        }
        updateBusInfo();
        updateLineInfo();
        pack();

    }

    private void setDataTarget(String text1, String text2) {
        this.problemText.setText(text1);
        this.methodologyText.setText(text2);
    }

    private void updateBusInfo() {
        setDataTarget(MainInterface.getMainInterface().getProblemName(), MainInterface.getMainInterface().getMethodologyName());

        ArrayList<double[]> data = Controller.getController().getBusDataSystem();
        DefaultTableModel model = (DefaultTableModel) busTable.getModel();

        int n = model.getRowCount();
        if (n > 0) {
            for (int i = 0; i < n; i++) {
                model.removeRow(0);
            }
        }

        for (double[] d : data) {
            Object[] aux = new Object[d.length];
            for (int i = 0; i < d.length; i++) {
                aux[i] = d[i];
            }
            model.addRow(aux);
        }
        busTable.setFillsViewportHeight(true);

        switch (Controller.getController().getNodeActiveInSimulation()) {
            case "Bus":
                for (int i = 0; i < busHiddenColumnIndex.length; i++) {
                    hiddenColumn("Bus", busHiddenColumnIndex[i]);
                }
                busTable.setPreferredSize(new Dimension(810 - 470, 16 * busTable.getRowCount()));
                jScrollPane1.setPreferredSize(new Dimension(810 - 470 + 20, 300));
                jScrollPane1.setMaximumSize(new Dimension(810 - 470 + 20, 300));
                jScrollPane1.setMinimumSize(new Dimension(810 - 470 + 20, 300));
                break;
            case "TFLocationNode":
                restoreAllColumnBusData();
                busTable.setPreferredSize(new Dimension(810, 16 * busTable.getRowCount()));
                jScrollPane1.setPreferredSize(new Dimension(800, 300));
                jScrollPane1.setMaximumSize(new Dimension(800, 300));
                jScrollPane1.setMinimumSize(new Dimension(800, 300));
                break;
        }
    }

    private void updateLineInfo() {
        setDataTarget(MainInterface.getMainInterface().getProblemName(), MainInterface.getMainInterface().getMethodologyName());

        ArrayList<double[]> data = Controller.getController().getLineDataSystem();
        DefaultTableModel model = (DefaultTableModel) lineTable.getModel();

        int n = model.getRowCount();
        if (n > 0) {
            for (int i = 0; i < n; i++) {
                model.removeRow(0);
            }
        }

        for (double[] d : data) {
            Object[] aux = new Object[d.length];
            for (int i = 0; i < d.length; i++) {
                aux[i] = d[i];
            }
            model.addRow(aux);
        }
        lineTable.setFillsViewportHeight(true);

        switch (Controller.getController().getNodeActiveInSimulation()) {
            case "Bus":
                for (int i = 0; i < simpleLineHiddenColumnIndex.length; i++) {
                    hiddenColumn("Line", simpleLineHiddenColumnIndex[i]);
                }
                lineTable.setPreferredSize(new Dimension(810 - 520, 16 * lineTable.getRowCount()));
                jScrollPane2.setPreferredSize(new Dimension(810 - 520 + 20, 300));
                jScrollPane2.setMaximumSize(new Dimension(810 - 520 + 20, 300));
                jScrollPane2.setMinimumSize(new Dimension(810 - 520 + 20, 300));
                break;
            case "TFLocationNode":
                restoreAllColumnLineData();
                for (int i = 0; i < tfLocationLineHiddenColumnIndex.length; i++) {
                    hiddenColumn("Line", tfLocationLineHiddenColumnIndex[i]);
                }
                lineTable.setPreferredSize(new Dimension(810 - 100, 16 * lineTable.getRowCount()));
                jScrollPane2.setPreferredSize(new Dimension(830 - 100, 300));
                jScrollPane2.setMaximumSize(new Dimension(830 - 100, 300));
                jScrollPane2.setMinimumSize(new Dimension(830 - 100, 300));
                break;
        }
    }

    private void hiddenColumn(String typeTable, int column) {
        switch (typeTable) {
            case "Bus":
                busTable.getColumnModel().getColumn(column).setMaxWidth(0);
                busTable.getColumnModel().getColumn(column).setMinWidth(0);
                busTable.getColumnModel().getColumn(column).setPreferredWidth(0);
                break;
            case "Line":
                lineTable.getColumnModel().getColumn(column).setMaxWidth(0);
                lineTable.getColumnModel().getColumn(column).setMinWidth(0);
                lineTable.getColumnModel().getColumn(column).setPreferredWidth(0);
                break;
        }

    }

    private void restoreAllColumnBusData() {
        for (int i = 0; i < widthColumnBusTable.length; i++) {
            setWidthColumn("Bus", i, widthColumnBusTable[i]);
        }
    }
    
    private void restoreAllColumnLineData() {
        for (int i = 0; i < widthColumnLineTable.length; i++) {
            setWidthColumn("Line", i, widthColumnLineTable[i]);
        }
    }

    private void setWidthColumn(String typeTable, int column, int width) {
        switch (typeTable) {
            case "Bus":
                busTable.getColumnModel().getColumn(column).setMaxWidth(width);
                busTable.getColumnModel().getColumn(column).setMinWidth(width);
                busTable.getColumnModel().getColumn(column).setPreferredWidth(width);
                break;
            case "Line":
                lineTable.getColumnModel().getColumn(column).setMaxWidth(width);
                lineTable.getColumnModel().getColumn(column).setMinWidth(width);
                lineTable.getColumnModel().getColumn(column).setPreferredWidth(width);
                break;
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel5 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lineTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        busTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        problemText = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        methodologyText = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Data System Table");
        setLocationByPlatform(true);
        setPreferredSize(new java.awt.Dimension(860, 810));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWidths = new int[] {0, 5, 0, 5, 0};
        layout.rowHeights = new int[] {0, 5, 0, 5, 0};
        getContentPane().setLayout(layout);

        java.awt.GridBagLayout jPanel5Layout = new java.awt.GridBagLayout();
        jPanel5Layout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        jPanel5Layout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        jPanel5.setLayout(jPanel5Layout);

        jButton4.setText("Export Data Line to Text File...");
        jButton4.setMaximumSize(new java.awt.Dimension(185, 23));
        jButton4.setMinimumSize(new java.awt.Dimension(185, 23));
        jButton4.setPreferredSize(new java.awt.Dimension(185, 23));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel5.add(jButton4, gridBagConstraints);

        jButton3.setText("Export Data Bus to Text File...");
        jButton3.setMaximumSize(new java.awt.Dimension(185, 23));
        jButton3.setMinimumSize(new java.awt.Dimension(185, 23));
        jButton3.setPreferredSize(new java.awt.Dimension(185, 23));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel5.add(jButton3, gridBagConstraints);

        jLabel4.setText("Data Line");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 0);
        jPanel5.add(jLabel4, gridBagConstraints);

        jLabel2.setText("Data Bus");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 0);
        jPanel5.add(jLabel2, gridBagConstraints);

        jButton2.setText("Update");
        jButton2.setMaximumSize(new java.awt.Dimension(75, 25));
        jButton2.setMinimumSize(new java.awt.Dimension(75, 25));
        jButton2.setPreferredSize(new java.awt.Dimension(75, 25));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2);

        jButton1.setText("OK");
        jButton1.setMaximumSize(new java.awt.Dimension(75, 25));
        jButton1.setMinimumSize(new java.awt.Dimension(75, 25));
        jButton1.setPreferredSize(new java.awt.Dimension(75, 25));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel5.add(jPanel3, gridBagConstraints);

        jPanel4.setPreferredSize(new java.awt.Dimension(834, 306));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setAlignmentX(0.0F);
        jScrollPane2.setAlignmentY(0.0F);
        jScrollPane2.setAutoscrolls(true);
        jScrollPane2.setMaximumSize(new java.awt.Dimension(830, 300));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(830, 300));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(830, 300));

        lineTable.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lineTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N° Line", "NS", "NT", "r [Ω]", "x [Ω]", "Dist [km]", "r [Ω/km]", "x [Ω/km]", "I max [kA]", "Cond. Type", "F", "N", "Rn", "Xn", "F. Time", "R. Time", "H. Time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        lineTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        lineTable.setMaximumSize(new java.awt.Dimension(810, 270));
        lineTable.setMinimumSize(new java.awt.Dimension(810, 270));
        lineTable.setPreferredSize(new java.awt.Dimension(810, 270));
        lineTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(lineTable);
        if (lineTable.getColumnModel().getColumnCount() > 0) {
            lineTable.getColumnModel().getColumn(0).setResizable(false);
            lineTable.getColumnModel().getColumn(0).setPreferredWidth(50);
            lineTable.getColumnModel().getColumn(1).setResizable(false);
            lineTable.getColumnModel().getColumn(1).setPreferredWidth(30);
            lineTable.getColumnModel().getColumn(2).setResizable(false);
            lineTable.getColumnModel().getColumn(2).setPreferredWidth(30);
            lineTable.getColumnModel().getColumn(3).setResizable(false);
            lineTable.getColumnModel().getColumn(3).setPreferredWidth(50);
            lineTable.getColumnModel().getColumn(4).setResizable(false);
            lineTable.getColumnModel().getColumn(4).setPreferredWidth(50);
            lineTable.getColumnModel().getColumn(5).setResizable(false);
            lineTable.getColumnModel().getColumn(5).setPreferredWidth(70);
            lineTable.getColumnModel().getColumn(6).setResizable(false);
            lineTable.getColumnModel().getColumn(6).setPreferredWidth(70);
            lineTable.getColumnModel().getColumn(7).setResizable(false);
            lineTable.getColumnModel().getColumn(7).setPreferredWidth(70);
            lineTable.getColumnModel().getColumn(8).setResizable(false);
            lineTable.getColumnModel().getColumn(8).setPreferredWidth(70);
            lineTable.getColumnModel().getColumn(9).setResizable(false);
            lineTable.getColumnModel().getColumn(9).setPreferredWidth(80);
            lineTable.getColumnModel().getColumn(10).setResizable(false);
            lineTable.getColumnModel().getColumn(10).setPreferredWidth(30);
            lineTable.getColumnModel().getColumn(11).setResizable(false);
            lineTable.getColumnModel().getColumn(11).setPreferredWidth(30);
            lineTable.getColumnModel().getColumn(12).setResizable(false);
            lineTable.getColumnModel().getColumn(12).setPreferredWidth(30);
            lineTable.getColumnModel().getColumn(13).setResizable(false);
            lineTable.getColumnModel().getColumn(13).setPreferredWidth(30);
            lineTable.getColumnModel().getColumn(14).setResizable(false);
            lineTable.getColumnModel().getColumn(14).setPreferredWidth(60);
            lineTable.getColumnModel().getColumn(15).setResizable(false);
            lineTable.getColumnModel().getColumn(15).setPreferredWidth(60);
            lineTable.getColumnModel().getColumn(16).setResizable(false);
            lineTable.getColumnModel().getColumn(16).setPreferredWidth(60);
        }

        jPanel4.add(jScrollPane2, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 9;
        jPanel5.add(jPanel4, gridBagConstraints);

        jPanel1.setDoubleBuffered(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(834, 306));
        jPanel1.setRequestFocusEnabled(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setAlignmentX(0.0F);
        jScrollPane1.setAlignmentY(0.0F);
        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(830, 300));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(830, 300));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(830, 300));

        busTable.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        busTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N° Node", "Type", "VA [pu]", "VB [pu]", "VC [pu]", "< A [deg]", "< B [deg]", "< C [deg]", "PA [kW]", "PB [kW]", "PC [kW]", "QA [kVA]", "QB [kVA]", "QC [kVA]", "NCli"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        busTable.setAlignmentX(0.0F);
        busTable.setAlignmentY(0.0F);
        busTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        busTable.setDoubleBuffered(true);
        busTable.setMaximumSize(new java.awt.Dimension(810, 270));
        busTable.setMinimumSize(new java.awt.Dimension(810, 270));
        busTable.setPreferredSize(new java.awt.Dimension(810, 270));
        busTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(busTable);
        if (busTable.getColumnModel().getColumnCount() > 0) {
            busTable.getColumnModel().getColumn(0).setResizable(false);
            busTable.getColumnModel().getColumn(0).setPreferredWidth(50);
            busTable.getColumnModel().getColumn(1).setResizable(false);
            busTable.getColumnModel().getColumn(1).setPreferredWidth(40);
            busTable.getColumnModel().getColumn(2).setResizable(false);
            busTable.getColumnModel().getColumn(2).setPreferredWidth(50);
            busTable.getColumnModel().getColumn(3).setResizable(false);
            busTable.getColumnModel().getColumn(3).setPreferredWidth(50);
            busTable.getColumnModel().getColumn(4).setResizable(false);
            busTable.getColumnModel().getColumn(4).setPreferredWidth(50);
            busTable.getColumnModel().getColumn(5).setResizable(false);
            busTable.getColumnModel().getColumn(5).setPreferredWidth(60);
            busTable.getColumnModel().getColumn(6).setResizable(false);
            busTable.getColumnModel().getColumn(6).setPreferredWidth(60);
            busTable.getColumnModel().getColumn(7).setResizable(false);
            busTable.getColumnModel().getColumn(7).setPreferredWidth(60);
            busTable.getColumnModel().getColumn(8).setResizable(false);
            busTable.getColumnModel().getColumn(8).setPreferredWidth(50);
            busTable.getColumnModel().getColumn(9).setResizable(false);
            busTable.getColumnModel().getColumn(9).setPreferredWidth(50);
            busTable.getColumnModel().getColumn(10).setResizable(false);
            busTable.getColumnModel().getColumn(10).setPreferredWidth(50);
            busTable.getColumnModel().getColumn(11).setResizable(false);
            busTable.getColumnModel().getColumn(11).setPreferredWidth(60);
            busTable.getColumnModel().getColumn(12).setResizable(false);
            busTable.getColumnModel().getColumn(12).setPreferredWidth(60);
            busTable.getColumnModel().getColumn(13).setResizable(false);
            busTable.getColumnModel().getColumn(13).setPreferredWidth(60);
            busTable.getColumnModel().getColumn(14).setResizable(false);
            busTable.getColumnModel().getColumn(14).setPreferredWidth(30);
        }

        jPanel1.add(jScrollPane1, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 9;
        jPanel5.add(jPanel1, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Working in: ");
        jPanel2.add(jLabel1);

        problemText.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        problemText.setText("----");
        jPanel2.add(problemText);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("-");
        jPanel2.add(jLabel3);

        methodologyText.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        methodologyText.setText("-----");
        jPanel2.add(methodologyText);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 0);
        jPanel5.add(jPanel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        getContentPane().add(jPanel5, gridBagConstraints);

        jMenu1.setText("File");

        jMenuItem1.setText("Export Data Bus to Text File...");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem3.setText("Export Data Line to Text File...");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem2.setText("Close table");
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
        MainInterface.getMainInterface().setStateCheckBoxDataTable(this.isVisible());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        MainInterface.getMainInterface().setStateCheckBoxDataTable(false);
    }//GEN-LAST:event_formWindowClosing

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        updateBusInfo();
        updateLineInfo();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable busTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable lineTable;
    private javax.swing.JLabel methodologyText;
    private javax.swing.JLabel problemText;
    // End of variables declaration//GEN-END:variables
}
