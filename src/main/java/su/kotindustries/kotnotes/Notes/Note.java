package su.kotindustries.kotnotes.Notes;

import org.springframework.data.annotation.Id;

import java.util.Calendar;
import java.util.Date;

public class Note {

    @Id
    public String id;
    public String authorName;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String caption;
    public String text;


    public Note() {
    }

    public Note(String authorId, String caption, String text) {
        this.authorName = authorId;
        this.caption = caption;
        this.text = text;
        this.createDate = getCurrentDate();
    }

    public Note getUpdatedNote(String caption, String text) {
        String authorId = this.authorName;
        Date createDate = this.createDate;

        Note newNote = new Note(authorId, caption, text);
        newNote.setCreateDate(createDate);
        newNote.setId(id);

        return newNote;
    }
    @Override
    public String toString() {
        return String.format(
                "noteId=%s, authorId=%s, caption='%s', text='%s']",
                id, authorName, caption, text);
    }

    private Date getCurrentDate(){
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }
}