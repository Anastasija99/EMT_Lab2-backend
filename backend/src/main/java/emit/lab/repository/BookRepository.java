package emit.lab.repository;

import emit.lab.model.Author;
import emit.lab.model.enumerations.CategoryType;
import emit.lab.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByNameLikeAndAuthorAndCategoryOrderById(String name, Author author, CategoryType categoryType);

}
