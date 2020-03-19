package sr.unasat.unabuku.Adapter;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sr.unasat.unabuku.Database.UnaBukuDAO;
import sr.unasat.unabuku.Entity.Book;
import sr.unasat.unabuku.R;
import sr.unasat.unabuku.Session;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksViewHolder> {
    private Context mContext;
    List<Book> mData;
    Dialog dialog;

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

        dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_insert_order);

        booksViewHolder.orderBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dialogBookTitleTv = (TextView) dialog.findViewById(R.id.insertBookTitle);
                TextView dialogBookAuthor = (TextView) dialog.findViewById(R.id.insertBookAuthor);

                dialogBookTitleTv.setText(mData.get(booksViewHolder.getAdapterPosition()).getTitle());
                dialogBookAuthor.setText(mData.get(booksViewHolder.getAdapterPosition()).getAuthor());

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
                UnaBukuDAO unaBukuDAO = new UnaBukuDAO(mContext);
                Session session = new Session(mContext);

                String titleText = bookTitle.getText().toString();
                String authorText = bookAuthor.getText().toString();
                int amountText = Integer.parseInt(amount.getText().toString());

                ContentValues contentValues = new ContentValues();
                contentValues.put("user_id", session.getUserId());
                contentValues.put("book_title", titleText);
                contentValues.put("book_author", authorText);
                contentValues.put("amount", amountText);
                unaBukuDAO.insertOneOrder(contentValues);

                dialog.dismiss();
                Toast.makeText(mContext, "Order successvol geplaatst", Toast.LENGTH_SHORT).show();
            }
        });

        return booksViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BooksViewHolder holder, int position) {
        String title = mData.get(position).getTitle();
        String author = mData.get(position).getAuthor();

        holder.bookTitleText.setText(title);
        holder.bookAuthorText.setText(author);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
