package id.sch.smktelkom_mlg.learn.recyclerview2;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Hotel> mlist = new ArrayList<>();
    HotelAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new HotelAdapter(mlist);
        recyclerView.setAdapter(mAdapter);

        fillData();
    }

    private void fillData() {
        Resources resources = getResources();
        String[] arJudul = resources.getStringArray(R.array.places);
        String[] arDeskripsi = resources.getStringArray(R.array.place_desc);
        TypedArray a = resources.obtainTypedArray(R.array.places_picture);
        Drawable[] arFoto = new Drawable[a.length()];
        for (int i = 0; i < arFoto.length; i++) {
            BitmapDrawable bd = (BitmapDrawable) a.getDrawable(i);
            RoundedBitmapDrawable rbd =
                    RoundedBitmapDrawableFactory.create(getResources(), bd.getBitmap());
            rbd.setCircular(true);
            arFoto[i] = rbd;
        }
        a.recycle();

        for (int i = 0; i < arJudul.length; i++) {
            mlist.add(new Hotel(arJudul[i], arDeskripsi[i], arFoto[i]));
        }
        mAdapter.notifyDataSetChanged();
    }

    public class Hotel {
        public String judul;
        public String deskripsi;
        public Drawable foto;

        public Hotel(String judul, String deskripsi, Drawable foto) {
            this.judul = judul;
            this.deskripsi = deskripsi;
            this.foto = foto;
        }
    }

    public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {
        ArrayList<Hotel> hotelList;

        public HotelAdapter(ArrayList<Hotel> hotelList) {
            this.hotelList = hotelList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Hotel hotel = hotelList.get(position);
            holder.tvJudul.setText(hotel.judul);
            holder.tvDeskripsi.setText(hotel.deskripsi);
            holder.ivFoto.setImageDrawable(hotel.foto);
        }

        @Override
        public int getItemCount() {
            if (hotelList != null)
                return hotelList.size();
            return 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivFoto;
            TextView tvJudul;
            TextView tvDeskripsi;

            public ViewHolder(View itemView) {
                super(itemView);
                ivFoto = (ImageView) itemView.findViewById(R.id.imageView);
                tvJudul = (TextView) itemView.findViewById(R.id.textViewJudul);
                tvDeskripsi = (TextView) itemView.findViewById(R.id.textViewDeskripsi);
            }
        }
    }
}

