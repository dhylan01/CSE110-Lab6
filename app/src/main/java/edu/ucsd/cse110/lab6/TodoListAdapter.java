package edu.ucsd.cse110.lab6;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> {

    private List<TodoListItem> todoItems = Collections.emptyList();
    private Consumer<TodoListItem> onCheckBoxClicked;
    private Consumer<TodoListItem> onDeleteClicked;
    private BiConsumer<TodoListItem, String> onTextEditedHandler;

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

    public void setOnCheckBoxClickedHandler(Consumer<TodoListItem> onCheckBoxClicked) {
        this.onCheckBoxClicked = onCheckBoxClicked;
    }

    public void setOnTextEditedHandler(BiConsumer<TodoListItem, String> onTextEdited) {
        this.onTextEditedHandler = onTextEdited;
    }

    public void setOnDeleteHandler(Consumer<TodoListItem> onDelete) {
        this.onDeleteClicked = onDelete;
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
        private final CheckBox checkBox;
        private TodoListItem todoItem;
        private final TextView deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.todo_item_text);
            this.checkBox = itemView.findViewById(R.id.checkBox);
            this.deleteButton = itemView.findViewById(R.id.textView);

            this.checkBox.setOnClickListener(view -> {
                if(onCheckBoxClicked == null) return;
                onCheckBoxClicked.accept(todoItem);
            });

            this.deleteButton.setOnClickListener(view -> {
                if(onDeleteClicked == null) return;
                onDeleteClicked.accept(todoItem);
            });

            this.textView.setOnFocusChangeListener((view, hasFocus) -> {
                if(!hasFocus) {
                    onTextEditedHandler.accept(todoItem, textView.getText().toString());
                }
            });
        }

        public TodoListItem getTodoItem() {
            return todoItem;
        }

        public void setTodoItem(TodoListItem todoItem) {
            this.todoItem = todoItem;
            this.textView.setText(todoItem.text);
            this.checkBox.setChecked(todoItem.completed);
        }
    }
}
