package sr.unasat.unabuku.Adapter;

import android.app.Dialog;
import android.app.Notification;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import sr.unasat.unabuku.Database.UnaBukuDAO;
import sr.unasat.unabuku.Entity.Book;
import sr.unasat.unabuku.R;
import sr.unasat.unabuku.Session;

import static sr.unasat.unabuku.App.CHANNEL_1_ID;
import static sr.unasat.unabuku.App.CHANNEL_2_ID;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksViewHolder> {
    private Context mContext;
    List<Book> mData;
    Dialog dialog;
    private NotificationManagerCompat notificationManager;

    public BooksAdapter(Context context, List<Book> mData) {
        this.mContext = context;
        this.mData = mData;
    }

    public static class BooksViewHolder extends RecyclerView.ViewHolder {
        private ImageButton orderBook;
        public TextView bookTitleText, bookAuthorText;
        ImageView bookCoverImage;


        public BooksViewHolder(@NonNull View itemView) {
            super(itemView);
            orderBook = (ImageButton) itemView.findViewById(R.id.orderBook);
            bookCoverImage = (ImageView) itemView.findViewById(R.id.bookCover);
            bookTitleText = itemView.findViewById(R.id.bookTitle);
            bookAuthorText = itemView.findViewById(R.id.bookAuthor);
        }
    }

    @Override
    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.book_item, parent, false);
        final BooksAdapter.BooksViewHolder booksViewHolder = new BooksViewHolder(v);

        notificationManager = NotificationManagerCompat.from(mContext);

        dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_insert_order);

        booksViewHolder.orderBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dialogBookTitleTv = (TextView) dialog.findViewById(R.id.insertBookTitle);
                TextView dialogBookAuthor = (TextView) dialog.findViewById(R.id.insertBookAuthor);
                ImageView dialogBookCover = (ImageView) dialog.findViewById(R.id.insertBookCover);

                dialogBookTitleTv.setText(mData.get(booksViewHolder.getAdapterPosition()).getTitle());
                dialogBookAuthor.setText(mData.get(booksViewHolder.getAdapterPosition()).getAuthor());
                Picasso.get().load(mData.get(booksViewHolder.getAdapterPosition()).getCover()).into(dialogBookCover);

                dialog.show();
            }
        });

        Button btnInsertOrder = dialog.findViewById(R.id.btnInsertOrder);
        final TextView bookTitle = dialog.findViewById(R.id.insertBookTitle);
        final TextView bookAuthor = dialog.findViewById(R.id.insertBookAuthor);
        final EditText amount = dialog.findViewById(R.id.insertAmount);

        btnInsertOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    UnaBukuDAO unaBukuDAO = new UnaBukuDAO(mContext);
                    Session session = new Session(mContext);

                    String titleText = bookTitle.getText().toString();
                    String authorText = bookAuthor.getText().toString();
                    if (!amount.getText().toString().equals("")) {
                        int amountText = Integer.parseInt(amount.getText().toString());

                        ContentValues contentValues = new ContentValues();
                        contentValues.put("user_id", session.getUserId());
                        contentValues.put("book_title", titleText);
                        contentValues.put("book_author", authorText);
                        contentValues.put("amount", amountText);

                        unaBukuDAO.insertOneOrder(contentValues);

                        dialog.dismiss();
                        Notification notification = new NotificationCompat.Builder(mContext, CHANNEL_1_ID)
                                .setSmallIcon(R.drawable.book)
                                .setContentTitle("Successvol")
                                .setContentText("Uw order is successvol geplaatst")
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                .build();

                        notificationManager.notify(1, notification);
                        Toast.makeText(mContext, "Order successvol geplaatst", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "U bent verplicht alle velden in te vullen", Toast.LENGTH_SHORT).show();
                    }
                } catch (SQLiteException e) {
                    Toast.makeText(mContext, "Order niet geplaatst", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return booksViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BooksViewHolder holder, int position) {
        String title = mData.get(position).getTitle();
        String author = mData.get(position).getAuthor();
        String url = mData.get(position).getCover();

        holder.bookTitleText.setText(title);
        holder.bookAuthorText.setText(author);
        Picasso.get().load(url).into(holder.bookCoverImage);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
