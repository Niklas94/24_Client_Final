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
	boolean waiting;


	public void run() {

		while(true){
			connect();
		}
	}

	public void connect() {

		try {
			Socket socket = new Socket(address, PORT);
			System.out.println("Connection established.");
			toSocket = new DataOutputStream(socket.getOutputStream());
			inStream = socket.getInputStream();
			inFromSocket = new Scanner(inStream);
			fromUser = new BufferedReader(new InputStreamReader(System.in));
			fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));


			synchronized(this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void sendMessage(String msg) {

		try {
			if (toSocket != null) {
				toSocket.writeBytes(msg  + " \n\r");
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String receiveMessage() throws NoInputFoundException {
		boolean received = false;

		String inLine = "";
		try {
			while(true){
				if(inStream.available() > 0){
					received = true;
					break;
				}
			}
			if (received){
				inLine = inFromSocket.nextLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		//if(!inLine.equals("")){
		return inLine;
		//	}

		//throw new NoInputFoundException("No input from socket.");
	}

	@Override
	public void resume(){
		waiting = false;
	}

	@Override
	public void closeConnection(Socket socket) {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
