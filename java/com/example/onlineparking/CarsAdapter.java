package com.example.onlineparking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.MyViewHolder>{
    private Context context;
    private List<ParkCarModel> parkCarModelList;
    private CarAdapterInterface listener;
    private List<ParkCarModel> parkCarModelListFiltered;

    public CarsAdapter(Context context, CarAdapterInterface listener) {
        this.context = context;
        this.listener = listener;
        parkCarModelList = new ArrayList<>();
        parkCarModelListFiltered = new ArrayList<>();
    }

    public void add(ParkCarModel parkCarModel){
        parkCarModelList.add(parkCarModel);
        parkCarModelListFiltered.add(parkCarModel);

        notifyDataSetChanged();

    }
    public void remove(int pos){
        parkCarModelList.remove(pos);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.park_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ParkCarModel model = parkCarModelList.get(position);
        holder.driverNumber.setText(model.getDriverNumber());
        holder.driverName.setText(model.getDriverName());
        holder.numberPlate.setText(model.getNumberPlate());
        holder.fee.setText(model.getFee());
        String[] dateAndTime = longIntoString(model.getTime());
        holder.status.setText(model.getStatus());

        holder.time.setText(dateAndTime[0]+"\n"+dateAndTime[1]);
        holder.vehicleType.setText(model.getVehicleType());

    }

    @Override
    public int getItemCount() {
        return parkCarModelList.size();

    }

    public void filter(String query) {
        parkCarModelList.clear();
        if (query.isEmpty()) {
            parkCarModelList.addAll(parkCarModelListFiltered);
        } else {
            for (ParkCarModel model : parkCarModelListFiltered) {
               // Log.d("TAG", String.valueOf(model.getNumberPlate()));
                if (model.getNumberPlate().toLowerCase().contains(query.toLowerCase())) {
                    parkCarModelList.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView vehicleType, driverName, driverNumber, numberPlate, fee, time,status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            vehicleType=itemView.findViewById(R.id.vehicleType);
            driverName=itemView.findViewById(R.id.driverName);
            driverNumber=itemView.findViewById(R.id.driverNumber);
            numberPlate=itemView.findViewById(R.id.numberPlate);
            fee=itemView.findViewById(R.id.amount);
            time=itemView.findViewById(R.id.date);
            status=itemView.findViewById(R.id.status);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.OnLongClick(getAdapterPosition(),
                            parkCarModelList.get(getAdapterPosition()).getId());
                    return true;
                }
            });

        }
    }

    private String [] longIntoString(long milliseconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm");
        SimpleDateFormat timeFormat = new SimpleDateFormat("");
        String date = dateFormat.format(calendar.getTime());
        String time = timeFormat.format(calendar.getTime());
        return new String[]{date, time};
    }


    }



