package com.example.dododopizza;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Oformlenie extends AppCompatActivity {
TextView  textCost;
Button GoGo;
EditText editTextAdress;
String costzayavka;
String id_zakaz;
AsyncRequestOformZAkaz akaz;
ProgressBar progressBar;
mainmenu mainmenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oformlenie);
        textCost = findViewById(R.id.textView22);
        GoGo = findViewById(R.id.button);
        progressBar= findViewById(R.id.progressBarofor);
        editTextAdress = findViewById(R.id.editText);
        Intent intent = getIntent();
        id_zakaz = intent.getStringExtra("id");
        costzayavka = intent.getStringExtra("cost");
        textCost.setText(costzayavka+" руб.");
        GoGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoGo.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                akaz = new AsyncRequestOformZAkaz();
                akaz.execute(id_zakaz,costzayavka,editTextAdress.getText().toString());
            }
        });
    }
    class AsyncRequestOformZAkaz extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... point) {
            String Idzak = point[0];
            String cost = point[1];
            String Adress= point[2];
            Statement statement = null;
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
                String sqlgo = "INSERT INTO `OformlenieZak` (`Id_Oform`, `SummZakaza`, `AdressDost`, `id_zakaz`) VALUES (NULL, '" + cost + "','" + Adress+ "','"+ Idzak+"');";
                statement.executeUpdate(sqlgo);
                statement.close();
                con.close();
                return "0";
            } catch (Throwable cause) {
                System.out.println(cause);
                return "1";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equals("0")){
                GoGo.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Ваш заказ принят, ожидайте звонка", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), mainmenu.getClass());
                startActivity(intent);

            }
            else {
                GoGo.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show();
            }


        }
    }
}
