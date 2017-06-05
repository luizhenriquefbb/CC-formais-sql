/*
 * Desenvolvio para a cadeira de Linguagens formais, professor Andrei Formiga
 * 
 * 
 * Desenvolvedores:
 * * Luiz Henrique Freire Barros (email: luizhenriquefbb@gmail.com)
 * * Gabriel Belarmino (email: gabrielbelarmino1995@gmail.com)
 * 2017
 */
package mysql;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *
 * @author gabriel e luiz
 */
public class Twitter {
    String code, content;
    int recontenteet = 0;
    int favorits = 0;
    List<Twitter> coments = null; 
    String date;
    String user; // guarda o id da resposta
    ArrayList<String> hashtags;

    
    public Twitter(String content,String user) {
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-mm-dd");
        //Data d = new Date();
        this.content = content;
        this.user = user;
        date = (dt1.format(new Date())).toString();
        this.hashtags = new ArrayList<>();
        splitHashtags(content);
        
    }

    public Twitter(String code,String content,int favorite, String user,String date) {
        this.code = code;
        this.favorits = favorite;
        this.content = content;
        this.user = user;
        this.date = date;
        this.hashtags = new ArrayList<>();
    }
    
    
    
    private void splitHashtags (String content){
        String[] n_hastags = content.split("#[^\\s]+");
        
        String n_content = content;
        for (String s : n_hastags){
            n_content = n_content.replaceAll(s, "");
        }
        
        String[] hashs = n_content.split("#");
        
        for (String s : hashs){
            if (s.matches("[ ]*")){
                //não adicionar vazio
            } else {
                hashtags.add(s);
            }
            
        }
        
        
    }

    @Override
    public String toString() {
        String hash = "";
        for (String s : this.hashtags){
            hash+=s + "\n";
        }
        
        return "Tweet\n" +
                "code = " + code +
                "\ncontent = " + content +
                "\nrecontenteet = " + recontenteet +
                "\nfavorits = " + favorits +
                "\ncoments = " + coments +
                "\ndate = " + date +
                "\nanswer = " + user +
                "\nhastags = " + hash;
    }
}
