package metier;

import java.util.LinkedHashMap;

public class Personnage {

    private String id;
    private Pv pv;
    private int att;
    private int def;
    private boolean fusion;
    private LinkedHashMap<Integer, LinkedHashMap<Integer, Action>> actions;

    public Personnage() {
    }

    public Personnage(String id, boolean fusion) {
        this.id = id;
        this.fusion = fusion;
        this.pv = new Pv(1000);
        this.att = 0;
        this.def = 0;
        this.actions = new LinkedHashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Pv getPv() {
        return pv;
    }

    public void setPv(Pv pv) {
        this.pv = pv;
    }

    public int getAtt() {
        return att;
    }

    public void setAtt(int att) {
        this.att = Math.max(att, 0);
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = Math.max(def, 0);
    }

    public boolean isFusion() {
        return fusion;
    }

    public void setFusion(boolean fusion) {
        this.fusion = fusion;
    }

    public LinkedHashMap<Integer, LinkedHashMap<Integer, Action>> getActions() {
        return actions;
    }

    public void setActions(LinkedHashMap<Integer, LinkedHashMap<Integer, Action>> actions) {
        this.actions = actions;
    }
}
