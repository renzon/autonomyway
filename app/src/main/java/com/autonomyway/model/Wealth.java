package com.autonomyway.model;

import com.autonomyway.R;
import com.autonomyway.business.transfer.TransferVisitor;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

@Entity
public class Wealth implements Node {
    @Id
    private Long id;
    @Index
    private String name;
    private long initialBalance;

    private long balance;

    public Wealth(String name, long initialBalance) {
        this(null, name, initialBalance, initialBalance);
    }

    @Override
    public String toString() {
        return "Wealth{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", initialBalance=" + initialBalance +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Wealth)) return false;

        Wealth wealth = (Wealth) o;

        if (initialBalance != wealth.initialBalance) return false;
        if (balance != wealth.balance) return false;
        if (id != null ? !id.equals(wealth.id) : wealth.id != null) return false;
        return name != null ? name.equals(wealth.name) : wealth.name == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public void increaseBalance(long delta) {
        balance += delta;
    }

    @Generated(hash = 1773132447)
    public Wealth(Long id, String name, long initialBalance, long balance) {
        this.id = id;
        this.name = name;
        this.initialBalance = initialBalance;
        this.balance = balance;
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

    public long getBalance() {
        return this.balance;
    }


    @Override
    public void handleTransferCreationAsOrigin(Transfer transfer) {
        decreaseBalance(transfer.getCash());
    }

    @Override
    public void handleTransferCreationAsDestination(Transfer transfer) {
        increaseBalance(transfer.getCash());
    }

    @Override
    public boolean hasRecurrentValues() {
        return false;
    }

    @Override
    public long getRecurrentCash() {
        return 0;
    }

    @Override
    public long getRecurrentDuration() {
        return 0;
    }

    private void decreaseBalance(long cash) {
        increaseBalance(-cash);
    }

    @Override
    public void acceptAsOrigin(TransferVisitor visitor, long transferCash, long transferDuration) {
        visitor.visitAsOrigin(this, transferCash, transferDuration);
    }

    @Override
    public void acceptAsDestination(TransferVisitor visitor, long transferCash, long transferDuration) {
        visitor.visitAsDestination(this, transferCash, transferDuration);
    }

    @Override
    public int getColorId() {
        return R.color.colorWealth;
    }

    void setBalance(long balance) {
        this.balance = balance;
    }

}
