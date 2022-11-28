package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Author: Jacob Visser
 * <p>
 * Dit is wat het programma doet.
 */
@Entity @Getter @Setter
public class Course {

    @Id
    @GeneratedValue
    private Long courseId;

    private String naam;
    private String studierichting;



}
