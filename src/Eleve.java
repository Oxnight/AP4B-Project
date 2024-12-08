import java.util.*;

public class Eleve {
    private String name;
    private int score = 0;
    private int nombreUVvalidees = 0;
    private UVManager uvManager;
    private UV currentUV;

    public Eleve(UVManager uvManager) {
        this.uvManager = uvManager;
    }

    public void identification() {
        System.out.println("Bonjour, veuillez vous identifier.");
        Scanner sc = new Scanner(System.in);
        System.out.print("Entrez votre nom : ");
        this.name = sc.nextLine();
        uvManager.ajouterEleves(this.name);
    }

    public void setCurrentUV(UV uv) {
        this.currentUV = uv;
    }

    public UV getCurrentUV() {
        return this.currentUV;
    }

    public int getScore() {
        return score;
    }

    public void incrementerNombreUVValidees() {
        this.nombreUVvalidees += 1;
    }

    public void incrementerScore() {
        this.score += 1;
    }
}