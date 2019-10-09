package puissance.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

import puissance.server.Information;
import puissance.server.Joueur;

public class JoueurGUI extends JFrame implements ActionListener{

	private String name;
    private JoueurImpl joueurImpl;
    private static Information info;
	
	private static final long serialVersionUID = 1L;	
	private JPanel conteneur, conteneur1, conteneur2, inputPanel, conteneurBouton;
	private JButton boutonConnexion, boutonJoueur, boutonStart;
    private JList<String> list;
    private DefaultListModel<String> listModel;
    protected JFrame frame;
    protected JPanel clientPanel, userPanel;
	protected JTextArea textArea;
	    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new JoueurGUI();
		/*try {
			info = (Information) Naming.lookup("//localhost:8080/TestRMI");
		}  catch (MalformedURLException e) {
		      e.printStackTrace();
	    } catch (RemoteException e) {
	      e.printStackTrace();
	    } catch (NotBoundException e) {
	      e.printStackTrace();
	    }*/
	}
	
	public JoueurGUI() {
		frame = new JFrame();
		
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
	        public void windowClosing(java.awt.event.WindowEvent winEvt) {
	        	if(joueurImpl != null) {
					try {
						System.out.println("sortie");
						joueurImpl.information.removeJoueur(name);
						//info.removeJoueur(name);
					} catch (RemoteException e) {
						e.printStackTrace();
					}		
	        	}
	            System.exit(0);
	        }
	    });
		
		//setTitle("Non Merci");
	
		//frame.setResizable(false);
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//setLocationRelativeTo(null);

		textArea = new JTextArea("Ecrivez votre nom et cliquez sur Connection");
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		boutonConnexion = new JButton("Connection");
		boutonConnexion.addActionListener(this);
		
		boutonStart = new JButton("Debut de la partie");
		boutonStart.setEnabled(false);
		boutonStart.addActionListener(this);
		
		boutonJoueur = new JButton("Afficher Joueur");
		boutonJoueur.setEnabled(false);
		boutonJoueur.addActionListener(this);
		
		conteneur = new JPanel();
		conteneur.setLayout(new BorderLayout());
		
		conteneur1 = new JPanel();
		conteneur1.setLayout(new BorderLayout());
		

		conteneurBouton = new JPanel();
		
		conteneurBouton.add(boutonConnexion, "West");
		conteneurBouton.add(boutonStart, "East");
		

		conteneur1.add(conteneurBouton, "North");
		conteneur1.add(textArea, "Center");
		conteneur1.add(boutonJoueur, "South");
		
		
		conteneur2 = new JPanel();
		conteneur2.setLayout(new BorderLayout());
		conteneur2.add(getUsersPanel());

		conteneur.add(conteneur1, "Center");
		conteneur.add(conteneur2, "West");
		
		frame.add(conteneur);
		frame.pack();

		frame.setAlwaysOnTop(true);
		frame.setLocation(300, 300);
	   	frame.setSize(500, 500);
		textArea.requestFocus();
	
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);

	}
	
	private Component getUsersPanel() {
		
		userPanel = new JPanel(new BorderLayout());
		String  userStr = " Current Users      ";
		
		JLabel userLabel = new JLabel(userStr, JLabel.CENTER);
		userPanel.add(userLabel, BorderLayout.NORTH);	
		userLabel.setFont(new Font("Meiryo", Font.PLAIN, 16));

		String[] noClientsYet = {"No other users"};
		setClientPanel(noClientsYet);


		return userPanel;	
	}

    public void setClientPanel(String[] currClients) {  	
    	clientPanel = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<String>();
        
        for(String s : currClients){
        	listModel.addElement(s);
        }
        
        //Create the list and put it in a scroll pane.
        list = new JList<String>(listModel);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.setVisibleRowCount(8);
        JScrollPane listScrollPane = new JScrollPane(list);

        clientPanel.add(listScrollPane, BorderLayout.CENTER);
        userPanel.add(clientPanel, BorderLayout.CENTER);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			//get connected to service
			if(e.getSource() == boutonConnexion){
				System.out.println("boutonStart");
				//if(!info.joueurExistant(textArea.getText())) {
					name = textArea.getText();			
					if(name.length() != 0){
						frame.setTitle(name + "'s console ");
						textArea.setText("");
						textArea.append("username : " + name + " connecting...\n");		
						
						joueurImpl = new JoueurImpl(this, name);
						joueurImpl.start();
						//info.saveJoueur(name);
						
						boutonConnexion.setEnabled(false);
						boutonJoueur.setEnabled(true);
						boutonStart.setEnabled(true);
					}
					else{
						JOptionPane.showMessageDialog(frame, "Entrez un pseudo pour commencer");
					}
				/*}
				else{
					JOptionPane.showMessageDialog(frame, "Pseudo déjà utilisé");
				}*/
				
			}
			
			if(e.getSource() == boutonJoueur) {
				System.out.println("boutonJoueur");
				//todo
				getJoueurs();
			}
			
			if(e.getSource() == boutonStart) {
				
			}
		} catch (HeadlessException e1) {
			e1.printStackTrace();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}
	
	private void getJoueurs() {
		try {
			String[] noClientsYet;
			noClientsYet = info.getJoueurList();
			setClientPanel(noClientsYet);

			clientPanel.repaint();
			clientPanel.revalidate();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
