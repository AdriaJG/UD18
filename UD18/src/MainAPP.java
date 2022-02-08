import java.awt.HeadlessException;
import java.sql.SQLException;

public class MainAPP {

	public static void main(String[] args) throws HeadlessException, SQLException {
		// TODO Auto-generated method stub
		Connector.connexion();
		
		DBControl experimento = new DBControl("TESTEANDO");
		experimento.crearDB();
		experimento.crearTabla("tablatest", "ID INT PRIMARY KEY AUTO_INCREMENT, Nombre VARCHAR(50),Apellido VARCHAR(50)");
		experimento.jInsercion();
	}

}
