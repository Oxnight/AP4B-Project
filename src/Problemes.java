import java.util.*;

/**
 * Classe représentant la gestion des problèmes d'un élève dans une UV.
 * Cette classe permet à l'élève d'interagir avec les problèmes en soumettant des hypothèses et en recevant des indices ou des feedbacks.
 */
public class Problemes {
    private final UVManager uvManager; // Gestionnaire des UVs.
    private final Eleve eleve; // L'élève qui interagit avec les problèmes.
    private String enonce; // Énoncé du problème actuel.
    private int tentativesRestantes = 3; // Nombre de tentatives restantes pour résoudre un problème.

    /**
     * Constructeur de la classe Problemes.
     *
     * @param uvManager Le gestionnaire des UVs.
     * @param eleve     L'élève qui résout les problèmes.
     */
    public Problemes(UVManager uvManager, Eleve eleve) {
        this.uvManager = uvManager;
        this.eleve = eleve;
    }

    /**
     * Enumération représentant les statuts de validation d'une hypothèse.
     */
    public enum ValidationStatus {
        SUCCESS, // Hypothèse correcte.
        NO_MORE_TRIES, // Aucune tentative restante.
        ECHEC // Hypothèse incorrecte.
    }

    /**
     * Récupère l'énoncé du problème en cours en fonction de l'UV de l'élève.
     *
     * @return L'énoncé du problème en cours.
     */
    public String getEnonce() {
        UV currentUV = eleve.getCurrentUV();
        if (currentUV != null) {
            enonce = uvManager.selectionnerProbleme(currentUV, true); // Sélectionne un problème dans l'UV en cours.
        }
        return enonce;
    }

    /**
     * Définit un nouvel énoncé de problème.
     *
     * @param enonce Le nouvel énoncé à définir.
     */
    public void setEnonce(String enonce) {
        this.enonce = enonce;
    }

    /**
     * Récupère le nombre de tentatives restantes pour résoudre un problème.
     *
     * @return Le nombre de tentatives restantes.
     */
    public int getTentativesRestantes() {
        return this.tentativesRestantes;
    }

    /**
     * Demande à l'élève d'entrer une hypothèse pour résoudre le problème.
     */
    public void demanderHypothese() {
        System.out.println("\nVeuillez entrer votre hypothèse : ");
        System.out.println("Le problème est : " + enonce);
        Scanner sc = new Scanner(System.in);
        String hypothese = sc.nextLine(); // Lecture de l'hypothèse de l'élève.
        effectuerHypothese(hypothese, false); // Vérification de l'hypothèse.
    }

    /**
     * Vérifie si l'hypothèse de l'élève est correcte.
     *
     * @param hypothese L'hypothèse de l'élève.
     * @param isGUI     Indique si l'interface graphique est utilisée.
     * @return Le statut de la validation de l'hypothèse.
     */
    public ValidationStatus effectuerHypothese(String hypothese, boolean isGUI) {
        UV currentUV = eleve.getCurrentUV();
        String solution = currentUV.getListeProblemes().get(enonce); // Récupération de la solution du problème.

        if (hypothese.equals(solution)) {
            // Hypothèse correcte
            if (!isGUI) {
                System.out.println("\nBravo, vous avez résolu le problème !");
            }
            eleve.incrementerScore(); // Augmenter le score de l'élève.
            eleve.afficherScore(false); // Afficher le score actuel.
            nouveauProbleme(isGUI); // Passer au prochain problème.
            return ValidationStatus.SUCCESS;
        } else {
            // Hypothèse incorrecte
            tentativesRestantes -= 1; // Réduire le nombre de tentatives restantes.
            if (tentativesRestantes == 0) {
                // Tentatives épuisées
                if (!isGUI) {
                    System.out.println("Echec ! Vous avez épuisé toutes vos tentatives.");
                }
                eleve.afficherScore(false);
                nouveauProbleme(isGUI); // Passer au prochain problème.
                return ValidationStatus.NO_MORE_TRIES;
            } else {
                // Hypothèse incorrecte, mais des tentatives restent
                if (!isGUI) {
                    System.out.println("\nIncorrect. Il vous reste " + tentativesRestantes + " tentatives.");
                }
                return ValidationStatus.ECHEC;
            }
        }
    }

