package sr.unasat.unabuku.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sr.unasat.unabuku.Database.UnaBukuDAO;
import sr.unasat.unabuku.Entity.Order;
import sr.unasat.unabuku.LoginActivity;
import sr.unasat.unabuku.R;
import sr.unasat.unabuku.Session;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> {
    private Context mContext;
    List<Order> mData;
    Dialog dialog;

    public OrdersAdapter(Context context, List<Order> mData) {
        this.mContext = context;
        this.mData = mData;
    }

    public static class OrdersViewHolder extends RecyclerView.ViewHolder {
        private ImageButton editItem, removeItem;
        public TextView titleText, authorText, amountText;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            editItem = (ImageButton) itemView.findViewById(R.id.editItem);
            removeItem = (ImageButton) itemView.findViewById(R.id.removeItem);
            titleText = itemView.findViewById(R.id.bookTitle);
            authorText = itemView.findViewById(R.id.bookAuthor);
            amountText = itemView.findViewById(R.id.orderAmount);
        }
    }

    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.order_item, parent, false);
        final OrdersViewHolder ordersViewHolder = new OrdersViewHolder(v);

        dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_update_order);

        ordersViewHolder.editItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TextView dialogOrderIdTv = (TextView) dialog.findViewById(R.id.updateOrderId);
                EditText dialogAmountEt = (EditText) dialog.findViewById(R.id.updateAmount);

                dialogOrderIdTv.setText(Integer.toString(mData.get(ordersViewHolder.getAdapterPosition()).getOrderId()));
                dialogAmountEt.setText(Integer.toString(mData.get(ordersViewHolder.getAdapterPosition()).getAmount()));

                dialog.show();
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


        Button btnUpdateOrder = dialog.findViewById(R.id.btnUpdateOrder);
        final TextView orderId = dialog.findViewById(R.id.updateOrderId);
        final EditText orderAmount = dialog.findViewById(R.id.updateAmount);
        btnUpdateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnaBukuDAO unaBukuDAO = new UnaBukuDAO(mContext);

                int orderIdText = Integer.parseInt(orderId.getText().toString());
                int orderAmountText = Integer.parseInt(orderAmount.getText().toString());
                
                ContentValues contentValues = new ContentValues();
                contentValues.put("amount", orderAmountText);
                unaBukuDAO.updateOrder(contentValues, orderIdText);

                Session session = new Session(mContext);
                swapData(unaBukuDAO.getOrdersByUserId(session.getUserId()));

                dialog.dismiss();
                Toast.makeText(mContext, "Succesvol bijgewerkt", Toast.LENGTH_SHORT).show();
            }
        });

        return ordersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        String title = mData.get(position).getBookTitle();
        String author = mData.get(position).getBookAuthor();
        String amount = Integer.toString(mData.get(position).getAmount());

        holder.titleText.setText(title);
        holder.authorText.setText(author);
        holder.amountText.setText(amount);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void swapData(List<Order> newData) {
        mData = newData;
        if (newData != null) {
            notifyDataSetChanged();
        }
    }
}
