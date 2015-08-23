<%@page import="jj.jjFileTxt"%>
<%@page import="java.io.BufferedWriter"%>
<%@page import="java.io.FileWriter"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>


<%
System.out.println("######################################");
System.out.println("call for index.jsp");
System.out.println("######################################");
    String address = request.getServletContext().getRealPath("/");
    File file = new File(address + "/"+"product.html" );
//    StringBuilder result = new StringBuilder(jjFileTxt.read(file).replace("\n", ""));
    StringBuilder result = new StringBuilder(jjFileTxt.read(file).replace("\n", ""));
    
    if (!file.exists()) {%>
<%= "is not bieng such as file"%>;
<%  } else {
        int index = result.indexOf("\"swName\"");//finding div in wich id="sw"
        if (index > -1) {
            index = result.indexOf(">", index); //finding end of "sw" div ( <div id="sw" clas="example" style="any" >
//            jjDatabaseWeb db;
//            Server.Connect();
//            db = Server.db;
            //            String content = cms.tools.Server.run(Server.getClazzes(), "Content", "sw", request, db, false);
            String content = " دوست عزیز سلام";
            //            String content = cms.tools.Server.run(request, re, false);
            result = result.insert(index+1,content);
        }
    }
%>
<%= result.toString()%>