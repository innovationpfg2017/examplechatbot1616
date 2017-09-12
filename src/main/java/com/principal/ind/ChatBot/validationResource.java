package com.principal.ind.ChatBot;


import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



@Path("/validate")
public class validationResource {
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public String addMessage(String jsonString) throws Exception{
		return "Sample test text"+jsonString;

	}


	private static Connection getConnection() throws Exception {
		URI dbUri = new URI("postgres://ikkwdpnxycsfmk:cd7d023968ceeb7f7e5d90fc8c40c1e29ad05d024f60007fb6ca78847c43fb65@ec2-54-163-246-154.compute-1.amazonaws.com:5432/d8gjfv40tdgpim");

//		String username = dbUri.getUserInfo().split(":")[0];
//		String password = dbUri.getUserInfo().split(":")[1];
//		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();
	    String username = dbUri.getUserInfo().split(":")[0];
	    String password = dbUri.getUserInfo().split(":")[1];
	    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
		return DriverManager.getConnection(dbUrl, username, password);
	}


	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getMessages() throws Exception{
		Class.forName("org.postgresql.Driver");
		Connection connection = getConnection();

		Statement stmt = connection.createStatement();
		System.out.println("Connection done!!");
		ResultSet rs = stmt.executeQuery("SELECT policy_num from POLICY_DETAILS");
		Integer lastValue = 0;
		while (rs.next()) {
			System.out.println("Read from DB: " + rs.getInt(1));
			lastValue= rs.getInt(1);
		}
		return "Get reached!! "+lastValue;
	}
}
