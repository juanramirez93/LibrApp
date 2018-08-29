package com.librapp.librapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.librapp.librapp.R;
import com.librapp.librapp.models.Book;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;


public class BookAdapterRecyclerView extends RecyclerView.Adapter<BookAdapterRecyclerView.ViewHolder> {

    private List<Book> books;
    private int layout;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public BookAdapterRecyclerView(List<Book> books, int layout, OnItemClickListener onItemClickListener, OnItemLongClickListener onItemLongClickListener) {
        this.books = books;
        this.layout = layout;
        this.onItemClickListener = onItemClickListener;
        this.onItemLongClickListener = onItemLongClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(books.get(position), onItemClickListener, onItemLongClickListener);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView author;
        public ImageView image;
        public RequestManager mGlideRequestManager;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.rVBookTitle);
            this.author = itemView.findViewById(R.id.rVBookAuthor);
            this.image = itemView.findViewById(R.id.rVBookImage);
            mGlideRequestManager = Glide.with(itemView.getContext());
        }

        public void bind(final Book book, final OnItemClickListener onItemClickListener, final OnItemLongClickListener onItemLongClickListener) {

            this.title.setText(book.getTitle());
            if (book.getAuthor() != null) {
                this.author.setText(book.getAuthor().getName());
            } else {
                this.author.setText("NR");
            }
            if (book.getImagePath() == null || book.getImagePath().isEmpty()) {
                Picasso.get().load(R.mipmap.ic_book_default).into(image);
            }else{
                mGlideRequestManager
                        .load(book.getImagePath())
                        .into(image);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(book, getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    BookAdapterRecyclerView.this.onItemLongClickListener.onItemLongClick(book, getAdapterPosition());
                    return true;
                }
            });
        }

    }

    public interface OnItemClickListener {
        void onItemClick(Book book, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Book book, int position);
    }

}
