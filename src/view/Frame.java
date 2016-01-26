package view;

import control.Observable;
import model.Data;
import model.Json;
import model.Utils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;

public class Frame extends JFrame {
    Data data;
    GraphPanel graphPanel;
    PromptPanel promptPanel;
    ToolsPanel toolsPanel;

    public Frame() {
        super("Neo4j");
        setResizable(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        data = new Data();
        //data.test();

        graphPanel = new GraphPanel();
        promptPanel = new PromptPanel();
        toolsPanel = new ToolsPanel();

        //ajout d'observateurs
        data.addObservateur(graphPanel);

        //placement des panneaux
        this.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints cont = new GridBagConstraints();
        cont.fill = GridBagConstraints.BOTH;
        cont.gridx = 0;
        cont.gridy = 0;
        cont.gridheight = 2;
        this.getContentPane().add(toolsPanel, cont);
        cont.gridx = 1;
        cont.gridy = 0;
        cont.gridheight = 1;

        this.getContentPane().add(graphPanel, cont);
        cont.gridx = 1;
        cont.gridy = 1;
        this.getContentPane().add(promptPanel, cont);

        this.pack();
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class GraphPanel extends JPanel implements control.Observateur{

        public JLabel lblText = new JLabel("Ceci est un graph");
        public VisuGraph graph = new VisuGraph(data);

        public GraphPanel(){
            this.setBorder(BorderFactory.createTitledBorder("Graph"));
            this.setLayout(new GridBagLayout());
            this.setPreferredSize(new Dimension(1000, 600));
            this.add(graph);

        }
        public void update(){
            graph.update(data);
            System.out.println("update de data");
        }
    }

    public class PromptPanel extends JPanel {
        public JTextField txtPrompt = new JTextField("                                                                                                                                   ");
        public JButton btnGenerate = new JButton("Generate");


        public PromptPanel() {
            this.setBorder(BorderFactory.createTitledBorder("Prompt"));

            this.add(btnGenerate);

            this.setLayout(new GridBagLayout());
            GridBagConstraints cont = new GridBagConstraints();
            cont.fill = GridBagConstraints.BOTH;
            cont.gridx = 0;
            cont.gridy = 0;
            this.add(txtPrompt, cont);

            cont.gridx = 1;
            cont.gridy = 0;
            this.add(btnGenerate, cont);
            this.setPreferredSize(new Dimension(1000, 100));


            btnGenerate.addActionListener(e -> {

                //Create a file chooser
                final JFileChooser fc = new JFileChooser();
            });
        }
    }

    public class ToolsPanel extends JPanel {
        //Create a file chooser
        final JFileChooser fileChooser = new JFileChooser();
        public JButton btnExport = new JButton("Exporter");
        public JButton btnImport = new JButton("Importer");
        public JButton btnRead = new JButton("Lire");

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
            this.setPreferredSize(new Dimension(150, 100));

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
                    data.changeData(Json.extract(file.getAbsolutePath()));
                    System.out.println(data);
                    data.notifier();
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
        }

    }
}

