<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.UserEntity" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Arrays" %>

<%
    // Пример данных для отображения
    List<UserEntity> users = new ArrayList<>();
    users.add(new UserEntity(1, "John", "john@example.com", "middle"));
    users.add(new UserEntity(2, "Alice", "alice@example.com", "senior"));
    users.add(new UserEntity(3, "Bob", "bob@example.com", "junior"));
    users.add(new UserEntity(4, "A", "noneed@example.com", ""));
%>

<html>
<head>
    <title>Admin Page</title>
</head>
<body>
<h1>Welcome Admin!</h1>

<h2>Database Content</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Role</th>
    </tr>
    <%-- Отображаем список пользователей --%>
    <% for (UserEntity user : users) { %>
    <tr>
        <td><%= user.getIdUser() %></td>
        <td><%= user.getName() %></td>
        <td><%= user.getEmail() %></td>
        <td><%= user.getRole() %></td>
    </tr>
    <% } %>
</table>

<%-- Добавим некоторые другие функции, такие как форма для добавления нового пользователя --%>
<h2>Change role for user</h2>
<form action="changeUser" method="post">
    Name: <input type="text" name="name"><br>
    Email: <input type="email" name="email"><br>
    Role: <input type="text" name="role"><br>
    <input type="submit" value="Change User">
</form>

<%-- Другие функции администратора могут быть добавлены здесь --%>
</body>
</html>
