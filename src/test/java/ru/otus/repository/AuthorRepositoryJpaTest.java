package ru.otus.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.entity.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Репозиторий на основе Jpa должен ")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {

    private static final int EXPECTED_NUMBER_OF_AUTHORS = 6;
    private static final String NEW_AUTHOR_NAME = "someNewAuthor";

    @Autowired
    private AuthorRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Находить все записи")
    @Test
    void findAll() {
        List<Author> allAuthors = repositoryJpa.findAll();
        assertEquals(EXPECTED_NUMBER_OF_AUTHORS, allAuthors.size());

    }

    @DisplayName("находить запись по ID")
    @Test
    void findById() {
        Optional<Author> optionalAuthor = repositoryJpa.findById(1L);
        assertEquals("author_unknown", optionalAuthor.get().getAuthorName());
    }

    @DisplayName("корректно сохранять запись (если она новая)")
    @Test
    void saveWhenNotExists() {
        Author author = new Author();
        //author.setId(6L);
        author.setAuthorName(NEW_AUTHOR_NAME);

        repositoryJpa.save(author);
        List<Author> allAuthors = repositoryJpa.findAll();
        assertEquals(EXPECTED_NUMBER_OF_AUTHORS + 1, allAuthors.size());
    }

    @DisplayName("корректно сохранять запись (если она старая)")
    @Test
    void saveWhenAlreadyExists() {
        Optional<Author> optionalAuthorBefore = repositoryJpa.findById(6L);
        assertEquals("author_05", optionalAuthorBefore.get().getAuthorName());

        Author author = new Author();
        author.setId(6L);
        author.setAuthorName(NEW_AUTHOR_NAME);

        repositoryJpa.save(author);
        List<Author> allAuthors = repositoryJpa.findAll();
        assertEquals(EXPECTED_NUMBER_OF_AUTHORS, allAuthors.size());

        Optional<Author> optionalAuthorAfter = repositoryJpa.findById(6L);
        assertEquals(NEW_AUTHOR_NAME, optionalAuthorAfter.get().getAuthorName());
    }

    @DisplayName("находить автора по имени")
    @Test
    void findByName() {
        List<Author> authors = repositoryJpa.findByName("author_03");
        assertEquals(2, authors.size());
        assertEquals(4, authors.get(0).getId());
        assertEquals(5, authors.get(1).getId());
    }

    @DisplayName("обновлять имя автора по ID")
    @Test
    void updateNameById() {
        Author authorBefore = em.find(Author.class, 1L);
        //assertEquals("author_unknown", authorBefore.getAuthorName());
        em.detach(authorBefore);

        repositoryJpa.updateNameById(1, NEW_AUTHOR_NAME);

        Author authorAfter = em.find(Author.class, 1L);
        //assertEquals(NEW_AUTHOR_NAME, authorAfter.getAuthorName());

        assertThat(authorAfter.getAuthorName()).isNotEqualTo(authorBefore.getAuthorName()).isEqualTo(NEW_AUTHOR_NAME);
    }

    @DisplayName("удалять запись по ID")
    @Test
    void deleteById() {
        val firstStudent = em.find(Author.class, 6L);
        assertThat(firstStudent).isNotNull();
        em.detach(firstStudent);

        repositoryJpa.deleteById(6L);
        val deletedStudent = em.find(Author.class, 6L);

        assertThat(deletedStudent).isNull();

        List<Author> allAuthors = repositoryJpa.findAll();
        assertEquals(EXPECTED_NUMBER_OF_AUTHORS - 1, allAuthors.size());
    }
}