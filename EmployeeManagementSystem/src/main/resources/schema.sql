CREATE TABLE  Department (
        DEPARTMENTID	INTEGER NOT NULL AUTO_INCREMENT,
        departmentname  VARCHAR(100) NOT NULL,
        PRIMARY KEY (DEPARTMENTID)
  );

INSERT INTO Department (DEPARTMENTID, departmentname ) VALUES
(1, 'Human Resources'),
(2, 'Back-End Development'),
(3, 'Designing'),
(4, 'Front-End Development'),
(5, 'Testing'),
(6, 'Finance'),
(7, 'Marketing');


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

create sequence hibernate_sequence;

INSERT INTO Employee (id,employee_number, name, dob, gender,emailID,mobile,qualification,
 experience, joining_date, city, state,DEPARTMENTID) VALUES
( 1, 'EMP123','Jhon','17/03/1993','Male','jhon@gmail.com','0431440140','MCA',3,'23/12/2020','Melbourne','Victoria',5);


INSERT INTO Employee (id,employee_number, name, dob, gender,emailID,mobile,qualification,
 experience, joining_date, city, state,DEPARTMENTID) VALUES
( 2, 'EMP128','Jeni','17/03/1993','Male','jeni@gmail.com','0431440140','MCA',3,'23/12/2020','Melbourne','Victoria',5);

INSERT INTO Employee (id,employee_number, name, dob, gender,emailID,mobile,qualification,
 experience, joining_date, city, state,DEPARTMENTID) VALUES
( 3, 'EMP130','Meera','17/03/1993','Male','meera@gmail.com','0431440140','MCA',3,'23/12/2020','Melbourne','Victoria',3);



