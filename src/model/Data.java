package model;

import control.Observable;

import java.util.ArrayList;

public class Data extends Observable {
    private ArrayList<Node> nodeList;
    private ArrayList<Relation> relationList;

    public Data() {
        super();
        nodeList = new ArrayList<>();
        relationList = new ArrayList<>();
    }

    public Data(control.Observateur ob) {
        super(ob);
        nodeList = new ArrayList<>();
        relationList = new ArrayList<>();
    }

    /**
     * @param nodeList     nodes list
     * @param relationList relations list
     */
    public Data(ArrayList<Node> nodeList, ArrayList<Relation> relationList) {
        super();
        this.nodeList = nodeList;
        this.relationList = relationList;
    }

    public void test() {
        nodeList.clear();
        relationList.clear();
        nodeList.add(new Node("Paul"));
        nodeList.get(0).addLabel("Paul");
        nodeList.add(new Node("Harry Plotter"));
        nodeList.get(1).addLabel("Livre");
        relationList.add(new Relation("aime lire", nodeList.get(0), nodeList.get(1)));
        notifier();
    }

    public ArrayList<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(ArrayList<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public Node getNodeByName(String name) {
        for (Node n : nodeList) {
            if (n.getName().equals(name)) return n;
        }
        return null;
    }

    public void addNode(Node node) {
        if (getNodeByName(node.getName()) == null)
            nodeList.add(node);
        else
            System.out.println("Node already exists");
    }

    public void removeNode(String name) {
        for (Relation r : searchRelationOfNode(getNodeByName(name)))
            relationList.remove(r);
        nodeList.remove(getNodeByName(name));
    }

    public void addRelation(Relation rel) {
        if (getRelationByName(rel.getName()) == null)
            relationList.add(rel);
        else
            System.out.println("Relation already exists");
    }

    public ArrayList<Relation> getRelationList() {
        return relationList;
    }

    public void setRelationList(ArrayList<Relation> relationList) {
        this.relationList = relationList;
    }

    public Relation getRelationByName(String name) {
        for (Relation r : relationList) {
            if (r.getName().equals(name)) return r;
        }
        return null;
    }

    public void deleteAll() {
        for (int i = 0; i < relationList.size(); i++)
            relationList.remove(i);
        for (int i = 0; i < nodeList.size(); i++)
            nodeList.remove(i);
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

    public String toString() {
        String display;

        display = "////////////////NODES////////////////\n";
        for (Node node : this.nodeList) {
            display += node.toString() + "\n";
        }

        display += "////////////////RELATIONS////////////////\n";
        for (Relation relation : this.relationList) {
            display += relation.toString() + "\n";
        }

        return display;
    }

    /**
     * Change List of nodes and list of relations
     *
     * @param data Data
     */
    public void changeData(Data data) {
        this.nodeList = data.getNodeList();
        this.relationList = data.getRelationList();
    }

    public void changeDataCurrent(Data data) {
        this.nodeList = data.getNodeList();
        this.relationList = data.getRelationList();
        this.notifier();
    }

    /**
     * Remove duplicate Nodes
     */
    public void check() {
        boolean stop = false, find;
        ArrayList<Node> nodeListTmp;
        while (!stop) {
            find = false;
            nodeListTmp = new ArrayList<>(nodeList);
            for (int i = 0; i < nodeListTmp.size() || !find; i++) {
                for (int j = i + 1; j < nodeListTmp.size(); j++) {
                    if (nodeListTmp.get(i).getName() == nodeListTmp.get(j).getName()) {
                        nodeList.remove(nodeListTmp.get(j));
                        find = true;
                        break;
                    }
                }
            }
        }
    }

    public Data getVoisin(String nodeName) {
        if (nodeExist(nodeName)) {
            System.out.println("la node existe");
            System.out.println("recherche des voisins");
            Data retour = new Data();

            Node nodeChoisi = this.getNodeByName(nodeName);
            retour.nodeList.add(nodeChoisi);
            retour.relationList = findAllRelationOfNode(nodeChoisi);
            for (Relation r : retour.relationList) {
                Node nodeVoisine = r.getNode1().equals(nodeChoisi) ? r.getNode2() : r.getNode1();
                if (!retour.nodeExist(nodeVoisine.getName())) {
                    retour.nodeList.add(nodeVoisine);
                }
            }
            return retour;
        }
        return null;
    }

    /**
     * @param nodeName     String
     * @param relationName String
     * @return Data
     */
    public Data getVoisinWithRelationName(String nodeName, String relationName) {
        if (nodeExist(nodeName) && relationExist(relationName)) {
            System.out.println("le noeud et au moins une relation existe relation existe");
            System.out.println("recherche des relations");
            Data retour = new Data();

            Node nodeChoisi = this.getNodeByName(nodeName);
            retour.nodeList.add(nodeChoisi);
            retour.relationList = findRelationOfNodeWithRelationName(nodeChoisi, relationName);
            for (Relation r : retour.relationList) {
                if (relationName.equals(r.getName())) {
                    Node nodeVoisine = r.getNode1().equals(nodeChoisi) ? r.getNode2() : r.getNode1();
                    if (!retour.nodeExist(nodeVoisine.getName())) {
                        retour.nodeList.add(nodeVoisine);
                    }
                }
            }
            return retour;
        }
        return null;
    }

    /**
     * @param id String
     * @return boolean
     */
    public boolean nodeExist(String id) {
        for (Node n : this.nodeList) {
            if (n.getName().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param id String
     * @return boolean
     */
    public boolean relationExist(String relationName) {
        for (Relation r : this.relationList) {
            if (r.getName().equals(relationName)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Relation> findAllRelationOfNode(Node n) {
        ArrayList<Relation> retour = new ArrayList();
        for (Relation r : relationList) {
            if (r.getNode1().equals(n) || r.getNode2().equals(n)) {
                retour.add(r);
            }
        }
        return retour;
    }

    /**
     * Trouver tous les noeuds
     *
     * @param relationName
     * @return
     */
    public ArrayList<Relation> findRelationOfNodeWithRelationName(Node n, String relationName) {
        ArrayList<Relation> retour = new ArrayList();
        for (Relation r : relationList) {
            if ((r.getNode1().equals(n) || r.getNode2().equals(n)) && r.getName().equals(relationName)) {
                retour.add(r);
            }
        }
        return retour;
    }
}
