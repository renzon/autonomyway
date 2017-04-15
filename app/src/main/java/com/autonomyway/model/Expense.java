package com.autonomyway.model;

import com.autonomyway.business.transfer.TransferVisitor;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

@Entity
public class Expense implements Node {
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

    @Override
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void handleTransferCreationAsOrigin(Transfer transfer) {

    }

    @Override
    public void handleTransferCreationAsDestination(Transfer transfer) {

    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public long getRecurrentCash() {
        return this.recurrentCash;
    }

    @Override
    public long getRecurrentDuration() {
        return 0;
    }


    public void setRecurrentCash(long recurrentCash) {
        this.recurrentCash = recurrentCash;
    }


    @Override
    public boolean hasRecurrentValues() {
        return true;
    }

    @Override
    public void acceptAsOrigin(TransferVisitor visitor, long transferCash, long transferDuration) {
        visitor.visitAsOrigin(this, transferCash, transferDuration);
    }

    @Override
    public void acceptAsDestination(TransferVisitor visitor, long transferCash, long transferDuration) {
        visitor.visitAsDestination(this, transferCash, transferDuration);
    }

}
