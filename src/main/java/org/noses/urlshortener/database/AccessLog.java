package org.noses.urlshortener.database;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.tuple.GeneratedValueGeneration;

import java.util.Date;

@Entity
@Data
public class AccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @ManyToOne
    URLMapping urlMapping;

    Date accessDate;

    String accessedBy;

    String referrer;

}
