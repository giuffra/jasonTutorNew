// CArtAgO artifact code for project moodle

package artifact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import abs.Course;
import abs.User;
import cartago.OPERATION;
import cartago.ObsProperty;
import cartago.OpFeedbackParam;

public class Bedel_Atc extends db_art {
	private Course course;

	@OPERATION
	void inc() {
		ObsProperty prop = getObsProperty("count");
		prop.updateValue(prop.intValue() + 1);
		signal("tick");
	}

	int idCourse = 0;
	String courseName = " ";
	String contextid;
	ArrayList<String> id_grade_item_Tarefas_entregues;
	ArrayList<String> lista_tarefas_avaliadas;
	private boolean possuiNovaTarefa = false;

	@OPERATION
	public void setIDcourse(int id) {
		idCourse = id;
		try {

			ResultSet rs = super.sfw("fullname", "mdl_course", "id=" + id);
			rs.next();
			course = new Course(id);
			courseName = course.getCourseName();
			rs = super.sfw("*", "mdl_context",
					"contextlevel = '50' and instanceid =" + idCourse);
			rs.next();
			contextid = rs.getString("id");
		} catch (SQLException e) {
		} catch (ClassNotFoundException e) {
		}
	}

	@OPERATION
	public void possui_nova_tarefa(OpFeedbackParam<Boolean> p){
		p.set(possuiNovaTarefa);
	}
	@OPERATION
	public void limpa_nova_tarefa(){
		possuiNovaTarefa=false;
	}
	@OPERATION
	public ArrayList<String> lista_id_alunos_curso() throws SQLException,
			ClassNotFoundException {
		ArrayList<String> lista_alunos_curso = new ArrayList<String>();

		ResultSet rs = super.sfw("id_aluno", "mdl_tutor_tutor_aluno",
				"id_curso =" + this.idCourse);

		while (rs.next()) {
			String id = rs.getString("id_aluno");
			lista_alunos_curso.add(id);
		}

		return lista_alunos_curso;
	}

	@OPERATION
	public ArrayList<String> lista_alunos_curso() throws SQLException,
			ClassNotFoundException {
		ArrayList<String> lista_alunos_curso = new ArrayList<String>();

		ResultSet rs = super.sfw("id_aluno", "mdl_tutor_tutor_aluno",
				"id_curso =" + this.idCourse);

		while (rs.next()) {
			int id = rs.getInt("id_aluno");
			String nome = (new User(id).getUserName());
			lista_alunos_curso.add("Nome :" + nome + "\t ID: " + id);
		}

		return lista_alunos_curso;
	}

	@OPERATION
	public void printar(String msg, int X) {
		for (; X > 0; X--)
			System.out.println(msg);
	}

