package com.example.trabalhoweb.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.StringTokenizer;

@Controller
public class ClienteController {

    Boolean token = false;
    ProdutoRepositorio repositorio;
    Carrinho carrinho = new Carrinho(new ArrayList<Produto>());


    ClienteController(ProdutoRepositorio repositorio2) {
        this.repositorio = repositorio2;
    }

    @RequestMapping("/listaprodutos")
    public void axios(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(request.getSession().getAttribute("tokenCliente") != null){
            token = (boolean)request.getSession().getAttribute("tokenCliente");

        }



        if(token){
            ArrayList<Produto> arrayList = (ArrayList<Produto>) repositorio.findAll();
            response.getWriter().println("<html>");
            response.getWriter().println("<head>");
            response.getWriter().println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">");
            response.getWriter().println("</head>");
            response.getWriter().println("<body>");

            response.getWriter().println("<table class=\"table\">");
            response.getWriter().println("<thead>");


            response.getWriter().println("<tr>");
            response.getWriter().println("<th scope=\"col\">Nome</th>");
            response.getWriter().println("<th scope=\"col\">Pre??o</th>");
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
                //setar parametro e enviar
                if (p.getEstoque() == 0) {
                    response.getWriter().println("<td>Sem estoque</td>");
                } else {
                    response.getWriter().println("<td><a class=\"nav-link\"href=\"addcarrinho?id=" + p.getId() + "&comand=add" + "\">Adicionar</a></td>");

                }
                response.getWriter().println();
                response.getWriter().println("</tr>");
            }
            response.getWriter().println("</tbody>");
            response.getWriter().println("</table>");
            response.getWriter().println("<a class=\"nav-link\"href=\"carrinho\">Ver Carrinho</a>");
            response.getWriter().println("<a class=\"nav-link\"href=\"/logout\">Sair</a>");
            response.getWriter().println("</body>");
            response.getWriter().println("</html>");

        }else{
            response.getWriter().println("realize o login para prosseguir!");
        }




    }

    @RequestMapping("/addcarrinho")
    public void doCarrinho(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if(token){
            int id = Integer.parseInt(request.getParameter("id"));
            Produto produto = repositorio.findById(id).get();

            var comand = request.getParameter("comand");


            if (request.getCookies() == null){
                Cookie cookie = new Cookie("carrinho", "");
                cookie.setMaxAge(60 * 60 * 48);
                response.addCookie(cookie);
            }


            Cookie[] cookies = request.getCookies();


            if (produto == null) {
                response.getWriter().println("produto inexistente");
            } else {
                if (comand.equals("add")) {
                    carrinho.addProduto(produto);
                    cookies[0].setValue(cookies[0].getValue() + produto.getId() + "/");
                    response.addCookie(cookies[0]);
                    response.sendRedirect("/listaprodutos");


                } else if (comand.equals("remove")) {
                    String cookieAux = "";
                    String string = cookies[0].getValue();
                    String[] tokens = string.split("/");
                    boolean achou = false;
                    for (int i = 0; i < tokens.length; i++) {
                        if (tokens[i].equals(String.valueOf(id)) && !achou) {
                            carrinho.removeProduto(id);
                            achou = true;

                        } else {
                            cookieAux += tokens[i] + "/";
                        }
                    }
                    cookies[0].setValue(cookieAux);
                    response.addCookie(cookies[0]);
                    RequestDispatcher encaminhar = request.getRequestDispatcher("/carrinho");
                    encaminhar.forward(request, response);


                }

            }

        }else {
            response.getWriter().println("Realize o login para prosseguir!");
        }



    }

    @RequestMapping("/carrinho")
    public void carrinho(HttpServletRequest request, HttpServletResponse response) throws IOException {



        if(token){
            Cookie[] cookie = request.getCookies();
            if (!cookie[0].getValue().equals("")) {


                StringTokenizer st = new StringTokenizer(cookie[0].getValue(), "/");

                response.getWriter().println("<html>");
                response.getWriter().println("<head>");
                response.getWriter().println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">");
                response.getWriter().println("</head>");
                response.getWriter().println("<body>");

                response.getWriter().println("<table class=\"table\">");
                response.getWriter().println("<thead>");


                response.getWriter().println("<tr>");
                response.getWriter().println("<th scope=\"col\">Nome</th>");
                response.getWriter().println("<th scope=\"col\">Pre??o</th>");
                response.getWriter().println("<th scope=\"col\">Id</th>");
                response.getWriter().println("</tr>");
                response.getWriter().println("</thead>");
                response.getWriter().println("<tbody>");

                while (st.hasMoreTokens()) {
                    Produto p = repositorio.findById(Integer.parseInt(st.nextToken())).get();

                    response.getWriter().println("<tr>");

                    response.getWriter().println("<td>" + p.getNome() + "</td>");
                    response.getWriter().println("<td>" + p.getPreco() + "</td>");
                    response.getWriter().println("<td>" + p.getId() + "</td>");

                    response.getWriter().println("<td><a class=\"nav-link\"href=\"addcarrinho?id=" + p.getId() + "&comand=remove" + "\">Remover</a></td>");
                    //setar parametro e enviar
                    response.getWriter().println();
                    response.getWriter().println("</tr>");
                }
                response.getWriter().println("</tbody>");
                response.getWriter().println("</table>");
                response.getWriter().println("<a href=\"finalizarPedido\"><button class=\"btn-primary\">Finalizar</button></a>");
                response.getWriter().println("<a class=\"nav-link\"href=\"listaprodutos\">Produtos</a>");

                response.getWriter().println("</body>");
                response.getWriter().println("</html>");

            } else if (cookie[0].getValue().equals("")) {
                response.getWriter().println("Carrinho Vazio!");
            }

        }else{
            response.getWriter().println("realize o login para prosseguir!");
        }



    }

    @RequestMapping("/finalizarPedido")
    public void finalizar(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if(token){

            Cookie[] cookies = request.getCookies();
            cookies[0].setValue("");

            ArrayList<Produto> arrayList = carrinho.getProdutos();
            for (Produto p : arrayList) {
                p.diminuiEstoque();
                repositorio.flush();

            }
            response.addCookie(cookies[0]);
            response.getWriter().println("Pedido Realizado!");
            response.sendRedirect("/listaprodutos");
        }else{
            response.getWriter().println("Realize o login para prosseguir!");
        }

    }

}
