use training;
CREATE TABLE user(
	id bigint primary key auto_increment,
    name VARCHAR(255) NOT NULL,
    wins_count int,
    loses_count int
);