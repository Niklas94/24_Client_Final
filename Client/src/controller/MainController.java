package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dbConnection.DALException;
import dbConnection.MySQLAccess;
import socket.ISocketController;
import socket.NoInputFoundException;

public class MainController implements IMainController {

	private ISocketController socketController;
	String id;
	boolean userExists;
	Object[] materials = new Object[10];

	public MainController(ISocketController socketController) {
		this.socketController = socketController;
		
	}

	public void start(){

		new Thread(socketController).start();
		
		try {MySQLAccess MySQLAccess = new MySQLAccess(); } 
		catch (InstantiationException e) {e.printStackTrace(); } 
		catch (IllegalAccessException e) {e.printStackTrace(); } 
		catch (ClassNotFoundException e) {e.printStackTrace(); } 
		catch (SQLException e) {e.printStackTrace(); }
		
		go();

	}

	public void go() {

		ResultSet rs;
		
		while(true){
			
			Wait(500);

			sequence("K 4");

//			Wait(500);
//
//			sequence("RM208 Enter Operator ID");

			Wait(500);
			
			try {
				String name = null;
				rs = MySQLAccess.doQuery("SELECT name FROM operator WHERE id = " + sequence("RM208 Enter your operator id")); //TODO add method to get id
					while(rs.next()){
						name = rs.getString("name");
						break;
					}
				sequence("RM208 You are: " + name + "?");
			} catch (DALException | SQLException e) {
				e.printStackTrace();
			}
			
//			Wait(500);
//
//			sequence("RM208 Enter Productbatch ID");
			
			try {
				String receipt = null;
				rs = MySQLAccess.doQuery("SELECT receipt FROM productbatch WHERE pbId = " + sequence("RM208 Enter Productbatch ID")); //TODO add method to get id (same as above)
					while(rs.next()) {
						receipt = rs.getString("receipt");
						break;
					}
				sequence("RM208 You are about to create " + receipt);
			} catch (DALException | SQLException e) {
				e.printStackTrace();
			}

			Wait(5000);

			for (Object o: materials) { //For each material in the receipt...
				
				while(!(sequence("RM208 Check if the weight is idle").equals("ok"))) { 
					//TODO DALException? Check if weight actually is 0?
					System.out.println("Press ok on simulator to continue...");	
				}
							
				sequence("T");
				
				Wait(500);
				
				//Insert tara container and tara its weight
				while(!sequence("RM208 Place taracontainer on the weight").equals("ok")) {
					//TODO Check if weight has changed?
					System.out.println("Taracointainer weight not recieved...");
				}
				
				sequence("T"); //TODO register tara containers weight...
				
				Wait(500);
				
				//Material batch number...
				
				while (!(sequence("RM208 Enter product batch number").equals("ok"))) {
					//TODO validate
					System.out.println("Product batch number not recieved...");
				}
				
				
				
				
				Wait(500);
				
				
				sequence("T");
				
			}
			


			sequence("T");

			Wait(500);

			sequence("RM208 Place your material in the bowl.");

			Wait(500);

			sequence("B");

			Wait(500);

			sequence("T");

			Wait(500);
			
			//Productbatch is finished....
			try {
				MySQLAccess.doUpdate("UPDATE productbatch SET status = 1 WHERE pbId = " + ""); //TODO add method to get id (same as previously)
				MySQLAccess.doUpdate("UPDATE productbatch SET status = 2 WHERE pbId = " + ""); //TODO same as above...
			} catch (DALException e) {
				e.printStackTrace();
			}
		}
	}

	private void Wait(int milliseconds) {

		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}
	}

	private String sequence(String str) {
		socketController.sendMessage(str);

		String answer = "";
		try {
			answer = socketController.receiveMessage();
			System.out.println(answer);
		} catch (NoInputFoundException e) {
			e.printStackTrace();
		}
		synchronized(socketController) {
			socketController.notify();
		}
		if(answer.equals("Command received.")) {
			id = "";
			try {
				id = socketController.receiveMessage();
			} catch (NoInputFoundException e) {
				e.printStackTrace();
			}
			System.out.println(id);
		}
		return answer;
	}

	public String getName(int id1) {
		String name1 = "";

		System.out.println(name1);
		return name1;
	}


	//1.Laboranten har modtaget en produktionsforskrift p� papir fra v�rkf�reren.
	//
	//2.Laboranten v�lger en afvejningsterminal.
	//
	//3.Laboranten indtaster laborant nr.
	//
	//4.V�gten svarer tilbage med laborantnavn som s� godkendes.
	//
	//5.Laboranten indtaster produktbatch nummer.
	//
	//6 V�gten svarer tilbage med navn p� recept der skal produceres (eks: saltvand med citron)
	//
	//7.Laboranten kontrollerer at v�gten er ubelastet og trykker �ok�
	//
	//
	//9.V�gten tareres.
	//
	//10.V�gten beder om f�rste tara beholder.
	//
	//11.Laborant placerer f�rste tarabeholder og trykker �ok�
	//
	//12.V�gten af tarabeholder registreres
	//
	//13.V�gten tareres.
	//
	//14.V�gten beder om raavarebatch nummer p� f�rste r�vare.
	//
	//15.Laboranten afvejer op til den �nskede m�ngde og trykker �ok�
	//
	//16.Pkt. 7 � 14 gentages indtil alle r�varer er afvejet.
	//
	//8.Systemet s�tter produktbatch nummerets status til �Under produktion�.
	//17.Systemet s�tter produktbatch nummerets status til �Afsluttet�.
	//
	//18.Det kan herefter genoptages af en ny laborant.
	//


}