    /**
     * Charge un nouveau problème après avoir résolu le précédent.
     * Si tous les problèmes de l'UV sont résolus, valide l'UV et passe à l'UV suivante.
     *
     * @param isGUI Indique si l'interface graphique est utilisée.
     */
    public void nouveauProbleme(boolean isGUI) {
        UV currentUV = eleve.getCurrentUV();
        if (this.enonce != null) {
            currentUV.getListeProblemes().remove(this.enonce); // Retirer le problème résolu de la liste.
        }
        String nouveauEnonce = uvManager.selectionnerProbleme(currentUV, isGUI); // Sélectionner un nouveau problème.
        tentativesRestantes = 3; // Réinitialiser le nombre de tentatives.

        if (nouveauEnonce != null) {
            this.enonce = nouveauEnonce; // Assigner le nouvel énoncé.
        } else {
            // Si tous les problèmes de l'UV ont été résolus.
            if (!isGUI) {
                System.out.println("Tous les problèmes de cette UV ont été résolus.");
            }
            if (eleve.getScore() >= 2) {
                if (!isGUI) {
                    System.out.println("Félicitations, vous avez validé l'UV !");
                }
                eleve.incrementerNombreUVValidees(); // L'élève valide l'UV.
            } else {
                if (!isGUI) {
                    System.out.println("Vous n'avez pas validé l'UV.");
                }
            }
            uvManager.retirerUV(currentUV); // Retirer l'UV de la liste de l'élève.
            eleve.resetScore(); // Réinitialiser le score de l'élève.
            if (!isGUI) {
                this.setEnonce(uvManager.selectionnerProbleme(uvManager.selectionnerUV(eleve), isGUI)); // Sélectionner un nouveau problème dans la prochaine UV.
            }
        }
    }

    /**
     * L'élève demande un indice pour l'aider à résoudre le problème.
     */
    public void demanderIndice() {
        System.out.println("\nVeuillez entrer une hypothèse : ");
        System.out.println("Le problème est : " + enonce);
        Scanner sc = new Scanner(System.in);
        String hypothese = sc.nextLine(); // Lecture de l'hypothèse pour demander un indice.

        if (eleve.getCurrentUV().getName().equals("PC20")) {
            while (!isValidGuess(hypothese)) {
                // Si l'UV est "PC20", valider que l'hypothèse soit une chaîne de 4 chiffres.
                System.out.println("Entrée non valide, veuillez rentrer une chaine de 4 chiffres:");
                hypothese = sc.nextLine();
            }
        }

        interroger(hypothese, false); // Interroger pour obtenir un feedback.
    }

    /**
     * Effectue un feedback en fonction de l'UV et du type de problème.
     *
     * @param hypothese L'hypothèse de l'élève.
     * @param isGUI     Indique si l'interface graphique est utilisée.
     * @return Le feedback sous forme de chaîne de caractères.
     */
    public String interroger(String hypothese, boolean isGUI) {
        if (eleve.getCurrentUV().getName().equals("PC20")) {
            return giveFeedbackPC20(hypothese, isGUI); // Feedback pour l'UV "PC20".
        }

        if (eleve.getCurrentUV().getName().equals("MT3F")) {
            return giveFeedbackMT3F(hypothese, isGUI); // Feedback pour l'UV "MT3F".
        }
        return null; // Pas de feedback.
    }

