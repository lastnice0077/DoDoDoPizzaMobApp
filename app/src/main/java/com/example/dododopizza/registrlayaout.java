package com.example.dododopizza;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class registrlayaout extends AppCompatActivity {
    EditText newLoginNum;
    EditText newNameClient;
    EditText newPassword;
    Button goRegistrClient;
    ProgressBar progressBar;
    AsyncRegistrClient asyncRegistrClient;
    MainActivity mainmenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrlayout);
        newLoginNum = findViewById(R.id.newloginNumber);
        newPassword = findViewById(R.id.newpassword);
        newNameClient = findViewById(R.id.newNameClient);
        goRegistrClient = findViewById(R.id.goregistr);
        progressBar = findViewById(R.id.progressBar3);
        goRegistrClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newLoginNum.getText().toString().replace(" ", "").replace("(", "").replace(")", "").replace("-", "").length() != 12){
                    Toast.makeText(getApplicationContext(), "Некорректный номер телефона", Toast.LENGTH_SHORT).show();
                }
                else{
                    if((newPassword.getText().toString().equals(""))){
                        Toast.makeText(getApplicationContext(), "Заполните поле Пароль", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(newNameClient.getText().toString().equals("")){
                            Toast.makeText(getApplicationContext(), "Заполните поле Имя", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            progressBar.setVisibility(View.VISIBLE);
                            asyncRegistrClient = new AsyncRegistrClient();
                            asyncRegistrClient.execute(newLoginNum.getText().toString().replace(" ", "").replace("(", "").replace(")", "").replace("-", ""),newPassword.getText().toString(),newNameClient.getText().toString());
                        }
                    }
                }
            }
        });
    }
    public class AsyncRegistrClient extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... string) {

            String sql = "SELECT * FROM loginclient";
            Statement statement = null;
            String newlogin = string[0];
            String newpassword = string[1];
            String newname = string[2];
            String url = "jdbc:mysql://db4free.net/pizzado";
            String username = "lastnice";
            String password = "lastnice123";
            int i = 0;
            try {
                Driver drive = new com.mysql.jdbc.Driver();
            } catch (SQLException e) {

                e.printStackTrace();
            }

            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {

                e.printStackTrace();
            }
            try {
                Connection con = (com.mysql.jdbc.Connection) DriverManager.getConnection(url, username, password);
                statement = (Statement) con.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                resultSet.last();
                int rowCount = resultSet.getRow();
                ResultSet resultSet1 = statement.executeQuery(sql);

                while(resultSet1.next())
                {
                    if (newlogin.equals(resultSet1.getString("numberclient")))
                    {
                        i = i+1;
                    }
                }
                if (i==0)
                {
                    String sqlgo = "INSERT INTO `loginclient` (`idclient`, `numberclient`, `nameclient`, `passwordclient`) VALUES ('" + rowCount + "', '" + newlogin + "', '" + newname + "', '" + newpassword + "');";
                    statement.executeUpdate(sqlgo);
                    return "1";
                }
                resultSet1.close();
                statement.close();
                con.close();
                return"0";
            }
            catch (Throwable cause) {
                System.out.println(cause);
                return "2";

            }

        }

        @Override
        protected void onPostExecute( String s) {
            super.onPostExecute(s);
            if (s.equals("2")){
                Toast.makeText(getApplicationContext(),"Ошибка, повторите еще раз", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                goRegistrClient.setClickable(true);
            }
            if (s.equals("0")){
                Toast.makeText(getApplicationContext(),"Такой пользователь уже зарегистрирован", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                goRegistrClient.setClickable(true);
            }
            if(s.equals("1")) {
                progressBar.setVisibility(View.INVISIBLE);
                goRegistrClient.setClickable(true);
                Toast.makeText(getApplicationContext(), "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                mainmenu = new MainActivity();
                Intent intent = new Intent(getApplicationContext(), mainmenu.getClass());
                startActivity(intent);
            }
            }


        }
    }

