package com.librapp.librapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.librapp.librapp.R;
import com.librapp.librapp.models.Book;

import java.util.List;

public class BookAdapterListView extends BaseAdapter {

    private Context context;
    private List<Book> books;
    private int layout;

    public BookAdapterListView(Context context, List<Book> books, int layout) {
        this.context = context;
        this.books = books;
        this.layout = layout;

    }


    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Book getItem(int position) {
        return books.get(position);
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
            viewHolder.title = convertView.findViewById(R.id.textViewBookTitle);
            viewHolder.author = convertView.findViewById(R.id.textViewBookAuthor);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Book book = books.get(position);

        viewHolder.title.setText(book.getTitle());
        if (book.getAuthor()!= null) {
            viewHolder.author.setText(book.getAuthor().getName());
        }

        return convertView;
    }

    public class ViewHolder {
        TextView title;
        TextView author;
    }
}
