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
        UV currentUV = eleve.getCurrentUV();
        if (currentUV != null) {
            enonce = uvManager.selectionnerProbleme(currentUV);
        }
        return enonce;
    }

    public void setEnonce(String enonce) {
        this.enonce = enonce;
    }

    public int getTentativesRestantes() {
        return this.tentativesRestantes;
    }

    public enum ValidationStatus {
        SUCCESS,
        NO_MORE_TRIES,
        ECHEC
    }

    public void demanderHypothese(){
        System.out.println("\nVeuillez entrer votre hypothèse : ");
        System.out.println("Le problème est : " + enonce);
        System.out.println("La solution est : " + eleve.getCurrentUV().getListeProblemes().get(enonce));
        Scanner sc = new Scanner(System.in);
        String hypothese = sc.nextLine();
        effectuerHypothese(hypothese, false);
    }

    public ValidationStatus effectuerHypothese(String hypothese, boolean isGUI) {
        UV currentUV = eleve.getCurrentUV();
        String solution = currentUV.getListeProblemes().get(enonce);
        if (hypothese.equals(solution)) {
            System.out.println("\nBravo, vous avez résolu le problème !");
            eleve.incrementerScore();
            eleve.afficherScore();
            nouveauProbleme(isGUI);
            return ValidationStatus.SUCCESS;
        } else {
            tentativesRestantes -= 1;
            if (tentativesRestantes == 0) {
                System.out.println("Echec ! Vous avez épuisé toutes vos tentatives.");
                eleve.afficherScore();
                nouveauProbleme(isGUI);
                return ValidationStatus.NO_MORE_TRIES;
            } else {
                System.out.println("\nIncorrect. Il vous reste " + tentativesRestantes + " tentatives.");
                return ValidationStatus.ECHEC;
            }
        }
    }

    public void nouveauProbleme(boolean isGUI) {
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
            eleve.resetScore();
            if (!isGUI){
                this.setEnonce(uvManager.selectionnerProbleme(uvManager.selectionnerUV(eleve)));
            }
        }
    }

    public void demanderIndice(){
        System.out.println("\nVeuillez entrer une hypothèse : ");
        System.out.println("Le problème est : " + enonce);
        System.out.println("La solution est : " + eleve.getCurrentUV().getListeProblemes().get(enonce));
        Scanner sc = new Scanner(System.in);
        String hypothese = sc.nextLine();

        if(eleve.getCurrentUV().getName().equals("PC20")){
            while (!isValidGuess(hypothese)) {
                System.out.println("Entrée non valide, veuillez rentrer une chaine de 4 chiffres:");
                hypothese = sc.nextLine();
            }
        }

        interroger(hypothese);
    }

    public String interroger(String hypothese) {
        if (eleve.getCurrentUV().getName().equals("PC20")) {
            return giveFeedbackMasterMind(hypothese);
        }

        if (eleve.getCurrentUV().getName().equals("MT3F")) {
            return giveFeedbackMT3F(hypothese);
        }
        return null;
    }

    public String giveFeedbackMT3F(String hypothese) {
        UV currentUV = eleve.getCurrentUV();
        String solution = currentUV.getListeProblemes().get(enonce);

        if (solution.equals("-143")) {
            String indice1 = "\nLe déterminant d'une matrice 3×3 se calcule en multipliant les éléments de la première ligne \n" +
                    "par les déterminants des matrices 2×2 restantes, en alternant les signes. \n" +
                    "Il s'agit de prendre chaque élément de la première ligne, \n" +
                    "multiplier par le déterminant de la sous-matrice 2×2 correspondante, \n" +
                    "et additionner ou soustraire ces produits en fonction de leur position (alternance des signes).\n";
            System.out.println(indice1);

            if (hypothese.matches("\\d+")) {
                String indice2 = "Le nombre à trouver est négatif.\n";
                System.out.println(indice2);
                return indice2 + "\n" + indice1;
            } else if (!hypothese.matches("\\d{3}")) {
                String indice3 = "Le nombre à trouver est composé de 3 chiffres.\n";
                System.out.println(indice3);
                return indice3 + "\n" + indice1;
            } else {
                String indice4 = "Pensez à bien relire les aides données.\n";
                System.out.println(indice4);
                return indice4 + "\n" + indice1;
            }
        } else if (solution.equals("1/2*ln(x^2+1)+c")) {
            String indice1 = "\nPour résoudre cette intégrale, il faut effectuer un changement de variable.\n" +
                    "On pose u = x^2 + 1, donc du = 2x dx.\n";
            System.out.println(indice1);

            if (hypothese.matches("1/2*ln(x^2+1)")) {
                String indice2 = "N'oubliez pas la constante.\n";
                System.out.println(indice2);
                return indice2 + "\n" + indice1;
            } else if (hypothese.matches("ln(x^2+1)+c")) {
                String indice3 = "N'oubliez pas le facteur devant le ln.\n";
                System.out.println(indice3);
                return indice3 + "\n" + indice1;
            } else {
                String indice4 = "Pensez à bien relire les aides données.\n";
                System.out.println(indice4);
                return indice4 + "\n" + indice1;
            }
        } else if (solution.equals("9.75")) {
            String indice1 = "\nPour résoudre ce produit, il faut le développer.\n" +
                    "On peut remarquer que le terme général est de la forme 1 + 1/(2n-1).\n" +
                    "Il faut donc développer ce produit jusqu'à n=30 et arrondir à 2 décimales supérieures.\n";
            System.out.println(indice1);

            if (!hypothese.matches("^\\d+(\\.\\d{2})?$")) {
                String indice2 = "L'hypothèse doit être un nombre à deux décimales.\n";
                System.out.println(indice2);
                return indice2 + "\n" + indice1;
            } else if (hypothese.matches("^9\\.7[0-9]$")) {
                char secondDecimal = hypothese.charAt(3);
                if (secondDecimal != '5') {
                    String indice3 = "L'arrondi n'est pas correct.\n";
                    System.out.println(indice3);
                    return indice3 + "\n" + indice1;
                }
            } else if (!hypothese.matches("^\\d\\.[0-9]+$")) {
                String indice4 = "L'unité doit avoir un seul chiffre avant la virgule.\n";
                System.out.println(indice4);
                return indice4 + "\n" + indice1;
            } else {
                String indice5 = "Pensez à bien relire les aides données.\n";
                System.out.println(indice5);
                return indice5 + "\n" + indice1;
            }
        } else if (solution.equals("105.19")) {
            String indice1 = "\nPour résoudre cette somme, on peut la séparer en deux parties : \n" +
                    "la somme de 1 à 100 de 1 et la somme de 1 à 100 de 1/n.\n" +
                    "La première somme est évidente, la deuxième est une série harmonique.\n";
            System.out.println(indice1);

            if (!hypothese.matches("^\\d+(\\.\\d{2})?$")) {
                String indice2 = "L'hypothèse doit être un nombre à deux décimales.\n";
                System.out.println(indice2);
                return indice2 + "\n" + indice1;
            } else if (hypothese.matches("^105\\.1[0-9]$")) {
                char secondDecimal = hypothese.charAt(5);
                if (secondDecimal != '9') {
                    String indice3 = "L'arrondi n'est pas correct.\n";
                    System.out.println(indice3);
                    return indice3 + "\n" + indice1;
                }
            } else if (!hypothese.matches("^\\d{3}\\.[0-9]+$")) {
                String indice4 = "L'unité doit avoir trois chiffres avant la virgule.\n";
                System.out.println(indice4);
                return indice4 + "\n" + indice1;
            } else {
                String indice5 = "Pensez à bien relire les aides données.\n";
                System.out.println(indice5);
                return indice5 + "\n" + indice1;
            }
        }
        return null;
    }

    public static boolean isValidGuess(String guess) {
        return guess.matches("\\d{4}"); // Vérifie que la chaîne est composée de 4 chiffres
    }

    public String giveFeedbackMasterMind(String hypothese) {

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

        String indice = "Bons chiffres à la bonne position: "+ bulls + "\nBons chiffres à la mauvaise position: " + cows;
        System.out.println(indice); // Retourne le feedback (xB = bulls, xW = cows)
        return indice;
    }
}
