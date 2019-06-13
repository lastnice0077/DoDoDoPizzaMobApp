package com.example.dododopizza;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.dododopizza.SampleDBSQLiteHelper.TABLE_NAME;

public class korzina extends AppCompatActivity {
    TextView costZakaz;
    ListView listView;
    String id_zakaz;
    int Cost = 0;
    AsyncRequestCostZakaz zakaz;
    Button oformit;
    Oformlenie Oformlenie;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korzina);
        costZakaz = findViewById(R.id.costzakaztext);
        listView = findViewById(R.id.list);
        progressBar = findViewById(R.id.progressBarkorzina);
        oformit = findViewById(R.id.oformit);
        Intent intent = getIntent();
        id_zakaz = intent.getStringExtra("idzak");
        zakaz = new AsyncRequestCostZakaz();
        listView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        oformit.setVisibility(View.INVISIBLE);
        zakaz.execute(id_zakaz);
        oformit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Oformlenie = new Oformlenie();
                Intent intent = new Intent(getApplicationContext(), Oformlenie.getClass());
                intent.putExtra("id", id_zakaz);
                intent.putExtra("cost", costZakaz.getText().toString().replace(" руб.",""));
                startActivity(intent);
            }
        });
    }

    class AsyncRequestCostZakaz extends AsyncTask<String, String, ArrayAdapter<String>> {
        @Override
        protected  ArrayAdapter<String> doInBackground(String... point) {
            String idZakazaCost = point[0];
            String sql = "SELECT * FROM korzina Where id_zakaz = "+idZakazaCost;
            String sqld = "SELECT * FROM korzina Where id_zakaz = "+idZakazaCost+" AND id_menu = ";
            Statement statement = null;
            String sqlmenu = "SELECT * FROM `Menu` WHERE Id_menu = ";
            String sqldopmenu = "SELECT * FROM `dopmenu` WHERE id_dopmenu = ";
            ArrayList<String> ar = new ArrayList<String>();
            ArrayAdapter<String> adapter = null;
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
                ResultSet resultSet2 ;
                ResultSet resultSet4;
                List<Integer> x = new ArrayList();
                List<Integer> y = new ArrayList();

                   while (resultSet1.next()) {
                       if (!x.contains(resultSet1.getInt("id_menu"))) {
                           x.add(resultSet1.getInt("id_menu"));
                       }
                       if (resultSet1.getInt("id_dopmenu") != 100) {
                           y.add(resultSet1.getInt("id_dopmenu"));
                       }

                   }

                for(int i = 0; i < x.size(); i++) {
                System.out.println("main"+x.get(i));
                }
                for(int i = 0; i < y.size(); i++) {
                    System.out.println("dop"+y.get(i));
                }
                System.out.println("Размер Y: "+y.size());
               for(int i = 0; i < x.size(); i++) {
                   resultSet2 = statement.executeQuery(sqlmenu + String.valueOf(x.get(i)));
                   resultSet2.first();
                       ar.add(resultSet2.getString("namepunkt") + " - " + String.valueOf(resultSet2.getInt("cost"))+" Руб. ");
                       System.out.println(resultSet2.getString("namepunkt"));
                       Cost = Cost + resultSet2.getInt("cost");
                       resultSet2.close();
               }

                for (int i = 0; i < y.size();) {
                        int t = y.get(i);
                        resultSet4 = statement.executeQuery(sqldopmenu + String.valueOf(t));
                        resultSet4.first();
                        ar.add(resultSet4.getString("name") + " - " + String.valueOf(resultSet4.getInt("cost")) + " Руб. ");
                        System.out.println(resultSet4.getString("name"));
                        Cost = Cost + resultSet4.getInt("cost");
                        i++;
                        if(i==y.size()){
                            break;
                        }
                }
                resultSet1.close();
                statement.close();
                con.close();

            } catch (Throwable cause) {
                System.out.println(cause);
            }
            adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, ar);
            return adapter;
        }

        @Override
        protected void onPostExecute(ArrayAdapter<String> result) {
            super.onPostExecute(result);
            listView.setAdapter(result);
            listView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            oformit.setVisibility(View.VISIBLE);
            costZakaz.setText(String.valueOf(Cost)+" руб.");
        }
    }

}
