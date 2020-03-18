package sr.unasat.unabuku.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sr.unasat.unabuku.Entity.Order;
import sr.unasat.unabuku.R;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> {
    private Context mContext;
    List<Order> mData;

    public OrdersAdapter(Context context, List<Order> mData) {
        this.mContext = context;
        this.mData = mData;
    }

    public static class OrdersViewHolder extends RecyclerView.ViewHolder {
        public TextView titleText, authorText;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.bookTitle);
            authorText = itemView.findViewById(R.id.bookAuthor);
        }
    }

    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.order_item, parent, false);
        OrdersViewHolder ordersViewHolder = new OrdersViewHolder(v);
        return ordersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        String title = mData.get(position).getBookTitle();
        String author = mData.get(position).getBookAuthor();

        holder.titleText.setText(title);
        holder.authorText.setText(author);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
