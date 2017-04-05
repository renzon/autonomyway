package com.autonomyway.model;


import android.content.res.Resources;

import com.autonomyway.R;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Income {
    @Id
    private Long id;
    @Index
    private String name;
    // Value of income time each time it happens. Ex: if it's a salary work,
    // You can put this value here
    private long recurrentCash;
    // Value of income time each time it happens. Ex: if it's a salary work,
    // and you receive monthly, it can be 160 (hours) * 60 minutes
    private long recurrentTime;


    @Convert(converter = TypeConverter.class, columnType = String.class)
    private Type type;

    public String getNameDashType(Resources resources) {
        String typeString = resources.getString(typeToResourceId.get(type));
        return name+" - "+typeString;
    }

    public enum Type {
        WORK("WORK"),
        BUSINESS("BUSINESS");

        private final String dbValue;

        Type(String dbValue) {
            this.dbValue = dbValue;
        }

        String getDbValue() {
            return dbValue;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Income)) return false;

        Income income = (Income) o;

        if (recurrentTime != income.recurrentTime) return false;
        if (recurrentCash != income.recurrentCash) return false;
        if (id != null ? !id.equals(income.id) : income.id != null) return false;
        if (name != null ? !name.equals(income.name) : income.name != null) return false;
        return type == income.type;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Income{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", recurrentTime=" + recurrentTime +
                ", recurrentCash=" + recurrentCash +
                ", type=" + type +
                '}';
    }

    private static final Map<Type, Integer> typeToResourceId;

    static {
        typeToResourceId = new HashMap<>();
        typeToResourceId.put(Type.BUSINESS, R.string.income_type_business);
        typeToResourceId.put(Type.WORK, R.string.income_type_work);
    }

    public static class TypeConverter implements PropertyConverter<Type, String> {
        @Override
        public String convertToDatabaseValue(Type entityProperty) {
            return entityProperty.getDbValue();
        }

        @Override
        public Type convertToEntityProperty(String databaseValue) {
            if (Type.WORK.getDbValue().equals(databaseValue)) {
                return Type.WORK;
            }
            return Type.BUSINESS;
        }
    }


    public Income(String name, long recurrentTime, long recurrentCash, Type type) {
        this.name = name;
        this.type = type;
        this.recurrentTime = recurrentTime;
        this.recurrentCash = recurrentCash;
    }

    @Generated(hash = 98319486)
    public Income(Long id, String name, long recurrentCash, long recurrentTime, Type type) {
        this.id = id;
        this.name = name;
        this.recurrentCash = recurrentCash;
        this.recurrentTime = recurrentTime;
        this.type = type;
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

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
