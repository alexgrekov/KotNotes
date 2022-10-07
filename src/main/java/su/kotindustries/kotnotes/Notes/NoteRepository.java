package su.kotindustries.kotnotes.Notes;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NoteRepository extends MongoRepository<Note, String>{

    public List<Note> findByAuthorId(String authorId);
    public List<Note> findByCaption(String caption);

}