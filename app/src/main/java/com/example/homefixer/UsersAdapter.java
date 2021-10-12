package com.example.homefixer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {

    private Context context;
    private List<UserModel> userModelList;




    public UsersAdapter(Context context, List<UserModel> userModelList) {
        this.context = context;
        this.userModelList = userModelList;
    }





    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_item_layout,parent,false);
        return new MyViewHolder(view);
    }






    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        UserModel userModel = userModelList.get(position);

        holder.cardName.setText(userModel.getName());
        holder.cardProfession.setText("Profession: "+userModel.getProfession());
        holder.cardExperience.setText("Experience: "+userModel.getExperience());
        holder.cardLocation.setText("Location: "+userModel.getLocation());
        holder.cardEmail.setText("Email:"+userModel.getEmail());
        holder.cardPhone.setText("Phone: "+userModel.getPhone());
        holder.cardDescription.setText(userModel.getDescription());


        Picasso.with(context)
                .load(userModel.getImageUrl())
                .placeholder(R.drawable.user)
                .fit()
                .centerCrop()
                .into(holder.cardProfilePicture);

    }









    @Override
    public int getItemCount() {return userModelList.size();}







    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView cardProfilePicture;
        TextView cardName,cardProfession,cardExperience,cardLocation,cardEmail,cardDescription,cardPhone;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardProfilePicture = itemView.findViewById(R.id.cardprofilePictureId);

            cardName = itemView.findViewById(R.id.cardNameId);
            cardProfession = itemView.findViewById(R.id.cardProfessionId);
            cardExperience = itemView.findViewById(R.id.cardExperienceId);
            cardLocation = itemView.findViewById(R.id.cardLocationId);
            cardEmail = itemView.findViewById(R.id.cardEmailId);
            cardPhone= itemView.findViewById(R.id.cardPhoneId);
            cardDescription = itemView.findViewById(R.id.cardDescriptionId);


        }
    }
}
