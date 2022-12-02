package CustomExceptions;

public class ArreteInexistanteException extends Exception{
    public ArreteInexistanteException(){
        super("Cette arrete n'existe pas");
    }

    public ArreteInexistanteException(String message){
        super(message);
    }
}
