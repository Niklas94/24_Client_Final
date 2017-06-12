package socket;

import java.net.Socket;

public interface ISocketController extends Runnable {
	
	public final static int PORT = 8000;
	public final static String address = "localhost";
	
	public void sendMessage(String msg);
	public String receiveMessage() throws NoInputFoundException;
	public void resume();
	public void closeConnection(Socket socket);
	
}
