package application.config;

import application.domain.Post;
import application.domain.User;
import application.dto.AuthorDTO;
import application.dto.CommentDTO;
import application.repository.PostRepository;
import application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        userRepository.deleteAll();
        postRepository.deleteAll();

        User maria = new User(null,"Maria Brown", "maria@gmail.com");
        User alex = new User(null,"Alex Green", "alex@gmail.com");
        User bob = new User(null,"Bob Grey", "bob@gmail.com");
        userRepository.saveAll((Arrays.asList(maria,alex,bob)));

        Post post1 = new Post(null,simpleDateFormat.parse("27/07/22"),"Roadtrip!", "Let`s go Prag",new AuthorDTO(maria)  );
        Post post2 = new Post(null,simpleDateFormat.parse("29/07/22"),"Good morning life", "Happy day today",new AuthorDTO(maria) );

        CommentDTO c1 = new CommentDTO("Have a nice trip!", simpleDateFormat.parse("27/07/22"), new AuthorDTO(alex));
        CommentDTO c2 = new CommentDTO("Enjoy", simpleDateFormat.parse("29/07/22"),new AuthorDTO(bob));
        CommentDTO c3 = new CommentDTO("Have a nice day", simpleDateFormat.parse("29/07/22"),new AuthorDTO(maria));
        post1.getComments().addAll(Arrays.asList(c1,c2));
        post2.getComments().addAll(Arrays.asList(c3));

        postRepository.saveAll((Arrays.asList(post1,post2)));

        maria.getPosts().addAll(Arrays.asList(post1,post2));
        userRepository.save(maria);

    }
}
