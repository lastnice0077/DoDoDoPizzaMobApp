package com.example.dododopizza;


import android.provider.Settings;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class textAsyncRegistrClient {

    public static void main(String...agrs){
        Scanner in = new Scanner(System.in);
        System.out.println("Введите id: ");
        String idclient = in.next();
        System.out.println("Введите логин: ");
        String  loginclient = in.next();
        System.out.println("Введите пароль: ");
        String passwordclient= in.next();
        System.out.println("Введите имя: ");
        String nameClient = in.next();
        Task(idclient,loginclient, passwordclient,nameClient);
        Boolean b = Test(idclient,loginclient, passwordclient,nameClient);
        if (b){
            System.out.println("Тест прошел успешно");
        }
        else {
            System.out.println("Тест не пройден");
        }
    }
    public static void Task(String... string){
        Statement statement = null;
        String idclient = string[0];
        String newlogin = string[1];
        String newpassword = string[2];
        String newname = string[3];
        String url = "jdbc:mysql://db4free.net/pizzado";
        String username = "lastnice";
        String password = "lastnice123";
        try {
            Driver drive = new com.mysql.jdbc.Driver();
        } catch (SQLException e) {
            System.out.println("Unable to load driver class.");
            e.printStackTrace();
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
        }
        try {
            Connection con = (com.mysql.jdbc.Connection) DriverManager.getConnection(url, username, password);
            statement = (Statement) con.createStatement();
            String sqlgo = "INSERT INTO `loginclient` (`idclient`, `numberclient`, `nameclient`, `passwordclient`) VALUES ('" + idclient + "', '" + newlogin + "', '" + newname + "', '" + newpassword + "');";
            statement.executeUpdate(sqlgo);
            statement.close();
            con.close();
        }
        catch (Throwable cause) {
            System.out.println(cause);
        }
    }

    public static boolean Test(String...ars) {
        Statement statement = null;
        String url = "jdbc:mysql://db4free.net/pizzado";
        String username = "lastnice";
        String password = "lastnice123";

        String sqlcount = "SELECT * FROM loginclient WHERE idclient = "+ars[0];
        try
        {
            Driver drive = new com.mysql.jdbc.Driver();
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (com.mysql.jdbc.Connection) DriverManager.getConnection(url, username, password);
            statement = (Statement) con.createStatement();
            ResultSet resultSet1 = statement.executeQuery(sqlcount);
            while(resultSet1.next()) {
                if (ars[1].equals(resultSet1.getString("numberclient"))) {
                    if (ars[2].equals(resultSet1.getString("passwordclient"))) {
                        if (ars[3].equals(resultSet1.getString("nameclient")))  {
                            return true;
                        }
                    }
                }
            }
            resultSet1.close();
            statement.close();
            con.close();
        } catch (
                SQLException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
        }
        return false;
    }

}
