package view;

import model.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;

public class Frame extends JFrame {

    Data data, dataCurrent;
    GraphPanel graphPanel;
    PromptPanel promptPanel;
    ToolsPanel toolsPanel;
    CreatePanel createpanel;
    InstructPanel instructPanel;
    JDialog dialInstruct;
    public Frame() {
        super("Neo4j");
        setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        data = new Data();
        dataCurrent = new Data();
        data.test();
        dataCurrent.changeDataCurrent(data);
        graphPanel = new GraphPanel();
        promptPanel = new PromptPanel();
        toolsPanel = new ToolsPanel();
        createpanel = new CreatePanel();
        instructPanel = new InstructPanel();

        dialInstruct = new JDialog(this);
        dialInstruct.add(instructPanel);
        dialInstruct.setSize(500, 400);
        dialInstruct.setLocationRelativeTo(this);
        JButton btnInstruct = new JButton("Manual");
        btnInstruct.addActionListener(e -> {
            dialInstruct.setVisible(true);
        });

        //ajout d'observateurs
        dataCurrent.addObservateur(graphPanel);
        dataCurrent.addObservateur(createpanel);
        System.out.println("nombre observateur: " + data.getNumberObervateur());

        //placement des panneaux
        this.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints cont = new GridBagConstraints();
        cont.fill = GridBagConstraints.BOTH;
        cont.gridx = 0;
        cont.gridy = 0;
        cont.gridheight = 1;
        this.getContentPane().add(toolsPanel, cont);
        cont.gridx = 0;
        cont.gridy = 1;
        cont.gridheight = 1;
        this.getContentPane().add(createpanel, cont);

        cont.gridx = 0;
        cont.gridy = 2;
        cont.gridheight = 1;
        this.getContentPane().add(add(btnInstruct), cont);

        cont.gridx = 1;
        cont.gridy = 0;
        cont.gridheight = 2;
        this.getContentPane().add(graphPanel, cont);
        cont.gridx = 1;
        cont.gridy = 2;
        this.getContentPane().add(promptPanel, cont);

        this.pack();

    }

    public void loop(){
            graphPanel.graph.loop();
    }

    public Data getData() {
        return data;
    }
    /*
     public void setData(Data data) {
     this.data.changeData(data);
     }
     */

    public class GraphPanel extends JPanel implements control.Observateur {

        public VisuGraph graph = new VisuGraph(data);

        public GraphPanel() {
            this.setBorder(BorderFactory.createTitledBorder("Graph"));
            this.setLayout(new GridBagLayout());
            this.setPreferredSize(new Dimension(1000, 600));
            this.add(graph);
        }

        /**
         * update data with Current Data
         */
        public void update() {
            graph.update(dataCurrent);
            System.out.println("update de data");
        }
    }

    public class PromptPanel extends JPanel {

        public JTextArea txtPrompt = new JTextArea(400, 200);
        public JButton btnGenerate = new JButton("Generate");

        public PromptPanel() {
            this.setBorder(BorderFactory.createTitledBorder("Prompt"));

            this.add(btnGenerate);

            this.setLayout(new GridBagLayout());
            GridBagConstraints cont = new GridBagConstraints();
            cont.fill = GridBagConstraints.BOTH;
            cont.gridx = 0;
            cont.gridy = 0;
            cont.weightx = 6;
            this.add(txtPrompt, cont);

            cont.gridx = 1;
            cont.gridy = 0;
            cont.weightx = 1;
            this.add(btnGenerate, cont);
            this.setPreferredSize(new Dimension(1000, 100));

            btnGenerate.addActionListener(e -> {
                //Call parser
                CommandControl.parser(data, txtPrompt.getText());
                dataCurrent.changeDataCurrent(data);
            });
        }

    }

    public class ToolsPanel extends JPanel {

        //Create a file chooser
        final JFileChooser fileChooser = new JFileChooser();
        public JButton btnExport = new JButton("Exporter");
        public JButton btnImport = new JButton("Importer");
        public JButton btnRead = new JButton("Lire");
        public JButton btnTest = new JButton("Tester");
        public JTextField txtNode = new JTextField("", 10);
        public JTextField txtRelation = new JTextField("", 10);
        public JButton btnVue = new JButton("Vue Globale");
        public JToggleButton btnShowValue = new JToggleButton("Voir les labels");

        public ToolsPanel() {
            this.setBorder(BorderFactory.createTitledBorder("Outils"));

            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    if (f.isDirectory()) {
                        return true;
                    }
                    return Utils.getExtension(f).equals(Utils.json);
                }

