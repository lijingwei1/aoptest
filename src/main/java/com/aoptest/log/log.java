package com.aoptest.log;
import org.springframework.dao.*;
import org.springframework.jdbc.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class log {

    public static <T,A> boolean isInstance(A obj, Class<T> calzz) {
        if (obj == null) {
            return false;
        }
        try {
            T t = (T) obj;
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        UncategorizedSQLException u = new UncategorizedSQLException("232323","select * form a" ,new SQLException());
        System.out.println(NonTransientDataAccessException.class.isInstance(u));
    }
}
