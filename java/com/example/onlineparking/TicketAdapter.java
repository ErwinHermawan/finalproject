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
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter <TicketAdapter.MyViewHolder> {
    private Context context;
    private List<ParkCarModel> parkCarModelList;
    private TicketAdapterInterface listener;

    public TicketAdapter(Context context, TicketAdapterInterface listener) {
        this.context = context;
        this.listener = listener;
        parkCarModelList = new ArrayList<>();
    }

    public void add(ParkCarModel parkCarModel) {
        parkCarModelList.add(parkCarModel);
        notifyDataSetChanged();
    }
    public void remove(int pos){
        parkCarModelList.remove(pos);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TicketAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.park_item,parent,false);
        return new TicketAdapter.MyViewHolder(view);
    }





    @Override
    public void onBindViewHolder(@NonNull TicketAdapter.MyViewHolder holder, int position) {
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
    private String[] longIntoString(long milliseconds) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd,yyyy");
        return new String[]{dateFormat.format(milliseconds), timeFormat.format(milliseconds)};
    }
}