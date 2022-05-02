package com.vegana.issposition.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vegana.issposition.R;
import com.vegana.issposition.model.ResponseISS;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PositionAdapter extends RecyclerView.Adapter<PositionAdapter.ViewHolder>{
    private ArrayList<ResponseISS> items;
    private ItemClickListener clickListener;

    public PositionAdapter(ArrayList<ResponseISS> items){
        this.items = items;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public ResponseISS getItem(int position){
        return items.get(position);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @NonNull
    @Override
    public PositionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.item_position, parent, false);

        // Return a new holder instance
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponseISS iss = items.get(position);
        Date date = new Date(iss.getRisetime() * 1000);
        DateFormat dateFormat = new SimpleDateFormat("EEEE d 'de' MMMM 'a las' HH:mm");
        String dateString = dateFormat.format(date);
        holder.dateTimePosition.setText(dateString.substring(0, 1).toUpperCase().concat(dateString.substring(1)));
        int minutes = iss.getDuration() / 60;
        int seconds = iss.getDuration() - (minutes * 60);
        String duration = "Durante %d minutos y %d segundos";
        holder.durationPosition.setText(String.format(duration, minutes, seconds));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView dateTimePosition;
        public TextView durationPosition;

        public ViewHolder(View itemView) {
            super(itemView);

            dateTimePosition = (TextView) itemView.findViewById(R.id.textDateTimePosition);
            durationPosition = (TextView) itemView.findViewById(R.id.textDurationPosition);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onItemClick(view, getBindingAdapterPosition());
        }
    }
}
