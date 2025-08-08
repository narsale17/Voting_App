package com.example.voting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voting.data.Poll;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PollAdapter extends RecyclerView.Adapter<PollAdapter.PollViewHolder> {
    private List<Poll> pollList;
    private OnPollClickListener listener;

    public interface OnPollClickListener {
        void onPollClick(Poll poll);
    }

    public PollAdapter(List<Poll> pollList, OnPollClickListener listener) {
        this.pollList = pollList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PollViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_poll, parent, false);
        return new PollViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PollViewHolder holder, int position) {
        Poll poll = pollList.get(position);
        holder.bind(poll);
    }

    @Override
    public int getItemCount() {
        return pollList.size();
    }

    class PollViewHolder extends RecyclerView.ViewHolder {
        private TextView tvQuestion;
        private TextView tvOptions;
        private TextView tvDate;

        public PollViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            tvOptions = itemView.findViewById(R.id.tvOptions);
            tvDate = itemView.findViewById(R.id.tvDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onPollClick(pollList.get(position));
                    }
                }
            });
        }

        public void bind(Poll poll) {
            tvQuestion.setText(poll.getQuestion());
            
            // Show options
            StringBuilder options = new StringBuilder();
            if (!poll.getOption1().isEmpty()) options.append("• ").append(poll.getOption1()).append("\n");
            if (!poll.getOption2().isEmpty()) options.append("• ").append(poll.getOption2()).append("\n");
            if (!poll.getOption3().isEmpty()) options.append("• ").append(poll.getOption3()).append("\n");
            if (!poll.getOption4().isEmpty()) options.append("• ").append(poll.getOption4());
            
            tvOptions.setText(options.toString());

            // Format date
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            String date = sdf.format(new Date(poll.getCreatedAt()));
            tvDate.setText(date);
        }
    }
} 