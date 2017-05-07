package speaktechnical.com.githubtask.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import speaktechnical.com.githubtask.R;

/**
 * Created by avi on 21/04/2017.
 */

public class GitCommitsRecyclerAdapter extends RecyclerView.Adapter<GitCommitsRecyclerAdapter.ItemViewHolder> {

    Context context;
    ArrayList<GitHubFeed> gitHubFeedArrayList;
    SimpleDateFormat simpleDateFormat;

    public GitCommitsRecyclerAdapter(Context context, ArrayList<GitHubFeed> gitHubFeedArrayList) {
        this.gitHubFeedArrayList = gitHubFeedArrayList;
        this.context = context;
        simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'z'");
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.
                from(parent.getContext()).inflate(R.layout.layout_git_list_item, parent, false));

    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        holder.tvForName.setText(gitHubFeedArrayList.get(position).getAuthor_name());
        holder.tvForCommitMsg.setText(gitHubFeedArrayList.get(position).getCommit_message());
        holder.tvForCommitSha.setText(gitHubFeedArrayList.get(position).getSha_code());
//        try {
//            holder.tvForTimeStamp.setText(new SimpleDateFormat("MMM dd, yyyy").format(simpleDateFormat.parse(gitHubFeedArrayList.get(position).getDate()).getTime()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        Glide.with(context).load(gitHubFeedArrayList.get(position).getImage_url()).asBitmap().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return gitHubFeedArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvForName, tvForCommitMsg, tvForTimeStamp,tvForCommitSha;
        ImageView imageView;
        CardView cardView;

        public ItemViewHolder(View itemView) {
            super(itemView);//initializing Viewholder's view to layout item's views
            tvForName = (TextView) itemView.findViewById(R.id.tvForAuthorNameAtItem);
            tvForCommitMsg = (TextView) itemView.findViewById(R.id.tvForMsgAtItem);
            tvForTimeStamp = (TextView) itemView.findViewById(R.id.tvForTimeStampAtItem);
            tvForCommitSha=(TextView)itemView.findViewById(R.id.tvForShaMsgAtItem);
            cardView = (CardView) itemView.findViewById(R.id.cardviewATStudentItem);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewAtItem);
        }
    }


}
