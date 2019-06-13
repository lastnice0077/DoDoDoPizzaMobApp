package com.example.dododopizza;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.BottomSheetDialogFragment;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExampleButtonChoose extends BottomSheetDialogFragment implements mainmenu.BottomSheetListener {
    public String id_menu;
    AsyncRequestGoVKorziny asyncRequestGoVKorziny;
    String id_dopmenuchees ;
    String id_dopmenutomato;
    String id_dopmenuchiken;
    public TextView nameMenu;
    String nameMenutext;
    String idClient;
    String imageSourse;
    ProgressBar progressBarChoose;
    public String Id_zakaz;
    public ImageView imageView;
    CheckBox checktomato;
    CheckBox checkcheese;
    CheckBox checkchiken;
    Button VKorziny;
    String idTomato="";
    String idchiken="";
    String idCheese ="";
    String opicanietext;
    TextView opicanie;
    TextView tx1;
    TextView tx2;
    TextView tx3;
    TextView tx4;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.buttonshhetdialog,container,false);
        nameMenu = v.findViewById(R.id.textnameandsumm);
        progressBarChoose = v.findViewById(R.id.progressBarChoose);
        tx1 = v.findViewById(R.id.doptext);
        tx2 = v.findViewById(R.id.chikentext);
        tx3 = v.findViewById(R.id.tomatotext);
        tx4 = v.findViewById(R.id.textsir);
        checkcheese = v.findViewById(R.id.checkcheese);
        checkchiken = v.findViewById(R.id.checkchiken);
        checktomato = v.findViewById(R.id.checktomato);
        imageView = v.findViewById(R.id.imageView);
        opicanie = v.findViewById(R.id.opicanie);
        VKorziny = v.findViewById(R.id.Vroziny);

        nameMenu.setText(nameMenutext);
        imageView.setImageResource(Integer.parseInt(imageSourse));
        opicanie.setText(opicanietext);

        if (Integer.parseInt(id_menu)>3){
            opicanie.setVisibility(View.INVISIBLE);
            checkcheese.setVisibility(View.INVISIBLE);
            checkchiken.setVisibility(View.INVISIBLE);
            checktomato.setVisibility(View.INVISIBLE);
            tx1.setVisibility(View.INVISIBLE);
            tx2.setVisibility(View.INVISIBLE);
            tx3.setVisibility(View.INVISIBLE);
            tx4.setVisibility(View.INVISIBLE);
        }
        VKorziny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncRequestGoVKorziny = new AsyncRequestGoVKorziny();
                if (checktomato.isChecked())
                    idTomato = "2";
                if(checkcheese.isChecked())
                    idCheese="0";
                if(checkchiken.isChecked())
                    idchiken = "1";
                VKorziny.setVisibility(View.INVISIBLE);
                progressBarChoose.setVisibility(View.VISIBLE);
                asyncRequestGoVKorziny.execute(Id_zakaz,id_menu,idCheese,idchiken,idTomato);
            }
        });
        return v;
    }


    @Override
    public void onButtonClicked(String[] text) {

    }

    class AsyncRequestGoVKorziny extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... point) {
            String id_dopCheese;
            String id_dopChiken;
            String id_dopTomato;
            String IdZakaza = point[0];
            String id_menu = point[1];
            id_dopCheese = point[2];
            id_dopChiken = point[3];
            id_dopTomato = point[4];
            String count = "-1";
            String sql = "SELECT * FROM korzina";
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
                if (!id_dopCheese.equals("")) {
                    String sqlgo = "INSERT INTO `korzina` (`id_korzina`, `id_menu`, `id_zakaz`, `id_dopmenu`) VALUES (NULL, '"+ id_menu+"', '"+IdZakaza+"', '0')";
                    statement.executeUpdate(sqlgo);
                }
                if (!id_dopChiken.equals("")) {
                    String sqlgo = "INSERT INTO `korzina` (`id_korzina`, `id_menu`, `id_zakaz`, `id_dopmenu`) VALUES (NULL, '"+ id_menu+"', '"+IdZakaza+"', '1')";
                    statement.executeUpdate(sqlgo);
                }
                if (!id_dopTomato.equals("")) {
                    String sqlgo = "INSERT INTO `korzina` (`id_korzina`, `id_menu`, `id_zakaz`, `id_dopmenu`) VALUES (NULL, '"+ id_menu+"', '"+IdZakaza+"', '2')";
                    statement.executeUpdate(sqlgo);
                }

               if ((id_dopCheese.equals(""))&&(id_dopChiken.equals(""))&&(id_dopTomato.equals(""))){
                   String sqlgo ="INSERT INTO `korzina` (`id_korzina`, `id_menu`, `id_zakaz`, `id_dopmenu`) VALUES (NULL, '"+ id_menu+"', '"+IdZakaza+"', 100)";
                   statement.executeUpdate(sqlgo);
               }
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
            if (result.equals("0")) {
                VKorziny.setVisibility(View.VISIBLE);
                progressBarChoose.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Товар добавлен в корзину", Toast.LENGTH_SHORT).show();
                dismiss();
            }
            else {
                VKorziny.setVisibility(View.VISIBLE);
                progressBarChoose.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Попробуйте еще раз", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

