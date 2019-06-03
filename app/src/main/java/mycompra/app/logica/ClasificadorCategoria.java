package mycompra.app.logica;

import android.content.Context;

import mycompra.app.dao.CategoriaDAO;
import mycompra.app.dao.TagDAO;
import mycompra.app.iterador.Iterador;
import mycompra.app.modelo.Categoria;
import mycompra.app.modelo.Tag;

public class ClasificadorCategoria
{
    private static CategoriaDAO categoriaDAO;
    private static TagDAO tagDAO;

    private static Iterador<Categoria> categorias;
    private static Iterador<Tag> tagsCategoria;

    public ClasificadorCategoria(Context context) {
        categoriaDAO = new CategoriaDAO(context);
        tagDAO = new TagDAO(context);
        categorias = categoriaDAO.getCategoriaList();
    }


    public static Categoria findCategoria(String nombreProd) {

        while (categorias.hasNext())
        {
            Categoria categoriaActual = categorias.next();

            tagsCategoria = tagDAO.getTagListByCategoria(categoriaActual.getId());

            while (tagsCategoria.hasNext())
            {
                if (tagsCategoria.next().getNombre().contains(nombreProd)) {
                    return categoriaActual;
                }
            }

        }

        return null;
    }
}