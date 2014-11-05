package modele;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CarnetDB extends Carnet implements CRUD {

    protected static Connection dbConnect=null;

  public CarnetDB(){
   	}
  
    


public CarnetDB(int id_carnet, String titre, int id_user) {
	super(id_carnet, titre, id_user);
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
            String query1="INSERT INTO carnet(id_carnet,titre,id_user) VALUES(?,?,?)";
            //String query2="select client_seq.currval from dual" ;
            PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
            pstm1.setInt(1,id_carnet);
            pstm1.setString(2,titre);
            pstm1.setInt(3,id_user);
            int nl = pstm1.executeUpdate();
            
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
   public static ArrayList<CarnetDB> read_all() throws Exception{
    CallableStatement cstmt=null;
    ArrayList<CarnetDB> plusieurs=new ArrayList<CarnetDB>();
        try{
             String query1="SELECT * FROM carnet";
             PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             ResultSet rs= pstm1.executeQuery();
             while(rs.next()){
                 int id_carnetTmp = rs.getInt("ID_CARNET");
                 String TitreTmp = rs.getString("TITRE");
                 int Id_userTmp = rs.getInt("ID_USER");
                 CarnetDB temp = new CarnetDB(id_carnetTmp, TitreTmp, Id_userTmp);
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


     /**
      * mise � jour des donn�es de la chambre sur base de son numero
      * @throws Exception erreur de mise � jour
      */

    public void update() throws Exception{
        CallableStatement cstmt=null;

    try{
            String query1="UPDATE carnet SET titre = ? where id_carnet= ?";
            PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
            pstm1.setInt(1,id_carnet);
            pstm1.setString(2,titre);
            pstm1.setInt(3,id_user);
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
               String query1="DELETE carnet WHERE id_carnet = ?";
               PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
               pstm1.setInt(1,id_carnet);
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
