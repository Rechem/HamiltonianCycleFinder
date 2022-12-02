package CustomExceptions;

public class GrapheNonConnexeException extends Exception{
    public GrapheNonConnexeException(){
        super("Cette arrete existe deja");
    }

    public GrapheNonConnexeException(String message){
        super(message);
    }
}
