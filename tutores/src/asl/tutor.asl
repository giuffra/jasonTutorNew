// Agent tutor in project moodleTutor
id(ID).
nome(Nome).
aluno(false).
professor(false).
//disc(Id, Nome).
/* Initial beliefs and rules */

/* Initial goals */



/* Plans */

+!start : true
<- !showAluno.

+!criarArtefato : true
<-	makeArtifact(S,"artifact.Tutor_Atc",[],id1)
.

+!showAluno : true
<-	?id(ID);
	.print("Aluno:");
	!showMe.

+!setStudent(ID,N) : true
<- 	-+idAluno(ID).

+!showMe : true
<-	?id(ID);
	?nome(NOME);
	.concat(" ID= ", ID," Nome= ", NOME, Rest);
	.print(Rest).
