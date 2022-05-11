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
    Boolean token = false;
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
        if(request.getSession().getAttribute("tokenLojista") != null){
            token = true;
        }

        if(token){
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
            response.getWriter().println("<a class=\"nav-link\"href=\"cadastrarproduto\">Cadastrar Produto</a>");
            response.getWriter().println("<a class=\"nav-link\"href=\"logout\">Logout</a>");

            response.getWriter().println("</body>");
            response.getWriter().println("</html>");

        }else{
            response.getWriter().println("realize o login para prosseguir!");
        }


    }
    @RequestMapping("cadastrarproduto")
    public void cadastrarproduto(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if(token){
            response.getWriter().println("<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Title</title>\n" +
                    "    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<form action=\"http://localhost:8080/cadastroproduto\" method=\"post\">\n" +
                    "    <div class=\"mb-3\">\n" +
                    "        <label for=\"exampleInputEmail1\" class=\"form-label\">Nome</label>\n" +
                    "        <input type=\"text\" class=\"form-control w-25\" id=\"exampleInputEmail1\" aria-describedby=\"emailHelp\" name=\"nome\">\n" +
                    "    </div>\n" +
                    "    <div class=\"mb-3\">\n" +
                    "        <label for=\"exampleInputEmail1\" class=\"form-label\">Descrição</label>\n" +
                    "        <input type=\"text\" class=\"form-control w-25\" aria-describedby=\"emailHelp\" name=\"descricao\">\n" +
                    "    </div>\n" +
                    "    <div class=\"mb-3\">\n" +
                    "        <label class=\"form-label\">Preço</label>\n" +
                    "        <input type=\"number\" class=\"form-control w-25\" name=\"preco\">\n" +
                    "    </div>\n" +
                    "    <div class=\"mb-3\">\n" +
                    "        <label class=\"form-label\">Estoque</label>\n" +
                    "        <input type=\"number\" class=\"ui-icon-circle-check w-25\" name=\"estoque\">\n" +
                    "    </div>\n" +
                    "    <button type=\"submit\" class=\"btn btn-primary\">Cadastrar Produto</button>\n" +
                    "    <a href=\"/lojista\" class=\"nav-link\">Produtos Cadastrados</a>\n" +
                    "\n" +
                    "\n" +
                    "</form>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>");

        }else {
            response.getWriter().println("Realize o login para prosseguir!");
        }

    }


}
