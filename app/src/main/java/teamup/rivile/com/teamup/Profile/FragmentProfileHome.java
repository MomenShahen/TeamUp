package teamup.rivile.com.teamup.Profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import teamup.rivile.com.teamup.DrawerActivity;
import teamup.rivile.com.teamup.Project.List.Project;
import teamup.rivile.com.teamup.R;


public class FragmentProfileHome extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Project> projectList;
    View view;
    TextView num;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rec);
        num =  view.findViewById(R.id.num_projects);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(layoutManager);
        projectList = new ArrayList<>();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((DrawerActivity) getActivity()).Hide();

        adapter = new AdapterProfileProject(getActivity(), projectList);
        recyclerView.setAdapter(adapter);
    }
}