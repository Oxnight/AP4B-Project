import java.util.*;

public class Main {
    public static void main(String[] args) {
        UVManager uvManager = new UVManager();
        UV mt3f = new UV("MT3F", "Cyril Godey le GOAT");
        uvManager.ajouterUV(mt3f);
        mt3f.ajouterProbleme("1 + 1 ?", "2");
        mt3f.ajouterProbleme("2 + 2 ?", "4");
        mt3f.ajouterProbleme("3 + 3 ?", "6");
        mt3f.ajouterProbleme("4 + 4 ?", "8");

        UV sq20 = new UV("SQ20", "Cyril Godey toujours le GOAT");
        uvManager.ajouterUV(sq20);
        sq20.ajouterProbleme("1 + 1 ?", "2");
        sq20.ajouterProbleme("2 + 2 ?", "4");
        sq20.ajouterProbleme("3 + 3 ?", "6");
        sq20.ajouterProbleme("4 + 4 ?", "8");

        Eleve oui = new Eleve(uvManager);
        oui.identification();
        uvManager.selectionnerUV(oui);

        String enonce = uvManager.selectionnerProbleme(oui.getCurrentUV());

        Problemes probleme = new Problemes(uvManager, oui);
        probleme.setEnonce(enonce);

        Scanner scanner = new Scanner(System.in);
        boolean continuer = true;

        while (continuer) {
            System.out.println("Que souhaitez-vous faire ?");
            System.out.println("1. Interroger la machine pour des indices.");
            System.out.println("2. Emettre une hypothese (il vous reste " + probleme.getTentativesRestantes() + " tentatives.)");
            System.out.println("3. Quitter.");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    // Logic for getting hints (not implemented)
                    break;
                case 2:
                    probleme.effectuerHypothese();
                    break;
                case 3:
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }

        System.out.println("Merci d'avoir utilisé le programme.");
    }
}