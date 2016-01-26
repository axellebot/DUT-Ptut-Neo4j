package view;


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

    public VisuGraph() {
        setVisible(true);
        setPreferredSize(new Dimension(950, 550));
        this.setLayout(new BorderLayout());
        graph = new SingleGraph("Graph");
        for (int i = 0; i < 10; i++) {
            graph.addNode("a" + i);
        }
        for (int i = 0; i < 9; i++) {
            graph.addEdge("a" + (2 * i), "a" + i, "a" + (i + 1));
        }
        String styleNode =
                "node {\n" +
                        "\tsize: 50px;\n" +
                        "\tshape: box;\n" +
                        "\tfill-color: green;\n" +
                        "\tstroke-mode: plain;\n" +
                        "\tstroke-color: yellow;\n" +
                        "}";
        graph.addAttribute("ui.stylesheet", styleNode);
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
        update();
        //graph.display();
    }

    public void update() {
        if (graph != null) {
            try {
                this.remove((Component) viewer.getView(Viewer.DEFAULT_VIEW_ID));
            } catch (Exception ex) {
            }
            viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
                    //Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
            viewer.enableAutoLayout();
            view = viewer.addDefaultView(false);   // false indicates "no JFrame".
            this.add((Component)view);
            view.requestFocus();
        }
    }

    public void update2() {
        view.requestFocus();
    }

    public void setGraphe(Graph g) {
        this.graph = g;
    }
}
