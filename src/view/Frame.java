package view;

import control.Observable;
import model.CommandControl;
import model.Data;
import model.Json;
import model.Utils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;

public class Frame extends JFrame {
    Data data, dataCurrent;
    GraphPanel graphPanel;
    PromptPanel promptPanel;
    ToolsPanel toolsPanel;

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

        //ajout d'observateurs
        dataCurrent.addObservateur(graphPanel);
        System.out.println("nombre observateur: " + data.getNumberObervateur());

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
/*
    public void setData(Data data) {
        this.data.changeData(data);
    }
    */

    public class GraphPanel extends JPanel implements control.Observateur{

        public VisuGraph graph = new VisuGraph(data);

        public GraphPanel(){
            this.setBorder(BorderFactory.createTitledBorder("Graph"));
            this.setLayout(new GridBagLayout());
            this.setPreferredSize(new Dimension(1000, 600));
            this.add(graph);

        }

        /**
         * update data with Current Data
         */
        public void update(){
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
        public JButton btnTest = new JButton("Voisin");
        public JTextField tVoisin = new JTextField(10);
        public JButton btnVue = new JButton("Vue Globale");

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
            this.add(tVoisin);
            this.add(btnTest);
            this.add(btnVue);
/*
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
            btnTest.addActionListener(e->{
                dataCurrent.changeDataCurrent(data.getVoisin(tVoisin.getText()));
            });
            btnVue.addActionListener(e->{
                dataCurrent.changeDataCurrent(data);
            });
        }

    }

}

