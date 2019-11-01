package Application;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.event.ListSelectionEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Component;
import java.awt.event.KeyAdapter;

public class Chat_Interface extends JFrame {
	
	private JPanel contentPane;
	private JTextField textField;
	private JScrollPane chatScrollPane;
	private JLabel lblNewLabel;
	private JLabel panel_4;
	private JButton btnAddFile;
	
	private HashMap<String, JTextPane> chatArea;
	private JScrollPane scrollPane;
	private JList<Object> list;
	private DefaultListModel<Object> DLM;
	
	private final String ServerStyle = "<html><body><p style='margin: 3px 0px; color: rgb(214, 71, 0);'>";
	private final String GroupStyle = "<html><body><p style='margin: 3px 0px; color: rgb(33, 137, 255);'>";
	private final String UserStyle = "<html><body><p style='margin: 3px 0px; color: rgb(0, 214, 36);'>";
	private final String EndStyle = "</p></body></html>";
	private final String exclamationMark = "<img width='25' height='25' alt='new' src='https://stickershop.line-scdn.net/sticonshop/v1/sticon/5c0f6869040ab1b3f8bec3af/iPhone/036.png'>";
	
	private ArrayList<String> ListUser;
	private HashMap<String, ArrayList<String> > ListGroup;
	private HashMap<String, ArrayList<Socket> > socketPane;
	private HashMap<String, ArrayList<Socket> > serverPane;
	
	private Thread read;
	private int PORT;
	private String name;
	private String paneName;
	private Server server;
	private BufferedReader input;
	private PrintWriter output;
	private Socket socket;
	
