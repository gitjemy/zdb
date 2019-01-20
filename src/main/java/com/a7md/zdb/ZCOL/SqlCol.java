package com.a7md.zdb.ZCOL;

import com.a7md.zdb.Link;
import com.a7md.zdb.Query.Select;
import com.a7md.zdb.Query.ZQ.Equal;
import com.a7md.zdb.RowTypes.types.ZSqlRow;
import com.a7md.zdb.ZTable;
import com.a7md.zdb.properties.WritableProperty;

import java.sql.ResultSet;
import java.sql.SQLException;

abstract public class SqlCol<E extends ZSqlRow, V> {

    public final String name;
    protected final WritableProperty<E, V> mProperty;
    final boolean not_null;
    public ZTable<E> mtable;

    protected SqlCol(String Name, WritableProperty<E, V> mProperty, boolean not_null) {
        this.name = Name;
        this.mProperty = mProperty;
        this.not_null = not_null;
    }

    protected SqlCol(String Name, WritableProperty<E, V> mProperty) {
        this.name = Name;
        this.mProperty = mProperty;
        this.not_null = false;
    }

    public Key toDbKey(E e) {
        V value = mProperty.getValue(e);
        return new Key<>(name, value);
    }

    public void assign(E e, ResultSet resultSet) throws Exception {
        V v = get(resultSet);
        mProperty.setValue(e, v);
    }

    public void setMtable(ZTable<E> mtable) {
        this.mtable = mtable;
    }

    public boolean exist(V val) throws Exception {
        return Select.exist(equal(val));
    }

    abstract public Equal equal(V val);

    abstract protected void create(CreateTable CreateTable, Link link);

    abstract public V get(ResultSet resultSet) throws SQLException;

}