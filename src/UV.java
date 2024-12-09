import java.util.*;

public class UV {
    private String name;
    private String description;
    private Map<String, String> listeProblemes;

    public UV(String name, String description) {
        this.name = name;
        this.description = description;
        this.listeProblemes = new HashMap<>();
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public Map<String, String> getListeProblemes() {
        return listeProblemes;
    }

    public void ajouterProbleme(String enonce, String solution) {
        listeProblemes.put(enonce, solution);
    }
}
