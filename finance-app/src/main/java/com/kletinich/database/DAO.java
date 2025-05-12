package com.kletinich.database;

import java.sql.Connection;
import java.util.List;

public interface DAO<T> {
    public static Connection connection = null;
    public abstract void getOne(int id);
    public abstract List<T> get(List<?> s);
    public abstract int insert(T list);
    public abstract void delete(int id);
    public abstract void update(T obj);
}
