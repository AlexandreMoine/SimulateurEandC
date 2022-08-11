package metier;

import exception.FusionException;

import java.util.*;

public class Simulation {

    private int nbTours;
    private int tour;
    private int jour;
    private ArrayList<Personnage> personnages;
    private static final Random random = new Random();

    public Simulation() {
    }

    public Simulation(int nbTours, int nbPersonnages) {
        this.nbTours = nbTours;
        this.tour = 1;
        this.jour = 1;
        this.personnages = new ArrayList<>();
        this.initPersonnages(nbPersonnages);
    }

    private void initPersonnages(int nbPersonnages) {
        for(int i = 1; i <= nbPersonnages; i++) {
            Personnage personnage = new Personnage("Perso " + i, false);
            this.personnages.add(personnage);
        }
    }

    public void startEntrainement() {
        for(Personnage personnage : this.personnages) {
            if(personnage.getPv().getValeur() > 0) {
                int pvPourcent = personnage.getPv().getPourcent();
                int proba = this.genererEntierAleatoire(1, 100);

                Action action = this.getActionEntrainement(proba <= pvPourcent, personnage.isFusion());

                personnage.setAtt(personnage.getAtt() + action.getPtsAtt());
                personnage.setDef(personnage.getDef() + action.getPtsDef());
                personnage.getPv().setPourcent(personnage.getPv().getPourcent() + action.getPtsPv());
                if(personnage.getPv().getPourcent() == 0) {
                    personnage.getPv().setPourcent(1);
                }

                personnage.getActions().computeIfAbsent(this.tour, k -> new LinkedHashMap<>());
                personnage.getActions().get(this.tour).put(this.jour, action);
            }

        }
        this.jour++;
    }

    public void startCombat() {
        this.personnages.forEach(this::combat);
        this.tour++;
        this.jour = 1;
    }

    public void fusionner(Personnage personnage1, Personnage personnage2) throws FusionException {
        if(personnage1.isFusion() || personnage2.isFusion()) {
            throw new FusionException(FusionException.PERSONNAGE_EST_FUSION);
        }

        if(personnage1.getPv().getValeur() == 0 || personnage2.getPv().getValeur() == 0) {
            throw new FusionException(FusionException.PERSONNAGE_EST_KO);
        }

        int attFusion = (personnage1.getAtt() + personnage2.getAtt()) / 2;
        int defFusion = (personnage1.getDef() + personnage2.getDef()) / 2;
        Pv pvFusion = new Pv();
        pvFusion.setMax(personnage1.getPv().getMax() + personnage2.getPv().getMax());
        pvFusion.setValeur(personnage1.getPv().getValeur() + personnage2.getPv().getValeur());
        String idFusion = personnage1.getId() + "-" + personnage2.getId();

        Personnage personnageFusion = new Personnage();
        personnageFusion.setId(idFusion);
        personnageFusion.setPv(pvFusion);
        personnageFusion.setAtt(attFusion);
        personnageFusion.setDef(defFusion);
        personnageFusion.setActions(new LinkedHashMap<>());
        personnageFusion.setFusion(true);

        int index = this.personnages.indexOf(personnage1);
        this.personnages.remove(personnage1);
        this.personnages.remove(personnage2);

        this.personnages.add(index, personnageFusion);
    }


    public boolean estTerminee() {
        boolean tousKO = this.personnages.stream().allMatch(p -> p.getPv().getValeur() == 0);
        boolean dernierTourAtteint = this.tour > this.nbTours;

        return tousKO || dernierTourAtteint;
    }

    private void combat(Personnage personnage) {
        int pvEnnemi = 1000 * this.tour;

        while(pvEnnemi > 0 && personnage.getPv().getValeur() > 0) {
            int attaquePersonnage = (int) (this.genererEntierAleatoire(5, 30) * personnage.getAtt() * 1.5);
            attaquePersonnage = attaquePersonnage == 0 ? 1 : attaquePersonnage;
            pvEnnemi = Math.max(pvEnnemi - attaquePersonnage, 0);

            int attaqueEnnemi = (int) (this.genererEntierAleatoire(5, 30) - (personnage.getDef() * 1.5));
            attaqueEnnemi = attaqueEnnemi <= 0 ? 1 : attaqueEnnemi;
            personnage.getPv().setValeur(personnage.getPv().getValeur() - attaqueEnnemi);
        }

    }

    private int genererEntierAleatoire(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }

    private Action getActionEntrainement(boolean isEntrainement, boolean isFusion) {
        Action action = new Action();

        if(isEntrainement) {
            Entrainement ent = Entrainement.values()[this.genererEntierAleatoire(0, 2)];
            action.setEntrainement(ent);
            int bonus = isFusion ? this.genererEntierAleatoire(3, 5) : 0;
            switch(ent) {
                case ATTAQUE:
                    action.setPtsPv(this.genererEntierAleatoire(-10, -5));
                    action.setPtsAtt(this.genererEntierAleatoire(1, 2) + bonus);
                    action.setPtsDef(this.genererEntierAleatoire(-2, -1));
                    break;
                case DEFENSE:
                    action.setPtsPv(this.genererEntierAleatoire(-10, -5));
                    action.setPtsAtt(this.genererEntierAleatoire(-2, -1));
                    action.setPtsDef(this.genererEntierAleatoire(1, 2) + bonus);
                    break;
                case SPECIAL:
                    action.setPtsPv(this.genererEntierAleatoire(-30, -20));
                    action.setPtsAtt(this.genererEntierAleatoire(-3, 5) + bonus);
                    action.setPtsDef(this.genererEntierAleatoire(-3, 5) + bonus);
                    break;
            }
        } else {
            action.setEntrainement(Entrainement.REPOS);
            action.setPtsPv(this.genererEntierAleatoire(10, 25));
            action.setPtsAtt(this.genererEntierAleatoire(-1, 0));
            action.setPtsDef(this.genererEntierAleatoire(-1, 0));
        }
        return action;
    }

    public int getNbTours() {
        return nbTours;
    }

    public void setNbTours(int nbTours) {
        this.nbTours = nbTours;
    }

    public int getTour() {
        return tour;
    }

    public void setTour(int tour) {
        this.tour = tour;
    }

    public int getJour() {
        return jour;
    }

    public void setJour(int jour) {
        this.jour = jour;
    }

    public ArrayList<Personnage> getPersonnages() {
        return personnages;
    }

    public void setPersonnages(ArrayList<Personnage> personnages) {
        this.personnages = personnages;
    }
}
