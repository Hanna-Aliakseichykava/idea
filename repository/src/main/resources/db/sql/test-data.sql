insert into USERS (EMAIL, PASSWORD, CREATION_TIME) values ('Chris@test.com', '1234', '2015-05-03 00:00:00');
insert into USERS (EMAIL, PASSWORD, CREATION_TIME) values ('Scott@test.com', '12345', '2015-11-02 00:00:00');
insert into USERS (EMAIL, PASSWORD, CREATION_TIME) values ('John@test.com', '123456', '2015-02-28 00:00:00');

insert into IDEAS (TITLE, DESCRIPTION, CREATION_TIME, MODIFICATION_TIME, RATING, USER_ID) values ('Nonlinear Optimization by Successive Linear Programming', 'F. Palacios-Gomez & L. Lasdon & M. Engquist', '2015-01-01 03:14:07', '2015-01-02 00:00:00', 2, 1);
insert into IDEAS (TITLE, DESCRIPTION, CREATION_TIME, MODIFICATION_TIME, RATING, USER_ID) values ('Interactive fuzzy goal programming approach for bilevel programming problem', 'Arora, S.R. & Gupta, Ritu', '2015-02-02 00:00:00', '2015-02-03 00:00:00', 3, 2);
insert into IDEAS (TITLE, DESCRIPTION, CREATION_TIME, MODIFICATION_TIME, RATING, USER_ID) values ('Two-Segment Separable Programming', 'R. R. Meyer', '2014-12-01', '2014-12-12', -2, 3);

insert into ROLES (NAME) values ('ADMIN');
insert into ROLES (NAME) values ('USER');

insert into USER_ROLES (USER_ID, ROLE_ID) values (1, 1);
insert into USER_ROLES (USER_ID, ROLE_ID) values (2, 2);
insert into USER_ROLES (USER_ID, ROLE_ID) values (3, 2);

insert into TAGS (NAME) values ('Programming');
insert into TAGS (NAME) values ('Porn');

insert into IDEA_TAGS (IDEA_ID, TAG_ID) values (1, 1);
insert into IDEA_TAGS (IDEA_ID, TAG_ID) values (2, 1);
insert into IDEA_TAGS (IDEA_ID, TAG_ID) values (3, 1);

insert into COMMENTS (USER_ID, IDEA_ID, BODY, CREATION_TIME, MODIFICATION_TIME, RATING) values (1, 1, 'Awesome!', '2014-08-02 00:00:00', '2014-12-24 00:00:00', 5);
insert into COMMENTS (USER_ID, IDEA_ID, BODY, CREATION_TIME, MODIFICATION_TIME, RATING) values (2, 2, 'Super!', '2014-06-12 00:00:00', '2014-09-20 00:00:00', 10);
insert into COMMENTS (USER_ID, IDEA_ID, BODY, CREATION_TIME, MODIFICATION_TIME, RATING) values (3, 3, 'Genius!', '2015-01-10 00:00:00', '2015-02-03 00:00:00', -6);