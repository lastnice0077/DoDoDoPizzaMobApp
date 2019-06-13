package com.example.dododopizza;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.dododopizza.SampleDBSQLiteHelper.TABLE_NAME;

public class mainmenu extends FragmentActivity {
  public  String idclient;
  AsyncRequestCountZakazID asyncRequestCountZakazID;
  ScrollView scrollView;
    Button Korzina;
    Button Margarita;
    Button FourCheese;
    Button Pepperoni;
    Button DoDoDoPizza;
    Button CocaCola;
    Button Fanta;
    Button Sprite;
    Button Potfree;
    Button BBQ;
    Button CheeseSous;
    String ID_ZAKAZ = "-1";
    korzina korzinar;
    ProgressBar progressBar;

    ExampleButtonChoose two;
    public BottomSheetListener mListener;
    String[] vibor = new String[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        Intent intent = getIntent();
        progressBar = findViewById(R.id.progressBarMenu);
        scrollView = findViewById(R.id.scrollView2);
        idclient = intent.getStringExtra("id");
        if (ID_ZAKAZ.equals("-1")){
            scrollView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            asyncRequestCountZakazID = new AsyncRequestCountZakazID();
            asyncRequestCountZakazID.execute(idclient);
        }
        Korzina = findViewById(R.id.gokorzina);
        Margarita = findViewById(R.id.margarita);
        FourCheese = findViewById(R.id.chetirecura);
        Pepperoni = findViewById(R.id.pepperoni);
        DoDoDoPizza = findViewById(R.id.dododo);
        CocaCola = findViewById(R.id.cocacola);
        Fanta = findViewById(R.id.fanta);
        Sprite = findViewById(R.id.sprite);
        Potfree = findViewById(R.id.kartoshkafree);
        BBQ = findViewById(R.id.bbq);
        CheeseSous = findViewById(R.id.cheesesous);
        Margarita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                two = new ExampleButtonChoose();
                two.id_menu ="0";
                two.imageSourse = String.valueOf(R.drawable.margarita);
                two.nameMenutext ="Маргарита - 120 р.";
                two.opicanietext = "Итальянские травы, томатный соус, моцарелла и томаты";
                two.idClient = idclient;
                two.Id_zakaz = ID_ZAKAZ;
                two.show(getSupportFragmentManager(), "example_botton_Sheet");
            }
        });
        FourCheese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                two = new ExampleButtonChoose();
                two.id_menu ="1";
                two.imageSourse = String.valueOf(R.drawable.chetiresira);
                two.nameMenutext ="Четыре сыра - 150 р.";
                two.opicanietext = "Сливочный соус, сыр блю чиз, моцарелла и смесь сыров чеддер и пармезан";
                two.idClient = idclient;
                two.Id_zakaz = ID_ZAKAZ;
                two.show(getSupportFragmentManager(), "example_botton_Sheet");
            }
        });
        Pepperoni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                two = new ExampleButtonChoose();
                two.id_menu = "2";
                two.imageSourse = String.valueOf(R.drawable.peperonni);
                two.nameMenutext ="Пепперони - 200 р.";
                two.opicanietext = "Пикантная пепперони, томатный соус и моцарелла";
                two.idClient = idclient;
                two.Id_zakaz = ID_ZAKAZ;
                two.show(getSupportFragmentManager(), "example_botton_Sheet");
            }
        });
        DoDoDoPizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                two = new ExampleButtonChoose();
                two.id_menu = "3";
                two.imageSourse = String.valueOf(R.drawable.dododopizza);
                two.nameMenutext ="ДоДоДо Пицца - 300 р.";
                two.opicanietext = "Ветчина, фарш из говядины, пикантная пепперони, томатный соус, шампиньоны , сладкий перец, красный лук , моцарелла и маслины";
                two.idClient = idclient;
                two.Id_zakaz = ID_ZAKAZ;
                two.show(getSupportFragmentManager(), "example_botton_Sheet");
            }
        });
        CocaCola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                two = new ExampleButtonChoose();
                two.id_menu ="4";
                two.imageSourse = String.valueOf(R.drawable.cola);
                two.nameMenutext ="Coca-cola 0.5л - 70 р.";
                two.opicanietext = "Coca-cola 0.5л - 70 р.";
                two.idClient = idclient;
                two.Id_zakaz = ID_ZAKAZ;
                two.show(getSupportFragmentManager(), "example_botton_Sheet");
            }
        });
        Fanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                two = new ExampleButtonChoose();
                two.id_menu ="5";
                two.imageSourse = String.valueOf(R.drawable.fanta);
                two.nameMenutext ="Fanta 0.5л - 70 р.";
                two.opicanietext = "Fanta 0.5л - 70 р.";
                two.idClient = idclient;
                two.Id_zakaz = ID_ZAKAZ;
                two.show(getSupportFragmentManager(), "example_botton_Sheet");
            }
        });
       Sprite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                two = new ExampleButtonChoose();
                two.id_menu ="6";
                two.imageSourse = String.valueOf(R.drawable.sprite);
                two.nameMenutext ="Sprite 0.5л - 70 р.";
                two.opicanietext = "Sprite 0.5л - 70 р.";
                two.idClient = idclient;
                two.Id_zakaz = ID_ZAKAZ;
                two.show(getSupportFragmentManager(), "example_botton_Sheet");
            }
        });
       Potfree.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               two = new ExampleButtonChoose();
               two.id_menu ="7";
               two.imageSourse = String.valueOf(R.drawable.kaptoh);
               two.nameMenutext ="Картошка-free - 50 р.";
               two.opicanietext = "Картошка-free - 50 р.";
               two.idClient = idclient;
               two.Id_zakaz = ID_ZAKAZ;
               two.show(getSupportFragmentManager(), "example_botton_Sheet");
           }
       });
       BBQ.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               two = new ExampleButtonChoose();
               two.id_menu ="8";
               two.imageSourse = String.valueOf(R.drawable.bbq);
               two.nameMenutext ="BBQ-соус - 20 р.";
               two.opicanietext = "BBQ-соус - 20 р.";
               two.idClient = idclient;
               two.Id_zakaz = ID_ZAKAZ;
               two.show(getSupportFragmentManager(), "example_botton_Sheet");
           }
       });
       CheeseSous.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               two = new ExampleButtonChoose();
               two.id_menu ="9";
               two.imageSourse = String.valueOf(R.drawable.cheesecous);
               two.nameMenutext ="Сырный соус - 20 р.";
               two.opicanietext = "Сырный соус - 20 р.";
               two.idClient = idclient;
               two.Id_zakaz = ID_ZAKAZ;
               two.show(getSupportFragmentManager(), "example_botton_Sheet");
           }
       });
       Korzina.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               korzinar = new korzina();
               Intent intent = new Intent(getApplicationContext(), korzinar.getClass());
               intent.putExtra("idzak", ID_ZAKAZ);
               startActivity(intent);
           }
       });

    }
    public  interface BottomSheetListener{
        void onButtonClicked(String[] text);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        try {
            mListener = (BottomSheetListener) fragment;
        }
        catch (ClassCastException e){
            //throw new ClassCastException(fragment.toString()+"must implement BottomSheetListener");
        }
    }
    class AsyncRequestCountZakazID extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... point) {
            String IdCl = point[0];
            String count = "-1";
            String sql = "SELECT * FROM Zakaz";
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
                ResultSet resultSet = statement.executeQuery(sql);
                resultSet.last();
                int rowCount = resultSet.getRow();
                String sqlgo = "INSERT INTO `Zakaz` (`id_zakaz`, `id_client`) VALUES('" + String.valueOf(rowCount) + "','" + IdCl + "');";
                statement.executeUpdate(sqlgo);
                count = String.valueOf(rowCount);
                resultSet.close();
                statement.close();
                con.close();
                return count;
            } catch (Throwable cause) {
                System.out.println(cause);
                return count;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ID_ZAKAZ = result;
            scrollView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

}