                @Override
                public String getDescription() {
                    return "Json";
                }
            });

            this.add(btnExport);
            this.add(btnImport);
            this.add(btnRead);
            this.add(txtNode);
            this.add(txtRelation);
            this.add(btnTest);
            this.add(btnVue);

            /* this.setLayout(new GridBagLayout());
            this.add(btnShowValue);
            /*
             this.setLayout(new GridBagLayout());
             GridBagConstraints cont = new GridBagConstraints();
             cont.fill = GridBagConstraints.BOTH;
             cont.gridx = 0;
             cont.gridy = 0;
             this.add(btnExport, cont);

            this.setLayout(new GridBagLayout());
            GridBagConstraints cont = new GridBagConstraints();
            cont.fill = GridBagConstraints.BOTH;
            cont.gridx = 0;
            cont.gridy = 0;
            this.add(btnExport, cont);

             cont.gridx = 0;
             cont.gridy = 1;
             this.add(btnImport, cont);

             cont.gridx = 0;
             cont.gridy = 2;
             this.add(btnRead, cont);
             */
            this.setPreferredSize(new Dimension(200, 200));

            btnExport.addActionListener(e -> {
                //In response to a button click:
                int returnVal = fileChooser.showSaveDialog(this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    //This is where a real application would save the file.

                    if (Utils.getExtension(file) == null) {
                        Json.export(data, file.getAbsolutePath() + ".json");
                    } else if (Utils.getExtension(file).equals(Utils.json)) {
                        Json.export(data, file.getAbsolutePath());
                    } else {
                        Json.export(data, file.getAbsolutePath() + ".json");
                    }
                } else {
                    System.out.println("Save command cancelled by user.");
                }
            });
            btnImport.addActionListener(e -> {
                //In response to a button click:
                int returnVal = fileChooser.showOpenDialog(this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    System.out.println("nombre Observateur avant importation: " + data.getNumberObervateur());
                    //System.out.println(data);
                    data.changeData(Json.extract(file.getAbsolutePath()));
                    dataCurrent.changeDataCurrent(data);
                    System.out.println("nombre Observateur après importation: " + data.getNumberObervateur());
                } else {
                    System.out.println("Open command cancelled by user.");
                }
            });
            btnRead.addActionListener(e -> {
                //In response to a button click:
                int returnVal = fileChooser.showOpenDialog(this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    Json.read(file.getAbsolutePath());
                } else {
                    System.out.println("Read command cancelled by user.");
                }
            });
            btnTest.addActionListener(e -> {
                if (!"".equals(txtNode.getText())) {
                    if (!"".equals(txtRelation.getText())) {
                        dataCurrent.changeDataCurrent(data.getVoisinWithRelationName(txtNode.getText(), txtRelation.getText()));
                    } else {
                        dataCurrent.changeDataCurrent(data.getVoisin(txtNode.getText()));
                    }
                }
            });
            btnVue.addActionListener(e -> {
                dataCurrent.changeDataCurrent(data);
            });
            btnShowValue.addActionListener(e ->{
                System.out.println("show labels");
                graphPanel.graph.setShowLabels(btnShowValue.isSelected());
                System.out.println("state show label: " + graphPanel.graph.isShowLabels());
                dataCurrent.notifier();
            });
        }


    }

    public class InstructPanel extends JPanel {
        public JLabel Create = new JLabel("<html><br>Create : </html>");
        public JLabel CreateWrite1 = new JLabel("<html>create (node_name) </html>");
        public JLabel CreateWrite2 = new JLabel("<html>OR</html>");
        public JLabel CreateWrite3 = new JLabel("<html>(node_name:label{prop:\"proprietie_name}\"</html>");
        public JLabel CreateExtras1 = new JLabel("<html>Can create multiple node by separating<br> them with a dot like: </html>");
        public JLabel CreateExtras2 = new JLabel("<html>create (node_name1),(node_name2)\")</html>");

        public JLabel Delete = new JLabel("<html><br>Delete :</html> ");
        public JLabel DeleteWrite = new JLabel("<html>Delete (node_name)</html>");

        public JLabel Update = new JLabel("<html><br>Update : </html>");
        public JLabel UpdateWrite = new JLabel("<html>Set (node_name) prop=propertie_name </html>");

        public JLabel Return = new JLabel("<html><br>Set : </html>");
        public JLabel ReturnWrite = new JLabel("<html>Match (node_name)</html>");
        public JLabel ReturnExtras1 = new JLabel("<html>To return every nodes and relations: </html>");
        public JLabel ReturnExtras2 = new JLabel("<html>Match *</html>");


        public InstructPanel() {
            this.setLayout(new GridBagLayout());
            GridBagConstraints cont = new GridBagConstraints();
            cont.fill = GridBagConstraints.BOTH;
            Font ft = new Font("Calibri", Font.PLAIN, 16);
            Font ft2 = new Font("Britannic Bold", Font.PLAIN, 18);


            Create.setFont(ft2);

            cont.gridx = 0;
            cont.gridy = 0;
            this.add(Create, cont);

            CreateWrite1.setFont(ft);
            CreateWrite1.setForeground(new Color(0, 0, 128));

            CreateWrite3.setFont(ft);
            CreateWrite3.setForeground(new Color(0, 0, 128));


            cont.gridx = 0;
            cont.gridy = 1;
            this.add(CreateWrite1, cont);
            cont.gridx = 0;
            cont.gridy = 2;
            this.add(CreateWrite2, cont);
            cont.gridx = 0;
            cont.gridy = 3;
            this.add(CreateWrite3, cont);

            CreateExtras2.setFont(ft);
            CreateExtras2.setForeground(new Color(0, 0, 128));

            cont.gridx = 0;
            cont.gridy = 4;
            this.add(CreateExtras1, cont);
            cont.gridx = 0;
            cont.gridy = 5;
            this.add(CreateExtras2, cont);

            Delete.setFont(ft2);

            cont.gridx = 0;
            cont.gridy = 6;
            this.add(Delete, cont);

            DeleteWrite.setFont(ft);
            DeleteWrite.setForeground(new Color(0, 0, 128));

            cont.gridx = 0;
            cont.gridy = 7;
            this.add(DeleteWrite, cont);

            Update.setFont(ft2);
            cont.gridx = 0;
            cont.gridy = 8;
            this.add(Update, cont);

            UpdateWrite.setFont(ft);
            UpdateWrite.setForeground(new Color(0, 0, 128));

            cont.gridx = 0;
            cont.gridy = 9;
            this.add(UpdateWrite, cont);

            Return.setFont(ft2);
            cont.gridx = 0;
            cont.gridy = 10;
            this.add(Return, cont);

            ReturnWrite.setFont(ft);
            ReturnWrite.setForeground(new Color(0, 0, 128));

            cont.gridx = 0;
            cont.gridy = 11;
            this.add(ReturnWrite, cont);

            cont.gridx = 0;
            cont.gridy = 12;
            this.add(ReturnExtras1, cont);

            ReturnExtras2.setFont(ft);
            ReturnExtras2.setForeground(new Color(0, 0, 128));

            cont.gridx = 0;
            cont.gridy = 13;
            this.add(ReturnExtras2, cont);

            this.setPreferredSize(new Dimension(100, 200));


        }

    }

    public class CreatePanel extends JPanel implements control.Observateur {

        public JLabel lCreateNode = new JLabel("Créer une node:");
        public JTextField tCreateNode = new JTextField(10);
        public JButton btCreateNode = new JButton("creer une node");

        public JLabel lCreateRelation = new JLabel("Créer une relation:");
        public JTextField tCreateRelation = new JTextField(10);
        public DefaultComboBoxModel<Node> modelNode1, modelNode2;
        public JComboBox<Node> cbNode1;
        public JComboBox<Node> cbNode2;
        public JButton btCreateRelation = new JButton("creer une relation");

        public CreatePanel() {
            this.setBorder(BorderFactory.createTitledBorder("Creation"));

            modelNode1 = new DefaultComboBoxModel();
            modelNode2 = new DefaultComboBoxModel();

            cbNode1 = new JComboBox(modelNode1);
            cbNode2 = new JComboBox(modelNode2);

            cbNode1.setPreferredSize(new Dimension(150, 20));
            cbNode2.setPreferredSize(new Dimension(150, 20));

            update();
            this.add(lCreateNode);
            this.add(tCreateNode);
            this.add(btCreateNode);
            this.add(lCreateRelation);
            this.add(tCreateRelation);
            this.add(cbNode1);
            this.add(cbNode2);
            this.add(btCreateRelation);
            this.setPreferredSize(new Dimension(200, 200));
            btCreateNode.addActionListener(e -> {
                if (!"".equals(tCreateNode.getText())) {
                    data.addNode(new Node(tCreateNode.getText()));
                    dataCurrent.changeDataCurrent(data);
                }
                tCreateNode.setText("");
            });
            btCreateRelation.addActionListener(e -> {
                if (!"".equals(tCreateRelation.getText()) && !modelNode1.getSelectedItem().equals(modelNode2.getSelectedItem())) {
                    data.addRelation(new Relation(tCreateRelation.getText(), (Node) modelNode1.getSelectedItem(), (Node) modelNode2.getSelectedItem()));
                    dataCurrent.changeDataCurrent(data);
                }
                tCreateRelation.setText("");
            });
        }

        @Override
        public void update() {
            modelNode1.removeAllElements();
            modelNode2.removeAllElements();
            for (Node n : dataCurrent.getNodeList()) {
                modelNode1.addElement(n);
            }
            for (Node n : dataCurrent.getNodeList()) {
                modelNode2.addElement(n);
            }
        }

    }
}
