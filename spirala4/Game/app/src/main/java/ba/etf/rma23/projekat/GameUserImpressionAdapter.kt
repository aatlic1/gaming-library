package ba.etf.rma23.projekat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GameUserImpressionAdapter (
    private var ui: List<UserImpression>
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):RecyclerView.ViewHolder{
        if(viewType == 0) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user_rating, parent, false)
            return RatingViewHolder(view);
        }
        else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user_review, parent, false)
            return ReviewViewHolder(view);
        }
    }
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if(holder is RatingViewHolder) {
            holder.userNameRating.text = ui[position].userName
            holder.rating.rating = (ui[position] as UserRating).rating.toFloat()
        }
        if(holder is ReviewViewHolder){
                holder.userNameReview.text = ui[position].userName
                holder.review.text = (ui[position] as UserReview).review
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(ui[position] is UserRating)
            return 0
        else return 1
    }
    override fun getItemCount(): Int = ui.size
    fun updateImpressions(impressions: List<UserImpression>) {
        this.ui = impressions
        notifyDataSetChanged()
    }

    inner class RatingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val userNameRating: TextView = itemView.findViewById(R.id.username_textview)
            val rating : RatingBar = itemView.findViewById(R.id.rating_bar)
        }

    inner class ReviewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val userNameReview: TextView = itemView.findViewById(R.id.username_textview)
        val review: TextView = itemView.findViewById(R.id.review_textview)
    }

}

