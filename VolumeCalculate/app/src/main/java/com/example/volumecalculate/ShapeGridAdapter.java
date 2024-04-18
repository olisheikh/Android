package com.example.volumecalculate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class ShapeGridAdapter extends ArrayAdapter<Shape> {

    private Context context;
    private List<Shape> shapes;

    public ShapeGridAdapter(Context context, List<Shape> shapes) {
        super(context, R.layout.grid_list, shapes);
        this.context = context;
        this.shapes = shapes;
    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null) {
            viewHolder = new ViewHolder();

            view = LayoutInflater.from(context).inflate(R.layout.grid_list, viewGroup, false);

            viewHolder.txtView = (TextView) view.findViewById(R.id.title);
            viewHolder.imgView = (ImageView) view.findViewById(R.id.logo);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.imgView.setImageResource(shapes.get(position).getImg());
        viewHolder.txtView.setText(shapes.get(position).getTitle());

        return view;
    }

    public static class ViewHolder {
        TextView txtView;
        ImageView imgView;
    }
}
