package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class Json {
    public Data data;
    private String filePath;

    public Json() {
        this.data = new Data();
    }

    public Json(String filePath) {
        this.filePath = filePath;
    }

    public Json(Data data) {
        this.data = data;
    }

    public void read() {
        JSONParser parser = new JSONParser();
        try {
            System.out.println("Reading JSON file from Java program");
            FileReader fileReader = new FileReader(this.filePath);

            JSONObject json = (JSONObject) parser.parse(fileReader);

            JSONArray arrayNodes = (JSONArray) json.get("nodes");
            JSONArray arrayRelations = (JSONArray) json.get("relations");

            System.out.println("-------------Nodes-------------");
            //nodes
            String name = "";
            String parameters = "";
            String labels = "";
            for (int i = 0; i < arrayNodes.size(); i++) {
                JSONObject node = (JSONObject) arrayNodes.get(i);
                JSONArray arrayParameters = (JSONArray) node.get("parameters");
                JSONArray arrayLabels = (JSONArray) node.get("labels");

                name = node.get("name").toString();
                System.out.println(name);

                parameters = "";
                for (int j = 0; j < arrayParameters.size(); j++) {
                    parameters += arrayParameters.get(j).toString() + " ";
                }
                System.out.println("Paremeters : {" + parameters + "}");

                labels = "";
                for (int j = 0; j < arrayLabels.size(); j++) {
                    labels += arrayLabels.get(j).toString() + " ";
                }
                System.out.println("Labels : {" + labels + "}");
            }


            System.out.println("-------------Relations-------------");
            //relations
            String relationName = "";
            String nodesNames = "";
            for (int i = 0; i < arrayRelations.size(); i++) {
                JSONObject relation = ((JSONObject) arrayRelations.get(i));
                relationName = relation.get("name").toString();
                System.out.println(relationName);
                nodesNames = relation.get("node1").toString() + ", ";
                nodesNames += relation.get("node2").toString();
                System.out.println("Nodes : {" + nodesNames + "}");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void extract() {

    }

    public void export() {
        if (this.data != null) {

        }
    }
}
