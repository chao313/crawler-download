package demo.spring.boot.demospringboot.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * sql
 */
public class CmdSqlUtils {

    /**
     * 获取创建db并且导入的语句
     */
    public static String getCreateDbAndImportShell(String db) {
        String format = MessageFormat.format("create database {0};use {1};set names utf8;source  /app/sql/db.sql;", db, db);
        return format;
    }


    /**
     * 获取导出创建用户
     * CREATE USER <account>@<host>  IDENTIFIED BY '<passwd>';
     */
    public static String getCreateUserShell(String account, String passwd) {
        String format = MessageFormat.format("CREATE USER ''{0}''@''%'' IDENTIFIED BY ''{1}'';", account, passwd);
        return format;
    }


    /**
     * 获取权限shell
     * GRANT REPLICATION SLAVE ON *.* TO '<account>';
     */
    public static String getPrivilegesShell(String db, String account) {
        String format = MessageFormat.format("GRANT ALL PRIVILEGES ON {0}.* TO ''{1}'';", db, account);
        return format;
    }

    /**
     * 获取刷新权限shell
     * FLUSH PRIVILEGES;#刷新权限
     */
    public static String getFlushPrivilegesShell() {
        return "FLUSH PRIVILEGES;";
    }


    public static List<String> getOrderCmds(String db, String account, String passwd) {
        String createDbAndImportShell = CmdSqlUtils.getCreateDbAndImportShell(db);
        String createUserShell = CmdSqlUtils.getCreateUserShell(account, passwd);
        String privilegesShell = CmdSqlUtils.getPrivilegesShell(db, account);
        String flushPrivilegesShell = CmdSqlUtils.getFlushPrivilegesShell();
        return new ArrayList<>(Arrays.asList(createDbAndImportShell, createUserShell, privilegesShell, flushPrivilegesShell));
    }

}
