package com.example.listycity;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    private Button addButton, deleteButton;
    private EditText inputCity;
    private int selectedPosition = AdapterView.INVALID_POSITION;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityList = findViewById(R.id.city_list);
        inputCity = findViewById(R.id.input_city);
        deleteButton = findViewById(R.id.btn_delete);
        addButton = findViewById(R.id.btn_add);
        String []cities = {"Edmonton", "Vancouver", "Moscow","Sydney","Berlin","Vienna","Tokyo","Beijing","Osaka", "New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this,R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
        });

        // Add city
        addButton.setOnClickListener(v -> addCity());  // Also add on keyboard 'Done'
        inputCity.setOnEditorActionListener((tv, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addCity();
                return true;
            }
            return false;
        });

        // Delete city
        deleteButton.setOnClickListener(v -> deleteSelectedCity());
    }

    private void addCity() {
        String name = inputCity.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this, "Type a city name first", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dataList.contains(name)) {
            Toast.makeText(this, "City already in the list", Toast.LENGTH_SHORT).show();
            return;
        }

        dataList.add(name);
        cityAdapter.notifyDataSetChanged();
        inputCity.setText("");

    }

    private void deleteSelectedCity() {
        if (selectedPosition == AdapterView.INVALID_POSITION) {
            return;
        }

        dataList.remove(selectedPosition);
        cityAdapter.notifyDataSetChanged();

        // Clear selection
        cityList.clearChoices();
        selectedPosition = AdapterView.INVALID_POSITION;
    }
}
