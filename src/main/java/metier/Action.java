package metier;

public class Action {

    private Entrainement entrainement;
    private int ptsAtt;
    private int ptsDef;
    private int ptsPv;

    public Action() {
    }

    public Action(Entrainement entrainement, int ptsAtt, int ptsDef, int ptsPv) {
        this.entrainement = entrainement;
        this.ptsAtt = ptsAtt;
        this.ptsDef = ptsDef;
        this.ptsPv = ptsPv;
    }

    public Entrainement getEntrainement() {
        return entrainement;
    }

    public void setEntrainement(Entrainement entrainement) {
        this.entrainement = entrainement;
    }

    public int getPtsAtt() {
        return ptsAtt;
    }

    public void setPtsAtt(int ptsAtt) {
        this.ptsAtt = ptsAtt;
    }

    public int getPtsDef() {
        return ptsDef;
    }

    public void setPtsDef(int ptsDef) {
        this.ptsDef = ptsDef;
    }

    public int getPtsPv() {
        return ptsPv;
    }

    public void setPtsPv(int ptsPv) {
        this.ptsPv = ptsPv;
    }
}
