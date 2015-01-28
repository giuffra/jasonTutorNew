package jasonTutor;

import java.sql.SQLException;

import artifact.BD_Artifact;
import artifact.db_art;
public class test {

	public static void main(String[] args) {
		db_art a = db_art.getInstance();
		BD_Artifact b = new BD_Artifact();
		 String string = "SELECT * from mdl_user";
		try {
			a.connectBD();
			a.select(string).next();
			a.fecharConexao();
			b.conexaoBD();
			b.select(string).next();
			b.fecharConexao();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
