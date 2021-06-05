package com.example.barber;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CMyListAdapter extends BaseAdapter {
    ArrayList<String> saloon_name,booking_date,scheduling_date,Contact;

    LayoutInflater inflater;
    public CMyListAdapter(Context context,ArrayList<String> saloonname,ArrayList<String> booking_date,ArrayList<String> scheduling_date,ArrayList<String> Contact)
    {
        this.saloon_name=saloonname;
        this.booking_date=booking_date;
        this.scheduling_date=scheduling_date;
        this.Contact=Contact;
        inflater=(LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return saloon_name.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position,View view,ViewGroup viewGroup)
    {

        view=inflater.inflate(R.layout.custom_list,viewGroup,false);

        TextView saloonname=view.findViewById(R.id.saloonname);
        TextView booking=view.findViewById(R.id.bookdisplay);
        TextView schedule=view.findViewById(R.id.scheduledisplay);
        TextView contact=view.findViewById(R.id.ContactNumberCustomer);

        saloonname.setText(saloon_name.get(position));
        booking.setText(booking_date.get(position));
        schedule.setText(scheduling_date.get(position));
        contact.setText(Contact.get(position));

        return view;
    }
}
