package CustomExceptions;

public class SommetExisteDejaException extends Exception{
    public SommetExisteDejaException(){
        super("Ce sommet existe deja");
    }
}
