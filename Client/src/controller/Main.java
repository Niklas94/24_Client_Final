package controller;

import socket.ISocketController;
import socket.SocketController;

public class Main {

	public static void main(String[] args) {
		ISocketController socketController = new SocketController();
		IMainController mainController = new MainController(socketController);
		mainController.start();
		
	}
}


