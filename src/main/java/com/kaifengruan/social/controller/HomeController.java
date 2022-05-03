package com.kaifengruan.social.controller;

import com.kaifengruan.social.POJO.*;
import com.kaifengruan.social.dao.*;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class HomeController {
    @Autowired
    UserDao userDao;
    @Autowired
    AdminDao adminDao;
    @Autowired
    CommentDao commentDao;
    @Autowired
    PostDao postDao;
    @Autowired
    ConnectDao connectDao;
    @Autowired
    LikeDao likeDao;

    @GetMapping("/")
    public ModelAndView homePage(){
        List<Post> posts = postDao.findAll();
        Collections.sort(posts, (Post a, Post b)->{if(a.getPost_updated().after(b.getPost_updated())) return -1; return 1;});
        return new ModelAndView("home", "posts", posts);
    }

    @GetMapping("/admin")
    public ModelAndView createAdmin(HttpServletRequest request){
        Admin admin = adminDao.findByEmail("admin@admin.com");
        if( admin == null){
            admin.setEmail("admin@admin.com");
            admin.setPassword("admin");
            admin.setUsername("admin");
            adminDao.save(admin);
        }
        request.getSession().setAttribute("admin", admin);
        List<Post> posts = postDao.findAll();
        Collections.sort(posts, (Post a, Post b)->{if(a.getPost_updated().after(b.getPost_updated())) return -1; return 1;});
        return new ModelAndView("control", "posts", posts);
    }

    @RequestMapping(value = "/login.htm", method= RequestMethod.GET)
    public String index(){
        return "login";
    }

    @GetMapping("/create.htm")
    public ModelAndView createUser(){
        return new ModelAndView("create");
    }

    @PostMapping("/register.htm")
    public ModelAndView register(@RequestParam String email, @RequestParam String username, @RequestParam String password){
        if(email.equals("") || username.equals("") || password.equals("")){
            String msg = "Attribute can not be empty!";
            return new ModelAndView("registerErr", "msg", msg);
        }
        if(!EmailValidator.getInstance().isValid(email)){
            String msg = "Email is invalid!";
            return new ModelAndView("registerErr", "msg", msg);
        }
        if(userDao.findByEmail(email) != null){
            String msg = "Email has been registered!";
            return new ModelAndView("registerErr", "msg", msg);
        }
        if(userDao.findByUsername(username) != null){
            String msg = "User name has been used!";
            return new ModelAndView("registerErr", "msg", msg);
        }
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        userDao.save(user);
        return new ModelAndView("registerSuc", "user", user.getUsername());
    }

    @PostMapping("/login.htm")
    public ModelAndView login(@RequestParam String email, @RequestParam String password, HttpServletRequest request){
        if(email.equals("") || password.equals("")){
            String msg = "Attribute can not be empty!";
            return new ModelAndView("loginErr", "msg", msg);
        }
        Admin admin = adminDao.findByEmailAndPassword(email, password);
        if(admin != null){
            List<Post> posts = postDao.findAll();
            request.getSession().setAttribute("admin", admin);
            return new ModelAndView("control", "posts", posts);
        }
        if(!EmailValidator.getInstance().isValid(email) || userDao.findByEmail(email) == null){
            String msg = "Email is invalid!";
            return new ModelAndView("loginErr", "msg", msg);
        }
        User user = userDao.findByEmail(email);
        if(!user.getPassword().equals(password)){
            String msg = "Password wrong!";
            return new ModelAndView("loginErr", "msg", msg);
        }
        request.getSession().setAttribute("user", user);
        List<Connect> conn = user.getFollows();
        List<Post> posts = new ArrayList<>();
        for(Connect cn : conn){
            String connectorId = cn.getConnectorId();
            for(Post p : userDao.findByUserId(connectorId).getPosts()){
                Collections.sort(p.getComments(), (Comment a, Comment b)->{if(a.getPost_created().after(b.getPost_created())) return -1; return 1;});
                Collections.sort(p.getLikes(), (Like a, Like b)->{if(a.getPost_created().after(b.getPost_created())) return -1; return 1;});
                posts.add(p);
            }
        }
        Collections.sort(posts, (Post a, Post b)->{if(a.getPost_updated().after(b.getPost_updated())) return -1; return 1;});
        return new ModelAndView("myFollows", "posts", posts);
    }

    @GetMapping("/myPost.htm")
    public ModelAndView myPost(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null) {
            String msg = "No user information!";
            return new ModelAndView("loginErr", "msg", msg);
        }
        request.getSession().setAttribute("user", user);
        List<Post> posts = postDao.findAllByUser(user);
        Collections.sort(posts, (Post a, Post b)->{if(a.getPost_created().after(b.getPost_created())) return -1; return 1;});
        for(Post p : posts){
            Collections.sort(p.getComments(), (Comment a, Comment b)->{if(a.getPost_created().after(b.getPost_created())) return -1; return 1;});
            Collections.sort(p.getLikes(), (Like a, Like b)->{if(a.getPost_created().after(b.getPost_created())) return -1; return 1;});
        }
        return new ModelAndView("myPosts", "posts", posts);
    }

    @GetMapping("/myFollows.htm")
    public ModelAndView myFollows(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null) {
            String msg = "No user information!";
            return new ModelAndView("loginErr", "msg", msg);
        }
        request.getSession().setAttribute("user", user);
        List<Connect> conn = connectDao.findAllByUser(user);
        List<Post> posts = new ArrayList<>();
        for(Connect cn : conn){
            String connectorId = cn.getConnectorId();
            List<Post> l = postDao.findAllByUser(userDao.findByUserId(connectorId));
            for(Post p : l){
                Collections.sort(p.getComments(), (Comment a, Comment b)->{if(a.getPost_created().after(b.getPost_created())) return -1; return 1;});
                Collections.sort(p.getLikes(), (Like a, Like b)->{if(a.getPost_created().after(b.getPost_created())) return -1; return 1;});
                posts.add(p);
            }
        }
        Collections.sort(posts, (Post a, Post b)->{if(a.getPost_updated().after(b.getPost_updated())) return -1; return 1;});
        return new ModelAndView("myFollows", "posts", posts);
    }

    @GetMapping("/worldPost.htm")
    public ModelAndView worldPost(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null) {
            String msg = "No user information!";
            return new ModelAndView("loginErr", "msg", msg);
        }
        List<Connect> conn = connectDao.findAllByUser(user);
        Set<String> set = new HashSet<>();
        for(Connect cn : conn){
            set.add(cn.getConnectorId());
        }
        set.add(user.getUserId());
        List<Post> posts = postDao.findAll();
        List<Post> psts = new ArrayList<>();
        for(Post p : posts){
            String curId = p.getUser().getUserId();
            if(set.contains(curId)) continue;
            psts.add(p);
        }
        Collections.sort(psts, (Post a, Post b)->{if(a.getPost_updated().after(b.getPost_updated())) return -1; return 1;});
        for(Post p : psts){
            Collections.sort(p.getComments(), (Comment a, Comment b)->{if(a.getPost_created().after(b.getPost_created())) return -1; return 1;});
            Collections.sort(p.getLikes(), (Like a, Like b)->{if(a.getPost_created().after(b.getPost_created())) return -1; return 1;});
        }
        request.getSession().setAttribute("user", user);
        return new ModelAndView("worldPosts", "posts", psts);
    }

    @PostMapping("/pushPost.htm")
    public ModelAndView pushPost(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null) {
            String msg = "No user information!";
            return new ModelAndView("loginErr", "msg", msg);
        }
        String content = request.getParameter("content");
        if(content != null && !content.equals("")){
            Post post = new Post();
            post.setContent(content);
            post.setUser(user);
            Date date = new Date();
            post.setPost_created(date);
            post.setPost_updated(date);
            user.getPosts().add(post);
            postDao.save(post);
        }
        request.getSession().setAttribute("user", user);
        List<Post> posts = postDao.findAllByUser(user);
        Collections.sort(posts, (Post a, Post b)->{if(a.getPost_created().after(b.getPost_created())) return -1; return 1;});
        for(Post p : posts){
            Collections.sort(p.getComments(), (Comment a, Comment b)->{if(a.getPost_created().after(b.getPost_created())) return -1; return 1;});
            Collections.sort(p.getLikes(), (Like a, Like b)->{if(a.getPost_created().after(b.getPost_created())) return -1; return 1;});
        }
        return new ModelAndView("myPosts", "posts", posts);

    }

    @PostMapping("/connect.htm")
    public ModelAndView connect(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null) {
            String msg = "Connect ERR!";
            return new ModelAndView("loginErr", "msg", msg);
        }
        String connectId = request.getParameter("connector_id");
        Connect conn = new Connect();
        conn.setUser(user);
        conn.setConnectorId(connectId);
        user.getFollows().add(conn);
        connectDao.save(conn);
        request.getSession().setAttribute("user", user);
        List<Connect> con = connectDao.findAllByUser(user);
        List<Post> posts = new ArrayList<>();
        for(Connect cn : con){
            String connectorId = cn.getConnectorId();
            List<Post> l = postDao.findAllByUser(userDao.findByUserId(connectorId));
            for(Post p : l){
                posts.add(p);
            }
        }
        Collections.sort(posts, (Post a, Post b)->{if(a.getPost_updated().after(b.getPost_updated())) return -1; return 1;});
        return new ModelAndView("myFollows", "posts", posts);
    }

    @PostMapping("/deletePost.htm")
    public ModelAndView deletePost(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null) {
            String msg = "Login ERR!";
            return new ModelAndView("loginErr", "msg", msg);
        }
        String postId = request.getParameter("postId");
        Post post = postDao.findByPostId(postId);
        List<Long> cc = new ArrayList<>();
        for(Comment c : post.getComments()){
            cc.add(c.getId());
        }
        for(long cid : cc){
            Comment cur = commentDao.findById(cid);
            post.getComments().remove(cur);
            commentDao.delete(cur.getId());
        }
        List<Long> ll = new ArrayList<>();
        for(Like l : post.getLikes()){
            ll.add(l.getId());
        }
        for(long lid : ll){
            Like cur = likeDao.findById(lid);
            if(cur != null){
                post.getLikes().remove(cur);
            }
            if(cur != null){
                likeDao.delete(cur.getId());
            }

        }
        user.getPosts().remove(post);
        postDao.delete(post);
        request.getSession().setAttribute("user", user);
        List<Post> posts = postDao.findAllByUser(user);
        Collections.sort(posts, (Post a, Post b)->{if(a.getPost_created().after(b.getPost_created())) return -1; return 1;});
        return new ModelAndView("myPosts", "posts", posts);
    }

    @PostMapping("/adminDelete.htm")
    public ModelAndView adminDelete(HttpServletRequest request){
        Admin admin = (Admin)request.getSession().getAttribute("admin");
        if(admin == null) {
            String msg = "Login ERR!";
            return new ModelAndView("loginErr", "msg", msg);
        }

        Post post = postDao.findByPostId(request.getParameter("postId"));
        User user = userDao.findByUserId(request.getParameter("postUserId"));
        List<Long> cc = new ArrayList<>();
        for(Comment c : post.getComments()){
            cc.add(c.getId());
        }
        for(long cid : cc){
            Comment cur = commentDao.findById(cid);
            post.getComments().remove(cur);
            commentDao.delete(cur.getId());
        }
        List<Long> ll = new ArrayList<>();
        for(Like l : post.getLikes()){
            ll.add(l.getId());
        }
        for(long lid : ll){
            Like cur = likeDao.findById(lid);
            post.getLikes().remove(cur);
            likeDao.delete(cur.getId());
        }
        user.getPosts().remove(post);
        postDao.delete(post);
        request.getSession().setAttribute("admin", admin);
        List<Post> posts = postDao.findAll();
        Collections.sort(posts, (Post a, Post b)->{if(a.getPost_created().after(b.getPost_created())) return -1; return 1;});
        return new ModelAndView("control", "posts", posts);
    }

    @PostMapping("/unfollow.htm")
    public ModelAndView unfollow(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null) {
            String msg = "Connect ERR!";
            return new ModelAndView("loginErr", "msg", msg);
        }
        String connectId = request.getParameter("connector_id");
        Connect conn = connectDao.findByConnectorIdAndUser(connectId, user);
        if(conn != null){
            deleteRelationship(user, connectId);
            user.getFollows().remove(conn);
            connectDao.delete(conn.getId());
        }

        List<Connect> con = connectDao.findAllByUser(user);
        List<Post> posts = new ArrayList<>();
        for(Connect cn : con){
            String connectorId = cn.getConnectorId();
            List<Post> l = postDao.findAllByUser(userDao.findByUserId(connectorId));
            for(Post p : l){
                Collections.sort(p.getComments(), (Comment a, Comment b)->{if(a.getPost_created().after(b.getPost_created())) return -1; return 1;});
                Collections.sort(p.getLikes(), (Like a, Like b)->{if(a.getPost_created().after(b.getPost_created())) return -1; return 1;});
                posts.add(p);
            }
        }
        Collections.sort(posts, (Post a, Post b)->{if(a.getPost_updated().after(b.getPost_updated())) return -1; return 1;});
        request.getSession().setAttribute("user", user);

        return new ModelAndView("myFollows", "posts", posts);
    }

    public void deleteRelationship(User user, String connector_id){
        User tmp = userDao.findByUserId(connector_id);
        List<Post> posts = postDao.findAllByUser(tmp);
        for(Post p : posts){
            List<Long> commentId = new ArrayList<>();
            for(Comment cmt : p.getComments()){
                if(cmt.getUsername().equals(user.getUsername())){
                    commentId.add(cmt.getId());
                }
            }
            for(long cid : commentId){
                Comment cur = commentDao.findById(cid);
                p.getComments().remove(cur);
                commentDao.delete(cur.getId());
            }

            Like like = likeDao.findByUsernameAndPost(user.getUsername(), p);
            if(like != null) {
                p.getLikes().remove(like);
                likeDao.delete(like.getId());
            }
        }
    }

    @PostMapping("/comment.htm")
    public ModelAndView comment(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        Post post = postDao.findByPostId(request.getParameter("postId"));
        if(user == null) {
            String msg = "Connect ERR!";
            return new ModelAndView("loginErr", "msg", msg);
        }
        String content = request.getParameter("comment");
        if(content != null && !content.equals("")){
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setUsername(user.getUsername());
            comment.setPost_created(new Date());
            comment.setPost(post);
            post.getComments().add(comment);
            commentDao.save(comment);
        }
        request.getSession().setAttribute("user", user);
        List<Connect> con = connectDao.findAllByUser(user);
        List<Post> posts = new ArrayList<>();
        for(Connect cn : con){
            String connectorId = cn.getConnectorId();
            List<Post> l = postDao.findAllByUser(userDao.findByUserId(connectorId));
            for(Post p : l){
                Collections.sort(p.getComments(), (Comment a, Comment b)->{if(a.getPost_created().after(b.getPost_created())) return -1; return 1;});
                Collections.sort(p.getLikes(), (Like a, Like b)->{if(a.getPost_created().after(b.getPost_created())) return -1; return 1;});
                posts.add(p);
            }
        }
        Collections.sort(posts, (Post a, Post b)->{if(a.getPost_updated().after(b.getPost_updated())) return -1; return 1;});
        return new ModelAndView("myFollows", "posts", posts);
    }

    @PostMapping("/like.htm")
    public ModelAndView like(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        Post post = postDao.findByPostId(request.getParameter("postId"));
        if(user == null) {
            String msg = "Connect ERR!";
            return new ModelAndView("loginErr", "msg", msg);
        }
        Like cur = likeDao.findByUsernameAndPost(user.getUsername(), post);
        if(cur != null){
            post.getLikes().remove(cur);
            likeDao.delete(cur.getId());
        }else{
            Like like = new Like();
            like.setUsername(user.getUsername());
            like.setPost(post);
            like.setPost_created(new Date());
            post.getLikes().add(like);
            likeDao.save(like);
        }

        request.getSession().setAttribute("user", user);
        List<Connect> con = connectDao.findAllByUser(user);
        List<Post> posts = new ArrayList<>();
        for(Connect cn : con){
            String connectorId = cn.getConnectorId();
            List<Post> l = postDao.findAllByUser(userDao.findByUserId(connectorId));
            for(Post p : l){
                Collections.sort(p.getComments(), (Comment a, Comment b)->{if(a.getPost_created().after(b.getPost_created())) return -1; return 1;});
                Collections.sort(p.getLikes(), (Like a, Like b)->{if(a.getPost_created().after(b.getPost_created())) return -1; return 1;});
                posts.add(p);
            }
        }
        Collections.sort(posts, (Post a, Post b)->{if(a.getPost_updated().after(b.getPost_updated())) return -1; return 1;});
        return new ModelAndView("myFollows", "posts", posts);
    }

}
