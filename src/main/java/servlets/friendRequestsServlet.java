package servlets;

import DAO.FriendRequestDao;
import DAO.MessageDao;
import DAO.UserDao;
import mailbox.FriendRequest;
import mailbox.Message;
import user.User;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/RequestServlet")
public class friendRequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        User user = (User) httpServletRequest.getServletContext().getAttribute("currUser");
        FriendRequestDao fDao = (FriendRequestDao) httpServletRequest.getServletContext().getAttribute("FriendRequestDao");
        UserDao uDao = (UserDao) httpServletRequest.getServletContext().getAttribute("UserDao");

        try {
            List<FriendRequest> requests = fDao.getFriendRequests(user.getId());
            List<User> friendReqs= new ArrayList<>();
            for(int i=0; i<requests.size(); i++){
                friendReqs.add(uDao.getUser(requests.get(i).getFromId()));
            }
            String s = friendReqs.get(0).getUsername();
            httpServletRequest.setAttribute("reqs", friendReqs);
            FriendRequest fr = new FriendRequest(user.getId(), u.getId());

            if(httpServletRequest.getParameter("Accept") !=null){
                User u =uDao.getUser(httpServletRequest.getParameter("userName1"))
                uDao.addFriend(user.getId(),u.getId());
                fDao.removeFriendRequest(fr);
            } else if(httpServletRequest.getParameter("Decline")!=null){
                fDao.removeFriendRequest(fr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        super.doPost(httpServletRequest, httpServletResponse);
    }
}
