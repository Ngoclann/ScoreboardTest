use training;
CREATE TABLE user(
	id bigint auto_increment,
    name VARCHAR(255) NOT NULL,
    wins_count int,
    loses_count int,
    PRIMARY KEY (id)
);
CREATE TABLE game(
	id bigint auto_increment,
    winner bigint,
    player_id bigint,
    FOREIGN KEY (player_id) REFERENCES user(id),
    PRIMARY KEY (id)
);