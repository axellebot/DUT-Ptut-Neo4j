package model;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class Relation {
    private String name;
    private Node node1;
    private Node node2;

    public Relation(String name, Node node1, Node node2) {
        this.node1 = node1;
        this.node2 = node2;
        this.name = name;
        System.out.println("Création d'une relation entre " + node1.getName() + " et " + node2.getName());
    }


    public Node getNode1() {
        return node1;
    }

    public void setNode1(Node node1) {
        this.node1 = node1;
    }

    public Node getNode2() {
        return node2;
    }

    public void setNode2(Node node2) {
        this.node2 = node2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "(" + node1.getName() + ")--" + this.name + "->(" + node2.getName() + ")";
    }


}
