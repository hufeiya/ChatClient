import java.awt.List;
import java.util.ArrayList;
import java.io.*; 
import java.net.*; 
import java.util.*; 

public class ChatServer {
	public ArrayList<Socket> clients = new ArrayList<Socket>();
	private void startServer(){
		ServerSocket server;
		try {
			server = new ServerSocket(12345);
			while(true)
			{
				System.out.println("Waiting...");
				Socket s = server.accept();
				System.out.println("Starting!");
				clients.add(s);
				new ClientThread(s).start();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	class ClientThread extends Thread
	{
		Socket s;
		BufferedReader in;
		public ClientThread(Socket client) 
		{
			s = client;
			try 
			{
				in = new BufferedReader(new InputStreamReader(s.getInputStream(),"utf-8"));
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		@Override
		public void run() {
			try {
				String sentence = null;
				while((sentence = in.readLine()) != null)
				{
					for(Socket s : clients)
					{
						try {
							s.getOutputStream().write((sentence + "\n").getBytes("utf-8"));
							
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		new ChatServer().startServer();
	}
}
