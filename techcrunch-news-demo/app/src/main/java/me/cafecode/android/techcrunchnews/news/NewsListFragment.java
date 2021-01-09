package me.cafecode.android.techcrunchnews.news;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.cafecode.android.techcrunchnews.R;
import me.cafecode.android.techcrunchnews.data.News;
import me.cafecode.android.techcrunchnews.injection.AppModule;

public class NewsListFragment extends Fragment implements NewsListContract.View {

    private View mNewsListLayout;
    private RecyclerView mNewsListView;
    private NewsListAdapter mListAdapter;
    private TextView mNoNewsText;
    private ProgressBar mProgressBar;
    private NewsListContract.Presenter mPresenter;

    public NewsListFragment() {
        // Required empty public constructor
    }

    public static NewsListFragment newInstance() {
        return new NewsListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new NewsListPresenter(AppModule.provideRepository(), this);

        mListAdapter = new NewsListAdapter(new ArrayList<News>());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_news_list, container, false);

        mNewsListLayout = rootView.findViewById(R.id.news_list_layout);

        mNoNewsText = (TextView) rootView.findViewById(R.id.no_news_message_text);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        mNewsListView = (RecyclerView) rootView.findViewById(R.id.news_list_view);
        mNewsListView.setAdapter(mListAdapter);


        int numColumns = getContext().getResources().getInteger(R.integer.num_news_columns);

        mNewsListView.setHasFixedSize(true);
        mNewsListView.setLayoutManager(new GridLayoutManager(getContext(), numColumns));

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter.start();
    }

    @Override
    public void showProgressBar(boolean isShow) {

        if (isShow) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showNewsList(List<News> newsList) {

        mNoNewsText.setVisibility(View.GONE);
        mNewsListView.setVisibility(View.VISIBLE);

        mListAdapter.setNewsList(newsList);
    }

    @Override
    public void showNoNews() {

        mNoNewsText.setVisibility(View.VISIBLE);
        mNewsListView.setVisibility(View.GONE);
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Snackbar.make(mNewsListLayout, errorMessage, Snackbar.LENGTH_SHORT).setAction("TRY AGAIN", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadNewsList();
            }
        }).show();
    }

    private static class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

        SimpleDateFormat format = new SimpleDateFormat("d MMM yyyy", Locale.getDefault());
        private List<News> mNewsList;
        private Context mContext;

        public NewsListAdapter(List<News> newsList) {
            mNewsList = newsList;
        }

        public void setNewsList(List<News> newsList) {
            mNewsList = newsList;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View newsView = inflater.inflate(R.layout.item_news, parent, false);

            return new ViewHolder(newsView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            News news = mNewsList.get(position);

            holder.title.setText(news.getTitle());
            holder.author.setText(news.getAuthor());

            holder.date.setText(format.format(news.getPublishedAt()));

            Glide.with(mContext)
                    .load(news.getUrlToImage())
                    .crossFade()
                    .into(holder.headerImage);

        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView title;
            TextView author;
            TextView date;
            ImageView headerImage;

            ViewHolder(View itemView) {
                super(itemView);

                title = (TextView) itemView.findViewById(R.id.title_text);
                author = (TextView) itemView.findViewById(R.id.author_text);
                date = (TextView) itemView.findViewById(R.id.date_text);
                headerImage = (ImageView) itemView.findViewById(R.id.header_image);
            }
        }
    }
}
