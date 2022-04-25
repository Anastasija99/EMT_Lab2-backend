package emit.lab.web.rest;

import emit.lab.model.BookPrint;
import emit.lab.model.enumerations.CategoryType;
import emit.lab.model.Book;
import emit.lab.model.dto.BookDto;
import emit.lab.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping
    private List<Book> findAllBooks() {
        return this.bookService.listBooks()
                .stream()
                .sorted(Comparator.comparing(Book::getId))
                .collect(Collectors.toList());
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        return ResponseEntity
                .ok()
                .body(Arrays.stream(CategoryType.values())
                        .map(Enum::toString)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findBook(@PathVariable Long id) {
        return this.bookService.findBook(id)
                .map(book -> {
//                    System.out.println(book);
                    return ResponseEntity.ok().body(book);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/print/{id}")
    public List<BookPrint> findBookPrints(@PathVariable Long id) {
        return this.bookService.listBookPrintsByBook(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Book> saveBook(@RequestBody BookDto bookDto) {
        return this.bookService.save(bookDto)
                .map(product -> ResponseEntity
                        .ok()
                        .body(product))
                .orElseGet(() -> ResponseEntity
                        .badRequest()
                        .build());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Book> editBook(@PathVariable Long id, @RequestBody BookDto productDto) {
        return this.bookService.editBook(id, productDto)
                .map(product -> ResponseEntity
                        .ok()
                        .body(product))
                .orElseGet(() -> ResponseEntity
                        .badRequest()
                        .build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteBook(@PathVariable Long id) {

        this.bookService.deleteBook(id);

        if (this.bookService.findBook(id).isEmpty()){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete/bookprint/{id}")
    public ResponseEntity deleteBookPrint(@PathVariable Long id) {
        this.bookService.deleteBookPrint(id);

        if (this.bookService.findBookPrint(id).isEmpty()) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/markAsTaken/{id}")
    public ResponseEntity<BookPrint> markAsTakenBookPrint(@PathVariable Long id) {
        return this.bookService.markBookPrintAsTaken(id)
                .map(bookPrint -> ResponseEntity
                        .ok()
                        .body(bookPrint))
                .orElseGet(() -> ResponseEntity
                        .badRequest()
                        .build());
    }

    @PutMapping("/markAsReturned/{id}")
    public ResponseEntity<BookPrint> markAsReturnedBookPrint(@PathVariable Long id) {
        return this.bookService.markBookPrintAsReturned(id)
                .map(bookPrint -> ResponseEntity
                        .ok()
                        .body(bookPrint))
                .orElseGet(() -> ResponseEntity
                        .badRequest()
                        .build());
    }

    @PutMapping("/addNewBookPrint/{id}")
    public ResponseEntity<Book> addNewBookPrint(@PathVariable Long id) {
        return this.bookService.addNewCopiesForBook(id, 1)
                .map(book -> ResponseEntity
                        .ok()
                        .body(book))
                .orElseGet(() -> ResponseEntity
                        .badRequest()
                        .build());
    }
}