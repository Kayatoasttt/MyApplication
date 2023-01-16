package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import org.w3c.dom.Text;

import java.sql.Array;
import java.util.ArrayList;

public class commonGamesRecyclerAdapter extends RecyclerView.Adapter<commonGamesRecyclerAdapter.ViewHolder> {

    private static final String TAG = "RecyclerAdapter";

    private ArrayList<String> gameNames = new ArrayList<>();
    private ArrayList<String> gameIcons = new ArrayList<>();
    private Context mContext;

    public commonGamesRecyclerAdapter(ArrayList<String> gameNames, ArrayList<String> gameIcons, Context mContext) {
        this.gameNames = gameNames;
        this.gameIcons = gameIcons;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public commonGamesRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_games_list_items,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: ");

        Glide.with(mContext)
                .asBitmap()
                .load(gameIcons.get(position))
                .into(holder.gameIcon);

        holder.gameName.setText(gameNames.get(position));

        holder.gameIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on an image: " + gameNames.get(holder.getAdapterPosition()));
                Toast.makeText(mContext,gameNames.get(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return gameIcons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ShapeableImageView gameIcon;
        TextView gameName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gameIcon = itemView.findViewById(R.id.circularGameIcon);
            gameName = itemView.findViewById(R.id.circularGameName);
        }
    }
}
