import java.util.*;

/**
 * Classe principale qui démarre le programme dans la console. Elle initialise les UV (Unités de Valeur),
 * les problèmes associés à ces UV, puis offre à l'élève un choix pour résoudre des problèmes.
 */
public class Main {

    /**
     * Méthode principale qui initialise les UV et les problèmes associés, puis lance le processus de jeu.
     * Elle interagit avec l'utilisateur via la console pour résoudre des problèmes.
     *
     * @param args Les arguments passés en ligne de commande (non utilisés ici).
     */
    public static void main(String[] args) {

        // Initialisation du gestionnaire d'UV et de l'élève
        UVManager uvManager = new UVManager();  // Crée un gestionnaire d'UV pour gérer les unités de valeur.
        Eleve student = new Eleve(uvManager);      // Crée un objet Eleve, représentant un élève avec le gestionnaire d'UV.

        // Ajout de l'UV "MT3F" et des problèmes associés
        UV mt3f = new UV("MT3F", "Cyril Godey le GOAT");  // Création d'une UV "MT3F".
        uvManager.ajouterUV(mt3f);  // Ajoute l'UV "MT3F" au gestionnaire d'UV.

        // Ajout de problèmes à l'UV "MT3F"
        mt3f.ajouterProbleme(Arrays.deepToString(new int[][]{{9, 7, 5}, {3, 6, 8}, {2, 4, 1}}), "-143");
        mt3f.ajouterProbleme("I = Intégrale de x/(x^2 + 1) dx", "1/2*ln(x^2+1)+c");
        mt3f.ajouterProbleme("P = Somme avec n allant de 1 à 30 de (1 + 1/(2n-1)) arrondi à 2 décimales au supérieur", "9.75");
        mt3f.ajouterProbleme("Produit de n allant de 1 à 100 de (1 + 1/n) arrondi à 2 décimales au supérieur", "105.19");

        // Ajout de l'UV "PC20" et des problèmes associés
        UV pc20 = new UV("PC20", "On fait du MasterMind ici, viens c'est cool");  // Création d'une UV "PC20".
        uvManager.ajouterUV(pc20);  // Ajoute l'UV "PC20" au gestionnaire d'UV.

        // Ajout de problèmes à l'UV "PC20"
        pc20.ajouterProbleme("Mastermind n°1", "0427");
        pc20.ajouterProbleme("Mastermind n°2", "4629");
        pc20.ajouterProbleme("Mastermind n°3", "6623");
        pc20.ajouterProbleme("Mastermind n°4", "5193");

        // Identification de l'élève
        student.identification();  // L'élève s'identifie.

        // Sélection de l'UV par l'élève
        uvManager.selectionnerUV(student);  // L'élève choisit une UV parmi celles disponibles.

        // Récupération de l'énoncé du problème à résoudre pour l'UV sélectionnée
        String enonce = uvManager.selectionnerProbleme(student.getCurrentUV(), false);  // L'élève choisit un problème à résoudre.

        // Création de l'objet 'Problemes' pour gérer les tentatives et les indices.
        Problemes probleme = new Problemes(uvManager, student);
        probleme.setEnonce(enonce);  // Associe l'énoncé du problème à l'objet problème.

        // Création d'un scanner pour interagir avec l'utilisateur
        Scanner scanner = new Scanner(System.in);
        boolean continuer = true;  // Variable pour contrôler la boucle principale.

        // Boucle principale pour interagir avec l'élève
        while (continuer) {
            System.out.println("Que souhaitez-vous faire ?");
            System.out.println("1. Interroger la machine pour des indices.");
            System.out.println("2. Emettre une hypothese (il vous reste " + probleme.getTentativesRestantes() + " tentatives.)");
            System.out.println("3. Quitter.");

            // Vérifie si l'entrée est un entier
            if (scanner.hasNextInt()) {
                int choix = scanner.nextInt();  // Capture le choix de l'utilisateur (1, 2 ou 3)
                scanner.nextLine();  // Consomme la ligne restante après la saisie d'un entier

                // Traitement du choix de l'utilisateur
                switch (choix) {
                    case 1:
                        probleme.demanderIndice();  // Si l'utilisateur choisit l'option 1, il peut demander un indice.
                        break;
                    case 2:
                        probleme.demanderHypothese();  // Si l'utilisateur choisit l'option 2, il peut faire une hypothèse.
                        break;
                    case 3:
                        continuer = false;  // Si l'utilisateur choisit l'option 3, on arrête la boucle et le programme.
                        break;
                    default:
                        System.out.println("\nChoix invalide. Veuillez réessayer.");  // Si le choix est invalide, on demande à l'utilisateur de réessayer.
                }
            } else {
                System.out.println("\nEntrée invalide. Veuillez entrer un nombre valide.\n");
                scanner.next();  // Consomme l'entrée invalide
            }
        }

        // Message de fin
        System.out.println("Merci d'avoir choisi l'UTBM.");
    }
}