<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<%String text = (String)request.getAttribute("text");
    if(text == null) text = "";
%>
<section class="vh-100" style="background-color: #508bfc;">
    <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                <div class="card shadow-2-strong" style="border-radius: 1rem;">
                    <div class="card-body p-5 text-center">

                    <h3 class="mb-5">Sign Up</h3>

                    <form action="RegisterServlet" method="post" class="mb-4">
                        <input type="text" name="username" class="form-control form-control-lg mb-4" maxlength="50" placeholder="Username" required />
                        <input type="password" name="password" class="form-control form-control-lg mb-4" placeholder="Password" required pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                               title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters"/>
                        <input type="text" name="firstname" class="form-control form-control-lg mb-4" maxlength="30" placeholder="First Name" required />
                        <input type="text" name="lastname" class="form-control form-control-lg mb-4" maxlength="30" placeholder="Last Name" required />
                        <input class="btn btn-primary btn-lg btn-block" type="submit" value="Sign Up">
                    </form>
                    <label style="color: red;"><%=text%></label>
                    <hr class="my-4">
                    <a href="login.jsp"> Sign in </a>
                </div>
            </div>
        </div>
    </div>
</div>
</section>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>
