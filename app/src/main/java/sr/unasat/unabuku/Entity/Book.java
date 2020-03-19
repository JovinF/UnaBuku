package sr.unasat.unabuku.Entity;

public class Book {
    private int bookId;
    private String title;
    private String author;
    private String description;
    private String cover;

    public Book(int bookId, String title, String author, String description, String cover) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.description = description;
        this.cover = cover;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }
}
