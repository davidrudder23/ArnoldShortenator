package org.noses.arnoldshortenator.database;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import java.util.Date;

@Entity
@Data
@JsonIgnoreProperties(value = { "urlMapping" })
public class AccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @ManyToOne
    @JsonIgnore
    URLMapping urlMapping;

    Date accessDate;

    String accessedBy;

    String referrer;

}
