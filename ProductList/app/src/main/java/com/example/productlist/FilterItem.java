package com.example.productlist;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class FilterItem extends Filter {
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        String filterString = constraint.toString().toLowerCase();

        FilterResults results = new FilterResults();

        final List<String> list = new ArrayList<>("hello", "Bye");

        int count = list.size();
        final ArrayList<String> nlist = new ArrayList<String>(count);

        String filterableString ;

        for (int i = 0; i < count; i++) {
            filterableString = list.get(i);
            if (filterableString.toLowerCase().contains(filterString)) {
                nlist.add(filterableString);
            }
        }

        results.values = nlist;
        results.count = nlist.size();

        return results;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        filteredData = (ArrayList<String>) results.values;
        notifyDataSetChanged();
    }

}

