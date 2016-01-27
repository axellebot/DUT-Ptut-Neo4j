package model;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Collections;
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
            //MATCH Command
            if(parts[0].toLowerCase().equals("match")){
                //if MATCH ALL (*)
                if(parts[1].equals("*")) System.out.println(data);
                else {
                    String[] subpart = parts[1].split("\\("); subpart = subpart[1].split("\\)");
                    //////
                    if (data.getNodeByName(subpart[0]) != null)
                        System.out.println(data.getNodeByName(subpart[0]));
                    else if (data.getRelationByName(subpart[0]) != null)
                        System.out.println(data.getRelationByName(subpart[0]));
                    //////
                    /*for (int i = 0; i < parts.length; i++) {
                        if (parts[i].toLowerCase().equals("return")) {
                            if (data.getNodeByName(parts[i + 1]) != null)
                                returnCommand(data.getNodeByName(parts[i + 1]));
                            else if (data.getRelationByName(parts[i + 1]) != null)
                                returnCommand(data.getRelationByName(parts[i + 1]));
                        }
                    }*/
                }
            }
            //CREATE Command
            else if(parts[0].toLowerCase().equals("create")){
                //split by '-'
                String[] relaParts = command.split("-");
                if(relaParts.length == 3){
                    String[] firstPart = relaParts[0].split("\\("); firstPart = firstPart[1].split("\\)");
                    String[] lastPart = relaParts[2].split("\\("); lastPart = lastPart[1].split("\\)");
                    String[] relationPart = relaParts[1].split("\\["); relationPart = relationPart[1].split("\\]");
                    if(data.getNodeByName(firstPart[0]) != null && data.getNodeByName(lastPart[0]) != null)
                        data.getRelationList().add(new Relation(relationPart[0],data.getNodeByName(firstPart[0]),data.getNodeByName(lastPart[0])));
                }
                else {
                    //split by ',' between nodes
                    String[] subparts = command.split(",");
                    //for each parts between the ','
                    for (String s : subparts) {
                        //remove parentheses
                        String[] subpart = s.split("\\(");
                        subpart = subpart[1].split("\\)");
                        //split by '{'
                        String[] propSplit = subpart[0].split("\\{");
                        ArrayList<String> propList = new ArrayList();
                        if (propSplit.length > 1) {
                            //remove '}'
                            String[] properties = propSplit[1].split("\\}");
                            //split by ';'
                            String[] propParts = properties[0].split(";");
                            Collections.addAll(propList, propParts);
                        }
                        //split by ':'
                        String[] labels = propSplit[0].split(":");
                        ArrayList<String> labelList = new ArrayList();
                        for (int i = 1; i < labels.length; i++)
                            labelList.add(labels[i]);
                        data.addNode(new Node(labels[0], labelList, propList));
                    }
                }
                for (int i = 0; i < parts.length; i++) {
                    if (parts[i].toLowerCase().equals("return")) {
                        if (data.getNodeByName(parts[i + 1]) != null)
                            returnCommand(data.getNodeByName(parts[i + 1]));
                        else if (data.getRelationByName(parts[i + 1]) != null)
                            returnCommand(data.getRelationByName(parts[i + 1]));
                    }
                }
            }
            //DELETE Command
            else if(parts[0].toLowerCase().equals("delete")){
                if (data.getNodeByName(parts[1]) != null)
                    data.removeNode(parts[1]);
                else if (data.getRelationByName(parts[1]) != null)
                    data.getRelationList().remove(data.getRelationByName(parts[1]));
            }
        }
        else System.out.println("Commande invalide : erreur de parenthesage");
    }

    public static void returnCommand(Node node){
        //afficher la node sur le graphe lAAAAAAAAAAAAAAAAAAAAA
        System.out.println(node);
    }

    public static void returnCommand(Relation rel){
        System.out.println(rel);
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
