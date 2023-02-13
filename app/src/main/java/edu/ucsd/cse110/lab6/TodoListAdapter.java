package edu.ucsd.cse110.lab6;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> {

    private List<TodoListItem> todoItems = Collections.emptyList();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.todo_list_item, parent, false);

        return new ViewHolder(view);
    }

    public void setTodoListItems(List<TodoListItem> newTodoItems) {
        this.todoItems.clear();
        this.todoItems = newTodoItems;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setTodoItem(todoItems.get(position));
    }

    @Override
    public int getItemCount() {
        return todoItems.size();
    }

    @Override
    public long getItemId(int position) {
        return todoItems.get(position).id;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private TodoListItem todoItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.todo_item_text);
        }

        public TodoListItem getTodoItem() {
            return todoItem;
        }

        public void setTodoItem(TodoListItem todoItem) {
            this.todoItem = todoItem;
            this.textView.setText(todoItem.text);
        }
    }
}
