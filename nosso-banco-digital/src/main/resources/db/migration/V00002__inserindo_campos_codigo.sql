
alter table cliente add cli_codigo varchar(36) not null after id;
alter table cliente add constraint uk_cliente_cli_codigo unique (cli_codigo);
