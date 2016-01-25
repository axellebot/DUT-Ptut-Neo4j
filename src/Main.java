/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import model.Data;
import model.Json;

import java.util.Scanner;

/**
 * @author Romain
 */
public class Main {
    public static void main(String[] args) {

        System.out.println("Bienvenue sur Neo4J Remake");

        //read Json
        Json test = new Json("D:\\_tmp\\IUT-Ptut\\model.json");
        test.read();

        //test
        Data data = new Data();
        System.out.println("Relation de test :");
        data.test();
        System.out.println(data.getRelaList().get(0));

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
