package su.kotindustries.kotnotes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import su.kotindustries.kotnotes.Notes.Note;
import su.kotindustries.kotnotes.Notes.NoteRepository;

import java.util.Objects;
import java.util.Optional;

@Controller
public class NotesController {
    @Autowired
    private NoteRepository noteRepository;

    // =========================== GET Methods =============================================
    @GetMapping("/notes")
    public String notesPage(Model model) {
        String currentUserName = getCurrentUserName();
        Iterable<Note> notes = noteRepository.findByAuthorName(currentUserName);
        model.addAttribute("notes", notes);
        model.addAttribute("userName", currentUserName);
        return "NotesView";
    }

    @GetMapping("/notes/add")
    public String notesAddPage(Model model) {
        String currentUserName = getCurrentUserName();
        model.addAttribute("note", new Note());
        model.addAttribute("action", "add");
        model.addAttribute("userName", currentUserName);
        return "NotesAddView";
    }

    @GetMapping("/notes/get")
    public String showNote(@RequestParam(value = "noteId") String id, Model model) {
        if (id == null) {
            return "redirect:/notes";
        }
        Optional<Note> note = noteRepository.findById(id);
        note.ifPresent(model::addAttribute);
        model.addAttribute("action", "update");
        return "NotesAddView";

    }

    // ================================ ACTIONS ============================================

    @PostMapping("/api/note/add")
    public String noteAdd(@RequestParam(value = "noteCaption", defaultValue = "No_caption") String noteCaption,
                          @RequestParam(value = "noteText", defaultValue = "") String noteText, Model model) {
        String currentUserName = getCurrentUserName();

        Note note = new Note(currentUserName, noteCaption, noteText);
        noteRepository.save(note);

        model.addAttribute("notification", "Note is successfully added.");
        return "redirect:/notes";
    }
    @PostMapping("/api/note/update")
    public String noteUpdate(@RequestParam(value = "noteCaption", defaultValue = "No_caption") String noteCaption,
                             @RequestParam(value = "noteText", defaultValue = "") String noteText,
                             @RequestParam(value = "noteId", defaultValue = "0") String noteId, Model model) {
        String currentUserName = getCurrentUserName();

        Optional<Note> oldNoteSearch = noteRepository.findById(noteId);

        if (oldNoteSearch.isPresent()) {
            Note oldNote = oldNoteSearch.get();
            String oldNoteUserName = oldNote.getAuthorName();
            if (Objects.equals(oldNoteUserName, currentUserName)){
                Note note = oldNote.getUpdatedNote(noteCaption, noteText);
                noteRepository.save(note);
                model.addAttribute("notification", "Note is successfully added.");
            } else {
                model.addAttribute("error", "Access denied.");
            }
        }  else
            model.addAttribute("error", "Note not found.");
        return "redirect:/notes";
    }
    @PostMapping("/api/note/delete")
    public String noteDel(@RequestParam(value = "noteId", defaultValue = "0") String noteId,
                          Model model) {

        String currentUserName = getCurrentUserName();
        Optional<Note> noteSearch = noteRepository.findById(noteId);
        if (noteSearch.isPresent()){
            Note note = noteSearch.get();
            String noteAuthor = note.getAuthorName();
            if (Objects.equals(noteAuthor, currentUserName)){
                noteRepository.deleteById(noteId);
                model.addAttribute("notification", "Note has been deleted.");
            } else
                model.addAttribute("error", "Note not found.");
        } else
            model.addAttribute("error", "Note not found.");

        return "redirect:/notes";
    }

    private String getCurrentUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
