package emit.lab.repository;

import emit.lab.model.BookPrint;
import emit.lab.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookPrintRepository extends JpaRepository<BookPrint, Long> {

    List<BookPrint> findByBookOrderById(Book book);
}