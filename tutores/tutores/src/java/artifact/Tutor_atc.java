package artifact;

import abs.User;
import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;

public class Tutor_atc extends Artifact {
	public User aluno;
	
	@OPERATION
	public void setAluno(User aluno){
		this.aluno = aluno;
		this.aluno.setAgent();
	}
	
	@OPERATION
	public void mostrarDisciplinas(OpFeedbackParam<String> s){
		s.set(aluno.show_disc());
	}
}
