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
    }

    public class GraphPanel extends JPanel {

        public JLabel lblText = new JLabel("Ceci est un graph");

        public GraphPanel() {
            this.setBorder(BorderFactory.createTitledBorder("Start/Stop"));
            this.setPreferredSize(new Dimension(100, 100));
            this.setLayout(new GridBagLayout());
            GridBagConstraints cont = new GridBagConstraints();
            cont.fill = GridBagConstraints.BOTH;
            cont.gridx = 0;
            cont.gridy = 0;
            this.add(lblText, cont);

        }
    }


    public class PromptPanel extends JPanel {
        public JButton btnGenerate = new JButton("Géneration");


        public PromptPanel() {

        }
    }

    public class ToolsPanel extends JPanel {
        public JButton btnExport = new JButton("Exporter");
        public JButton btnImport = new JButton("Importer");
        public JButton btnRead = new JButton("Lire");

        public ToolsPanel() {
            this.setBorder(BorderFactory.createTitledBorder("Outils"));

            this.add(btnExport);
            this.add(btnImport);
            this.add(btnRead);
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

