# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table tbl_timeline (
  id                        bigint auto_increment not null,
  user_id                   bigint,
  retweet_user_id           bigint,
  tweet_id                  bigint,
  constraint pk_tbl_timeline primary key (id))
;

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


create table tbl_user_tbl_user (
  tbl_user_id                    bigint not null,
  id                             bigint not null,
  constraint pk_tbl_user_tbl_user primary key (tbl_user_id, id))
;
alter table tbl_timeline add constraint fk_tbl_timeline_user_1 foreign key (user_id) references tbl_user (id) on delete restrict on update restrict;
create index ix_tbl_timeline_user_1 on tbl_timeline (user_id);
alter table tbl_timeline add constraint fk_tbl_timeline_retweetUser_2 foreign key (retweet_user_id) references tbl_user (id) on delete restrict on update restrict;
create index ix_tbl_timeline_retweetUser_2 on tbl_timeline (retweet_user_id);
alter table tbl_timeline add constraint fk_tbl_timeline_tweet_3 foreign key (tweet_id) references tbl_tweet (id) on delete restrict on update restrict;
create index ix_tbl_timeline_tweet_3 on tbl_timeline (tweet_id);
alter table tbl_tweet add constraint fk_tbl_tweet_author_4 foreign key (author_id) references tbl_user (id) on delete restrict on update restrict;
create index ix_tbl_tweet_author_4 on tbl_tweet (author_id);



alter table tbl_user_tbl_user add constraint fk_tbl_user_tbl_user_tbl_user_01 foreign key (tbl_user_id) references tbl_user (id) on delete restrict on update restrict;

alter table tbl_user_tbl_user add constraint fk_tbl_user_tbl_user_tbl_user_02 foreign key (id) references tbl_user (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table tbl_timeline;

drop table tbl_tweet;

drop table tbl_user;

drop table tbl_user_tbl_user;

SET FOREIGN_KEY_CHECKS=1;

