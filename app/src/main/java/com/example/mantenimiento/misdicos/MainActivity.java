package com.example.mantenimiento.misdicos;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    EditText editTextGrupo,editTextDisco;
    ListView listaDiscos;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextGrupo= (EditText) findViewById(R.id.A1EditTextGrupo);
        editTextDisco= (EditText) findViewById(R.id.A1EditTextTituloDisco);
        listaDiscos= (ListView) findViewById(R.id.A1ListView);

        db=openOrCreateDatabase("MisDisco", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS misDiscos(Grupo VARCHAR,Disco VARCHAR);");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void listar()
    {
        ArrayAdapter<String> adaptador;
        List<String> lista = new ArrayList<String>();
        Cursor c=db.rawQuery("SELECT * FROM MisDiscos", null);
        if(c.getCount()==0)
        {
            lista.add("No hay registros");
        }
        else
        {
            while(c.moveToNext())
            {
                lista.add(c.getString(0) + "-" + c.getString(1));
            }
        }
        adaptador=new ArrayAdapter<String>(getApplicationContext(),R.layout.item_list,lista);
        listaDiscos.setAdapter(adaptador);
    }

    public void añadir(View v)
    {
        db.execSQL("INSERT INTO MisDiscos VALUES ('"+editTextGrupo.getText().toString()+"','"+editTextDisco.getText().toString()+"')");
        Toast.makeText(this,"Se ha añadido el disco "+editTextDisco.getText().toString(), Toast.LENGTH_LONG).show();
        listar();
    }

    public void borrar(View v)
    {
        try {
            db.execSQL("DELETE FROM MisDiscos WHERE Grupo = '" + editTextGrupo.getText().toString() + "' AND Disco='" + editTextDisco.getText().toString() + "'");
            Toast.makeText(this, "Se ha borrado el disco " + editTextDisco.getText().toString(), Toast.LENGTH_LONG).show();
        }
        catch(SQLException s){
            Toast.makeText(this, "Error al borrar!", Toast.LENGTH_LONG).show();
        }
        listar();
    }

}
