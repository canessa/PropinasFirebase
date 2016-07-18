package edu.galileo.android.androidchat.adapters;

import butterknife.ButterKnife;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import edu.galileo.android.androidchat.R;
import edu.galileo.android.androidchat.models.TipRecord;

/**
 * Created by cmiguel on 18/07/2016.
 */
public class TipAdapter extends RecyclerView.Adapter<TipAdapter.ViewHolder>  {
    private List<TipRecord> dataset;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public TipAdapter( Context context, List<TipRecord> dataset, OnItemClickListener onItemClickListener) {
        this.dataset = dataset;
        this.context = context;
        this.onItemClickListener    =   onItemClickListener;
    }

    public TipAdapter( Context context, OnItemClickListener onItemClickListener) {
        this.dataset = new ArrayList<TipRecord>();
        this.context = context;
        this.onItemClickListener    =   onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view   = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TipRecord   element =   dataset.get(position);
        String      strTip  =   String.format(context.getString(R.string.global_messa_tip),element.getTip());

        holder.txtContent.setText(strTip);
        holder.txtDate.setText(element.getDateFormatted());
        holder.setOnItemClickListener(element,onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void add(TipRecord record){
        dataset.add(0,record);
        notifyDataSetChanged();
    }

    public void clear(){
        dataset.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.textContent)
        TextView txtContent;

        @Bind(R.id.textDate)
        TextView txtDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setOnItemClickListener(final TipRecord element, final OnItemClickListener onItemClickListener) {
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    onItemClickListener.onItemClick(element);
                }
            });
        }
    }
}
