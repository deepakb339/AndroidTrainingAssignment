package org.nagarro.todolist.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.nagarro.todolist.R;

public class TodoDetailActivity extends AppCompatActivity {
    TextView title,status;
    CardView cardView;
    LinearLayout card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        cardView = findViewById(R.id.card_view);
        card = findViewById(R.id.card_view_container);
        card.animate().alpha(1f).setDuration(3000).start();
        title = findViewById(R.id.todoTitle);
        status = findViewById(R.id.todoStatus);
        title.setText(getIntent().getStringExtra("title"));
        status.setText(getIntent().getStringExtra("status"));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}