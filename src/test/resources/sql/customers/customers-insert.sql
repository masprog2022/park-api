insert into tb_users (id, username, password, role) values (100, 'ana@gmail.com', '$2a$12$u/OJB7yDQ3GXdR2y41aBa.QRsxRIwcF2ZhyIjITuFnPKhMLEvMM4K', 'ROLE_ADMIN');
insert into tb_users (id, username, password, role) values (101, 'bia@gmail.com', '$2a$12$u/OJB7yDQ3GXdR2y41aBa.QRsxRIwcF2ZhyIjITuFnPKhMLEvMM4K', 'ROLE_CUSTOMER');
insert into tb_users (id, username, password, role) values (102, 'bob@gmail.com', '$2a$12$u/OJB7yDQ3GXdR2y41aBa.QRsxRIwcF2ZhyIjITuFnPKhMLEvMM4K', 'ROLE_CUSTOMER');
insert into tb_users (id, username, password, role) values (103, 'toby@gmail.com', '$2a$12$u/OJB7yDQ3GXdR2y41aBa.QRsxRIwcF2ZhyIjITuFnPKhMLEvMM4K', 'ROLE_CUSTOMER');

insert into tb_customers (id, name, cpf, id_user) values (10, 'Bianca Silva', '63609496010', 101);
insert into tb_customers (id, name, cpf, id_user) values (20, 'Roberto Gomes', '52186821010', 102);