package nonMerci.client;

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

import nonMerci.server.IServeur;
import nonMerci.server.Joueur;

public class ClientGUI extends JFrame implements ActionListener{

	private String name;
    private ClientImpl joueurImpl;
    private static IServeur info;
	
	private static final long serialVersionUID = 1L;	
	private JPanel conteneur, conteneur1, conteneur2, conteneur3, inputPanel, conteneurBouton;
	private JButton boutonConnexion, boutonJoueur, boutonStart;

    private JList<String> list;
    private DefaultListModel<String> listModel;
    protected JFrame frame;
    protected JPanel clientPanel, userPanel;
	protected JTextField textField;
	protected JTextArea textArea;
	protected JButton boutonPass, boutonAccept;
	    
	public static void main(String[] args) {
		new ClientGUI();
	}
	
	public ClientGUI() {
		frame = new JFrame();
		
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
	        public void windowClosing(java.awt.event.WindowEvent winEvt) {
	        	if(joueurImpl != null) {
					/*try {
						System.out.println("sortie");
						joueurImpl.information.removeJoueur(name);
						//info.removeJoueur(name);
					} catch (RemoteException e) {
						e.printStackTrace();
					}		*/
	        	}
	            System.exit(0);
	        }
	    });
		
		//setTitle("Non Merci");
	
		//frame.setResizable(false);
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//setLocationRelativeTo(null);

		textArea = new JTextArea("");
		textField = new JTextField("Entez votre nom");
		textField.setSize(50, 10);
		//textField.setLineWrap(true);
		//textField.setWrapStyleWord(true);
		boutonConnexion = new JButton("Connection");
		boutonConnexion.addActionListener(this);
				
		boutonAccept = new JButton("Accepter");
		boutonAccept.setEnabled(false);
		boutonAccept.addActionListener(this);
		
		boutonPass = new JButton("Passer");
		boutonPass.setEnabled(false);
		boutonPass.addActionListener(this);
		
		conteneur = new JPanel();
		conteneur.setLayout(new BorderLayout());
		
		conteneur1 = new JPanel();
		conteneur1.setLayout(new BorderLayout());
		

		conteneurBouton = new JPanel();
		
		conteneurBouton.add(textField, "East");
		conteneurBouton.add(boutonConnexion, "West");

		conteneur3 = new JPanel();
		conteneur3.add(boutonAccept, "East");
		conteneur3.add(boutonPass, "West");

		conteneur1.add(conteneurBouton, "North");
		conteneur1.add(textArea, "Center");
		conteneur1.add(conteneur3, "South");
			
		
		conteneur2 = new JPanel();
		conteneur2.setLayout(new BorderLayout());
		conteneur2.add(getUsersPanel());
		
		conteneur.add(conteneur1, "Center");
		conteneur.add(conteneur2, "West");
		
		frame.add(conteneur);
		frame.pack();

		frame.setAlwaysOnTop(true);
		frame.setLocation(300, 300);
	   	frame.setSize(400, 500);
		textField.requestFocus();
	
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
		setUsersPanel(noClientsYet);


		return userPanel;	
	}

    public void setUsersPanel(String[] currClients) {  	
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
					name = textField.getText();			
					if(name.length() != 0){
						frame.setTitle(name + "'s console ");
						//textField.setText("");
						textArea.setText("username : " + name + " connecting...\n");		
						
						joueurImpl = new ClientImpl(this, name);
						joueurImpl.start();
						
						boutonConnexion.setEnabled(false);
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
				getJoueurs();
			}
			
			if(e.getSource() == boutonAccept) {
				joueurImpl.acceptJoueur(name);
			}
			
			if(e.getSource() == boutonPass) {
				joueurImpl.passJoueur(name);
			}
		} catch (HeadlessException e1) {
			e1.printStackTrace();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}
	
	private void getJoueurs() {
		/*try {
			String[] noClientsYet;
			noClientsYet = info.getJoueurList();
			setUsersPanel(noClientsYet);

			clientPanel.repaint();
			clientPanel.revalidate();
		} catch (RemoteException e) {
			e.printStackTrace();
		}*/
	}

	public void setPlateau(String[] data) {
		textArea.append("Carte tirée : " + data[0] + ", Jeton pour la carte : " + data[1] + "\n");
		
	}
}
