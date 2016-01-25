package model;

import java.util.ArrayList;

public class Data {
    private ArrayList<Node> nodeList;
    private ArrayList<Relation> relaList;

    public Data() {
        nodeList = new ArrayList<>();
        relaList = new ArrayList<>();
        //test();
    }

    public void test() {
        nodeList.add(new Node("Paul"));
        nodeList.get(0).addLabel("Personne");
        nodeList.add(new Node("Harry Plotter"));
        nodeList.get(1).addLabel("Livre");
        relaList.add(new Relation("aime lire", nodeList.get(0), nodeList.get(1)));
    }

    public ArrayList<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(ArrayList<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public ArrayList<Relation> getRelaList() {
        return relaList;
    }

    public void setRelaList(ArrayList<Relation> relaList) {
        this.relaList = relaList;
    }

    /**
     * Recherche les relations d'un noeud
     *
     * @param node noeud
     * @return liste des de relations
     */
    public ArrayList<Relation> searchRelationOfNode(Node node) {
        ArrayList<Relation> listRelation = new ArrayList<>();
        for (int i = 0; i < this.relaList.size(); i++) {
            if (this.relaList.get(i).getNode1() == node || this.relaList.get(i).getNode2() == node)
                listRelation.add(this.relaList.get(i));
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
        for (int i = 0; i < this.relaList.size(); i++) {
            if (relaList.get(i) == relation) {
                ArrayList<Node> listNode = new ArrayList<>();
                listNode.add(relaList.get(i).getNode1());
                listNode.add(relaList.get(i).getNode2());
                return listNode;
            }
        }
        return null;
    }
}
