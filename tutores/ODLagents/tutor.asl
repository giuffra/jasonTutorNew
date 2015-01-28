// Agent sample_agent in project tutor

/* Initial beliefs and rules */

notaPerfilAluno(Nota).
atividadeAvaliada(false).

/* Initial goals */

/* Plans */

!atualizaNota.

+!atualizaNota:  atividadeAvaliada(I) & I == true <-
Avaliou = false;
-+atividadeAvaliada(Avaliou);
-+notaPerfilAluno(Nota).

+!enviaMensagem: true <-
enviamensagem(Nota).

/*!print.

+!print<- true; 
 .print("tutor"). 
  */
 //manda mensagem ao alunos

