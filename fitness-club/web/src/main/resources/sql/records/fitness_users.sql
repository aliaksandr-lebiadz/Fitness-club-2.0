INSERT INTO fitness_user(email, password, role, first_name, second_name, discount)
VALUES
('admin@mail.ru', md5('admin'), 'ADMIN', NULL, NULL, 52),
('client@mail.ru', md5('client'), 'CLIENT', 'Ivan', 'Ivanov', 10),
('client2@mail.ru', md5('client2'), 'CLIENT', 'Alexandr', 'Tuhev', 0),
('client3@mail.ru', md5('client3'), 'CLIENT', 'Konstantin', 'Aleev', 4),
('client4@mail.ru', md5('client4'), 'CLIENT', 'Alesya', 'Kotova', 5),
('trainer@mail.ru', md5('trainer'), 'TRAINER', NULL, NULL, 25),
('trainer2@mail.ru', md5('trainer2'), 'TRAINER', NULL, NULL, 2),
('trainer3@mail.ru', md5('trainer3'), 'TRAINER', NULL, NULL, 11),
('trainer4@mail.ru', md5('trainer4'), 'TRAINER', NULL, NULL, 0),
('trainer5@mail.ru', md5('trainer5'), 'TRAINER', NULL, NULL, 58);