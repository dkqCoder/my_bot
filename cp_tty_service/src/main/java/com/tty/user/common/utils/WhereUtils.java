package com.tty.user.common.utils;

import java.util.ArrayList;
import java.util.List;


/**
 * @author pdl
 * @date 2016/9/20
 * @Description
 **/

public class WhereUtils {

    private static StringBuilder where;
    private static List<Object> parms;
    private String hql;

    public static WhereUtils ins(String hql) {
        WhereUtils ret = new WhereUtils();
        ret.hql = hql;
        parms = new ArrayList<Object>();
        where = new StringBuilder(hql);
        return ret;
    }

    public WhereUtils addOrderBy(String hql) {
        where.append(hql);
        return this;
    }

    public WhereUtils addGroupBy(String hql) {
        where.append(hql);
        return this;
    }

    public String getAllSql() {
        return where.toString();
    }

    public String getCountSql(String field) {
        return countSql(getAllSql(), field);
    }

    public String getCountSql() {
        return countSql(getAllSql(), null);
    }

    private String countSql(String hql, String field) {
        final String tag = "FROM ";

        int pos = hql.toUpperCase().indexOf(tag);
        if (pos < 0) {
            throw new IllegalArgumentException("hql must does not match [select ... from ...] or [from ...]: " + hql);
        }
        if (isEmpty(field)) {
            return "select count(*) " + hql.substring(pos);
        }
        return "select count(" + field + ")" + hql.substring(pos);
    }


    private boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public List<Object> getParms() {
        return parms;
    }

    public WhereUtils contains(String field, Object par) {
        if (nullEmpty(par)) {
            return this;
        }
        where.append(" and ").append( field ).append(" like ? ");
        parms.add("%"+par+"%");
        return this;
    }

    public WhereUtils endWith(String field, Object par) {
        if (nullEmpty(par)) {
            return this;
        }
        where.append(" and ").append(field).append(" like ? ");
        parms.add("%"+par);
        return this;
    }

    public WhereUtils startWith(String field, Object par) {
        if (nullEmpty(par)) {
            return this;
        }
        where.append(" and ").append(field).append(" like ? ");
        parms.add(par+"%");
        return this;
    }

    public WhereUtils andEq(String field, Object par) {
        if (nullEmpty(par)) {
            return this;
        }
        where.append(" and ").append(field).append(" = ?");
        parms.add(par);
        return this;
    }

    public WhereUtils andGe(String field, Object par) {
        if (nullEmpty(par)) {
            return this;
        }
        where.append(" and ").append(field).append(" >= ?");
        parms.add(par);
        return this;

    }

    public WhereUtils andGt(String field, Object par) {
        if (nullEmpty(par)) {
            return this;
        }
        where.append(" and ").append(field).append(" > ? ");
        parms.add(par);
        return this;
    }

    public WhereUtils andLe(String field, Object par) {
        if (nullEmpty(par)) {
            return this;
        }
        where.append(" and ").append(field).append(" <= ? ");
        parms.add(par);
        return this;
    }

    public WhereUtils andLt(String field, Object par) {
        if (nullEmpty(par)){
            return this;
        }

        where.append(" and ").append(field).append(" < ? ");
        parms.add(par);
        return this;
    }

    public WhereUtils andNe(String field, Object par) {
        if (nullEmpty(par)) {
            return this;
        }
        where.append(" and ").append(field).append(" <> ? ");
        parms.add(par);
        return this;
    }


    private boolean nullEmpty(Object par) {
        if (par == null) {
            return true;
        }
        if (par instanceof String) {
            String spar = (String) par;
            if (spar.isEmpty()) {
                return true;
            }
        }

        return false;
    }

}
