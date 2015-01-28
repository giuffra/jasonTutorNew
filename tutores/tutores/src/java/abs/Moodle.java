package abs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import cartago.OpFeedbackParam;

public class Moodle {	
	private Iterator<Course> naCourses;
	private Iterator<User> naUsers;
	
	
	private List<User> lUser;
	private List<Course> lCourse;

	public Moodle() {
		lUser = new ArrayList<User>();
		lCourse = new ArrayList<Course>();
	}

	private void atualizaIteradores() {
		atualizaNaUsers();
		atualizaNaCourses();
	}

	public void addUser(int id) {
		if (!this.containsUser(id)) {
			lUser.add(new User(id));
			atualizaIteradores();
		}
	}

	public void addCourse(int id) {
		if (!this.containsCourse(id)) {
			lCourse.add(new Course(id));
			atualizaIteradores();
		}
	}

	public User getUser(int id) {
		int pos = lUser.indexOf(new User(id));
		return lUser.get(pos);
	}

	public Course getCourse(int id) {
		int pos = lCourse.indexOf(new Course(id));
		return lCourse.get(pos);
	}

	public boolean containsUser(int id) {
		return lUser.contains(id);
	}

	public boolean containsCourse(int id) {
		return lCourse.contains(id);
	}

	public int sizeUser() {
		return lUser.size();
	}

	public int sizeCourse() {
		return lCourse.size();
	}

	public Iterator<User> atualizaNaUsers() {
		ArrayList<User> temp = new ArrayList<User>();
		for (User u : lUser) {
			if (!u.agentePronto()) {
				temp.add(u);
			}
		}
		naUsers = lUser.iterator();
		return naUsers;
	}

	public Iterator<Course> atualizaNaCourses() {
		ArrayList<Course> temp = new ArrayList<Course>();
		for (Course c : lCourse) {
			if (!c.agentePronto()) {
				temp.add(c);
			}
		}
		naCourses = lCourse.iterator();
		return naCourses;
	}

	public User getNextNaUser(OpFeedbackParam<Boolean> bool) throws NoSuchElementException{
		bool.set(naUsers.hasNext());
			return naUsers.next();
	}

	public Course getNextNaCourse(OpFeedbackParam<Boolean> bool) throws NoSuchElementException{
		bool.set(naCourses.hasNext());
		return naCourses.next();
	}
}
