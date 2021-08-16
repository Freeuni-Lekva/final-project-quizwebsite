create database quizwebsite_db;

use quizwebsite_db;

create table users (
   id int primary key auto_increment,
   username varchar(50) unique not null ,
   hashed_password varchar(200) not null,
   is_admin boolean not null,
   first_name varchar(30) not null,
   last_name varchar(30) not null
);

create table messages(
     id int primary key auto_increment,
     from_user_id int not null,
     to_user_id int not null,
     msg_text varchar(1000) not null,
     sent_time timestamp not null,
     foreign key(from_user_id) references users(id) on delete cascade,
     foreign key(to_user_id) references users(id) on delete cascade
);

create table friend_requests(
    id int primary key auto_increment,
    from_user_id int not null,
    to_user_id int not null,
    foreign key(from_user_id) references users(id) on delete cascade,
    foreign key(to_user_id) references users(id) on delete cascade
);

create table friendship(
   id int primary key auto_increment,
   first_user_id int not null references users(id) on delete cascade,
   second_user_id int not null references users(id) on delete cascade
);

create table quizzes(
    id int primary key auto_increment,
    author int not null,
    quiz_name varchar(200) not null,
    is_random_order boolean not null,
    foreign key(author) references users(id) on delete cascade
);

create table quiz_history(
     id int primary key auto_increment,
     quiz_id int not null,
     user_id int not null,
     score double not null,
     attempt_time timestamp not null,
     foreign key(quiz_id) references quizzes(id) on delete cascade,
     foreign key(user_id) references users(id) on delete cascade
);

create table standard_unordered_questions(
     id int primary key auto_increment,
     question_text varchar(800) not null,
     quiz_id int not null,
     foreign key(quiz_id) references quizzes(id) on delete cascade
);

create table standard_unordered_answers (
    id int primary key auto_increment,
    answer_text varchar(200) not null,
    question_id int not null,
    foreign key(question_id) references standard_unordered_questions(id) on delete cascade
);

create table multiple_choice_unordered_questions (
     id int primary key auto_increment,
     question_text varchar(800) not null,
     quiz_id int not null,
     foreign key(quiz_id) references quizzes(id) on delete cascade
);

create table multiple_choice_unordered_answers (
   id int primary key auto_increment,
   answer_text varchar(200) not null,
   question_id int not null,
   foreign key(question_id) references multiple_choice_unordered_questions(id) on delete cascade,
   is_correct boolean not null
);

create table picture_unordered_questions(
    id int primary key auto_increment,
    question_text varchar(800) not null,
    img_url varchar(1000) not null,
    quiz_id int not null,
    foreign key(quiz_id) references quizzes(id) on delete cascade
);

create table picture_unordered_answers (
   id int primary key auto_increment,
   answer_text varchar(200) not null,
   question_id int not null,
   foreign key(question_id) references picture_unordered_questions(id) on delete cascade
);

create table multiple_answer_unordered_questions (
     id int primary key auto_increment,
     question_text varchar(800),
     quiz_id int not null,
     numOfRequestedAnswers int not null,
     foreign key(quiz_id) references quizzes(id) on delete cascade
);

create table multiple_answer_unordered_answers (
   id int primary key auto_increment,
   answer_text varchar(200),
   question_id int not null,
   foreign key(question_id) references multiple_answer_unordered_questions(id) on delete cascade
);


-- examples --
/*
insert into users(username, hashed_password, is_admin) values("test1", "pass", 1);
insert into quizzes(author, quiz_name) values (1, "Quiz1");
insert into quizzes(author, quiz_name) values (1, "Quiz2");

insert into standard_unordered_questions(question_text, quiz_id) values ("What is the highest mountain", 1);
insert into standard_unordered_questions(question_text, quiz_id) values ("What is the deepest lake", 1);
insert into multiple_choice_unordered_questions(question_text, quiz_id) values ("What is the deepest lake in georgia", 1);
insert into standard_unordered_answers(answer_text, question_id) values ("everest", 1);
insert into standard_unordered_answers(answer_text, question_id) values ("baikal", 2);
insert into multiple_choice_unordered_answers(answer_text, question_id, is_correct) values ("paravani", 1, false);
insert into multiple_choice_unordered_answers(answer_text, question_id, is_correct) values ("ritsa", 1, true);
insert into multiple_choice_unordered_answers(answer_text, question_id, is_correct) values ("paliastomi", 1, false);


insert into multiple_choice_unordered_questions(question_text, quiz_id) values ("What is the biggest sea", 2);
insert into multiple_choice_unordered_questions(question_text, quiz_id) values ("What is the biggest city", 2);
insert into multiple_choice_unordered_answers(answer_text, question_id, is_correct) values ("black sea", 2, false);
insert into multiple_choice_unordered_answers(answer_text, question_id, is_correct) values ("azov sea", 2, false);
insert into multiple_choice_unordered_answers(answer_text, question_id, is_correct) values ("philippine Sea", 2, true);
insert into multiple_choice_unordered_answers(answer_text, question_id, is_correct) values ("tbilisi", 3, false);
insert into multiple_choice_unordered_answers(answer_text, question_id, is_correct) values ("tokyo", 3, true);
insert into multiple_choice_unordered_answers(answer_text, question_id, is_correct) values ("riga", 3, false);
*/

-- selects
/*
select * from users;
select * from quizzes;
select * from quiz_history;
select * from standard_unordered_questions;
select * from standard_unordered_answers;
select * from multiple_choice_unordered_questions;
select * from multiple_choice_unordered_answers;
select * from picture_unordered_questions;
select * from picture_unordered_answers;
select * from multiple_answer_unordered_questions;
select * from multiple_answer_unordered_answers;
*/

-- drops
/*
drop table standard_unordered_answers;
drop table standard_unordered_questions;
drop table multiple_choice_unordered_answers;
drop table multiple_choice_unordered_questions;
drop table picture_unordered_answers;
drop table picture_unordered_questions;
drop table multiple_answer_unordered_answers;
drop table multiple_answer_unordered_questions;
drop table quiz_history;
drop table quizzes;
drop table friend_requests;
drop table friendship;
drop table messages;
drop table users;
*/
