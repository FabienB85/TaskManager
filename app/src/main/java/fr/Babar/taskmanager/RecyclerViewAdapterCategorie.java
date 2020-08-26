package fr.Babar.taskmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.Babar.taskmanager.model.Categorie;

public class RecyclerViewAdapterCategorie extends RecyclerView.Adapter<RecyclerViewAdapterCategorie.MyViewHolder> {
    private List<Categorie> categorieListList;
    RecyclerViewAdapterCategorie(List<Categorie> mItemList){
        this.categorieListList = mItemList;
    }
    @NonNull
    @Override
    public RecyclerViewAdapterCategorie.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_categorie,parent,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(RecyclerViewAdapterCategorie.MyViewHolder holder, final int position) {
        final Categorie categorie = categorieListList.get(position);
        holder.setCategorie(categorie);
    }
    @Override
    public int getItemCount() {
        return categorieListList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description;
        private CheckBox checkBoxCategorie;
        private Categorie mCategorie;
        private LinearLayout itemLayout;

        public MyViewHolder(final View arg_itemView) {
            super(arg_itemView);
            name = arg_itemView.findViewById(R.id.nomCategorie);
            description = arg_itemView.findViewById(R.id.descriptionCategorie);
            itemLayout = arg_itemView.findViewById(R.id.itemview_layoutCategorie);
            checkBoxCategorie = arg_itemView.findViewById(R.id.checkBoxCategorie);


            checkBoxCategorie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        // check !
                        mCategorie.setSelectionne(true,checkBoxCategorie);
                    }else{
                        mCategorie.setSelectionne(false,checkBoxCategorie);
                    }
                }
            });
        }
        public void setCategorie(Categorie arg_categorie){
            mCategorie = arg_categorie;
            name.setText(mCategorie.getNom());
            description.setText(mCategorie.getDescription());
        }
    }
}