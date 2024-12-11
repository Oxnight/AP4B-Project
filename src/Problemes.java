import java.util.*;

public class Problemes {
    private String enonce;
    private int tentativesRestantes = 3;
    private UVManager uvManager;
    private Eleve eleve;

    public Problemes(UVManager uvManager, Eleve eleve) {
        this.uvManager = uvManager;
        this.eleve = eleve;
    }

    public String getEnonce() {
        return this.enonce;
    }

    public void setEnonce(String enonce) {
        this.enonce = enonce;
    }

    public int getTentativesRestantes() {
        return this.tentativesRestantes;
    }

    public boolean effectuerHypothese() {
        System.out.println("Veuillez entrer votre hypothèse : ");
        System.out.println("Le problème est : " + enonce);
        Scanner sc = new Scanner(System.in);
        String hypothese = sc.nextLine();
        UV currentUV = eleve.getCurrentUV();
        String solution = currentUV.getListeProblemes().get(enonce);
        if (hypothese.equals(solution)) {
            System.out.println("Bravo, vous avez résolu le problème !");
            eleve.incrementerScore();
            eleve.afficherScore();
            nouveauProbleme();
            return true;
        } else {
            tentativesRestantes -= 1;
            if (tentativesRestantes == 0) {
                System.out.println("Echec ! Vous avez épuisé toutes vos tentatives.");
                nouveauProbleme();
            } else {
                System.out.println("Incorrect. Il vous reste " + tentativesRestantes + " tentatives.");
            }
            return false;
        }
    }

    public void nouveauProbleme() {
        UV currentUV = eleve.getCurrentUV();
        if (this.enonce != null){
            currentUV.getListeProblemes().remove(this.enonce);
        }
        String nouveauEnonce = uvManager.selectionnerProbleme(currentUV);
        tentativesRestantes = 3;
        if (nouveauEnonce != null) {
            this.enonce = nouveauEnonce;
        } else {
            System.out.println("Tous les problèmes de cette UV ont été résolus.");
            if (eleve.getScore() >= 2) {
                System.out.println("Félicitations, vous avez validé l'UV !");
                eleve.incrementerNombreUVValidees();
            } else {
                System.out.println("Vous n'avez pas validé l'UV.");
            }
            uvManager.retirerUV(currentUV);
            uvManager.selectionnerProbleme(uvManager.selectionnerUV(eleve));
        }
    }

    public void interroger() {
        if (eleve.getCurrentUV().getName().equals("PC20")) {
            giveFeedbackMasterMind();
        }

        if (eleve.getCurrentUV().getName().equals("MT3F")) {
            giveFeedbackMT3F();
        }
    }

    public void giveFeedbackMT3F() {
        UV currentUV = eleve.getCurrentUV();
        String solution = currentUV.getListeProblemes().get(enonce);

        if (solution.equals("-143")) {
            System.out.println("\nLe déterminant d'une matrice 3×3 se calcule en multipliant les éléments de la première ligne \n" +
                    "par les déterminants des matrices 2×2 restantes, en alternant les signes. \n" +
                    "Il s'agit de prendre chaque élément de la première ligne, \n" +
                    "multiplier par le déterminant de la sous-matrice 2×2 correspondante, \n" +
                    "et additionner ou soustraire ces produits en fonction de leur position (alternance des signes).\n");

            System.out.print("Veuillez rentrer une hypothese :");
            Scanner sc = new Scanner(System.in);
            String hypothese = sc.nextLine();

            if (hypothese.matches("\\d+")) {
                System.out.println("Le nombre à trouver est négatif.\n");
            } else if (!hypothese.matches("\\d{3}")) {
                System.out.println("Le nombre à trouver est composé de 3 chiffres.\n");
            } else {
                System.out.println("Pense à bien relire les aides données.\n");
            }
        } else if (solution.equals("1/2*ln(x^2+1)+c")) {
            System.out.println("\nPour résoudre cette intégrale, il faut effectuer un changement de variable.\n" +
                    "On pose u = x^2 + 1, donc du = 2x dx.\n");

            System.out.print("Veuillez rentrer une hypothèse : ");
            Scanner sc = new Scanner(System.in);
            String hypothese = sc.nextLine();

            if (hypothese.matches("1/2*ln(x^2+1)")) {
                System.out.println("N'oublie pas la constante.\n");
            } else if (hypothese.matches("ln(x^2+1)+c")) {
                System.out.println("N'oubliez pas le facteur devant le ln.\n");
            } else {
                System.out.println("Pense à bien relire les aides données.\n");
            }
        } else if (solution.equals("9,75")) {
            System.out.println("\nPour résoudre ce produit infini, il faut le développer.\n" +
                    "On peut remarquer que le terme général est de la forme 1 + 1/(2n-1).\n" +
                    "Il faut donc développer ce produit jusqu'à n=30 et arrondir à 2 décimales supérieures.\n");

            System.out.print("Veuillez rentrer une hypothèse : ");
            Scanner sc = new Scanner(System.in);
            String hypothese = sc.nextLine();

            if (!hypothese.matches("^\\d+(\\.\\d{2})?$")) {
                System.out.println("L'hypothèse doit être un nombre à deux décimales.\n");
            } else if (hypothese.matches("^9\\,7[0-9]$")) {
                char secondDecimal = hypothese.charAt(3);
                if (secondDecimal != '5') {
                    System.out.println("L'arrondi n'est pas correct.\n");
                }
            } else if (hypothese.matches("^\\d\\,[0-9]+$")) {
                System.out.println("L'unité doit être un seul chiffre avant la virgule.");
            } else {
                System.out.println("Pensez à bien relire les aides données.\n");
            }
        }
    }

    public static boolean isValidGuess(String guess) {
        return guess.matches("\\d{4}"); // Vérifie que la chaîne est composée de 4 chiffres
    }

    public void giveFeedbackMasterMind() {
        System.out.println("Veuillez rentrer une hypothese:");
        Scanner sc = new Scanner(System.in);
        String hypothese = sc.nextLine().trim();
        while (!isValidGuess(hypothese)) {
            System.out.println("Entrée non valide, veuillez rentrer une chaine de 4 chiffres:");
            hypothese = sc.nextLine();
        }

        UV currentUV = eleve.getCurrentUV();
        String solution = currentUV.getListeProblemes().get(enonce);
        int bulls = 0; // Bons chiffres à la bonne position
        int cows = 0; // Bons chiffres à la mauvaise position

        boolean[] solutionUsed = new boolean[4];
        boolean[] guessUsed = new boolean[4];

        // Étape 1 : Compter les "bulls"
        for (int i = 0; i < 4; i++) {
            if (hypothese.charAt(i) == solution.charAt(i)) {
                bulls++;
                solutionUsed[i] = true;
                guessUsed[i] = true;
            }
        }

        // Étape 2 : Compter les "cows"
        for (int i = 0; i < 4; i++) {
            if (guessUsed[i]) continue; // Ignorer les "bulls"
            for (int j = 0; j < 4; j++) {
                if (solutionUsed[j]) continue; // Ignorer les chiffres déjà utilisés
                if (hypothese.charAt(i) == solution.charAt(j)) {
                    cows++;
                    solutionUsed[j] = true;
                    break;
                }
            }
        }

        System.out.println("Bons chiffres à la bonne position: "+ bulls + "\nBons chiffres à la mauvaise position: " + cows); // Retourne le feedback (xB = bulls, xW = cows)
    }
}
