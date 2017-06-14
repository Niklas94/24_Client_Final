package socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;


public class SocketController implements ISocketController {

	static DataOutputStream toSocket;
	static BufferedReader fromSocket, fromUser;
	static Scanner inFromSocket;
	static InputStream inStream;
	static Socket socket;
	boolean waiting;

	@Override
	public void connect() {

		try {
			socket = new Socket(address, PORT);
			System.out.println("Connection established.");
			toSocket = new DataOutputStream(socket.getOutputStream());
			inStream = socket.getInputStream();
			inFromSocket = new Scanner(inStream);
			fromUser = new BufferedReader(new InputStreamReader(System.in));
			fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void sendMessage(String msg) {
		try {
			if (toSocket != null) {
				toSocket.writeBytes(msg  + "\n\r");
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String receiveMessage() throws NoInputFoundException {
		
		String inLine = "";
		try {
			while(inLine.equals("")){
				if(inStream.available() > 0){
					inLine = inFromSocket.nextLine();					
				}
			}
		} catch (IOException e) { e.printStackTrace(); }
		return inLine;
	}

	@Override
	public void resume(){
		waiting = false;
	}

	@Override
	public void closeConnection() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String readFromWeight() {
		String msg = "";
		try {
			while(true) {
				if (inStream.available() > 0) {
					msg = fromSocket.readLine();
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return msg;
	}

}
