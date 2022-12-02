import CustomExceptions.*;

import java.util.*;

public class Graph {
    private ArrayList<Node> nodes = new ArrayList<>();

    private ArrayList<Arrete> arretes = new ArrayList<>();

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public ArrayList<Arrete> getArretes() {
        return arretes;
    }

    public Node addNode(String label) throws SommetExisteDejaException {
        if (this.hasNode(label)) {
            throw new SommetExisteDejaException();
        }

        Node newNode = new Node(label);
        nodes.add(newNode);
        return newNode;
    }

    public void removeNode(String label) throws SommetInexistantException, ArreteInexistanteException {
        Node n = getNode(label);
        if (n == null) {
            throw new SommetInexistantException();
        }

        nodes.remove(n);
        for (Node node : nodes) {
            node.removeAdjacent(n);
        }
        arretes.removeIf(a -> a.getExtrimtes().contains(n));
    }

    public void addEdge(String node1Label, String node2Label, int poid)
            throws SommetInexistantException, ArreteExisteDejaException {

        Node node1 = getNode(node1Label);
        Node node2 = getNode(node2Label);

        node1.addAdjacent(node2);
        node2.addAdjacent(node1);
        Arrete arrete = new Arrete(poid, node1, node2);
        if(arretes.contains(arrete))
            throw new ArreteExisteDejaException();
        arretes.add(arrete);
    }

    public void addEdge(Arrete a) throws ArreteExisteDejaException {
        if(arretes.contains(a))
            throw new ArreteExisteDejaException();

        Node firstNode = a.getExtrimtes().get(0);
        Node secondNode = a.getExtrimtes().get(1);
        firstNode.addAdjacent(secondNode);
        secondNode.addAdjacent(firstNode);
        arretes.add(a);
    }

    public void removeEdge(String node1Label, String node2Label)
            throws SommetInexistantException, ArreteInexistanteException {

        Node node1 = getNode(node1Label);
        Node node2 = getNode(node2Label);

        node1.removeAdjacent(node2);
        node2.removeAdjacent(node1);

        arretes.removeIf(a->a.getExtrimtes().containsAll(new ArrayList<Node>() {
            {
                add(node1);
                add(node2);
            }
        }));
    }

    public boolean hasNode(String label) {
        boolean result = false;
        for (Node n : nodes) {
            if (n.getLabel().equals(label)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public Node getNode(String label) throws SommetInexistantException {
        for (Node n : nodes) {
            if (n.getLabel().equals(label)) {
                return n;
            }
        }
        throw new SommetInexistantException("Le sommet " + label + " n'existe pas");
    }

    public Arrete findArreteByExtrimites(Node n1, Node n2) throws ArreteInexistanteException {
        for (Arrete a : arretes) {
            if (a.getExtrimtes().containsAll(new ArrayList<Node>() {
                {
                    add(n1);
                    add(n2);
                }
            })) {
                return a;
            }
        }
        throw new ArreteInexistanteException();
    }

    public int getNodesCount() {
        return nodes.size();
    }

    public int getNumberOfVisitableNodes(Node startingNode){

        ArrayList<Node> visitedNodes = new ArrayList<Node>();

        Queue<Node> queue = new LinkedList<Node>();

        visitedNodes.add(startingNode);
        queue.add(startingNode);

        int numberOfVisitedNodes = 0;

        while (!queue.isEmpty()) {
            Node currentNode = queue.remove();
            numberOfVisitedNodes++;

            for (Node adjacent : currentNode.getAdjacents()) {
                if (!visitedNodes.contains(adjacent)) {
                    visitedNodes.add(adjacent);
                    queue.add(adjacent);
                }
            }
        }

        return numberOfVisitedNodes;

    }

    public void showNodes(){

        for (Node n : nodes) {
            System.out.print(n.getLabel() + " ");
        }
        System.out.println("");
    }

    public void showEdges() {

        for (Arrete a: arretes) {
            System.out.print(a.getExtrimtes().get(0).getLabel());
            System.out.print("--(" + a.getPoid() + ")--");
            System.out.print(a.getExtrimtes().get(1).getLabel() + ", ");
        }
        System.out.println("");

    }

    public boolean graphIsConnexe(){
        int numberOfVisitableNodes = getNumberOfVisitableNodes(nodes.get(0));
        return numberOfVisitableNodes == getNodesCount();
    }

    public void findCycleHamiltonien() throws Exception {

        Cycle cycle = new Cycle(this);

        double start = System.nanoTime();
        cycle.findCycleHamiltonienBruteForce();
        double elapsedTime = (System.nanoTime() - start)/ 1e6;;
        System.out.println("Temps avec la methode du backtracking : " + elapsedTime +" ms");
        System.out.println();
        start = System.nanoTime();
        cycle.findCycleHamiltonienKruskal();
        elapsedTime = (System.nanoTime() - start)/ 1e6;
        System.out.println("Temps avec la methode de l'heuristic (Kruskal) : " + elapsedTime +" ms");
        System.out.println();
    }

}
