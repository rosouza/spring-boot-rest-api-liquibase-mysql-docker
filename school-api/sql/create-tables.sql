CREATE TABLE t_student
(
    id   BIGINT    NOT NULL AUTO_INCREMENT,
    name CHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE t_course
(
    id   BIGINT    NOT NULL AUTO_INCREMENT,
    name CHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE t_student_course
(
    student_id BIGINT NOT NULL,
    course_id  BIGINT NOT NULL,
    PRIMARY KEY (student_id, course_id)
);