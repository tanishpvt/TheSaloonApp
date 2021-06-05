package com.example.barber;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapterToday extends BaseAdapter {
    ArrayList<String> customer_name,booking_date,scheduling_time,mobile_number;
    LayoutInflater inflater;
    ListAdapterToday(Context context, ArrayList<String> customername, ArrayList<String> booking_date, ArrayList<String> scheduling_time,ArrayList<String> mobile_number)
    {
        this.customer_name=customername;
        this.booking_date=booking_date;
        this.scheduling_time=scheduling_time;
        this.mobile_number=mobile_number;
        inflater=(LayoutInflater.from(context));
    }
    @Override
    public int getCount() {
        return customer_name.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup)
    {
        view=inflater.inflate(R.layout.custom_listtoday,viewGroup,false);

        TextView customername=view.findViewById(R.id.customername);
        TextView booking=view.findViewById(R.id.bookdisplay);
        TextView schedule=view.findViewById(R.id.scheduledisplay);
        TextView Contact=view.findViewById(R.id.ContactNumber);

        customername.setText(customer_name.get(position));
        booking.setText(booking_date.get(position));
        schedule.setText(scheduling_time.get(position));
        Contact.setText(mobile_number.get(position));

        return view;
    }
}
