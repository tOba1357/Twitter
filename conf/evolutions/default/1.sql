# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table tbl_tweet (
  id                        bigint auto_increment not null,
  content                   varchar(255) not null,
  author_id                 bigint,
  create_date               datetime(6) not null,
  constraint pk_tbl_tweet primary key (id))
;

create table tbl_user (
  id                        bigint auto_increment not null,
  email                     varchar(255) not null,
  hashed_password           varchar(255) not null,
  user_name                 varchar(255) not null,
  create_date               datetime(6) not null,
  update_date               datetime(6) not null,
  constraint pk_tbl_user primary key (id))
;

alter table tbl_tweet add constraint fk_tbl_tweet_author_1 foreign key (author_id) references tbl_user (id) on delete restrict on update restrict;
create index ix_tbl_tweet_author_1 on tbl_tweet (author_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table tbl_tweet;

drop table tbl_user;

SET FOREIGN_KEY_CHECKS=1;

