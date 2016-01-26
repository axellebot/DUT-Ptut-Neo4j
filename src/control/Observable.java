package control;

import java.util.ArrayList;

public abstract class Observable {
    private ArrayList<Observateur> listObservateur;

    public Observable() {
        listObservateur = new ArrayList<>();
    }

    public Observable(Observateur o) {
        listObservateur = new ArrayList<>();
        listObservateur.add(o);
        System.out.println("nombre d'observateur: " + listObservateur.size());
    }

    public  void addObservateur(Observateur o) {
        listObservateur.add(o);
        System.out.println("nombre d'observateur: " + listObservateur.size());
    }

    public void notifier() {
        System.out.println("nombre d'observateur: " + listObservateur.size());
        for (Observateur ob : listObservateur) {
            ob.update();
            System.out.println("update");
        }
    }
}
