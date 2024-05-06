package com.example.sportsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class SportCustomAdapter extends RecyclerView.Adapter<SportCustomAdapter.SportViewHolder>{
    List<Sport> sports;

    public SportCustomAdapter(List<Sport> sports) {
        this.sports = sports;
    }
    @Override
    public int getItemCount() {
        return sports.size();
    }

    @Override
    public SportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.sports_types_card_view,
                        parent,
                        false
                );

        return new SportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SportViewHolder sportViewHolder, int position) {
        Sport sport = sports.get(position);

        sportViewHolder.titleOfSport.setText(sport.getSportName());
        sportViewHolder.imgOfSport.setImageResource(sport.getSportImg());
    }

    public static class SportViewHolder extends RecyclerView.ViewHolder{
        TextView titleOfSport;
        ImageView imgOfSport;
        public SportViewHolder(View view) {
            super(view);

            titleOfSport = view.findViewById(R.id.titleOfCardView);
            imgOfSport = view.findViewById(R.id.backgroundPic);
        }
    }
}
