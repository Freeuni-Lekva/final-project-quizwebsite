use quizwebsite_db;

drop table if exists standard_unordered_answers;
drop table if exists standard_unordered_questions;
drop table if exists multiple_choice_unordered_answers;
drop table if exists multiple_choice_unordered_questions;
drop table if exists picture_unordered_answers;
drop table if exists picture_unordered_questions;
drop table if exists multiple_answer_unordered_answers;
drop table if exists multiple_answer_unordered_questions;
drop table if exists quiz_history;
drop table if exists quizzes;
drop table if exists friend_requests;
drop table if exists friendship;
drop table if exists messages;
drop table if exists users;

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
