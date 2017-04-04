package com.autonomyway.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Wealth {
    @Id
    private Long id;
    @Index
    private String name;
    private long initialBalance;

    public Wealth(String name, long initialBalance) {
        this(null, name, initialBalance);
    }

    @Override
    public String toString() {
        return "Wealth{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", initialBalance=" + initialBalance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Wealth)) return false;

        Wealth wealth = (Wealth) o;

        if (initialBalance != wealth.initialBalance) return false;
        if (id != null ? !id.equals(wealth.id) : wealth.id != null) return false;
        return name != null ? name.equals(wealth.name) : wealth.name == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Generated(hash = 304742747)
    public Wealth(Long id, String name, long initialBalance) {
        this.id = id;
        this.name = name;
        this.initialBalance = initialBalance;
    }

    @Generated(hash = 1762457066)
    public Wealth() {
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
    public long getInitialBalance() {
        return this.initialBalance;
    }
    public void setInitialBalance(long initialBalance) {
        this.initialBalance = initialBalance;
    }
}
