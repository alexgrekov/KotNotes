package su.kotindustries.kotnotes.Notes;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Note {

    @Id
    public String id;
    public String authorId;

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

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
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


    public Note() {}

    public Note(String authorId, String caption, String text) {
        this.authorId = authorId;
        this.caption = caption;
        this.text = text;
    }

    @Override
    public String toString() {
        return String.format(
                "noteId=%s, authorId=%s, caption='%s', text='%s']",
                id, authorId, caption, text);
    }

}