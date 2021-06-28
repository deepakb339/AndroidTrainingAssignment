package org.nagarro.todolist.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;

import org.jetbrains.annotations.NotNull;
import org.nagarro.todolist.R;
import org.nagarro.todolist.databinding.FragmentHomeBinding;
import org.nagarro.todolist.model.Todos;
import org.nagarro.todolist.network.GetDataService;
import org.nagarro.todolist.presenter.HomeFragmentContract;
import org.nagarro.todolist.presenter.HomeFragmentPresenter;
import org.nagarro.todolist.utility.adapter.CustomAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, HomeFragmentContract.View {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ShimmerFrameLayout shimmerFrameLayout;
    TextView listCount;
    LinearLayout errorLayout;
    Button retry;
    HomeFragmentPresenter presenter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        presenter = new HomeFragmentPresenter(getContext());
        recyclerView = binding.customRecyclerView;

        shimmerFrameLayout = binding.shimmerFrameLayout;
        errorLayout = binding.errorPage;
        retry = binding.retryBtn;
        listCount = binding.listCount;
        // SwipeRefreshLayout
        mSwipeRefreshLayout = (SwipeRefreshLayout) binding.swipeContainer;
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.purple_200,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        mSwipeRefreshLayout.post(() -> {

            mSwipeRefreshLayout.setRefreshing(true);

            // Fetching data from server
            loadRecyclerViewData();
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void loadRecyclerViewData() {

        if (presenter.checkNetwork()) {
            shimmerFrameLayout.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            errorLayout.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setRefreshing(false);
            retry.setOnClickListener(v -> loadRecyclerViewData());
        } else {
            // Showing refresh animation before making http call
            errorLayout.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            shimmerFrameLayout.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setRefreshing(true);
            GetDataService service = presenter.getDataService();

            Call<List<Todos>> call = service.getAllTodos();
            call.enqueue(new Callback<List<Todos>>() {

                @Override
                public void onResponse(@NotNull Call<List<Todos>> call, @NotNull Response<List<Todos>> response) {
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.INVISIBLE);
                    listCount.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    // Stopping swipe refresh
                    mSwipeRefreshLayout.setRefreshing(false);
                    generateDataList(response.body());
                }

                @Override
                public void onFailure(@NotNull Call<List<Todos>> call, @NotNull Throwable t) {
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.INVISIBLE);
                    // Stopping swipe refresh
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void generateDataList(List<Todos> todosList) {
        listCount.setText("Number of todos: "+todosList.size()+"/"+todosList.size()*2);
        CustomAdapter adapter = new CustomAdapter(getContext(), todosList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        loadRecyclerViewData();
    }

    @Override
    public void onError() {

    }
}