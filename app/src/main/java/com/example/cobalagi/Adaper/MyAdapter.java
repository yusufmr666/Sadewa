package com.example.cobalagi.Adaper;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cobalagi.DetailActivity;
import com.example.cobalagi.GPSTracker;
import com.example.cobalagi.R;
import com.example.cobalagi.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Double startLat;
    private Double startlng;
    private Double endlat;
    private Double endLng;
    private double distance;
    GPSTracker gpsHandler;

    private List<User> list = new ArrayList<User>();

    String finaldistance;

    public MyAdapter(ArrayList<User> list)
    {
        this.list = list;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listd, viewGroup, false);
        gpsHandler = new GPSTracker(viewGroup.getContext());

        for ( User tempPlace: list){

            endLng = Double.parseDouble(tempPlace.getLng());
            endlat = Double.parseDouble(tempPlace.getLat());
            startlng = gpsHandler.getLongitude();
            startLat = gpsHandler.getLatitude();
            distance = Haversine.calculateDistance(startLat, startlng, endlat, endLng);
            tempPlace.setPlaceDistance(distance);
        }


        Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return new Double(o1.getPlaceDistance()).compareTo(new Double(o2.getPlaceDistance()));
            }
        });
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        User user = list.get(position);

        finaldistance = String.format("%.2f", user.getPlaceDistance());

        holder.getTvJarak.setText("" + finaldistance + " km");
        holder.firstName.setText(user.getname());
        holder.lastName.setText(user.getalamat());
        Glide.with(holder.img.getContext()).load(user.getImg()).into(holder.img);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(ArrayList<User> filterllist) {
        list = filterllist;

        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView firstName, lastName, getTvJarak;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.id);
            lastName = itemView.findViewById(R.id.idd);
            img = itemView.findViewById(R.id.image);
            getTvJarak = itemView.findViewById(R.id.Jarak);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
                    User modelWisata = list.get(getAdapterPosition());
                    intent.putExtra("pass_nama",modelWisata.getname());
                    intent.putExtra("alamat",modelWisata.getalamat());
                    intent.putExtra("gambar",modelWisata.getImg());
                    intent.putExtra("gambar1",modelWisata.getImg1());
                    intent.putExtra("gambar2",modelWisata.getImg2());
                    intent.putExtra("gambar3",modelWisata.getImg3());
                    intent.putExtra("pass_deskripsi",modelWisata.getDeskripsi());
                    intent.putExtra("pass_lat",modelWisata.getLat());
                    intent.putExtra("pass_lng",modelWisata.getLng());
                    intent.putExtra("sumber", modelWisata.getSumber());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }






}