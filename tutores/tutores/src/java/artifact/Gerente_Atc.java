// CArtAgO artifact code for project moodle

package artifact;

import java.sql.ResultSet;
import java.sql.SQLException;

import abs.Course;
import abs.Moodle;
import abs.User;
import cartago.OPERATION;
import cartago.ObsProperty;
import cartago.OpFeedbackParam;

public class Gerente_Atc extends db_art {

	Moodle moodle = new Moodle();

	void init(int initialValue) {
		defineObsProperty("count", initialValue);
		super.init(initialValue);
		this.iniciaCursos();
		this.iniciaUser();
	}

	private void iniciaUser() {
		try {

			ResultSet users = super.sf("id", "mods_user");
			while (users.next())
				moodle.addUser(users.getInt(1));

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void iniciaCursos() {
		try {
			ResultSet cursos = super
					.sfw("id", "mods_course", "summaryformat=1");

			while (cursos.next())
				moodle.addCourse(cursos.getInt(1));
		} catch (SQLException e) {
		} catch (ClassNotFoundException e) {
		}
	}

	@OPERATION
	public void inicia() {
		iniciaCursos();
		iniciaUser();
		System.out.println("Artefato iniciado...");
	}

	@OPERATION
	void inc() {
		ObsProperty prop = getObsProperty("count");
		prop.updateValue(prop.intValue() + 1);
		signal("tick");
	}

	@OPERATION
	public void getNUsers(OpFeedbackParam<Integer> i) {
		ResultSet rest = null;
		try {
			rest = sfw("id", "mods_user", "confirmed=1");
			for (int n = 0; rest.next(); n++)
				i.set(n);

		} catch (ClassNotFoundException e) {
		} catch (SQLException e) {
		}
	}

	@OPERATION
	public void getNCursos(OpFeedbackParam<Integer> I) {
		ResultSet Rs;
		int retur = 0;
		try {
			Rs = super.sfw("id", "mods_course", "category=2");
			while (Rs.next())
				retur++;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		I.set(retur);
	}

	@OPERATION
	public void verificaNovos() {
		try {
			ResultSet users = super.sf("id", "mods_user");
			ResultSet cursos = super.sf("id", "mods_course");
			while (users.next()) {
				moodle.addUser(users.getInt(1));
				System.out.println("users " + moodle.sizeUser());
			}
			while (cursos.next()) {
				moodle.addCourse(cursos.getInt(1));
				//TODO retirar pfln
				System.out.println("disc " + moodle.sizeCourse());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	@OPERATION
	public void getNaIdUser(OpFeedbackParam<Integer> id, OpFeedbackParam<String> nome,OpFeedbackParam<Boolean> bool){
		try{
			User u = moodle.getNextNaUser(bool);
			u.setAgent();
			id.set(u.getId());
			nome.set(u.getUserName());
		}catch (Exception e) {
			bool.set(false);
		}
	}
	@OPERATION
	public void getNaIdCourse(OpFeedbackParam<Integer> id, OpFeedbackParam<String> nome, OpFeedbackParam<Boolean> bool){
		try{
			Course c = moodle.getNextNaCourse(bool);
			c.setAgent();
			id.set(c.getId());
			nome.set(c.getCourseName());
		}catch (Exception e) {
			bool.set(false);
		}
	}

	// @OPERATION
	// public void getIdLastCourse(OpFeedbackParam<Integer> I) {
	// ResultSet Rs;
	// try {
	// Rs = super.sfw("id", "mods_course", "summaryformat=1");
	// if (Rs.next())
	// I.set(Rs.getInt(1));
	// } catch (ClassNotFoundException e) {
	// e.printStackTrace();
	//
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	//
	// }

	// @OPERATION
	// public void getIdLastUser(OpFeedbackParam<Integer> I) {
	// ResultSet Rs;
	// try {
	// Rs = super.sf("id", "mods_user");
	// if (Rs.next())
	// I.set(Rs.getInt(1));
	// } catch (ClassNotFoundException e) {
	// e.printStackTrace();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	//
	// }

	// @OPERATION
	// public void getCourseName(int id, OpFeedbackParam<String> Name) {
	// ResultSet Rs;
	// try {
	// Rs = super.sfw("fullname", "mods_course", "id=" + id);
	// Rs.next();
	// Name.set(Rs.getString(1));
	// } catch (ClassNotFoundException e) {
	// e.printStackTrace();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }

}
