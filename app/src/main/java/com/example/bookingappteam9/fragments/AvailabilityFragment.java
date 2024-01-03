package com.example.bookingappteam9.fragments;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Parcel;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.adapters.AvailabilityAdapter;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.databinding.FragmentAvailabilityBinding;
import com.example.bookingappteam9.model.AccommodationStatus;
import com.example.bookingappteam9.model.Host;
import com.example.bookingappteam9.model.HostAccommodation;
import com.example.bookingappteam9.model.NewAccommodation;
import com.example.bookingappteam9.model.Photo;
import com.example.bookingappteam9.model.TimeSlot;
import com.example.bookingappteam9.utils.PrefUtils;
import com.example.bookingappteam9.viewmodels.NewAccommodationViewModel;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.CompositeDateValidator;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import javax.xml.validation.Validator;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AvailabilityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvailabilityFragment extends Fragment {
    private FragmentAvailabilityBinding binding;
    private NewAccommodationViewModel viewModel;
    private AvailabilityAdapter availabilityAdapter;
    private MaterialDatePicker datePicker;
    private String picked_date;
    private Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    private TimeSlot slot = new TimeSlot();
    private TextInputLayout cancellationDeadline;
    private SwitchCompat autoApproval;
    private SwitchCompat pricePerGuest;

    public AvailabilityFragment() {
        // Required empty public constructor
    }


    public static AvailabilityFragment newInstance(String param1, String param2) {
        AvailabilityFragment fragment = new AvailabilityFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAvailabilityBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        List<TimeSlot> availability = new ArrayList<>();
        availabilityAdapter = new AvailabilityAdapter(availability);
        pricePerGuest = binding.pricePerGuest;
        cancellationDeadline = binding.cancellationDeadline;
        autoApproval = binding.autoApproval;
        binding.recyclerView.setAdapter(availabilityAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CalendarConstraints.DateValidator validator = new CalendarConstraints.DateValidator() {
            @Override
            public boolean isValid(long date) {
                for (TimeSlot s: availabilityAdapter.getSlots()){
                    long start = s.getStartDate().toEpochSecond(ZoneOffset.UTC)*1000;
                    long end = s.getEndDate().toEpochSecond(ZoneOffset.UTC)*1000;
                    if (date >= start && date <= end - 86400000){
                        return false;
                    }
                }
                return true;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(@NonNull Parcel dest, int flags) {

            }
        };
        Long today = MaterialDatePicker.todayInUtcMilliseconds();
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        Long dec = calendar.getTimeInMillis();
        CalendarConstraints constraints = new CalendarConstraints.Builder().setValidator(validator).setStart(today).setEnd(dec).build();
        datePicker = MaterialDatePicker.Builder.dateRangePicker().setSelection(new Pair<>(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.thisMonthInUtcMilliseconds())).setCalendarConstraints(constraints).build();
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
            Pair<Long, Long> dates = (Pair<Long, Long>) selection;
            slot.setStartDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(dates.first), ZoneId.of("UTC")));
            slot.setEndDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(dates.second), ZoneId.of("UTC")));
            slot.setRangeString(datePicker.getHeaderText());
            picked_date = datePicker.getHeaderText();
            binding.inputDate.getEditText().setText(picked_date);
        }});
        binding.inputDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("date clicked");
                datePicker.show(getChildFragmentManager(), "123");
            }
        });
        binding.addSlotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price = binding.inputPrice.getEditText().getText().toString();
                if (price.isEmpty()){
                    binding.inputPrice.setError("Required!");
                }else{
                    binding.inputPrice.setError(null);
                }
                if (picked_date.isEmpty()){
                    binding.inputDate.setError("Required!");
                }else{
                    binding.inputDate.setError(null);
                }
                if (!Objects.equals(picked_date, "") && !price.equals("")){
                    slot.setPrice(Double.valueOf(price));
                    slot.setOccupied(false);
                    DateTimeFormatter formater = DateTimeFormatter.ofPattern("MMM dd");
                    for (TimeSlot slt: availabilityAdapter.getSlots()){
                        if (slot.getPrice() == slt.getPrice()){
                            if (slot.getStartDate().isEqual(slt.getEndDate())){
                                slt.setEndDate(slot.getEndDate());
                                String range = slt.getStartDate().format(formater) + " - " + slt.getEndDate().format(formater);
                                slt.setRangeString(range);
                                availabilityAdapter.notifyDataSetChanged();
                                resetForm();
                                return;
                            }
                            if (slot.getEndDate().isEqual(slt.getStartDate())){
                                slt.setStartDate(slot.getStartDate());
                                String range = slt.getStartDate().format(formater) + " - " + slt.getEndDate().format(formater);

                                slt.setRangeString(range);
                                availabilityAdapter.notifyDataSetChanged();
                                resetForm();
                                return;
                            }
                        }

                    }
                    availabilityAdapter.addSlot(new TimeSlot(null, slot.getStartDate(), slot.getEndDate(), slot.getPrice(), slot.isOccupied(), slot.getRangeString()));
                    resetForm();
                }


            }
        });
        binding.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cancellationDeadlineStr = cancellationDeadline.getEditText().getText().toString();
                if (!cancellationDeadlineStr.isEmpty()){
                    cancellationDeadline.setError(null);
                    viewModel.setCancellationDeadline(Integer.valueOf(cancellationDeadlineStr));
                    viewModel.setAvailability(availabilityAdapter.getSlots());
                    viewModel.setAutoApproval(autoApproval.isChecked());
                    viewModel.setPricePerGuest(pricePerGuest.isChecked());
                    createAccommodation();

                }else{
                    cancellationDeadline.setError("Required!");
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
    private void resetForm(){
        slot = new TimeSlot();
        binding.inputPrice.getEditText().setText("");
        binding.inputDate.getEditText().setText("");
        picked_date = "";
        binding.inputDate.requestFocus();
    }
    private void createAccommodation(){

        NewAccommodation accommodation = new NewAccommodation();
        accommodation.setName(viewModel.getName().getValue());
        accommodation.setDescription(viewModel.getDescription().getValue());
        accommodation.setAccommodationType(viewModel.getType().getValue());
        accommodation.setAddress(viewModel.getAddress().getValue());
        accommodation.setAmenities(viewModel.getAmenities().getValue());
        accommodation.setMaxGuests(viewModel.getMaxGuests().getValue());
        accommodation.setMinGuests(viewModel.getMinGuests().getValue());
        accommodation.setPhotos(viewModel.getPhotos().getValue());
        accommodation.setPricePerGuest(viewModel.getPricePerGuest().getValue());
        accommodation.setStatus(AccommodationStatus.Pending);
        accommodation.setAvailability(viewModel.getAvailability().getValue());
        accommodation.setCancellationDeadline(viewModel.getCancellationDeadline().getValue());
        accommodation.setAutoApproval(viewModel.getAutoApproval().getValue());
        Call<Host> call = ClientUtils.hostService.getById(PrefUtils.getUserInfo(getActivity().getApplicationContext()).getId());
        call.enqueue(new Callback<Host>() {
            @Override
            public void onResponse(Call<Host> call, Response<Host> response) {
                if (response.code() == 200){
                   accommodation.setHost(response.body());
                   if (!viewModel.getPhotos().getValue().isEmpty()){
                       Call<ResponseBody> call2 = uploadImages();
                       call2.enqueue(new Callback<ResponseBody>() {
                           @Override
                           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                               if (response.code() == 201){
                                   Log.d("upload", "success!");
                                   Call<NewAccommodation> call3 = ClientUtils.accommodationService.createAccommodation(accommodation);
                                   call3.enqueue(new Callback<NewAccommodation>() {
                                       @Override
                                       public void onResponse(Call<NewAccommodation> call, Response<NewAccommodation> response) {
                                           if (response.isSuccessful()){
                                               Log.d("upload", "accommodation created successfully!");
                                               AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                               builder.setMessage("Accommodation succesfully created!").setTitle("Success");
                                               builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                   @Override
                                                   public void onClick(DialogInterface dialog, int which) {
                                                       findNavController(getParentFragment()).navigate(R.id.action_availabilityFragment_to_navigation_host_properties);
                                                       getActivity().getViewModelStore().clear();
                                                   }
                                               });
                                               AlertDialog dialog = builder.create();
                                               dialog.show();
                                           }
                                       }

                                       @Override
                                       public void onFailure(Call<NewAccommodation> call, Throwable t) {
                                           Log.d("QM", t.getMessage() != null?t.getMessage():"error");

                                       }
                                   });
                               }
                           }

                           @Override
                           public void onFailure(Call<ResponseBody> call, Throwable t) {
                               Log.d("QM", t.getMessage() != null?t.getMessage():"error");

                           }
                       });
                   }



                }else{
                    Log.d("QM","Meesage recieved: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<Host> call, Throwable t) {
                Log.d("QM", t.getMessage() != null?t.getMessage():"error");

            }
        });





    }
    private Call<ResponseBody> uploadImages(){
            List<Photo> photoList = viewModel.getRawPhotos().getValue();
            MultipartBody.Part[] photos = new MultipartBody.Part[photoList.size()];
            for (int i =0; i < photoList.size(); i++){
                File file = new File(getRealPathFromURI(photoList.get(i).getUri()));
                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"),file);
                photos[i] = MultipartBody.Part.createFormData("file",
                        file.getName(),
                        reqFile);
            }
            Call<ResponseBody> call = ClientUtils.imageService.uploadPhoto(photos);
            return call;
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
    private Host findHost(){

       /* try {
            Response<Host> response = call.execute();
            if (response.code() == 200){
                return response.body();
            }
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
        return null;*/
        return null;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(NewAccommodationViewModel.class);
        if (!viewModel.getFourthStepEmpty()){
            viewModel.getAvailability().observe(getViewLifecycleOwner(), av -> {
                availabilityAdapter.addSlots(av);
            });
            viewModel.getCancellationDeadline().observe(getViewLifecycleOwner(), cd->{
                cancellationDeadline.getEditText().setText(cd);
            });
            viewModel.getAutoApproval().observe(getViewLifecycleOwner(), ap->{
                autoApproval.setChecked(ap);
            });
            viewModel.getPricePerGuest().observe(getViewLifecycleOwner(), gp ->{
                pricePerGuest.setChecked(gp);
            });


        }
    }
}