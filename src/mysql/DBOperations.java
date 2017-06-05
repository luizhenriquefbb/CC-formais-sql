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
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author gabriel e luiz
 */
public class DBOperations {
    
    
    
    
    /**
     * insere um user à classe de usuários
     * @param user
     * @return boolean - inserido com sucesso ou não
     */
    public static boolean insertUser(User user){
        try {
            
            Statement st = Main.conn.createStatement();
            st.executeUpdate("INSERT INTO `twitter`.`User`(idUser,email,name,country,lang,cellphone)"
            + "VALUES('"+user.user
                        +"','"
                        +user.email
                        +"','"        
                        +user.name        
                        +"','"        
                        +user.country 
                        +"','"        
                        +user.language
                        +"','"        
                        +user.telephone+ 
                        "')");
        
        }catch (SQLException e){
            Logger.getLogger(DBOperations.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null,"Erro Na Inserção do Supermercado");
            return false;
        }
         
        
        return true;
    }
    /**
     * método que cria x usuários e adiciona no banco
     * @param x  numero de usuarios
     * @return void
     * @author gabriel
     */
    public static void generateUsers(int x){
        User tem_user;
        boolean success = false;
        for (int i=0; i < x; i++){
            tem_user = (new User("nome"+(i), "@user"+(i)));
            success = insertUser(tem_user);
            if(!success){
                System.out.println("erro na insercao");
                System.exit(1); //tratando o erro, não vamos mais crashar mais
            }
        }
        
    }
    /**
     * um usuário "é seguido" por outro e esse outro "Segue" o primeiro
     * @param User
     * @param User 
     */
    public static boolean generateFollower(User a,User b){
        try {
            Statement st = Main.conn.createStatement();
            System.out.println(b.user);
            st.executeUpdate("INSERT INTO Followers (User_idUser,Folower)"
                    + "VALUES('"+a.user
                        +"','"
                        +b.user+
                        "')");
            st.executeUpdate("INSERT INTO Following (User_idUser,Followed)"
                    + "VALUES('"+b.user
                        +"','"
                        +a.user+
                        "')");
        }catch (SQLException e){
            Logger.getLogger(DBOperations.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
         
        
        return true;
    }
    /**
     * @param nick
     * @return user ou null
     * @author Gabriel
     */
    public static User findUserByNick(String nick){
        User u = null;
        try {
            Statement st = Main.conn.createStatement();
            ResultSet set = st.executeQuery(" SELECT * FROM twitter.User WHERE idUser = '"+nick+"';");
            if(set.next()){
                    u = new User(set.getString("name"),
                                set.getString("idUser"),
                                set.getString("email"),
                                set.getString("country"),
                                set.getString("lang"),
                                set.getString("cellphone")                               
                                );
            }
            set = st.executeQuery(" SELECT * FROM twitter.Followers WHERE User_idUser = '"+nick+"';");
            String a;
            while(set.next()){
                a = set.getString("Folower");
                u.followed.add(a);
            }    
            set = st.executeQuery(" SELECT * FROM twitter.Following WHERE User_idUser = '"+nick+"';");
            while(set.next()){
                a = set.getString("Followed");
                u.follow.add(a);
            }  
            set = st.executeQuery("SELECT * FROM twitter.Feed WHERE User_idUser = '"+nick+"';");
            while(set.next()){
                a = set.getString("idFeed");
                u.tweet.add(a);
            }  
            
        }catch (SQLException e){
            Logger.getLogger(DBOperations.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
         
        return u;
    }
     /**
      * Deleta um usuário especifico
     * @param nick
     * @return bollean do sucesso da operação
     * @author Gabriel
     */
    public static boolean DeleteNick(String nick){
        try {
            
            
            Statement st = Main.conn.createStatement();
            st.executeUpdate(" Delete FROM twitter.Followers WHERE User_idUser = '"+nick+"' or Folower ='"+nick+"';");
            st.executeUpdate(" Delete FROM twitter.Following WHERE User_idUser = '"+nick+"' or Followed ='"+nick+"';");
            st.executeUpdate(" Delete FROM twitter.Hashtags WHERE Feed_User_idUser = '"+nick+"';");
            st.executeUpdate(" Delete FROM twitter.Feed WHERE User_idUser = '"+nick+"';");
            st.executeUpdate(" Delete FROM twitter.User WHERE idUser = '"+nick+"';");
            
        }catch (SQLException e){
            Logger.getLogger(DBOperations.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
         return true;
    }
    
    public static Twitter findTwitter(String idFeed){
        Twitter t = null;
        try {
            
            
            Statement st = Main.conn.createStatement();
            ResultSet set = st.executeQuery(" SELECT * FROM twitter.Feed WHERE idFeed = '"+idFeed+"';");
            
            if(set.next()){
                    t = new Twitter(set.getString("idFeed"),
                                set.getString("tweet"),
                                set.getInt("favorite"),
                                set.getString("User_idUser"),
                                set.getString("data")
                                );
            }
            set = st.executeQuery(" SELECT * FROM twitter.Hashtags WHERE Feed_idFeed = '"+idFeed+"';");
            while(set.next()){
                t.hashtags.add(set.getString("tag"));
            }
            
        }catch (SQLException e){
            Logger.getLogger(DBOperations.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
         
        return t;
    }
     
    
    /**
     * 
     * @param name
     * @return ArrayList(user) ou null
     * @author Gabriel
     */
    public static ArrayList<User> findUserByName(String name){
        User u = null;
        ArrayList<User> users = new ArrayList<>();
        try {
            
            
            Statement st = Main.conn.createStatement();
            ResultSet set = st.executeQuery(" SELECT * FROM twitter.User WHERE name = '"+name+"';");
            
            while(set.next()){
                    u = new User(set.getString("name"),
                                set.getString("idUser"),
                                set.getString("email"),
                                set.getString("country"),
                                set.getString("lang"),
                                set.getString("cellphone")                               
                                );
                    users.add(u);
            }
        
        }catch (SQLException e){
            Logger.getLogger(DBOperations.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null,"Erro Na Inserção do Supermercado");
            return null;
        }
         
        
                 
        return users;         
    }
    /**
     * 
     * @param email
     * @return user ou null
     * @author Gabriel
     */
    public static User findUserByEmail(String email){
        User u = null;
        try {
            
            
            Statement st = Main.conn.createStatement();
            ResultSet set = st.executeQuery(" SELECT * FROM twitter.User WHERE email = '"+email+"';");
            if(set.next()){
                    u = new User(set.getString("name"),
                                set.getString("idUser"),
                                set.getString("email"),
                                set.getString("country"),
                                set.getString("lang"),
                                set.getString("cellphone")                               
                                );
            }
            
        }catch (SQLException e){
            Logger.getLogger(DBOperations.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
         
        return u;
    }
    /**
     * 
     * @param telefone
     * @return user ou null
     * @author GabrielBelarmino e LuizHenrique
     */  
    public static User findUserByTelefone(String telefone){
        User u = null;
        try {
            
            
            Statement st = Main.conn.createStatement();
            ResultSet set = st.executeQuery(" SELECT * FROM twitter.User WHERE cellphone = '"+telefone+"';");
            if(set.next()){
                    u = new User(set.getString("name"),
                                set.getString("idUser"),
                                set.getString("email"),
                                set.getString("country"),
                                set.getString("lang"),
                                set.getString("cellphone")                               
                                );
            }
            
        }catch (SQLException e){
            Logger.getLogger(DBOperations.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
         
        return u;
    }
    /**
     * Deletar em massa: coloca os parametros (campo, valor_minimo) Parametros:
     * valor_minimo == deleta registros maiores ou igual ao valor minimo 
     */
    static boolean clearTables() {
        try {
            
            
            Statement st = Main.conn.createStatement();
            //st.executeUpdate("SET SQL_SAFE_UPDATES = 0");
            st.executeUpdate("DELETE FROM twitter.Following");
            st.executeUpdate("DELETE FROM twitter.Hashtags");
            st.executeUpdate("DELETE FROM twitter.Followers");
            st.executeUpdate("DELETE FROM twitter.Feed");
            st.executeUpdate("DELETE FROM twitter.User");
            
        }catch (SQLException e){
            Logger.getLogger(DBOperations.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null,"Erro Na Inserção do Supermercado");
            return false;
        }
         
        
        return true;

    }
    /**
     * gera uma lista de tt pre determinados e desses, escolhe um aleatoriamente
     * para twittar
     * @param u 
     */  
    public static boolean twittar(User u, String message){
        Twitter t = new Twitter(message,u.user);
        System.out.println(t);
        
        String idFeed;
        try {
            
            
            
            Statement st = Main.conn.createStatement();
            System.out.println(u.tweet.size());
            System.out.println(u.user);
            idFeed = u.user+"-"+u.tweet.size();
            st.executeUpdate("INSERT INTO Feed (idFeed,tweet,favorite,data,User_idUser)"
            + "VALUES('"+idFeed
                        +"','"  
                        +t.content
                        +"','"
                        +t.favorits
                        +"','"        
                        +t.date
                        +"','"+ 
                        t.user+
                        "')");
            
            for(int i = 0; i< t.hashtags.size();i++){
                st.executeUpdate("INSERT INTO `twitter`.`Hashtags`(Feed_User_idUser,Feed_idFeed,tag)"
                    + "VALUES('"+t.user
                    +"','"
                    +idFeed
                    +"','"        
                    +t.hashtags.get(i)+
                    "')");
            }
        }catch (SQLException e){
            Logger.getLogger(DBOperations.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
         
        
        return true;
    }
    
    /**
     * gera uma lista de tt pre determinados e desses, escolhe um aleatoriamente
     * para twittar
     * @param u 
     */
    public static boolean twittarMassivo(User u){
         //lista de twitters aleatorios
       
        String[] tweets = {"#first Primeiro Tweet",
            
                           "#BodyBuilder #Partiu Academia!!",
                           
                           "#Trump MAKE AMERICA GREAT AGAIN! #elections #elections2017",
                           
                           "#elections This election is a total sham and a travesty. We are not a democracy! #Trump",
                           
                           "#NoDiet I have never seen a thin person drinking Diet Coke.",
                           
                           ".@katyperry must have been drunk when she married Russell Brand @rustyrockets – "
                            + " but he did send me a really nice letter of apology!",
                           
                           "Sorry losers and haters, but my I.Q. is one of the highest "
                            + "and you all know it! Please don’t feel so stupid or insecure,it’s not your fault",
                           
                            "Every time I speak of the haters and losers I do so with great love and affection."
                                + "They cannot help the fact that they were born fucked up!",
                            
                            "#HappyGivings Happy Thanksgiving to all--even the haters and losers!",
                            
                            "#GoPackGo",
                            
                            "#Happy2017 Happy New Year to all, including to my many"
                                   + "enemies!",
                            
                            "#trump #trump #trump"
                
                
         };
        
        //pega um tt aleatorio
        String tweet = tweets[(int)Math.ceil(Math.random() * tweets.length)-1];
        Twitter t = new Twitter(tweet,u.user);
        
        String idFeed;
        try {
            
            
            
            Statement st = Main.conn.createStatement();
            idFeed = u.user+"-"+ u.tweet.size();
            st.executeUpdate("INSERT INTO Feed (idFeed,tweet,favorite,data,User_idUser)"
            + "VALUES('"+idFeed
                        +"','"  
                        +t.content
                        +"','"
                        +t.favorits
                        +"','"        
                        +t.date
                        +"','"+ 
                        t.user+
                        "')");
 
            for(int i = 0; i< t.hashtags.size();i++){
                st.executeUpdate("INSERT INTO `twitter`.`Hashtags`(Feed_User_idUser,Feed_idFeed,tag)"
                    + "VALUES('"+t.user
                    +"','"
                    +idFeed
                    +"','"        
                    +t.hashtags.get(i)+
                    "')");
            }
            u.tweet.add(idFeed);
          
        }catch (SQLException e){
            Logger.getLogger(DBOperations.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
         
        
        return true;
    }
    /**
     * 
     * @param hashtag
     * @return ArrayList<Twitter> ou null
     * 
     */ 
    public static ArrayList<Twitter> findHashtags(String hashtag){
        ArrayList<Twitter> tweets = new ArrayList<>();
        try {
            
            
            
            Statement st = Main.conn.createStatement();
            ResultSet set = st.executeQuery("SELECT Feed_idFeed FROM Hashtags WHERE tag = '"+hashtag+"';");
            String id;
            while(set.next()){
                id = set.getString("Feed_idFeed");
                tweets.add(findTwitter(id));
            }
            
        }catch (SQLException e){
            Logger.getLogger(DBOperations.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }       
        return tweets;
    }
}
