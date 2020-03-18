package sr.unasat.unabuku;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sr.unasat.unabuku.Adapter.OrdersAdapter;
import sr.unasat.unabuku.Database.UnaBukuDAO;
import sr.unasat.unabuku.Entity.Order;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment {
    View v;
    private RecyclerView recyclerView;
    private List<Order> orders;


    public OrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_orders, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);
        UnaBukuDAO unaBukuDAO = new UnaBukuDAO(getContext());
        OrdersAdapter ordersAdapter = new OrdersAdapter(getContext(), unaBukuDAO.getOrdersByUserId());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(ordersAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UnaBukuDAO unaBukuDAO = new UnaBukuDAO(getContext());

        orders = unaBukuDAO.getOrdersByUserId();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
