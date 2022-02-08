import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

public class DBControl {
	
	private String database;
	
	public DBControl(String database) {
		this.database = database;
	}
	
	public void crearDB()  {
		Connector.createDB(database);
		
	}
	
	public void crearTabla(String nom, String consulta) {
		Connector.createTable(database, nom, consulta);
	}
	
	public void insertar(String tabla, String consulta) {
		Connector.insertData(database, tabla, consulta);
	}
	
	//Metodo que obtiene el nombre de las columnas de una tabla
	public String[] obtenerColumnas(String tabla) throws SQLException {
		ResultSetMetaData informacion = Connector.obtenerResultados(database, tabla);
		int numCol = informacion.getColumnCount();
		String[] decodificador = new String[numCol];
		for (int i = 0; i < numCol; i++) {
			decodificador[i] = informacion.getColumnName(i + 1);
		}
		 return decodificador;
		
	}
	
	//Metodo grafico para insertar filas en tablas
	public void jInsercion() throws HeadlessException, SQLException {
		String[] tablas = Connector.obtenerTablas(database);
		String input = (String) JOptionPane.showInputDialog(null, "Selecciona la tabla",
		        "Insertar filas en la tabla..", JOptionPane.QUESTION_MESSAGE, null, // Use
		        tablas, // Array of choices
		        tablas[0]); // Initial choice
		System.out.println(input);
		String[] columnas = obtenerColumnas(input);
		boolean continuar = true;
		String consulta = "";
		while(continuar == true) {
			consulta += "(";
		    	for (int i = 0; i < columnas.length; i++) {
					consulta += "\"" + JOptionPane.showInputDialog(columnas[i]) + "\",";
				}
		    	StringBuffer sb= new StringBuffer(consulta);
		    	sb.deleteCharAt(sb.length()-1);
		    	consulta = sb.toString();
		    	int añadirMasEntradas = JOptionPane.showConfirmDialog(null, "Añadir otra fila");
		    	if(añadirMasEntradas != JOptionPane.YES_OPTION) {
		    		consulta += ");";
		    		continuar = false;
		    	} else {
		    		consulta += "),";
		    	}
		    }
		System.out.println(consulta);
		insertar(input, consulta);
	}
	
}
