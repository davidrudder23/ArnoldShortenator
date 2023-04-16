package org.noses.arnoldshortenator.database;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class AccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    URLMapping urlMapping;

    Date accessDate;

    String accessedBy;

    String referrer;

}
