alter table cliente add cli_status varchar(10) not null;
update cliente set cli_status ='Inativo';