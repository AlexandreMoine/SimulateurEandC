package exception;

public class FusionException extends Exception {

    public static final int PERSONNAGE_EST_FUSION = 1;
    public static final int PERSONNAGE_EST_KO = 2;

    private final int typeErreur;

    public FusionException(int typeErreur) {
        super();
        this.typeErreur = typeErreur;
    }

    @Override
    public String getMessage() {
        if(this.typeErreur == PERSONNAGE_EST_FUSION) {
            return "Erreur de fusion : un des deux personnages est déjà une fusion.";
        } else if(this.typeErreur == PERSONNAGE_EST_KO) {
            return "Erreur de fusion : un des deux personnages est KO.";
        } else {
            return super.getMessage();
        }

    }
}
