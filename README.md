# iFace

<p align="left"> 
  Sistema que mantém uma rede de relacionamentos, baseado em conceitos de redes sociais (Facebook, Twitter, etc.).</br>
  Desenvolvido em Java 17 como requisito da disciplina <strong>COMP372 - PROJETO DE SOFTWARE</strong> (Programação 2)
</p>

## AB1
<div> 
Iniciar a construção do projeto aplicando conceitos de classes, herança e polimorfismo </br></br>
Na primeira entrega, desenvolver funcionalidades.</br>
Na segunda entrega, refatorar o projeto para aplicar corretamente: herança, polimorfismo, classes abstratas, interfaces e generics.</br>
</div>

## AB2
<div> 
Trazer o projeto para um nível "production ready" aplicando padrões de projeto, tratamento de exceções e mitigando de code smells para melhorar a qualidade do software</br></br>
Na terceira entrega, adicionar tratamento de exceções.</br>
Na quarta entrega, garantir a qualidade do projeto mitigando code smells e aplicando padrões de projeto.</br>
</div>

## Funcionalidades

🟩 (1) - Criação de Conta
<div> 
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Permita a um usuário criar uma conta no iFace.
  <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Deve ser fornecido um login, uma senha e um nome com o qual o usuário será conhecido na rede.
  <br/>&nbsp;
</div>


🟩 (2) - Criação/Edição de Perfil
<div> 
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Permita a um usuário cadastrado do iFace criar/editar atributos de seu perfil.
  <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ele deve poder modificar qualquer atributo do perfil ou preencher um atributo inexistente.
  <br/>&nbsp;
</div>

🟩 (3) - Adição de Amigos
<div> 
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Permita a um usuário cadastrado do iFace adicionar outro usuário como amigo, o que faz o sistema enviar-lhe um convite.
  <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;O relacionamento só é efetivado quando o outro usuário o adicionar de volta.
  <br/>&nbsp;
</div>

🟩 (4) - Envio de Mensagens
<div> 
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Permita a um usuário cadastrado do iFace enviar um recado a qualquer outro usuário cadastrado ou comunidade.
  <br/>&nbsp;
</div>

🟩 (5) - Criação de Comunidades
<div> 
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Permita a um usuário cadastrado do iFace criar uma comunidade.
  <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Deve ser fornecido um nome e uma descrição.
  <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;O usuário passa a ser o dono da comunidade e é o responsável por gerenciar os membros.
  <br/>&nbsp;
</div>

🟩 (6) - Adição de membros
<div> 
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Permita a um usuário cadastrado do iFace se tornar membro de uma comunidade.
  <br/>&nbsp;
</div>

🟩 (7) - Recuperar Informações sobre um determinado Usuário
<div> 
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Permita a um usuário cadastrado do iFace recuperar informações sobre o seu perfil, comunidades, amigos e mensagens.
    <br/>&nbsp;
</div>

🟩 (8) - Remoção de Conta
<div> 
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Permita a um usuário encerrar sua conta no iFace.
  <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Todas as suas informações devem sumir do sistema: relacionamentos, mensagens enviadas, perfil.
  <br/>&nbsp;
</div>

🟩 (9) - Envio de Mensagens no Feed de Notícias
<div> 
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Permita a um usuário enviar mensagens no Feed de notícias
  <br/>&nbsp;
</div>

🟩 (10) - Controle de visualização do Feed de Notícias
<div> 
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Permita a um usuário definir o controle de visualização das mensagens do Feed 
  <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(somente amigos ou todos podem visualizar as mensagens)
  <br/>&nbsp;
</div>
