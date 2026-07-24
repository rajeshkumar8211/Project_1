create table cgpa(stud_name varchar(30),
register_no varchar(30) ,
foreign key (register_no) references gpa(register_no),cgpa double);