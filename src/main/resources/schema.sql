CREATE TABLE Employee(
	id		INTEGER PRIMARY KEY,
  	name       	VARCHAR(50) NOT NULL,
  	dob             VARCHAR(10) NOT NULL,
	qualification 	VARCHAR(50) NOT NULL,
	experience	INTEGER NOT NULL,
  	dateofjoining	VARCHAR(10) NOT NULL,
	emailid		VARCHAR(50) NOT NULL
);

create sequence hibernate_sequence;
