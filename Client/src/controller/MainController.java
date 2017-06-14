package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import dbConnection.DALException;
import dbConnection.MySQLAccess;
import socket.ISocketController;
import socket.NoInputFoundException;

public class MainController implements IMainController {

	private ISocketController socketController;
	String id;

	public MainController(ISocketController socketController) {
		this.socketController = socketController;
	}

	public void start(){


//		try {MySQLAccess MySQLAccess = new MySQLAccess(); } 
//		catch (InstantiationException e) {e.printStackTrace(); } 
//		catch (IllegalAccessException e) {e.printStackTrace(); } 
//		catch (ClassNotFoundException e) {e.printStackTrace(); } 
//		catch (SQLException e) {e.printStackTrace(); }

		go();

	}

	public void go() {

		ResultSet rs;

		while(true){

			
			
			socketController.connect();
			//Change keystate to 4...
			sequence("K 4");
			
			socketController.closeConnection();
			//Read user id from weight...
			//msgToWeight("RM208 Enter your user ID");
			
			socketController.connect();
			sequence("RM208 Enter your user ID");
			socketController.closeConnection();

			System.out.println("Finished sequence");
			
		}
	}

	private String sequence(String str) {
		socketController.sendMessage(str);

		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String answer = "";
		try {
			answer = socketController.receiveMessage();
			System.out.println("answer is: " + answer);
		} catch (NoInputFoundException e) {
			e.printStackTrace();
		}
		
		return answer;
	}
	
	private void msgToWeight(String msg) {
		socketController.sendMessage(msg);
		try {
			socketController.receiveMessage();
		} catch (NoInputFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	//1.Laboranten har modtaget en produktionsforskrift på papir fra værkføreren.
	//
	//2.Laboranten vælger en afvejningsterminal.
	//
	//3.Laboranten indtaster laborant nr.
	//
	//4.Vægten svarer tilbage med laborantnavn som så godkendes.
	//
	//5.Laboranten indtaster produktbatch nummer.
	//
	//6 Vægten svarer tilbage med navn på recept der skal produceres (eks: saltvand med citron)
	//
	//7.Laboranten kontrollerer at vægten er ubelastet og trykker ’ok’
	//
	//
	//9.Vægten tareres.
	//
	//10.Vægten beder om første tara beholder.
	//
	//11.Laborant placerer første tarabeholder og trykker ’ok’
	//
	//12.Vægten af tarabeholder registreres
	//
	//13.Vægten tareres.
	//
	//14.Vægten beder om raavarebatch nummer på første råvare.
	//
	//15.Laboranten afvejer op til den ønskede mængde og trykker ’ok’
	//
	//16.Pkt. 7 – 14 gentages indtil alle råvarer er afvejet.
	//
	//8.Systemet sætter produktbatch nummerets status til ”Under produktion”.
	//17.Systemet sætter produktbatch nummerets status til ”Afsluttet”.
	//
	//18.Det kan herefter genoptages af en ny laborant.
	//


}
