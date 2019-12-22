package nonMerci.client;

import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import javax.swing.*;

public class ClientGUI extends JFrame implements ActionListener{

	private String name;
    private ClientImpl joueurImpl;
	
	private static final long serialVersionUID = 1L;	
	public JPanel conteneur, conteneur1, conteneur2, conteneur3,jeux,conteneurBouton,affichage, clientPanel, userPanel;
	private JButton boutonConnexion;

    private JList<String> list;
    private DefaultListModel<String> listModel;
    protected JFrame frame;
	protected JTextField textField;
	
	JLabel label; 
	
	
	protected JButton boutonPass, boutonAccept;
	    
	public static void main(String[] args) {
		new ClientGUI();
	}
	
	public ClientGUI() {
		frame = new JFrame();
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
	        public void windowClosing(java.awt.event.WindowEvent winEvt) {
	        	if(joueurImpl != null) {
					joueurImpl.quitter();
	        	}
	            System.exit(0);
	        }
	    });
		
		label =new JLabel("");
		textField = new JTextField("Entez votre nom");
		textField.setSize(50, 10);
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
		
		jeux = new JPanel();
		affichage = new JPanel();
		
		conteneurBouton = new JPanel();
		
		conteneurBouton.add(textField, "East");
		conteneurBouton.add(boutonConnexion, "West");

		conteneur3 = new JPanel();
		conteneur3.add(boutonAccept, "East");
		conteneur3.add(boutonPass, "West");

		conteneur1.add(conteneurBouton, "North");
		label.setBounds(0, 0, 200, 50);
		jeux.add(label,"Center");
		jeux.add(affichage);
		conteneur1.add(jeux,"Center");
		conteneur1.add(conteneur3, "South");
			
		
		conteneur2 = new JPanel();
		conteneur2.setLayout(new BorderLayout());
		conteneur2.add(getUsersPanel());
		
		conteneur.add(conteneur1, "Center");
		conteneur.add(conteneur2, "West");
		
		affichage.setLayout(new FlowLayout());
		
		frame.add(conteneur);
		frame.pack();
		
		frame.setAlwaysOnTop(true);
		frame.setLocation(300, 300);
	   	frame.setSize(1200, 200);
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
				name = textField.getText();			
				if(name.length() != 0){
					frame.setTitle(name + "'s console ");
					label.setText("En attente de joueur...\n");
					label.repaint();
					label.revalidate();
				
					joueurImpl = new ClientImpl(this, name);
					joueurImpl.start();
					
					boutonConnexion.setEnabled(false);
				}
				else{
					JOptionPane.showMessageDialog(frame, "Entrez un pseudo pour commencer");
				}
			}
			
			if(e.getSource() == boutonAccept) {
				affichage.removeAll();
				clientPanel.repaint();
				clientPanel.revalidate();
				joueurImpl.acceptJoueur();
			}
			if(e.getSource() == boutonPass) {
				joueurImpl.passJoueur();
			}
		} catch (HeadlessException e1) {
			e1.printStackTrace();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}
	

	public void setPlateau(String[] data) {
		label.setText("Carte tirée : " + data[0] + ", Jeton pour la carte : " + data[1]);
	}
}
