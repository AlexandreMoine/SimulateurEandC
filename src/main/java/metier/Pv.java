package metier;

public class Pv {

    private int valeur;
    private int max;
    private int pourcent;

    public Pv() {
    }

    public Pv(int max) {
        this.max = max;
        this.valeur = max;
        this.pourcent = 100;
    }

    private void calculerValeurSelonPourcent() {
        this.valeur = (int) ((double) this.max * ((double) this.pourcent / 100.0));
    }

    private void calculerPourcentSelonValeur() {
        this.pourcent = (int) ((int) 100.0 * (double) this.valeur / (double) this.max);
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        if(valeur < 0) {
            this.valeur = 0;
        } else {
            this.valeur = Math.min(valeur, this.max);
        }
        this.calculerPourcentSelonValeur();
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getPourcent() {
        return pourcent;
    }

    public void setPourcent(int pourcent) {
        if(pourcent < 0) {
            this.pourcent = 0;
        } else {
            this.pourcent = Math.min(pourcent, 100);
        }
        this.calculerValeurSelonPourcent();
    }

    @Override
    public String toString() {
        return "Pv{" +
                "valeur=" + valeur +
                ", max=" + max +
                ", pourcent=" + pourcent +
                '}';
    }
}
