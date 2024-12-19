import javax.swing.*;
import java.util.*;

public class MainGUI {
    public static void main(String[] args) {
        UVManager uvManager = new UVManager();
        Eleve eleve = new Eleve(uvManager);

        // Ajout des UV et des problèmes
        UV mt3f = new UV("MT3F", "Cyril Godey le GOAT");
        uvManager.ajouterUV(mt3f);
        mt3f.ajouterProbleme("Calculer le déterminant de la matrice " + Arrays.deepToString(new int[][]{{9, 7, 5}, {3, 6, 8}, {2, 4, 1}}), "-143");
        mt3f.ajouterProbleme("I = ∫ 𝒙 ∕ (𝒙^2+1) 𝑑𝒙", "1/2*ln(x^2+1)+c");
        mt3f.ajouterProbleme("P = ∏ avec 𝒏 allant de 1 à 30 de (1 + 1/(2𝒏-1)) arrondi à 2 décimales au supérieur", "9.75");
        mt3f.ajouterProbleme("∑ de 𝒏 allant de 1 à 100 de (1 + 1/𝒏) arrondi à 2 décimales au supérieur", "105.19");

        UV pc20 = new UV("PC20", "On fait du MasterMind ici, vient c'est cool");
        uvManager.ajouterUV(pc20);
        pc20.ajouterProbleme("Mastermind n°1", "0427");
        pc20.ajouterProbleme("Mastermind n°2", "4629");
        pc20.ajouterProbleme("Mastermind n°3", "6623");
        pc20.ajouterProbleme("Mastermind n°4", "5193");

        // Lancement de l'interface graphique
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI(uvManager, eleve);
            }
        });
    }
}
