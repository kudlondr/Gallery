create table USERS ( 
  id int not null auto_increment,
  username varchar2(20 char) not null,
  password varchar2(64 char) not null,
  email varchar2(60 char) not null,
  name varchar2(60 char),
  surname varchar2(60 char),
  showemail number(1, 0) default '0',
  showname number(1, 0) default '0',
  showsurname number(1, 0) default '0',
  
  constraint I_USERS_ID primary key (id),
  constraint U_USERS unique (email)
);

create table IMAGES (
	id int not null auto_increment,
	name varchar(100 char) not null,
	createdDate timestamp not null default sysdate,
	userId int not null,
	hidden boolean not null default false,
	description clob,
	data blob,
	previewid int not null,
	
	constraint I_IMAGES_ID primary key (id),
	constraint U_IMAGES unique (name, userId)
);

create table COMMENTS (
	id int not null auto_increment,
	createdBy int not null,
	belongsTo int not null,
	type varchar(1 byte) not null, --I (image), U (profile)
	createdDate timestamp not null default sysdate,
	hidden boolean not null default false,
	text clob,

	constraint I_COMMENT_ID primary key (id)
);

create table MESSAGES (
	id int not null auto_increment,
	responseId int,
	createdBy int not null,
	belongsTo int not null,
	createdDate timestamp not null default sysdate,
	text clob,
	read boolean not null default false,
	
	constraint I_MESSAGE_ID primary key (id)
);

create table WATCHLISTS (
	id int not null auto_increment,
	belongsTo int not null,
	watching int not null,
	createdDate timestamp not null default sysdate,
	
	constraint I_WATCHLIST_ID primary key (id)
);

create table IMAGE_COMMENTS (
	id integer(10) not null,
	imageId integer(10) not null,
	commentId integer(10) not null,
	constraint I_IMAGE_COMMENTS_ID primary key (id)
);

create table USER_COMMENTS (
	id integer(10) not null,
	userId integer(10) not null,
	commentId integer(10) not null,
	constraint I_USER_COMMENT_ID primary key (id)
);

create table IMAGE_PREVIEWS (
	id int not null auto_increment,
	createdDate timestamp not null default sysdate,
	data blob,
	constraint I_IMAGE_PREVIEWS_ID primary key (id)
);