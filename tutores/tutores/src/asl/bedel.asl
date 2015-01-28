// Agent sample_agent in project moodleTutor
id(ID).
nome(NOME).
/* Initial beliefs and rules */

/* Initial goals */

/* Plans */
+!start : true 
<-	?id(ID);
	!string(S);
	makeArtifact(S ,"artifact.Bedel_Atc",[],Id1);
	.print("artefato criado");
	setIDcourse(ID);
	!showMe;
	//!showTeachers;
	//!showStudents;
	.
	
+!string(S) : true 
<- 	?id(I);
	.concat("inst",I,S).	
	
+!showTeachers : true
<-	.print("Professores:");
	show_Teacher.
	
+!showStudents : true
<-	.print("Alunos:");
	show_Students.

+!showMe : true
<- .print("Tutor pronto:");
	show_Me.
