<!DOCTYPE html>
<%@page language="java" import="com.kaifengruan.social.POJO.User, com.kaifengruan.social.POJO.Post, java.util.*" %> 
<%@ taglib uri ="http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>World posts</title>
    <link href="resources/css/style.css" rel="stylesheet" type="text/css">
  </head>
  <body>
  <center>
    <table>
      <tr>
        <td><a href="login.htm">Login</a></td>
      </tr>
    </table>

    <table>
    <c:forEach items="${requestScope.posts}" var="post">
      
      <tr>
      <td>User: </td>
      <td>${post.user.username}</td>
      </tr>
      <tr>
      <td>Post: </td>
      <td>${post.content}</td>
      </tr>

      <tr>
      <td>Date: </td>
      <td>${post.post_created}</td>
      </tr>
    
      <tr>
        <td>Liked by:</td>
        <td>
          <c:forEach items="${post.likes}" var="like">
          ${like.username}&nbsp
          </c:forEach><br>
        </td>
      </tr>

      <tr>
        <td>Comments:</td>
        <td></td>
      </tr>
      <c:forEach items="${post.comments}" var="comment">
        <tr>
          <td>
            ${comment.username}:
          </td>
          <td>
            ${comment.content}
          </td>
        </tr>
        
      </c:forEach>
    </c:forEach>
    </table>
    
  </center>
  </body>
</html>
