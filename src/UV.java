import java.util.*;

/**
 * Classe représentant une UV (Unité de Valeur), avec un nom, une description et une liste de problèmes associés.
 */
public class UV {
    private final String name; // Le nom de l'UV
    private final String description; // La description de l'UV
    private final Map<String, String> listeProblemes; // Une map de problemes associant l'énoncé du problème à sa solution

    /**
     * Constructeur de la classe UV.
     *
     * @param name        Le nom de l'UV
     * @param description La description de l'UV
     */
    public UV(String name, String description) {
        this.name = name;
        this.description = description;
        this.listeProblemes = new HashMap<>();
    }

    /**
     * Méthode pour obtenir le nom de l'UV.
     *
     * @return Le nom de l'UV
     */
    public String getName() {
        return this.name;
    }

    /**
     * Méthode pour obtenir la description de l'UV.
     *
     * @return La description de l'UV
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Méthode pour obtenir la liste des problèmes associés à l'UV.
     *
     * @return Une map contenant les problèmes avec leurs solutions
     */
    public Map<String, String> getListeProblemes() {
        return listeProblemes;
    }

    /**
     * Méthode pour ajouter un problème à la liste de l'UV.
     *
     * @param enonce   L'énoncé du problème à ajouter
     * @param solution La solution associée au problème
     */
    public void ajouterProbleme(String enonce, String solution) {
        listeProblemes.put(enonce, solution);
    }
}