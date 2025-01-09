import java.util.*;

/**
 * La classe UVManager gère les UV disponibles ainsi que les étudiants qui les suivent.
 * Elle permet d'ajouter des UV, de sélectionner des UV et de gérer les problèmes associés.
 */
public class UVManager {
    private final List<UV> listeUV; // Liste des UV disponibles
    private final List<String> listeEleves; // Liste des noms des étudiants

    /**
     * Constructeur de la classe UVManager.
     * Initialise les listes d'UV et d'élèves.
     */
    public UVManager() {
        this.listeUV = new ArrayList<>();
        this.listeEleves = new ArrayList<>();
    }

    /**
     * Ajoute un élève à la liste des élèves si ce n'est pas déjà fait.
     *
     * @param name Le nom de l'élève à ajouter.
     */
    public void ajouterEleves(String name) {
        if (!listeEleves.contains(name)) { // Vérifie si l'élève est déjà dans la liste
            listeEleves.add(name); // Ajoute l'élève si ce n'est pas le cas
        }
    }

    /**
     * Ajoute une UV à la liste des UV disponibles.
     *
     * @param uv L'UV à ajouter.
     */
    public void ajouterUV(UV uv) {
        listeUV.add(uv);
    }

    /**
     * Permet à un élève de sélectionner une UV dans la liste disponible.
     * Si aucune UV n'est disponible, affiche un message de fin.
     *
     * @param eleve L'élève qui sélectionne l'UV.
     * @return L'UV sélectionnée par l'élève.
     */
    public UV selectionnerUV(Eleve eleve) {
        Scanner sc = new Scanner(System.in); // Création du scanner pour la saisie de l'élève

        while (true) {
            if (listeUV.isEmpty()) { // Vérifie si la liste des UV est vide
                // Si la liste des UV est vide, l'élève a fini le programme
                System.out.println("\nBravo, vous avez fini le TC ! Vous saurez d'ici peu si vous passez en branche.");
                if (eleve.getNombreUVvalidees() >= 1) {
                    System.out.println("Vous avez l'autorisation de partir en FISE INFORMATIQUE!");
                } else {
                    System.out.println("Convoqué devant le 2ème jury de suivi. Très mauvais semestre. Risque de réorientation. Maintien à l'UTBM selon vos résultats. Prenez vos précautions en vue d'une réorientation, inscrivez vous sur ParcourSup.");
                }
                System.exit(0); // Quitte l'application si la liste des UV est vide
            }
            System.out.println("\nVoici la liste des UV disponibles :");
            // Affiche toutes les UV disponibles
            for (UV uv : listeUV) {
                System.out.println(uv.getName() + " : " + uv.getDescription());
            }

            System.out.print("Veuillez choisir une UV : ");
            String choix = sc.nextLine(); // Lecture du choix de l'élève
            for (UV uv : listeUV) {
                if (choix.equals(uv.getName())) { // Vérifie si le choix de l'élève correspond à une UV existante
                    eleve.setCurrentUV(uv); // Définit l'UV actuelle de l'élève
                    return uv; // Retourne l'UV sélectionnée
                }
            }

            System.out.println("Cette UV n'existe pas. Veuillez réessayer."); // Si l'UV choisie n'existe pas, demande de réessayer
        }
    }

    /**
     * Sélectionne un problème au hasard parmi ceux associés à une UV donnée.
     *
     * @param uv    L'UV dont on veut sélectionner un problème.
     * @param isGUI Indique si l'application est en mode GUI ou non.
     * @return L'énoncé du problème sélectionné, ou null si aucun problème n'est disponible.
     */
    public String selectionnerProbleme(UV uv, boolean isGUI) {
        Map<String, String> problemes = uv.getListeProblemes(); // Récupère les problèmes de l'UV
        if (problemes.isEmpty()) {
            return null; // Si aucun problème n'est disponible, retourne null
        }
        List<String> enonces = new ArrayList<>(problemes.keySet()); // Récupère les énoncés des problèmes
        Random random = new Random(); // Objet pour générer un nombre aléatoire
        int index = random.nextInt(enonces.size()); // Sélectionne un index aléatoire
        String selectedEnonce = enonces.get(index); // Récupère l'énoncé du problème sélectionné aléatoirement
        if (!isGUI) {
            System.out.println("\nNouveau problème : " + selectedEnonce); // Affiche le problème si l'application n'est pas en mode GUI
        }
        return selectedEnonce; // Retourne l'énoncé du problème sélectionné
    }

    /**
     * Retire une UV de la liste des UV disponibles.
     *
     * @param uv L'UV à retirer.
     */
    public void retirerUV(UV uv) {
        listeUV.remove(uv);
    }

    /**
     * Retourne la liste des UV disponibles.
     *
     * @return La liste des UV.
     */
    public List<UV> getListeUV() {
        return listeUV;
    }
}