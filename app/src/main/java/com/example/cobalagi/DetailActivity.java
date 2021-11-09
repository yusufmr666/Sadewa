package com.example.cobalagi;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    TextView tv_nama_detail, tv_deskripsi, tv_sumber, tv_alamat;
    ImageView iv_wisata_detail, iv_gambar1, iv_gambar2, iv_gambar3;
    Button btn_lihat_rute;

    private String nama, deskripsi, gambar, lat, lng, alamat, gambar1, gambar2, gambar3, sumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getDataIntent();
        initView();
    }

    private void getDataIntent() {
        nama        = getIntent().getExtras().getString("pass_nama");
        deskripsi   = getIntent().getExtras().getString("pass_deskripsi");
        gambar      = getIntent().getExtras().getString("gambar");
        gambar1     = getIntent().getExtras().getString("gambar1");
        gambar2     = getIntent().getExtras().getString("gambar2");
        gambar3     = getIntent().getExtras().getString("gambar3");
        sumber      = getIntent().getExtras().getString("sumber");
        alamat      = getIntent().getExtras().getString("alamat");
        lat         = getIntent().getExtras().getString("pass_lat");
        lng         = getIntent().getExtras().getString("pass_lng");

        Log.e("ALLDATA",lat+lng);

    }

    private void initView() {


        tv_nama_detail   = (TextView)findViewById(R.id.namdet);
        tv_deskripsi     = (TextView)findViewById(R.id.deskripsi);
        tv_alamat        = (TextView)findViewById(R.id.alamatt);
        iv_wisata_detail = (ImageView) findViewById(R.id.images);
        iv_gambar1       = (ImageView) findViewById(R.id.gambar1);
        iv_gambar2       = (ImageView) findViewById(R.id.gambar2);
        iv_gambar3       = (ImageView) findViewById(R.id.gambar3);
        btn_lihat_rute   = (Button)findViewById(R.id.btn_lihat_rute);
        tv_sumber        = (TextView)findViewById(R.id.sumber);

        tv_nama_detail.setText(nama);
        tv_deskripsi.setText(deskripsi);
        tv_alamat.setText(alamat);
        tv_sumber.setText("Sumber:  " + sumber);
        Glide.with(iv_wisata_detail.getContext()).load(gambar).into(iv_wisata_detail);
        Glide.with(iv_gambar1.getContext()).load(gambar1).into(iv_gambar1);
        Glide.with(iv_gambar2.getContext()).load(gambar2).into(iv_gambar2);
        Glide.with(iv_gambar3.getContext()).load(gambar3).into(iv_gambar3);

        btn_lihat_rute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://maps.google.com/maps?daddr=" +lat + "," +lng)));

            }
        });
    }

    public void onResume() {
        super.onResume();
        ActionBar supportActionBar = ((AppCompatActivity)this).getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.hide();
    }

}