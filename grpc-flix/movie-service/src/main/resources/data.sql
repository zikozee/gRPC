DROP TABLE IF EXISTS movie;
CREATE TABLE movie as SELECT * FROM CSVREAD('classpath:movie.csv')