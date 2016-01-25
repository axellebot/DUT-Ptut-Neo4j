package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Json {

    /**
     * Constructor
     */
    public Json() {
    }

    public void read() {
        JSONParser parser = new JSONParser();
        String inputFilePath = ".\\model.json";
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
            for (int i = 0; i < arrayNodes.size(); i++) {
                JSONObject node = (JSONObject) arrayNodes.get(i);
                JSONArray arrayProperties = (JSONArray) node.get("properties");
                JSONArray arrayLabels = (JSONArray) node.get("labels");

                name = (String) node.get("name");
                System.out.println(name);

                properties = "";
                for (int j = 0; j < arrayProperties.size(); j++) {
                    properties += arrayProperties.get(j) + " ";
                }
                System.out.println("Properties : {" + properties + "}");

                labels = "";
                for (int j = 0; j < arrayLabels.size(); j++) {
                    labels += arrayLabels.get(j) + " ";
                }
                System.out.println("Labels : {" + labels + "}");
            }


            System.out.println("-------------Relations-------------");
            //relations
            String relationName = "";
            String nodesNames = "";
            for (int i = 0; i < arrayRelations.size(); i++) {
                JSONObject relation = ((JSONObject) arrayRelations.get(i));
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

    public void extract(Data data) {
    }

    public void export(Data data) {
        if (data != null) {
            String outputFilePath = "./save/save-" + new java.util.Date().getTime() + ".json";

            System.out.println(outputFilePath);
            JSONObject json = new JSONObject();
            JSONArray arrayNodes = new JSONArray();
            JSONArray arrayRelations = new JSONArray();

            for (int i = 0; i < data.getNodeList().size(); i++) {
                JSONObject node = new JSONObject();
                node.put("name", data.getNodeList().get(i).getName());

                JSONArray arrayLabels = new JSONArray();
                JSONArray arrayProperties = new JSONArray();
                for (int j = 0; j < data.getNodeList().get(i).getLabels().size(); j++) {
                    arrayLabels.add(data.getNodeList().get(i).getLabels().get(j));
                }


                for (int j = 0; j < data.getNodeList().get(i).getProperties().size(); j++) {
                    arrayProperties.add(data.getNodeList().get(i).getProperties().get(j));
                }

                node.put("name", data.getNodeList().get(i).getName());
                node.put("labels", arrayProperties);
                node.put("properties", arrayProperties);

                arrayNodes.add(node);
            }

            json.put("nodes", arrayNodes);
            for (int i = 0; i < data.getRelaList().size(); i++) {
                JSONObject relation = new JSONObject();
                relation.put("name", data.getRelaList().get(i).getName());
                relation.put("node1", data.getRelaList().get(i).getNode1().getName());
                relation.put("node2", data.getRelaList().get(i).getNode2().getName());
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
