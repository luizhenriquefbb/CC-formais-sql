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
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author gabriel e luiz
 */
public class Main {
    public static Connection conn = null; 
    public static final String serverName = "127.0.0.1";
    public static final String mydatabase = "twitter";  
    //public static final String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
    public static final String url = "jdbc:mysql://localhost:3306/"+mydatabase+"?autoReconnect=true&useSSL=false";
    public static final String username = "root";
    public static final String password = "030465";
    public static Statement st;
    public static void main(String[] args) {
        try{
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(url, username, password);
        st = Main.conn.createStatement();
        int op;
        //buidCollectionUser();
        //buildCollectionTwitter();
        
        long tempoInicial;
        User user_obj; //resultado dos retornos do menu
        Twitter tw;
        //leituras de teclado
        int x;
        String name, nick, user, email, country, language, telephone; 
        String message, hashtag;
        do{
            System.out.println(menu());
            Scanner read = new Scanner(System.in);
            op = Integer.parseInt(read.nextLine());
            
            switch(op){
                case 0:
                    break;
                    
                case 1: //gerar x usuários genéricos automaticamente
                    System.out.println("Quantos usuários genéricos criar?");
                    x = read.nextInt();
                    
                    tempoInicial = System.currentTimeMillis();
                    
                    DBOperations.generateUsers(x);
                    
                    System.out.println("o metodo gerou esses usuarios em " +(float)(  System.currentTimeMillis() - tempoInicial) + 
                            "milisegudos");
                    break;
                     
                case 2: //gerar um usuario personalizado
                    System.out.println("digite nome do usuário");
                    name = read.nextLine();
                    
                    System.out.println("digite user do usuário");
                    user = read.nextLine();
                    
                    System.out.println("digite email do usuário");
                    email = read.nextLine();
                    System.out.println("digite pais do usuário");
                    country = read.nextLine();
                    
                    System.out.println("digite lingua do usuário");
                    language = read.nextLine();
                    
                    System.out.println("digite telefone do usuário");
                    telephone = read.nextLine();
                    
                    tempoInicial = System.currentTimeMillis();
                    
                    DBOperations.insertUser(new User(name, user, email, country, language, telephone));
                    
                    System.out.println("o metodo gerou esse usuario em " +(float)(  System.currentTimeMillis() - tempoInicial) + 
                            "milisegudos");
                    break;
                    
                case 3: //busca por nome
                    System.out.println("Digite nome a ser buscado:");
                    name = read.nextLine();
                    tempoInicial = System.currentTimeMillis();
                    
                    ArrayList<User> search = DBOperations.findUserByName(name);
                    
                    System.out.println("busca executada em " +(float)(  System.currentTimeMillis() - tempoInicial) + " milisegundos");
                    
                    System.out.println("encontrado "+search.size()+" usuários\n");
                    for (User u: search){
                        System.out.println(u);
                    }
                    break;
                    
                case 4: //busca por telefone
                    System.out.println("Digite telefone a ser buscado:");
                    telephone = read.nextLine();
                    tempoInicial = System.currentTimeMillis();
                    
                    user_obj = DBOperations.findUserByTelefone(telephone);
                    System.out.println("busca executada em " +(float)(  System.currentTimeMillis() - tempoInicial) + " milisegundos");
                    if (user_obj != null){
                        System.out.println("retorno da busca:\n"
                                + user_obj);
                    }else{
                        System.out.println("nenhum encontrado");
                    }
                    break;
                    
                    
                case 5: //bus por nick
                    System.out.println("Digite nick a ser buscado:");
                    nick = read.nextLine();
                    tempoInicial = System.currentTimeMillis();
                    
                    user_obj = DBOperations.findUserByNick(nick);
                    System.out.println("busca executada em " +(float)(  System.currentTimeMillis() - tempoInicial) + " milisegundos");
                    if (user_obj != null){
                        System.out.println("retorno da busca:\n"
                                + user_obj);
                    }else{
                        System.out.println("nenhum encontrado");
                    }
                    break;
                    
                    
                case 6: //buscar por email
                   System.out.println("Digite email a ser buscado:");
                    email = read.nextLine();
                    tempoInicial = System.currentTimeMillis();
                    
                    user_obj = DBOperations.findUserByEmail(email);
                    System.out.println("busca executada em " +(float)(  System.currentTimeMillis() - tempoInicial) + " milisegundos");
                    if (user_obj != null){
                        System.out.println("retorno da busca:\n"
                                + user_obj);
                    }else{
                        System.out.println("nenhum encontrado");
                    }
                    break;
                    
                case 7: //remover usuário
                    System.out.println("Digite nick a ser removido:");
                    nick = read.nextLine();
                    tempoInicial = System.currentTimeMillis();
                    
                    boolean success = DBOperations.DeleteNick(nick);
                    System.out.println("busca executada em " +(float)(  System.currentTimeMillis() - tempoInicial) + " milisegundos");
                    if (!success){
                        System.out.println("Remoção Deu Erro!");
                    }
                    break;
                    
                case 8: //limpar todas tabelas
                    tempoInicial = System.currentTimeMillis();
                    
                    DBOperations.clearTables();
                    System.out.println("tabela limpa em " +(float)(  System.currentTimeMillis() - tempoInicial) + " milisegundos");
                    break;
                    
                    
                case 9: //twittar
                    System.out.println("digite o user que twitta");
                    nick = read.nextLine();
                    user_obj = DBOperations.findUserByNick(nick);
                    if (user_obj == null){
                        System.err.println("usuario nao existe");
                        break;
                    }
                    
                    System.out.println("digite o TWITTER");
                    message = read.nextLine();
                    DBOperations.twittar(user_obj,message);
                    break;
                    
                case 10://Twittar Massivo
                    System.out.println("Quantos Usuarios");
                    int n_user = read.nextInt();
                    System.out.println("Quantos Tweets");
                    int n_tweets = read.nextInt();
                    
                    tempoInicial = System.currentTimeMillis();
                    for(int i = 0;i < n_user; i++){
                        user_obj = DBOperations.findUserByNick("@user"+i);
                        for(int j = 0; j < n_tweets; j++){
                            DBOperations.twittarMassivo(user_obj);
                        }     
                    }
                    System.out.println("o metodo executou em " +(float)(  System.currentTimeMillis() - tempoInicial) + " milisegundos");
                    break;
              
                case 11://Buscar Twitter
                    System.out.println("Digtre i idFeed");
                    String idFeed = read.nextLine();
                    
                    tempoInicial = System.currentTimeMillis();
                    
                    tw = DBOperations.findTwitter(idFeed);
                    if (tw != null){
                        System.out.println("retorno da busca:\n"
                                + tw);
                    }else{
                        System.out.println("nenhum encontrado");
                    }
                    System.out.println("busca executada em " +(float)(  System.currentTimeMillis() - tempoInicial) + " milisegundos");

                    break;
                    
                case 12: //Buscar uma hashtag
                    System.out.println("Digite a hashtag a ser buscada");
                    hashtag = read.nextLine();
                    tempoInicial = System.currentTimeMillis();

                    ArrayList<Twitter> search_twitter = DBOperations.findHashtags(hashtag);
                    System.out.println("busca executada em " +(float)(  System.currentTimeMillis() - tempoInicial) + " milisegundos");

                    if (search_twitter == null){
                        System.out.println("hashtag nao encontrada");
                        break;
                    }
                    
                    System.out.println("foram encontradas "+ search_twitter.size() + 
                            " twitters com essa hash");
                    for (Twitter t : search_twitter){
                        System.out.println(t);
                    }                  
                    break;
                   
                case 13: //usuario seguir outro
                    System.out.println("Digite o user que sera Seguido");
                    User a = DBOperations.findUserByNick(read.nextLine());
                    System.out.println("Digite o user que sera Seguidor");
                    User b = DBOperations.findUserByNick(read.nextLine());
                    DBOperations.generateFollower(a,b);
                    break;
            }
        }while (op != 0);
        conn.close();
                } catch (ClassNotFoundException e) {
            Logger.getLogger(DBOperations.class.getName()).log(Level.SEVERE, null, e);
        }catch (SQLException e){
            Logger.getLogger(DBOperations.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
 
//    /**Configura a collection*/
//    private static void buidCollectionUser(){
//        //setando 'user' como indice unique
//        IndexOptions indexOptions = new IndexOptions().unique(true);
//        collection_users.createIndex(Indexes.ascending("User"), indexOptions);
//        
//    }
//    
//    /**Configura a collection*/
//    private static void buildCollectionTwitter(){
//        //setando '_id' como indice unique
//        IndexOptions indexOptions = new IndexOptions().unique(true);
//        collection_twitteres.createIndex(Indexes.ascending("Code"), indexOptions);
//    }
      
    private static String menu(){
        int i=0;
        return(
                  "\n\n\n\n\n======================\n"
                + (i++) +" - sair\n"
                + (i++) +" - gerar x usuários genericos\n"
                + (i++) +" - adicionar um usuario\n\n"
                
                + (i++) +" - buscar por nome\n"
                + (i++) +" - buscar usuario por telefone\n"
                + (i++) +" - buscar por nick\n"
                + (i++) +" - buscar por email\n\n"
                
                + (i++) +" - remover usuario\n"
                + (i++) +" - limpar tabelas\n\n"
                
                + (i++) +" - twittar\n"
                + (i++) +" - Varios Tweets\n"
                + (i++) +" - Buscar um Twitter pelo id\n\n"
                + (i++) +" - Buscar uma hashtag\n\n"
                
                + (i++) +" - usuario seguir outro\n"
                + "=====================");
        
    }
    
}
