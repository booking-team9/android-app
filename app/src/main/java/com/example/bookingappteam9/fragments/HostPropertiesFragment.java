package com.example.bookingappteam9.fragments;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.adapters.AccommodationShortListAdapter;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.databinding.FragmentHostPropertiesBinding;
import com.example.bookingappteam9.model.AccommodationShort;
import com.example.bookingappteam9.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HostPropertiesFragment extends ListFragment {
    private AccommodationShortListAdapter adapter;
    private ArrayList<AccommodationShort> accommodations;
    private FragmentHostPropertiesBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HostPropertiesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HostPropertiesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HostPropertiesFragment newInstance(String param1, String param2) {
        HostPropertiesFragment fragment = new HostPropertiesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AccommodationShort accommodationShort = new AccommodationShort(1L, "QM", "aleksa kuma 21 kosjeric", 4.5);
        ArrayList<AccommodationShort> list = new ArrayList<>();
//        list.add(accommodationShort);
//        accommodations = (ArrayList<AccommodationShort>) ClientUtils.accommodationService.getByHostId(1L);
        adapter = new AccommodationShortListAdapter(getActivity(),list);

        setListAdapter(adapter);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ShopApp", "onCreate Products List Fragment");

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHostPropertiesBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        PrefUtils.UserInfo userInfo = PrefUtils.getUserInfo(getActivity().getApplicationContext());
        Call<ArrayList<AccommodationShort>> call = ClientUtils.accommodationService.getByHostId(userInfo.getId());
        call.enqueue(new Callback<ArrayList<AccommodationShort>>() {
            @Override
            public void onResponse(Call<ArrayList<AccommodationShort>> call, Response<ArrayList<AccommodationShort>> response) {
                if (response.code() == 200){
                    Log.d("QM","Meesage recieved");
                    System.out.println(response.body());
//                    accommodations=list;
                    ArrayList<AccommodationShort> accommodationShorts = response.body();
                    int i = 5;
                    for(AccommodationShort ac : accommodationShorts){
                        ac.setImage(getImage(i));
                        i++;
                    }

                    addProducts(accommodationShorts);
                    adapter.notifyDataSetChanged();
//                    setData();

                }else{
                    Log.d("QM","Meesage recieved: "+response.code());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<AccommodationShort>> call, Throwable t) {
                Log.d("QM", t.getMessage() != null?t.getMessage():"error");
            }
        });
        binding.addProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNavController(getParentFragment()).navigate(R.id.action_navigation_host_properties_to_newPropertyFragment);

            }
        });

        return root;
    }

    public void addProducts(ArrayList<AccommodationShort> list){
        this.adapter.addAll(list);
    }



    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // Handle the click on item at 'position'
    }

    private int getImage(int i){
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.ap1);
        list.add(R.drawable.ap2);
        list.add(R.drawable.ap3);
        list.add(R.drawable.apart1);
        list.add(R.drawable.apart2);
        list.add(R.drawable.apart3);
        list.add(R.drawable.hotel);
        list.add(R.drawable.hotel1);
        list.add(R.drawable.hotel2);
        list.add(R.drawable.hotel3);
        list.add(R.drawable.slika1);
        list.add(R.drawable.slika2);
        list.add(R.drawable.slika3);
        list.add(R.drawable.slika33);
        list.add(R.drawable.slika11);
        return list.get(i);
    }
}