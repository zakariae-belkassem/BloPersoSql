package com.example.bloperso.Service;

import com.example.bloperso.Entities.Blogger;
import com.example.bloperso.Entities.Comment;
import com.example.bloperso.Entities.Post;
import com.example.bloperso.Entities.PostCategorie;
import com.example.bloperso.dao.BloggerRepository;
import com.example.bloperso.dao.CommentRepository;
import com.example.bloperso.dao.PostRepository;
import com.example.bloperso.dto.BloggerDto;
import com.example.bloperso.dto.DtoMapper;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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
    private DtoMapper dtoMapper;
    @Autowired
    private BloggerRepository bloggerRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;


    private Long idBlogger = 1L;

    public void reg(BloggerDto bloggerDto) {
        Blogger blogger = dtoMapper.toEntity(bloggerDto);
        System.out.println(blogger.toString());
        bloggerRepository.save(blogger);
    }


    public Blogger getBloggerInfo(Long id){
       Optional<Blogger> b= bloggerRepository.findById(id);
        Map<String ,String> info = new HashMap<>() ;
        return null;
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
    public void like(long idP,long idB){
        Post p = postRepository.findById(idP).orElse(null);
        Blogger blogger = bloggerRepository.findById(idB).orElse(null);
        System.out.println("from like func "+idB +"  ++++id post "+idP);
        if(p.getLikers().contains(blogger)) {
            p.removeLiker(blogger);
            postRepository.save(p);
        }else{
            //p.getLikers().add(bloggerRepository.findById(idB).orElse(null));
            p.addLiker(blogger);
            postRepository.save(p);
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

    public List<Post> Bookmarked(){
        return bloggerRepository.
                findById(idBlogger)
                .get()
                .getBookMarks();
    }
    public void AddFriend(Long idRe)
    {
        Blogger b = bloggerRepository.findById(idRe).orElse(null);
        Blogger sen = bloggerRepository.findById(idBlogger).orElse(null);
        if(b.getFriendRequest().contains(sen)) b.RemoveRequest(sen);
        b.AddRequest(sen);
        bloggerRepository.save(b);

    }

    public void removePost(Long idP ){
        if (postRepository.findById(idP).isPresent()) postRepository.deleteById(idP);
    }

    public List<Post> ownPosts(Long idB){
        return postRepository.findAll().stream().filter(e->e.getBlogger().getId().equals(idB)).toList();
    }
    public BloggerDto getBloggerDto(Long id){
        return dtoMapper.toDto(bloggerRepository.findById(id).orElse(null));
    }
    public Map<String,Integer> getCountC(Long id){
        Map<String,Integer> count = new HashMap<>();
        for (PostCategorie c : PostCategorie.values()){
            count.put(c.name(),ownPosts(id).stream().filter(cat ->cat.getTheme()==c).toList().size());
        }


        return count;
    }
}
