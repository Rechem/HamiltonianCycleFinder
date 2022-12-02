import CustomExceptions.SommetInexistantException;

import java.util.ArrayList;
import java.util.Objects;

public class Arrete implements Comparable<Arrete> {
    private int poid;
    private ArrayList<Node> extrimtes = new ArrayList<>();

    public Arrete(int poid, Node node1, Node node2){
        this.poid = poid;
        extrimtes.add(node1);
        extrimtes.add(node2);
    }

    public int getPoid() {
        return poid;
    }

    public ArrayList<Node> getExtrimtes() {
        return extrimtes;
    }

    public Node getOtherExtrimte(Node extrimte) throws SommetInexistantException {
        if(extrimte.equals(extrimtes.get(0)))
            return extrimtes.get(1);
        else if(extrimte.equals(extrimtes.get(1)))
            return extrimtes.get(0);
        else
            throw new SommetInexistantException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arrete arrete = (Arrete) o;
        return extrimtes.containsAll(arrete.extrimtes) && this.poid == arrete.poid;
    }

    @Override
    public int compareTo(Arrete o) {
        return this.poid - o.poid;
    }
}
