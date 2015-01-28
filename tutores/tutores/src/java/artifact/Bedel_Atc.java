// CArtAgO artifact code for project moodle

package artifact;

import java.sql.ResultSet;
import java.sql.SQLException;

import abs.Course;

import cartago.OPERATION;
import cartago.ObsProperty;

public class Bedel_Atc extends db_art {
	private Course course;
	void init(int initialValue) {
		defineObsProperty("count", initialValue);
		super.init(initialValue);
	}

	@OPERATION
	void inc() {
		ObsProperty prop = getObsProperty("count");
		prop.updateValue(prop.intValue() + 1);
		signal("tick");
	}

	int idCouse = 0;
	String courseName = " ";

	@OPERATION
	public void setIDcourse(int id) {
		idCouse = id;
		try {
			ResultSet rs = super.sfw("fullname", "mods_course", "id=" + id);
			rs.next();
			course = new Course(id);
			courseName = course.getCourseName();
		} catch (SQLException e) {
		} catch (ClassNotFoundException e) {
		}
	}

	/**
	 * @brief Is a operation, get all students in course
	 * @return A list of students with yours id
	 * @throws SQLException 
	 */
	@OPERATION
	public void get_User_List(int contextId, int roleid) throws SQLException {

//		ResultSet rest = get_users_id(this.idCouse, roleid, contextId);
		ResultSet rest = get_users_id(roleid, contextId);
		try {
			while (rest.next()) {
				int id = rest.getInt("id");
				String r = "id =" + id + "  nome=" + this.get_user_name(id);
				System.out.println(r);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		closeConnection();
	}
	
	private ResultSet get_users_id(int roleid, int contextid){
		ResultSet rest = null;
		try {
			rest = sfw("*","mods_role_assignments","roleid="+roleid+"AND contextid="+contextid);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rest;
	}

	/**
	 * 
	 * @param roleid
	 * @param contextid
	 * @return all users whith this roleid and this contextid
	 * @throws SQLException 
	 */
//	private ResultSet get_users_id(int course_id, int roleId, int contextId) {
//		ResultSet rest = null;
//		try {
//			rest = sql("select mods_user.id"
//					+ "from mods_course,  mods_context, mods_role_assignments, mods_user"
//					+ "where mods_context.id=mods_role_assignments.contextid"
//					+ "and mods_context.instanceid="+course_id
//					+ "and mods_role_assignments.userid = mods_user.id"
//					+ "and mods_role_assignments.roleid=" + roleId
//					+ "and mods_role_assignments.contextid=" + contextId
//					+ "order by mods_course.id;");
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			rest.next();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return rest;
//	}

	/**
	 * @brief Is a operation, get the teacher group id. 3 is a default value???
	 * @return integer
	 */
	@OPERATION
	public int get_teacher_group_id() {
		int i = 3;
		try {
			i = this.sfw("id", "mods_role", "shortname='editingteacher'")
					.getInt("id");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeConnection();
		return i;
	}

	/**
	 * @brief Is a operation, get the student group id. 5 is a default value???
	 * @return integer
	 */
	@OPERATION	
	public int get_student_group_id() {
		int i = 5;
		try {
			i = this.sfw("id", "mods_role", "shortname='student'").getInt("id");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeConnection();
		return i;
	}

	/**
	 * 
	 * @param id
	 * @return name of user
	 */
	@OPERATION
	public String get_user_name(int id) {
		String s = " ";
		try {
			ResultSet rest = this.sfw("username", "mods_user", "id=" + id);
			printar();
			rest.next();
			s = rest.getString(1);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeConnection();
		return s;
	}

	@OPERATION
	public void printar() {
		System.out.println("say");
	}

	@OPERATION
	public void show_Students() {
		int contextId = 30;
		try {
			this.get_User_List(this.get_student_group_id(), contextId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@OPERATION
	public void show_Teacher() {
		int contextId = 30;
		try {
			this.get_User_List(this.get_teacher_group_id(),contextId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@OPERATION
	public void show_Me() {
		System.out.println("Nome do curso: " + courseName
				+ "\t\t ID do curso: " + idCouse);
	}

}
