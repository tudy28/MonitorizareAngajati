package model;

public class Solutie extends Entity<Long>{
    private String rezolvare;
    private Bug bugRezolvat;

    public Solutie(){ }

    public Solutie(String rezolvare, Bug bugRezolvat){
        this.rezolvare=rezolvare;
        this.bugRezolvat=bugRezolvat;
    }

    public Bug getBugRezolvat() {
        return bugRezolvat;
    }

    public void setBugRezolvat(Bug bugRezolvat) {
        this.bugRezolvat = bugRezolvat;
    }

    public String getRezolvare() {
        return rezolvare;
    }

    public void setRezolvare(String rezolvare) {
        this.rezolvare = rezolvare;
    }


}
