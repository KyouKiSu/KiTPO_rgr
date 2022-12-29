package org.fomin.rgr;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.google.android.material.snackbar.Snackbar;
import org.fomin.rgr.databinding.FragmentFirstBinding;
import org.fomin.rgr.types.UserFactory;
import org.fomin.rgr.types.UserType;
import org.fomin.rgr.vtree.VTree;
import org.fomin.rgr.vtree.VTreeFactory;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    VTreeFactory vTreeFactory = new VTreeFactory();
    VTree tree;

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    protected void setFactoryClasses(){
        String selectedClass = binding.spinner.getSelectedItem().toString();
        vTreeFactory.setType(selectedClass);
    }

    protected void resetTree(){
        tree = vTreeFactory.createTree();
    }

    protected void refreshTree(){
        binding.textView.setText(tree.print());
    }

    protected void initTree(){
        setFactoryClasses();
        resetTree();
        refreshTree();
    }

    protected UserType createElement(){
        ArrayList<String> values = new ArrayList<>();
        for(int i = 0; i< binding.tablelayout.getChildCount(); i++){
            TableRow element =(TableRow) binding.tablelayout.getChildAt(i);
            EditText value = (EditText) element.getChildAt(1);
            values.add(value.getText().toString());
        }
        UserType builder = vTreeFactory.getTypeInstance();
        UserType instance = builder.create(values);
        return instance;
    }

    protected void insertElement(UserType element){
        tree.Insert(element);
    }

    protected void rebalanceTree(){
        tree.rebalance();
    }

    protected void removeElement(UserType element){
        tree.Remove(element);
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        ArrayList<String> list = new ArrayList<>();
        for (UserType _class: UserFactory.types) {
            list.add(_class.getClassName());
        }
        Spinner s = binding.spinner;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        binding.changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //binding.textView.setText(binding.spinner.getSelectedItem().toString());

                String selectedClass = binding.spinner.getSelectedItem().toString();
                ArrayList<Pair<String, String>> userTypeFieldInfo = UserFactory.getBuilderByName(selectedClass).getFields();
                binding.tablelayout.removeAllViews();
                for (Pair<String, String> field: userTypeFieldInfo) {
                    TextView label = new TextView(getActivity());
                    label.setText(field.first);
                    EditText text = new EditText(getActivity());
                    text.setHint(field.second);

                    TableRow group = new TableRow(getActivity());
                    group.setOrientation(LinearLayout.HORIZONTAL);

                    group.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));


                    group.addView(label);
                    group.addView(text);
                    binding.tablelayout.addView(group);
                }
                initTree();
            }
        });

        binding.addButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     UserType element = createElement();
                     insertElement(element);
                     refreshTree();
                 }
             }
        );

        binding.RemoveButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {

                     UserType element = createElement();
                     removeElement(element);
                     refreshTree();
                 }
             }
        );
        binding.RebalanceButton.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View view) {
                                                     rebalanceTree();
                                                     refreshTree();
                                                 }
                                             }
        );
        binding.ResetButton.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View view) {
                                                     resetTree();
                                                     refreshTree();
                                                 }
                                             }
        );
        binding.ShowVTreeButton.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View view) {
                                                     refreshTree();
                                                 }
                                             }
        );
        binding.ShowJSONButton.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View view) {
                                                     try {
                                                         binding.textView.setText(new JSONObject(tree.packValue()).toString(4));
                                                     } catch (JSONException e) {
                                                         throw new RuntimeException(e);
                                                     }
                                                 }
                                             }
        );


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}