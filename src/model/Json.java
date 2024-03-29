package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Json {

    /**
     * Constructor
     */
    public Json() {
    }

    public static void read(String inputFilePath) {
        JSONParser parser = new JSONParser();
        try {
            System.out.println("Reading JSON file from Java program");
            FileReader fileReader = new FileReader(inputFilePath);

            JSONObject json = (JSONObject) parser.parse(fileReader);

            JSONArray arrayNodes = (JSONArray) json.get("nodes");
            JSONArray arrayRelations = (JSONArray) json.get("relations");

            System.out.println("-------------Nodes-------------");
            //nodes
            String name = "";
            String properties = "";
            String labels = "";
            for (Object nodeJson : arrayNodes) {
                JSONObject node = (JSONObject) nodeJson;
                JSONArray arrayProperties = (JSONArray) node.get("properties");
                JSONArray arrayLabels = (JSONArray) node.get("labels");

                name = (String) node.get("name");
                System.out.println(name);

                properties = "";
                for (Object propertiesJson : arrayProperties) {
                    properties += propertiesJson + " ";
                }
                System.out.println("Properties : {" + properties + "}");

                labels = "";
                for (Object labelJson : arrayLabels) {
                    labels += labelJson + " ";
                }
                System.out.println("Labels : {" + labels + "}");
            }


            System.out.println("-------------Relations-------------");
            //relations
            String relationName = "";
            String nodesNames = "";
            for (Object relationJson : arrayRelations) {
                JSONObject relation = ((JSONObject) relationJson);
                relationName = (String) relation.get("name");
                System.out.println(relationName);
                nodesNames = relation.get("node1") + ", ";
                nodesNames += (String) relation.get("node2");
                System.out.println("Nodes : {" + nodesNames + "}");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Data extract(String inputFilePath) {
        ArrayList<Node> nodeList = null;
        ArrayList<Relation> relationList = null;
        JSONParser parser = new JSONParser();

        System.out.println("Extracting JSON file to Java program");

        try {
            FileReader fileReader = new FileReader(inputFilePath);

            JSONObject json = (JSONObject) parser.parse(fileReader);
            JSONArray arrayNodes = (JSONArray) json.get("nodes");
            JSONArray arrayRelations = (JSONArray) json.get("relations");

            //-------------Nodes-------------
            nodeList = new ArrayList<>();
            for (Object nodeJson : arrayNodes) {
                JSONObject node = (JSONObject) nodeJson;
                String nodeName = (String) node.get("name");
                ArrayList<String> listProperties = (JSONArray) node.get("properties");
                ArrayList<String> listLabels = (JSONArray) node.get("labels");
                nodeList.add(new Node(nodeName, listLabels, listProperties));
            }

            //-------------Relations-------------
            relationList = new ArrayList<>();
            for (Object relationJson : arrayRelations) {
                JSONObject relation = (JSONObject) relationJson;
                String relationName = (String) relation.get("name");
                Node node1 = Node.findNodeInNodeList(nodeList, (String) relation.get("node1"));
                Node node2 = Node.findNodeInNodeList(nodeList, (String) relation.get("node2"));
                relationList.add(new Relation(relationName, node1, node2));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new Data(nodeList, relationList);
    }


    public static void export(Data data, String outputFilePath) {
        if (data != null) {
            System.out.println(outputFilePath);
            JSONObject json = new JSONObject();
            JSONArray arrayNodes = new JSONArray();
            JSONArray arrayRelations = new JSONArray();
            for (Node n : data.getNodeList()) {
                JSONObject node = new JSONObject();
                node.put("name", n.getName());

                JSONArray arrayLabels = new JSONArray();
                JSONArray arrayProperties = new JSONArray();
                for (String label : n.getLabels()) {
                    arrayLabels.add(label);
                }
                for (String properties : n.getProperties()) {
                    arrayProperties.add(properties);
                }
                node.put("name", n.getName());
                node.put("labels", arrayProperties);
                node.put("properties", arrayProperties);

                arrayNodes.add(node);
            }

            json.put("nodes", arrayNodes);
            for (Relation n : data.getRelationList()) {
                JSONObject relation = new JSONObject();
                relation.put("name", n.getName());
                relation.put("node1", n.getNode1().getName());
                relation.put("node2", n.getNode2().getName());
                arrayRelations.add(relation);
            }
            json.put("relations", arrayRelations);

            try {
                System.out.println("Writting JSON into file ...");
                FileWriter jsonFileWriter = new FileWriter(outputFilePath);
                jsonFileWriter.write(json.toJSONString());
                jsonFileWriter.flush();
                jsonFileWriter.close();
                System.out.println("Done");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
