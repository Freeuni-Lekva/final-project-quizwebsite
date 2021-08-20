<%@ page import="DAO.MessageDao" %>
<%@ page import="user.User" %>
<%@ page import="mailbox.Message" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>animated chat window - Bootdey.com</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src=
                    "http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js">
    </script>
</head>
<body>
    <%
if (session.getAttribute("currUser") == null) response.sendRedirect("login.jsp");
MessageDao dao = (MessageDao) request.getServletContext().getAttribute("MessageDao");
    User currUser = (User)session.getAttribute("currUser");
    User user = (User) request.getAttribute("user");
    String username = "";
    username = user.getUsername();
    List<Message> messageList = null;
    try {
        messageList = dao.getConversation(currUser.getId(), user.getId());
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
%>

    <nav class="navbar navbar-expand-lg navbar-light bg-light" style="margin-top:0; margin-bottom : 1.5%">
        <a class="navbar-brand" href="<%=request.getContextPath()%>/quizzes.jsp">Quiz Website</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-item nav-link active" href="<%=request.getContextPath()%>/UserServlet?username=<%=currUser.getUsername()%>">My Profile <span class="sr-only">(current)</span></a>
                <a class="nav-item nav-link" href="<%=request.getContextPath()%>/FriendRequestsServlet?userId=<%=currUser.getId()%>">Friend Requests</a>
                <a class="nav-item nav-link" href="<%=request.getContextPath()%>/addQuizServlet?userId=<%=currUser.getId()%>">Create Quiz</a>

                <a class="nav-item nav-link mr-auto" href="<%=request.getContextPath()%>/LogoutServlet">Sign out</a>

            </div>
        </div>
    </nav>
<div class="chat_window">



    <div class="bottom_wrapper clearfix">
        <form action='MessengerServlet' method='post'>
            <div class="message_input_wrapper">
                <input name="msg_text" type="text" class="message_input" placeholder="Type your message here...">
                <input name="from_user_id" type="hidden" value="<%=currUser.getId()%>">
                <input id="to_user_id" name="to_user_id" type="hidden" value="<%=user.getId()%>">
            </div>
            <input type="submit" class="send_message" value="Send">
        </form>
    </div>


    <div class="top_menu" style="text-align: center">
        <a class="title" href="<%=request.getContextPath() + "/UserServlet?username=" + user.getUsername()%>"><%=user.getUsername()%></a>
    </div>
    <ul class="messages">

            <%
                if (messageList != null) {
                    for(Message m : messageList) {
                        if (m.getFromId() == user.getId()) {
                            out.println("<li class='message right appeared'>");
                        } else {
                            out.println("<li class='message left appeared'>");
                        }
                        out.println("<div class='avatar'></div>");
                        out.println("<div class='text_wrapper'>");
                        out.println("<div class='text'>" + m.getMessage() + "</div></div></li>");
                    }
                }
            %>



</div>
<div class="message_template">
    <li class="message">
        <div class="avatar"></div>
        <div class="text_wrapper">
            <div class="text"></div>
        </div>
    </li>
</div>

<style type="text/css">
    * {
        box-sizing: border-box;
    }

    body {
        background-color: #508bfc;
        font-family: "Calibri", "Roboto", sans-serif;
    }

    .chat_window {
        position: absolute;
        width: calc(100% - 20px);
        max-width: 800px;
        height: 500px;
        border-radius: 10px;
        background-color: #fff;
        left: 50%;
        top: 50%;
        transform: translateX(-50%) translateY(-50%);
        box-shadow: 0 10px 20px rgba(0, 0, 0, 0.15);
        background-color: #f8f8f8;
        overflow: hidden;
    }


    .top_menu {
        background-color: #fff;
        width: 100%;
        padding: 20px 0 15px;
        box-shadow: 0 1px 30px rgba(0, 0, 0, 0.1);
    }
    .top_menu .buttons {
        margin: 3px 0 0 20px;
        position: absolute;
    }
    .top_menu .buttons .button {
        width: 16px;
        height: 16px;
        border-radius: 50%;
        display: inline-block;
        margin-right: 10px;
        position: relative;
    }
    .top_menu .buttons .button.close {
        background-color: #f5886e;
    }
    .top_menu .buttons .button.minimize {
        background-color: #fdbf68;
    }
    .top_menu .buttons .button.maximize {
        background-color: #a3d063;
    }
    .top_menu .title {
        text-align: center;
        color: #bcbdc0;
        font-size: 20px;
    }

    .messages {
        position: relative;
        list-style: none;
        padding: 20px 10px 0 10px;
        margin: 0;
        height: 347px;
        overflow: scroll;
    }
    .messages .message {
        clear: both;
        overflow: hidden;
        margin-bottom: 20px;
        transition: all 0.5s linear;
        opacity: 0;
    }
    .messages .message.left .avatar {
        background-color: #f5886e;
        float: left;
    }
    .messages .message.left .text_wrapper {
        background-color: #ffe6cb;
        margin-left: 20px;
    }
    .messages .message.left .text_wrapper::after, .messages .message.left .text_wrapper::before {
        right: 100%;
        border-right-color: #ffe6cb;
    }
    .messages .message.left .text {
        color: #c48843;
    }
    .messages .message.right .avatar {
        background-color: #fdbf68;
        float: right;
    }
    .messages .message.right .text_wrapper {
        background-color: #c7eafc;
        margin-right: 20px;
        float: right;
    }
    .messages .message.right .text_wrapper::after, .messages .message.right .text_wrapper::before {
        left: 100%;
        border-left-color: #c7eafc;
    }
    .messages .message.right .text {
        color: #45829b;
    }
    .messages .message.appeared {
        opacity: 1;
    }
    .messages .message .avatar {
        width: 60px;
        height: 60px;
        border-radius: 50%;
        display: inline-block;
    }
    .messages .message .text_wrapper {
        display: inline-block;
        padding: 20px;
        border-radius: 6px;
        width: calc(100% - 85px);
        min-width: 100px;
        position: relative;
    }
    .messages .message .text_wrapper::after, .messages .message .text_wrapper:before {
        top: 18px;
        border: solid transparent;
        content: " ";
        height: 0;
        width: 0;
        position: absolute;
        pointer-events: none;
    }
    .messages .message .text_wrapper::after {
        border-width: 13px;
        margin-top: 0px;
    }
    .messages .message .text_wrapper::before {
        border-width: 15px;
        margin-top: -2px;
    }
    .messages .message .text_wrapper .text {
        font-size: 18px;
        font-weight: 300;
    }

    .bottom_wrapper {
        position: relative;
        width: 100%;
        background-color: #fff;
        padding: 20px 20px;
        position: absolute;
        bottom: 0;
    }
    .bottom_wrapper .message_input_wrapper {
        display: inline-block;
        height: 50px;
        border-radius: 25px;
        border: 1px solid #bcbdc0;
        width: calc(100% - 160px);
        position: relative;
        padding: 0 20px;
    }
    .bottom_wrapper .message_input_wrapper .message_input {
        border: none;
        height: 100%;
        box-sizing: border-box;
        width: calc(100% - 40px);
        position: absolute;
        outline-width: 0;
        color: gray;
    }
    .find_friend{
        width: 120px;
        height: 30px;
        display: inline-block;
        border-radius: 50px;
        background-color: #508bfc;
        border: 2px solid #073180;
        color: #fff;
        cursor: pointer;
        transition: all 0.2s linear;
        text-align:center;
        float: center;
    }
    .bottom_wrapper .send_message {
        width: 140px;
        height: 50px;
        display: inline-block;
        border-radius: 50px;
        background-color: #508bfc;
        border: 2px solid #073180;
        color: #fff;
        cursor: pointer;
        transition: all 0.2s linear;
        text-align: center;
        float: right;
    }
    .bottom_wrapper .send_message:hover {
        color: #a3d063;
        background-color: #fff;
    }
    .bottom_wrapper .send_message .text {
        font-size: 18px;
        font-weight: 300;
        display: inline-block;
        line-height: 48px;
    }

    .message_template {
        display: none;
    }

</style>
