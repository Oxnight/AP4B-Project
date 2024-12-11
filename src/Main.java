import java.util.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        UVManager uvManager = new UVManager();
        Eleve oui = new Eleve(uvManager);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI(uvManager, oui);
            }
        });


        UV mt3f = new UV("MT3F", "Cyril Godey le GOAT");
        uvManager.ajouterUV(mt3f);
        mt3f.ajouterProbleme(Arrays.deepToString(new int[][]{{9, 7, 5}, {3, 6, 8}, {2, 4, 1}}), "-143");
        mt3f.ajouterProbleme("I = ∫ 𝒙 ∕ (𝒙^2+1) 𝑑𝒙", "1/2*ln(x^2+1)+c");
        mt3f.ajouterProbleme("P = ∏ avec 𝒏 allant de 1 à 30 de (1 + 1/(2𝒏-1)) arrondi à 2 décimales au supérieur", "9,75");

        UV pc20 = new UV("PC20", "On fait du MasterMind ici, vient c'est cool");
        uvManager.ajouterUV(pc20);
        pc20.ajouterProbleme("Mastermind n°1", "0427");
        pc20.ajouterProbleme("Mastermind n°2", "4629");
        pc20.ajouterProbleme("Mastermind n°3", "6623");
        pc20.ajouterProbleme("Mastermind n°4", "5193");

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
                    probleme.interroger();
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
