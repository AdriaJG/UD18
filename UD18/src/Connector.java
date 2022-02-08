import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;
public class Connector {
	static String ip = "172.31.91.95";
	static String user = "mboot";
	static String password = "-1q2w3e4R-";
	static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	static Connection conexion;
	
	public static void connexion() {
		try {
			Class.forName(DRIVER);
			conexion=DriverManager.getConnection("jdbc:mysql://"+ ip +":3306?useTimezone=true&serverTimezone=UTC",user,password);//credenciales temporales
			System.out.print("Server Connected");
			
		}catch(SQLException | ClassNotFoundException ex  ){
			System.out.print("No se ha podido conectar con mi base de datos");
			System.out.println(ex.getMessage());
			
		}
	}
	
	//Crea la base de datos
	public static void createDB(String name) {
		try {
			String Query="CREATE DATABASE "+ name;
			Statement st= conexion.createStatement();
			st.executeUpdate(Query);
			System.out.println("DB creada con exito!");
			
		JOptionPane.showMessageDialog(null, "Se ha creado la DB " +name+ "de forma exitosa.");
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
			System.out.println("Error creando la DB.");
		}	
	}
	
	//Crea tablas en la base de datos
	public static void createTable(String db,String name, String consulta) {
		
		try {
			String Querydb = "USE "+db+";";
			Statement stdb= conexion.createStatement();
			stdb.executeUpdate(Querydb);
			
			String Query = "CREATE TABLE IF NOT EXISTS " + name + "(" + consulta + ")";
			Statement st= conexion.createStatement();
			st.executeUpdate(Query);
			System.out.println("Tabla creada con exito!");
			
		}catch (SQLException ex){
			System.out.println(ex.getMessage());
			System.out.println("Error crando tabla.");
			
		}
		
	}
	
	//Inserta datos en las tablas
	public static void insertData(String db, String table_name, String filas) {
		try {
			String Querydb = "USE "+db+";";
			Statement stdb= conexion.createStatement();
			stdb.executeUpdate(Querydb);
						
			String Query = "REPLACE " + table_name + " () VALUES " + filas;
			Statement st = conexion.createStatement();
			st.executeUpdate(Query);
			
			System.out.println("Datos almacenados correctamente");;
			
		} catch (SQLException ex ) {
			System.out.println(ex.getMessage());
			JOptionPane.showMessageDialog(null, "Error en el almacenamiento");
		}
					
	}
	
	//Obtiene datos de la base de datos
	public static ResultSetMetaData obtenerResultados(String db, String tabla) throws SQLException {
		String Querydb = "USE "+db+";";
		Statement stdb= conexion.createStatement();
		stdb.executeUpdate(Querydb);
					
		String Query = "SELECT * FROM " + tabla;
		Statement st = conexion.createStatement();
		java.sql.ResultSet resultSet;
		resultSet = st.executeQuery(Query);
		ResultSetMetaData informacion = resultSet.getMetaData();
		return informacion;
	}
	
	//Obtiene todas las tablas de la base de datos especificada
	public static String[] obtenerTablas(String db) throws SQLException {
		String Querydb = "USE "+db+";";
		Statement stdb= conexion.createStatement();
		stdb.executeUpdate(Querydb);
		
		DatabaseMetaData metadata = conexion.getMetaData();
		ResultSet tablas = metadata.getTables(db, null, "%", null);
		ArrayList<String> nombreTablas = new ArrayList<>();
		while(tablas.next()) {
			nombreTablas.add(tablas.getString("TABLE_NAME"));
		}
		
		return nombreTablas.toArray(new String[0]);
	}
	
	public void cerrarCinexion() throws SQLException {
		conexion.close();
	}
	
}
