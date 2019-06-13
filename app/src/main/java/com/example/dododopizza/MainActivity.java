package com.example.dododopizza;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import static com.example.dododopizza.SampleDBSQLiteHelper.TABLE_NAME;

public class MainActivity extends AppCompatActivity {

    private mainmenu mainmenu;
    EditText Login;
    EditText Password;
    Button Enter;

    ProgressBar progressBar;
    String loginclient;
    String passwordclient;
    registrlayaout registrlayaout;
    Button goreg;
    TextView xt1;
    TextView xt2;
    TextView xt3;
    private AsyncRequestClients asyncRequestClients;
    public SampleDBSQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Login = findViewById(R.id.EditLog);
        Password = findViewById(R.id.EditPass);
        Enter = findViewById(R.id.BtnEnter);
        goreg = findViewById(R.id.newclientreg);
        progressBar = findViewById(R.id.progressBar2);
        xt1 = findViewById(R.id.textView);
        xt2 = findViewById(R.id.textView2);
        xt3 = findViewById(R.id.textView3);
        dbHelper = new SampleDBSQLiteHelper(getApplicationContext());
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        goreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrlayaout = new registrlayaout();
                Intent intent = new Intent(getApplicationContext(), registrlayaout.getClass());
                startActivity(intent);
            }
        });
        final Cursor cursor = database.query(TABLE_NAME, new String[]{dbHelper.COLUMN_LOGIN, dbHelper.COLUMN_PASSWORD}, null, null, null, null, null);
        if (cursor.getCount() == 0) {
            Enter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    Enter.setClickable(false);
                    loginclient = Login.getText().toString().replace(" ", "").replace("(", "").replace(")", "").replace("-", "");
                    passwordclient = Password.getText().toString();
                    asyncRequestClients = new AsyncRequestClients();
                    asyncRequestClients.execute(loginclient,passwordclient);
                }
            });

        }
        else {
            cursor.moveToFirst();
            progressBar.setVisibility(View.VISIBLE);
            xt1.setVisibility(View.INVISIBLE);
            xt2.setVisibility(View.INVISIBLE);
            xt3.setVisibility(View.INVISIBLE);
            goreg.setVisibility(View.INVISIBLE);
            Enter.setVisibility(View.INVISIBLE);
            Login.setVisibility(View.INVISIBLE);
            Password.setVisibility(View.INVISIBLE);
            loginclient = cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_LOGIN));
            passwordclient = cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_PASSWORD));
            asyncRequestClients = new AsyncRequestClients();
            asyncRequestClients.execute(loginclient,passwordclient);

        }


    }

    class AsyncRequestClients extends AsyncTask<String, String, String[]> {
        @Override
        protected String[] doInBackground(String... point) {
            String loginclient = point[0];
            String passwordclient = point[1];
            System.out.println(loginclient);
            System.out.println(passwordclient);
            String[] strings = new String[2];

            strings[0] = "0";
            strings[1] = "";
            String sql = "SELECT * FROM loginclient";
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
                ResultSet resultSet1 = statement.executeQuery(sql);
                while (resultSet1.next()) {
                    if (loginclient.equals(resultSet1.getString("numberclient"))) {
                        if (passwordclient.equals(resultSet1.getString("passwordclient"))) {
                            //i = i + 1;
                            System.out.println("Вход выполнен");
                            strings[0] = "1";
                            strings[1] = resultSet1.getString("idclient");
                            return strings;
                        }
                    }
                }
                resultSet1.close();
                statement.close();
                con.close();
                return strings;
            } catch (Throwable cause) {

                return strings;
            }

        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            if (result[0].equals("1")) {
                SQLiteDatabase database = new SampleDBSQLiteHelper(getApplicationContext()).getWritableDatabase();
                ContentValues values = new ContentValues();
                database.execSQL("delete from " + TABLE_NAME);
                values.put(SampleDBSQLiteHelper.COLUMN_LOGIN, loginclient);
                values.put(SampleDBSQLiteHelper.COLUMN_PASSWORD, passwordclient);
                database.insert(TABLE_NAME, null, values);
                progressBar.setVisibility(View.INVISIBLE);
                Enter.setClickable(true);
                mainmenu = new mainmenu();
                Intent intent = new Intent(getApplicationContext(), mainmenu.getClass());
                intent.putExtra("id", result[1]);
                startActivity(intent);
                xt1.setVisibility(View.VISIBLE);
                xt2.setVisibility(View.VISIBLE);
                xt3.setVisibility(View.VISIBLE);
                goreg.setVisibility(View.VISIBLE);
                Enter.setVisibility(View.VISIBLE);
                Login.setVisibility(View.VISIBLE);
                Password.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.INVISIBLE);
                xt1.setVisibility(View.VISIBLE);
                xt2.setVisibility(View.VISIBLE);
                xt3.setVisibility(View.VISIBLE);
                goreg.setVisibility(View.VISIBLE);
                Enter.setVisibility(View.VISIBLE);
                Login.setVisibility(View.VISIBLE);
                Password.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                Enter.setClickable(true);

            }
        }
    }
}
