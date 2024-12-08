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
        Scanner sc = new Scanner(System.in);
        String hypothese = sc.nextLine();
        UV currentUV = eleve.getCurrentUV();
        String solution = currentUV.getListeProblemes().get(enonce);
        if (hypothese.equals(solution)) {
            System.out.println("Bravo, vous avez résolu le problème !");
            eleve.incrementerScore();
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
}