CREATE DATABASE playstack;

use playstack;

CREATE TABLE User (
    username VARCHAR(120) PRIMARY KEY,
    name VARCHAR(150),
    password VARCHAR(125),
    email VARCHAR(140)
);

CREATE TABLE Category (
    id INT(7) unsigned AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    description TEXT,
    user_username VARCHAR(120),
    FOREIGN KEY (user_username) REFERENCES User(username)
);

CREATE TABLE Game (
    id INT(7) unsigned AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150),
    platform VARCHAR(50),
	user_username VARCHAR(120),                      
    FOREIGN KEY (user_username) REFERENCES User(username)
);

CREATE TABLE Holds (
    category_id INT(7) unsigned,
    game_id INT(7) unsigned,
    PRIMARY KEY (category_id, game_id),
    FOREIGN KEY (category_id) REFERENCES Category(id) ON DELETE CASCADE,
    FOREIGN KEY (game_id) REFERENCES Game(id) ON DELETE CASCADE
);