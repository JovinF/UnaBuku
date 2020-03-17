package sr.unasat.unabuku.Entity;

public class Order {
    private int orderId;
    private int userId;
    private int bookId;
    private String orderDate;

    public Order(int orderId, int userId, int bookId, String orderDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.bookId = bookId;
        this.orderDate = orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", orderDate='" + orderDate + '\'' +
                '}';
    }
}
