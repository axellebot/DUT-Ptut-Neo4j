package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by axell on 25/01/16.
 */
public class Frame extends JFrame {
    PanneauVisu panoVisu;
    PanneauCommande panoComm;
    PanneauOutils panoOutil;

    public Frame() {
        super("Neo4j");
        setResizable(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panoVisu = new PanneauVisu();
        panoComm = new PanneauCommande();
        panoOutil = new PanneauOutils();
        //placement des panneaux
        this.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints cont = new GridBagConstraints();
        cont.fill = GridBagConstraints.BOTH;
        cont.gridx = 0;
        cont.gridy = 0;
        cont.gridwidth = 5;
        cont.gridheight = 15;
        this.getContentPane().add(panoOutil, cont);
        cont.gridx = 1;
        cont.gridy = 0;
        cont.gridheight = 13;
        cont.gridwidth = 10;
        this.getContentPane().add(panoVisu, cont);
        cont.gridx = 1;
        cont.gridy = 1;
        cont.gridheight = 2;
        cont.gridwidth = 10;
        this.getContentPane().add(panoComm, cont);
    }

    public class PanneauVisu extends JPanel {

        public JButton btStart = new JButton("Start");
        public JButton btStop = new JButton("Stop");


        public PanneauVisu() {
            this.setBorder(BorderFactory.createTitledBorder("Start/Stop"));
            this.setPreferredSize(new Dimension(100, 100));
            this.setLayout(new GridBagLayout());
            GridBagConstraints cont = new GridBagConstraints();
            cont.fill = GridBagConstraints.BOTH;
            cont.gridx = 0;
            cont.gridy = 0;
            this.add(btStart, cont);
            cont.gridy = 1;
            this.add(btStop, cont);
        }
    }


    public class PanneauCommande extends JPanel {
        public JButton Generer = new JButton("Géneration");


        public PanneauCommande() {

        }

    }

    public class PanneauOutils extends JPanel {


        public PanneauOutils() {

        }

    }
}

