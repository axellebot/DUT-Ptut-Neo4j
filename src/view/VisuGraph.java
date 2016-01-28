package view;


import model.Data;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

import javax.swing.*;
import java.awt.*;

public class VisuGraph extends JPanel implements ViewerListener {

    protected boolean loop = true;
    Graph graph;
    Viewer viewer;
    View view;
    ViewerPipe fromViewer;
    int id;
    boolean showLabels;
    Data data;

    public VisuGraph(Data data) {
        showLabels = false;
        id = 1;
        setVisible(true);
        setPreferredSize(new Dimension(950, 550));
        this.setLayout(new BorderLayout());
        graph = new MultiGraph("Graph");
        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        //Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        view = viewer.addDefaultView(false);   // false indicates "no JFrame".
        this.add((Component) view);
        //view.requestFocus();
        this.revalidate();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);

        // We connect back the viewer to the graph,
        // the graph becomes a sink for the viewer.
        // We also install us as a viewer listener to
        // intercept the graphic events.
        //Viewer viewer = graph.display();
        fromViewer = viewer.newViewerPipe();
        fromViewer.addViewerListener(this);
        fromViewer.addSink(graph);
        update(data);
    }

    public void update(Data data) {
        graph.clear();
        this.data = data;
        for (model.Node n : data.getNodeList()) {
            graph.addNode(n.getName());
            String label = n.getName();
            if (!n.getLabels().isEmpty() && showLabels) {
                label += " : " + n.getLabels().get(0);
            }
            graph.getNode(n.getName()).setAttribute("ui.label", label);
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
        graph.addAttribute("ui.stylesheet", styleNode);
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
        /*
        if (graph != null) {
            this.removeAll();
            viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
            //Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
            viewer.enableAutoLayout();
            view = viewer.addDefaultView(false);   // false indicates "no JFrame".
            this.add((Component) view);
            //view.requestFocus();
            this.revalidate();
            viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);

            // We connect back the viewer to the graph,
            // the graph becomes a sink for the viewer.
            // We also install us as a viewer listener to
            // intercept the graphic events.
            //Viewer viewer = graph.display();
            fromViewer = viewer.newViewerPipe();
            fromViewer.addViewerListener(this);
            fromViewer.addSink(graph);

            // Then we need a loop to do our work and to wait for events.
            // In this loop we will need to call the
            // pump() method before each use of the graph to copy back events
            // that have already occurred in the viewer thread inside
            // our thread.
            */
        System.out.println("fin update VisuGraph");


        //}

    }

    public void viewClosed(String id) {
        loop = false;
    }

    public void buttonPushed(String id) {

        if(!data.getNodeByName(id).getLabels().isEmpty()) {
            graph.getNode(id).setAttribute("ui.label", id + " : " + data.getNodeByName(id).getLabels().get(0));
        }
        System.out.println("Button pushed on node " + id);
    }

    public void buttonReleased(String id) {
            graph.getNode(id).setAttribute("ui.label", id);
        System.out.println("Button released on node " + id);
    }

    public void loop() {
        while (loop) {
            try {
                fromViewer.blockingPump(); // or fromViewer.blockingPump(); in the nightly builds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // here your simulation code.

        // You do not necessarily need to use a loop, this is only an example.
        // as long as you call pump() before using the graph. pump() is non
        // blocking.  If you only use the loop to look at event, use blockingPump()
        // to avoid 100% CPU usage. The blockingPump() method is only available from
        // the nightly builds.
    }

    public void update2() {
        view.requestFocus();
    }

    public void setGraphe(Graph g) {
        this.graph = g;
    }

    public boolean isShowLabels() {
        return showLabels;
    }

    public void setShowLabels(boolean showLabels) {
        this.showLabels = showLabels;
    }
}
