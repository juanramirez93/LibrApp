package com.librapp.librapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.librapp.librapp.R;
import com.librapp.librapp.models.Author;
import com.librapp.librapp.models.Book;

import java.util.List;

public class AuthorAdapterSpinner extends BaseAdapter {

    private Context context;
    private List<Author> authors;
    private int layout;

    public AuthorAdapterSpinner(Context context, List<Author> authors, int layout) {
        this.context = context;
        Author a = new Author();
        this.authors = authors;
        this.layout = layout;

    }

    @Override
    public int getCount() {
        return authors.size();
    }

    @Override
    public Author getItem(int position) {
        return authors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layout, null);
            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.textViewAuthorName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Author author = authors.get(position);

        viewHolder.name.setText(author.getName());

        return convertView;
    }

    public class ViewHolder {
        TextView name;
    }
}
