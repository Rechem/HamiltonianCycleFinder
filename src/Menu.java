import CustomExceptions.ChoixInvalideException;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.exit;

public class Menu {

    public static void main(String[] args) {
        GraphManager graphManager = GraphManager.getGraphManager();


        while (true){
            graphManager.showNodesHandler();
            graphManager.showEdgesHandler();
            showMenu();

            String input = ScannerClass.getScanner().nextLine();

            try {
                execute(input);
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public static void showMenu(){
        System.out.println("1 - Ajouter un sommet");
        System.out.println("2 - Supprimer un sommet");
        System.out.println("3 - Ajouter une arrete");
        System.out.println("4 - Supprimer une arrete");
        System.out.println("========================");
        System.out.println("10 - Chercher un cycle hamiltonien");
        System.out.println("50 - Run test");
        System.out.println("99 - Quiter");
    }

    public static void execute(String input) throws Exception {
        switch (input){
            case "1":
                GraphManager.getGraphManager().addNodeHandler();
                break;
            case "2":
                GraphManager.getGraphManager().removeNodeHandler();
                break;
            case "3":
                GraphManager.getGraphManager().addEdgeHandler();
                break;
            case "4":
                GraphManager.getGraphManager().removeEdgeHandler();
                break;
            case "10":
                GraphManager.getGraphManager().findCycleHamiltonienHandler();
                break;
            case "50":
                GraphManager.getGraphManager().runTest();
                break;
            case "99":
                exit(0);
                break;
            default:
                throw new ChoixInvalideException();
        }
    }

}