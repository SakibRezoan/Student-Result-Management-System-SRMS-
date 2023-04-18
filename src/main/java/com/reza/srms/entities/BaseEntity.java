package com.reza.srms.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reza.srms.enums.RecordStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity implements Serializable {

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    protected Date createdAt;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    protected Date updatedAt;
    @Version
    @JsonIgnore
    @Column(name = "record_version")
    private Integer recordVersion;
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @Column(name = "record_status", length = 10)
    private RecordStatus recordStatus;
    @JsonIgnore
    @Column(name = "created_by")
    private Long createdBy;
    @JsonIgnore
    @Column(name = "updated_by")
    private Long updatedBy;

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date();
    }

}
