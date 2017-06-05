package mysql;
/*
 * Desenvolvio para a cadeira de Linguagens formais, professor Andrei Formiga
 * 
 * 
 * Desenvolvedores:
 * * Luiz Henrique Freire Barros (email: luizhenriquefbb@gmail.com)
 * * Gabriel Belarmino (email: gabrielbelarmino1995@gmail.com)
 * 2017
 */
import java.util.ArrayList;
/**
 *
 * @author gabriel e luiz
 */
public class User {
    String name, user, email, country, language, telephone;
    ArrayList<String> follow = new ArrayList<>();
    ArrayList<String> tweet = new ArrayList<>();
    ArrayList<String> followed = new ArrayList<>();
    
    public User(String name,String user,String email,
           String country,String language,String telephone){
        this.name = name;
        this.user = user;
        this.email = email;
        this.country = country;
        this.language = language;
        this.telephone = telephone;
    }

    User(String name, String user) {
        this.name = name;
        this.user = user;
        this.email = "email_generico";
        this.country = "Brasil";
        this.language = "PT-BR";
        this.telephone = "";
    }

     public User(String name,String user,String email,
           String country,String language,String telephone,
           ArrayList<String> twitter, ArrayList<String> follow, ArrayList<String> followed){
        this.name = name;
        this.user = user;
        this.email = email;
        this.country = country;
        this.language = language;
        this.telephone = telephone;
        this.tweet = twitter;
        this.follow = follow;
        this.followed = followed;
    }
    
    
    
    @Override
    public String toString(){
        return "name = " + this.name + "\n" +
		"user = " + this.user + "\n" +
		"email = " + this.email + "\n" +
		"country = " + this.country + "\n" +
		"language = " + this.language + "\n" +
		"telephone = " + this.telephone + "\n" //+
//		"tweet = " + this.tweet + "\n" +
//		"follow = " + this.follow + "\n" +
//		"followed = " + this.followed + "\n"
                ;
    }
    
}
