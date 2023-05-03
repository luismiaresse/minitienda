package minitienda.actions;

import jakarta.servlet.http.HttpServlet;

import minitienda.application.Producto;
import minitienda.database.DBFront;
import minitienda.application.Carrito;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.ArrayList;

public class anadirProducto extends HttpServlet {

    //Carrito carrito;

    private DBFront db;
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db = DBFront.getInstance();
    }

    // Se ejecuta cuando se envia un formulario con method="get" o sin method
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
    }

    // Se ejecuta cuando se envia un formulario con method="post"
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        // Obtenemos el producto y la cantidad a partir de la peticion
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        Producto producto = db.getProductById(idProducto);
        HttpSession session = request.getSession(true);

        // Obtenemos la lista de productos del carrito
        Carrito carrito = (Carrito) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new Carrito();
        }
        carrito.anadirProducto(producto, cantidad);
        session.setAttribute("carrito", carrito);

        // Reenviamos a la pagina que muestra los parametros
        gotoPage("/WEB-INF/jsp/index.jsp", request, response);

        //carrito.anadirProducto();
    }

    private void gotoPage(String address, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    public void destroy() {
    }
}