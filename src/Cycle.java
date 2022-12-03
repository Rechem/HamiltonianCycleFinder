import CustomExceptions.ArreteInexistanteException;
import CustomExceptions.GrapheNonConnexeException;
import CustomExceptions.SommetExisteDejaException;
import CustomExceptions.SommetInexistantException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cycle {

    private final Graph graph;
    //all of the variables below are only useful for the backtkhacking method
    private ArrayList<Node> visitedNodes;
    private ArrayList<Arrete> tempArretes;
    private ArrayList<Arrete> minCycleArretes;
    private int coutCycle = -1;
    private Node startingNode;
    private int numberOfVisitedNodes;

    public Cycle(Graph graph) {
        this.graph = graph;
    }

    public void findCycleHamiltonienKruskal() throws Exception {
        if (!graph.graphIsConnexe())
            throw new GrapheNonConnexeException();

        int numberOfVisitableNodes = graph.getNodesCount();
        // ascending order by weight
        Collections.sort(graph.getArretes());

        Graph tempGraph = new Graph();
        boolean isFound = false;

        for (Arrete a : graph.getArretes()) {
            if (tempGraph.getNodes().containsAll(a.getExtrimtes())) {
                //C'est un cycle
                if (tempGraph.getNodesCount() == numberOfVisitableNodes) {
                    tempGraph.addEdge(a);
                    isFound = true;
                }
            } else {
                //Ce n'est pas un cycle
                //Si ce boolean est a faux, cela veut dire que le degree d'un somet devient 3
                boolean edgeIsEligible = true;

                for (Node n : a.getExtrimtes()) {
                    try {
                        if (tempGraph.getNode(n.getLabel()).getDegree() == 2) {
                            edgeIsEligible = false;
                            break;
                        }
                    } catch (SommetInexistantException e) {
                        //All good <3
                    }

                }

                if (!edgeIsEligible) {
                    continue;
                }

                ArrayList<Node> extrimites = new ArrayList<>();

                for (Node n : a.getExtrimtes()) {
                    try {
                        if (tempGraph.hasNode(n.getLabel())) {
                            extrimites.add(tempGraph.getNode(n.getLabel()));
                        } else {
                            extrimites.add(tempGraph.addNode(n.getLabel()));
                        }
                    } catch (SommetExisteDejaException e) {
                        //No worries <3
                    }
                }
                //on sauvgarde l'arrete
                Arrete arreteCopy = new Arrete(a.getPoid(), extrimites.get(0), extrimites.get(1));
                tempGraph.addEdge(arreteCopy);
            }
        }

        if (!isFound) {
            System.out.println("Pas de cycle Hamiltonian trouve avec l'heuristic Kruskal.");
        } else {
            //Affichage
            System.out.println("Le cycle hamiltonien trouve avec l'heuristic Kruskal :");
            afficherCycle(tempGraph.getArretes(), tempGraph.getNodesCount());
        }

    }

    public void findCycleHamiltonienBruteForce() throws Exception {

        //init
        if (!graph.graphIsConnexe())
            throw new GrapheNonConnexeException();

        visitedNodes = new ArrayList<>();
        tempArretes = new ArrayList<>();
        minCycleArretes = new ArrayList<>();
        numberOfVisitedNodes = 0;

        try {
            startingNode = graph.getNodes().get(0);
            backtrackingCycleHamiltonien(startingNode);
            if(!minCycleArretes.isEmpty()){
                System.out.println("Le cycle hamiltonien trouve avec la methode du backtracking:");
                afficherCycle(minCycleArretes, graph.getNodesCount());
            }else{
                System.out.println("Pas de cycle hamiltonien trouve avec la methode du backtracking");
            }

        } catch (ArreteInexistanteException e) {
            System.out.println("Erreur fatal lors du backtkhacking");
        }
    }

    private void backtrackingCycleHamiltonien(Node node) throws Exception {
        visitedNodes.add(node);
        numberOfVisitedNodes++;

        for (Node adjacent : node.getAdjacents()) {
            if (!visitedNodes.contains(adjacent)) {

                Arrete a = graph.findArreteByExtrimites(node, adjacent);
                tempArretes.add(a);

                backtrackingCycleHamiltonien(adjacent);

                tempArretes.remove(a);

            } else if (numberOfVisitedNodes == graph.getNodesCount() && startingNode.equals(adjacent)) {
                //affichage

                Arrete derniereArette = graph.findArreteByExtrimites(node, adjacent);
                tempArretes.add(derniereArette);

                int coutNewCycle = getCoutCycle(tempArretes);

                if (this.coutCycle == -1 || coutNewCycle < this.coutCycle) {
                    this.coutCycle = coutNewCycle;
                    this.minCycleArretes = (ArrayList<Arrete>) tempArretes.clone();
                }

                tempArretes.remove(derniereArette);
                break;
            }
        }
        visitedNodes.remove(node);
        numberOfVisitedNodes--;
    }

    private static int getCoutCycle(List<Arrete> arretesList) {
        int cout = 0;
        for (Arrete a : arretesList) {
            cout += a.getPoid();
        }
        return cout;
    }

    private static void afficherCycle(List<Arrete> arretesList, int nodesCount) throws SommetInexistantException {

        Arrete edge = arretesList.get(0);
        Node currentNode = edge.getExtrimtes().get(0);
        Node nextNode = null;
        int numberOfVisitedNodes = 1;
        int cout = edge.getPoid();

        System.out.print(currentNode.getLabel());

        while (true) {
            nextNode = edge.getOtherExtrimte(currentNode);
            System.out.print("--(" + edge.getPoid() + ")--");
            System.out.print(nextNode.getLabel());

            numberOfVisitedNodes++;
            if (numberOfVisitedNodes == nodesCount + 1) {
                break;
            }

            for (Arrete a : arretesList) {
                if (a.getExtrimtes().contains(nextNode) && !a.getExtrimtes().contains(currentNode)) {
                    edge = a;
                    cout += edge.getPoid();
                    currentNode = nextNode;
                    break;
                }
            }
        }
        System.out.print("; Cout : " + cout);
        System.out.println();
    }
}
