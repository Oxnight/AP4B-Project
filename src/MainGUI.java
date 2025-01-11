import javax.swing.*;
import java.util.*;

/**
 * La classe principale qui démarre l'application en mode interface graphique.
 * Elle initialise les UV (unités de valeurs) et les problèmes associés,
 * puis lance l'interface graphique où l'élève peut résoudre les problèmes.
 */
public class MainGUI {

    /**
     * Méthode principale pour démarrer le programme.
     * Elle initialise les UVs, les problèmes associés et lance l'interface graphique.
     *
     * @param args Les arguments de la ligne de commande (non utilisés ici).
     */
    public static void main(String[] args) {

        // Création du gestionnaire d'UV et de l'élève
        UVManager uvManager = new UVManager();  // Crée une instance du gestionnaire d'UV
        Eleve eleve = new Eleve(uvManager);     // Crée une instance de l'élève en associant le gestionnaire d'UV

        // Ajout de l'UV MT3F et des problèmes associés
        UV mt3f = new UV("MT3F", "Cyril Godey le GOAT");  // Création de l'UV "MT3F" (Mathématiques)
        uvManager.ajouterUV(mt3f);  // Ajoute l'UV au gestionnaire d'UV

        // Ajout des problèmes pour l'UV "MT3F"
        mt3f.ajouterProbleme("Calculer le déterminant de la matrice " + Arrays.deepToString(new int[][]{{9, 7, 5}, {3, 6, 8}, {2, 4, 1}}), "-143");
        mt3f.ajouterProbleme("I = ∫ 𝒙 ∕ (𝒙^2+1) 𝑑𝒙", "1/2*ln(x^2+1)+c");
        mt3f.ajouterProbleme("P = ∏ avec 𝒏 allant de 1 à 30 de (1 + 1/(2𝒏-1)) arrondi à 2 décimales au supérieur", "9.75");
        mt3f.ajouterProbleme("∑ de 𝒏 allant de 1 à 100 de (1 + 1/𝒏) arrondi à 2 décimales au supérieur", "105.19");

        // Ajout de l'UV PC20 et des problèmes associés
        UV pc20 = new UV("PC20", "On fait du MasterMind ici, viens c'est cool");  // Création de l'UV "PC20" (MasterMind)
        uvManager.ajouterUV(pc20);  // Ajoute l'UV "PC20" au gestionnaire

        // Ajout des problèmes pour l'UV "PC20"
        pc20.ajouterProbleme("Mastermind n°1", "0427");
        pc20.ajouterProbleme("Mastermind n°2", "4629");
        pc20.ajouterProbleme("Mastermind n°3", "6623");
        pc20.ajouterProbleme("Mastermind n°4", "5193");

        // Démarrage de l'interface graphique via SwingUtilities pour garantir que l'UI s'exécute sur le bon thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Création de la fenêtre graphique et passage du gestionnaire d'UV et de l'élève à l'interface
                new GUI(uvManager, eleve);
            }
        });
    }
}