	/**
	 * Create the frame.
	 */
	public Chat_Interface(Socket sock, BufferedReader in, PrintWriter out, String name) {
		setBackground(Color.BLACK);
		this.socket = sock;
		this.input = in;
		this.output = out;
		this.panel_4 = new JLabel();
		panel_4.setBounds(113, 11, 63, 45);
		
		this.socketPane = new HashMap<String, ArrayList<Socket> >();
		this.serverPane = new HashMap<String, ArrayList<Socket> >();
		
		try {
			this.PORT = Integer.valueOf(this.input.readLine());
			this.server = new Server(this.PORT);
			this.server.start();
		} catch (NumberFormatException | IOException e1) {
			try {
				this.socket.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
		this.name = name;
		System.out.println(this.name);
		
		this.chatScrollPane = new JScrollPane();
		this.chatArea = new HashMap<String, JTextPane>();
		this.scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		this.list = new JList<>();
		list.setBorder(null);
		list.setBackground(new Color(51, 102, 153));
		this.paneName = "SERVER";
		this.ListUser = new ArrayList<String>();
		this.ListGroup = new HashMap<String, ArrayList<String> >();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 501);
		this.contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 153));
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(this.contentPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(null);
		panel_1.setBackground(new Color(0, 0, 153));
		
		JLabel lblFriendList = new JLabel("User List");
		lblFriendList.setForeground(new Color(245, 255, 250));
		lblFriendList.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(0, 0, 153));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(0, 0, 153));
		
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(lblFriendList, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
							.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)))
					.addGap(10))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblFriendList)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JLabel lblName = new JLabel(this.name.toUpperCase());
		lblName.setBounds(10, 22, 83, 23);
		lblName.setForeground(Color.WHITE);
		lblName.setHorizontalAlignment(SwingConstants.CENTER);

		String path = "assets\\person_icon.png";
		BufferedImage img = null;
		try {
			path = this.input.readLine();
		    img = ImageIO.read(new File(path));
		} catch (IOException e) {
			path = "assets\\person_icon.png";
		    try {
				img = ImageIO.read(new File(path));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	    Image dimg = img.getScaledInstance(this.panel_4.getSize().width, this.panel_4.getSize().height, Image.SCALE_SMOOTH);
		this.panel_4.setIcon(new ImageIcon(dimg));
		panel_3.setLayout(null);
		panel_3.add(lblName);
		panel_3.add(panel_4);
		
		this.list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					if(!list.isSelectionEmpty()) {
						String selected = list.getSelectedValue().toString();
						String paneMsg = "SERVER";
						if(selected.contains(exclamationMark)) {
							String temp = selected.replace(exclamationMark, "");
							paneMsg = temp.substring(temp.indexOf(");'>") + 4, temp.indexOf("    </p>"));
							if(paneMsg.equals("SERVER")) {
								DLM.set(DLM.indexOf(selected), ServerStyle + "SERVER    " + EndStyle);
							}
							else if(ListGroup.containsKey(paneMsg)) {
								DLM.set(DLM.indexOf(selected), GroupStyle + paneMsg + "    " + EndStyle);		
							} else {
								DLM.set(DLM.indexOf(selected), UserStyle + paneMsg + "    " + EndStyle);		
							}
						} else {
							paneMsg = selected.substring(selected.indexOf(");'>") + 4, selected.indexOf("    </p>"));
						}
						if(!paneMsg.equals(name)) {
							changechatPane(paneMsg);
						}
					}
				}
			}
		});
		this.scrollPane.setViewportView(this.list);
		DefaultListCellRenderer renderer =  (DefaultListCellRenderer) this.list.getCellRenderer();  
		renderer.setHorizontalAlignment(JLabel.CENTER); 
		this.DLM = new DefaultListModel<>();
		this.list.setModel(this.DLM);
		this.list.setSelectedIndex(0);
		
		JButton btnChangeImage = new JButton("Change Image");
		btnChangeImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeImage();
			}
		});
		
		JButton btnCreateGroup = new JButton("Create Group");
		btnCreateGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createGroup();
			}
		});
		
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(45)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnCreateGroup, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnChangeImage, Alignment.LEADING))
					.addContainerGap(46, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addComponent(btnChangeImage)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnCreateGroup)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(153, 255, 255));
		
		this.chatArea.put(this.paneName, createChatPane("SERVER"));
		this.chatArea.get(this.paneName).setContentType("text/html");
		this.chatArea.get(this.paneName).setEditable(false);
		chatScrollPane.setViewportView(chatArea.get(this.paneName));
	    
	    JButton btnSend = new JButton("Send");
	    btnSend.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		sendMessage();
	    	}
	    });
	    
	    this.textField = new JTextField();
	    textField.addKeyListener(new KeyAdapter() {
	    	@Override
	    	public void keyPressed(KeyEvent arg0) {
	    		if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
	    			sendMessage();
	    		}
	    	}
	    });
	    this.textField.setColumns(10);
	   
	    btnAddFile = new JButton(new ImageIcon("assets/add_file.png"));
	    btnAddFile.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		addfile();
	    	}
	    });
	    this.btnAddFile.setVisible(false);
	    
	    JButton btnNewButton = new JButton(new ImageIcon("assets/log_out.png"));
	    btnNewButton.setAlignmentY(Component.TOP_ALIGNMENT);
	    btnNewButton.setBackground(Color.RED);
	    btnNewButton.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent arg0) {
	    		output.println("IAMLOGGINGOUT");
	    		System.exit(0);
	    	}
	    });
	    
	    lblNewLabel = new JLabel("SERVER");
	    lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
	    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

	    JButton button = new JButton(new ImageIcon("assets/more_icon.png"));
	    button.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		moreGif();
	    	}
	    });
	    
	    GroupLayout gl_panel = new GroupLayout(panel);
	    gl_panel.setHorizontalGroup(
	    	gl_panel.createParallelGroup(Alignment.TRAILING)
	    		.addGroup(gl_panel.createSequentialGroup()
	    			.addContainerGap()
	    			.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
	    				.addGroup(gl_panel.createSequentialGroup()
	    					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
	    						.addComponent(chatScrollPane, GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
	    						.addGroup(gl_panel.createSequentialGroup()
	    							.addComponent(btnAddFile)
	    							.addPreferredGap(ComponentPlacement.RELATED)
	    							.addComponent(textField, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
	    							.addPreferredGap(ComponentPlacement.RELATED)
	    							.addComponent(button, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
	    							.addGap(4)
	    							.addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
	    						.addGroup(gl_panel.createSequentialGroup()
	    							.addGap(188)
	    							.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
	    							.addGap(155)))
	    					.addContainerGap())
	    				.addGroup(gl_panel.createSequentialGroup()
	    					.addGap(418)
	    					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))))
	    );
	    gl_panel.setVerticalGroup(
	    	gl_panel.createParallelGroup(Alignment.TRAILING)
	    		.addGroup(gl_panel.createSequentialGroup()
	    			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
	    				.addGroup(gl_panel.createSequentialGroup()
	    					.addGap(12)
	    					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
	    				.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
	    			.addPreferredGap(ComponentPlacement.RELATED)
	    			.addComponent(chatScrollPane, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
	    			.addPreferredGap(ComponentPlacement.RELATED)
	    			.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
	    				.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	    				.addComponent(btnSend)
	    				.addComponent(btnAddFile, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
	    				.addComponent(button, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
	    			.addContainerGap())
	    );
	    panel.setLayout(gl_panel);
		
		GroupLayout gl_contentPane = new GroupLayout(this.contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(1))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
		);
		
		this.contentPane.setLayout(gl_contentPane);
		this.read = new Read();
		this.read.start();
	}
	
	private void moreGif() {
		JFrame emotion = new JFrame("CHANGE IMAGE");
		emotion.setBounds(100, 100, 360, 355);
		emotion.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		emotion.setVisible(true);
		
		JPanel panelEmotion = new JPanel();
		panelEmotion.setLayout(null);
		
		JTextPane textPaneEmotion1 = new JTextPane();
		textPaneEmotion1.setContentType("text/html");
		textPaneEmotion1.setEditable(false);
		textPaneEmotion1.setBounds(0, 0, 104, 95);
		panelEmotion.add(textPaneEmotion1);
		
		JTextPane textPaneEmotion2 = new JTextPane();
		textPaneEmotion2.setEditable(false);
		textPaneEmotion2.setContentType("text/html");
		textPaneEmotion2.setBounds(114, 0, 104, 95);
		panelEmotion.add(textPaneEmotion2);
		
		JTextPane textPaneEmotion3 = new JTextPane();
		textPaneEmotion3.setContentType("text/html");
		textPaneEmotion3.setEditable(false);
		textPaneEmotion3.setBounds(230, 0, 104, 95);
		panelEmotion.add(textPaneEmotion3);
		
		JTextPane textPaneEmotion4 = new JTextPane();
		textPaneEmotion4.setEditable(false);
		textPaneEmotion4.setContentType("text/html");
		textPaneEmotion4.setBounds(0, 104, 104, 95);
		panelEmotion.add(textPaneEmotion4);
		
		JTextPane textPaneEmotion5 = new JTextPane();
		textPaneEmotion5.setEditable(false);
		textPaneEmotion5.setContentType("text/html");
		textPaneEmotion5.setBounds(114, 104, 104, 95);
		panelEmotion.add(textPaneEmotion5);
		
		JTextPane textPaneEmotion6 = new JTextPane();
		textPaneEmotion6.setEditable(false);
		textPaneEmotion6.setContentType("text/html");
		textPaneEmotion6.setBounds(230, 101, 104, 95);
		panelEmotion.add(textPaneEmotion6);
		
		JTextPane textPaneEmotion7 = new JTextPane();
		textPaneEmotion7.setContentType("text/html");
		textPaneEmotion7.setEditable(false);
		textPaneEmotion7.setBounds(0, 207, 104, 95);
		panelEmotion.add(textPaneEmotion7);
		
		JTextPane textPaneEmotion8 = new JTextPane();
		textPaneEmotion8.setEditable(false);
		textPaneEmotion8.setContentType("text/html");
		textPaneEmotion8.setBounds(114, 207, 104, 95);
		panelEmotion.add(textPaneEmotion8);
		
		JTextPane textPaneEmotion9 = new JTextPane();
		textPaneEmotion9.setContentType("text/html");
		textPaneEmotion9.setEditable(false);
		textPaneEmotion9.setBounds(230, 207, 104, 95);
		panelEmotion.add(textPaneEmotion9);

		
		GroupLayout groupLayout = new GroupLayout(emotion.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelEmotion, GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelEmotion, GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
					.addContainerGap())
		);
		emotion.getContentPane().setLayout(groupLayout);
		emotion.setResizable(false);

		appendToPane(textPaneEmotion1, "<img width='104' height='95' src='https://data.whicdn.com/images/226549072/original.gif'>");
		textPaneEmotion1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.setText("<img width='120' height='120' src='https://data.whicdn.com/images/226549072/original.gif'>");
				sendMessage();
				emotion.dispose();
			}
		});
		appendToPane(textPaneEmotion2, "<img width='104' height='95' src='https://steamuserimages-a.akamaihd.net/ugc/963099873035049668/397B115E5FF3FE0D1034C20E36063D631A5C4002/'>");
		textPaneEmotion2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.setText("<img width='120' height='120' src='https://steamuserimages-a.akamaihd.net/ugc/963099873035049668/397B115E5FF3FE0D1034C20E36063D631A5C4002/'>");
				sendMessage();
				emotion.dispose();
			}
		});
		appendToPane(textPaneEmotion3, "<img width='104' height='95' src='https://thumbs.gfycat.com/MeaslyNegativeGoldenmantledgroundsquirrel-size_restricted.gif'>");
		textPaneEmotion3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.setText("<img width='120' height='120' src='https://thumbs.gfycat.com/MeaslyNegativeGoldenmantledgroundsquirrel-size_restricted.gif'>");
				sendMessage();
				emotion.dispose();
			}
		});
		appendToPane(textPaneEmotion4, "<img width='104' height='95' src='https://cdn130.picsart.com/235791987029202.gif?r240x240'>");
		textPaneEmotion4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.setText("<img width='120' height='120' src='https://cdn130.picsart.com/235791987029202.gif?r240x240'>");
				sendMessage();
				emotion.dispose();
			}
		});
		appendToPane(textPaneEmotion5, "<img width='104' height='95' src='https://i.redd.it/v1vrtlxfrtq11.gif'");
		textPaneEmotion5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.setText("<img width='120' height='120' src='https://i.redd.it/v1vrtlxfrtq11.gif'");
				sendMessage();
				emotion.dispose();
			}
		});
		appendToPane(textPaneEmotion6, "<img width='104' height='95' src='https://data.whicdn.com/images/85353259/original.gif'>");
		textPaneEmotion6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.setText("<img width='120' height='120' src='https://data.whicdn.com/images/85353259/original.gif'>");
				sendMessage();
				emotion.dispose();
			}
		});
		appendToPane(textPaneEmotion7, "<img width='104' height='95' src='https://2.bp.blogspot.com/-pIKZUIKQD2Q/WE_PR_HswtI/AAAAAAAMc00/CIzbUZvgmpA7BS5_9Gfd5JHO2fVFDzLnQCLcB/s1600/AW294318_04.gif'>");
		textPaneEmotion7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.setText("<img width='120' height='120' src='https://2.bp.blogspot.com/-pIKZUIKQD2Q/WE_PR_HswtI/AAAAAAAMc00/CIzbUZvgmpA7BS5_9Gfd5JHO2fVFDzLnQCLcB/s1600/AW294318_04.gif'>");
				sendMessage();
				emotion.dispose();
			}
		});
		appendToPane(textPaneEmotion8, "<img width='104' height='95' src='https://i.imgur.com/wI2NYGP.gif?noredirect'>");
		textPaneEmotion8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.setText("<img width='120' height='120' src='https://i.imgur.com/wI2NYGP.gif?noredirect'>");
				sendMessage();
				emotion.dispose();
			}
		});
		appendToPane(textPaneEmotion9, "<img width='104' height='95' src='https://3.bp.blogspot.com/-XHww70Gt2Bg/XG34miX8d9I/AAAAAAAMbDw/mjwFAnLvBdUBstyJUPaO4zL3W_6_Ez7qQCLcBGAs/s1600/AS0004980_10.gif'>");
		textPaneEmotion9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.setText("<img width='120' height='120' src='https://3.bp.blogspot.com/-XHww70Gt2Bg/XG34miX8d9I/AAAAAAAMbDw/mjwFAnLvBdUBstyJUPaO4zL3W_6_Ez7qQCLcBGAs/s1600/AS0004980_10.gif'>");
				sendMessage();
				emotion.dispose();
			}
		});
	}
	
	private void changeImage() {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int returnValue = jfc.showOpenDialog(null);			
		
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			String path = selectedFile.getPath();
			BufferedImage img = null;
			try {
				output.println("CHANGEIMAGE:" + path);
			    img = ImageIO.read(new File(path));
			    Image dimg = img.getScaledInstance(this.panel_4.getSize().width, this.panel_4.getSize().height, Image.SCALE_SMOOTH);
				this.panel_4.setIcon(new ImageIcon(dimg));
			} catch (IOException e) {
			    e.printStackTrace();
			}
		}
	}

	private void addfile() {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int returnValue = jfc.showOpenDialog(null);			
		
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			textField.setText("SENDFILE:" + selectedFile.getPath());
		}
	}
	
	private void createGroup() {
		JFrame addGroupFrame = new JFrame("ADD MEMBER TO GROUP");
		addGroupFrame.setBounds(100, 100, 400, 300);
		addGroupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JScrollPane scrollPaneAddGroup = new JScrollPane();
		JList<String> listAddGroup = new JList<>();
		DefaultListCellRenderer renderer = (DefaultListCellRenderer)listAddGroup.getCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
		DefaultListModel<String> DAGLM = new DefaultListModel<>();
		listAddGroup.setModel(DAGLM);
		
		JTextField groupNameTextField = new JTextField();
		groupNameTextField.setColumns(10);
		
		JButton addGroup = new JButton("ADD");
		addGroup.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String groupName = groupNameTextField.getText();
				if(groupName.equals("") || groupName.equals("SERVER")) {
					JOptionPane.showMessageDialog(null, "Invalid Group Name!");
				} else if(ListUser.contains(groupName)) {
					JOptionPane.showMessageDialog(null, "Group Name can not be the same as User Name!");					
				} else if(ListGroup.keySet().contains(groupName)) {
					JOptionPane.showMessageDialog(null, "Group Name Existed!");					
				} else {
					if (listAddGroup.getSelectedValuesList().size() <= 2) {
						JOptionPane.showMessageDialog(null, "Invalid Group Member Size!");
					} else {
						ListGroup.put(groupName, (ArrayList<String>) listAddGroup.getSelectedValuesList());
						output.println("REUPDATEUSERLISTANDGROUPLIST:" + name + ":" + groupName + ":" + ListGroup.get(groupName).toString());
						changechatPane(groupName);
						addGroupFrame.dispose();						
					}
				}
			}
		});
		
		listAddGroup.setModel(DAGLM);
		scrollPaneAddGroup.setViewportView(listAddGroup);
		
		GroupLayout groupLayout = new GroupLayout(addGroupFrame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(91)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(scrollPaneAddGroup, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(groupNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(addGroup, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(102, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPaneAddGroup, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(addGroup)
						.addComponent(groupNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		addGroupFrame.getContentPane().setLayout(groupLayout);
		
		for (String user : this.ListUser) {
			DAGLM.addElement(user);
		}
		
		listAddGroup.addMouseListener(new MouseAdapter() {
			Robot robot;
			{
				try {
					robot = new Robot();
				} catch (AWTException ex) {
					ex.printStackTrace();
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				if (robot != null)
					robot.keyPress(KeyEvent.VK_CONTROL);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if (robot != null)
					robot.keyRelease(KeyEvent.VK_CONTROL);
			}
		});
		listAddGroup.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					listAddGroup.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				}
			}
		});
		addGroupFrame.setResizable(false);
		addGroupFrame.setVisible(true);
	}
	
	private JTextPane createChatPane(String paneName) {
		JTextPane tempPane = new JTextPane();
		tempPane.setContentType("text/html");
		tempPane.setEditable(false);
		return tempPane;
	}
	
	private void changechatPane(String paneName) {
		if(paneName.equals("SERVER")) {
			this.paneName = "SERVER";
		}
		else {
			if(this.chatArea.keySet().contains(paneName)) {
				this.paneName = paneName;
			} else {
				this.paneName = paneName;
				if(ListGroup.keySet().contains(paneName)) {
					this.serverPane.put(this.paneName, new ArrayList<Socket>());
				} else {
					this.socketPane.put(this.paneName, new ArrayList<Socket>());
				}
				this.chatArea.put(this.paneName, createChatPane(this.paneName));
			}
		}
		if(!this.paneName.equals("SERVER") && !this.ListGroup.keySet().contains(paneName)) {
			this.output.println("CHATLISTENERREQUESTTOUSER:" + this.paneName);
		} else if(!this.paneName.equals("SERVER") && this.ListGroup.keySet().contains(paneName)) {
			this.output.println("CHATLISTENERREQUESTTOGROUP:" + this.paneName);
		}
		if(this.paneName.equals("SERVER") || this.ListGroup.containsKey(this.paneName)) {
			this.btnAddFile.setVisible(false);
		} else {
			this.btnAddFile.setVisible(true);			
		}
		this.chatScrollPane.setViewportView(chatArea.get(this.paneName));
		lblNewLabel.setText(this.paneName);
	}
	
	private void updateListener(String condi, String paneName) {
		ArrayList<String> inVals = new ArrayList<String>();
		if(condi.contains(",")) {
			inVals = new ArrayList<String>(Arrays.asList(condi.split(",")));
			if(this.serverPane.get(paneName).isEmpty()) {
				for(int i=0; i<inVals.size(); i++) {
					String[] addr = inVals.get(i).split(":");
					Socket socket = null;
					try {
						socket = new Socket(addr[0], Integer.valueOf(addr[1]));
						new PrintWriter(socket.getOutputStream()).println("FROMPANE:" + paneName);
						this.serverPane.get(paneName).add(socket);
						new Peer(socket, paneName, addr[0], Integer.valueOf(addr[1])).start();
					} catch(Exception e) {
						if (socket != null) {
							try {
								socket.close();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
						else System.out.println("invalid input");
					}
				}
			}
		} else {
			inVals.add(condi);
			if(this.socketPane.get(paneName).isEmpty()) {
				String[] addr = inVals.get(0).split(":");
				Socket socket = null;
				try {
					socket = new Socket(addr[0], Integer.valueOf(addr[1]));
					new PrintWriter(socket.getOutputStream()).println("FROMPANE:" + paneName);
					this.socketPane.get(paneName).add(socket);
					new Peer(socket, paneName, addr[0], Integer.valueOf(addr[1])).start();
				} catch(Exception e) {
					if (socket != null) {
						try {
							socket.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					else System.out.println("invalid input");
				}
			}
		}
	}

	private void sendMessage() {
		try {
			String message = this.textField.getText().trim();
            message = message.replace("#:)", "<img height=50 width=50 src='https://stickershop.line-scdn.net/sticonshop/v1/sticon/5c25eead031a670084d9b325/iPhone/006.png'>");
            message = message.replace("#:D", "<img height=50 width=50 src='https://stickershop.line-scdn.net/sticonshop/v1/sticon/5c25eead031a670084d9b325/iPhone/025.png'>");
            message = message.replace("#:(", "<img height=50 width=50 src='https://stickershop.line-scdn.net/sticonshop/v1/sticon/5c25eead031a670084d9b325/iPhone/015.png'>");
            message = message.replace("#;|", "<img height=50 width=50 src='https://stickershop.line-scdn.net/sticonshop/v1/sticon/5c25eead031a670084d9b325/iPhone/034.png'>");
            message = message.replace("#:p", "<img height=50 width=50 src='https://stickershop.line-scdn.net/sticonshop/v1/sticon/5c25eead031a670084d9b325/iPhone/027.png'>");
            message = message.replace("#:o", "<img height=50 width=50 src='https://stickershop.line-scdn.net/sticonshop/v1/sticon/5c25eead031a670084d9b325/iPhone/002.png'>");
            message = message.replace("#:O", "<img height=50 width=50 src='https://stickershop.line-scdn.net/sticonshop/v1/sticon/5c25eead031a670084d9b325/iPhone/017.png'>");
            message = message.replace("#:hmm", "<img height=50 width=50 src='https://stickershop.line-scdn.net/sticonshop/v1/sticon/5c25eead031a670084d9b325/iPhone/030.png'>");
            message = message.replace("#:wow", "<img height=50 width=50 src='https://stickershop.line-scdn.net/sticonshop/v1/sticon/5c25eead031a670084d9b325/iPhone/026.png'>");
            message = message.replace("#:|", "<img height=50 width=50 src='https://stickershop.line-scdn.net/sticonshop/v1/sticon/5c25eead031a670084d9b325/iPhone/028.png'>");
            if (message.equals("")) {
				return;
			}
			if(this.paneName.equals("SERVER")) {
				appendToPane(chatArea.get("SERVER"), "<p align='right' style='margin:0; color:rgb(180, 52, 235);'>" + this.name + ": " + message + "</p>");
				output.println(message);
				this.textField.requestFocus();
				this.textField.setText(null);
			} else {
				if(message.startsWith("SENDFILE:")) {
					File selectedFile = new File(message.substring(9));
					appendToPane(chatArea.get(this.paneName), "<p align='right' style='margin:0; color:rgb(180, 52, 235);'>" + this.name + ": " + selectedFile.getName() + "</p>");
					this.server.sendMessage("<p style='margin:0; color:rgb(250, 98, 27);'>" + "#SENDFILE#" + "</p>");
					this.server.sendFile(selectedFile);
					this.textField.setText(null);
				}
				else {
					appendToPane(chatArea.get(this.paneName), "<p align='right' style='margin:0; color:rgb(180, 52, 235);'>" + this.name + ": " + message + "</p>");
					this.server.sendMessage("<p style='margin:0; color:rgb(250, 98, 27);'>" + this.name + ": " + message + "</p>");
					this.textField.requestFocus();
					this.textField.setText(null);
				}
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
			System.exit(0);
		}
	}

	// read new incoming messages
	// change
	class Read extends Thread {
		public void run() {
			String message = "";
			while(!Thread.currentThread().isInterrupted()){
				try {
					message = input.readLine();
					if(message.contains("-")) {
						String[] readInfo = message.split("-");
						if(readInfo[0].equals("LISTENERLIST")) {
							updateListener(readInfo[1], paneName);
							message = "";
						} else if(readInfo[0].equals("REQUESTLISTENERFROMUSER")) {
							String[] request = readInfo[1].split("@");
							if(!chatArea.keySet().contains(request[0])) {
								socketPane.put(request[0], new ArrayList<Socket>());
								chatArea.put(request[0], createChatPane(request[0]));
							}
							updateListener(request[1], request[0]);
							message = "";
						} else if(readInfo[0].equals("REQUESTLISTENERFROMGROUP")) {
							String[] request = readInfo[1].split("@");
							if(!chatArea.keySet().contains(request[0])) {
								serverPane.put(request[0], new ArrayList<Socket>());
								chatArea.put(request[0], createChatPane(request[0]));
							}
							updateListener(request[1], request[0]);
							message = "";
						}
					}
					if(message != "") {
						if (message.charAt(0) == '[') {
							if(!message.contains(":")) {
								message = message.substring(1, message.length()-1);
								ListUser = new ArrayList<String>(Arrays.asList(message.split(", ")));
							} else {
								String groupTemp[] = message.split("-, ");
								ListUser = new ArrayList<String>(Arrays.asList(groupTemp[groupTemp.length-1].substring(1, groupTemp[groupTemp.length-1].length()-1).split(", ")));
								for (int i=0; i<groupTemp.length-1; i++) {
									String group[] = groupTemp[i].split(":");
									ListGroup.put(group[0].substring(1, group[0].length()-1), new ArrayList<String>(Arrays.asList(group[1].substring(1, group[1].length()-1).split(", "))));									
								}
							}
							DLM.removeAllElements();
							DLM.addElement(ServerStyle + "SERVER    " + EndStyle);
							if(!ListGroup.isEmpty()) {
								for(String groupName : ListGroup.keySet()) {
									DLM.addElement(GroupStyle + groupName + "    " + EndStyle);
								}
							}
							for(String user : ListUser) {
								DLM.addElement(UserStyle + user + "    " + EndStyle);
							}
						} else {
							if(!paneName.equals("SERVER")) {
								DLM.set(DLM.indexOf(ServerStyle + "SERVER    " + EndStyle), ServerStyle + "SERVER    " + exclamationMark + EndStyle);
							}
							appendToPane(chatArea.get("SERVER"), message);
						}
					}
				} catch (IOException ex) {
					System.err.println("Failed to parse incoming message");
					try {
						socket.close();
						output.close();
						input.close();
						dispose();
						interrupt();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	  // send html to pane
	private void appendToPane(JTextPane tp, String msg) {
		HTMLDocument doc = (HTMLDocument) tp.getDocument();
		HTMLEditorKit editorKit = (HTMLEditorKit) tp.getEditorKit();
		try {
	    	editorKit.insertHTML(doc, doc.getLength(), msg, 0, 0, null);
	    	tp.setCaretPosition(doc.getLength());
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	// change
	class Peer extends Thread {
		private String msgPane;
		private Socket socket;
		private BufferedReader input;
		private String address;
		private int port;
		
		public Peer(Socket socket, String paneName, String addr, int por) {
			this.socket = socket;
			this.msgPane = paneName;
			this.address = addr;
			this.port = por;
			try {
				this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
				PrintWriter output = new PrintWriter(this.socket.getOutputStream(), true);
				if(ListGroup.keySet().contains(this.msgPane)) {
					output.println("THISSOCKETFROMGROUP:" + this.msgPane);
				} else {
					output.println("THISSOCKETFROMUSER:" + name);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void run() {
			while(true) {
				try {
					String msg = this.input.readLine();
					if(msg.contains("SENDFILE")) {
						appendToPane(chatArea.get(this.msgPane), msg);
						int bytesRead;
						int current = 0;
						String FILE_TO_RECEIVED = "";
				        JFrame parentFrame = new JFrame();
				        JFileChooser fileChooser = new JFileChooser();
				        fileChooser.setDialogTitle("Specify a file to save");   
				        int userSelection = fileChooser.showSaveDialog(parentFrame);
				        if (userSelection == JFileChooser.APPROVE_OPTION) {
				            File fileToSave = fileChooser.getSelectedFile();
				            FILE_TO_RECEIVED = fileToSave.getAbsolutePath();
				        }
				        byte [] mybytearray  = new byte [6022386];
				        InputStream is = socket.getInputStream();
				        FileOutputStream fos = new FileOutputStream(FILE_TO_RECEIVED);
				        BufferedOutputStream bos = new BufferedOutputStream(fos);
				        bytesRead = is.read(mybytearray,0,mybytearray.length);
				        current = bytesRead;
				        do {
				           bytesRead =
				              is.read(mybytearray, current, (mybytearray.length-current));
				           if(bytesRead >= 0) current += bytesRead;
				        } while(bytesRead > -1);
				        bos.write(mybytearray, 0 , current);
				        bos.flush();
				        fos.close();
				        bos.close();
				        
				        Socket socket = new Socket(getAddrServer(), getPortServer());
						socketPane.get(this.msgPane).remove(this.socket);
						socketPane.get(this.msgPane).add(socket);
						new Peer(socket, this.msgPane, getAddrServer(), getPortServer()).start();
				        break;
					}
					else {
						if(!this.msgPane.equals(paneName)) {
							boolean hasMark = false;
							for (int i=0;i<DLM.getSize(); i++) {
								String temp = (String) DLM.get(i);
								if(temp.contains(exclamationMark) && temp.equals(this.msgPane)) {
									hasMark = true;
								}
							}
							if(!hasMark) {
								if(ListGroup.containsKey(this.msgPane)) {
									DLM.set(DLM.indexOf(GroupStyle + this.msgPane + "    " + EndStyle), GroupStyle + this.msgPane + "    " + exclamationMark + EndStyle);		
								} else {
									DLM.set(DLM.indexOf(UserStyle + this.msgPane + "    " + EndStyle), UserStyle + this.msgPane + "    " + exclamationMark + EndStyle);		
								}
							}
						}
						appendToPane(chatArea.get(this.msgPane), msg);
					}
				} catch (IOException e) {
					if(ListGroup.keySet().contains(this.msgPane)) {
						serverPane.get(this.msgPane).remove(this.socket);
						if(serverPane.get(this.msgPane).size() <=2) {
							serverPane.remove(this.msgPane);
						}
					} else {
						socketPane.remove(this.msgPane);
					}
					break;
				}
			}
		}
		
		public String getAddrServer() {
			return this.address;
		}
		
		public int getPortServer() {
			return this.port;
		}
	}	

	// change
	class Server extends Thread {
	    private ServerSocket serverSocket;
	    private HashSet<ServerSocketPair> serversocketPair;

	    public Server(int port) throws IOException {
	        this.serverSocket = new ServerSocket(port);
	        this.serversocketPair = new HashSet<ServerSocketPair>();
	    }

	    public void run() {
	        try {
	            while(true) {
	                ServerSocketPair serversocketPair = new ServerSocketPair(this.serverSocket.accept(), this);
	                this.serversocketPair.add(serversocketPair);
	                serversocketPair.start();
	            }
	        } catch (Exception ex) { ex.printStackTrace(); }
	    }

	    public void sendMessage(String message) {
	        try {
				for(ServerSocketPair serversocketPair : this.serversocketPair) {
	        		if(serversocketPair.checkPane().equals(paneName)) {
	    	            serversocketPair.getPrintWriter().println(message);
	        		}
	        	}
	        } catch (Exception ex) { ex.printStackTrace(); }
	    }

	    public Set<ServerSocketPair> getServerThreadThreads() {
	        return this.serversocketPair;
	    }
	    
	    public void sendFile(File selectedFile) throws IOException, InterruptedException {
	    	FileInputStream fis = null;
	    	BufferedInputStream bis = null;
	    	for(ServerSocketPair x : serversocketPair) {
    	        try {
    	        	BufferedOutputStream os = null;
    	        	byte [] mybytearray  = new byte [(int)selectedFile.length()];
    	        	fis = new FileInputStream(selectedFile);
    	        	bis = new BufferedInputStream(fis);
    	        	bis.read(mybytearray, 0, mybytearray.length);
    	        	os = new BufferedOutputStream(x.socket.getOutputStream());
    		        os.write(mybytearray,0,mybytearray.length);
    		        fis.close();
    		        bis.close();
    		        os.flush();
    		        os.close();
    	        } catch(Exception e) {
		        	e.printStackTrace();
		        }
	    	}
	    }
	}
	
	// change
	class ServerSocketPair extends Thread {
	    private Server server;
	    private Socket socket;
	    private PrintWriter pr;
	    private String paneName;
	    
	    public ServerSocketPair(Socket socket, Server server){
	        this.socket = socket;
	        this.server = server;
	    }

		public void run(){
	        try {
	            BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
	            String msg = br.readLine();
	            if(msg.contains("THISSOCKETFROM")) {
		            this.paneName = msg.split(":")[1];
		            this.pr = new PrintWriter(this.socket.getOutputStream(), true);
	            }
	            
	        } catch(Exception ex) {
	        	this.server.getServerThreadThreads().remove(this);
	        }
	    }

	    public PrintWriter getPrintWriter() {
	        return this.pr;
	    }
	    
	    public Socket getSocket() {
	    	return socket;
	    }
	    public String checkPane() {
	    	return this.paneName;
	    }
	}
}

