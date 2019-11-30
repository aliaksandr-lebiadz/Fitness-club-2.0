use fitness;
INSERT INTO fitness_user(email, password, role, first_name, second_name, discount)
VALUES
('admin@mail.ru', md5('admin'), 'admin', NULL, NULL, 52),
('client@mail.ru', md5('client'), 'client', 'Ivan', 'Ivanov', 10),
('client2@mail.ru', md5('client2'), 'client', 'Alexandr', 'Tuhev', 0),
('client3@mail.ru', md5('client3'), 'client', 'Konstantin', 'Aleev', 4),
('client4@mail.ru', md5('client4'), 'client', 'Alesya', 'Kotova', 5),
('trainer@mail.ru', md5('trainer'), 'trainer', NULL, NULL, 25),
('trainer2@mail.ru', md5('trainer2'), 'trainer', NULL, NULL, 2),
('trainer3@mail.ru', md5('trainer3'), 'trainer', NULL, NULL, 11),
('trainer4@mail.ru', md5('trainer4'), 'trainer', NULL, NULL, 0),
('trainer5@mail.ru', md5('trainer5'), 'trainer', NULL, NULL, 58);