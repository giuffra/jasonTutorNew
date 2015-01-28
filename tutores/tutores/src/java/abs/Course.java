package abs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import artifact.db_art;

public class Course {
	private int id;
	private List<User> alunos;
	boolean agentePronto = false;
	db_art DB;
	
	public Course (int id) {
		this.id = id;
		DB = db_art.getInstance();
		alunos = new ArrayList<User>();
	}

	public int getId() {
		return id;
	}

	public void addAluno(User aluno) {
		if (!alunos.contains(aluno)) {
			alunos.add(aluno);
		}
	}

	public void remAluno(User aluno) {
		try {
			alunos.remove(aluno);
		} catch (IndexOutOfBoundsException e) {}
	}
	
	public String getCourseName(){
		ResultSet Rs;
		try {
			Rs = DB.sfw("fullname", "mods_course", "id=" + id);
			Rs.next();
			return Rs.getString(1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "ae";
	}
	
	public boolean equals(Object id) {
		if (id instanceof Course) {
			return this.id == ((Course)id).getId();
		}
		else if(id instanceof Integer){
			return this.id == (Integer)id;
		}
		return false;
	}
	
	public void setAgent(){
		agentePronto=true;
	}

	public boolean agentePronto() {
		return agentePronto;
	}

}
