package com.bb.radioetzion.BL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bb.radioetzion.R;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ListAdapter extends BaseAdapter {
    private Context context;
    private List<Show> showsList;

    // get the context and show list from the main activity in the constructor
    public ListAdapter(Context context, List<Show> showsList) {
        this.context = context;
        this.showsList = showsList;
    }

    // set the list count by the passed list size
    @Override
    public int getCount() {
        return showsList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /** return the current inflated view
     *  recycle the passed view and if necessary inflates the list_row layout using viewHolder pattern
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(showsList.get(position).getName());
        // set the length in format HH:mm:ss
        viewHolder.length.setText(fromMinutesToHHmm(showsList.get(position).getLengthMinutes()));
        viewHolder.created.setText(showsList.get(position).getCreatedTime().substring(0, 10));
        return convertView;
    }

    /**
     * Creating ViewHolder inner class to optimize the listview's performance by reducing the number of
     * findViewById calls in the adapter's getView
      */

    private class ViewHolder {
        // Declaring row_list members
        TextView name;
        TextView length;
        TextView created;

        // Assign reference to the row_list members
        private ViewHolder(View view) {
            name = view.findViewById(R.id.lblName);
            length = view.findViewById(R.id.lblLength);
            created = view.findViewById(R.id.lblCreated);
        }
    }

    // Convert minutes (int) to string HH:mm format
    private String fromMinutesToHHmm(int minutes) {
        long hours = TimeUnit.MINUTES.toHours(minutes);
        long remainMinutes = minutes - TimeUnit.HOURS.toMinutes(hours);
        if(hours>59){
            return String.format(Locale.getDefault(), "%02d:%02d:%02d",hours/60, hours%60, remainMinutes);
        }
        return String.format(Locale.getDefault(), "%02d:%02d", hours, remainMinutes);
    }
}
