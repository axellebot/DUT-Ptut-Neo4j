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
    }

    public  void addObservateur(Observateur o) {
        listObservateur.add(o);
    }

    public void notifier() {
        for (Observateur ob : listObservateur) {
            ob.update();
            System.out.println("update");
        }
    }
    public int getNumberObervateur(){
        return listObservateur.size();
    }
}
