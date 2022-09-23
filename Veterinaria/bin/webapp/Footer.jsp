<%@ page import = "java.io.*,java.util.*" %>

<footer class="text-center bg-dark text-white py-4 " style="vertical-align:bottom">
    <p id="footerText" class="m-0">Todos los derechos reservados © - <% 
    Date date = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    out.print( calendar.get(Calendar.YEAR)); %></p>
</footer>