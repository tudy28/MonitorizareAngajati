package model;

public class Bug extends Entity<Long>{
    private String numeBug;
    private String descriereBug;
    private String stareBug;

    public Bug(){}

    public Bug(String numeBug,String descriereBug){
        this.numeBug=numeBug;
        this.descriereBug=descriereBug;
        this.stareBug="nerezolvat";
    }

    public Bug(String numeBug,String descriereBug,String stareBug){
        this.numeBug=numeBug;
        this.descriereBug=descriereBug;
        this.stareBug=stareBug;
    }



    public String getDescriereBug() {
        return descriereBug;
    }

    public String getNumeBug() {
        return numeBug;
    }

    public void setDescriereBug(String descriereBug) {
        this.descriereBug = descriereBug;
    }

    public void setNumeBug(String numeBug) {
        this.numeBug = numeBug;
    }

    public String getStareBug() {
        return stareBug;
    }

    public void setStareBug(String stareBug) {
        this.stareBug = stareBug;
    }
}
