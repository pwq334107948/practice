import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import au.anu.mymarketplace.R;
import CartFragment;
import MessageFragment;
import ProfileFragment;
import SearchFragment;

/**
 * @author Weiqiang Pu
 * @date 2022/10/04  22:59
 */

public class MainActivity extends AppCompatActivity {
    ProfileFragment profileFragment = new ProfileFragment();
    CartFragment cartFragment = new CartFragment();
    MessageFragment messageFragment = new MessageFragment();
    SearchFragment searchFragment = new SearchFragment();

    // 整合内容
    private String tempSellerUid = "0a7p6gzQyNbJzafYl0WoXmLcDaA2";
    private String tempSellerName = "testseller@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigationView = findViewById(R.id.nav_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, searchFragment).commit();
        navigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_search: {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, searchFragment).commit();
                    return true;
                }
                case R.id.navigation_cart: {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, cartFragment).commit();
                    return true;
                }
                case R.id.navigation_message: {
                    // TODO: 此处修改了(不完美)
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, messageFragment).commit();
                    return true;
                }
                case R.id.navigation_profile: {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, profileFragment).commit();
                    return true;
                }
            }
            return false;
        });
    }


}