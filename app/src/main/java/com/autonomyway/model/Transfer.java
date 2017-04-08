package com.autonomyway.model;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
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
    @NotNull
    @Convert(converter = TypeConverter.class, columnType = String.class)
    private NameableClass originClass;
    @NotNull
    @Convert(converter = TypeConverter.class, columnType = String.class)
    private NameableClass destinationClass;

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

    @Generated(hash = 1522367525)
    public Transfer(Long id, @NotNull Date date, String detail, long originId, long destinationId,
                    long cash, long duration, @NotNull NameableClass originClass,
                    @NotNull NameableClass destinationClass) {
        this.id = id;
        this.date = date;
        this.detail = detail;
        this.originId = originId;
        this.destinationId = destinationId;
        this.cash = cash;
        this.duration = duration;
        this.originClass = originClass;
        this.destinationClass = destinationClass;
    }

    @Generated(hash = 137042952)
    public Transfer() {
    }

    public void setOrigin(Node origin) {
        this.originId = origin.getId();
        this.originClass = NameableClass.toEnum(origin.getClass());
    }

    public void setDestination(Node destination) {
        this.destinationId = destination.getId();
        this.destinationClass = NameableClass.toEnum(destination.getClass());
    }


    public enum NameableClass {
        EXPENSE("Expense", Expense.class),
        INCOME("Income", Income.class),
        WEALTH("Wealth", Wealth.class);
        private final String dbValue;
        private final Class<? extends Node> nameable;


        NameableClass(String dbValue, Class<? extends Node> nameable) {
            this.nameable = nameable;
            this.dbValue = dbValue;
        }

        String getDbValue() {
            return dbValue;
        }

        public static NameableClass toEnum(Class<? extends Node> nameable) {
            for (NameableClass t : NameableClass.values()) {
                if (t.nameable.equals(nameable)) {
                    return t;
                }
            }
            throw new RuntimeException("NameableClass not found for " + nameable);
        }
    }


    static class TypeConverter implements PropertyConverter<NameableClass, String> {
        @Override
        public String convertToDatabaseValue(NameableClass entityProperty) {
            return entityProperty.getDbValue();
        }

        @Override
        public NameableClass convertToEntityProperty(String databaseValue) {
            for (NameableClass t : NameableClass.values()) {
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

    public NameableClass getOriginClass() {
        return this.originClass;
    }

    public void setOriginClass(NameableClass originClass) {
        this.originClass = originClass;
    }

    public NameableClass getDestinationClass() {
        return this.destinationClass;
    }

    public void setDestinationClass(NameableClass destinationClass) {
        this.destinationClass = destinationClass;
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
