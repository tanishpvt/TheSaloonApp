package com.example.barber;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class OMyListAdapter extends BaseAdapter {
    ArrayList<String> customer_name,booking_date,scheduling_date,Customer_Mobile_Number;
    LayoutInflater inflater;
    public OMyListAdapter(Context context, ArrayList<String> customername, ArrayList<String> booking_date, ArrayList<String> scheduling_date,ArrayList<String> Mobile_number)
    {
        this.customer_name=customername;
        this.booking_date=booking_date;
        this.scheduling_date=scheduling_date;
        this.Customer_Mobile_Number=Mobile_number;
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

    public View getView(int position, View view, ViewGroup viewGroup)
    {

        view=inflater.inflate(R.layout.custom_listowner,viewGroup,false);

        TextView customername=view.findViewById(R.id.customername);
        TextView booking=view.findViewById(R.id.slotdisplay);
        TextView schedule=view.findViewById(R.id.scheduledisplay);
        TextView mobile=view.findViewById(R.id.ContactCustomerNumber);

        customername.setText(customer_name.get(position));
        booking.setText(booking_date.get(position));
        schedule.setText(scheduling_date.get(position));
        mobile.setText(Customer_Mobile_Number.get(position));
        return view;
    }
}
