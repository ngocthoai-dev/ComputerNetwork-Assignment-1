package Application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login_Screen extends JFrame {

	private Socket socket;
	private PrintWriter pr;
	private BufferedReader in;
	private static String IP;
	
	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField pwdPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame getIPFrame = new JFrame();
					getIPFrame.getContentPane().setBackground(new Color(0, 0, 153));
					getIPFrame.setBounds(100, 100, 448, 89);
					getIPFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					
					JTextField getIPTextField = new JTextField();
					getIPTextField.setColumns(10);
					getIPTextField.addKeyListener(new KeyAdapter() {
						boolean pressed = false;
						@Override
						public void keyPressed(KeyEvent arg0) {
							if(!pressed) {
								if(arg0.getKeyCode() != KeyEvent.VK_ENTER) {
									pressed = true;
								}
							}
							else {
								if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
									IP = getIPTextField.getText();
									Login_Screen frame = new Login_Screen();
									frame.setVisible(true);
									getIPFrame.dispose();
								}
							}
						}
					});
					
					JButton getIPBtn = new JButton("GET");
					
					GroupLayout groupLayout = new GroupLayout(getIPFrame.getContentPane());
					groupLayout.setHorizontalGroup(
						groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(getIPTextField, GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(getIPBtn)
								.addGap(16))
					);
					groupLayout.setVerticalGroup(
						groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addContainerGap()
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(getIPTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(getIPBtn))
								.addContainerGap(19, Short.MAX_VALUE))
					);
					getIPFrame.getContentPane().setLayout(groupLayout);
					getIPFrame.setResizable(false);
					getIPFrame.setVisible(true);
					
					getIPBtn.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							IP = getIPTextField.getText();
							getIPFrame.dispose();
							Login_Screen frame = new Login_Screen();
							frame.setVisible(true);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login_Screen() {		
		try {
			this.socket = new Socket(IP, 12345);
			this.pr = new PrintWriter(socket.getOutputStream(), true);
	        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 153));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 0, 153));
		
		JButton btnNewButton = new JButton("LOGIN");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				login();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JButton btnNewButton_1 = new JButton("REGISTER");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				goToRegister();
				
			}

			private void goToRegister() {
				Register_Form registerPage = new Register_Form();
				registerPage.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(63)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
					.addGap(71))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(69)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
					.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
					.addGap(58))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(32)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
					.addGap(25))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
					.addGap(11)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton_1)
						.addComponent(btnNewButton))
					.addGap(8))
		);
		
		txtUsername = new JTextField();
		txtUsername.addKeyListener(new KeyAdapter() {
			boolean pressed = false;
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(!pressed) {
					if(arg0.getKeyCode() != KeyEvent.VK_TAB) {
						txtUsername.setText("");
						txtUsername.setForeground(Color.BLACK);
						pressed = true;
					}
				}
			}
		});
		txtUsername.setForeground(Color.LIGHT_GRAY);
		txtUsername.addMouseListener(new MouseAdapter() {
			boolean pressed = false;
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(!pressed) {
					if(arg0.getButton() == MouseEvent.BUTTON1) {
						txtUsername.setText("");
						txtUsername.setForeground(Color.BLACK);
						pressed = true;
					}
				}
			}
		});
		txtUsername.setText("Username");
		txtUsername.setColumns(10);
		
		pwdPassword = new JPasswordField();
		pwdPassword.addKeyListener(new KeyAdapter() {
			boolean pressed = false;
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(!pressed) {
					if(arg0.getKeyCode() != KeyEvent.VK_ENTER) {
						pwdPassword.setText("");
						pwdPassword.setForeground(Color.BLACK);
						pwdPassword.setEchoChar('*');
						pressed = true;
					}
				}
				else {
					if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
						login();
					}
				}
			}
		});
		pwdPassword.addMouseListener(new MouseAdapter() {
			boolean pressed = false;
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(!pressed) {
				if(arg0.getButton() == MouseEvent.BUTTON1) {
					pwdPassword.setText("");
					pwdPassword.setForeground(Color.BLACK);
					pwdPassword.setEchoChar('*');
					pressed = true;
					}
				}
			}
		});
		pwdPassword.setForeground(Color.LIGHT_GRAY);
		pwdPassword.setSelectionColor(Color.MAGENTA);
		pwdPassword.setText("Password");
		pwdPassword.setEchoChar((char) 0);  
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(21)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addComponent(txtUsername, Alignment.LEADING)
						.addComponent(pwdPassword, Alignment.LEADING, 324, 324, Short.MAX_VALUE))
					.addGap(22))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
					.addGap(31)
					.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addGap(32)
					.addComponent(pwdPassword, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(24, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
		JLabel lblLogIn = new JLabel("Login");
		lblLogIn.setForeground(Color.WHITE);
		lblLogIn.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogIn.setHorizontalTextPosition(SwingConstants.CENTER);
		lblLogIn.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(54)
					.addComponent(lblLogIn, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
					.addGap(50))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addGap(4)
					.addComponent(lblLogIn, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void login() {
		String user = txtUsername.getText();
		String pass = new String(pwdPassword.getPassword());
		try {
			String info = "login:" + user + "," + pass;
			pr.println(info);
			String msg = in.readLine();
			JOptionPane.showMessageDialog(null, msg);
			if(msg.equals("Logged in!")) {
				Chat_Interface chat = new Chat_Interface(socket, in, pr, user);
				chat.setVisible(true);
				dispose();
			}
		} catch (UnknownHostException ex) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			ex.printStackTrace();
		} catch (IOException ex) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			ex.printStackTrace();
		}
	}
}

