use ScoreboardTest;
CREATE TABLE player(
id bigint auto_increment,
fullname varchar(255) not null,
winscount int not null,
losecount int not null,
username varchar(255) not null,
password varchar(255) not null,
status int not null,
point bigint not null,
primary key (id)
);

CREATE TABLE game(
id bigint auto_increment not null,
winner bigint not null,
player1 bigint not null,
player2 bigint not null,
primary key (id),
foreign key (player1) references player(id),
foreign key (player2) references player(id)
);

CREATE TABLE log(
id bigint auto_increment not null,
status int not null,
player1 bigint not null,
player2 bigint not null,
point1 bigint not null,
cpoint1 bigint not null,
point2 bigint not null,
cpoint2 bigint not null,
gameID bigint not null,
primary key (id),
foreign key (player1) references player(id),
foreign key (player2) references player(id)
);

INSERT INTO player (fullname, winscount, losecount, username, password, status, point) VALUES('Apple', 0, 0, 'elppa', 'password', 0, 0);
INSERT INTO player (fullname, winscount, losecount, username, password, status, point) VALUES('Ngoclann', 0, 0, 'ngoclann', '123456', 0, 0);
INSERT INTO player (fullname, winscount, losecount, username, password, status, point) VALUES('pipi', 0, 0, 'pipi', 'pipi', 0, 0);
INSERT INTO player (fullname, winscount, losecount, username, password, status, point) VALUES('didi', 0, 0, 'didi', 'didi', 0, 0);
