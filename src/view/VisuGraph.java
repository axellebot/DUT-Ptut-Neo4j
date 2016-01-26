package view;


import model.Data;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
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

    public VisuGraph(Data data) {
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
            graph.addEdge(r.getName(), r.getNode1().getName(), r.getNode2().getName());
            //graph.getEdge(r.getName()).setAttribute("iu.label", "test");
        }
        for (Edge e : graph.getEachEdge()) {
            e.setAttribute("iu.label", "test");
        }
        String styleNode =
                "node {" +
                        "size: 50px;" +
                        "shape: box;" +
                        "fill-color: rgb(51,51,51);" +
                        "stroke-mode: plain;" +
                        "stroke-color: yellow; " +
                        "text-color: blue;" +
                        "text-alignment: above;" +
                        "text-size: 20px;" +
                        "}" +
                        "edge{" +
                        "text-mode: normal;" +
                        "text-alignment:along;" +
                        "}";
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
