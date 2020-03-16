package sr.unasat.unabuku;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

//    private static final String TAG = "MenuActivity";
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

    }

    HomeFragment homeFragment = new HomeFragment();
    OrdersFragment ordersFragment = new OrdersFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

switch (item.getItemId()){
    case R.id.navigation_home:
        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
        return true;
    case R.id.navigation_orders:
        getSupportFragmentManager().beginTransaction().replace(R.id.container,ordersFragment).commit();
        return true;
    case R.id.navigation_profile:
        getSupportFragmentManager().beginTransaction().replace(R.id.container,profileFragment).commit();
        return true;
}
        return false;
    }
}
