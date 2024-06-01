package com.example.bloperso.Service;

import com.example.bloperso.Entities.*;
import com.example.bloperso.dao.BloggerRepository;
import com.example.bloperso.dao.CommentRepository;
import com.example.bloperso.dao.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service

public class BloggerService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BloggerRepository bloggerRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;








    private static Long idBlogger ;


    public static void SetupB(Long i){
        idBlogger=i;
    }
    public void reg(Blogger bloggerDto) {
        bloggerDto.setPassword(passwordEncoder.encode(bloggerDto.getPassword()));
        bloggerRepository.save(bloggerDto);
    }


    public Blogger getBloggerInfo(Long id){
       Optional<Blogger> b= bloggerRepository.findById(id);

        return b.get();
    }

    public void comment(Comment c,Long idB,Long idP){
        Comment cnew = new Comment();
        cnew.setContent(c.getContent());


        cnew.setBlogger(bloggerRepository.findById(idB).orElse(null));
        Optional<Post> pp =  postRepository.findById(idP);
        if (pp.isEmpty()) {
            return ;
        }
        Post p = pp.get();
        cnew.setPost(p);
        cnew.setCreated(Date.valueOf(LocalDate.now()));
        p.addComment(cnew);
        commentRepository.save(cnew);
        postRepository.save(p);

    }
    public Boolean like(long idP, long idB){
        Post p = postRepository.findById(idP).orElse(null);
        Blogger blogger = bloggerRepository.findById(idB).orElse(null);
        if(p.getLikers().contains(blogger)) {
            p.removeLiker(blogger);
            blogger.removeLikedpost(p);
            bloggerRepository.save(blogger);
            postRepository.save(p);
            return false;
        }else{
            p.addLiker(blogger);
            blogger.addLikedpost(p);
            bloggerRepository.save(blogger);
            postRepository.save(p);
            return true;
        }

    }
    public Boolean BookMark(Long idP,Long idB){
        Post p = postRepository.findById(idP).orElse(null);
        Blogger blogger = bloggerRepository.findById(idB).orElse(null);
        if (blogger.getBookMarks().contains(p)){
            blogger.removeBookMark(p);
            bloggerRepository.save(blogger);
            return false;
        }else {
            blogger.AddBookMark(p);
            bloggerRepository.save(blogger);
            return true;
        }

    }
    public Blogger getByUsername(String username){
        return bloggerRepository.findBloggerByUsername(username);
    }


    public List<Post> Bookmarked(){

        return bloggerRepository.
                findById(idBlogger)
                .get()
                .getBookMarks();
    }
    public String AddFriendReq(Long idRe)
    {

        Blogger b = bloggerRepository.findById(idRe).orElse(null);
        Blogger sen = bloggerRepository.findById(idBlogger).orElse(null);
        if(b.getFriends().contains(sen)) {

            return "already friends lol";
        }
        if(b.getFriendRequest().contains(sen)) {b.RemoveRequest(sen);
        bloggerRepository.save(b);
        return "removed friend request";
        } else {
            b.AddRequest(sen);
            bloggerRepository.save(b);
            return "added friend request";
        }

    }

    public void removePost(Long idP ){
        if (postRepository.findById(idP).isPresent()) {
            Post post = postRepository.findById(idP).orElse(null);
            for (Blogger liker : post.getLikers()) {
                liker.getLikedPosts().remove(post);
                bloggerRepository.save(liker); // Save changes to the liker
            }
            Blogger b = bloggerRepository.findById(idBlogger).orElse(null);
            b.removePost(post);
            bloggerRepository.save(b);
            postRepository.deleteById(idP);
        }
    }

    public List<Post> ownPosts(Long idB){
        return postRepository.findAll().stream().filter(e->e.getBlogger().getId().equals(idB)).toList();
    }

    public Map<String,Integer> getCountC(Long id){
        Map<String,Integer> count = new HashMap<>();
        for (PostCategorie c : PostCategorie.values()){
            count.put(c.name(),ownPosts(id).stream().filter(cat ->cat.getTheme()==c).toList().size());
        }


        return count;
    }

    public void refuseRequest(Long idB){
        Blogger b = bloggerRepository.findById(idB).orElse(null);
        Blogger sen = bloggerRepository.findById(idBlogger).orElse(null);
        b.RemoveRequest(sen);
        sen.RemoveRequest(b);
        bloggerRepository.save(b);
        bloggerRepository.save(sen);
    }


    public void becomeFriends(Long idB){
        Blogger b = bloggerRepository.findById(idB).orElse(null);
        Blogger sen = bloggerRepository.findById(idBlogger).orElse(null);
        b.addFriend(sen);
        sen.addFriend(b);
        sen.RemoveRequest(b);
        b.RemoveRequest(sen);
        bloggerRepository.save(b);
        bloggerRepository.save(sen);
    }

    public void update(Blogger b) {
        bloggerRepository.save(b);
    }
}
