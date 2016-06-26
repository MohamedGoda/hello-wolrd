import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;


public class ChatFromServer {

	JFrame frame;
	private static	JTextArea textArea;
	private	JTextArea textArea_1;
	private	JButton btnNewButton;
	static ServerSocket server;
	static Socket connection;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private static JLabel lblNewLabel;
	/**
	 * Launch the application.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatFromServer window = new ChatFromServer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		 server = new ServerSocket(5020);
		 connection = server.accept();
		 if (connection.isConnected()) {
			 lblNewLabel.setForeground(Color.GREEN);
			 lblNewLabel.setText("Client Is On");
		}
		while (true) {
			try {
				DataInputStream input = new DataInputStream(
						connection.getInputStream());
				String str = input.readUTF();
				textArea.setText(textArea.getText() + "\n" + "Client :" + str);
			} catch (Exception e) {
				textArea.setText(textArea.getText()+"\n"+"Network Error");
				Thread.sleep(2000);
				System.exit(0);
			}
		}
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public ChatFromServer() throws IOException {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		frame.setBounds(100, 100, 737, 532);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		btnNewButton = new JButton("Send Message");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (textArea_1.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Can't Send Empty Message");
				}
				else{
					textArea.setText(textArea.getText() + "\n" + "Server :"
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
			}		});
		btnNewButton.setBounds(560, 365, 143, 76);
		frame.getContentPane().add(btnNewButton);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 693, 341);
		frame.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
	    
	    scrollPane_1 = new JScrollPane();
	    scrollPane_1.setBounds(10, 365, 540, 76);
	    frame.getContentPane().add(scrollPane_1);
		
	    textArea_1 = new JTextArea();
	    scrollPane_1.setViewportView(textArea_1);
	    
	    lblNewLabel = new JLabel();
	    lblNewLabel.setText("Client Is Off");
	    lblNewLabel.setForeground(Color.BLACK);
	    lblNewLabel.setFont(new Font("Sylfaen", Font.ITALIC, 12));
	    lblNewLabel.setBounds(20, 452, 68, 25);
	    frame.getContentPane().add(lblNewLabel);
	}
}
