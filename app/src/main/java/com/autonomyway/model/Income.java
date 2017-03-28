package com.autonomyway.model;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Income {
    @Id
    private Long id;
    @Index
    private String name;
    private long totalTime; // minutes
    private long totalCash; // cents
    // Value of income time each time it happens. Ex: if it's a salary work,
    // and you receive monthly, it can be 160 (hours) * 60 minutes
    private long recurrentTime;
    // Value of income time each time it happens. Ex: if it's a salary work,
    // You can put this value here
    private long recurrentCash;

    public Income(String name, long recurrentTime, long recurrentCash) {
        this.name = name;
        this.totalTime = 0;
        this.totalCash = 0;
        this.recurrentTime = recurrentTime;
        this.recurrentCash = recurrentCash;
    }

    @Generated(hash = 468178786)
    public Income(Long id, String name, long totalTime, long totalCash,
            long recurrentTime, long recurrentCash) {
        this.id = id;
        this.name = name;
        this.totalTime = totalTime;
        this.totalCash = totalCash;
        this.recurrentTime = recurrentTime;
        this.recurrentCash = recurrentCash;
    }

    @Generated(hash = 1009272208)
    public Income() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTotalTime() {
        return this.totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public long getTotalCash() {
        return this.totalCash;
    }

    public void setTotalCash(long totalCash) {
        this.totalCash = totalCash;
    }

    public long getRecurrentTime() {
        return this.recurrentTime;
    }

    public void setRecurrentTime(long recurrentTime) {
        this.recurrentTime = recurrentTime;
    }

    public long getRecurrentCash() {
        return this.recurrentCash;
    }

    public void setRecurrentCash(long recurrentCash) {
        this.recurrentCash = recurrentCash;
    }

}
