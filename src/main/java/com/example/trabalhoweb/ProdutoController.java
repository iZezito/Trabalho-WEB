package com.example.trabalhoweb;

import com.example.trabalhoweb.classes.Produto;
import com.example.trabalhoweb.classes.ProdutoRepositorio;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class ProdutoController {
    boolean token;
    ProdutoRepositorio produtos;

    ProdutoController(ProdutoRepositorio produtoRepositorio) {
        this.produtos = produtoRepositorio;
    }

    @RequestMapping("/cadastroproduto")
    public void axios(HttpServletRequest request, HttpServletResponse response) throws IOException {

        var nome = request.getParameter("nome");
        var preco = Float.parseFloat(request.getParameter("preco"));
        var descricao = request.getParameter("descricao");
        var estoque = Integer.parseInt(request.getParameter("estoque"));

        Produto p = new Produto(preco, nome, descricao, estoque);
        produtos.save(p);
        response.sendRedirect("/lojista");


    }


    @RequestMapping("/lojista")
    public void lojista(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ArrayList<Produto> arrayList = (ArrayList<Produto>) produtos.findAll();

        response.getWriter().println("<html>");
        response.getWriter().println("<head>");
        response.getWriter().println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">");
        response.getWriter().println("</head>");
        response.getWriter().println("<body>");

        response.getWriter().println("<table class=\"table\">");
        response.getWriter().println("<thead>");


        response.getWriter().println("<tr>");
        response.getWriter().println("<th scope=\"col\">Nome</th>");
        response.getWriter().println("<th scope=\"col\">Preço</th>");
        response.getWriter().println("<th scope=\"col\">Estoque</th>");
        response.getWriter().println("<th scope=\"col\">Id</th>");
        response.getWriter().println("</tr>");
        response.getWriter().println("</thead>");
        response.getWriter().println("<tbody>");

        for (Produto p : arrayList) {
            response.getWriter().println("<tr>");
            response.getWriter().println("<td>" + p.getNome() + "</td>");
            response.getWriter().println("<td>" + p.getPreco() + "</td>");
            response.getWriter().println("<td>" + p.getEstoque() + "</td>");
            response.getWriter().println("<td>" + p.getId() + "</td>");
            response.getWriter().println("</tr>");
        }
        response.getWriter().println("</tbody>");

        response.getWriter().println("</table>");
        response.getWriter().println("<a class=\"nav-link\"href=\"cadastrarproduto.html\">Cadastrar Produto</a>");
        response.getWriter().println("<a class=\"nav-link\"href=\"logout\">Logout</a>");

        response.getWriter().println("</body>");
        response.getWriter().println("</html>");


    }


}
