package fi.academy;

import fi.academy.dao.ToDoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/todot")
@CrossOrigin
public class ToDoController {
    private ToDoDao dao;

    @Autowired
    public ToDoController(@Qualifier("jdbc") ToDoDao dao) {
        this.dao = dao;
    }

    @GetMapping("")
    public List<Task> showAll() {
        List<Task> all = dao.showAll();
        return all;
    }

    //toteutetaan myöhemmin
    /*@GetMapping("/{id}")
    public ResponseEntity<?> etsi(@PathVariable(name = "id", required = true) int id) {
        var haettu = dao.showTasksWithId(id);
        if (!haettu.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new Virheviesti(String.format("Id %d ei ole olemassa", id)));
        }
        return ResponseEntity.ok(haettu.get());
    }*/

    @PostMapping("")
    public ResponseEntity<?> createNew(@RequestBody Task t) {
        int id = dao.add(t);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).body(t);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        Task deleted = dao.delete(id);
        if (deleted != null)
            return ResponseEntity.ok(deleted);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new Virheviesti(String.format("Id %d ei ole olemassa: ei poistettu", id)));
    }

    /*toteutetaan myöhemmin
    @PutMapping("/{id}")
    public ResponseEntity<?> muokkaa(@RequestBody Task tiedot,
                                     @PathVariable("id") int id) {
        boolean muuttiko = dao.modify(id, tiedot);
        if (muuttiko) {
            tiedot.setId(id);
            return ResponseEntity.ok(tiedot);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new Virheviesti(String.format("Id %d ei ole olemassa: ei muutettu", id)));
    }*/

}
