INSERT INTO users
VALUES ('0cee74e5-6741-4de7-9b36-4d7c09e333c8', 'robert_smith@email.com', 'Robert', 'Smith', 1),
       ('6fa86495-e5af-4e49-a962-598a4b50e81c', 'john_conor@email.com', 'John', 'Conor', 1),
       ('25530c99-fea6-4cb5-afb5-6586caa304b5', 'someemail@email.com', 'Somename', 'Somesurname', 1);

insert into locations
VALUES (gen_random_uuid(), '0cee74e5-6741-4de7-9b36-4d7c09e333c8', '2022-02-08T11:44:00.524Z', 52.25742342295784, 10.540583401747602),
       (gen_random_uuid(), '0cee74e5-6741-4de7-9b36-4d7c09e333c8', '2022-02-08T11:45:00.524Z', 52.25792342295784, 10.640584401747602),
       (gen_random_uuid(), '0cee74e5-6741-4de7-9b36-4d7c09e333c8', '2022-02-08T11:46:00.524Z', 52.25842342295784, 10.740583401747602),
       (gen_random_uuid(), '0cee74e5-6741-4de7-9b36-4d7c09e333c8', '2022-02-08T11:47:00.524Z', 52.25942342295784, 10.840583401747602),
       (gen_random_uuid(), '0cee74e5-6741-4de7-9b36-4d7c09e333c8', '2022-02-08T11:48:00.524Z', 52.26142342295784, 10.940583401747602);

insert into locations
VALUES (gen_random_uuid(), '6fa86495-e5af-4e49-a962-598a4b50e81c', '2022-02-08T11:44:00.524Z', 62.25742342295784, 25.540583401747602),
       (gen_random_uuid(), '6fa86495-e5af-4e49-a962-598a4b50e81c', '2022-02-08T12:44:00.524Z', 62.25792342295784, 25.640584401747602),
       (gen_random_uuid(), '6fa86495-e5af-4e49-a962-598a4b50e81c', '2022-02-08T13:44:00.524Z', 62.25842342295784, 25.740583401747602),
       (gen_random_uuid(), '6fa86495-e5af-4e49-a962-598a4b50e81c', '2022-02-08T14:44:00.524Z', 62.25942342295784, 25.840583401747602),
       (gen_random_uuid(), '6fa86495-e5af-4e49-a962-598a4b50e81c', '2022-02-08T15:44:00.524Z', 62.26142342295784, 25.940583401747602);