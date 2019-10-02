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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

public class JoueurGUI extends JFrame implements ActionListener{

	private ClientImpl clientImpl;
	private static final long serialVersionUID = 1L;	
	private JPanel conteneur, inputPanel;
	private JTextField textField;
	private JButton boutonStart;

    
    protected JFrame frame;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JoueurGUI gui = new JoueurGUI();
		gui.setVisible(true);
	}
	
	public JoueurGUI() {
		frame = new JFrame();
		
		setTitle("Puissance 4 cardgame");
	
		setSize(200,100);
		//frame.setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLocationRelativeTo(null);
		
		conteneur = new JPanel();
		conteneur.setLayout(new BoxLayout(conteneur, BoxLayout.PAGE_AXIS));
		textField = new JTextField("Bienvenue, écrivez votre nom et tapez entrée pour commencer");
		
		boutonStart = new JButton("Start");
		conteneur.add(boutonStart);
		conteneur.add(textField);
		setContentPane(conteneur);
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

	private Component getUsersPanel() {
		
		userPanel = new JPanel(new BorderLayout());
		String  userStr = " Current Users      ";
		
		JLabel userLabel = new JLabel(userStr, JLabel.CENTER);
		userPanel.add(userLabel, BorderLayout.NORTH);	
		userLabel.setFont(new Font("Meiryo", Font.PLAIN, 16));

		String[] noClientsYet = {"No other users"};
		setClientPanel(noClientsYet);

		clientPanel.setFont(meiryoFont);
		userPanel.add(makeButtonPanel(), BorderLayout.SOUTH);		
		userPanel.setBorder(blankBorder);

		return userPanel;	
	}

    public void setClientPanel(String[] currClients) {  	
    	clientPanel = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<String>();
        
        for(String s : currClients){
        	listModel.addElement(s);
        }
        if(currClients.length > 1){
        	privateMsgButton.setEnabled(true);
        }
        
        //Create the list and put it in a scroll pane.
        list = new JList<String>(listModel);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.setVisibleRowCount(8);
        list.setFont(meiryoFont);
        JScrollPane listScrollPane = new JScrollPane(list);

        clientPanel.add(listScrollPane, BorderLayout.CENTER);
        userPanel.add(clientPanel, BorderLayout.CENTER);
    }
	
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
		/*try {
			
		} catch (RemoteException remoteExc) {			
			remoteExc.printStackTrace();	
		}*/
		
	}

}
