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

public class ProductoDAO {

    private static DBHelper dbHelper;

    public ProductoDAO(Context context) {
        dbHelper = DBHelper.getDbH(context);
    }

    public int insert(Producto producto) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Producto.KEY_Nombre, producto.getNombre());
        values.put(Producto.KEY_Precio, producto.getPrecio());
        values.put(Producto.KEY_Caducidad, producto.getCaducidad());
        values.put(Producto.KEY_Cantidad, producto.getCantidad());
        values.put(Producto.KEY_ID_Inventario, producto.getIdInventario());
        values.put(Producto.KEY_ID_Categoria, producto.getIdCategoria());

        long idProducto = db.insert(Producto.TABLE, null, values);
        db.close();
        return (int) idProducto;
    }

    public void delete(int idProducto) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Producto.TABLE, Producto.KEY_ID + "= ?", new String[]{String.valueOf(idProducto)});
        db.close();
    }

    public void update(Producto producto) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Producto.KEY_Nombre, producto.getNombre());
        values.put(Producto.KEY_Precio, producto.getPrecio());
        values.put(Producto.KEY_Caducidad, producto.getCaducidad());
        values.put(Producto.KEY_Cantidad, producto.getCantidad());
        values.put(Producto.KEY_ID_Inventario, producto.getIdInventario());
        values.put(Producto.KEY_ID_Categoria, producto.getIdCategoria());

        db.update(Producto.TABLE, values, Producto.KEY_ID + "= ?", new String[]{String.valueOf(producto.getId())});
        db.close();
    }

    public Producto getProductoById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Producto.KEY_ID + "," +
                Producto.KEY_Nombre + "," +
                Producto.KEY_Precio + "," +
                Producto.KEY_Caducidad + "," +
                Producto.KEY_Cantidad + "," +
                Producto.KEY_ID_Inventario + "," +
                Producto.KEY_ID_Categoria +
                " FROM " + Producto.TABLE
                + " WHERE " +
                Producto.KEY_ID + "=?";

        Producto producto = new Producto();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            do {
                producto.setId(cursor.getInt(cursor.getColumnIndex(Producto.KEY_ID)));
                producto.setNombre(cursor.getString(cursor.getColumnIndex(Producto.KEY_Nombre)));
                producto.setPrecio(cursor.getDouble(cursor.getColumnIndex(Producto.KEY_Precio)));
                producto.setCaducidad(cursor.getString(cursor.getColumnIndex(Producto.KEY_Caducidad)));
                producto.setCantidad(cursor.getInt(cursor.getColumnIndex(Producto.KEY_Cantidad)));
                producto.setIdInventario(cursor.getInt(cursor.getColumnIndex(Producto.KEY_ID_Inventario)));
                producto.setIdCategoria(cursor.getInt(cursor.getColumnIndex(Producto.KEY_ID_Categoria)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return producto;
    }

    public Iterador<Producto> getProductoList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Producto.KEY_ID + "," +
                Producto.KEY_Nombre + "," +
                Producto.KEY_Precio + "," +
                Producto.KEY_Caducidad + "," +
                Producto.KEY_Cantidad + "," +
                Producto.KEY_ID_Inventario + "," +
                Producto.KEY_ID_Categoria +
                " FROM " + Producto.TABLE;

        Agregado<Producto> agregaProd = new AgregadoConcreto<>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Producto producto = new Producto();
                producto.setId(cursor.getInt(cursor.getColumnIndex(Producto.KEY_ID)));
                producto.setNombre(cursor.getString(cursor.getColumnIndex(Producto.KEY_Nombre)));
                producto.setPrecio(cursor.getDouble(cursor.getColumnIndex(Producto.KEY_Precio)));
                producto.setCaducidad(cursor.getString(cursor.getColumnIndex(Producto.KEY_Caducidad)));
                producto.setCantidad(cursor.getInt(cursor.getColumnIndex(Producto.KEY_Cantidad)));
                producto.setIdInventario(cursor.getInt(cursor.getColumnIndex(Producto.KEY_ID_Inventario)));
                producto.setIdCategoria(cursor.getInt(cursor.getColumnIndex(Producto.KEY_ID_Categoria)));
                agregaProd.add(producto);
            } while (cursor.moveToNext());
        }
        Iterador<Producto> productoList = agregaProd.iterador();
        cursor.close();
        db.close();
        return productoList;
    }

    public Iterador<Producto> getProductoListNevera() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Producto.KEY_ID + "," +
                Producto.KEY_Nombre + "," +
                Producto.KEY_Precio + "," +
                Producto.KEY_Caducidad + "," +
                Producto.KEY_Cantidad + "," +
                Producto.KEY_ID_Inventario + "," +
                Producto.KEY_ID_Categoria +
                " FROM " + Producto.TABLE +
                " WHERE " + Producto.KEY_ID_Inventario + " = 1";

        Agregado<Producto> agregaProd = new AgregadoConcreto<Producto>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Producto producto = new Producto();
                producto.setId(cursor.getInt(cursor.getColumnIndex(Producto.KEY_ID)));
                producto.setNombre(cursor.getString(cursor.getColumnIndex(Producto.KEY_Nombre)));
                producto.setPrecio(cursor.getDouble(cursor.getColumnIndex(Producto.KEY_Precio)));
                producto.setCaducidad(cursor.getString(cursor.getColumnIndex(Producto.KEY_Caducidad)));
                producto.setCantidad(cursor.getInt(cursor.getColumnIndex(Producto.KEY_Cantidad)));
                producto.setIdInventario(cursor.getInt(cursor.getColumnIndex(Producto.KEY_ID_Inventario)));
                producto.setIdCategoria(cursor.getInt(cursor.getColumnIndex(Producto.KEY_ID_Categoria)));
                agregaProd.add(producto);
            } while (cursor.moveToNext());
        }
        Iterador<Producto> productoList = agregaProd.iterador();
        cursor.close();
        db.close();
        return productoList;
    }

    public Iterador<Producto> getProductoListDespensa() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Producto.KEY_ID + "," +
                Producto.KEY_Nombre + "," +
                Producto.KEY_Precio + "," +
                Producto.KEY_Caducidad + "," +
                Producto.KEY_Cantidad + "," +
                Producto.KEY_ID_Inventario + "," +
                Producto.KEY_ID_Categoria +
                " FROM " + Producto.TABLE +
                " WHERE " + Producto.KEY_ID_Inventario + " = 3";

        Agregado<Producto> agregaProd = new AgregadoConcreto<Producto>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Producto producto = new Producto();
                producto.setId(cursor.getInt(cursor.getColumnIndex(Producto.KEY_ID)));
                producto.setNombre(cursor.getString(cursor.getColumnIndex(Producto.KEY_Nombre)));
                producto.setPrecio(cursor.getDouble(cursor.getColumnIndex(Producto.KEY_Precio)));
                producto.setCaducidad(cursor.getString(cursor.getColumnIndex(Producto.KEY_Caducidad)));
                producto.setCantidad(cursor.getInt(cursor.getColumnIndex(Producto.KEY_Cantidad)));
                producto.setIdInventario(cursor.getInt(cursor.getColumnIndex(Producto.KEY_ID_Inventario)));
                producto.setIdCategoria(cursor.getInt(cursor.getColumnIndex(Producto.KEY_ID_Categoria)));
                agregaProd.add(producto);
            } while (cursor.moveToNext());
        }

        Iterador<Producto> productoList = agregaProd.iterador();
        cursor.close();
        db.close();
        return productoList;
    }

    public Iterador<Producto> getProductoListCongelador() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Producto.KEY_ID + "," +
                Producto.KEY_Nombre + "," +
                Producto.KEY_Precio + "," +
                Producto.KEY_Caducidad + "," +
                Producto.KEY_Cantidad + "," +
                Producto.KEY_ID_Inventario + "," +
                Producto.KEY_ID_Categoria +
                " FROM " + Producto.TABLE +
                " WHERE " + Producto.KEY_ID_Inventario + " = 2";

        Agregado<Producto> agregaProd = new AgregadoConcreto<Producto>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Producto producto = new Producto();
                producto.setId(cursor.getInt(cursor.getColumnIndex(Producto.KEY_ID)));
                producto.setNombre(cursor.getString(cursor.getColumnIndex(Producto.KEY_Nombre)));
                producto.setPrecio(cursor.getDouble(cursor.getColumnIndex(Producto.KEY_Precio)));
                producto.setCaducidad(cursor.getString(cursor.getColumnIndex(Producto.KEY_Caducidad)));
                producto.setCantidad(cursor.getInt(cursor.getColumnIndex(Producto.KEY_Cantidad)));
                producto.setIdInventario(cursor.getInt(cursor.getColumnIndex(Producto.KEY_ID_Inventario)));
                producto.setIdCategoria(cursor.getInt(cursor.getColumnIndex(Producto.KEY_ID_Categoria)));
                agregaProd.add(producto);
            } while (cursor.moveToNext());
        }
        Iterador<Producto> productoList = agregaProd.iterador();
        cursor.close();
        db.close();
        return productoList;
    }

    public Producto getLastProducto() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Producto.KEY_ID + "," +
                Producto.KEY_Nombre + "," +
                Producto.KEY_Precio + "," +
                Producto.KEY_Caducidad + "," +
                Producto.KEY_Cantidad + "," +
                Producto.KEY_ID_Inventario + "," +
                Producto.KEY_ID_Categoria +
                " FROM " + Producto.TABLE
                + " ORDER BY " + Producto.KEY_ID + " DESC LIMIT 1";

        Producto producto = new Producto();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                producto.setId(cursor.getInt(cursor.getColumnIndex(Producto.KEY_ID)));
                producto.setNombre(cursor.getString(cursor.getColumnIndex(Producto.KEY_Nombre)));
                producto.setPrecio(cursor.getDouble(cursor.getColumnIndex(Producto.KEY_Precio)));
                producto.setCaducidad(cursor.getString(cursor.getColumnIndex(Producto.KEY_Caducidad)));
                producto.setCantidad(cursor.getInt(cursor.getColumnIndex(Producto.KEY_Cantidad)));
                producto.setIdInventario(cursor.getInt(cursor.getColumnIndex(Producto.KEY_ID_Inventario)));
                producto.setIdCategoria(cursor.getInt(cursor.getColumnIndex(Producto.KEY_ID_Categoria)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return producto;
    }
}
