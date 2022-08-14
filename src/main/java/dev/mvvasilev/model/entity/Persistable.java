package dev.mvvasilev.model.entity;

import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "app")
public interface Persistable {

    long getId();

}
