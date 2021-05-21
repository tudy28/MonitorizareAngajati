package model;

public class Utilizator extends Entity<Long>{
    private String username;
    private Integer password;

    public Utilizator(){}

    public Utilizator(String username, Integer password){
        this.username=username;
        this.password=password;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String newUsername){
        this.username=newUsername;
    }

    public Integer getPassword(){
        return password;
    }

    public void setPassword(Integer newPassword){
        this.password=newPassword;
    }
}
