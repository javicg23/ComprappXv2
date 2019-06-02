package mycompra.app.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import mycompra.app.dbhelper.DBHelper;
import mycompra.app.iterador.Agregado;
import mycompra.app.iterador.AgregadoConcreto;
import mycompra.app.iterador.Iterador;
import mycompra.app.modelo.Producto;
import mycompra.app.modelo.ProductoLista;

public class ProductoListaDAO {

    private static DBHelper dbHelper;

    public ProductoListaDAO(Context context) {
        dbHelper = DBHelper.getDbH(context);
    }

    public int insert(ProductoLista productoLista) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ProductoLista.KEY_ID_Producto, productoLista.getIdProducto());
        values.put(ProductoLista.KEY_ID_Lista, productoLista.getIdLista());

        long idProductoLista = db.insert(ProductoLista.TABLE, null, values);
        db.close();
        return (int) idProductoLista;
    }

    public void delete(int idProductoLista) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(ProductoLista.TABLE, ProductoLista.KEY_ID_Producto + "= ?", new String[]{String.valueOf(idProductoLista)});
        db.close();
    }

    public void update(ProductoLista productoLista) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ProductoLista.KEY_ID_Producto, productoLista.getIdProducto());
        values.put(ProductoLista.KEY_ID_Lista, productoLista.getIdLista());

        db.update(ProductoLista.TABLE, values, ProductoLista.KEY_ID_Producto + "= ?", new String[]{String.valueOf(productoLista.getIdProducto())});
        db.close();
    }

    public ProductoLista getProductoListaById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                ProductoLista.KEY_ID_Producto + "," +
                ProductoLista.KEY_ID_Lista +
                " FROM " + ProductoLista.TABLE
                + " WHERE " +
                ProductoLista.KEY_ID_Producto + "=?";

        ProductoLista productoLista = new ProductoLista();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            do {
                productoLista.setIdProducto(cursor.getInt(cursor.getColumnIndex(ProductoLista.KEY_ID_Producto)));
                productoLista.setIdLista(cursor.getInt(cursor.getColumnIndex(ProductoLista.KEY_ID_Lista)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productoLista;
    }

    public Iterador<ProductoLista> getProductoListaList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                ProductoLista.KEY_ID_Producto + "," +
                ProductoLista.KEY_ID_Lista +
                " FROM " + ProductoLista.TABLE;


        Agregado<ProductoLista> agregaPL = new AgregadoConcreto<ProductoLista>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ProductoLista productoLista = new ProductoLista();
                productoLista.setIdProducto(cursor.getInt(cursor.getColumnIndex(ProductoLista.KEY_ID_Producto)));
                productoLista.setIdLista(cursor.getInt(cursor.getColumnIndex(ProductoLista.KEY_ID_Lista)));
                agregaPL.add(productoLista);
            } while (cursor.moveToNext());
        }
        Iterador<ProductoLista> iteraPL = agregaPL.iterador();
        cursor.close();
        db.close();
        return iteraPL;
    }

    public Iterador<Producto> getProductoListFromListaHabitual(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Producto.KEY_Nombre +
                " FROM " + Producto.TABLE + ", " + ProductoLista.TABLE
                + " WHERE " +
                ProductoLista.KEY_ID_Lista + "=?" + " AND " +
                ProductoLista.KEY_ID_Producto + " = " + Producto.KEY_ID;


        Agregado<Producto> agregaPL = new AgregadoConcreto<Producto>();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            do {
                Producto productoLista = new Producto();
                productoLista.setNombre(cursor.getString(cursor.getColumnIndex(Producto.KEY_Nombre)));
                agregaPL.add(productoLista);
            } while (cursor.moveToNext());
        }
        Iterador<Producto> iteraPL = agregaPL.iterador();
        cursor.close();
        db.close();
        return iteraPL;
    }
}
