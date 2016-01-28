/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import model.CommandControl;
import model.Data;
import org.jfree.ui.RefineryUtilities;
import view.Frame;

import javax.swing.*;
import java.util.Scanner;

/**
 * @author Romain
 */
public class Main {
    public static void main(String[] args) {

        System.out.println("Bienvenue sur Neo4J Remake");

        //test Data
        Data data = new Data();
        System.out.println("Relation de test :");
        data.test();

        //test parser
        CommandControl.parser(data, "CREATE (Pierre Salomon:Person),(Sarah:Person{age:'25';ville:'Lyon'})");
        //CommandControl.parser(data, "MATCH (Pierre Salomon:Person),(Sarah:Person{age:'25';ville:'Lyon'})");

        //UserInterface
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        //IHM
        Frame _fenetre = new Frame();

        RefineryUtilities.centerFrameOnScreen(_fenetre);

        _fenetre.setVisible(true);

        _fenetre.loop();

        //CommandControl
        Scanner sc = new Scanner(System.in);
        String str;
        String exitCommand = "exit";
        do {
            System.out.print("Anonymous User>>");
            str = sc.nextLine();
            if (!str.equals(exitCommand)) System.out.println("Grave !");
        } while (!str.equals(exitCommand));
        System.out.println("A+");
    }

}
