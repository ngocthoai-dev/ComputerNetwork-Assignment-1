package CommonServer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CommonServer extends JFrame {
	private int port;
	private ArrayList<User> clients;
	private HashMap<String, ArrayList<User> > groups;
	private HashMap<User, ArrayList<String> > listPane; 
	private ServerSocket server;
	private final String userPath = "login.json";
	
	private JTextPane serverTextPane;

	public static void main(String[] args) {
		try {
			new CommonServer().run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public CommonServer() throws IOException {
		this.port = 12345;
		this.clients = new ArrayList<User>();
		this.groups = new HashMap<String, ArrayList<User> >();
		this.listPane = new HashMap<User, ArrayList<String> >();
		
		JScrollPane serverScrollPane = new JScrollPane();
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(serverScrollPane, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(serverScrollPane, GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
		);
		
		this.serverTextPane = new JTextPane();
		serverScrollPane.setViewportView(this.serverTextPane);
		getContentPane().setLayout(groupLayout);
		this.serverTextPane.setEditable(false);
		this.serverTextPane.setContentType("text/html");
		setVisible(true);
	}
	
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

	public void run() throws IOException {
		this.server = new ServerSocket(this.port);
		String ip = "127.0.0.1";
		try(final DatagramSocket socket = new DatagramSocket()){
			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
		    ip = socket.getLocalAddress().getHostAddress();
		} catch(Exception e) {
			e.printStackTrace();
		}
		appendToPane(this.serverTextPane, "Server: " + ip + ", Port " + this.port + " is now open.");

		while (true) {
			// accepts a new client
			Socket client = server.accept();
			appendToPane(this.serverTextPane, "New Client host: " + client.getInetAddress().getHostAddress());
			new userChecking(this, client).start();
		}
	}
	
	class userChecking extends Thread {
		CommonServer server;
		Socket client;
		
		public userChecking(CommonServer server, Socket client) {
			this.server = server;
			this.client = client;
		}
		
		@SuppressWarnings("unchecked")
		public void run() {
			// create new User
			try {
				boolean login = false;
				while(!login) {
		            BufferedReader in = new BufferedReader(new InputStreamReader(this.client.getInputStream(), "utf-8"));
		       		PrintWriter out = new PrintWriter(this.client.getOutputStream(), true);
		            String info = in.readLine();
		            String type = info.substring(0, info.indexOf(":"));
		            String username = info.substring(info.indexOf(":")+1, info.indexOf(","));
		            String password = info.substring(info.indexOf(",")+1, info.length());
		            String newPort = "";
		            int decision = checkUser(username, password);
		            if(type.equals("register")){
		            	if(decision > 0){
		            		out.println("Existed!");
		            	} else {
		            		try (FileReader reader = new FileReader(userPath)){
						        //Read JSON file
						        JSONParser parser = new JSONParser();
						    	JSONObject user = (JSONObject) parser.parse(reader);
						        JSONArray employeeArr = (JSONArray) user.get("user");
						        newPort = String.valueOf(employeeArr.size() + 1024);
						        String img = "assets\\person_icon.png";
						    	JSONObject employeeDetails = new JSONObject();
						        employeeDetails.put("username", username);
						        employeeDetails.put("password", password);
						        employeeDetails.put("port", newPort);
						        employeeDetails.put("image", img);
						        employeeArr.add(employeeDetails);
						    	try (FileWriter file = new FileWriter(userPath)) {
						            file.write(user.toJSONString());
						            file.flush();
						            out.println("Successfully Registered!");
						 
						        } catch (IOException e) {
						            e.printStackTrace();
						        }
	
								// add newUser message to list
			        			User newUser = new User(username, newPort, this.client, in, out, this.client.getInetAddress().getHostAddress());
								clients.add(newUser);
			        			newUser.getOutStream().println(newUser.getPort());
			        			newUser.getOutStream().println(img);
	
								// Welcome msg
			        			// https://steamuserimages-a.akamaihd.net/ugc/963099873035049668/397B115E5FF3FE0D1034C20E36063D631A5C4002/
			        			// https://i.gifer.com/3BBW.gif
			        			
			        			// https://data.whicdn.com/images/226549072/original.gif
			        			// https://pa1.narvii.com/6689/29bfe1136108c8e708196487dab32eec4059fdbf_hq.gif
			        			// https://thumbs.gfycat.com/MeaslyNegativeGoldenmantledgroundsquirrel-size_restricted.gif
			        			// https://cdn130.picsart.com/235791987029202.gif?r240x240
			        			String welcomeMessage = "<p style='margin: 5px 0 0 0;' align='center'><img width='100' height='120' src='http://giphygifs.s3.amazonaws.com/media/IcJ6n6VJNjRNS/200.gif'><b> Welcome: " 
														+ newUser.toString() + 
														" to This App </b><img width='100' height='120' src='http://giphygifs.s3.amazonaws.com/media/IcJ6n6VJNjRNS/200.gif'></p>";
								newUser.getOutStream().println(welcomeMessage);
								new UserHandler(this.server, newUser).start();
			        			login = true;
						    }
						    catch (FileNotFoundException e) {
						    	this.client.close();
						    } catch (IOException e) {
						    	this.client.close();
						    } catch (ParseException e) {
						    	this.client.close();
						    }
		            	}
					} else {
		            	if(decision == 0){
		            		out.println("Not Exist!");
		            	} else if(decision == 1) {
		            		out.println("Wrong Password!");      	
		            	} else if(decision == 2) {
		            		out.println("User has Logged in!");
		            	} else {
		            		newPort = String.valueOf(decision);
		            		out.println("Logged in!");
		            		
		        			// add newUser message to list
		        			User newUser = new User(username, newPort, this.client, in, out, this.client.getInetAddress().getHostAddress());
		        			clients.add(newUser);
		        			newUser.getOutStream().println(newUser.getPort());
		        			try (FileReader reader = new FileReader(userPath)) {
		        		        //Read JSON file
		        		        JSONParser parser = new JSONParser();
		        		    	JSONObject user = (JSONObject) parser.parse(reader);
		        		    	JSONArray userList = (JSONArray) user.get("user");
		        		        for(int i=0; i<userList.size(); i++) {
		        		        	JSONObject emp = (JSONObject) userList.get(i);  
		        			        String usernameDB = (String) emp.get("username");    
		        					if (newUser.getNickname().equals(usernameDB)) {
		    		        			newUser.getOutStream().println(emp.get("image"));
		        					}
		        		        }
		        			} catch (FileNotFoundException e) {
		        				e.printStackTrace();
		        			} catch (IOException | ParseException e) {
		        				e.printStackTrace();
		        			}
	
		        			// Welcome msg
		        			String welcomeMessage = "<p style='margin: 5px 0 0 0;' align='center'><img width='100' height='120' src='http://giphygifs.s3.amazonaws.com/media/IcJ6n6VJNjRNS/200.gif'><b> Welcome: " 
													+ newUser.toString() + 
													" to This App </b><img width='100' height='120' src='http://giphygifs.s3.amazonaws.com/media/IcJ6n6VJNjRNS/200.gif'></p>";
		        			newUser.getOutStream().println(welcomeMessage);
		        			
		        			new UserHandler(this.server, newUser).start();
		        			login = true;
		            	}
					}
				}
			} catch (IOException e1) {
		    	try {
					this.client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public int checkUser(String username, String password) {
		for(User cli : this.clients) {
			if(cli.getNickname().equals(username)) {
				return 2;
			}
		}
		try (FileReader reader = new FileReader(userPath)) {
	        //Read JSON file
	        JSONParser parser = new JSONParser();
	    	JSONObject user = (JSONObject) parser.parse(reader);
	    	JSONArray userList = (JSONArray) user.get("user");
	        for(int i=0; i<userList.size(); i++) {
	        	JSONObject emp = (JSONObject) userList.get(i);  
		        String usernameDB = (String) emp.get("username");    
		        String passwordDB = (String) emp.get("password");
				if (username.equals(usernameDB)) {
					if(password.equals(passwordDB)) {
						int port = Integer.valueOf(emp.get("port").toString());
						return port;
					}
					return 1;
				}
	        }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	// delete a user from the list
	public void removeUser(User user) {
		this.clients.remove(user);
		for (String groupName : this.groups.keySet()) {
			ArrayList<User> client = this.groups.get(groupName);
			if(client.contains(user)) {
				client.remove(user);
				this.groups.replace(groupName, client);
			}
			removeGroup(groupName);
		}
	}

	public void removeGroup(String groupName) {
		if(this.groups.size() <= 2)
			this.groups.remove(groupName);
	}

	// send incoming msg to all Users
	public void broadcastMessages(String msg, User userSender) {
		for (User client : this.clients) {
			if(!client.getNickname().equals(userSender.getNickname())) {
				client.getOutStream().println("<p style='margin:0; color:rgb(250, 98, 27);'>" + userSender.toString() + ": " + msg + "</p>");
			}
		}
	}

	// send list of clients and groups to all Users
	public void broadcastAllUsers() {
		listPane = new HashMap<User, ArrayList<String> >();
		for (User client : this.clients) {
			listPane.put(client, new ArrayList<String> ());
		}
		for (String groupName : this.groups.keySet()) {
			for (User client : this.groups.get(groupName)) {
				listPane.get(client).add("[" + groupName + "]" + ":" + this.groups.get(groupName).toString() + "-");
			}
		}
		for (User client : this.clients) {
			listPane.get(client).add(this.clients.toString());
		}
		for(User client : listPane.keySet()) {
			String listTemp = listPane.get(client).toString().substring(1, listPane.get(client).toString().length()-1);
			client.getOutStream().println(listTemp);
		}
	}

	// send message to a User 
	public void uni_castMessages(String msg, User userSender, String userDest) {
		if(userDest.equals(""))
			userSender.getOutStream().println(userSender.toString() + " -> <b>(self)</b>: " + msg);
		else {
			boolean found = false;
			for (User client : this.clients) {
				if(client.getNickname().equals(userDest) && client != userSender) {
					found = true;
					userSender.getOutStream().println(userSender.toString() + " -> <b>" + client.toString() + "</b> : " + msg);
					client.getOutStream().println("(Private)" + userSender.toString() + ": " + msg);
				}
			}
			if (!found) {
				userSender.getOutStream().println(userSender.toString() + " -> <b>(self)</b>: " + msg);
			}
		}
	}

	class UserHandler extends Thread {
		private CommonServer server;
		private User user;

		public UserHandler(CommonServer server, User user) {
			this.server = server;
			this.user = user;
			this.server.broadcastAllUsers();
		}

		@SuppressWarnings("unchecked")
		public void run() {
			// when there is a new message, broadcast to all
			boolean discon = false;
			while (!discon) {
				String message;
				try {
					message = this.user.getInputStream().readLine();
					
					// emoji
		            message = message.replace("#:)", "<img height=40 width=40 src='https://cdn.shopify.com/s/files/1/1061/1924/files/Hugging_Face_Emoji_2028ce8b-c213-4d45-94aa-21e1a0842b4d_large.png?15202324258887420558'>");
		            message = message.replace("#:D", "<img height=40 width=40 src='https://images.vexels.com/media/users/3/134594/isolated/preview/cb4dd9ad3fa5ad833e9b38cb75baa18a-happy-emoji-emoticon-by-vexels.png'>");
		            message = message.replace("#:(", "<img height=40 width=40 src='https://vectorskey.com/wp-content/uploads/2019/01/crying-sad-emoji.png'>");
		            message = message.replace("#;|", "<img height=40 width=40 src='https://pngriver.com/wp-content/uploads/2018/04/Download-Unamused-Face-Emoji-PNG.png'>");
		            message = message.replace("#:p", "<img height=40 width=40 src='https://www.pinclipart.com/picdir/middle/357-3571823_free-png-download-ios-10-crying-laughing-emoji.png'>");
		            message = message.replace("#:o", "<img height=40 width=40 src='https://www.pinpng.com/pngs/m/2-24416_emoji-omg-png-emoji-iphone-transparent-png.png'>");
		            message = message.replace("#:O", "<img height=40 width=40 src='https://www.pinpng.com/pngs/m/47-477291_emoji-tumblr-images-in-collection-page-png-tumblr.png'>");
		            message = message.replace("#:hmm", "<img height=40 width=40 src='https://www.pngfind.com/pngs/m/81-815205_question-face-blinking-emoji-emoji-angry-png-transparent.png'>");
		            message = message.replace("#:wow", "<img height=40 width=40 src='https://www.pngix.com/pngfile/middle/9-92071_the-new-emojis-coming-to-your-iphone-star.png'>");
		            message = message.replace("#:|", "<img height=40 width=40 src='https://img.pngio.com/pile-of-poo-emoji-png-and-pile-of-poo-emoji-transparent-clipart-pile-of-poo-emoji-png-260_260.jpg'>");
		            
					// handle messages private
					if (message.charAt(0) == '@') {
						if(message.contains(":")) {
							int firstSpace = message.indexOf(":");
							String userPrivate= message.substring(1, firstSpace);
							this.server.uni_castMessages(message.substring(firstSpace+1, message.length()), user, userPrivate);
						} else {
							this.server.uni_castMessages(message.substring(1, message.length()), user, "");
						}
					} else {
						// log out
						if(message.startsWith("IAMLOGGINGOUT")) {
							new userChecking(this.server, this.user.getSocket()).start();
							removeUser(this.user);
							continue;
						}
						// change photo
						if(message.startsWith("CHANGEIMAGE:")) {
							try (FileReader reader = new FileReader(userPath)) {
						        //Read JSON file
						        JSONParser parser = new JSONParser();
						    	JSONObject user = (JSONObject) parser.parse(reader);
						    	JSONArray userList = (JSONArray) user.get("user");
						        for(int i=0; i<userList.size(); i++) {
						        	JSONObject emp = (JSONObject) userList.get(i);  
							        String usernameDB = (String) emp.get("username");    
							        String passwordDB = (String) emp.get("password");
							        String port = (String) emp.get("port");
									if (this.user.getNickname().equals(usernameDB)) {
										userList.remove(i);
								    	JSONObject employeeDetails = new JSONObject();
								        employeeDetails.put("username", usernameDB);
								        employeeDetails.put("password", passwordDB);
								        employeeDetails.put("port", port);
								        employeeDetails.put("image", message.substring(12));
								        userList.add(employeeDetails);
								    	try (FileWriter file = new FileWriter(userPath)) {
								            file.write(user.toJSONString());
								            file.flush();
								        } catch (IOException e) {
								            e.printStackTrace();
								        }
								    	break;
									}
						        }
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException | ParseException e) {
								e.printStackTrace();
							}
							continue;
						}
						
						// send chat server port
						if(message.contains("REUPDATEUSERLISTANDGROUPLIST")) {
							//output.println("REUPDATEUSERLISTANDGROUPLIST:" + name + ":" + groupName + ":" + ListGroup.get(groupName).toString())
							String[] groupCreateRequest = message.split(":");
							groups.put(groupCreateRequest[2], new ArrayList<User>());
							groups.get(groupCreateRequest[2]).add(this.user);
							for(User client : clients) {
								if(groupCreateRequest[3].substring(1, groupCreateRequest[3].length()-1).contains(client.getNickname())) {
									groups.get(groupCreateRequest[2]).add(client);
								}
							}
							this.server.broadcastAllUsers();		
						} else if(message.contains("CHATLISTENERREQUESTTO")) {
							if (message.contains("CHATLISTENERREQUESTTOUSER")) {
								String[] addressRequest = message.split(":");
								String listListener = "LISTENERLIST-";
								for(User client : clients) {
									if(addressRequest[1].equals(client.getNickname())) {
										listListener = listListener + client.getAddress() + ":" + String.valueOf(client.getPort());
										String subListener = "REQUESTLISTENERFROMUSER-" + this.user.getNickname() + "@" + this.user.getAddress() + ":" + this.user.getPort();
										client.getOutStream().println(subListener);
										break;
									}
								}
								this.user.getOutStream().println(listListener);
							} else {
								String[] addressRequest = message.split(":");
								String listListener = "LISTENERLIST-";
								ArrayList<User> hostName = groups.get(addressRequest[1]);
								for(User client : hostName) {
									if(!client.getNickname().equals(this.user.getNickname())) {
										String subListener = "REQUESTLISTENERFROMGROUP-";
										for (User sub : hostName) {
											if(!client.getNickname().equals(sub.getNickname())) {
												if(subListener.equals("REQUESTLISTENERFROMGROUP-"))
													subListener = subListener + addressRequest[1] + "@" + client.getAddress() + ":" + String.valueOf(client.getPort());
												else
													subListener = subListener + "," + client.getAddress() + ":" + String.valueOf(client.getPort());
												client.getOutStream().println(subListener);																							
											}
										}
										if(listListener.equals("LISTENERLIST-"))
											listListener = listListener + client.getAddress() + ":" + String.valueOf(client.getPort());
										else
											listListener = listListener + "," + client.getAddress() + ":" + String.valueOf(client.getPort());
									}
								}
								this.user.getOutStream().println(listListener);	
							}
						} else {
							// update user list
							this.server.broadcastMessages(message, user);
						}
					}
				} catch (IOException e) {
					// end of Thread
					this.server.removeUser(user);
					this.server.broadcastAllUsers();
					discon = true;
				}
			}
		}
	}

	class User {
	    private int noUser;
	    private int userId;
	    private Socket socket;
	    private PrintWriter streamOut;
	    private BufferedReader streamIn;
	    private String nickname;
	    private String port;
	    private String address;

	    // constructor
	    public User(String name, String port, Socket socket, BufferedReader in, PrintWriter output, String address) throws IOException {
	    	this.socket = socket;
	        this.streamOut = output;
	        this.streamIn = in;
	        this.nickname = name;
	        this.userId = noUser;
	        this.address = address;
	        this.port = port;
	        noUser += 1;
	    }

	    public PrintWriter getOutStream() {
	        return this.streamOut;
	    }

	    public BufferedReader getInputStream() {
	        return this.streamIn;
	    }

	    public String getNickname() {
	        return this.nickname;
	    }

	    public String getPort() {
	        return this.port;
	    }

	    public int getUserID() {
	        return this.userId;
	    }

	    public String toString() {
	        return this.getNickname();
	    }

	    public int getNoUser() {
	        return noUser;
	    }
	    
	    public String getAddress() {
	    	return this.address;
	    }
	    
	    public Socket getSocket() {
	    	return this.socket;
	    }
	}
}


