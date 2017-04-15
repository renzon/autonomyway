package com.autonomyway.model;


import android.content.res.Resources;

import com.autonomyway.R;
import com.autonomyway.business.transfer.TransferVisitor;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.HashMap;
import java.util.Map;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Income implements Node {
    @Id
    private Long id;
    @Index
    private String name;
    // Value of income time each time it happens. Ex: if it's a salary work,
    // You can put this value here
    private long recurrentCash;
    // Value of income time each time it happens. Ex: if it's a salary work,
    // and you receive monthly, it can be 160 (hours) * 60 minutes
    private long recurrentDuration;


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

        if (recurrentDuration != income.recurrentDuration) return false;
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
                ", recurrentDuration=" + recurrentDuration +
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

    static class TypeConverter implements PropertyConverter<Type, String> {
        @Override
        public String convertToDatabaseValue(Type entityProperty) {
            return entityProperty.getDbValue();
        }

        @Override
        public Type convertToEntityProperty(String databaseValue) {
            for (Type t:Type.values()){
                if(t.getDbValue().equals(databaseValue)){
                    return t;
                }
            }
            throw new RuntimeException("NameableClass not found for "+databaseValue);

        }
    }


    public Income(String name, long recurrentDuration, long recurrentCash, Type type) {
        this.name = name;
        this.type = type;
        this.recurrentDuration = recurrentDuration;
        this.recurrentCash = recurrentCash;
    }

    @Keep
    public Income(Long id, String name, long recurrentCash, long recurrentDuration, Type type) {
        this.id = id;
        this.name = name;
        this.recurrentCash = recurrentCash;
        this.recurrentDuration = recurrentDuration;
        this.type = type;
    }

    @Keep
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

    @Override
    public void handleTransferCreationAsOrigin(Transfer transfer) {

    }

    @Override
    public void handleTransferCreationAsDestination(Transfer transfer) {

    }

    @Override
    public boolean hasRecurrentValues() {
        return true;
    }


    public void setName(String name) {
        this.name = name;
    }
    @Override
    public long getRecurrentDuration() {
        return this.recurrentDuration;
    }



    public void setRecurrentDuration(long recurrentDuration) {
        this.recurrentDuration = recurrentDuration;
    }
    @Override
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

    @Override
    public void acceptAsOrigin(TransferVisitor visitor, long transferCash, long transferDuration) {
        visitor.visitAsOrigin(this, transferCash, transferDuration);
    }

    @Override
    public void acceptAsDestination(TransferVisitor visitor, long transferCash, long transferDuration) {
        visitor.visitAsDestination(this, transferCash, transferDuration);
    }
}
