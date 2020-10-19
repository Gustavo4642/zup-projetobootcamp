create table proposta 
	(id bigint not null auto_increment, 
    pro_codigo varchar(36) not null,
    pro_cliente_id bigint not null, 
    pro_status varchar(10) not null, 
    pro_motivo varchar(150) not null,
    
    primary key (id)
    ) 
engine=InnoDB default charset=utf8;

alter table proposta add constraint fk_proposta_cliente
	foreign key (pro_cliente_id) references cliente (id);
	
alter table proposta add constraint uk_proposta_pro_codigo unique (pro_codigo);