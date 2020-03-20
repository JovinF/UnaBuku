package sr.unasat.unabuku;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sr.unasat.unabuku.Adapter.BooksAdapter;
import sr.unasat.unabuku.Entity.Book;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    View v;
    private RecyclerView recyclerView;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = v.findViewById(R.id.booksRecyclerView);

        //Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());

        final String ALL_BOOKS = "https://limitless-wildwood-86411.herokuapp.com/api/books";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ALL_BOOKS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<Book> bookList = mapJsonToCountryObject(response);
                        BooksAdapter booksAdapter = new BooksAdapter(getContext(), bookList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(booksAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                data.setText("something went wrong");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        return v;
    }

    private List<Book> mapJsonToCountryObject(String jsonArray) {
        ObjectMapper mapper = new ObjectMapper();
        List<Book> bookList = new ArrayList<>();
        List<Map<String, ?>> countryArray = null;
        Book book = null;

        try {
            countryArray = mapper.readValue(jsonArray, List.class);
            for (Map<String, ?> map : countryArray) {
                book = new Book(
                        (Integer) map.get("id"),
                        (String) map.get("title"),
                        (String) map.get("author"),
                        (String) map.get("cover"));

                bookList.add(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Er is wat fout gegaan bij het parsen van de json data");
        }
        return bookList;
    }

}
