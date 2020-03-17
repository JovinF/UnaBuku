package sr.unasat.unabuku.Entity;

public class Book {
    private int bookId;
    private String title;
    private String author;
    private String synopsis;

    public Book(int bookId, String title, String author, String synopsis) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.synopsis = synopsis;
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

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", synopsis='" + synopsis + '\'' +
                '}';
    }
}
