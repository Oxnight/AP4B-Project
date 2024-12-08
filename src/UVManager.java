import java.util.*;

public class UVManager {
    private List<UV> listeUV;
    private List<String> listeEleves;

    public UVManager() {
        this.listeUV = new ArrayList<>();
        this.listeEleves = new ArrayList<>();
    }

    public void ajouterEleves(String name) {
        if (!listeEleves.contains(name)) {
            listeEleves.add(name);
        }
    }

    public void ajouterUV(UV uv) {
        listeUV.add(uv);
    }

    public UV selectionnerUV(Eleve eleve) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nVoici la liste des UV disponibles :");
            for (UV uv : listeUV) {
                System.out.println(uv.getName());
            }

            System.out.print("Veuillez choisir une UV : ");
            String choix = sc.nextLine();
            for (UV uv : listeUV) {
                if (choix.equals(uv.getName())) {
                    eleve.setCurrentUV(uv);
                    return uv;
                }
            }

            System.out.println("Cette UV n'existe pas. Veuillez réessayer.");
        }
    }

    public String selectionnerProbleme(UV uv){
        Map<String, String> problemes = uv.getListeProblemes();
        if (problemes.isEmpty()) {
            return null;
        }
        List<String> enonces = new ArrayList<>(problemes.keySet());
        Random random = new Random();
        int index = random.nextInt(enonces.size());
        String selectedEnonce = enonces.get(index);
        System.out.println("\nNouveau problème : " + selectedEnonce);
        return selectedEnonce;
    }

    public void retirerUV(UV uv) {
        listeUV.remove(uv);
    }
}
