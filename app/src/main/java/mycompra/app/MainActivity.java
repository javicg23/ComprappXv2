package mycompra.app;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mycompra.app.controlador.Configuracion;
import mycompra.app.controlador.InfoMensual;
import mycompra.app.controlador.Inventarios;
import mycompra.app.controlador.Listas;
import mycompra.app.controlador.Principal;
import mycompra.app.controlador.Productos;
import mycompra.app.controlador.TicketsDelMes;
import mycompra.app.dao.CategoriaDAO;
import mycompra.app.dao.InventarioDAO;
import mycompra.app.dao.ListaDAO;
import mycompra.app.dao.MesDAO;
import mycompra.app.dao.ProductoDAO;
import mycompra.app.dao.ProductoListaDAO;
import mycompra.app.dao.ProductoTicketDAO;
import mycompra.app.dao.SupermercadoDAO;
import mycompra.app.dao.TagDAO;
import mycompra.app.dao.TicketDAO;
import mycompra.app.dbhelper.DBHelper;
import mycompra.app.modelo.AbstractMes;
import mycompra.app.modelo.Categoria;
import mycompra.app.modelo.Inventario;
import mycompra.app.modelo.Lista;
import mycompra.app.modelo.Mes;
import mycompra.app.modelo.MesFactory;
import mycompra.app.modelo.Producto;
import mycompra.app.modelo.ProductoTicket;
import mycompra.app.modelo.Supermercado;
import mycompra.app.modelo.Tag;
import mycompra.app.modelo.Ticket;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static InventarioDAO inventarioDAO;
    private static CategoriaDAO categoriaDAO;
    private static ListaDAO listaDAO;
    private static MesDAO mesDAO;
    private static ProductoDAO productoDAO;
    private static SupermercadoDAO supermercadoDAO;
    private static TagDAO tagDAO;
    private static TicketDAO ticketDAO;
    private static ProductoTicketDAO productoTicketDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        setTitle("Principal");

        Principal fragment = new Principal();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment, "Principal");
        fragmentTransaction.commit();

        FragmentManager fragmentTransactionNevera = getSupportFragmentManager();

        File database = getApplicationContext().getDatabasePath("Comprapp.db");

        if (!database.exists()) {
            inventarioDAO = new InventarioDAO(this);
            insertsInventario();
            listaDAO = new ListaDAO(this);
            insertsLista();
            supermercadoDAO = new SupermercadoDAO(this);
            insertsSupermercado();
            mesDAO = new MesDAO(this);
            insertsMes();
            categoriaDAO = new CategoriaDAO(this);
            insertsCategoria();
            tagDAO = new TagDAO(this);
            insertsTag();
            ticketDAO = new TicketDAO(this);
            insertsTicket();
            productoDAO = new ProductoDAO(this);
            insertsProducto();
            productoTicketDAO = new ProductoTicketDAO(this);
            insertsProductoTicket();
        }
        comprobarExisteMesActual();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inventario) {
            setTitle("Inventarios");
            Inventarios fragment = new Inventarios();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment, "Inventarios");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_productos) {
            setTitle("Productos");
            Productos fragment = new Productos();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment, "Productos");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_listas) {
            setTitle("Listas");
            Listas fragment = new Listas();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment, "Listas");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_tickets) {
            setTitle("Tickets");
            TicketsDelMes fragment = new TicketsDelMes();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment, "Tickets");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_infoMensual) {
            setTitle("InfoMensual");
            InfoMensual fragment = new InfoMensual();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment, "InfoMensual");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_Home) {
            setTitle("Principal");
            Principal fragment = new Principal();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment, "Principal");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_configuracion) {
            setTitle("Configuracion");
            Configuracion fragment = new Configuracion();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment, "Configuración");
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void insertsInventario() {
        insertaInventario("Nevera");
        insertaInventario("Congelador");
        insertaInventario("Despensa");
    }

    public void insertsLista() {
        insertaLista("Mensual");
        insertaLista("Semanal");
        insertaLista("Diaria");
    }

    public void insertsSupermercado() {
        insertaSuper("Mercadona");
        insertaSuper("Consum");
        insertaSuper("Otros");
    }

    public void insertsMes() {
        insertaMes("Mayo", 2019, 200);
    }

    public void insertsCategoria() {
        insertaCategoria("Lacteos", 1);
        insertaCategoria("Vegetales", 1);
        insertaCategoria("Frutas", 1);
        insertaCategoria("Carne", 1);
        insertaCategoria("Pescado", 1);
        insertaCategoria("Huevos", 1);
        insertaCategoria("Bebidas", 1);
        insertaCategoria("Bebidas Alcoholicas", 1);
        insertaCategoria("Preparados", 1);
        insertaCategoria("Pasta", 3);
        insertaCategoria("Arroz", 3);
        insertaCategoria("Legumbres", 3);
        insertaCategoria("Dulces", 3);
        insertaCategoria("Aperitivos", 3);
        insertaCategoria("Embutidos", 3);
        insertaCategoria("Hogar", 3);
        insertaCategoria("Aceite", 3);
        insertaCategoria("Cosmetica", 3);
        insertaCategoria("Congelados", 2);
        insertaCategoria("Hortalizas", 3);
        insertaCategoria("Panaderia", 3);

    }

    public void insertsTag() {
        insertaTag("Pollo", 4);
        insertaTag("Ternera", 4);
        insertaTag("Conejo", 4);
        insertaTag("Cerdo", 4);
        insertaTag("Chuleta", 4);
        insertaTag("Lomo", 4);
        insertaTag("Merluza", 5);
        insertaTag("Atun", 5);
        insertaTag("Gamba", 5);
        insertaTag("Mejillon", 5);
        insertaTag("Lubina", 5);
        insertaTag("Jamón York", 4);
        insertaTag("Chorizo", 4);
        insertaTag("Gouda", 1);
        insertaTag("Emmental", 1);
        insertaTag("Queso", 1);
        insertaTag("Leche", 1);
        insertaTag("Yogurt", 1);
        insertaTag("Roquefort", 1);
        insertaTag("Rallado", 1);
        insertaTag("Pera", 3);
        insertaTag("Manzana", 3);
        insertaTag("Plátano", 3);
        insertaTag("Melocotón", 3);
        insertaTag("Piña", 3);
        insertaTag("Cereza", 3);
        insertaTag("Naranja", 3);
        insertaTag("Mandarina", 3);
        insertaTag("Sandia", 3);
        insertaTag("Melón", 3);
        insertaTag("Lechuga", 2);
        insertaTag("Tomate", 2);
        insertaTag("Pepino", 2);
        insertaTag("Maiz", 2);
        insertaTag("Cebolla", 2);
        insertaTag("Patata", 2);
        insertaTag("Calabacin", 2);
        insertaTag("Calabaza", 2);
        insertaTag("Pimienro", 2);
        insertaTag("Guisantes", 2);
        insertaTag("Napolitana", 14);
        insertaTag("Galleta", 14);
        insertaTag("Cereal", 14);
        insertaTag("Oreo", 14);
        insertaTag("Chips", 14);
        insertaTag("Cookies", 14);
        insertaTag("Flan", 6);
        insertaTag("Natillas", 6);
        insertaTag("Rosquilletas", 15);
        insertaTag("Pan", 8);
        insertaTag("Barra Pan", 8);
        insertaTag("Tortilla", 6);
        insertaTag("Huevo", 6);
        insertaTag("Sopa", 10);
        insertaTag("Crema", 10);
        insertaTag("Pizza", 10);
        insertaTag("Lasagna", 10);
        insertaTag("Sandwitch", 10);
        insertaTag("Tortellini", 11);
        insertaTag("Espagueti", 11);
        insertaTag("Macarron", 11);
        insertaTag("Fideo", 11);
        insertaTag("Arroz", 12);
        insertaTag("Basmati", 12);
        insertaTag("Yatekomo", 11);
        insertaTag("Campesinas", 15);
        insertaTag("Harina", 16);
        insertaTag("Azúcar", 17);
        insertaTag("Moreno", 17);
        insertaTag("Aceite", 18);
        insertaTag("Girasol", 18);
        insertaTag("Helado", 19);
        insertaTag("Vainilla", 19);
        insertaTag("Chocolate", 14);
        insertaTag("Hielo", 20);
        insertaTag("Croqueta", 21);
        insertaTag("Nuggets", 21);
        insertaTag("Congelado", 21);
        insertaTag("Salteado", 21);
        insertaTag("Ron", 9);
        insertaTag("Vodka", 9);
        insertaTag("Ginebra", 9);
        insertaTag("Whisky", 9);
        insertaTag("Cola", 7);
        insertaTag("Fanta", 7);
        insertaTag("Nestea", 7);
        insertaTag("Energetica", 7);
        insertaTag("Red Bull", 7);
        insertaTag("Burn", 7);
    }

    public void insertsTicket() {
        insertaTicket("05/05/2019", 20.33, 1, 2);
        insertaTicket("12/05/2019", 9.7, 1, 1);
        insertaTicket("22/05/2019", 17.3, 1, 1);
    }

    public void insertsProducto() {
        insertaProducto("Huevos XL", 1.60, 1, "02/06/2019", 1, 6);
        insertaProducto("Yogurt Fresa", 1.60, 2, "25/05/2019", 1, 1);
        insertaProducto("ChipsAhoy", 1.60, 1, "25/05/2019", 3, 13);
        insertaProducto("Pan Molde", 168, 1, "29/06/2019", 3, 21);
        insertaProducto("Queso Fresco", 2.50, 2, "02/06/2019", 1, 1);
        insertaProducto("Lechuga Iceberg", 1.10, 1, "23/06/2019", 1, 2);
        insertaProducto("Pizza 4 Quesos", 2.70, 1, "15/06/2019", 1, 9);
        insertaProducto("York Lonchas", 1.80, 1, "11/06/2019", 1, 15);
        insertaProducto("Salteado verduras", 2.20, 1, "16/06/2019", 2, 19);
        insertaProducto("Helado vainilla", 2.50, 1, "14/06/2019", 2, 19);
        insertaProducto("Platano Canarias", 1.39, 1, "29/06/2019", 1, 3);
        insertaProducto("Napolitanas", 0.89, 1, "01/06/2019", 3, 21);
        insertaProducto("Manzana Royal Gala", 1.25, 1, "27/06/2019", 1, 3);
    }

    public void insertsProductoTicket() {
        insertaProductoTicket(3, 3, 1);
        insertaProductoTicket(2, 5, 1);
        insertaProductoTicket(2, 9, 2);
        insertaProductoTicket(2, 8, 3);
        insertaProductoTicket(2, 2, 1);
        insertaProductoTicket(1, 13, 1);
        insertaProductoTicket(1, 7, 1);
        insertaProductoTicket(1, 4, 1);
        insertaProductoTicket(1, 1, 1);
        insertaProductoTicket(1, 6, 1);
    }

    public void insertaInventario(String nombre) {
        Inventario inventario = new Inventario();
        inventario.setNombre(nombre);
        inventarioDAO.insert(inventario);
    }

    public void insertaLista(String nombre) {
        Lista lista = new Lista();
        lista.setNombre(nombre);
        listaDAO.insert(lista);
    }

    public void insertaSuper(String nombre) {
        Supermercado a = new Supermercado();
        a.setNombre(nombre);
        supermercadoDAO.insert(a);
    }

    public void insertaMes(String nombre, int anyo, int presupuesto) {
        Mes mesMayo = new Mes();
        mesMayo.setNombre(nombre);
        mesMayo.setAnyo(anyo);
        mesMayo.setPresupuesto(presupuesto);
        mesDAO.insert(mesMayo);
    }

    public void insertaCategoria(String nombre, int idInv) {
        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        categoria.setIdInventario(idInv);
        categoriaDAO.insert(categoria);
    }

    public void insertaTag(String nombre, int idCat) {
        Tag tagMoreno = new Tag();
        tagMoreno.setNombre(nombre);
        tagMoreno.setIdCategoria(idCat);
        tagDAO.insert(tagMoreno);
    }

    public void insertaTicket(String fecha, double precio, int idMes, int idSup) {
        Ticket ticket = new Ticket();
        ticket.setPrecio(precio);
        ticket.setFecha(fecha);
        ticket.setIdSupermercado(idSup);
        ticket.setIdMes(idMes);
        ticketDAO.insert(ticket);

    }

    public void insertaProducto(String nombre, double precio, int cantidad, String caducidad, int idInv, int idCat) {

        Producto productoHuevos = new Producto();
        productoHuevos.setNombre(nombre);
        productoHuevos.setPrecio(precio);
        productoHuevos.setCantidad(cantidad);
        productoHuevos.setCaducidad(caducidad);
        productoHuevos.setIdInventario(idInv);
        productoHuevos.setIdCategoria(idCat);
        productoDAO.insert(productoHuevos);
    }

    public void insertaProductoTicket(int idTicket, int idProd, int cantidad) {

        ProductoTicket productoTicket = new ProductoTicket();
        productoTicket.setIdTicket(idTicket);
        productoTicket.setIdProducto(idProd);
        productoTicket.setCantidad(cantidad);
        productoTicketDAO.insert(productoTicket);
    }

    public void comprobarExisteMesActual() {

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/YYYY");
        String fechaActual = mdformat.format(date);

        String[] partesFecha = fechaActual.split("/");
        String mesString = partesFecha[1];
        String anyoActual = partesFecha[2];

        String nombreMes = "";

        switch (Integer.parseInt(mesString)) {
            case 1:
                nombreMes = "Enero";
                break;
            case 2:
                nombreMes = "Febrero";
                break;
            case 3:
                nombreMes = "Marzo";
                break;
            case 4:
                nombreMes = "Abril";
                break;
            case 5:
                nombreMes = "Mayo";
                break;
            case 6:
                nombreMes = "Junio";
                break;
            case 7:
                nombreMes = "Julio";
                break;
            case 8:
                nombreMes = "Agosto";
                break;
            case 9:
                nombreMes = "Septiembre";
                break;
            case 10:
                nombreMes = "Octubre";
                break;
            case 11:
                nombreMes = "Noviembre";
                break;
            case 12:
                nombreMes = "Diciembre";
                break;
        }

        AbstractMes abstractMes = MesFactory.getMes(this, nombreMes, Integer.parseInt(anyoActual));

        if (abstractMes.isNil()) {
            MesDAO mesDAOExisteMes = new MesDAO(this);
            Mes mes = new Mes();
            mes.setPresupuesto(0);
            mes.setNombre(nombreMes);
            mes.setAnyo(Integer.parseInt(anyoActual));
            mesDAOExisteMes.insert(mes);
        }
    }
}
