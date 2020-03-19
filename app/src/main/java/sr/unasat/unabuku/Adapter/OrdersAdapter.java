package sr.unasat.unabuku.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sr.unasat.unabuku.Database.UnaBukuDAO;
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
        private ImageButton editItem, removeItem;
        public TextView titleText, authorText;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            editItem = (ImageButton) itemView.findViewById(R.id.editItem);
            removeItem = (ImageButton) itemView.findViewById(R.id.removeItem);
            titleText = itemView.findViewById(R.id.bookTitle);
            authorText = itemView.findViewById(R.id.bookAuthor);
        }
    }

    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.order_item, parent, false);
        final OrdersViewHolder ordersViewHolder = new OrdersViewHolder(v);

        ordersViewHolder.editItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Test " + mData.get(ordersViewHolder.getAdapterPosition()).getOrderId(), Toast.LENGTH_SHORT).show();
            }
        });

        ordersViewHolder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setCancelable(true);
                builder.setTitle("Order verwijderen");
                builder.setMessage("Bent u zeker dat u deze order wilt verwijderen?");
                builder.setPositiveButton("Ja",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int orderId = mData.get(ordersViewHolder.getAdapterPosition()).getOrderId();
                                UnaBukuDAO unaBukuDAO = new UnaBukuDAO(mContext);
                                unaBukuDAO.deleteOrder(orderId);
                                mData.remove(ordersViewHolder.getAdapterPosition());
                                notifyItemRemoved(ordersViewHolder.getAdapterPosition());
                                Toast.makeText(mContext, "Succesvol verwijdert", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.setNegativeButton("Nee", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(mContext, "Geannuleerd", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

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
