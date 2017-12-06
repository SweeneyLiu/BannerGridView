package com.lsw.bannergridview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private List<Fruit> fruitList = new ArrayList<Fruit>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFruits();
        FruitAdapter adapter = new FruitAdapter(MainActivity.this,
                R.layout.fruit_item, fruitList);
        HeaderGridView gridView = (HeaderGridView) findViewById(R.id.grid_view);
        BannerView bannerView = new BannerView(MainActivity.this);
        gridView.addHeaderView(bannerView);
        gridView.setAdapter(adapter);
        gridView.setNumColumns(3);
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.i(TAG, "onItemClick: "+position);
                Fruit fruit = fruitList.get(position-3);
                Toast.makeText(MainActivity.this, fruit.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initFruits() {
        Fruit apple = new Fruit("Apple", R.mipmap.apple_pic);
        fruitList.add(apple);
        Fruit banana = new Fruit("Banana", R.mipmap.banana_pic);
        fruitList.add(banana);
        Fruit orange = new Fruit("Orange", R.mipmap.orange_pic);
        fruitList.add(orange);
        Fruit watermelon = new Fruit("Watermelon", R.mipmap.watermelon_pic);
        fruitList.add(watermelon);
        Fruit pear = new Fruit("Pear", R.mipmap.pear_pic);
        fruitList.add(pear);
        Fruit grape = new Fruit("Grape", R.mipmap.grape_pic);
        fruitList.add(grape);
        Fruit pineapple = new Fruit("Pineapple", R.mipmap.pineapple_pic);
        fruitList.add(pineapple);
        Fruit strawberry = new Fruit("Strawberry", R.mipmap.strawberry_pic);
        fruitList.add(strawberry);
        Fruit cherry = new Fruit("Cherry", R.mipmap.cherry_pic);
        fruitList.add(cherry);
        Fruit mango = new Fruit("Mango", R.mipmap.mango_pic);
        fruitList.add(mango);
    }
}
