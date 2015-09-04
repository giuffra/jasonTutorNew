// CArtAgO artifact code for project tutor

package artifact;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import cartago.Artifact;
import cartago.INTERNAL_OPERATION;
import cartago.OPERATION;

//import cartago.OpFeedbackParam;

public class BD_Artifact extends Artifact {

	Connection conn;
	boolean counting;
	final static long TICK_TIME = 20;// 86400000; //24 horas em milisegundos

	public void init(int initialValue) {
		counting = false;
		// defineObsProperty("idCourse", 2);
	}

	public ResultSet select(String string) throws SQLException,
			ClassNotFoundException {
		ResultSet result = null;
		try {
			conexaoBD();
			Statement stm = conn.createStatement();
			result = stm.executeQuery(string);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			// fecharConexao();
		}
		return result;
	}

	void nada() {
		System.out.println("");
	}

	public int update(String string) throws SQLException,
			ClassNotFoundException {
		int result = 0;
		try {
			Statement stm = conn.createStatement();
			result = stm.executeUpdate(string);
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	@OPERATION
	public Connection conexaoBD() throws ClassNotFoundException, SQLException {
		try {
			// Este � um dos me"ios para registrar um driver
			Class.forName("org.gjt.mm.mysql.Driver");

		// Registrado o driver, vamos estabelecer uma conexão
			conn = DriverManager.getConnection("jdbc:mysql://posiate.inf.ufsc.br/moodle_cecilia", "cecilia", "cecilia");		

					
		} catch (ClassNotFoundException e) {
			// Driver n�o encontrado
			System.out.println("O driver expecificado não foi encontrado.");
		} catch (SQLException e) {
			// N�o est� conseguindo se conectar ao banco
			System.out.println("Não foi possível conectar ao Banco de Dados");
		}
		return conn;
	}

	public void fecharConexao() {// fechar conex�o com o Banco de dados
		if (conn != null)
			try {
				conn.close();
			} catch (Exception e) {
			}
	}

	@OPERATION
	void start() {
		if (!counting) {
			System.out.println("entrei no start");
			counting = true;
			execInternalOp("count");
		} else {
			failed("already_counting");
		}
	}

	@OPERATION
	void stop() {
		counting = false;
	}

	@INTERNAL_OPERATION
	void count() {
		while (counting) {
			signal("tick");
			System.out.println("entrei no count");
			await_time(TICK_TIME);
		}
	}

	@OPERATION
	void test() {
		try {
			this.conexaoBD();
			ResultSet t = this.select("SELECT username FROM mdl_user");
			while (t.next()) {
				System.out.println(t.getString(1));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
