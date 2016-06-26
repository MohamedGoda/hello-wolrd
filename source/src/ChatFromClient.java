import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class ChatFromClient {

	private JFrame frame;
	private static	JTextArea textArea;
	private	JTextArea textArea_1;
	private static Socket connection;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	

	/**
	 * Launch the application.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatFromClient window = new ChatFromClient();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		connection = new Socket("127.0.0.1", 5020);
		while (true) {
			try {
				DataInputStream input = new DataInputStream(
						connection.getInputStream());
				String str = input.readUTF();
				textArea.setText(textArea.getText() + "\n" + "Server :" + str);
			} catch (Exception e) {
				textArea.setText(textArea.getText()+"\n"+"Network Error");
				Thread.sleep(2000);
				System.exit(0);
			}
			
		}
	}

	/**
	 * Create the application.
	 */
	public ChatFromClient() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 733, 489);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Send Message");
		btnNewButton.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				
			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (textArea_1.getText().equals(""))
				{
				JOptionPane.showMessageDialog(null, "Can't Send Empty Message");	
				}
				else{
					textArea.setText(textArea.getText() + "\n" + "Client :"
							+ textArea_1.getText());
					try {
						DataOutputStream output = new DataOutputStream(
								connection.getOutputStream());
						output.writeUTF(textArea_1.getText());
					} catch (IOException e) {
						textArea.setText(textArea.getText() + "\n"
								+ "Network Error");
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						System.exit(0);
					}
					textArea_1.setText("");
				}
			}
		});
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setBounds(524, 370, 183, 69);
		frame.getContentPane().add(btnNewButton);
		 
		 scrollPane = new JScrollPane();
		 scrollPane.setBounds(10, 10, 697, 349);
		 frame.getContentPane().add(scrollPane);
		
		 textArea = new JTextArea();
		 scrollPane.setViewportView(textArea);
		 textArea.setEditable(false);
		 
		 scrollPane_1 = new JScrollPane();
		 scrollPane_1.setBounds(10, 370, 504, 69);
		 frame.getContentPane().add(scrollPane_1);
		
		 textArea_1 = new JTextArea();
		 scrollPane_1.setViewportView(textArea_1);
	}
}
