package mycompra.app.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import mycompra.app.dbhelper.DBHelper;
import mycompra.app.iterador.Agregado;
import mycompra.app.iterador.AgregadoConcreto;
import mycompra.app.iterador.Iterador;
import mycompra.app.modelo.ProductoTicket;

public class ProductoTicketDAO {

    private static DBHelper dbHelper;

    public ProductoTicketDAO(Context context) {
        dbHelper = DBHelper.getDbH(context);
    }

    public int insert(ProductoTicket productoTicket) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ProductoTicket.KEY_ID_Producto, productoTicket.getIdProducto());
        values.put(ProductoTicket.KEY_ID_Ticket, productoTicket.getIdTicket());
        values.put(ProductoTicket.KEY_Cantidad, productoTicket.getCantidad());

        long idProductoTicket = db.insert(ProductoTicket.TABLE, null, values);
        db.close();
        return (int) idProductoTicket;
    }

    public void delete(int idProductoTicket) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(ProductoTicket.TABLE, ProductoTicket.KEY_ID_Producto + "= ?", new String[]{String.valueOf(idProductoTicket)});
        db.close();
    }

    public void update(ProductoTicket productoTicket) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ProductoTicket.KEY_ID_Producto, productoTicket.getIdProducto());
        values.put(ProductoTicket.KEY_ID_Ticket, productoTicket.getIdTicket());
        values.put(ProductoTicket.KEY_Cantidad, productoTicket.getCantidad());

        db.update(ProductoTicket.TABLE, values, ProductoTicket.KEY_ID_Producto + "= ?", new String[]{String.valueOf(productoTicket.getIdProducto())});
        db.close();
    }

    public ProductoTicket getProductoTicketById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                ProductoTicket.KEY_ID_Producto + "," +
                ProductoTicket.KEY_ID_Ticket + "," +
                ProductoTicket.KEY_Cantidad +
                " FROM " + ProductoTicket.TABLE
                + " WHERE " +
                ProductoTicket.KEY_ID_Producto + "=?";

        ProductoTicket productoTicket = new ProductoTicket();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            do {
                productoTicket.setIdProducto(cursor.getInt(cursor.getColumnIndex(ProductoTicket.KEY_ID_Producto)));
                productoTicket.setIdTicket(cursor.getInt(cursor.getColumnIndex(ProductoTicket.KEY_ID_Ticket)));
                productoTicket.setCantidad(cursor.getInt(cursor.getColumnIndex(ProductoTicket.KEY_Cantidad)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productoTicket;
    }

    public Iterador<ProductoTicket> getProductoTicketList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                ProductoTicket.KEY_ID_Producto + "," +
                ProductoTicket.KEY_ID_Ticket + "," +
                ProductoTicket.KEY_Cantidad +
                " FROM " + ProductoTicket.TABLE;

        Agregado<ProductoTicket> agregaPT = new AgregadoConcreto<ProductoTicket>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ProductoTicket productoTicket = new ProductoTicket();
                productoTicket.setIdProducto(cursor.getInt(cursor.getColumnIndex(ProductoTicket.KEY_ID_Producto)));
                productoTicket.setIdTicket(cursor.getInt(cursor.getColumnIndex(ProductoTicket.KEY_ID_Ticket)));
                productoTicket.setCantidad(cursor.getInt(cursor.getColumnIndex(ProductoTicket.KEY_Cantidad)));
                agregaPT.add(productoTicket);
            } while (cursor.moveToNext());
        }
        Iterador<ProductoTicket> iteraPT = agregaPT.iterador();
        cursor.close();
        db.close();
        return iteraPT;
    }
}
