package com.example.mentalhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mentalhealth.publish.PublishTopicActivity;
import com.example.mentalhealth.test.TestListFragment;
import com.example.mentalhealth.test.util.DatabaseInitializer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseInitializer.populateAsync(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化 FAB（从 activity_main.xml 中获取）
        fab = findViewById(R.id.fab_publish);

        // 设置 FAB 点击事件
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PublishTopicActivity.class);
            startActivity(intent);
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        // 设置底部导航栏的选项选择监听器
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            // 根据选中的菜单项 ID 创建对应的 Fragment 实例
            if (item.getItemId() == R.id.nav_treehole) {
                selectedFragment = new TreeHoleFragment();// 树洞 Fragment
                fab.setVisibility(View.VISIBLE); // 显示 FAB
            } else if (item.getItemId() == R.id.nav_post) {
                selectedFragment = new PostFragment();// 驿站 Fragment
                fab.setVisibility(View.GONE); // 隐藏 FAB
            } else if (item.getItemId() == R.id.nav_test) {
                selectedFragment = new TestListFragment();// 测试 Fragment
                fab.setVisibility(View.GONE); // 隐藏 FAB
            } else if (item.getItemId() == R.id.nav_mine) {
                selectedFragment = new MineFragment();// 我的 Fragment
                fab.setVisibility(View.GONE); // 隐藏 FAB
            }

            if (selectedFragment != null) {
                // 使用 FragmentManager 替换当前的 Fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, selectedFragment)
                        .commit();
            }

            return true;
        });

        // 默认选中第一个 Tab
        bottomNav.setSelectedItemId(R.id.nav_treehole);
    }
}
