package modele;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
public class UserDB extends User implements CRUD {

    protected static Connection dbConnect=null;

  public UserDB(){
	  
  }
  
  
   
  public UserDB(int id_user, String pseudo, String email,
		String password) {
	super(id_user, pseudo,  email, password);
	// TODO Auto-generated constructor stub
}

  public UserDB( String pseudo, String email,
		String password) {
	super( pseudo,  email, password);
	// TODO Auto-generated constructor stub
}
  

public UserDB(int id_user) {
	super(id_user);
	// TODO Auto-generated constructor stub
}



/**
   * m�thode statique permettant de partager la connexion entre toutes les instances de
   * ChambreDB
   * @param nouvdbConnect connexion � la base de donn�es
   */
   public static void setConnection(Connection nouvdbConnect) {
      dbConnect=nouvdbConnect;
   }

  /**
   * enregistrement d'une nouvelle chambre dans la base de donn�es
   * @throws Exception erreur de cr�ation
   */
   public void create() throws Exception{
        CallableStatement   cstmt=null;
       try{
            String query1="INSERT INTO users(pseudo, mail, password) VALUES(?,?,?)";
            String query2="select user_seq.currval from dual" ;
            PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
            PreparedStatement pstm2 = dbConnect.prepareStatement(query2);
            pstm1.setString(1,pseudo);
            pstm1.setString(2,email);
            pstm1.setString(3,password);
            int nl = pstm1.executeUpdate();
            ResultSet rs = pstm2.executeQuery();
            if(rs.next()){
            	int nc = rs.getInt(1);
            	id_user = nc;
            }else{
            	System.out.println("Erreur de l'ajout");
            }
            
       }
       catch(Exception e ){
                throw new Exception("Erreur de cr�ation "+e.getMessage());
             }
       finally{
            try{
              cstmt.close();
            }
            catch (Exception e){}
        }
       }
   
/**
 * r�cup�ration des donn�es d'une chambre sur base de son numero
 * @throws Exception code inconnu
 */
   public static ArrayList<UserDB> read_all() throws Exception{
    CallableStatement cstmt=null;
    ArrayList<UserDB> plusieurs=new ArrayList<UserDB>();
        try{
             String query1="SELECT * FROM user";
             PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             ResultSet rs= pstm1.executeQuery();
             while(rs.next()){
                 int numeroTmp = rs.getInt("ID_USER");
                 String pseudoTmp = rs.getString("PSEUDO");
                 String mailTmp = rs.getString("MAIL");
                 String passwordTmp = rs.getString("PASSWORD");
                 UserDB temp = new UserDB(numeroTmp, pseudoTmp,mailTmp,passwordTmp);
                 plusieurs.add(temp);
             }
            return plusieurs;
        }
	catch(Exception e){
             
                throw new Exception("Erreur de lecture "+e.getMessage());
             }
        finally{
            try{
              cstmt.close();
            }
            catch (Exception e){}
        }
}
   public void read(int numeroTmp)throws Exception{
       
        CallableStatement cstmt=null;
        try{
            boolean trouve=false;
             String query1="SELECT * FROM chambre WHERE numero = ?";
             PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             pstm1.setInt(1,numeroTmp);
             ResultSet rs= pstm1.executeQuery();
             if(rs.next()){
                 trouve = true;
                 id_user = rs.getInt("ID_USER");
                 pseudo = rs.getString("PSEUDO");
                 email = rs.getString("MAIL");
                 password = rs.getString("PASSWORD");
             }
             if(!trouve)throw new Exception("numero inconnu dans la table !");
        }
	catch(Exception e){
             
                throw new Exception("Erreur de lecture "+e.getMessage());
             }
        finally{//effectu� dans tous les cas 
            try{
              cstmt.close();
            }
            catch (Exception e){}
        }
     }
/**
 * m�thode statique permettant de r�cup�rer toutes les chambre portant un certain numero
 * @param rechNumero numero recherch�
 * @return liste des chambres
 * @throws Exception nom inconnu
 */
   public static ArrayList<UserDB> rechNumero(int rechNumero)throws Exception{
	    ArrayList<UserDB> plusieurs=new ArrayList<UserDB>();
	    CallableStatement cstmt=null;
	    try{
                boolean trouve=false;
                String query1="SELECT * FROM chambre WHERE numero = ?";
                PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
                pstm1.setInt(1,rechNumero);
                ResultSet rs= pstm1.executeQuery();
                if(rs.next()){
                    trouve = true;
                    int numeroTmp=rs.getInt("NUMERO");
                    String pseudoTmp=rs.getString("PSEUDO");
                    String mailTmp=rs.getString("MAIL");
                    String passwordTmp=rs.getString("PASSWORD");
                    plusieurs.add(new UserDB(numeroTmp, pseudoTmp, mailTmp,passwordTmp ));
                }
                
              if(!trouve)throw new Exception("nom inconnu");
              else return plusieurs;
	     }
	     catch(Exception e){
		
                throw new Exception("maj echou�");
             }
            finally{
            try{
              cstmt.close();
            }
            catch (Exception e){}
        }
     }
	
     /**
      * mise � jour des donn�es de la chambre sur base de son numero
      * @throws Exception erreur de mise � jour
      */

    public void update() throws Exception{
        CallableStatement cstmt=null;

    try{
            String query1="UPDATE chambre SET pseudo = ?, password = ? WHERE id_user = ?";
            PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
            pstm1.setString(1,pseudo);
            pstm1.setString(2,email);
            pstm1.setString(3,password);
            int nl = pstm1.executeUpdate();
            if(nl == 1)
            {
                System.out.println("La ligne a bien �t� mise a jour !");
            }else{
                System.out.println("Aucune ligne n'a �t� mise a jour !");
            }
            
    }

	  catch(Exception e){
	  	
                throw new Exception("Erreur de mise � jour : "+e.getMessage());
             }
          finally{//effectu� dans tous les cas 
            try{
              cstmt.close();
            }
            catch (Exception e){}
        }
    }
 
 /**
  * effacement de la chambre sur base de son numero
  * @throws Exception erreur d'effacement
  */
    public void delete()throws Exception{
	
           CallableStatement cstmt =null;
	   try{
               String query1="DELETE users WHERE id_user = ?";
               PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
               pstm1.setInt(1,id_user);
               int nl = pstm1.executeUpdate();
               if(nl == 1)
               {
                   System.out.println("La ligne a bien �t� suprim� !");
               }else{
                   System.out.println("Aucune ligne n'a �t� suprim� !");
               }
             
	   }	
	   catch (Exception e){
	     	
                throw new Exception("Erreur d'effacement : "+e.getMessage());
             }
           finally{
            try{
              cstmt.close();
            }
            catch (Exception e){}
          }
   	}

    @Override
    public void read() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}

