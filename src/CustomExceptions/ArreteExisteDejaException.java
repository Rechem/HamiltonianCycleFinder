package CustomExceptions;

public class ArreteExisteDejaException extends Exception{
    public ArreteExisteDejaException(){
        super("Cette arrete existe deja");
    }

    public ArreteExisteDejaException(String message){
        super(message);
    }
}
