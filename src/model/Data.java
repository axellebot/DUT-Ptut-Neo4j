package model;

import control.Observable;

import java.util.ArrayList;

public class Data extends Observable {
    private ArrayList<Node> nodeList;
    private ArrayList<Relation> relationList;

    public Data() {
        nodeList = new ArrayList<>();
        relationList = new ArrayList<>();
    }

    /**
     * @param nodeList     nodes list
     * @param relationList relations list
     */
    public Data(ArrayList<Node> nodeList, ArrayList<Relation> relationList) {
        this.nodeList = nodeList;
        this.relationList = relationList;
    }


    public void test() {
        nodeList.add(new Node("Paul"));
        nodeList.get(0).addLabel("Paul");
        nodeList.add(new Node("Harry Plotter"));
        nodeList.get(1).addLabel("Livre");
        relationList.add(new Relation("aime lire", nodeList.get(0), nodeList.get(1)));
    }

    public ArrayList<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(ArrayList<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public ArrayList<Relation> getRelationList() {
        return relationList;
    }

    public void setRelationList(ArrayList<Relation> relationList) {
        this.relationList = relationList;
    }

    /**
     * Recherche les relations d'un noeud
     *
     * @param node noeud
     * @return liste des de relations
     */
    public ArrayList<Relation> searchRelationOfNode(Node node) {
        ArrayList<Relation> listRelation = new ArrayList<>();
        for (int i = 0; i < this.relationList.size(); i++) {
            if (this.relationList.get(i).getNode1() == node || this.relationList.get(i).getNode2() == node)
                listRelation.add(this.relationList.get(i));
        }
        if (listRelation.size() == 0)
            return null;
        return listRelation;
    }

    /**
     * Recherche les Noeuds d'une relation
     *
     * @param relation relation contenant les nodes
     * @return list des nodes contenu dans la relation
     */
    public ArrayList<Node> searchNodeFromRelation(Relation relation) {
        for (int i = 0; i < this.relationList.size(); i++) {
            if (relationList.get(i) == relation) {
                ArrayList<Node> listNode = new ArrayList<>();
                listNode.add(relationList.get(i).getNode1());
                listNode.add(relationList.get(i).getNode2());
                return listNode;
            }
        }
        return null;
    }
}
