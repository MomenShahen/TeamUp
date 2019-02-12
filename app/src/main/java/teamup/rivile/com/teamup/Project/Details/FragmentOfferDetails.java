package teamup.rivile.com.teamup.Project.Details;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.badoualy.stepperindicator.StepperIndicator;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.ApiConfig;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.AppConfig;
import teamup.rivile.com.teamup.Project.Add.Adapters.FilesAdapter;
import teamup.rivile.com.teamup.Project.Add.Adapters.ImagesAdapter;
import teamup.rivile.com.teamup.Project.List.ContributerImages;
import teamup.rivile.com.teamup.Project.join.FragmentJoinHome;
import teamup.rivile.com.teamup.R;
import teamup.rivile.com.teamup.Uitls.APIModels.AttachmentModel;
import teamup.rivile.com.teamup.Uitls.APIModels.LikeModel;
import teamup.rivile.com.teamup.Uitls.APIModels.OfferDetailsJsonObject;
import teamup.rivile.com.teamup.Uitls.APIModels.UserModel;
import teamup.rivile.com.teamup.Uitls.AppModels.FilesModel;

public class FragmentOfferDetails extends Fragment {
    View view;
    RelativeLayout money, contributors;
    LinearLayout moneySection, contributorsSection, moneyRequired;
    RelativeLayout place, experience;
    LinearLayout placeSection, experienceSection;
    RelativeLayout attachment, cap, dep, tags, DepSection;
    LinearLayout CapSection, tagSection;

    int m, c, p, e, a, ca, d, t;/** متغير ثابت عشان اغير حاله ال shrink وال expand*/
    /**
     * 1: Expand, 0:Shrink
     */

    CircleImageView user_image;
    TextView project_name, user_name;
    TextView proDetail/*, moneyDesc*/;
    CheckBox moneyProfitType, genderRequired, placeState, placeType;
    StepperIndicator educationLevel;

    FloatingActionButton arrowContributors, arrowMoney, arrowTag, arrowDep, arrowAttach, arrowEx, arrowPlace;
    TextView moneyOutFrom, moneyOutTo, moneyInFrom, moneyInTo, conFrom, conTo;


    TextView placeDesc, placeAddress, exDesc, exDep, depName, experienceFrom, experienceTo, tagsList;
    TextView nun_contributor, num_likes;

    View map;

    RecyclerView recFiles, recImages;
    RecyclerView.Adapter imagesAdapter, filesAdapter;
    List<FilesModel> filesModels, imagesModels;
    ImageView preview;

    RecyclerView recCont;
    RecyclerView.Adapter conAdapter;

    TextView like, make_offer, share;


    static int projectId = 50;
    DownloadManager downloadManager;
    ImageView report;

    public static FragmentOfferDetails setProjectId(int proId) {
        projectId = proId;
        return new FragmentOfferDetails();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_project_details, container, false);
        m = c = 1;
        p = e = 1;
        a = ca = d = t = 1;
        /** Shrink and Expand Views */
        money = view.findViewById(R.id.money);
        moneyRequired = view.findViewById(R.id.moneyRequired);
        contributors = view.findViewById(R.id.contributors);
        moneySection = view.findViewById(R.id.moneySection);
        contributorsSection = view.findViewById(R.id.contributorsSection);
        place = view.findViewById(R.id.place);
        experience = view.findViewById(R.id.experience);
        placeSection = view.findViewById(R.id.placeSection);
        experienceSection = view.findViewById(R.id.experienceSection);
        attachment = view.findViewById(R.id.attachment);
        cap = view.findViewById(R.id.cap);
        dep = view.findViewById(R.id.dep);
        tags = view.findViewById(R.id.tags);
        CapSection = view.findViewById(R.id.CapSection);
        DepSection = view.findViewById(R.id.DepSection);
        tagSection = view.findViewById(R.id.tagSection);
        /** Input Views */

