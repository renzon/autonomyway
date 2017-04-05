package com.autonomyway.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Expense {
    @Id
    private Long id;
    @Index
    private String name;
    // Value of expense time each time it happens. Ex: if it's monthly rent,
    // You can put this value here
    private long recurrentCash;

    public Expense(String name, long recurrentCash) {
        this(null, name, recurrentCash);
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", recurrentCash=" + recurrentCash +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expense)) return false;

        Expense expense = (Expense) o;

        if (recurrentCash != expense.recurrentCash) return false;
        if (id != null ? !id.equals(expense.id) : expense.id != null) return false;
        return name != null ? name.equals(expense.name) : expense.name == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Generated(hash = 1145096770)
    public Expense(Long id, String name, long recurrentCash) {
        this.id = id;
        this.name = name;
        this.recurrentCash = recurrentCash;
    }

    @Generated(hash = 2061519627)
    public Expense() {
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

    public long getRecurrentCash() {
        return this.recurrentCash;
    }

    public void setRecurrentCash(long recurrentCash) {
        this.recurrentCash = recurrentCash;
    }
}
