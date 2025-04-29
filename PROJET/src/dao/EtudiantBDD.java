package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import gui.Administrateur;
import gui.Etudiant;







public class EtudiantBDD extends ConnexionBDD{
	public EtudiantBDD() {
		super();
	}
	/**
	 * Qui permet de retourner les identifiants , le mdp et le statut de l'etudiant  de la BDD
	 * @return
	 */
	
public static ArrayList<Etudiant> getListeEtudiant(){
	// Objet  qui permet d'etablir une connexion a notre BDD
	Connection con = null;
	//Objet pour executer des requetes SQL
	PreparedStatement ps = null;
	//Objet qui permet de stocker le resultat des donnees d'une requete SQL
	ResultSet rs = null;
	ArrayList<Etudiant> returnValue = new ArrayList<Etudiant>();

	// connexion a la base de donnees
	try {
		con = DriverManager.getConnection(URL, LOGIN, PASS);
		ps = con.prepareStatement("SELECT * FROM etudiant ");

		// on execute la requete
		rs = ps.executeQuery();
		// on parcourt les lignes du resultat
		
		while (rs.next()) {
			returnValue.add(new Etudiant(
					                     rs.getString("etu_identifiant"),
					                     rs.getString("etu_mdp"),
					                     rs.getString("etu_statut")));
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
public int updateEtudiant(Etudiant etudiant ) {
	Connection con = null;
	PreparedStatement ps = null;
	int returnValue = 0;

	// connexion a la base de donnees
	try {

		// tentative de connexion
		con = DriverManager.getConnection(URL, LOGIN, PASS);
		// preparation de l'instruction SQL, chaque ? represente une valeur
		// a communiquer dans la modification.
		// les getters permettent de recuperer les valeurs des attributs souhaites
		ps = con.prepareStatement("UPDATE etudiant  set  etu_nom = ?, etu_prenom = ?, etu_mdp =?, etu_date_naissance = ? , etu_classement = ?,etu_statut = ?, etu_entreprise = ?,etu_cotrat = ?, etu_mobilite = ?,etu_choix_final_id = ?, etu_id_promo= ?, WHERE etu_identifiant = ?");
		ps.setString(12, etudiant .getId());
		ps.setString(1, etudiant.getNom());
		ps.setString(2, etudiant.getPrenom());
		ps.setString(3, etudiant.getMdp());
		ps.setString(4, etudiant.getDatedeNaissance());
		ps.setInt(5, etudiant.getClassement());
		ps.setString(6, etudiant.getStatut());
		ps.setString(7, etudiant.getEntreprise());
		ps.setString(8, etudiant.getContrat());
		ps.setString(9, etudiant.getMobilite());
		ps.setInt(10, etudiant.getChoixFinal());
		ps.setInt(11, etudiant.getIdentifiantPromo());
		
		
		
		
		

		// Execution de la requete
		returnValue = ps.executeUpdate();

	} catch (Exception e) {
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

    /**
     * Methode qui permet d'ajouter un etudiant
     * @param supplier
     * @return
     */
public int addEtudiant(Etudiant etudiant) {
    Connection con = null;
    PreparedStatement ps = null;
    int returnValue = 0;

    try {
        con = DriverManager.getConnection(URL, LOGIN, PASS);

        ps = con.prepareStatement("INSERT INTO etudiant (etu_identifiant, etu_nom, etu_prenom, etu_mdp, etu_date_naissance, etu_classement, etu_statut, etu_entreprise, etu_contrat, etu_mobilite, etu_choix_final_id, etu_promo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        ps.setString(1, etudiant.getId());
        ps.setString(2, etudiant.getNom());
        ps.setString(3, etudiant.getPrenom());
        ps.setString(4, etudiant.getMdp());

        String dateStr = etudiant.getDatedeNaissance(); // exemple : "2000-01-15"
        if (dateStr != null && !dateStr.isEmpty()) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = formatter.parse(dateStr);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            ps.setDate(5, sqlDate);
        } else {
            ps.setNull(5, java.sql.Types.DATE);
        }

        ps.setInt(6, etudiant.getClassement());
        ps.setString(7, etudiant.getStatut());
        ps.setString(8, etudiant.getEntreprise());
        ps.setString(9, etudiant.getContrat());
        ps.setString(10, etudiant.getMobilite());
        ps.setInt(11, etudiant.getChoixFinal());
        ps.setInt(12, etudiant.getIdentifiantPromo());

        returnValue = ps.executeUpdate();

    } catch (Exception e) {
        if (e.getMessage().contains("ORA-00001"))
            System.out.println("Cet identifiant de fournisseur existe déjà. Ajout impossible !");
        else
            e.printStackTrace();
    } finally {
        try {
            if (ps != null) ps.close();
        } catch (Exception ignore) {}
        try {
            if (con != null) con.close();
        } catch (Exception ignore) {}
    }
    return returnValue;
}
   public Etudiant  getEtudiant(String id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Etudiant  returnValue = null;

		// connexion a la base de donnees
		try {

			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT * FROM etudiant WHERE etu_identifiant  = ?");
			ps.setString(1, id);

			// on execute la requete
			// rs contient un pointeur situe juste avant la premiere ligne retournee
			rs = ps.executeQuery();
			// passe a la premiere (et unique) ligne retournee
			if (rs.next()) {
				returnValue = new Etudiant(rs.getString("etu_identifiant"),
					       rs.getString("etu_nom"),
					       rs.getString("etu_prenom"),
					       rs.getString("etu_mdp"),
					       rs.getString("etu_date_naissance"),
					       rs.getInt("etu_classement"),
					       rs.getString("etu_statut"),
					       rs.getString("etu_entreprise"),
					       rs.getString("etu_contrat"),
					       rs.getString("etu_mobilite"),
					       rs.getInt("etu_choix_final_id"),
					       rs.getInt("etu_promo"));
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
   public int updateEtuPlaces(Etudiant etudiant) {
	   Connection con = null;
		PreparedStatement ps = null;
		int returnValue = 0;

		// connexion a la base de donnees
		try {

			// tentative de connexion
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			// preparation de l'instruction SQL, chaque ? represente une valeur
			// a communiquer dans la modification.
			// les getters permettent de recuperer les valeurs des attributs souhaites
			ps = con.prepareStatement("UPDATE etudiant  set  etu_choix_final_id = ? WHERE etu_identifiant = ?");
			ps.setInt(1, etudiant.getChoixFinal());
			ps.setString(2, etudiant.getId());
			
			
			
			
			

			// Execution de la requete
			returnValue = ps.executeUpdate();

		} catch (Exception e) {
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
   
   public static boolean update(Etudiant etu) {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean isValid = false;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = conn.prepareStatement("UPDATE etudiant SET etu_mdp = ? where etu_identifiant = ?");
			ps.setString(1, etu.getMdp());
			ps.setString(2, etu.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				isValid = true; 
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (conn != null) conn.close();
			} catch (Exception ignore) {
			}
		}
		return isValid;


	}


}