        report = view.findViewById(R.id.report);
        user_image = view.findViewById(R.id.user_image);
        preview = view.findViewById(R.id.preview);
        project_name = view.findViewById(R.id.project_name);
        user_name = view.findViewById(R.id.user_name);
        num_likes = view.findViewById(R.id.num_likes);
        nun_contributor = view.findViewById(R.id.num_contributer);
        proDetail = view.findViewById(R.id.proDetail);
        educationLevel = view.findViewById(R.id.educationLevel);
        educationLevel.setCurrentStep(0);
        arrowMoney = view.findViewById(R.id.arrowMoney);
        arrowContributors = view.findViewById(R.id.arrowContributors);
        arrowTag = view.findViewById(R.id.arrowTag);
        arrowDep = view.findViewById(R.id.arrowDep);
        arrowAttach = view.findViewById(R.id.arrowAttach);
        arrowEx = view.findViewById(R.id.arrowEx);
        arrowPlace = view.findViewById(R.id.arrowPlace);
        moneyOutFrom = view.findViewById(R.id.moneyOutFrom);
        moneyOutTo = view.findViewById(R.id.moneyOutTo);
        moneyInFrom = view.findViewById(R.id.moneyInFrom);
        moneyInTo = view.findViewById(R.id.moneyInTo);
        conFrom = view.findViewById(R.id.conFrom);
        conTo = view.findViewById(R.id.conTo);
        moneyProfitType = view.findViewById(R.id.moneyProfitType);
        genderRequired = view.findViewById(R.id.genderRequired);
        placeState = view.findViewById(R.id.placeState);
        placeType = view.findViewById(R.id.placeType);
        placeDesc = view.findViewById(R.id.placeDesc);
        placeAddress = view.findViewById(R.id.placeAddress);
        exDesc = view.findViewById(R.id.exDesc);
        depName = view.findViewById(R.id.depName);
        experienceFrom = view.findViewById(R.id.experienceFrom);
        experienceTo = view.findViewById(R.id.experienceTo);
        tagsList = view.findViewById(R.id.tagsList);
        exDep = view.findViewById(R.id.exDep);
        share = view.findViewById(R.id.share);
        like = view.findViewById(R.id.like);
        make_offer = view.findViewById(R.id.make_offer);
//        map = view.findViewById(R.id.map);

        recFiles = view.findViewById(R.id.recFiles);
        recImages = view.findViewById(R.id.recImages);
        recCont = view.findViewById(R.id.rec);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recImages.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recFiles.setLayoutManager(layoutManager1);
        filesModels = new ArrayList<>();
        imagesModels = new ArrayList<>();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        registerReceiver(onComplete,
//                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        loadOfferDetails(projectId);
    }