    /**
     * Fournit un feedback pour l'UV "MT3F" en fonction de l'hypothèse de l'élève.
     *
     * @param hypothese L'hypothèse de l'élève.
     * @param isGUI     Indique si l'interface graphique est utilisée.
     * @return Le feedback sous forme de chaîne de caractères.
     */
    public String giveFeedbackMT3F(String hypothese, boolean isGUI) {
        UV currentUV = eleve.getCurrentUV();
        String solution = currentUV.getListeProblemes().get(enonce); // Solution du problème.

        // Conditions pour les différents problèmes dans l'UV "MT3F".
        if (solution.equals("-143")) {
            // Détail pour un problème de calcul de déterminant.
            String indice1 = "\nLe déterminant d'une matrice 3×3 se calcule en multipliant les éléments de la première ligne \n" +
                    "par les déterminants des matrices 2×2 restantes, en alternant les signes. \n" +
                    "Il s'agit de prendre chaque élément de la première ligne, \n" +
                    "multiplier par le déterminant de la sous-matrice 2×2 correspondante, \n" +
                    "et additionner ou soustraire ces produits en fonction de leur position (alternance des signes).\n";
            if (!isGUI) {
                System.out.println(indice1);
            }

            if (hypothese.matches("\\d+")) {
                String indice2 = "Le nombre à trouver est négatif.\n";
                if (!isGUI) {
                    System.out.println(indice2);
                }
                return indice2 + "\n" + indice1;
            } else if (!hypothese.matches("-?\\d{3}")) {
                String indice3 = "Le nombre à trouver est composé de 3 chiffres.\n";
                if (!isGUI) {
                    System.out.println(indice3);
                }
                return indice3 + "\n" + indice1;
            } else if (hypothese.equals("-143")) {
                String indice4 = "Bravo, vous avez trouvé le bon résultat !\nVeuillez le reporter dans l'onglet 'Proposer une hypothese'.\n";
                if (!isGUI) {
                    System.out.println(indice4);
                }
                return indice4;
            } else {
                String indice5 = "Pensez à bien relire les aides données.\n";
                if (!isGUI) {
                    System.out.println(indice5);
                }
                return indice5 + "\n" + indice1;
            }
        } else if (solution.equals("1/2*ln(x^2+1)+c")) {
            // Détail pour un problème d'intégrale.
            String indice1 = "\nPour résoudre cette intégrale, il faut effectuer un changement de variable.\n" +
                    "On pose u = x^2 + 1, donc du = 2x dx.\n";
            if (!isGUI) {
                System.out.println(indice1);
            }

            if (hypothese.equals("1/2*ln(x^2+1)")) {
                String indice2 = "N'oubliez pas la constante.\n";
                if (!isGUI) {
                    System.out.println(indice2);
                }
                return indice2 + "\n" + indice1;
            } else if (hypothese.equals("ln(x^2+1)+c")) {
                String indice3 = "N'oubliez pas le facteur devant le ln.\n";
                if (!isGUI) {
                    System.out.println(indice3);
                }
                return indice3 + "\n" + indice1;
            } else if (hypothese.equals("1/2*ln(x^2+1)+c")) {
                String indice4 = "Bravo, vous avez trouvé le bon résultat !\nVeuillez le reporter dans l'onglet 'Proposer une hypothese'.\n";
                if (!isGUI) {
                    System.out.println(indice4);
                }
                return indice4;
            } else {
                String indice5 = "Pensez à bien relire les aides données.\n";
                if (!isGUI) {
                    System.out.println(indice5);
                }
                return indice5 + "\n" + indice1;
            }
        } else if (solution.equals("9.75")) {
            // Détail pour un problème de produit.
            String indice1 = "\nPour résoudre ce produit, il faut le développer.\n" +
                    "On peut remarquer que le terme général est de la forme 1 + 1/(2n-1).\n" +
                    "Il faut donc développer ce produit jusqu'à n=30 et arrondir à 2 décimales supérieures.\n";
            System.out.println(indice1);

            if (!hypothese.matches("^\\d+(\\.\\d{2})?$")) {
                String indice2 = "L'hypothèse doit être un nombre à deux décimales.\n";
                if (!isGUI) {
                    System.out.println(indice2);
                }
                return indice2 + "\n" + indice1;
            } else if (hypothese.matches("^9\\.7[0-9]$") && !hypothese.equals("9.75")) {
                char secondDecimal = hypothese.charAt(3);
                if (secondDecimal != '5') {
                    String indice3 = "L'arrondi n'est pas correct.\n";
                    if (!isGUI) {
                        System.out.println(indice3);
                    }
                    return indice3 + "\n" + indice1;
                }
            } else if (!hypothese.matches("^\\d\\.[0-9]+$")) {
                String indice4 = "L'unité doit avoir un seul chiffre avant la virgule.\n";
                if (!isGUI) {
                    System.out.println(indice4);
                }
                return indice4 + "\n" + indice1;
            } else if (hypothese.equals("9.75")) {
                String indice5 = "Bravo, vous avez trouvé le bon résultat !\nVeuillez le reporter dans l'onglet 'Proposer une hypothese'.\n";
                if (!isGUI) {
                    System.out.println(indice5);
                }
                return indice5;
            } else {
                String indice6 = "Pensez à bien relire les aides données.\n";
                if (!isGUI) {
                    System.out.println(indice6);
                }
                return indice6 + "\n" + indice1;
            }
        } else if (solution.equals("105.19")) {
            // Détail pour un problème de somme.
            String indice1 = "\nPour résoudre cette somme, on peut la séparer en deux parties : \n" +
                    "la somme de 1 à 100 de 1 et la somme de 1 à 100 de 1/n.\n" +
                    "La première somme est évidente, la deuxième est une série harmonique.\n";
            if (!isGUI) {
                System.out.println(indice1);
            }

            if (!hypothese.matches("^\\d+(\\.\\d{2})?$")) {
                String indice2 = "L'hypothèse doit être un nombre à deux décimales.\n";
                if (!isGUI) {
                    System.out.println(indice2);
                }
                return indice2 + "\n" + indice1;
            } else if (hypothese.matches("^105\\.1[0-9]$") && !hypothese.equals("105.19")) {
                char secondDecimal = hypothese.charAt(5);
                if (secondDecimal != '9') {
                    String indice3 = "L'arrondi n'est pas correct.\n";
                    if (!isGUI) {
                        System.out.println(indice3);
                    }
                    return indice3 + "\n" + indice1;
                }
            } else if (!hypothese.matches("^\\d{3}\\.[0-9]+$")) {
                String indice4 = "L'unité doit avoir trois chiffres avant la virgule.\n";
                if (!isGUI) {
                    System.out.println(indice4);
                }
                return indice4 + "\n" + indice1;
            } else if (hypothese.equals("105.19")) {
                String indice5 = "Bravo, vous avez trouvé le bon résultat !\nVeuillez le reporter dans l'onglet 'Proposer une hypothese'.\n";
                if (!isGUI) {
                    System.out.println(indice5);
                }
                return indice5;
            } else {
                String indice6 = "Pensez à bien relire les aides données.\n";
                if (!isGUI) {
                    System.out.println(indice6);
                }
                return indice6 + "\n" + indice1;
            }
        }
        return null;
    }

