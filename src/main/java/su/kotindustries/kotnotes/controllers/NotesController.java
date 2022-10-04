package su.kotindustries.kotnotes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
        Iterable<Note> notes = noteRepository.findAll();
        model.addAttribute("notes", notes);
        return "NotesView";
    }

    @GetMapping("/notes/add")
    public String notesAddPage(Model model) {
        model.addAttribute("note", new Note());
        model.addAttribute("editType", "createNew");
        return "NotesAddView";
    }

    @GetMapping("/notes/show")
    public String showNote(@RequestParam(value = "noteId") String id, Model model) {
        if (id == null) {
            return "redirect:/notes";
        }
        Optional<Note> note = noteRepository.findById(id);
        note.ifPresent(model::addAttribute);
        model.addAttribute("editType", "updateOld");
        return "NotesAddView";

    }

    // ================================ ACTIONS ============================================
    @PostMapping("/action/note/save")
    public String noteAdd(@RequestParam(value = "authorId", defaultValue = "6338a22e3a85595ebb17b608") String authorId,
                          @RequestParam(value = "noteCaption", defaultValue = "No_caption") String noteCaption,
                          @RequestParam(value = "noteText", defaultValue = "sample_text") String noteText,
                          @RequestParam(value = "editType", defaultValue = "createNew") String editType,
                          @RequestParam(value = "noteId", defaultValue = "0") String noteId) {
        Note note;
        switch (editType){
            case "createNew":
                note = new Note(authorId, noteCaption, noteText);
                noteRepository.save(note);
                break;
            case "updateOld":
                Optional<Note> noteSearch = noteRepository.findById(noteId);
                if (noteSearch.isPresent()){
                    note=noteSearch.get();
                    note.setCaption(noteCaption);
                    note.setText(noteText);
                }
                break;
        }
        return "redirect:/notes";
    }

    @PostMapping("/action/note/delete")
    public String noteDel(@RequestParam(value = "noteId", defaultValue = "0") String noteId) {
        if (noteRepository.existsById(noteId)) {
            noteRepository.deleteById(noteId);
        }
        return "redirect:/notes";
    }

}
