package mycompra.app.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import mycompra.app.dbhelper.DBHelper;
import mycompra.app.iterador.Agregado;
import mycompra.app.iterador.AgregadoConcreto;
import mycompra.app.iterador.Iterador;
import mycompra.app.modelo.Mes;

public class MesDAO {

    private static DBHelper dbHelper;

    public MesDAO(Context context) {
        dbHelper = DBHelper.getDbH(context);
    }

    public int insert(Mes mes) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Mes.KEY_Nombre, mes.getNombre());
        values.put(Mes.KEY_Anyo, mes.getAnyo());
        values.put(Mes.KEY_Presupuesto, mes.getPresupuesto());

        long idMes = db.insert(Mes.TABLE, null, values);
        db.close();
        return (int) idMes;
    }

    public void delete(int idMes) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Mes.TABLE, Mes.KEY_ID + "= ?", new String[]{String.valueOf(idMes)});
        db.close();
    }

    public void update(Mes mes) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Mes.KEY_Nombre, mes.getNombre());
        values.put(Mes.KEY_Anyo, mes.getAnyo());
        values.put(Mes.KEY_Presupuesto, mes.getPresupuesto());

        db.update(Mes.TABLE, values, Mes.KEY_ID + "= ?", new String[]{String.valueOf(mes.getId())});
        db.close();
    }

    public Mes getMesById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Mes.KEY_ID + "," +
                Mes.KEY_Nombre + "," +
                Mes.KEY_Anyo + "," +
                Mes.KEY_Presupuesto +
                " FROM " + Mes.TABLE
                + " WHERE " +
                Mes.KEY_ID + "=?";

        Mes mes = new Mes();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            do {
                mes.setId(cursor.getInt(cursor.getColumnIndex(Mes.KEY_ID)));
                mes.setNombre(cursor.getString(cursor.getColumnIndex(Mes.KEY_Nombre)));
                mes.setAnyo(cursor.getInt(cursor.getColumnIndex(Mes.KEY_Anyo)));
                mes.setPresupuesto(cursor.getDouble(cursor.getColumnIndex(Mes.KEY_Presupuesto)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return mes;
    }

    public Iterador<Mes> getMesList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Mes.KEY_ID + "," +
                Mes.KEY_Nombre + "," +
                Mes.KEY_Anyo + "," +
                Mes.KEY_Presupuesto +
                " FROM " + Mes.TABLE;


        Agregado<Mes> agregaMes = new AgregadoConcreto<Mes>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Mes mes = new Mes();
                mes.setId(cursor.getInt(cursor.getColumnIndex(Mes.KEY_ID)));
                mes.setNombre(cursor.getString(cursor.getColumnIndex(Mes.KEY_Nombre)));
                mes.setAnyo(cursor.getInt(cursor.getColumnIndex(Mes.KEY_Anyo)));
                mes.setPresupuesto(cursor.getDouble(cursor.getColumnIndex(Mes.KEY_Presupuesto)));
                agregaMes.add(mes);
            } while (cursor.moveToNext());
        }
        Iterador<Mes> mesList = agregaMes.iterador();
        cursor.close();
        db.close();
        return mesList;
    }
}
