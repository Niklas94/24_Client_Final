package dbConnection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import dbConnection.DbConstants;


public class MySQLAccess {
	/*
	 * In order for the program to work as intended the Database must contain a table called
	 *  users(UserID INT(10), name VARCHAR(20), initials VARCHAR(4), password VARCHAR(15), cpr VARCHAR(11), roles VARCHAR(100), primary key(UserID)) 
	 */
	
	public static Connection connectToDatabase(String url, String username, String password) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// call the driver class' no argument constructor
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		
		// get Connection-object via DriverManager
		return (Connection) DriverManager.getConnection(url, username, password);
	}

	private static Connection conn;
	private static Statement stm;

	public MySQLAccess(String server, int port, String database, String username, String password) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		conn	= connectToDatabase("jdbc:mysql://"+server+":"+port+"/"+database,
				username, password);
		stm		= conn.createStatement();
	}
	
	public MySQLAccess() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		this(DbConstants.server, DbConstants.port, DbConstants.database,
				DbConstants.username, DbConstants.password);
	}
	
	public static ResultSet doQuery(String cmd) throws DALException {
		try { return stm.executeQuery(cmd); }
		catch (SQLException e) { throw new DALException(e); }
	}
	
	public static int doUpdate(String cmd) throws DALException {
		try { return stm.executeUpdate(cmd); }
		catch (SQLException e) { throw new DALException(e); }
	}

}
