<!DOCTYPE html>
<%@page language="java" import="com.kaifengruan.social.POJO.User,
com.kaifengruan.social.POJO.Post, java.util.*" %> <%@ taglib uri =
"http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta
      http-equiv="refresh"
      content="3;url=http://localhost:8080/myPost.htm"
    />
    <title>Success</title>
    <% User user = (User)session.getAttribute("user");
    request.getSession().setAttribute("user", user); %>
  </head>
  <body>
    <h1>Post successfully pushed!</h1>
  </body>
</html>
