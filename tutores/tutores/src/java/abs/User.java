package abs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import artifact.db_art;

public class User {
	private int id;
	private List<Course> discs;
	boolean agentePronto = false;
	db_art DB;

	public User(int id) {
		this.id = id;
		DB = db_art.getInstance();
		discs = new ArrayList<Course>();
	}

	public int getId() {
		return id;
	}

	public void addDisc(Course disc) {
		if (!discs.contains(disc)) {
			discs.add(disc);
		}
	}

	public String show_disc() {
		String ret = "Disciplinas do aluno:";
		Iterator<Course> i = discs.iterator();
		while (i.hasNext()) {
			ret += "\n" + i.next();
		}
		return ret;
	}

	public void setAgent() {
		agentePronto = true;
	}

	public void remDisc(Course disc) {
		try {
			discs.remove(disc);
		} catch (IndexOutOfBoundsException e) {
		}
	}

	public boolean equals(Object id) {
		if (id instanceof User) {
			return this.id == ((User)id).getId();
		}
		else if(id instanceof Integer){
			return this.id == (Integer)id;
		}
		return false;
	}

	public String getUserName() {
		ResultSet Rs1;
		try {
			Rs1 = DB.sfw("firstname", "mods_user", "id=" + id);
			Rs1.next();
			return Rs1.getString(1);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean agentePronto() {
		return agentePronto;
	}
}
