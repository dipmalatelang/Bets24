package com.app.bet.HomeScreen.News.CricketNews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bet.HomeScreen.More.PrivacyTermsActivity;
import com.app.bet.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class NewsCricketAdapter extends RecyclerView.Adapter<NewsCricketAdapter.MyViewHolder> {

    private Context context;
    private RssFeedCricket rssFeedCricket;
    private int count;

    public NewsCricketAdapter(Context context, RssFeedCricket rssFeedCricket, int count) {
        this.context = context;
        this.rssFeedCricket = rssFeedCricket;
        this.count = count;
    }

    @NonNull
    @Override
    public NewsCricketAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsCricketAdapter.MyViewHolder holder, final int position) {

        holder.TvTitile.setText(rssFeedCricket.getItemCrickets().get(position).title);
        holder.TvDate.setText(rssFeedCricket.getItemCrickets().get(position).pubDate);

        Glide.with(context)
                .load(rssFeedCricket.getItemCrickets().get(position).image)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground))
                .into(holder.IvThumbNail);

        holder.CvNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,PrivacyTermsActivity.class);
                intent.putExtra("url", rssFeedCricket.getItemCrickets().get(position).link);
                intent.putExtra("title", rssFeedCricket.getItemCrickets().get(position).title);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount(){
            return count;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView IvThumbNail;
        TextView TvTitile,TvDate;
        CardView CvNews;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            IvThumbNail = itemView.findViewById(R.id.IvThumbNail);
            TvTitile = itemView.findViewById(R.id.TvTitile);
            TvDate = itemView.findViewById(R.id.TvDate);
            CvNews = itemView.findViewById(R.id.CvNews);
        }
    }
}
