package GUI;
import javax.swing.UIManager;
import PriceFetcher.PriceFetcher;

public class GUI extends javax.swing.JFrame {
    
    private PriceFetcher prices;
    
    public GUI() {
        initComponents();
        prices = new PriceFetcher();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        wormholeClassPanel = new javax.swing.JPanel();
        wormholeClassLabel = new javax.swing.JLabel();
        C1Button = new javax.swing.JRadioButton();
        C2Button = new javax.swing.JRadioButton();
        C3Button = new javax.swing.JRadioButton();
        C4Button = new javax.swing.JRadioButton();
        C5Button = new javax.swing.JRadioButton();
        C6Button = new javax.swing.JRadioButton();
        tablePanel = new javax.swing.JPanel();
        reactionsLabel = new javax.swing.JLabel();
        reactionsScrollPane = new javax.swing.JScrollPane();
        reactionsTable = new javax.swing.JTable();
        setupPanel = new javax.swing.JPanel();
        cycleTimeLabel = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        m3PerCycleLabel = new javax.swing.JLabel();
        m3PerCycleTextField = new javax.swing.JTextField();
        wipLabel = new javax.swing.JLabel();
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
        totalMonthlyProfitLabel = new javax.swing.JLabel();
        totalMonthlyProfitTextField = new javax.swing.JTextField();
        monthlyInputVolumeLabel = new javax.swing.JLabel();
        monthlyInputVolumeTextField = new javax.swing.JTextField();
        monthlyOutputVolumeLabel = new javax.swing.JLabel();
        monthlyOutputVolumeTextField = new javax.swing.JTextField();
        averageEfficiencyLabel = new javax.swing.JLabel();
        averageEfficiencyTextField = new javax.swing.JTextField();
        monthlyMiningTimeLabel = new javax.swing.JLabel();
        monthlyMiningTimeTextField = new javax.swing.JTextField();
        calculateButton = new javax.swing.JButton();
        optimizeOutputButton = new javax.swing.JButton();
        setupLabel = new javax.swing.JLabel();
        aboutButton = new javax.swing.JButton();
        optimizeForPanel = new javax.swing.JPanel();
        optimizeForLabel = new javax.swing.JLabel();
        marginButton = new javax.swing.JRadioButton();
        timeButton = new javax.swing.JRadioButton();
        efficiencyButton = new javax.swing.JRadioButton();
        reactorsPanel = new javax.swing.JPanel();
        reactorsTextField = new javax.swing.JTextField();
        reactorsLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("mainFrame"); // NOI18N
        setResizable(false);

        wormholeClassPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        wormholeClassPanel.setPreferredSize(new java.awt.Dimension(100, 177));

        wormholeClassLabel.setText("Wormhole Class");

        C1Button.setText("C1");

        C2Button.setText("C2");

        C3Button.setText("C3");

        C4Button.setText("C4");

        C5Button.setText("C5");

        C6Button.setText("C6");

        javax.swing.GroupLayout wormholeClassPanelLayout = new javax.swing.GroupLayout(wormholeClassPanel);
        wormholeClassPanel.setLayout(wormholeClassPanelLayout);
        wormholeClassPanelLayout.setHorizontalGroup(
            wormholeClassPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wormholeClassPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(wormholeClassPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(C3Button)
                    .addComponent(C4Button)
                    .addComponent(C2Button)
                    .addComponent(C1Button)
                    .addComponent(C5Button)
                    .addComponent(C6Button)
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
                .addComponent(C2Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(C3Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(C4Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(C5Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(C6Button)
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

        m3PerCycleLabel.setText("m3 Per Cycle");

        wipLabel.setText("WIP");

        javax.swing.GroupLayout setupPanelLayout = new javax.swing.GroupLayout(setupPanel);
        setupPanel.setLayout(setupPanelLayout);
        setupPanelLayout.setHorizontalGroup(
            setupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(setupPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(setupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cycleTimeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1)
                    .addComponent(m3PerCycleLabel)
                    .addComponent(m3PerCycleTextField))
                .addGap(85, 85, 85)
                .addComponent(wipLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        setupPanelLayout.setVerticalGroup(
            setupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(setupPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cycleTimeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m3PerCycleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(setupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(m3PerCycleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(wipLabel))
                .addContainerGap(70, Short.MAX_VALUE))
        );

        outputLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        outputLabel.setText("Output");

        outputTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Polymer Name", "Profit/Month", "Revenue/Month", "Total Time(Hr)", "Quantity/Month", "Volume/Month"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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
                "Material Name", "Quantity/Month", "Volume/Month", "Mining Time(Hr)"
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

        totalMonthlyProfitLabel.setText("Total Monthly Profit");

        totalMonthlyProfitTextField.setEditable(false);
        totalMonthlyProfitTextField.setBackground(new java.awt.Color(255, 255, 255));

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

        calculateButton.setText("Calculate");

        optimizeOutputButton.setText("Optimize Output");

        javax.swing.GroupLayout outputPanelLayout = new javax.swing.GroupLayout(outputPanel);
        outputPanel.setLayout(outputPanelLayout);
        outputPanelLayout.setHorizontalGroup(
            outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputPanelLayout.createSequentialGroup()
                .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(outputPanelLayout.createSequentialGroup()
                        .addComponent(outputLabel)
                        .addGap(60, 60, 60)
                        .addComponent(removeReactionButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addReactionButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(calculateButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(optimizeOutputButton))
                    .addComponent(outputPane, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(materialsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(outputPanelLayout.createSequentialGroup()
                        .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(totalMonthlyProfitTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(totalMonthlyRevenueTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(totalMonthlyRevenueLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(totalMonthlyProfitLabel))
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
                                    .addComponent(averageEfficiencyTextField))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        outputPanelLayout.setVerticalGroup(
            outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputPanelLayout.createSequentialGroup()
                .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addReactionButton)
                    .addComponent(removeReactionButton)
                    .addComponent(outputLabel)
                    .addComponent(calculateButton)
                    .addComponent(optimizeOutputButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(outputPanelLayout.createSequentialGroup()
                        .addComponent(materialsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(totalMonthlyRevenueLabel)
                            .addComponent(monthlyInputVolumeLabel)
                            .addComponent(averageEfficiencyLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(totalMonthlyRevenueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(monthlyInputVolumeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(averageEfficiencyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(totalMonthlyProfitLabel)
                            .addComponent(monthlyOutputVolumeLabel)
                            .addComponent(monthlyMiningTimeLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(totalMonthlyProfitTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(monthlyOutputVolumeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(monthlyMiningTimeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(outputPane, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 1, Short.MAX_VALUE))
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

        optimizeForPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        optimizeForLabel.setText("Optimize For");

        marginButton.setText("Margin");
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

        reactorsTextField.setText("0");
        reactorsTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reactorsTextFieldActionPerformed(evt);
            }
        });

        reactorsLabel.setText("# Reactors");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(wormholeClassPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(optimizeForPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(aboutButton)
                    .addComponent(reactorsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(aboutButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wormholeClassPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(optimizeForPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reactorsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(tablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addReactionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addReactionButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addReactionButtonActionPerformed

    private void efficiencyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_efficiencyButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_efficiencyButtonActionPerformed

    private void reactorsTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reactorsTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_reactorsTextFieldActionPerformed

    private void timeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_timeButtonActionPerformed

    private void marginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_marginButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_marginButtonActionPerformed

    private void averageEfficiencyTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_averageEfficiencyTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_averageEfficiencyTextFieldActionPerformed

    private void monthlyInputVolumeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthlyInputVolumeTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_monthlyInputVolumeTextFieldActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
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
    private javax.swing.JRadioButton C2Button;
    private javax.swing.JRadioButton C3Button;
    private javax.swing.JRadioButton C4Button;
    private javax.swing.JRadioButton C5Button;
    private javax.swing.JRadioButton C6Button;
    private javax.swing.JButton aboutButton;
    private javax.swing.JButton addReactionButton;
    private javax.swing.JLabel averageEfficiencyLabel;
    private javax.swing.JTextField averageEfficiencyTextField;
    private javax.swing.JButton calculateButton;
    private javax.swing.JLabel cycleTimeLabel;
    private javax.swing.JRadioButton efficiencyButton;
    private javax.swing.JTextField jTextField1;
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
    private javax.swing.JTextField reactorsTextField;
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
