<!DOCTYPE html> <%@page language="java"
import="com.kaifengruan.social.POJO.User,
com.kaifengruan.social.POJO.Post, java.util.*" %> <%@ taglib uri =
"http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<script type="text/javascript">
  $("#submit").click(function () {
    $("#pushPost").submit(function (e) {
      e.preventDefault(); // avoid to execute the actual submit of the form.
      var form = $(this);
      var actionUrl = form.attr("action");
      $.ajax({
        type: "POST",
        url: actionUrl,
        data: form.serialize(), // serializes the form's elements.
      });
    });
  });

  $("#delete").click(function () {
    $("#deleteForm").submit(function (e) {
      e.preventDefault(); // avoid to execute the actual submit of the form.
      var form = $(this);
      var actionUrl = form.attr("action");
      $.ajax({
        type: "POST",
        url: actionUrl,
        data: form.serialize(), // serializes the form's elements.
      });
    });
  });

</script>

<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <link href="resources/css/style.css" rel="stylesheet" type="text/css">
    <title>My posts</title>
    <% User user = (User)session.getAttribute("user");
    request.getSession().setAttribute("user", user); %>
  </head>
  <body>
  <center>
    <table>
      <tr>
        <td><a href="myFollows.htm">My Follows</a></td>
        <td><a href="worldPost.htm">World Posts</a></td>
        <td><a href="myPost.htm">My posts</a></td>
        <td><a href="http://localhost:8080/">Sign out</a></td>
      </tr>
    </table>

    <form id="pushPost" action="pushPost.htm" method="post">
      <textarea name="content" id="content" cols="30" rows="10"></textarea>
      <button id="submit">Post</button>
    </form>
    
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

      <tr>
        <td>
        <form id="deleteForm" action="/deletePost.htm" method="post">
        <input type="hidden" name="postId" value=${post.postId}>
        <button id="delete">Delete</button>
        </form>
        </td>
      </tr>
    </c:forEach>
    </table>
      
    </center>
  </body>
</html>
