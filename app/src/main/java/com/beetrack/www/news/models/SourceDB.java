package com.beetrack.www.news.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class SourceDB {

    @Id(autoincrement = true)
    private Long id;
    private String idSource;
    private String name;

    public SourceDB() {
    }

    @Generated(hash = 386409240)
    public SourceDB(Long id, String idSource, String name) {
        this.id = id;
        this.idSource = idSource;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdSource() {
        return idSource;
    }

    public void setIdSource(String idSource) {
        this.idSource = idSource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
