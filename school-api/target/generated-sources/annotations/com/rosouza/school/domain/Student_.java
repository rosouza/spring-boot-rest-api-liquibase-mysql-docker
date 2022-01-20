package com.rosouza.school.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Student.class)
public abstract class Student_ {

	public static volatile ListAttribute<Student, Course> courses;
	public static volatile SingularAttribute<Student, String> name;
	public static volatile SingularAttribute<Student, Long> id;

	public static final String COURSES = "courses";
	public static final String NAME = "name";
	public static final String ID = "id";

}

