create table conta 
	(id bigint not null auto_increment, 
    con_codigo varchar(36) not null,
    con_agencia varchar(5) not null, 
    con_numero_conta varchar(9) not null, 
    con_codigo_banco varchar(3) not null, 
    con_saldo  decimal(10,2) not null,
    con_proposta_id bigint not null,
    
    primary key (id)
    ) 
engine=InnoDB default charset=utf8;

alter table conta add constraint uk_conta_con_codigo unique (con_codigo);

alter table conta add constraint fk_conta_proposta
	foreign key (con_proposta_id) references proposta (id);