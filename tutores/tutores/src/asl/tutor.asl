// Agent tutor in project moodleTutor
id(ID).
nome(Nome).
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

/* 
+!addDisc(Id, Nome) :true
<- +disc(Id, Nome)
.
                                    TESTAR
+!remDisc(Id, Nome) : true
<- -disc(Id, Nome)
.
*/
+!showMe : true
<-	?id(ID);
	?nome(NOME);
	.concat(" ID= ", ID," Nome= ", NOME, Rest);
	.print(Rest).
