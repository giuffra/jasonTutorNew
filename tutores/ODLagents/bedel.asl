// Agent bedel in project tutor

/* Initial beliefs and rules */

acessoProfessor(false).
olhoFechado(false).
ativouBanco(false).
existeAtividadeParaAvaliar(false).
existeAtividadeAvaliada(false). //novacren�a

/* Initial goals */

!ativaArtBanco. //achievement goal.

+!ativaArtBanco: acessoProfessor(I) & I == false <-
	makeArtifact("codigoDisciplina","artifact.BD_Artifact", [], Id1);
	.print("artefato criado");
	Ativou = true;
	-+ativouBanco(Ativou);	
	focus(Id1);
	start;
	!verifica_Login_Professor 
.
+!verifica_Login_Professor: acessoProfessor(J) & J == false <- //feito uma vez s�, depois do primeiro acesso, 
	verifica_login_professor;								//o belief do bedel deve ficar como "j� fez login".	
	.print("acesso professor 1");
	Acessou = true;
	-+acessoProfessor(Acessou);
	!verifica_Olho_Fechado.

+!verifica_Olho_Fechado: olhoFechado(K) & K == false <- //feito uma vez s�, no in�cio da disciplina,	
	verifica_olho_fechado;								// o belief do bedel deve ficar como "j� iniciou".
	Fechado = true;
	-+olhoFechado(Fechado);
	!verifica_avaliacao.

@plan
+tick: true <-
	.print ("entrei no tick");	
	!verifica_data_final_tarefa.

+!verifica_data_final_tarefa: true <-//feito uma vez por dia, para verificar se tem tarefas com data de entrega "vencida" e devem ser avaliadas.
	.print("verifiquei data final tarefaaaaaaaaaaaaaaaaaaaaaaaaaaaa ");	
	verifica_data_final_tarefa; // inserir aqui os dados das tarefas que est�o com data final "vencida" como argumento.
	!verifica_avaliacao. 

+!verifica_avaliacao: true <- //se data_final_tarefa � true, ent�o verifica se j� foi avaliada.
	verifica_avaliacao;
	.print("verifiquei avaliacao");
	!calcula_notas_perfil_alunos.

+!calcula_notas_perfil_alunos: true <- //se tarefa foi avaliada ent�o calcula_notas_perfil_alunos
	calcula_notas_perfil_alunos;
	.print("calculei notas perfil");
	!calcula_media_perfil.// inserir aqui os dados das tarefas que est�o com data final "vencida" como argumento.

+!calcula_media_perfil: true <- //ap�s calcular a nota do perfil dos alunos deve calcular a media do perfil.
	calcula_media_perfil;
	.print("calculei media perfil");
	!cacula_valores_perfis.

+!cacula_valores_perfis: true<- //ap�s media do perfil deve calcular os valores do perfil.
	cacula_valores_perfis;
	.print(final).
