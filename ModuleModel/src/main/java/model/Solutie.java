package model;

public class Solutie extends Entity<Long>{
    private String rezolvare;
    private Long bugRezolvat;

    public Solutie(){ }

    public Solutie(String rezolvare, Long bugRezolvat){
        this.rezolvare=rezolvare;
        this.bugRezolvat=bugRezolvat;
    }

    public Long getBugRezolvat() {
        return bugRezolvat;
    }

    public void setBugRezolvat(Long bugRezolvat) {
        this.bugRezolvat = bugRezolvat;
    }

    public String getRezolvare() {
        return rezolvare;
    }

    public void setRezolvare(String rezolvare) {
        this.rezolvare = rezolvare;
    }


}
