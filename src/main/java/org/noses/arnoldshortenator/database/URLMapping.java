package org.noses.arnoldshortenator.database;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import net.minidev.json.annotate.JsonIgnore;

import java.util.Date;
import java.util.List;

@ToString
@Data
@Entity
public class URLMapping {

    @Id
    @NotNull
    @Column
    private String slug;

    private String destinationURL;

    private Date created;

    private Date lastModified;

    private Integer numTimesAccessed;

    private String interpretedDestinationURL;

}
