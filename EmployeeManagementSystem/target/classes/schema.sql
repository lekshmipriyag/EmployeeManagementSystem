CREATE TABLE  Department (
        DEPARTMENTID	INTEGER NOT NULL AUTO_INCREMENT,
        departmentname  VARCHAR(100) NOT NULL,
        PRIMARY KEY (DEPARTMENTID)
  );


CREATE TABLE Employee(
	id		        INTEGER NOT NULL AUTO_INCREMENT,
	employee_number  VARCHAR(10) NOT NULL,
  	name       	    VARCHAR(50) NOT NULL,
  	dob             VARCHAR(10) NOT NULL,
  	gender       	VARCHAR(7) NOT NULL,
    emailID		    VARCHAR(50) NOT NULL,
    mobile	        VARCHAR(50) NOT NULL,
	qualification 	VARCHAR(50) NOT NULL,
	experience	    INTEGER NOT NULL,
  	joining_date	VARCHAR(10) NOT NULL,
  	city            VARCHAR(50) NOT NULL,
	state       	VARCHAR(50) NOT NULL,
	PRIMARY KEY (id),
	DEPARTMENTID number(3) references Department(DEPARTMENTID)
);


