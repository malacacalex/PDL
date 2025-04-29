package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import gui.Choix;
import gui.Etudiant;


public class ChoixBDD extends ConnexionBDD {
	public ChoixBDD() { 

		super();  

		} 
	/**
	 * 
	 * @param choix
	 * @return
	 */
	public int addChoix(Choix choix) {
		Connection con = null;
		PreparedStatement ps = null;
		int returnValue=0;

		// connexion a la base de donnees
		try {

			// tentative de connexion
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			// preparation de l'instruction SQL, chaque ? represente une valeur
			// a communiquer dans l'insertion.
			// les getters permettent de recuperer les valeurs des attributs souhaites
			ps = con.prepareStatement("INSERT INTO choix(ch_dom_id,ch_etu_id,ch_priorite) VALUES(?, ?, ?)");
			ps.setInt(1, choix.getdomId());
			ps.setString(2, choix.getId());
			ps.setInt(3, choix.getchoixPriorite());
            System.out.println(choix.getdomId());
            System.out.println(choix.getId());
			// Execution de la requete
			returnValue = ps.executeUpdate();

		} catch (Exception e) {
			if (e.getMessage().contains("ORA-00001"))
				System.out.println("Cet identifiant de fournisseur existe déjà. Ajout impossible !");
			else
				e.printStackTrace();
		} finally {
			// fermeture du preparedStatement et de la connexion
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception ignore) {
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ignore) {
			}
		}
		return returnValue;
		
	}
	public static ArrayList<Choix> getListeChoix() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Choix> returnValue = new ArrayList<Choix>();

		// connexion a la base de donnees
		try {
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT * FROM choix ");

			// on execute la requete
			rs = ps.executeQuery();
			// on parcourt les lignes du resultat
			while (rs.next()) {
				returnValue.add(new Choix(rs.getInt("ch_dom_id"),
						                     rs.getString("ch_etu_id"),
						                     
						                     rs.getInt("ch_priorite")));
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			// fermeture du rs, du preparedStatement et de la connexion
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ignore) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception ignore) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ignore) {
			}
		}
		return returnValue;
	}
	/**
	 * Method which return the choices by an 
	 */
	
 
	public ArrayList <Choix> getChoix(String id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Choix> returnValue = new ArrayList<Choix>();

		// connexion a la base de donnees
		try {

			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT * FROM choix  WHERE ch_etu_id  = ? ORDER BY ch_priorite ASC");
			ps.setString(1, id);

			// on execute la requete
			// rs contient un pointeur situe juste avant la premiere ligne retournee
			rs = ps.executeQuery();
			// passe a la premiere (et unique) ligne retournee
			while (rs.next()) {
				returnValue.add(new Choix(rs.getInt("ch_dom_id"),
	                     rs.getString("ch_etu_id"),
	                     
	                     rs.getInt("ch_priorite")));
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			// fermeture du ResultSet, du PreparedStatement et de la Connexion
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception ignore) {
			}
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception ignore) {
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ignore) {
			}
		}
		return returnValue;
	}
}
