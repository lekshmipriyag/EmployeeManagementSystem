CREATE TABLE Employee(
	id		INTEGER PRIMARY KEY,
  	name       	VARCHAR(50) NOT NULL,
  	dob             VARCHAR(10) NOT NULL,
	qualification 	VARCHAR(50) NOT NULL,
	experience	INTEGER NOT NULL,
  	JOINING_DATE	VARCHAR(10) NOT NULL,
	emailID		VARCHAR(50) NOT NULL
);

create sequence hibernate_sequence;
