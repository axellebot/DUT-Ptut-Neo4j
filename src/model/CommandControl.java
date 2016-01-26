package model;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * @author Romain
 */
public abstract class CommandControl {

    public static void parser(Data data, String command){
        if(validParenthesage(command)) {
            //split by ' ' between CRUD and node or relation
            //like "CREATE (Paul:Person),(Sarah:Person{age:'25',ville:'Lyon'})"
            String[] parts = command.split(" ");
            if(parts[0].equals("MATCH")){
                //si on MATCH ALL (*)
                if(parts[1].equals("*")) System.out.println(data);
                else {
                    for (int i = 0; i < parts.length; i++) {
                        if (parts[i].equals("RETURN"))
                            returnCommand(data.getNodeByName(parts[i + 1]));
                    }
                }
            }
            else if(parts[0].equals("CREATE")){
                //split by ',' between nodes
                String[] subparts = command.split(",");
                //for each parts between the ','
                for(String s : subparts) {
                    //remove parentheses
                    String[] subpart = s.split("\\("); subpart = subpart[1].split("\\)");
                    //split by '{'
                    String[] propSplit = subpart[0].split("\\{");
                    ArrayList<String> propList = new ArrayList();
                    if(propSplit.length > 1) {
                        //remove '}'
                        String[] properties = propSplit[1].split("\\}");
                        //split by ';'
                        String[] propParts = properties[0].split(";");
                        for(String p : propParts) propList.add(p);
                    }
                    //split by ':'
                    String[] labels = propSplit[0].split(":");
                    ArrayList<String> labelList = new ArrayList();
                    for(int i = 1; i < labels.length; i++)
                        labelList.add(labels[i]);
                    data.getNodeList().add(new Node(labels[0], labelList, propList));
                }
                for(int i = 0; i < parts.length; i++){
                    if(parts[i].equals("RETURN"))
                        returnCommand(data.getNodeByName(parts[i+1]));
                }
            }
            else if(parts[0].equals("DELETE")){
                data.getNodeList().remove(data.getNodeByName(parts[1]));
            }
        }
        else System.out.println("Commande invalide : erreur de parenthesage");
    }

    public static void returnCommand(Node node){
        //afficher la node sur le graphe lAAAAAAAAAAAAAAAAAAAAA
    }

    public static boolean validParenthesage(String s) {
        HashMap<Character, Character> map = new HashMap();
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');
        Stack<Character> stack = new Stack();
        for (int i = 0; i < s.length(); i++) {
            char curr = s.charAt(i);

            if (map.keySet().contains(curr)) {
                stack.push(curr);
            } else if (map.values().contains(curr)) {
                if (!stack.empty() && map.get(stack.peek()) == curr) {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }
        return stack.empty();
    }
}
