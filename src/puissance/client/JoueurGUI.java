package puissance.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class JoueurGUI extends JFrame implements ActionListener{

	private JoueurImpl clientImpl;
	private static final long serialVersionUID = 1L;	
	private JPanel conteneur, conteneur1, conteneur2, inputPanel;
	private JTextArea textArea;
	private JButton boutonStart;
	private String name;
    private JList<String> list;
    private DefaultListModel<String> listModel;
    private JoueurImpl joueurImpl;
    
    protected JFrame frame;
    protected JPanel clientPanel, userPanel;
	
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JoueurGUI gui = new JoueurGUI();
		gui.setVisible(true);
	}
	
	public JoueurGUI() {
		frame = new JFrame();
		
		setTitle("Puissance 4 cardgame");
	
		//frame.setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLocationRelativeTo(null);

		textArea = new JTextArea("Bienvenue, écrivez votre nom et tapez entrée pour commencer");
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		boutonStart = new JButton("Start");
		
		conteneur = new JPanel();
		conteneur.setLayout(new BorderLayout());
		
		conteneur1 = new JPanel();
		conteneur1.setLayout(new BorderLayout());
		
		conteneur1.add(boutonStart, "North");
		conteneur1.add(textArea, "Center");
		
		
		conteneur2 = new JPanel();
		conteneur2.setLayout(new BorderLayout());
		conteneur2.add(getUsersPanel());
		//conteneur2.setSize(50, 300);

		conteneur.add(conteneur1, "Center");
		conteneur.add(conteneur2, "West");
		
		frame.add(conteneur);
		frame.pack();

		frame.setAlwaysOnTop(true);
		frame.setLocation(150, 150);
	   	frame.setSize(400, 300);
		textArea.requestFocus();
	
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
		//pack();
			/*
		Container c = getContentPane();
		JPanel outerPanel = new JPanel(new BorderLayout());
		
		outerPanel.add(getInputPanel(), BorderLayout.CENTER);
		outerPanel.add(getTextPanel(), BorderLayout.NORTH);
		
		c.setLayout(new BorderLayout());
		c.add(outerPanel, BorderLayout.CENTER);
		c.add(getUsersPanel(), BorderLayout.WEST);

		frame.add(c);
		frame.pack();
		frame.setAlwaysOnTop(true);
		frame.setLocation(150, 150);
		textField.requestFocus();
	
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);*/
		
	}
/*
	private Component getTextPanel() {
		String welcome = "Welcome enter your name and press Start to begin\n";
		textArea = new JTextArea(welcome, 10, 20);
		textArea.setMargin(new Insets(10, 10, 10, 10));
		textArea.setFont(meiryoFont);
		
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		textPanel = new JPanel();
		textPanel.add(scrollPane);
	
		textPanel.setFont(new Font("Meiryo", Font.PLAIN, 14));
		return textPanel;
	}
*/
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
	/*
	public JPanel makeButtonPanel() {		
		
		startButton = new JButton("Start ");
		startButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 1));
		buttonPanel.add(startButton);
		
		return buttonPanel;
	}
	
	private Component getInputPanel() {
		inputPanel = new JPanel(new GridLayout(5, 5, 5, 5));
		inputPanel.setBorder(blankBorder);	
		textField = new JTextField();
		textField.setFont(meiryoFont);
		inputPanel.add(textField);
		return inputPanel;
	}*/

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			//get connected to chat service
			if(e.getSource() == boutonStart){
				name = textArea.getText();			
				if(name.length() != 0){
					frame.setTitle(name + "'s console ");
					textArea.setText("");
					textArea.append("username : " + name + " connecting...\n");							
					getConnected(name);
					if(!joueurImpl.connectionProblem){
						boutonStart.setEnabled(false);
						}
				}
				else{
					JOptionPane.showMessageDialog(frame, "Enter your name to Start");
				}
			}
		} catch (RemoteException remoteExc) {			
			remoteExc.printStackTrace();	
		}
		
	}
	

	private void getConnected(String userName) throws RemoteException{
		//remove whitespace and non word characters to avoid malformed url
		String cleanedUserName = userName.replaceAll("\\s+","_");
		cleanedUserName = userName.replaceAll("\\W+","_");
		try {		
			joueurImpl = new JoueurImpl(this, cleanedUserName);
			joueurImpl.startClient();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
