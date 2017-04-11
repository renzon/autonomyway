package com.autonomyway.model;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.Date;

@Entity
public class Transfer implements Identifiable {
    @Id
    private Long id;
    @NotNull
    @Index
    private Date date;

    private String detail;

    private long originId;
    private long destinationId;
    private long cash; // in cents
    private long duration; // in minutes
    @Transient
    private Node origin;
    @Transient
    private Node destination;
    @NotNull
    @Convert(converter = TypeConverter.class, columnType = String.class)
    private NodeClassHolder originClassHolder;
    @NotNull
    @Convert(converter = TypeConverter.class, columnType = String.class)
    private NodeClassHolder destinationClassHolder;

    @Keep
    public Transfer(@NotNull Node origin, @NotNull Node destination, @NotNull Date date,
                    long cash, long duration, String detail) {
        this.date = date;
        this.cash = cash;
        this.duration = duration;
        this.detail = detail;
        setOrigin(origin);
        setDestination(destination);

    }


    public Node getOrigin() {
        return origin;
    }

    public Node getDestination() {
        return destination;
    }

    @Keep
    public Transfer(Long id, @NotNull Date date, String detail, long originId, long destinationId,
                    long cash, long duration, @NotNull NodeClassHolder originClass,
                    @NotNull NodeClassHolder destinationClass) {
        this.id = id;
        this.date = date;
        this.detail = detail;
        this.originId = originId;
        this.destinationId = destinationId;
        this.cash = cash;
        this.duration = duration;
        this.originClassHolder = originClass;
        this.destinationClassHolder = destinationClass;
    }

    @Generated(hash = 137042952)
    public Transfer() {
    }

    public void setOrigin(Node origin) {
        this.originId = origin.getId();
        this.originClassHolder = NodeClassHolder.toEnum(origin.getClass());
        this.origin = origin;
    }

    public void setDestination(Node destination) {
        this.destinationId = destination.getId();
        this.destinationClassHolder = NodeClassHolder.toEnum(destination.getClass());
        this.destination = destination;
    }




    public enum NodeClassHolder {
        EXPENSE("Expense", Expense.class),
        INCOME("Income", Income.class),
        WEALTH("Wealth", Wealth.class);
        private final String dbValue;

        public Class<? extends Node> getNodeClass() {
            return nodeClass;
        }

        private final Class<? extends Node> nodeClass;


        NodeClassHolder(String dbValue, Class<? extends Node> nameable) {
            this.nodeClass = nameable;
            this.dbValue = dbValue;
        }

        String getDbValue() {
            return dbValue;
        }

        public static NodeClassHolder toEnum(Class<? extends Node> nameable) {
            for (NodeClassHolder t : NodeClassHolder.values()) {
                if (t.nodeClass.equals(nameable)) {
                    return t;
                }
            }
            throw new RuntimeException("NameableClass not found for " + nameable);
        }
    }


    static class TypeConverter implements PropertyConverter<NodeClassHolder, String> {
        @Override
        public String convertToDatabaseValue(NodeClassHolder entityProperty) {
            return entityProperty.getDbValue();
        }

        @Override
        public NodeClassHolder convertToEntityProperty(String databaseValue) {
            for (NodeClassHolder t : NodeClassHolder.values()) {
                if (t.getDbValue().equals(databaseValue)) {
                    return t;
                }
            }
            throw new RuntimeException("NameableClass not found for " + databaseValue);

        }
    }


    @Override
    public Long getId() {
        return id;
    }


    public Date getDate() {
        return this.date;
    }


    public void setDate(Date date) {
        this.date = date;
    }


    public String getDetail() {
        return this.detail;
    }


    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getOriginId() {
        return this.originId;
    }

    public void setOriginId(long originId) {
        this.originId = originId;
    }

    public long getDestinationId() {
        return this.destinationId;
    }

    public void setDestinationId(long destinationId) {
        this.destinationId = destinationId;
    }

    public NodeClassHolder getOriginClassHolder() {
        return this.originClassHolder;
    }

    public void setOriginClassHolder(NodeClassHolder originClassHolder) {
        this.originClassHolder = originClassHolder;
    }

    public NodeClassHolder getDestinationClassHolder() {
        return this.destinationClassHolder;
    }

    public void setDestinationClassHolder(NodeClassHolder destinationClassHolder) {
        this.destinationClassHolder = destinationClassHolder;
    }

    public long getCash() {
        return this.cash;
    }

    public void setCash(long cash) {
        this.cash = cash;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
