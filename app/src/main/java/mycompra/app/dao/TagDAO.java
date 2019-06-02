package mycompra.app.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import mycompra.app.dbhelper.DBHelper;
import mycompra.app.iterador.Agregado;
import mycompra.app.iterador.AgregadoConcreto;
import mycompra.app.iterador.Iterador;
import mycompra.app.modelo.Tag;

public class TagDAO {
    private static DBHelper dbHelper;

    public TagDAO(Context context) {
        dbHelper = DBHelper.getDbH(context);
    }

    public int insert(Tag tag) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Tag.KEY_Nombre, tag.getNombre());
        values.put(Tag.KEY_ID_Categoria, tag.getIdCategoria());

        long idTag = db.insert(Tag.TABLE, null, values);
        db.close();
        return (int) idTag;
    }

    public void delete(int idTag) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Tag.TABLE, Tag.KEY_ID + "= ?", new String[]{String.valueOf(idTag)});
        db.close();
    }

    public void update(Tag tag) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Tag.KEY_Nombre, tag.getNombre());
        values.put(Tag.KEY_ID_Categoria, tag.getIdCategoria());

        db.update(Tag.TABLE, values, Tag.KEY_ID + "= ?", new String[]{String.valueOf(tag.getId())});
        db.close();
    }

    public Tag getTagById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Tag.KEY_ID + "," +
                Tag.KEY_Nombre + "," +
                Tag.KEY_ID_Categoria +
                " FROM " + Tag.TABLE
                + " WHERE " +
                Tag.KEY_ID + "=?";

        Tag tag = new Tag();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            do {
                tag.setId(cursor.getInt(cursor.getColumnIndex(Tag.KEY_ID)));
                tag.setNombre(cursor.getString(cursor.getColumnIndex(Tag.KEY_Nombre)));
                tag.setIdCategoria(cursor.getInt(cursor.getColumnIndex(Tag.KEY_ID_Categoria)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tag;
    }

    public Iterador<Tag> getTagList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Tag.KEY_ID + "," +
                Tag.KEY_Nombre + "," +
                Tag.KEY_ID_Categoria +
                " FROM " + Tag.TABLE;

        Agregado<Tag> agregaTag = new AgregadoConcreto<Tag>();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Tag tag = new Tag();
                tag.setId(cursor.getInt(cursor.getColumnIndex(Tag.KEY_ID)));
                tag.setNombre(cursor.getString(cursor.getColumnIndex(Tag.KEY_Nombre)));
                tag.setIdCategoria(cursor.getInt(cursor.getColumnIndex(Tag.KEY_ID_Categoria)));
                agregaTag.add(tag);
            } while (cursor.moveToNext());
        }
        Iterador<Tag> iteraTag = agregaTag.iterador();
        cursor.close();
        db.close();
        return iteraTag;
    }

    public Iterador<Tag> getTagListByCategoria(int idCategoria) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Tag.KEY_ID + "," +
                Tag.KEY_Nombre + "," +
                Tag.KEY_ID_Categoria +
                " FROM " + Tag.TABLE +
                " WHERE " + Tag.KEY_ID_Categoria + "=?";

        Agregado<Tag> agregaTag = new AgregadoConcreto<Tag>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Tag tag = new Tag();
                tag.setId(cursor.getInt(cursor.getColumnIndex(Tag.KEY_ID)));
                tag.setNombre(cursor.getString(cursor.getColumnIndex(Tag.KEY_Nombre)));
                tag.setIdCategoria(cursor.getInt(cursor.getColumnIndex(Tag.KEY_ID_Categoria)));
                agregaTag.add(tag);
            } while (cursor.moveToNext());
        }
        Iterador<Tag> iteraTag = agregaTag.iterador();
        cursor.close();
        db.close();
        return iteraTag;
    }
}