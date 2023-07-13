package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.R;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.gui.activity.ListMealsActivity;
import rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.model.MealFilter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FilterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FilterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilterFragment newInstance(String param1, String param2) {
        FilterFragment fragment = new FilterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

    private Spinner spinner;
    private EditText filtertext;
    private CheckBox sortedcb;
    private EditText name;
    private EditText tags;
    private EditText pagination;
    private Button showbt;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        spinner = view.findViewById(R.id.spinner1);
        filtertext = view.findViewById(R.id.editTextText);
        sortedcb = view.findViewById(R.id.checkBox);
        name = view.findViewById(R.id.editTextText2);
        tags = view.findViewById(R.id.editTextText3);
        pagination = view.findViewById(R.id.editTextNumber);
        showbt = view.findViewById(R.id.button11);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        showbt.setOnClickListener(e->{
            MealFilter mealFilter = new MealFilter();
            mealFilter.setCategory(spinner.getSelectedItem().toString());
            mealFilter.setMealName(name.getText().toString());
            mealFilter.setTag(tags.getText().toString());
            mealFilter.setSorted(sortedcb.isChecked());
            if(!pagination.getText().toString().equals("")){
                mealFilter.setMaxperpage(Integer.parseInt(pagination.getText().toString()));
            }
            String item = spinner.getSelectedItem().toString();
            if(item.equals("Category")){
                mealFilter.setCategory(filtertext.getText().toString());
            }
            else if(item.equals("Area")){
                mealFilter.setArea(filtertext.getText().toString());
            }
            else if(item.equals("Ingredient")){
                mealFilter.setIngredient(filtertext.getText().toString());
            }
            Intent intent = new Intent(view.getContext(), ListMealsActivity.class);
            intent.putExtra("filter", mealFilter);
            startActivity(intent);
        });



    }
}