    /**
     * Vérifie si l'hypothèse est valide (4 chiffres).
     *
     * @param guess L'hypothèse de l'élève.
     * @return true si l'hypothèse est valide, false sinon.
     */
    public static boolean isValidGuess(String guess) {
        return guess.matches("\\d{4}"); // Vérifie que la chaîne est composée de 4 chiffres.
    }

    /**
     * Fournit un feedback pour l'UV "PC20" en fonction de l'hypothèse de l'élève.
     *
     * @param hypothese L'hypothèse de l'élève.
     * @param isGUI     Indique si l'interface graphique est utilisée.
     * @return Le feedback sous forme de chaîne de caractères.
     */
    public String giveFeedbackPC20(String hypothese, boolean isGUI) {

        UV currentUV = eleve.getCurrentUV();
        String solution = currentUV.getListeProblemes().get(enonce);
        int bulls = 0; // Bons chiffres à la bonne position.
        int cows = 0; // Bons chiffres à la mauvaise position.

        boolean[] solutionUsed = new boolean[4];
        boolean[] guessUsed = new boolean[4];

        // Étape 1 : Compter les bons chiffres à la bonne position.
        for (int i = 0; i < 4; i++) {
            if (hypothese.charAt(i) == solution.charAt(i)) {
                bulls++;
                solutionUsed[i] = true;
                guessUsed[i] = true;
            }
        }

        // Étape 2 : Compter les bons chiffres à la mauvaise position.
        for (int i = 0; i < 4; i++) {
            if (guessUsed[i]) continue; // Ignorer les bons chiffres à la bonne position.
            for (int j = 0; j < 4; j++) {
                if (solutionUsed[j]) continue; // Ignorer les chiffres déjà utilisés.
                if (hypothese.charAt(i) == solution.charAt(j)) {
                    cows++;
                    solutionUsed[j] = true;
                    break;
                }
            }
        }
        // Retourne le feedback
        String indice;
        if (bulls == 4) {
            indice = "Bravo, vous avez résolu le problème! Veuillez la reporter dans l'onglet 'Proposer une hypothese'.";
        } else {
            indice = "Bons chiffres à la bonne position: " + bulls + "\nBons chiffres à la mauvaise position: " + cows;
        }
        if (!isGUI) {
            System.out.println(indice);
        }
        return indice;
    }
}