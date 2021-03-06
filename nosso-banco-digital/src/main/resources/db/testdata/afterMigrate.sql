
set foreign_key_checks=0;

delete from cliente;
delete from endereco;
delete from foto_cliente;
delete from proposta;
delete from conta;

set foreign_key_checks=1;

alter table cliente auto_increment=1;
alter table endereco auto_increment=1;
alter table foto_cliente auto_increment=1;
alter table proposta auto_increment=1;
alter table conta auto_increment=1;

/* início clientes */
insert into endereco (end_cep, end_rua, end_numero, end_bairro, end_complemento, end_cidade, end_estado) 
	values ("13611-365", "Tome de Souza", "500", "Santa Rita", "Casa", "Leme", "SP");
insert into endereco (end_cep, end_rua, end_numero, end_bairro, end_complemento, end_cidade, end_estado) 
	values ("13611-365", "Tome de Souza", "500", "Santa Rita", "Casa", "Leme", "SP");
insert into endereco (end_cep, end_rua, end_numero, end_bairro, end_complemento, end_cidade, end_estado) 
	values ("13611-365", "Tome de Souza", "500", "Santa Rita", "Casa", "Leme", "SP");
	
/* fim clientes */

/* início clientes */
insert into cliente (cli_codigo, cli_nome, cli_sobrenome, cli_email, cli_data_nascimento, cli_cpf, cli_endereco_id, cli_status) 
	values (UUID(), "Luiz Gustavo", "do Amaral Correa", "nayara.lgac@gmail.com", "1989-07-20", "111.111.111-11", 3, "INATIVO");
insert into cliente (cli_codigo, cli_nome, cli_sobrenome, cli_email, cli_data_nascimento, cli_cpf, cli_endereco_id, cli_status) 
	values (UUID(), "João", "Eli Correa", "cjoaoeli@gmail.com", "1989-07-20", "123.456.789-10", 1, "INATIVO");
insert into cliente (cli_codigo, cli_nome, cli_sobrenome, cli_email, cli_data_nascimento, cli_cpf, cli_endereco_id, cli_status) 
	values (UUID(), "Nayara", "Arrais Serodio Correa", "nayaraserodio@gmail.com", "1990-03-14", "222.222.222-22", 2, "INATIVO");

/* fim clientes */