	@OPERATION
	public void show_Students(OpFeedbackParam<String> students) {
		String stu = "";
		try {
			try {
				stu += "Alunos do curso: " + courseName + "\n";
				for (String s : this.lista_id_alunos_curso()) {
					stu += s + "\n";
				}
				printar("", 5);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		students.set(stu);
	}

	@OPERATION
	public void show_Teacher(OpFeedbackParam<String> teachers) {
		ArrayList<String> professores = new ArrayList<String>();
		try {
			String string;
			String id = "";
			string = "SELECT id FROM mdl_role WHERE shortname='editingteacher'";
			// devolve o id do perfil professor
			ResultSet id_role_editingteacher = this.select(string);

			while (id_role_editingteacher.next()) {
				id = id_role_editingteacher.getString("id");
			}
			string = "SELECT * FROM mdl_role_assignments WHERE roleid = " + id;
			// devolve todos os professores
			ResultSet role_assignment_all_role_3 = this.select(string);

			string = "SELECT * FROM mdl_context WHERE contextlevel = '50' and instanceid ="
					+ idCourse;
			// devolve dados da tabela context cujo contextlevel � 50 (contexto
			// de
			// curso)
			ResultSet context = this.select(string);

			while (role_assignment_all_role_3.next()) { // para todos os
														// professores
				contextid = role_assignment_all_role_3.getString("contextid");
				while (context.next()) { // para todos os contextos
					String idcontext = context.getString("id");
					if (contextid.equals(idcontext)) { // se o contexto do
														// professor
														// � igual ao contexto
						int id_professor_curso = role_assignment_all_role_3
								.getInt("userid");
						professores.add("Nome: "
								+ (new User(id_professor_curso)).getUserName()
								+ "\t ID: " + id_professor_curso);
					}
				}
			}
		} catch (Exception e) {
		}
		String tea = "Professores do curso: " + courseName + "\n";

		for (String s : professores)
			tea += s + "\n";

		teachers.set(tea);
	}

	@OPERATION
	public void show_Me(OpFeedbackParam<String> string) {
		string.set("Nome do curso: " + courseName + "\t\t ID do curso: "
				+ idCourse);
	}

	// ///////////////////////////////

	@OPERATION
	void verifica_login_professor() throws SQLException, ClassNotFoundException {
		this.conexaoBD();
		String string;
		String id = "";
		string = "SELECT id FROM mdl_role WHERE shortname='editingteacher'";
		// devolve o id do perfil professor
		ResultSet id_role_editingteacher = this.select(string);

		while (id_role_editingteacher.next()) {
			id = id_role_editingteacher.getString("id");
		}
		string = "SELECT * FROM mdl_role_assignments WHERE roleid = " + id;
		// devolve todos os professores
		ResultSet role_assignment_all_role_3 = this.select(string);

		string = "SELECT * FROM mdl_context WHERE contextlevel = '50' and instanceid ="
				+ idCourse;
		// devolve dados da tabela context cujo contextlevel � 50 (contexto de
		// curso)
		ResultSet context = this.select(string);

		while (role_assignment_all_role_3.next()) { // para todos os professores
			contextid = role_assignment_all_role_3.getString("contextid");
			while (context.next()) { // para todos os contextos
				String idcontext = context.getString("id");
				if (contextid.equals(idcontext)) { // se o contexto do professor
													// � igual ao contexto
					String id_professor_curso = role_assignment_all_role_3
							.getString("userid");
					string = "SELECT * FROM mdl_log WHERE userid = "
							+ id_professor_curso;
					ResultSet logprof = this.select(string);
					if (logprof.next()) {
						// se o professor tiver feito login uma vez entra
						// System.out.println("true");
						// signal("tick");
					}
				}
			}

		}
		this.fecharConexao();
	}

	@OPERATION
	boolean verifica_olho_fechado() throws SQLException, ClassNotFoundException {
		this.conexaoBD();
		// verifica olho fechado dos recursos e atividades, se for inicial deixa
		// aberto, se n�o for fecha e avisa ao professor.
		boolean atualizou_olho_fechado = false;
		int update_course_modules = 0;
		int update_iniciais = 0;
		int update_modinfo = 0;
		String string;
		string = "SELECT rec_ativ_id FROM mdl_tutor_dependencia WHERE curso_id="
				+ idCourse + " AND pre_req_id = '0'";
		ResultSet pre_req_0 = this.select(string); // 1a atividade e 1o recurso
													// do curso (disciplina)

		ArrayList<String> iniciais = new ArrayList<String>();
		while (pre_req_0.next()) {
			iniciais.add(pre_req_0.getString("rec_ativ_id"));
			// lista com 1a atividade e 1 recurso
			string = "UPDATE mdl_course_modules SET visible = '1' WHERE id="
					+ pre_req_0.getString("rec_ativ_id");
			update_iniciais = this.update(string);

			// System.out.println("iniciais " + update_iniciais);
		}

		string = "SELECT * FROM mdl_course_modules WHERE course = " + idCourse
				+ " AND visible = '1' AND id !=" + iniciais.get(0)
				+ " AND id !=" + iniciais.get(1);
		ResultSet course_modules = this.select(string);

		ArrayList<String> course_modules_list = new ArrayList<String>();
		while (course_modules.next()) {
			course_modules_list.add(course_modules.getString("id"));
			// lista de course_modules com olho aberto
			string = "UPDATE mdl_course_modules SET visible = '0' WHERE id="
					+ course_modules.getString("id");
			update_course_modules = this.update(string);

			// System.out.println("course " + update_course_modules);

		}
		//
		if (update_course_modules == 1 && update_iniciais == 1) {
			//
			string = "UPDATE mdl_course SET modinfo = '' WHERE id=" + idCourse;
			update_modinfo = this.update(string);

			//
			// System.out.println("modinfo" + update_modinfo);
		}

		if (update_modinfo == 1) {
			atualizou_olho_fechado = true;
		}
		this.fecharConexao();
		return atualizou_olho_fechado;
		// volta true se modificou visibilidade.
	}

	/*
	 * @OPERATION void verifica_hoje(OpFeedbackParam<Boolean> hj) throws
	 * SQLException, ClassNotFoundException {
	 * System.out.println("entrei no verifica_hoje"); hj.set(false); }
	 */

	@OPERATION
	void verifica_data_final_tarefa() throws SQLException,
			ClassNotFoundException {
		// verifica se tem tarefas que passaram a data de entrega
		this.conexaoBD();
		// System.out.println("entrei no verifica_data_tarefa");
		String string;
		id_grade_item_Tarefas_entregues = new ArrayList<String>();

		// para servir com foruns avaliados, mas tem que verificar tipo de
		// modulo para n�o confundir com iteminstance
		// tem que mudar o converte_item_instace_em_id_grade_item adicionando o
		// itemmodule
		/*
		 * string = "SELECT * FROM mdl_forum WHERE course=" + idCourse;
		 * ResultSet forum = this.select(string); while (forum.next()) { long
		 * timestamp = forum.getLong("assesstimefinish"); timestamp = timestamp
		 * + 10800; // hora de assignment + 3 horas GMT em timestamp long hoje =
		 * System.currentTimeMillis() / 1000; System.out.println(timestamp);
		 * System.out.println(hoje); if (timestamp <= hoje) {
		 * id_grade_item_Tarefas_entregues
		 * .add(this.converte_item_instance_em_id_grade_item
		 * (forum.getString("id"))); //mudar para grade_item
		 * /***************************
		 */
		// avisa para o professor que j� deve avaliar, pois passou da data
		// limite de entrega
		/*
		 * } }
		 */
		string = "SELECT * FROM mdl_assign WHERE course=" + idCourse;
		ResultSet assignment = this.select(string);
		string = "SELECT * FROM mdl_quiz WHERE course=" + idCourse;
		ResultSet quiz = this.select(string);
		while (assignment.next()) {
			long timestamp = assignment.getLong("duedate");
			timestamp = timestamp + 10800; // hora de assignment + 3 horas GMT
											// em timestamp
			long hoje = System.currentTimeMillis() / 1000;
			if (timestamp <= hoje) {
				id_grade_item_Tarefas_entregues.add(this
						.converte_item_instance_em_id_grade_item(assignment
								.getString("id"))); // mudar para grade_item
													// /****************************/
				// avisa para o professor que j� deve avaliar, pois passou da
				// data limite de entrega
				// Stem.out.println("tarefas entregues "+this.converte_item_instance_em_id_grade_item(assignment.getString("id")));
			}
			while (quiz.next()) {
				if (quiz.getInt("timeclose") > 0) {
					id_grade_item_Tarefas_entregues.add(this
							.converte_item_instance_em_id_grade_item(quiz.getString("id")));	
				}
			}
		}
		this.fecharConexao();
	}

	@OPERATION
	String converte_item_instance_em_id_grade_item(String itemInstance)
			throws SQLException, ClassNotFoundException {
		this.conexaoBD();
		String string;
		string = "SELECT * FROM mdl_grade_items WHERE courseid=" + idCourse
				+ " AND iteminstance=" + itemInstance;
		ResultSet tabela_grade_items = this.select(string);
		String id_grade_items = "";
		while (tabela_grade_items.next()) {
			id_grade_items = tabela_grade_items.getString("id");
		}
		return id_grade_items;
	}

	@OPERATION
	String converte_id_grade_item_em_id_module(String gradeItem)
			throws SQLException, ClassNotFoundException {
		ArrayList<Associacao> lista_modules = this.lista_modules();
		this.conexaoBD();
		String string;
		string = "SELECT * FROM mdl_grade_items WHERE courseid=" + idCourse
				+ " AND id=" + gradeItem;
		ResultSet tabela_item_instance = this.select(string);
		String id_module = "";
		String item_instance = "";
		String item_module = "";
		String id_item_module = "";
		while (tabela_item_instance.next()) {
			item_instance = tabela_item_instance.getString("iteminstance");
			item_module = tabela_item_instance.getString("itemmodule");
		}
		for (int i = 0; i < lista_modules.size(); i++) {
			if (lista_modules.get(i).getItemmodule().equals(item_module)) {
				id_item_module = lista_modules.get(i).getId();
			}
		}
		string = "SELECT id FROM mdl_course_modules WHERE course=" + idCourse
				+ " AND module=" + id_item_module + " AND instance="
				+ item_instance;
		ResultSet id_course_modules = this.select(string);
		while (id_course_modules.next()) {
			id_module = id_course_modules.getString("id");
		}
		return id_module;
	}
	
	double arredondar(double valor, int casas) {  
	    double arredondado = valor;  
	    arredondado *= (Math.pow(10, casas));    
	        arredondado = Math.floor(arredondado);  
	    arredondado /= (Math.pow(10, casas));  
	    return arredondado;  
	}  

	@OPERATION
	boolean verifica_avaliacao() throws SQLException, ClassNotFoundException {
		// verifica se todos os alunos foram avaliados nas tarefas, atualiza
		// mdl_tutor_tarefas_avaliadas com avaliada = 1 na tarefa se todos os
		// alunos foram avaliados nela.
		String string;
		int count_itens_nota = 0;
		int count_itens_tarefas_avaliadas_curso = 0;
		boolean avisar = false;
		ArrayList<Associacao> id_modules = lista_modules();
		this.conexaoBD();
		string = "SELECT * FROM mdl_grade_items WHERE courseid=" + idCourse
				+ " AND categoryid != 'NULL'";
		// seleciona da lista de itens com avaliacao aqueles do curso que est�o
		// ativos (n�o null)
		ResultSet itens_nota = this.select(string);

		ArrayList<Associacao> id_itens_nota = new ArrayList<Associacao>();
		while (itens_nota.next()) {
			Associacao ass = new Associacao();
			ass.setId(itens_nota.getString("id"));
			String nome_modulo = itens_nota.getString("itemmodule");
			for (int i = 0; i < id_modules.size(); i++) {
				if (id_modules.get(i).getItemmodule().equals(nome_modulo)) {
					ass.setItemmodule(id_modules.get(i).getId());
				}
			}
			ass.setInstance(itens_nota.getString("iteminstance"));
			id_itens_nota.add(ass);
			// lista com id dos itens de nota (grade_items) e id dos m�dulos
			// (assigment, resource, etc) dos itens de nota do curso.
			count_itens_nota++;
			// quantidade de itens de nota do curso.
		}

		string = "SELECT * FROM mdl_tutor_tarefas_avaliadas WHERE curso_id="
				+ idCourse;
		ResultSet itens_tarefas_avaliadas_curso = this.select(string);

		ArrayList<String> id_grade_items = new ArrayList<String>();
		while (itens_tarefas_avaliadas_curso.next()) {
			count_itens_tarefas_avaliadas_curso++;
			id_grade_items.add(itens_tarefas_avaliadas_curso
					.getString("id_grade_items"));
			// quantidade de tarefas que j� foram avaliadas no curso
		}
		if (count_itens_tarefas_avaliadas_curso < count_itens_nota) {

			for (int i = 0; i < id_itens_nota.size(); i++) {
				if (!id_grade_items.contains(id_itens_nota.get(i).getId())) {
					// verifica se a tabela tarefas_avaliadas est� com todos os
					// itens de avalia��o do curso nela, sen�o, insere os que
					// faltam.
					string = "INSERT INTO mdl_tutor_tarefas_avaliadas (curso_id, id_grade_items, avaliada) "
							+ "VALUES ("
							+ idCourse
							+ ", "
							+ id_itens_nota.get(i).getId() + ", '0')";
					this.update(string);
				}
			}
		}
		string = "SELECT id_grade_items FROM mdl_tutor_tarefas_avaliadas WHERE avaliada = '0' AND curso_id="
				+ idCourse;
		// devolve o id dos itens de avalia��o que n�o tiveram a avalia��o
		// completada para todos os alunos.
		ResultSet tarefas_nao_avaliadas = this.select(string);

		ArrayList<String> lista_tarefas_nao_avaliadas = new ArrayList<String>();
		while (tarefas_nao_avaliadas.next()) {
			lista_tarefas_nao_avaliadas.add(tarefas_nao_avaliadas
					.getString("id_grade_items"));
		}
		this.fecharConexao();
		this.conexaoBD();
		// chama fun��o lista_alunos para pegar todos os alunos do curso
		lista_tarefas_avaliadas = new ArrayList<String>();

		for (int k = 0; k < lista_tarefas_nao_avaliadas.size(); k++) {
			if (id_grade_item_Tarefas_entregues
					.contains(lista_tarefas_nao_avaliadas.get(k))) {

				string = "SELECT userid FROM mdl_grade_grades WHERE itemid="
						+ lista_tarefas_nao_avaliadas.get(k)
						+ " AND rawgrade != 'NULL'";
				ResultSet alunos_avaliados = this.select(string);

				ArrayList<String> lista_alunos_avaliados = new ArrayList<String>();
				while (alunos_avaliados.next()) {
					lista_alunos_avaliados.add(alunos_avaliados
							.getString("userid"));
				}
				// atualiza tabela tutor_tarefas_atualizadas, marcando os
				// itens de avalia��o que est�o com todos alunos avaliados.
				string = "UPDATE mdl_tutor_tarefas_avaliadas SET avaliada = '1' WHERE id_grade_items="
						+ lista_tarefas_nao_avaliadas.get(k);
				this.update(string);
				lista_tarefas_avaliadas.add(lista_tarefas_nao_avaliadas.get(k));
			}
		}
		this.fecharConexao();
		return avisar;
	}

	public ArrayList<Associacao> lista_modules() throws SQLException,
			ClassNotFoundException {
		this.conexaoBD();
		String string;
		//
		string = "SELECT * FROM mdl_modules";
		// seleciona a tabela modules
		ResultSet modules = this.select(string);

		ArrayList<Associacao> id_modules = new ArrayList<Associacao>();
		while (modules.next()) {
			Associacao ass_modules = new Associacao();
			ass_modules.setId(modules.getString("id")); // id = id dos m�dulos
			ass_modules.setItemmodule(modules.getString("name")); // itemmodule
																	// = nome
																	// dos
																	// m�dulos
			id_modules.add(ass_modules);
			// lista com id e nome de modules
		}
		this.fecharConexao();
		return id_modules;
	}

	@OPERATION
	ArrayList<Associacao_Aluno_Logs> verifica_log_pre_req(String idTarefa)
			throws SQLException, ClassNotFoundException {
		this.conexaoBD();
		// Devolve um objeto associacao_aluno_logs, com id do aluno e a quant de
		// logs nos recursos.
		String string;
		// idTarefa = "4";
		//
		string = "SELECT * FROM mdl_tutor_dependencia WHERE curso_id="
				+ idCourse + " AND rec_ativ_id=" + idTarefa;
		ResultSet tutor_dependencia = this.select(string);

		ArrayList<Associacao_dependencia> lista_rec_ativ_id = new ArrayList<Associacao_dependencia>();

		while (tutor_dependencia.next()) {
			Associacao_dependencia ass_dep = new Associacao_dependencia();
			ass_dep.setRec_ativ_id(tutor_dependencia.getString("rec_ativ_id"));
			ass_dep.inserePreReq(tutor_dependencia.getString("pre_req_id"));
			boolean igual = false;
			for (int i = 0; i < lista_rec_ativ_id.size(); i++) {
				if (lista_rec_ativ_id.get(i).getRec_ativ_id()
						.equals(tutor_dependencia.getString("rec_ativ_id"))) {
					igual = true;
					ArrayList<String> listaDependencia = new ArrayList<String>();
					listaDependencia = lista_rec_ativ_id.get(i).getPre_req_id();
					listaDependencia.add(tutor_dependencia
							.getString("pre_req_id"));
					// Adiciona pre_req da mesma rec_ativ na lista.
				}
			}
			if (!igual) {
				lista_rec_ativ_id.add(ass_dep);
				// Adiciona � lista um objeto associacao_dependencia, com id da
				// rec_ativ e a lista de pre_req dela.
			}
		}
		ArrayList<String> recursos_pre = new ArrayList<String>();
		for (int j = 0; j < lista_rec_ativ_id.size(); j++) {
			for (int k = 0; k < lista_rec_ativ_id.get(j).getPre_req_id().size(); k++) {
				string = "SELECT id FROM mdl_course_modules WHERE id="
						+ lista_rec_ativ_id.get(j).getPre_req_id().get(k)
						+ " AND (module=15 OR module=13)";
				ResultSet recursos = this.select(string);

				if (recursos.next()) {
					recursos_pre.add(recursos.getString("id"));
				}
			}
		}
		this.fecharConexao();
		ArrayList<String> course_students = this.lista_id_alunos_curso();
		this.conexaoBD();
		ArrayList<Associacao_Aluno_Logs> alunos_cont_log = new ArrayList<Associacao_Aluno_Logs>();
		for (int l = 0; l < course_students.size(); l++) {
			for (int m = 0; m < recursos_pre.size(); m++) {
				int count_log = 0;
				string = "SELECT * FROM mdl_log WHERE userid = "
						+ course_students.get(l) + " AND cmid = "
						+ recursos_pre.get(m);
				ResultSet log_alunos = this.select(string);

				while (log_alunos.next()) {
					count_log++;
					Associacao_Aluno_Logs ass_al_log = new Associacao_Aluno_Logs();
					ass_al_log.setId_aluno(course_students.get(l));
					ass_al_log.setCount_logs(count_log);
					boolean igual = false;
					int quant_log;
					for (int o = 0; o < alunos_cont_log.size(); o++) {
						if (alunos_cont_log.get(o).getId_aluno()
								.equals(ass_al_log.getId_aluno())
								&& alunos_cont_log.get(o).getCount_logs() > 0) {
							igual = true;
							quant_log = alunos_cont_log.get(o).getCount_logs();
							quant_log++; // incrementa logs em recursos que s�o
											// pre_req das tarefas
							alunos_cont_log.get(o).setCount_logs(quant_log);
						}
					}
					if (!igual) {
						alunos_cont_log.add(ass_al_log);
						// Adiciona � lista um objeto associacao_aluno_logs, com
						// id do aluno e a quant de logs nos recursos.
					}
				}
			}
		}
		this.fecharConexao();
		return alunos_cont_log;
	}

	@OPERATION
	void calcula_notas_perfil_alunos() throws SQLException,
			ClassNotFoundException {
		// verifica log_pre_req da tarefa no argumento, verifica se a tarefa foi
		// avaliada, caso sim, pega lista dos alunos e cria lista de notas
		// perfil deles com a nota da tarefa e os pontos de leitura (log)
		String string;
		String idTarefa;
		for (int tar = 0; tar < lista_tarefas_avaliadas.size(); tar++) {
			idTarefa = this
					.converte_id_grade_item_em_id_module(lista_tarefas_avaliadas
							.get(tar));
			ArrayList<Associacao_Aluno_Logs> lista_alunos_cont_log = this
					.verifica_log_pre_req(idTarefa); // lista com id do aluno e
														// quant de logs nos
														// pre_req
			ArrayList<String> lista_alunos = this.lista_id_alunos_curso();
			ArrayList<Associacao> id_modules = lista_modules();
			this.conexaoBD();
			String item_module = "";
			Associacao ass_module_grade_item = new Associacao();
			//
			string = "SELECT * FROM mdl_course_modules WHERE id=" + idTarefa;
			ResultSet course_module_idTarefa = this.select(string);

			if (course_module_idTarefa.next()) {
				ass_module_grade_item.setId(course_module_idTarefa
						.getString("id"));
				ass_module_grade_item.setInstance(course_module_idTarefa
						.getString("instance"));
				ass_module_grade_item.setItemmodule(course_module_idTarefa
						.getString("module"));
				for (int i = 0; i < id_modules.size(); i++) {
					if (id_modules.get(i).getId()
							.equals(ass_module_grade_item.getItemmodule())) {
						item_module = id_modules.get(i).getItemmodule();
					}
				}
			}
			String id_grade_item_idTarefa = "";
			string = "SELECT id FROM mdl_grade_items WHERE itemmodule='"
					+ item_module + "' AND iteminstance="
					+ ass_module_grade_item.getInstance();
			ResultSet id_grade_item = this.select(string);

			if (id_grade_item.next()) {
				id_grade_item_idTarefa = id_grade_item.getString("id");
			}

			string = "SELECT avaliada FROM mdl_tutor_tarefas_avaliadas WHERE id_grade_items="
					+ id_grade_item_idTarefa;
			ResultSet avaliada = this.select(string);

			boolean tarefa_avaliada = false;
			if (avaliada.next()) {
				if (avaliada.getString("avaliada").equals("1")) {
					tarefa_avaliada = true;
				}
			}
			//
			if (tarefa_avaliada) {
				//

				string = "SELECT * FROM mdl_grade_grades WHERE itemid="
						+ id_grade_item_idTarefa;
				ResultSet notas_alunos_tarefas = this.select(string);

				ArrayList<Associacao_notas_alunos_tarefa> lista_notas_alunos_tarefa = new ArrayList<Associacao_notas_alunos_tarefa>();
				while (notas_alunos_tarefas.next()) {
					Associacao_notas_alunos_tarefa ass_nota_tarefa = new Associacao_notas_alunos_tarefa();
					ass_nota_tarefa.setId_aluno(notas_alunos_tarefas
							.getString("userid"));
					ass_nota_tarefa.setNota(Double
							.parseDouble(notas_alunos_tarefas
									.getString("finalgrade")));
					lista_notas_alunos_tarefa.add(ass_nota_tarefa);
				}
				double notaComLog = 2;
				double notaSemLog = 4;
				boolean temlog = false;
				ArrayList<Associacao_notas_alunos_tarefa> lista_alunos_nota_perfil = new ArrayList<Associacao_notas_alunos_tarefa>();
				for (int i = 0; i < lista_alunos.size(); i++) {
					Associacao_notas_alunos_tarefa ass_nota_perfil = new Associacao_notas_alunos_tarefa();
					ass_nota_perfil.setId_aluno(lista_alunos.get(i));
					if (lista_alunos_cont_log.size() > 0) {
						for (int j = 0; j < lista_alunos_cont_log.size(); j++) {
							if (lista_alunos.get(i).equals(
									lista_alunos_cont_log.get(j).getId_aluno())) {
								temlog = true;
								if (lista_alunos_cont_log.get(j)
										.getCount_logs() > 0) {
									ass_nota_perfil.setNota(notaComLog);
								} else {
									ass_nota_perfil.setNota(notaSemLog);
								}
							}
							if (!temlog) {
								ass_nota_perfil.setNota(notaSemLog);
							}
						}
					} else {
						ass_nota_perfil.setNota(notaSemLog);
					}
					for (int j = 0; j < lista_notas_alunos_tarefa.size(); j++) {
						if (lista_alunos.get(i).equals(
								lista_notas_alunos_tarefa.get(j).getId_aluno())) {
							ass_nota_perfil.setNota(ass_nota_perfil.getNota()
									+ lista_notas_alunos_tarefa.get(j)
											.getNota());
						}
					}
					lista_alunos_nota_perfil.add(ass_nota_perfil);
				}
				int nro_calculo = 1;
				string = "SELECT MAX(nro_calculo) as max_calculo FROM mdl_tutor_nota_perfil WHERE curso_id = "
						+ idCourse;
				ResultSet max_nro_calculo = this.select(string);

				boolean tem_mais = false;
				int ultimo_nro_calculo = 0;
				if (max_nro_calculo.next()) {
					if (max_nro_calculo.getString("max_calculo") != null) {
						ultimo_nro_calculo = Integer.parseInt(max_nro_calculo
								.getString("max_calculo"));
						if (ultimo_nro_calculo > 0) {
							tem_mais = true;
						}
						nro_calculo = ultimo_nro_calculo + 1;
					}
				}

				string = "SELECT id_grade_item FROM mdl_tutor_nota_perfil WHERE id_grade_item="
						+ id_grade_item_idTarefa;
				ResultSet id_grade_item_existe = this.select(string);

				if (!id_grade_item_existe.next()) {
					for (int i = 0; i < lista_alunos_nota_perfil.size(); i++) {
						if (tem_mais) {
							string = "SELECT nota FROM mdl_tutor_nota_perfil WHERE aluno_id="
									+ lista_alunos_nota_perfil.get(i)
											.getId_aluno()
									+ " AND nro_calculo="
									+ ultimo_nro_calculo
									+ " AND curso_id = " + idCourse;
							ResultSet ultima_nota = this.select(string);

							if (ultima_nota.next()) {
								double soma_notas = ultima_nota
										.getDouble("nota") * ultimo_nro_calculo;
								double nota_perfil_aluno = (soma_notas + lista_alunos_nota_perfil
										.get(i).getNota()) / nro_calculo;
								lista_alunos_nota_perfil.get(i).setNota(
										nota_perfil_aluno);
							}
						}
						string = "INSERT INTO mdl_tutor_nota_perfil (curso_id, id_grade_item, aluno_id, nota, nro_calculo) "
								+ "VALUES ("
								+ idCourse
								+ ", "
								+ id_grade_item_idTarefa
								+ ","
								+ lista_alunos_nota_perfil.get(i).getId_aluno()
								+ ", "
								+ arredondar(lista_alunos_nota_perfil.get(i).getNota(),2)
								+ ", " + nro_calculo + " )";
						this.possuiNovaTarefa  = true;
						this.update(string);

					}
				}
				//
			} else {
				possuiNovaTarefa = false;
			}
		}
		this.fecharConexao();
	}

	@OPERATION
	double calcula_media_perfil() throws SQLException, ClassNotFoundException {
		this.conexaoBD();
		String string;
		//
		string = "SELECT MAX(nro_calculo) as max_calculo FROM mdl_tutor_nota_perfil WHERE curso_id = "
				+ idCourse;
		ResultSet max_nro_calculo = this.select(string);

		int ultimo_nro_calculo = 0;
		if (max_nro_calculo.next()) {
			if (max_nro_calculo.getString("max_calculo") != null) {
				ultimo_nro_calculo = Integer.parseInt(max_nro_calculo
						.getString("max_calculo"));
			}
		}

		string = "SELECT * FROM mdl_tutor_nota_perfil WHERE curso_id = "
				+ idCourse + " and nro_calculo=" + ultimo_nro_calculo;
		ResultSet notas_alunos = this.select(string);

		double soma_todas_notas = 0;
		while (notas_alunos.next()) {
			soma_todas_notas = soma_todas_notas
					+ notas_alunos.getDouble("nota");
		}
		this.fecharConexao();
		ArrayList<String> lista_alunos = this.lista_id_alunos_curso();
		double media_perfil = soma_todas_notas / (lista_alunos.size());
		return media_perfil;
	}

	@OPERATION
	void cacula_valores_perfis() throws SQLException, ClassNotFoundException {

		double media = arredondar(this.calcula_media_perfil(),2);
		this.conexaoBD();
		double media_basico = 0;
		double media_avancado = 0;

		if (media < 60) {
			media = 60;
		}
		if (media > 80) {
			media = 80;
		}
		media_basico = media - 6;
		media_avancado = media + 6;

		int nro_calculo_medias = 1;
		//
		String string = "SELECT MAX(nro_calculo) as max_calculo FROM mdl_tutor_media_perfis WHERE  curso_id ="
				+ this.idCourse;
		ResultSet max_nro_calculo_medias = this.select(string);

		int ultimo_nro_calculo_medias = 0;
		if (max_nro_calculo_medias.next()) {
			if (max_nro_calculo_medias.getString("max_calculo") != null) {
				ultimo_nro_calculo_medias = Integer
						.parseInt(max_nro_calculo_medias
								.getString("max_calculo"));
				nro_calculo_medias = ultimo_nro_calculo_medias + 1;
			}
		}
		String medio = "2";
		String basico = "1";
		String avancado = "3";
		string = "INSERT INTO mdl_tutor_media_perfis (curso_id, perfil_id, nota, nro_calculo)"
				+ "VALUES ("
				+ idCourse
				+ ", "
				+ medio
				+ ","
				+ media
				+ ", "
				+ nro_calculo_medias + " )";
		this.update(string);

		string = "INSERT INTO mdl_tutor_media_perfis (curso_id, perfil_id, nota, nro_calculo)"
				+ "VALUES ("
				+ idCourse
				+ ", "
				+ basico
				+ ","
				+ media_basico
				+ ", " + nro_calculo_medias + " )";
		this.update(string);

		string = "INSERT INTO mdl_tutor_media_perfis (curso_id, perfil_id, nota, nro_calculo)"
				+ "VALUES ("
				+ idCourse
				+ ", "
				+ avancado
				+ ","
				+ media_avancado + ", " + nro_calculo_medias + " )";
		this.update(string);

		this.fecharConexao();
	}

	// ///////////////////

}
