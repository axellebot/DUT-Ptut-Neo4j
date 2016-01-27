package view;


import model.Data;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Milihhard on 26/01/2016.
 */
public class VisuGraph extends JPanel {
    Graph graph;
    Viewer viewer;
    View view;
    int id;

    public VisuGraph(Data data) {
        id = 1;
        setVisible(true);
        setPreferredSize(new Dimension(950, 550));
        this.setLayout(new BorderLayout());

        update(data);
        //graph.display();
    }

    public void update(Data data) {
        graph = new SingleGraph("Graph");
        for (model.Node n : data.getNodeList()) {
            graph.addNode(n.getName());
            graph.getNode(n.getName()).setAttribute("ui.label", n.getName());
        }
        for (model.Relation r : data.getRelationList()) {
            graph.addEdge(Integer.toString(id), r.getNode1().getName(), r.getNode2().getName());
            graph.getEdge(Integer.toString(id)).setAttribute("ui.label", r.getName());
            id++;
        }
        final String styleNode =
                "node{\n" +
                        "shape: cross; size: 50px; fill-color: rgb(51,51,51);\n" +
                        "text-alignment: center; text-color: blue; text-size: 20px;\n" +
                        "}\n" +
                        "edge{\n" +
                        "text-mode: normal; text-alignment: along; text-color: blue; text-size: 18px;\n" +
                        "}\n";

        System.out.println(styleNode);
        graph.addAttribute("ui.stylesheet", styleNode);
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
        if (graph != null) {
            this.removeAll();
            viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
            //Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
            viewer.enableAutoLayout();
            view = viewer.addDefaultView(false);   // false indicates "no JFrame".
            this.add((Component) view);
            //view.requestFocus();
            this.revalidate();
        }
        System.out.println("fin update VisuGraph");
    }

    public void update2() {
        view.requestFocus();
    }

    public void setGraphe(Graph g) {
        this.graph = g;
    }
}
