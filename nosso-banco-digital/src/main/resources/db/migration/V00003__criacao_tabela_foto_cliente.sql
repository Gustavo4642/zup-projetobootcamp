create table foto_cliente (
	cli_id bigint not null,
    fot_nome_arquivo varchar(150) not null,
    fot_descricao varchar(150),
    fot_content_type varchar(80) not null,
    fot_tamanho int not null,
    
    primary key (cli_id),
    constraint fk_foto_cliente_cliente foreign key (cli_id) references cliente (id)
) engine=InnoDB default charset=utf8