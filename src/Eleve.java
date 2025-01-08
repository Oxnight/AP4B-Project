import java.util.*;

/**
 * La classe Eleve représente un étudiant avec un nom, un score, un nombre d'UV validées et un gestionnaire d'UV.
 */
public class Eleve {
    private String name; // Nom de l'étudiant
    private int score = 0; // Score de l'étudiant, initialisé à zéro
    private int nombreUVvalidees = 0; // Nombre d'UV validées par l'étudiant, initialisé à zéro
    private final UVManager uvManager; // Le gestionnaire d'UV de l'étudiant
    private UV currentUV; // L'UV actuellement suivie par l'étudiant

    /**
     * Constructeur de la classe Eleve.
     *
     * @param uvManager Le gestionnaire d'UV associé à l'étudiant.
     */
    public Eleve(UVManager uvManager) {
        this.uvManager = uvManager; // Initialisation du gestionnaire d'UV
    }

    /**
     * Identifie l'étudiant en demandant son nom.
     */
    public void identification() {
        System.out.println("Bonjour, veuillez vous identifier.");
        Scanner sc = new Scanner(System.in); // Création du scanner pour récupérer l'entrée utilisateur
        System.out.print("Entrez votre nom : ");
        this.name = sc.nextLine(); // Lecture du nom de l'étudiant
        uvManager.ajouterEleves(this.name); // Ajout de l'élève au gestionnaire d'UV
    }

    /**
     * Retourne l'UV actuelle de l'étudiant.
     *
     * @return L'UV actuelle de l'étudiant.
     */
    public UV getCurrentUV() {
        return this.currentUV; // Retourne l'UV actuelle de l'étudiant
    }

    /**
     * Définit l'UV actuelle de l'étudiant.
     *
     * @param uv L'UV à définir comme actuelle.
     */
    public void setCurrentUV(UV uv) {
        this.currentUV = uv; // Définir l'UV actuelle de l'étudiant
    }

    /**
     * Retourne le score de l'étudiant.
     *
     * @return Le score de l'étudiant.
     */
    public int getScore() {
        return score; // Retourne le score actuel de l'étudiant
    }

    /**
     * Réinitialise le score de l'étudiant à zéro.
     *
     * @return Le score réinitialisé.
     */
    public int resetScore() {
        return score = 0; // Réinitialise le score de l'étudiant à zéro et le retourne
    }

    /**
     * Retourne le nombre d'UV validées par l'étudiant.
     *
     * @return Le nombre d'UV validées.
     */
    public int getNombreUVvalidees() {
        return nombreUVvalidees; // Retourne le nombre d'UV validées par l'étudiant
    }

    /**
     * Retourne le nom de l'étudiant.
     *
     * @return Le nom de l'étudiant.
     */
    public String getName() {
        return name; // Retourne le nom de l'étudiant
    }

    /**
     * Définit le nom de l'étudiant.
     *
     * @param name Le nom de l'étudiant.
     */
    public void setName(String name) {
        this.name = name; // Définit le nom de l'étudiant
    }

    /**
     * Incrémente le nombre d'UV validées par l'étudiant.
     */
    public void incrementerNombreUVValidees() {
        this.nombreUVvalidees += 1; // Incrémente le nombre d'UV validées par l'étudiant
    }

    /**
     * Incrémente le score de l'étudiant.
     */
    public void incrementerScore() {
        this.score += 1; // Incrémente le score de l'étudiant
    }

    /**
     * Affiche le score de l'étudiant si l'application est en mode GUI.
     *
     * @param isGUI Indique si l'application est en mode GUI.
     */
    public void afficherScore(boolean isGUI) {
        if (isGUI) {
            System.out.println("Score = " + this.score); // Affiche le score si l'application est en mode GUI
        }
    }
}