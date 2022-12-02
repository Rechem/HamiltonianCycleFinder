import java.util.Scanner;

public class ScannerClass {

    private static Scanner scanner;

    private static synchronized Scanner initializeInstance(){
        if(scanner == null){
            scanner = new Scanner(System.in);
        }
        return scanner;
    }

    public static Scanner getScanner(){
        if(scanner == null){
            scanner = initializeInstance();
        }
        return scanner;
    }
}
