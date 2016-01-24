package control;

import java.util.ArrayList;

public class Observable {
    private ArrayList<Observateur> listObservateur;

    public Observable(Observateur o) {
        listObservateur = new ArrayList<>();
    }

    public void addObservateur(Observateur o) {
        listObservateur.add(o);
    }

    public void notifier() {
        for (int i = 0; i < listObservateur.size(); i++) {
            listObservateur.get(i).update();
        }
    }
}
