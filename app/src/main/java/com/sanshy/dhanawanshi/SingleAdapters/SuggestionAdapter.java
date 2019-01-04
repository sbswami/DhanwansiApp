package com.sanshy.dhanawanshi.SingleAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.sanshy.dhanawanshi.R;

import java.util.ArrayList;

public class SuggestionAdapter extends ArrayAdapter<SuggetionItem> {

    ArrayList<SuggetionItem> SuggetionItems, tempSuggetionItem, suggestions;

    public SuggestionAdapter(Context context, ArrayList<SuggetionItem> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.SuggetionItems = objects;
        this.tempSuggetionItem = new ArrayList<SuggetionItem>(objects);
        this.suggestions = new ArrayList<SuggetionItem>(objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SuggetionItem SuggetionItem = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.suggestion_list, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView Village = convertView.findViewById(R.id.ganv);
        TextView FirstRelationKey = convertView.findViewById(R.id.first_relation_key);
        TextView FirstRelationValue = convertView.findViewById(R.id.first_relation_value);
        if (name != null)
            name.setText(SuggetionItem.getName()+" ("+SuggetionItem.getCast()+")");

        Village.setText(SuggetionItem.getVillage());
        FirstRelationKey.setText(SuggetionItem.getFirstRelationKey());
        FirstRelationValue.setText(SuggetionItem.getFirstRelationValue());

        // Now assign alternate color for rows

        return convertView;
    }


    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            SuggetionItem SuggetionItem = (SuggetionItem) resultValue;
            return SuggetionItem.getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (SuggetionItem people : tempSuggetionItem) {
                    if (people.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<SuggetionItem> c = (ArrayList<SuggetionItem>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (SuggetionItem cust : c) {
                    add(cust);
                    notifyDataSetChanged();
                }
            }
        }
    };
}

