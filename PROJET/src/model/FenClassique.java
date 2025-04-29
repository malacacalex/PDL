package model;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.*;
import gui.*;

public class FenClassique {

	JFrame frame;
	private DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenClassique window = new FenClassique();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FenClassique() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Classique");
		frame.setBounds(100, 100, 1060, 640);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel classique = createAddPanel();
		frame.add(classique);
	}

	private JPanel createAddPanel() {
		JPanel tab  = new JPanel();
		DominanteBDD recupdom = new DominanteBDD();
		ArrayList<Dominante>dom = recupdom.getListDom();
		//Colonnes de notre tableau à choix
		String[] colonnes = {"Dominante", "Choix 1","Choix 2"};

		//Lignes de notre tableau à choix
		Object[][]donnees = {
				{dom.get(0).getNom(), false,false},
				{dom.get(1).getNom(), false,false},
				{dom.get(2).getNom(), false,false},
				{dom.get(3).getNom(), false,false},
				{dom.get(4).getNom(), false,false},
				{dom.get(5).getNom(), false,false},
				{dom.get(6).getNom(), false,false},
				{dom.get(7).getNom(), false,false},
				{dom.get(8).getNom(), false,false},
				{dom.get(9).getNom(), false,false},
				{dom.get(10).getNom(), false,false},
				{dom.get(11).getNom(), false,false},
				{dom.get(12).getNom(), false,false},
				{dom.get(13).getNom(), false,false},


		};
		//Objet qui permet de d'organiser notre tableau
		model = new DefaultTableModel(donnees, colonnes) {
			/**
			 * Methode qui permet de retourner les types de nos colonnes
			 */
			public Class<?> getColumnClass(int columnIndex) {
				switch (columnIndex) {
				case 0: return String.class;   
				case 1: return Boolean.class;  
				case 2: return Boolean.class;  


				default: return Object.class;
				}

			}
			/**
			 * 
			 */


		};

		JTable table = new JTable(model);
		table.setBounds(10, 5, 1000, 200);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//Recupere la ligne coche 
				int rowselect =table.getSelectedRow();
				//recupere la colonne coche
				int columnselect = table.getSelectedColumn();
				//Parcours tous les colonnes
				for(int i = 1; i <3; i++) {
					//Si c'est la colonne selectionnée
					if(i==columnselect) {
						//Pour tous les lignes
						for(int j = 0 ; j< table.getRowCount(); j++) {
							//Si une ligne est differente de la ligne ou on clicque
							if (j != rowselect) {
								//Ca met tous les lignes de la colonnes i a false
								table.setValueAt(false, j, i);


							}

						}
						table.setValueAt(true, rowselect, i);
					}

				} 
				for(int i = 0; i <14; i++) {
					//Si c'est la ligne  selectionnée
					if(i==rowselect) {
						//Pour tous les colonnes
						for(int j = 1 ; j< table.getColumnCount(); j++) {
							//Si une colonne est differente de la colonne ou on clicque
							if (j != columnselect) {
								//Ca met tous les lignes de la colonnes i a false
								table.setValueAt(false, i, j);


							}

						}
						table.setValueAt(true, i, columnselect);
					}

				} 
			}

		});
		
		// Bouton qui va permettre de stocker les choix des etudiants dans la BDD
        JButton btnAfficher = new JButton("Valider");
        btnAfficher.setBounds(271, 298, 65, 21);
        btnAfficher.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	ChoixBDD aj = new ChoixBDD();
            	DominanteBDD dominante = new DominanteBDD();
            	ArrayList<Dominante>domi= dominante.getListDom();
                StringBuilder result = new StringBuilder("Options sélectionnées :\n");
                for(int j = 1 ; j < model.getColumnCount();j++) {
                for (int i = 0; i < model.getRowCount(); i++) {
                	//Recupere les choix
                    boolean isSelected = (boolean) model.getValueAt(i, j);
                    //Si l'etudiant a choisi cette domminance en choix j 
                    if (isSelected) {
                    	System.out.println(idEtudiant);
                    	System.out.println(domi.get(i).getidDom());
                    	System.out.println(j);
          
                    	Choix choix = new Choix(dom.get(i).getidDom(),idEtudiant,j);
                         int  d= aj.addChoix(choix);
                    	System.out.println(d);
                    	
                    	
                        result.append(model.getValueAt(i, 0)).append("\n");
                    }
                }
                //JOptionPane.showMessageDialog(null, result.toString());
            }
                JOptionPane.showMessageDialog(null, result.toString());
            }
        });
        

		tab.setLayout(null);

		tab.add(table);
		tab.add(btnAfficher);



		return tab;



	}

}