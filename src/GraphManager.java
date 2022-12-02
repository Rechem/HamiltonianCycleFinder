import CustomExceptions.ArreteExisteDejaException;
import CustomExceptions.GrapheNonConnexeException;
import CustomExceptions.SommetExisteDejaException;

import java.util.ArrayList;
import java.util.Collections;

public class GraphManager {

    private static GraphManager instance;
    private Graph graph;

    public static GraphManager getGraphManager() {
        if (instance == null) {
            instance = new GraphManager();
            instance.graph = new Graph();
        }
        return instance;
    }

    public void showNodesHandler() {
        if (graph.getNodesCount() == 0) {
            System.out.println("Le graphe est vide");
            return;
        }

        System.out.println("Liste des sommets :");
        graph.showNodes();
    }

    public void showEdgesHandler() {
        if (graph.getNodesCount() == 0) {
            return;
        }
        System.out.println("Liste des arretes :");
        graph.showEdges();
    }

    public void addNodeHandler() {
        System.out.println("Nom du sommet (label) : ");
        String label = ScannerClass.getScanner().nextLine();
        try {
            graph.addNode(label);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeNodeHandler(){
        System.out.println("Nom du sommet (label) : ");
        String label = ScannerClass.getScanner().nextLine();
        try {
            graph.removeNode(label);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addEdgeHandler() {
        System.out.println("Nom du premier sommet : ");
        String label1 = ScannerClass.getScanner().nextLine();

        System.out.println("Nom du deuxieme sommet : ");
        String label2 = ScannerClass.getScanner().nextLine();

        System.out.println("Poid : ");
        int poid = ScannerClass.getScanner().nextInt();

        try {
            graph.addEdge(label1, label2, poid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeEdgeHandler() {
        System.out.println("Nom du premier sommet : ");
        String label1 = ScannerClass.getScanner().nextLine();

        System.out.println("Nom du deuxieme sommet : ");
        String label2 = ScannerClass.getScanner().nextLine();

        try {
            graph.removeEdge(label1, label2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void findCycleHamiltonienHandler() throws Exception {

        graph.findCycleHamiltonien();
    }

    public void runTest(){
        try {
            graph.addNode("A");
            graph.addNode("B");
            graph.addNode("C");
            graph.addNode("D");
            graph.addEdge("A", "B", 4);
            graph.addEdge("A", "C", 2);
            graph.addEdge("A", "D", 1);
            graph.addEdge("B", "C", 13);
            graph.addEdge("B", "D", 9);
            graph.addEdge("D", "C", 8);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
