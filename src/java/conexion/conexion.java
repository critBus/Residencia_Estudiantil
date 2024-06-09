
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class conexion {

    Connection con1 = null;
    Connection con2 = null;

    public Connection ConexionSigreBD() {
        try {
            Class.forName("org.postgresql.Driver");
            con1 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Residencia_Unah", "postgres", "postgres");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con1;
    }

    public Connection ConexionUnah() {
        try {
            Class.forName("org.postgresql.Driver");
            con2 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Residencia_Unah", "postgres", "postgres");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con2;
    }

    public void CerrarConexionRes(Connection con) {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
