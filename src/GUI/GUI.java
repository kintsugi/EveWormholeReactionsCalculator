package GUI;
import Calculator.Calculator;
import Database.ProgramDatabase;
import Database.SQLiteTable;
import PriceFetcher.PriceFetcher;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GUI extends javax.swing.JFrame {
    
    private boolean optimizeProfit;
    private boolean optimizeEfficiency;
    private boolean optimizeTime;
    
    public GUI() {
        initComponents();
        setLocationRelativeTo(null);
        BufferedImage iconImage = null;
        try {
            File imageFile = new File("data/icon.png");
            iconImage = ImageIO.read(imageFile);
        } catch(IOException e) {
            e.printStackTrace();
        }
        setIconImage(iconImage);
        ProgramDatabase.load();
        ProgramDatabase db = new ProgramDatabase();
        PriceFetcher.load();
        marginButtonActionPerformed(null);
        ButtonGroup wormholeClassButtonGroup = new ButtonGroup();
        ButtonGroup optimizeForButtonGroup = new ButtonGroup();
        wormholeClassButtonGroup.add(C1Button);
        wormholeClassButtonGroup.add(C3Button);
        wormholeClassButtonGroup.add(C5Button);
        wormholeClassButtonGroup.setSelected(C5Button.getModel(), true);
        optimizeForButtonGroup.add(marginButton);
        optimizeForButtonGroup.add(timeButton);
        optimizeForButtonGroup.add(efficiencyButton);
        optimizeForButtonGroup.setSelected(marginButton.getModel(), true);
        optimizeOutputButton.setLayout(new BorderLayout());
        JLabel optimizebuttonlabel1 = new JLabel("Optimize");
        JLabel optimizebuttonlabel2 = new JLabel(" Output");
        optimizeOutputButton.add(BorderLayout.NORTH, optimizebuttonlabel1);
        optimizeOutputButton.add(BorderLayout.SOUTH, optimizebuttonlabel2);
        
        reactionsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable target = (JTable)e.getSource();
                int row = target.getSelectedRow();
                updateMaterialTable(reactionsTable.getValueAt(row, 0).toString());
            }
        });
        
        outputTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable target = (JTable)e.getSource();
                int row = target.getSelectedRow();
                updateMaterialTable(outputTable.getValueAt(row, 0).toString());
            }
        });
        
        SQLiteTable reactions = db.getReactions();
        ArrayList<String> polymerNames = reactions.getColumn("NAME");
        for(int i = 0; i < polymerNames.size(); i++) {
            reactionsTable.setValueAt(polymerNames.get(i), i, 0);
        }
        calculate();
    }
    
    private void calculate() {
        ProgramDatabase db = new ProgramDatabase();
        Calculator calc = new Calculator();
        for(int i = 0; i < reactionsTable.getRowCount(); i++) {
            Object rowObject = reactionsTable.getValueAt(i, 0);
            String polymerName = rowObject.toString();
            reactionsTable.setValueAt(calc.profitPerHour(polymerName), i, 1);
            reactionsTable.setValueAt(calc.revenuePerHour(polymerName), i, 2);
            reactionsTable.setValueAt(calc.efficiency(polymerName, Double.parseDouble(cycleTimeTextField.getText()), Double.parseDouble(m3PerCycleTextField.getText())), i, 3);
            reactionsTable.setValueAt(calc.time(polymerName, Double.parseDouble(cycleTimeTextField.getText()), Double.parseDouble(m3PerCycleTextField.getText())), i, 4);   
        }
        for(int i = 0; i < outputTable.getRowCount(); i++) {
            Object rowObject = outputTable.getValueAt(i, 0);
            String polymerName = rowObject.toString();
            outputTable.setValueAt(calc.profitPerHour(polymerName) * 720, i, 1);
            outputTable.setValueAt(calc.revenuePerHour(polymerName) * 720, i, 2);
            outputTable.setValueAt(720 / calc.time(polymerName, Double.parseDouble(cycleTimeTextField.getText()), Double.parseDouble(m3PerCycleTextField.getText())), i, 3);
            SQLiteTable reaction = db.getReactions();
            outputTable.setValueAt(Double.parseDouble(reaction.getWhere("VOLUME", "NAME", polymerName)) * Double.parseDouble(reaction.getWhere("BATCH", "NAME", polymerName))  * 720, i, 4);
        }
        
        BigDecimal totalMonthlyRevenue = new BigDecimal(0);
        for(int i = 0; i < outputTable.getRowCount(); i++) {
            Object rowObject = outputTable.getValueAt(i, 0);
            String polymerName = rowObject.toString();
            BigDecimal monthlyRevenue = new BigDecimal(Double.toString(calc.revenuePerHour(polymerName)));
            monthlyRevenue = monthlyRevenue.multiply(new BigDecimal("720"));
            totalMonthlyRevenue = totalMonthlyRevenue.add(monthlyRevenue);
        }
        BigInteger totalMonthlyRevenueBI = totalMonthlyRevenue.toBigInteger();
        totalMonthlyRevenueTextField.setText(NumberFormat.getIntegerInstance().format(totalMonthlyRevenueBI) + " ISK");
        
        BigDecimal totalMonthlyProfit = new BigDecimal("0");
        for(int i = 0; i < outputTable.getRowCount(); i++) {
            Object rowObject = outputTable.getValueAt(i, 0);
            String polymerName = rowObject.toString();
            BigDecimal monthlyProfit = new BigDecimal(Double.toString(calc.profitPerHour(polymerName)));
            monthlyProfit = monthlyProfit.multiply(new BigDecimal("720"));
            totalMonthlyProfit = totalMonthlyProfit.add(monthlyProfit);
        }
        BigInteger totalMonthlyProfitBI = totalMonthlyProfit.toBigInteger();
        totalMonthlyProfitTextField.setText(NumberFormat.getIntegerInstance().format(totalMonthlyProfitBI) + " ISK");
        
        BigDecimal totalEfficiency = new BigDecimal("0");
        for(int i = 0; i < outputTable.getRowCount(); i++) {
            Object rowObject = outputTable.getValueAt(i, 0);
            String polymerName = rowObject.toString();
            BigDecimal efficiency = new BigDecimal(Double.toString(calc.efficiency(polymerName, Double.parseDouble(cycleTimeTextField.getText()), Double.parseDouble(m3PerCycleTextField.getText()))));
            totalEfficiency = totalEfficiency.add(efficiency);
        }
        int rowCount = outputTable.getRowCount();
        if(rowCount > 1)
            totalEfficiency = totalEfficiency.divide(new BigDecimal(rowCount), 2, RoundingMode.HALF_UP);        
        BigInteger totalEfficiencyBI = totalEfficiency.toBigInteger();
        averageEfficiencyTextField.setText(NumberFormat.getIntegerInstance().format(totalEfficiencyBI));
       
        double totalminingTime = 0;
        for(int i = 0; i < outputTable.getRowCount(); i++) {
            Object rowObject = outputTable.getValueAt(i, 0);
            String polymerName = rowObject.toString();
            double miningTime = 720 / calc.time(polymerName, Double.parseDouble(cycleTimeTextField.getText()), Double.parseDouble(m3PerCycleTextField.getText()));
            totalminingTime += miningTime;
        }
        monthlyMiningTimeTextField.setText(Double.toString(totalminingTime));
        
        double monthlyInputVolume = 0;
        
        for(int i = 0; i < outputTable.getRowCount(); i++) {
            Object rowObject = outputTable.getValueAt(i, 0);
            String polymerName = rowObject.toString();
            SQLiteTable recipe = db.getRecipe(polymerName);
            ArrayList<String>quantities = recipe.getColumn("QUANTITY");
            ArrayList<String>volumes = recipe.getColumn("VOLUME");
            for(int j = 0; j < quantities.size(); j++) {
                monthlyInputVolume += (Double.parseDouble(quantities.get(j)) * Double.parseDouble(volumes.get(j))) * 720;
            }
        }
        monthlyInputVolumeTextField.setText(NumberFormat.getIntegerInstance().format(monthlyInputVolume) + " m³");
        
        double monthlyOutputVolume = 0;
        for(int i = 0; i < outputTable.getRowCount(); i++) {
            Object rowObject = outputTable.getValueAt(i, 0);
            String polymerName = rowObject.toString();
            SQLiteTable reaction = db.getReactions();
            monthlyOutputVolume += Double.parseDouble(reaction.getWhere("VOLUME", "NAME", polymerName)) * Double.parseDouble(reaction.getWhere("BATCH", "NAME", polymerName))  * 720;
        }
        monthlyOutputVolumeTextField.setText(NumberFormat.getIntegerInstance().format(monthlyOutputVolume) + " m³");
    }
    
    private void updateMaterialTable(String polymerName) {
        ProgramDatabase db = new ProgramDatabase();
        SQLiteTable recipe = db.getRecipe(polymerName);
        ArrayList<String> names = recipe.getColumn("NAME");
        ArrayList<String> quantities = recipe.getColumn("QUANTITY");
        ArrayList<String> volumes = recipe.getColumn("VOLUME");
        for(int i = 0; i < names.size(); i++) {
            materialsTable.setValueAt(names.get(i), i, 0);
            materialsTable.setValueAt(Integer.parseInt(quantities.get(i)) * 720, i, 1);
            materialsTable.setValueAt(Integer.parseInt(quantities.get(i)) * Double.parseDouble(volumes.get(i)) * 720, i, 2);
            if(i < 2) {
                double volumePerHour = (3600/Double.parseDouble(cycleTimeTextField.getText())) * (Double.parseDouble(m3PerCycleTextField.getText()));
                double gasPerHour = volumePerHour / Double.parseDouble(recipe.getWhere("VOLUME", "NAME", names.get(i)));
                materialsTable.setValueAt((Integer.parseInt(quantities.get(i)) * 720) / gasPerHour, i, 3);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        wormholeClassPanel = new javax.swing.JPanel();
        wormholeClassLabel = new javax.swing.JLabel();
        C1Button = new javax.swing.JRadioButton();
        C3Button = new javax.swing.JRadioButton();
        C5Button = new javax.swing.JRadioButton();
        tablePanel = new javax.swing.JPanel();
        reactionsLabel = new javax.swing.JLabel();
        reactionsScrollPane = new javax.swing.JScrollPane();
        reactionsTable = new javax.swing.JTable();
        setupPanel = new javax.swing.JPanel();
        cycleTimeLabel = new javax.swing.JLabel();
        cycleTimeTextField = new javax.swing.JTextField();
        m3PerCycleLabel = new javax.swing.JLabel();
        m3PerCycleTextField = new javax.swing.JTextField();
        wipLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        outputPanel = new javax.swing.JPanel();
        outputLabel = new javax.swing.JLabel();
        outputPane = new javax.swing.JScrollPane();
        outputTable = new javax.swing.JTable();
        removeReactionButton = new javax.swing.JButton();
        addReactionButton = new javax.swing.JButton();
        materialsScrollPane = new javax.swing.JScrollPane();
        materialsTable = new javax.swing.JTable();
        totalMonthlyRevenueLabel = new javax.swing.JLabel();
        totalMonthlyRevenueTextField = new javax.swing.JTextField();
        monthlyInputVolumeLabel = new javax.swing.JLabel();
        monthlyInputVolumeTextField = new javax.swing.JTextField();
        monthlyOutputVolumeLabel = new javax.swing.JLabel();
        monthlyOutputVolumeTextField = new javax.swing.JTextField();
        averageEfficiencyLabel = new javax.swing.JLabel();
        averageEfficiencyTextField = new javax.swing.JTextField();
        monthlyMiningTimeLabel = new javax.swing.JLabel();
        monthlyMiningTimeTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        totalMonthlyProfitTextField = new javax.swing.JTextField();
        totalMonthlyProfitLabel = new javax.swing.JLabel();
        setupLabel = new javax.swing.JLabel();
        aboutButton = new javax.swing.JButton();
        optimizeForPanel = new javax.swing.JPanel();
        optimizeForLabel = new javax.swing.JLabel();
        marginButton = new javax.swing.JRadioButton();
        timeButton = new javax.swing.JRadioButton();
        efficiencyButton = new javax.swing.JRadioButton();
        reactorsPanel = new javax.swing.JPanel();
        reactorsLabel = new javax.swing.JLabel();
        reactorsTextField = new javax.swing.JFormattedTextField();
        optimizeOutputButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Eve Wormhole Reactions Calculator");
        setAlwaysOnTop(true);
        setLocationByPlatform(true);
        setName("mainFrame"); // NOI18N
        setResizable(false);

        wormholeClassPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        wormholeClassPanel.setPreferredSize(new java.awt.Dimension(100, 177));

        wormholeClassLabel.setText("Wormhole Class");

        C1Button.setText("C1 - C2");
        C1Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C1ButtonActionPerformed(evt);
            }
        });

        C3Button.setText("C3 - C4");
        C3Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C3ButtonActionPerformed(evt);
            }
        });

        C5Button.setText("C5-C6");
        C5Button.setToolTipText("");
        C5Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C5ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout wormholeClassPanelLayout = new javax.swing.GroupLayout(wormholeClassPanel);
        wormholeClassPanel.setLayout(wormholeClassPanelLayout);
        wormholeClassPanelLayout.setHorizontalGroup(
            wormholeClassPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wormholeClassPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(wormholeClassPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(C3Button)
                    .addComponent(C1Button)
                    .addComponent(C5Button)
                    .addComponent(wormholeClassLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        wormholeClassPanelLayout.setVerticalGroup(
            wormholeClassPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wormholeClassPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(wormholeClassLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(C1Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(C3Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(C5Button)
                .addContainerGap())
        );

        reactionsLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        reactionsLabel.setText("Reactions");

        reactionsTable.setAutoCreateRowSorter(true);
        reactionsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Polymer Name", "Profit/Hour", "Revenue/Hour", " Efficiency", "Time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        reactionsTable.setToolTipText("");
        reactionsTable.setSurrendersFocusOnKeystroke(true);
        reactionsScrollPane.setViewportView(reactionsTable);

        setupPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        cycleTimeLabel.setText("Cycle Time (s)");

        cycleTimeTextField.setText("30");
        cycleTimeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cycleTimeTextFieldActionPerformed(evt);
            }
        });

        m3PerCycleLabel.setText("m3 Per Cycle");

        m3PerCycleTextField.setText("80");
        m3PerCycleTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m3PerCycleTextFieldActionPerformed(evt);
            }
        });

        wipLabel.setText("To be developed. Use a tool such as EVE Isk Per Hour to calculate");
        wipLabel.setToolTipText("");

        jLabel1.setText("these values.");

        javax.swing.GroupLayout setupPanelLayout = new javax.swing.GroupLayout(setupPanel);
        setupPanel.setLayout(setupPanelLayout);
        setupPanelLayout.setHorizontalGroup(
            setupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(setupPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(setupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(wipLabel)
                    .addComponent(jLabel1)
                    .addGroup(setupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cycleTimeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cycleTimeTextField)
                        .addComponent(m3PerCycleLabel)
                        .addComponent(m3PerCycleTextField)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        setupPanelLayout.setVerticalGroup(
            setupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(setupPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cycleTimeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cycleTimeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m3PerCycleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m3PerCycleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wipLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        outputLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        outputLabel.setText("Output");

        outputTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Polymer Name", "Profit/Month", "Revenue/Month", "Total Time(Hr)", "Volume/Month"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        outputPane.setViewportView(outputTable);

        removeReactionButton.setText("Remove Reaction");
        removeReactionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeReactionButtonActionPerformed(evt);
            }
        });

        addReactionButton.setText("Add Reaction");
        addReactionButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        addReactionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addReactionButtonActionPerformed(evt);
            }
        });

        materialsTable.setAutoCreateRowSorter(true);
        materialsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Material Name", "Quantity/Month", "Volume/Month", "Mining Hr/Mon."
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        materialsTable.setFocusCycleRoot(true);
        materialsTable.setRowSelectionAllowed(false);
        materialsScrollPane.setViewportView(materialsTable);

        totalMonthlyRevenueLabel.setText("Total Monthly Revenue");

        totalMonthlyRevenueTextField.setEditable(false);
        totalMonthlyRevenueTextField.setBackground(new java.awt.Color(255, 255, 255));
        totalMonthlyRevenueTextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        monthlyInputVolumeLabel.setText("Monthly Input Volume");

        monthlyInputVolumeTextField.setEditable(false);
        monthlyInputVolumeTextField.setBackground(new java.awt.Color(255, 255, 255));
        monthlyInputVolumeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthlyInputVolumeTextFieldActionPerformed(evt);
            }
        });

        monthlyOutputVolumeLabel.setText("Monthly Output Volume");

        monthlyOutputVolumeTextField.setEditable(false);
        monthlyOutputVolumeTextField.setBackground(new java.awt.Color(255, 255, 255));

        averageEfficiencyLabel.setText("Average Efficiency");

        averageEfficiencyTextField.setEditable(false);
        averageEfficiencyTextField.setBackground(new java.awt.Color(255, 255, 255));
        averageEfficiencyTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                averageEfficiencyTextFieldActionPerformed(evt);
            }
        });

        monthlyMiningTimeLabel.setText("Monthly Mining Time(Hr)");

        monthlyMiningTimeTextField.setEditable(false);
        monthlyMiningTimeTextField.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Mining time for minerals is omitted due to its negligibility.");

        totalMonthlyProfitTextField.setEditable(false);
        totalMonthlyProfitTextField.setBackground(new java.awt.Color(255, 255, 255));
        totalMonthlyProfitTextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        totalMonthlyProfitLabel.setText("Total Monthly Profit");

        javax.swing.GroupLayout outputPanelLayout = new javax.swing.GroupLayout(outputPanel);
        outputPanel.setLayout(outputPanelLayout);
        outputPanelLayout.setHorizontalGroup(
            outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputPanelLayout.createSequentialGroup()
                .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(outputPanelLayout.createSequentialGroup()
                        .addComponent(outputLabel)
                        .addGap(260, 260, 260)
                        .addComponent(addReactionButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeReactionButton))
                    .addComponent(outputPane, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(materialsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(outputPanelLayout.createSequentialGroup()
                        .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(outputPanelLayout.createSequentialGroup()
                                .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(totalMonthlyRevenueTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(totalMonthlyRevenueLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(totalMonthlyProfitTextField)
                            .addComponent(totalMonthlyProfitLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(outputPanelLayout.createSequentialGroup()
                                .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(monthlyOutputVolumeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(monthlyOutputVolumeTextField))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(monthlyMiningTimeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(monthlyMiningTimeTextField)))
                            .addGroup(outputPanelLayout.createSequentialGroup()
                                .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(outputPanelLayout.createSequentialGroup()
                                        .addComponent(monthlyInputVolumeLabel)
                                        .addGap(14, 14, 14))
                                    .addGroup(outputPanelLayout.createSequentialGroup()
                                        .addComponent(monthlyInputVolumeTextField)
                                        .addGap(6, 6, 6)))
                                .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(averageEfficiencyLabel)
                                    .addComponent(averageEfficiencyTextField)))))
                    .addComponent(jLabel2))
                .addContainerGap())
        );
        outputPanelLayout.setVerticalGroup(
            outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputPanelLayout.createSequentialGroup()
                .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(addReactionButton)
                        .addComponent(removeReactionButton)
                        .addComponent(outputLabel))
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(outputPanelLayout.createSequentialGroup()
                        .addComponent(materialsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(monthlyInputVolumeLabel)
                            .addComponent(averageEfficiencyLabel)
                            .addComponent(totalMonthlyProfitLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(monthlyInputVolumeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(averageEfficiencyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(totalMonthlyProfitTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(monthlyOutputVolumeLabel)
                            .addComponent(monthlyMiningTimeLabel)
                            .addComponent(totalMonthlyRevenueLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(monthlyOutputVolumeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(monthlyMiningTimeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(totalMonthlyRevenueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(outputPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        setupLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        setupLabel.setText("Setup");

        javax.swing.GroupLayout tablePanelLayout = new javax.swing.GroupLayout(tablePanel);
        tablePanel.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablePanelLayout.createSequentialGroup()
                .addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tablePanelLayout.createSequentialGroup()
                        .addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tablePanelLayout.createSequentialGroup()
                                .addComponent(reactionsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(470, 470, 470)
                                .addComponent(setupLabel))
                            .addComponent(outputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(tablePanelLayout.createSequentialGroup()
                        .addComponent(reactionsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(setupPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablePanelLayout.createSequentialGroup()
                .addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reactionsLabel)
                    .addComponent(setupLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(reactionsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(setupPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        aboutButton.setText("About");
        aboutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutButtonActionPerformed(evt);
            }
        });

        optimizeForPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        optimizeForLabel.setText("Optimize For");

        marginButton.setText("Profit");
        marginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                marginButtonActionPerformed(evt);
            }
        });

        timeButton.setText("Time");
        timeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeButtonActionPerformed(evt);
            }
        });

        efficiencyButton.setText("Efficiency");
        efficiencyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                efficiencyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout optimizeForPanelLayout = new javax.swing.GroupLayout(optimizeForPanel);
        optimizeForPanel.setLayout(optimizeForPanelLayout);
        optimizeForPanelLayout.setHorizontalGroup(
            optimizeForPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optimizeForPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(optimizeForPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(timeButton)
                    .addComponent(optimizeForLabel)
                    .addComponent(marginButton)
                    .addComponent(efficiencyButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        optimizeForPanelLayout.setVerticalGroup(
            optimizeForPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optimizeForPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(optimizeForLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(marginButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timeButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(efficiencyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        reactorsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        reactorsLabel.setText("# Reactors");

        reactorsTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        reactorsTextField.setText("0");

        javax.swing.GroupLayout reactorsPanelLayout = new javax.swing.GroupLayout(reactorsPanel);
        reactorsPanel.setLayout(reactorsPanelLayout);
        reactorsPanelLayout.setHorizontalGroup(
            reactorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reactorsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(reactorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(reactorsPanelLayout.createSequentialGroup()
                        .addComponent(reactorsLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(reactorsTextField))
                .addContainerGap())
        );
        reactorsPanelLayout.setVerticalGroup(
            reactorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reactorsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(reactorsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reactorsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        optimizeOutputButton.setPreferredSize(new java.awt.Dimension(73, 9));
        optimizeOutputButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optimizeOutputButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Note: Resets Output");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(optimizeOutputButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(wormholeClassPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(optimizeForPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(reactorsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(aboutButton)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(tablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(aboutButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wormholeClassPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(optimizeForPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reactorsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(optimizeOutputButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel3)
                .addGap(44, 44, 44))
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(tablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addReactionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addReactionButtonActionPerformed
        int[] selectedRowsArray = reactionsTable.getSelectedRows();
        ArrayList<Integer> selectedRows = new ArrayList(selectedRowsArray.length);
        for(int i = 0; i < selectedRowsArray.length; i++)
            selectedRows.add(selectedRowsArray[i]);
        for(Integer i : selectedRows) {
            ((DefaultTableModel)outputTable.getModel()).addRow(new Object[] {reactionsTable.getValueAt(i, 0), null, null, null, null, null});
        }
        calculate();
    }//GEN-LAST:event_addReactionButtonActionPerformed

    private void efficiencyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_efficiencyButtonActionPerformed
        optimizeProfit = false;
        optimizeEfficiency = true;
        optimizeTime = false;
    }//GEN-LAST:event_efficiencyButtonActionPerformed

    private void timeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeButtonActionPerformed
        optimizeProfit = false;
        optimizeEfficiency = false;
        optimizeTime = true;
    }//GEN-LAST:event_timeButtonActionPerformed

    private void marginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_marginButtonActionPerformed
        optimizeProfit = true;
        optimizeEfficiency = false;
        optimizeTime = false;
    }//GEN-LAST:event_marginButtonActionPerformed

    private void averageEfficiencyTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_averageEfficiencyTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_averageEfficiencyTextFieldActionPerformed

    private void monthlyInputVolumeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthlyInputVolumeTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_monthlyInputVolumeTextFieldActionPerformed

    private void m3PerCycleTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m3PerCycleTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_m3PerCycleTextFieldActionPerformed

    private void cycleTimeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cycleTimeTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cycleTimeTextFieldActionPerformed

    private void C1ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C1ButtonActionPerformed
        ProgramDatabase db = new ProgramDatabase();
        SQLiteTable reactions = db.getReactions();
        for(int i = 0; i < reactionsTable.getRowCount(); i++) {
            ((DefaultTableModel)reactionsTable.getModel()).removeRow(i); i--;
        }
        ArrayList<String> polymerNames = reactions.getColumn("NAME");
        for(int i = 0; i < polymerNames.size(); i++) {
            ((DefaultTableModel)reactionsTable.getModel()).addRow( new Object[] {polymerNames.get(i), null, null, null, null});
        }
        for(int i = 0; i < reactionsTable.getRowCount(); i++) {
            String rowName = (String) reactionsTable.getValueAt(i, 0);
            if(rowName.equalsIgnoreCase("C3-FTM Acid") || rowName.equalsIgnoreCase("Carbon-86 Epoxy Resin") || rowName.equalsIgnoreCase("Scandium Metallofullerene") ||rowName.equalsIgnoreCase("Graphene Nanoribbons")) {
               ((DefaultTableModel)reactionsTable.getModel()).removeRow(i); i--;
            }
        }
        calculate();
    }//GEN-LAST:event_C1ButtonActionPerformed

    private void C3ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C3ButtonActionPerformed
        ProgramDatabase db = new ProgramDatabase();
        SQLiteTable reactions = db.getReactions();
        for(int i = 0; i < reactionsTable.getRowCount(); i++) {
            ((DefaultTableModel)reactionsTable.getModel()).removeRow(i); i--;
        }
        ArrayList<String> polymerNames = reactions.getColumn("NAME");
        for(int i = 0; i < polymerNames.size(); i++) {
            ((DefaultTableModel)reactionsTable.getModel()).addRow( new Object[] {polymerNames.get(i), null, null, null, null});
        }
        for(int i = 0; i < reactionsTable.getRowCount(); i++) {
            String rowName = (String) reactionsTable.getValueAt(i, 0);
            if(rowName.equalsIgnoreCase("C3-FTM Acid") || rowName.equalsIgnoreCase("Carbon-86 Epoxy Resin")) {
               ((DefaultTableModel)reactionsTable.getModel()).removeRow(i); i--;
            }
        }
        calculate();
    }//GEN-LAST:event_C3ButtonActionPerformed

    private void C5ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_C5ButtonActionPerformed
        ProgramDatabase db = new ProgramDatabase();
        SQLiteTable reactions = db.getReactions();
        for(int i = 0; i < reactionsTable.getRowCount(); i++) {
            ((DefaultTableModel)reactionsTable.getModel()).removeRow(i); i--;
        }
        ArrayList<String> polymerNames = reactions.getColumn("NAME");
        for(int i = 0; i < polymerNames.size(); i++) {
            ((DefaultTableModel)reactionsTable.getModel()).addRow( new Object[] {polymerNames.get(i), null, null, null, null});
        }
        calculate();
    }//GEN-LAST:event_C5ButtonActionPerformed

    private void removeReactionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeReactionButtonActionPerformed
        int[] selectedRowsArray = outputTable.getSelectedRows();
        ArrayList<Integer> selectedRows = new ArrayList<>(selectedRowsArray.length);
        for(int i = 0; i < selectedRowsArray.length; i++)
            selectedRows.add(selectedRowsArray[i]);

        for (Integer selectedRow : selectedRows) {
            for (int j = 0; j < outputTable.getRowCount(); j++) {
                if (j == selectedRow) {
                    ((DefaultTableModel)outputTable.getModel()).removeRow(selectedRow);
                    j--;
                }
            }
        }
        calculate();
    }//GEN-LAST:event_removeReactionButtonActionPerformed

    private void optimizeOutputButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optimizeOutputButtonActionPerformed
        for(int i = 0; i < outputTable.getRowCount(); i++ ) {
            ((DefaultTableModel)outputTable.getModel()).removeRow(0); i--;
        }
        if(optimizeProfit) {
            double bestProfit = 0; int bestProfitRow = 0;
            for(int i = 0; i < reactionsTable.getRowCount(); i++) {
                if(bestProfit < (double)reactionsTable.getValueAt(i, 1)) {
                    bestProfit = (double)reactionsTable.getValueAt(i, 1);
                    bestProfitRow = i;
                }
            }
            for(int i = 0; i < Integer.parseInt(reactorsTextField.getText()); i++) {
                ((DefaultTableModel)outputTable.getModel()).addRow( new Object[] {reactionsTable.getValueAt(bestProfitRow, 0)});
            }
            calculate();
        } else if(optimizeTime) {
            double bestTime = 0; int bestTimeRow = 0;
            for(int i = 0; i < reactionsTable.getRowCount(); i++) {
                if(bestTime < (double)reactionsTable.getValueAt(i, 4)) {
                    bestTime = (double)reactionsTable.getValueAt(i, 4);
                    bestTimeRow = i;
                } else if(bestTime == (double)reactionsTable.getValueAt(i, 4)) {
                    if((double)reactionsTable.getValueAt(bestTimeRow, 4) < (double)reactionsTable.getValueAt(i, 4)) {
                        bestTime = (double)reactionsTable.getValueAt(i, 4);
                        bestTimeRow = i;
                    }
                }
            }
            for(int i = 0; i < Integer.parseInt(reactorsTextField.getText()); i++) {
                ((DefaultTableModel)outputTable.getModel()).addRow( new Object[] {reactionsTable.getValueAt(bestTimeRow, 0)});
            }
            calculate();
        }else if(optimizeEfficiency) {
            double bestEfficiency = 0; int bestEfficiencyRow = 0;
            for(int i = 0; i < reactionsTable.getRowCount(); i++) {
                if(bestEfficiency < (double)reactionsTable.getValueAt(i, 3)) {
                    bestEfficiency = (double)reactionsTable.getValueAt(i, 3);
                    bestEfficiencyRow = i;
                }
            }
            for(int i = 0; i < Integer.parseInt(reactorsTextField.getText()); i++) {
                ((DefaultTableModel)outputTable.getModel()).addRow( new Object[] {reactionsTable.getValueAt(bestEfficiencyRow, 0)});
            }
            calculate();
        }
    }//GEN-LAST:event_optimizeOutputButtonActionPerformed

    private void aboutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutButtonActionPerformed
    About.main(null);
    }//GEN-LAST:event_aboutButtonActionPerformed

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton C1Button;
    private javax.swing.JRadioButton C3Button;
    private javax.swing.JRadioButton C5Button;
    private javax.swing.JButton aboutButton;
    private javax.swing.JButton addReactionButton;
    private javax.swing.JLabel averageEfficiencyLabel;
    private javax.swing.JTextField averageEfficiencyTextField;
    private javax.swing.JLabel cycleTimeLabel;
    private javax.swing.JTextField cycleTimeTextField;
    private javax.swing.JRadioButton efficiencyButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel m3PerCycleLabel;
    private javax.swing.JTextField m3PerCycleTextField;
    private javax.swing.JRadioButton marginButton;
    private javax.swing.JScrollPane materialsScrollPane;
    private javax.swing.JTable materialsTable;
    private javax.swing.JLabel monthlyInputVolumeLabel;
    private javax.swing.JTextField monthlyInputVolumeTextField;
    private javax.swing.JLabel monthlyMiningTimeLabel;
    private javax.swing.JTextField monthlyMiningTimeTextField;
    private javax.swing.JLabel monthlyOutputVolumeLabel;
    private javax.swing.JTextField monthlyOutputVolumeTextField;
    private javax.swing.JLabel optimizeForLabel;
    private javax.swing.JPanel optimizeForPanel;
    private javax.swing.JButton optimizeOutputButton;
    private javax.swing.JLabel outputLabel;
    private javax.swing.JScrollPane outputPane;
    private javax.swing.JPanel outputPanel;
    private javax.swing.JTable outputTable;
    private javax.swing.JLabel reactionsLabel;
    private javax.swing.JScrollPane reactionsScrollPane;
    private javax.swing.JTable reactionsTable;
    private javax.swing.JLabel reactorsLabel;
    private javax.swing.JPanel reactorsPanel;
    private javax.swing.JFormattedTextField reactorsTextField;
    private javax.swing.JButton removeReactionButton;
    private javax.swing.JLabel setupLabel;
    private javax.swing.JPanel setupPanel;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JRadioButton timeButton;
    private javax.swing.JLabel totalMonthlyProfitLabel;
    private javax.swing.JTextField totalMonthlyProfitTextField;
    private javax.swing.JLabel totalMonthlyRevenueLabel;
    private javax.swing.JTextField totalMonthlyRevenueTextField;
    private javax.swing.JLabel wipLabel;
    private javax.swing.JLabel wormholeClassLabel;
    private javax.swing.JPanel wormholeClassPanel;
    // End of variables declaration//GEN-END:variables
}
