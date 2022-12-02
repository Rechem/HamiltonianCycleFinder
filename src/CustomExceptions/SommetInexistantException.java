package CustomExceptions;

public class SommetInexistantException extends Exception{
    public SommetInexistantException(){
        super("Ce sommet n'existe pas");
    }

    public SommetInexistantException(String message){
        super(message);
    }
}
