package com.example.bookingappteam9.fragments;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.adapters.NewPhotosAdapter;
import com.example.bookingappteam9.databinding.FragmentAccommodationPhotosBinding;
import com.example.bookingappteam9.model.Photo;
import com.example.bookingappteam9.viewmodels.NewAccommodationViewModel;
import com.google.android.material.chip.Chip;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccommodationPhotosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccommodationPhotosFragment extends Fragment {
    private NewPhotosAdapter adapter;
    private NewAccommodationViewModel viewModel;
    private FragmentAccommodationPhotosBinding binding;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void askStoragePermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_MEDIA_IMAGES) ==
                PackageManager.PERMISSION_GRANTED) {
            // FCM SDK (and your app) can post notifications.
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES)) {
            // TODO: display an educational UI explaining to the user the features that will be enabled
            //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
            //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
            //       If the user selects "No thanks," allow the user to continue without notifications.
        } else {
            // Directly ask for the permission
            requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
        }
    }

    ActivityResultLauncher<PickVisualMediaRequest> pickMultipleMedia =
            registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(7), uris -> {
                // Callback is invoked after the user selects media items or closes the
                // photo picker.
                if (!uris.isEmpty()) {
                    Log.d("PhotoPicker", "Number of items selected: " + uris.size());
                    for (int i = 0; i < uris.size(); i++) {
                        int finalI = i;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Photo newPhoto = new Photo(uris.get(finalI), "image1", getBitmapFromUri(uris.get(finalI)));
                                newPhoto.setFile(new File(uris.get(finalI).getPath()));
                                Cursor returnCursor =
                                        getContext().getContentResolver().query(uris.get(finalI), null, null, null, null);
                                returnCursor.moveToFirst();
                                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                                newPhoto.setName(returnCursor.getString(nameIndex));
                                adapter.addPhoto(newPhoto);
                            }
                        }).run();

                    }
                } else {
                    Log.d("PhotoPicker", "No media selected");
                }
            });


    private Bitmap getBitmapFromUri(Uri uri) {
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            parcelFileDescriptor = getContext().getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            return image;
        } catch (IOException e) {
            Log.d("PhotoPicker", "Error with file");
        }
        return null;

    }

    public AccommodationPhotosFragment() {
        // Required empty public constructor
    }


    public static AccommodationPhotosFragment newInstance(String param1, String param2) {
        AccommodationPhotosFragment fragment = new AccommodationPhotosFragment();
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
        binding = FragmentAccommodationPhotosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ArrayList<Photo> addedPhotos = new ArrayList<>();
        adapter = new NewPhotosAdapter(addedPhotos);
        binding.newPhotosList.setAdapter(adapter);
        binding.newPhotosList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    askStoragePermission();
                }
                pickMultipleMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });
        binding.newPhotoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Photo> photos = adapter.getPhotos();
                if (photos.size() > 0) {
                    viewModel.setRawPhotos(new ArrayList<>(photos));
                    viewModel.setPhotos(photos.stream().map(photo -> photo.getName()).collect(Collectors.toList()));
                    viewModel.setThirdStepEmpty(false);
                    findNavController(getParentFragment()).navigate(R.id.action_accommodationPhotosFragment_to_availabilityFragment);

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Continue without photos").setTitle("You haven't selected any photo!");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            findNavController(getParentFragment()).navigate(R.id.action_accommodationPhotosFragment_to_availabilityFragment);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }


            }
        });
        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(NewAccommodationViewModel.class);
        if (!viewModel.getThirdStepEmpty()) {
            viewModel.getRawPhotos().observe(getViewLifecycleOwner(), photos -> {
                for (Photo ph : photos) {
                    this.adapter.addPhoto(ph);
                }
            });
        }


    }
}