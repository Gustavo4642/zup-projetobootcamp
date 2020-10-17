create table cliente 
	(id bigint not null auto_increment, 
    cli_nome varchar(150) not null,
    cli_sobrenome varchar(150) not null, 
    cli_email varchar(100) not null, 
    cli_data_nascimento datetime not null,
    cli_cpf varchar(14) not null, 
    cli_endereco_id bigint,
    
    primary key (id)
    ) 
engine=InnoDB default charset=utf8;

create table endereco 
	(id bigint not null auto_increment, 
    end_cep varchar(9) not null,
    end_rua varchar(255) not null, 
    end_numero varchar(10) not null, 
    end_bairro varchar(120) not null, 
    end_complemento varchar(150) not null, 
    end_cidade varchar(150) not null, 
    end_estado varchar(150) not null, 
    
    primary key (id)
    ) 
engine=InnoDB default charset=utf8;

alter table cliente add constraint fk_cliente_endereco
	foreign key (cli_endereco_id) references endereco (id);