    @Override
    public void onStart() {
        super.onStart();

        report.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            EditText textReport = new EditText(getActivity());
            textReport.setHint(getString(R.string.reportText));
            //Todo: Report Action (view, inflate, webservice)

        });

        make_offer.setOnClickListener(v -> getFragmentManager().beginTransaction()
                .replace(R.id.frame,
                        FragmentJoinHome.setOfferId(projectId))
                .addToBackStack("FragmentJoinHome").commit());


        like.setOnClickListener(v -> {
            likeOffer(projectId);
        });

        imagesAdapter = new ImagesAdapter(getActivity(), imagesModels, item -> {
            try {
                Picasso.get().load(item.getFileName()).into(preview);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        filesAdapter = new FilesAdapter(getActivity(), filesModels, item -> {
            try {
                String fileType = "Files";
                Environment.getExternalStoragePublicDirectory(
                        getString(R.string.app_name) + File.separator + fileType);
//                File file = new File(path, item.getFileName());

                downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri;
                Log.e("File Link", item.getFileName());
                uri = Uri.parse(item.getFileName());
                DownloadManager.Request request = new DownloadManager.Request(uri);
                File folder = new File(Environment.getExternalStoragePublicDirectory(
                        getString(R.string.app_name) + File.separator + fileType), item.getFileName());
                Uri path = Uri.withAppendedPath(Uri.fromFile(folder), item.getFileName());


                boolean success = true;
                if (!folder.exists()) {
                    success = folder.mkdirs();
                    System.out.println("Done");
                }
                if (success) {
                    System.out.println("Done");
                    request.setDestinationUri(path);
//                    request.setDestinationInExternalFilesDir(getActivity(), getString(R.string.app_name) + File.separator + fileType, item.getFileName());
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);
                    request.setAllowedOverRoaming(false);
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                            DownloadManager.Request.NETWORK_MOBILE);
                    request.setTitle(item.getServerFileName());
                    downloadManager.enqueue(request);
                } else {
                    Toast.makeText(getActivity(),getString(R.string.noSapce),Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        recFiles.setAdapter(filesAdapter);

        recImages.setAdapter(imagesAdapter);

        money.setOnClickListener(v -> {
            if (m == 1) {
                m = 0;
                moneySection.setVisibility(View.GONE);
                arrowMoney.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));

            } else {
                m = 1;
                moneySection.setVisibility(View.VISIBLE);
                arrowMoney.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));

            }
        });

        contributors.setOnClickListener(v -> {
            if (c == 1) {
                c = 0;
                contributorsSection.setVisibility(View.GONE);
                arrowContributors.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));

            } else {
                c = 1;
                contributorsSection.setVisibility(View.VISIBLE);
                arrowContributors.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));
            }

        });


        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (p == 1) {
                    p = 0;
                    placeSection.setVisibility(View.GONE);
                    arrowPlace.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));
                } else {
                    p = 1;
                    placeSection.setVisibility(View.VISIBLE);
                    arrowPlace.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));
                }
            }
        });

        experience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (e == 1) {
                    e = 0;
                    experienceSection.setVisibility(View.GONE);
                    arrowEx.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));
                } else {
                    e = 1;
                    experienceSection.setVisibility(View.VISIBLE);
                    arrowEx.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));
                }
            }
        });
        //attachment, cap, dep, tags
        attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a == 1) {
                    a = 0;
//                    attachment.setVisibility(View.GONE);
//                attachmentSection.setVisibility(View.GONE);
                    recImages.setVisibility(View.GONE);
                    recFiles.setVisibility(View.GONE);
                    preview.setVisibility(View.GONE);
                    arrowAttach.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));
                } else {
                    a = 1;
//                    attachment.setVisibility(View.VISIBLE);
//                attachmentSection.setVisibility(View.GONE);
                    recImages.setVisibility(View.VISIBLE);
                    recFiles.setVisibility(View.VISIBLE);
                    preview.setVisibility(View.VISIBLE);
                    arrowAttach.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));
                }
            }
        });
        dep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (d == 1) {
                    d = 0;
                    DepSection.setVisibility(View.GONE);
                    arrowDep.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));
                } else {
                    d = 1;
                    DepSection.setVisibility(View.VISIBLE);
                    arrowDep.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));
                }
            }
        });
        tags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (t == 1) {
                    t = 0;
                    tagSection.setVisibility(View.GONE);
                    arrowTag.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_down));
                } else {
                    t = 1;
                    tagSection.setVisibility(View.VISIBLE);
                    arrowTag.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_arrow_up));
                }
            }
        });


    }

    private void loadOfferDetails(int Id) {
        // Map is used to multipart the file using okhttp3.RequestBody
        AppConfig appConfig = new AppConfig(API.BASE_URL);

        ApiConfig getOffers = appConfig.getRetrofit().create(ApiConfig.class);
        Call<OfferDetailsJsonObject> call;

        call = getOffers.offerDetails(Id, API.URL_TOKEN);

        call.enqueue(new Callback<OfferDetailsJsonObject>() {
            @Override
            public void onResponse(Call<OfferDetailsJsonObject> call, retrofit2.Response<OfferDetailsJsonObject> response) {
                OfferDetailsJsonObject Offers = response.body();
                List<UserModel> Users = Offers.getOffers().getUsers();
                if (Offers.getOffers() != null) {
                    Gson gson = new Gson();
                    Log.e("GSON", gson.toJson(Offers.getOffers()));
                    fillOffers(Offers.getOffers());
                } else {

                }
            }

            @Override
            public void onFailure(Call<OfferDetailsJsonObject> call, Throwable t) {
                //textView.setText(t.getMessage());
            }
        });
    }


    public static void likeOffer(int offerId) {
        // Map is used to multipart the file using okhttp3.RequestBody
        AppConfig appConfig = new AppConfig(API.BASE_URL);

        ApiConfig getOffers = appConfig.getRetrofit().create(ApiConfig.class);
        Call<String> call;
        LikeModel likeModel = new LikeModel();
        likeModel.setOfferId(offerId);
        likeModel.setUserId(1);
        Gson gson = new Gson();


        call = getOffers.likeOffer(gson.toJson(likeModel), API.URL_TOKEN);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                String Offers = response.body();
                Log.e("Like",Offers);

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //textView.setText(t.getMessage());
            }
        });
    }

    @SuppressLint("ResourceType")
    private void fillOffers(OfferDetails Offers) {
        project_name.setText(Offers.getName());
        proDetail.setText(Offers.getDescription());
        num_likes.setText(String.valueOf(Offers.getNumLiks()));
        nun_contributor.setText(String.valueOf(Offers.getNumContributorTo()));
        if (Offers.getUsers() != null && Offers.getUsers().size() > 1) {
            conAdapter = new ContributerImages(getActivity(), Offers.getUsers());
            recCont.setAdapter(conAdapter);
        }

        if (Offers.getProfitType() == 0) {
            moneyProfitType.setText(getResources().getString(R.string.day));
        } else if (Offers.getProfitType() == 1) {
            moneyProfitType.setText(getResources().getString(R.string.month));
        } else if (Offers.getProfitType() == 2) {
            moneyProfitType.setText(getResources().getString(R.string.year));
        } else if (Offers.getProfitType() == 3) {
            moneyProfitType.setText(getResources().getString(R.string.anotherKind));
        }

        if (Offers.getRequirments() != null && Offers.getRequirments().get(0).isNeedPlace()) {
            if (Offers.getRequirments().get(0).isNeedPlaceType()) {
                placeType.setText(getResources().getString(R.string.avail));
            } else if (Offers.getProfitType() == 1) {
                placeType.setText(getResources().getString(R.string.notAvail));
            }

            if (Offers.getRequirments().get(0).isNeedPlaceStatus()) {
                placeState.setText(getResources().getString(R.string.rent));
            } else if (Offers.getProfitType() == 1) {
                placeState.setText(getResources().getString(R.string.owned));
            }

            if (Offers.getRequirments().get(0).getPlaceAddress() != null && !Offers.getRequirments().get(0).getPlaceAddress().isEmpty()) {
                placeAddress.setText(Offers.getRequirments().get(0).getPlaceAddress());
            } else {
                placeAddress.setText(getString(R.string.noSpAddress));
            }

            if (Offers.getRequirments().get(0).getPlaceDescriptions() != null && !Offers.getRequirments().get(0).getPlaceDescriptions().isEmpty()) {
                placeDesc.setText(Offers.getRequirments().get(0).getPlaceDescriptions());
            } else {
                placeDesc.setText(getString(R.string.noDesPlace));
            }

        } else {
            place.setVisibility(View.GONE);
            placeSection.setVisibility(View.GONE);
        }

        depName.setText(Offers.getCategoryName());
        if (Offers.getRequirments() != null && Offers.getRequirments().get(0).isNeedExperience()) {
            //Todo: load Data From Experience Model and load it to (exDep)
            experienceFrom.setText(String.valueOf(Offers.getRequirments().get(0).getExperienceFrom()));
            experienceTo.setText(String.valueOf(Offers.getRequirments().get(0).getExperienceTo()));
            exDesc.setText(String.valueOf(Offers.getRequirments().get(0).getExperienceDescriptions()));
        } else {
            experience.setVisibility(View.GONE);
            experienceSection.setVisibility(View.GONE);
        }
        if (Offers.getRequirments() != null && Offers.getRequirments().get(0).isNeedMoney()) {
            moneyOutFrom.setText(String.valueOf(Offers.getProfitFrom()));
            moneyOutTo.setText(String.valueOf(Offers.getProfitTo()));
            moneyInFrom.setText(String.valueOf(Offers.getRequirments().get(0).getMoneyFrom()));
            moneyInTo.setText(String.valueOf(Offers.getRequirments().get(0).getMoneyTo()));
        } else {
            moneyRequired.setVisibility(View.GONE);
        }

        if (Offers.getGenderContributor() == 0) {
            genderRequired.setText(getResources().getString(R.string.male));
        } else if (Offers.getProfitType() == 1) {
            genderRequired.setText(getResources().getString(R.string.female));
        } else if (Offers.getProfitType() == 2) {
            genderRequired.setText(getResources().getString(R.string.both));
        }

        conFrom.setText(String.valueOf(Offers.getNumContributorFrom()));
        conTo.setText(String.valueOf(Offers.getNumContributorTo()));
        educationLevel.setCurrentStep(Offers.getEducationContributorLevel());

        if (Offers.getUsers() != null) {
            for (int i = 0; i < Offers.getUsers().size(); i++) {
                if (Offers.getUserId().equals(Offers.getUsers().get(i).getId())) {
                    if (Offers.getUsers().get(i).getImage() != null && !Offers.getUsers().get(i).getImage().isEmpty()) {
                        user_name.setText(Offers.getUsers().get(i).getFullName());
                        Picasso.get().load(API.BASE_URL + Offers.getUsers().get(i).getImage()).into(user_image);
                    }
                }
            }
        }

        if (Offers.getRequirments().get(0).getAttachmentModels().size() > 0) {
            for (int i = 0; i < Offers.getRequirments().get(0).getAttachmentModels().size(); i++) {
                AttachmentModel model = Offers.getRequirments().get(0).getAttachmentModels().get(i);
                Gson gson = new Gson();
                Log.e("Gson", gson.toJson(model));
                if (model.getType().equals(true)) {
                    Log.e("Type", "File");
                    try {
                        FilesModel f = new FilesModel();
                        f.setFileName(API.BASE_URL + model.getSource());
                        f.setServerFileName(model.getName());
                        filesModels.add(f);
                        filesAdapter.notifyDataSetChanged();
                    } catch (Exception ex) {
                        Log.e("Files EX", ex.getMessage());
                    }
                } else {
                    Log.e("Type", "Image");
                    try {
                        FilesModel f = new FilesModel();
                        f.setFileName(API.BASE_URL + model.getSource());
                        imagesModels.add(f);
                        imagesAdapter.notifyDataSetChanged();
                        Picasso.get().load(API.BASE_URL + model.getSource()).into(preview);

                    } catch (Exception ex) {
                        Log.e("Images EX", ex.getMessage());
                    }
                }

            }

            if (filesModels.size() > 0) {
                recFiles.setVisibility(View.VISIBLE);
            }

            if (imagesModels.size() > 0) {
                recImages.setVisibility(View.VISIBLE);
                preview.setVisibility(View.VISIBLE);
            }


        } else {
            attachment.setVisibility(View.GONE);
//                attachmentSection.setVisibility(View.GONE);
            recImages.setVisibility(View.GONE);
            recFiles.setVisibility(View.GONE);
            preview.setVisibility(View.GONE);
        }


//        //Todo: Download file names & images source
//        AppConfig appConfig = new AppConfig(API.BASE_URL);
//
//        if (Offers.getRequirments() != null) {
//            if (Offers.getRequirments().get(0).getAttachmentModels().size() > 0) {
//                for (int i = 0; i < Offers.getRequirments().get(0).getAttachmentModels().size(); i++) { // Todo: Attachment.size()
//                    AttachmentModel model = Offers.getRequirments().get(0).getAttachmentModels().get(i);
//                    String fileLink = model.getName(); //Todo: attachment.getName()
//                    if (!model.getType()) {
//                        ApiConfig getResponse = appConfig.getRetrofit().create(ApiConfig.class);
//                        getResponse.download(fileLink, new Callback<AttachmentModel>() {
//                            @Override
//                            public void onResponse(Call<AttachmentModel> call, Response<AttachmentModel> response) {
//                                AttachmentModel model = response.body();
//                                if (model != null) {
//                                    try {
//                                        FilesModel f = new FilesModel();
//                                        f.setFileName(API.BASE_URL + model.getSource());
//                                        imagesModels.add(f);
//                                        imagesAdapter.notifyDataSetChanged();
//                                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), f.getFileUri());
//                                        preview.setImageBitmap(bitmap);
//
//                                    } catch (Exception ex) {
//                                        Log.e("Images EX", ex.getMessage());
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<AttachmentModel> call, Throwable t) {
//
//                            }
//                        });
//                    } else {
//                        ApiConfig getResponse = appConfig.getRetrofit().create(ApiConfig.class);
//                        getResponse.download(fileLink, new Callback<AttachmentModel>() {
//                            @Override
//                            public void onResponse(Call<AttachmentModel> call, Response<AttachmentModel> response) {
//                                AttachmentModel model = response.body();
//                                if (model != null) {
//                                    try {
//                                        FilesModel f = new FilesModel();
//                                        f.setFileName(API.BASE_URL + model.getSource());
//                                        filesModels.add(f);
//                                        filesAdapter.notifyDataSetChanged();
//                                    } catch (Exception ex) {
//                                        Log.e("Files EX", ex.getMessage());
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<AttachmentModel> call, Throwable t) {
//
//                            }
//                        });
//                    }
//                }
//            } else {
//                attachment.setVisibility(View.GONE);
////                attachmentSection.setVisibility(View.GONE);
//                recImages.setVisibility(View.GONE);
//                recFiles.setVisibility(View.GONE);
//                preview.setVisibility(View.GONE);
//            }
//
//        }
    }

    BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            Toast.makeText(ctxt, getString(R.string.finishFileDownload), Toast.LENGTH_LONG).show();
        }
    };
}