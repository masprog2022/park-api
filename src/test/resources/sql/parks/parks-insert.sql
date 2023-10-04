insert into tb_users (id, username, password, role) values (100, 'ana@gmail.com', '$2a$12$u/OJB7yDQ3GXdR2y41aBa.QRsxRIwcF2ZhyIjITuFnPKhMLEvMM4K', 'ROLE_ADMIN');
insert into tb_users (id, username, password, role) values (101, 'bia@gmail.com', '$2a$12$u/OJB7yDQ3GXdR2y41aBa.QRsxRIwcF2ZhyIjITuFnPKhMLEvMM4K', 'ROLE_ADMIN');
insert into tb_users (id, username, password, role) values (102, 'bob@gmail.com', '$2a$12$u/OJB7yDQ3GXdR2y41aBa.QRsxRIwcF2ZhyIjITuFnPKhMLEvMM4K', 'ROLE_CUSTOMER');
insert into tb_users (id, username, password, role) values (103, 'toby@gmail.com', '$2a$12$u/OJB7yDQ3GXdR2y41aBa.QRsxRIwcF2ZhyIjITuFnPKhMLEvMM4K', 'ROLE_CUSTOMER');


insert into tb_customers (id, name, cpf, id_user) values (21, 'Bianca Silva', '63609496010', 100);
insert into tb_customers (id, name, cpf, id_user) values (22, 'Roberto Gomes', '52186821010', 102);

insert into tb_slots (id, code, status) values (100, 'A-01', 'BUSY');
insert into tb_slots (id, code, status) values (200, 'A-02', 'BUSY');
insert into tb_slots (id, code, status) values (300, 'A-03', 'BUSY');
insert into tb_slots (id, code, status) values (400, 'A-04', 'FREE');
insert into tb_slots (id, code, status) values (500, 'A-05', 'FREE');

insert into tb_customers_has_slots (number_receipt, plate, make, model, color, input_data, id_customer, id_slot)
    values ('20230313-101300', 'FIT-1020', 'FIAT', 'PALIO', 'VERDE', '2023-03-13 10:15:00', 22, 100);
insert into tb_customers_has_slots (number_receipt, plate, make, model, color, input_data, id_customer, id_slot)
    values ('20230314-101400', 'SIE-1020', 'FIAT', 'SIENA', 'BRANCO', '2023-03-14 10:15:00', 21, 200);
insert into tb_customers_has_slots (number_receipt, plate, make, model, color, input_data, id_customer, id_slot)
    values ('20230315-101500', 'FIT-1020', 'FIAT', 'PALIO', 'VERDE', '2023-03-14 10:15:00', 22, 300);