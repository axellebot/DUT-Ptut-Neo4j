package model;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class Node {
    private String name;
    private ArrayList<String> labels;
    private ArrayList<String> properties;

    /**
     * Constructeur model.Node
     *
     * @param name nom du noeud
     */
    public Node(String name) {
        this.name = name;
        labels = new ArrayList();
        properties = new ArrayList();
        System.out.println("Création du noeud " + name);
    }

    /**
     * Constructeur model.Node
     *
     * @param name   nom du noeud
     * @param labels ArrayList<String> liste des Labels
     */
    public Node(String name, ArrayList<String> labels) {
        this.name = name;
        this.labels = labels;
        properties = new ArrayList();
        System.out.print("Création du noeud " + name);
    }

    /**
     * Constructeur model.Node
     *
     * @param name       nom
     * @param properties ArrayList<String> liste des Propriétés
     * @param labels     ArrayList<String> liste des Labels
     */
    public Node(String name, ArrayList<String> labels, ArrayList<String> properties) {
        this.name = name;
        this.labels = labels;
        this.properties = properties;
        System.out.print("Création du noeud " + name);
    }

    public static Node findNodeInNodeList(ArrayList<Node> nodeList, String nodeName) {
        for (Node node : nodeList) {
            if (node.getName().equals(nodeName))
                return node;
        }
        return null;
    }

    /**
     * Getter node's name
     *
     * @return String node's name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter node's name
     *
     * @param name String node's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter de la node's labels list
     *
     * @return ArrayList<String> Labels
     */
    public ArrayList<String> getLabels() {
        return labels;
    }

    /**
     * Add Labels to a node
     *
     * @param labels ArrayList String
     */
    public void setLabels(ArrayList<String> labels) {
        this.labels = labels;
    }

    /**
     * Getter de la liste de propriété du noeud
     *
     * @return ArrayList<String> Propriétés
     */
    public ArrayList<String> getProperties() {
        return properties;
    }

    /**
     * Ajouter propriétés à un noeud
     *
     * @param properties
     */
    public void setProperties(ArrayList<String> properties) {
        this.properties = properties;
    }

    /**
     * Getter d'un label du noeud à un indice
     *
     * @param i indice >=0
     * @return Le label
     */
    public String getLabelIndex(int i) {
        if (i < this.labels.size())
            return this.labels.get(i);
        System.out.println("Index en dehors de la liste de labels");
        return null;
    }

    /**
     * Getter d'une propriété du noeud à un indice
     *
     * @param i indice >=0
     * @return
     */
    public String getPropertieIndex(int i) {
        if (i < this.properties.size())
            return this.properties.get(i);
        System.out.println("Index en dehors de la liste de propriétés");
        return null;
    }

    /**
     * Setter label à un indice
     *
     * @param label
     * @param i     indice >=0
     */
    public void setLabelIndex(String label, int i) {
        if (i < this.labels.size())
            this.labels.set(i, label);
        System.out.println("Index en dehors de la liste de labels");
    }

    /**
     * Ajouter ou changer une propriété d'un noeud à un indice précis
     *
     * @param properties propriété
     * @param i          indice >=0
     */
    public void setPropertieIndex(String properties, int i) {
        if (i < this.properties.size())
            this.properties.set(i, properties);
        System.out.println("Index en dehors de la liste de propriétés");
    }

    /**
     * Ajouter un label à un noeud
     *
     * @param label nom du label
     */

    public void addLabel(String label) {
        this.labels.add(label);
    }

    /**
     * Ajouter une propriété à un noeud
     *
     * @param properties propriété
     */
    public void addPropertie(String properties) {
        this.properties.add(properties);
    }
}