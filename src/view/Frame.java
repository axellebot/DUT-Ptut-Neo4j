package view;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    GraphPanel graphPanel;
    PromptPanel promptPanel;
    ToolsPanel toolsPanel;

    public Frame() {
        super("Neo4j");
        setResizable(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        graphPanel = new GraphPanel();
        promptPanel = new PromptPanel();
        toolsPanel = new ToolsPanel();

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

    public class GraphPanel extends JPanel {

        public JLabel lblText = new JLabel("Ceci est un graph");

        public GraphPanel() {
            this.setBorder(BorderFactory.createTitledBorder("Graph"));
            this.setLayout(new GridBagLayout());
            GridBagConstraints cont = new GridBagConstraints();

            cont.fill = GridBagConstraints.BOTH;
            cont.gridx = 0;
            cont.gridy = 0;
            this.add(lblText, cont);
            this.setPreferredSize(new Dimension(1000, 600));

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
        public JButton btnExport = new JButton("Export");
        public JButton btnImport = new JButton("Import");
        public JButton btnRead = new JButton("Read");

        public ToolsPanel() {
            this.setBorder(BorderFactory.createTitledBorder("Tools"));

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
                //Create a file chooser
                final JFileChooser fc = new JFileChooser();
            });
            btnImport.addActionListener(e -> {
            });
            btnRead.addActionListener(e -> {
            });
        }

    }
}

