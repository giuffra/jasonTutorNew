package artifact;

// CArtAgO artifact code for project moodleTutor

import java.sql.*;
import java.util.Map;

import cartago.Artifact;
import cartago.OPERATION;

public class db_art extends Artifact {
	protected Connection DB;
	protected Map<String, Integer> levelMap;

	String url = "jdbc:mysql://150.162.60.167:3306/moodle_cecilia";
    String login = "cecilia";
    String senha = "cecilia";

	private static db_art Dbinstance = null;

	void init(int initialValue) {
		defineObsProperty("count", initialValue);
		this.setLevelMap();
	}

	public void setLevelMap() {
		levelMap.put("professor", 10);
		levelMap.put("aluno", 30);
	}

	protected db_art() {

	}

	// @OPERATION
	// void inc() {
	// ObsProperty prop = getObsProperty("count");
	// prop.updateValue(prop.intValue()+1);
	// signal("tick");
	// }

	/**
	 * @brief Is a operation, open DB connection
	 * @throws ClassNotFoundException
	 *             when "driver not found" error
	 * @throws SQLException
	 *             when "can't connect DB" error
	 */
	@OPERATION
	public void connectBD() {
		try {
			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
			System.out.println("\nDriver carregado com sucesso!\n");
			try {
				DB = DriverManager
						.getConnection(url, login, senha);
				System.out.println("Conexão realizada");
			} catch (Exception ex) {
				System.out.println("\nErro no connection!");
			}
		} catch (Exception ex) {
			System.out.println("\nDriver nao pode ser carregado!");
		}
	}

	/**
	 * @brief "SELECT FROM WHERE" of SQL
	 * @alert EVER CLOSE THE CONNECTION
	 * @param select
	 * @param from
	 * @param where
	 * @return result of search
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public ResultSet sfw(String select, String from, String where)
			throws SQLException, ClassNotFoundException {
		try {
			String s = "SELECT " + select + " FROM " + from + " WHERE " + where;
			connectBD();
			return this.sql_execute(s);
		} finally {
			closeConnection();
		}
	}

	public ResultSet sql(String sql) throws SQLException,
			ClassNotFoundException {
		try {
			connectBD();
			return this.sql_execute(sql);
		} finally {
			closeConnection();
		}
	}

	/**
	 * @brief "SELECT FROM WHERE" of SQL
	 * @alert EVER CLOSE THE CONNECTION
	 * @param select
	 * @param from
	 * @param where
	 * @return result of search
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public ResultSet sf(String select, String from) throws SQLException,
			ClassNotFoundException {
		try {
			String s = "SELECT " + select + " FROM " + from;
			connectBD();
			ResultSet rs = this.sql_execute(s);
			return rs;
		} finally {
			closeConnection();
		}
	}

	/**
	 * @brief Execute an string SQL command
	 * @param string
	 * @return result of command
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public ResultSet sql_execute(String string) throws SQLException,
			ClassNotFoundException {
		ResultSet result = null;
		try {
			Statement st = DB.createStatement();
			result = st.executeQuery(string);
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	/**
	 * @brief Close DB connection
	 */
	public void closeConnection() {
		if (DB != null)
			try {
				DB.close();
			} catch (Exception e) {
			}
	}

	public static db_art getInstance() {
		if (Dbinstance == null) {
			Dbinstance = new db_art();
		}

		return Dbinstance;
	}

}
