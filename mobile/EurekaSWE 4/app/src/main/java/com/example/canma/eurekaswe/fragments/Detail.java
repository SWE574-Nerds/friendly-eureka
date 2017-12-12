package com.example.canma.eurekaswe.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.SimpleArrayMap;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.canma.eurekaswe.EurekaApplication;
import com.example.canma.eurekaswe.MainActivity;
import com.example.canma.eurekaswe.R;
import com.example.canma.eurekaswe.adapters.AltRecyclerAdapter;
import com.example.canma.eurekaswe.adapters.RecyclerPostAdapter;
import com.example.canma.eurekaswe.components.NewImageView;
import com.example.canma.eurekaswe.data.AltCellData;
import com.example.canma.eurekaswe.data.AnnotateData;
import com.example.canma.eurekaswe.data.Body2;
import com.example.canma.eurekaswe.data.CellData;
import com.example.canma.eurekaswe.data.LatLong;
import com.example.canma.eurekaswe.data.Selector;
import com.example.canma.eurekaswe.data.Selector2;
import com.example.canma.eurekaswe.data.SelectorTextLayer;
import com.example.canma.eurekaswe.data.eventBusEvents.BussedToDetail;
import com.example.canma.eurekaswe.data.eventBusEvents.BussedToHighlight;
import com.example.canma.eurekaswe.interfaces.calls.AltListApi;
import com.example.canma.eurekaswe.interfaces.calls.AnnotateTextApi;
import com.tumblr.remember.Remember;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Detail extends Fragment {

    View view = null;


    MainActivity mainActivity;

    Unbinder unbinder;

    AltRecyclerAdapter adapter;


    RecyclerPostAdapter categoryAdapter;


    SimpleArrayMap<Integer, Boolean> highlighteds;

    @Inject
    @Named("regular")
    Retrofit retrofit;
    @BindView(R.id.user_tv)
    TextView userTv;
    @BindView(R.id.date_tv)
    TextView dateTv;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.item_name)
    TextView itemName;
    @BindView(R.id.item_location)
    TextView itemLocation;
    @BindView(R.id.item_time_info)
    TextView itemTimeInfo;
    @BindView(R.id.follow_button)
    Button followButton;
    @BindView(R.id.delete_button)
    Button deleteButton;
    @BindView(R.id.item_image)
    NewImageView itemImage;
    @BindView(R.id.item_expl)
    TextView itemExpl;
    @BindView(R.id.category_recycler)
    RecyclerView categoryRecycler;

    @BindView(R.id.altrecycler)
    RecyclerView altrecycler;


    CellData cellData;

    String myId;











    /*
       for (Selector s:
                altCellData.selector
             ) {

            Spannable wordtoSpan = new SpannableString();
            wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), 2, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(WordtoSpan);




        }

        */




    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHighlightPressed(BussedToHighlight bussedToHighlight) {


        if(highlighteds.get(bussedToHighlight.altCellPos)){


    for (Selector s:
            bussedToHighlight.selectors
            ) {


        int start = s.prefix.length();

        int end = start + s.exact.length();

        Spannable wordtoSpan = new SpannableString(itemExpl.getText());


        wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLACK), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        itemExpl.setText(wordtoSpan);


    }


            highlighteds.setValueAt(highlighteds.indexOfKey(bussedToHighlight.altCellPos),false);

        }else {




        for (Selector s:
                bussedToHighlight.selectors
                ) {



            int start = s.prefix.length();

            int end = start + s.exact.length();

            Spannable wordtoSpan = new SpannableString(itemExpl.getText());


            wordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            itemExpl.setText(wordtoSpan);


        }
            highlighteds.setValueAt(highlighteds.indexOfKey(bussedToHighlight.altCellPos),true);

        }



    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        ((EurekaApplication) getActivity().getApplication()).getNetComponent().inject(this);
        setHasOptionsMenu(true);
        myId = Remember.getString("uid", "oops");
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            cellData = (CellData) bundle.getSerializable("post");
        }
highlighteds= new SimpleArrayMap<>();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        adapter = new AltRecyclerAdapter(getActivity(), retrofit);
        categoryAdapter= new RecyclerPostAdapter(mainActivity);


        fillMainCell(cellData);
        itemExpl.setCustomSelectionActionModeCallback(new StyleCallback());

       // altrecycler.setNestedScrollingEnabled(false);
        altrecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        List temp = new ArrayList<AltCellData>();

        altrecycler.setAdapter(adapter);
        listItems();


        adapter.notifyDataSetChanged();


        return view;
    }


    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();


    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    public void listItems() {


        AltListApi listApi = retrofit.create(AltListApi.class);


        String auth = Remember.getString("token", "oops");

        Call<List<AltCellData>> call = listApi.list(auth, ""+cellData.listoryId);

        call.enqueue(new Callback<List<AltCellData>>() {
            @Override
            public void onResponse(Call<List<AltCellData>> call, Response<List<AltCellData>> response) {


                adapter.addAltCellData(response.body());

                List<AltCellData> altCellDataList=response.body();

for (AltCellData altCellData:altCellDataList){

        highlighteds.put(
              altCellDataList.indexOf(altCellData)
                        ,false);





}
            }

            @Override
            public void onFailure(Call<List<AltCellData>> call, Throwable t) {
                AlertDialog alertDialog = new AlertDialog.Builder(mainActivity).create();
                alertDialog.setTitle("Uyarı");
                alertDialog.setMessage("Bir sorun oluştu birazdan tekrar deneyin!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });


    }




    public void fillMainCell(CellData cellData){





   userTv.setText(cellData.owner.name);
   dateTv.setText(cellData.createdAt==null?"10 m":cellData.createdAt);
   itemExpl.setText(cellData.description);
   itemLocation.setText("");
   itemName.setText(cellData.name);
   followButton.setTag(cellData.listoryId);
   deleteButton.setTag(cellData.listoryId);
    categoryAdapter.setmDataSet(cellData.tags);



        if(cellData.owner.userId!=Integer.parseInt(myId)){

          deleteButton.setVisibility(View.INVISIBLE);

        }else {
            deleteButton.setVisibility(View.VISIBLE);

        }



        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
categoryRecycler.setLayoutManager(mLayoutManager);

        categoryRecycler.setAdapter(categoryAdapter);

        String temp="";


        for(int x=0;x<cellData.time.count;x++){
            temp+=cellData.time.values.get(x)+" - ";
        }

        temp.substring(0,temp.length()-3);

  itemTimeInfo.setText(cellData.time.name+" "+temp);


  if(cellData.image.length()>0) {
      Glide
              .with(mainActivity)
              .load(cellData.image)
              .into(itemImage);

  }else {
      itemImage.setVisibility(View.GONE);


  }







    }



  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:

    activateTextSelectListener();



                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }


    }*/

/*
    public void  activateTextSelectListener(){


        itemExpl.setCustomSelectionActionModeCallback(new Act {

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // Remove the "select all" option
                menu.removeItem(android.R.id.selectAll);
                // Remove the "cut" option
                menu.removeItem(android.R.id.cut);
                // Remove the "copy all" option
                menu.removeItem(android.R.id.copy);
                return true;
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Called when action mode is first created. The menu supplied
                // will be used to generate action buttons for the action mode

                // Here is an example MenuItem
                menu.add(0, DEFINITION, 0, "Definition").setIcon(R.drawable.ic_action_book);
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // Called when an action mode is about to be exited and
                // destroyed
            }
*/

    /*        @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case DEFINITION:
                        int min = 0;
                        int max = mTextView.getText().length();
                        if (mTextView.isFocused()) {
                            final int selStart = mTextView.getSelectionStart();
                            final int selEnd = mTextView.getSelectionEnd();

                            min = Math.max(0, Math.min(selStart, selEnd));
                            max = Math.max(0, Math.max(selStart, selEnd));
                        }
                        // Perform your definition lookup with the selected text
                        final CharSequence selectedText = mTextView.getText().subSequence(min, max);
                        // Finish and close the ActionMode
                        mode.finish();
                        return true;
                    default:
                        break;
                }
                return false;
            }

        });









    }

*/

    class StyleCallback implements ActionMode.Callback {

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            Log.d("act", "onCreateActionMode");
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_style, menu);
            menu.removeItem(android.R.id.selectAll);
            return true;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
             CharacterStyle cs;
            int start = itemExpl.getSelectionStart();
            int end = itemExpl.getSelectionEnd();
            SpannableStringBuilder ssb = new SpannableStringBuilder(itemExpl.getText());

            switch(item.getItemId()) {



                case R.id.annotate:
                    cs = new StyleSpan(Typeface.ITALIC);
                    ssb.setSpan(cs, start, end, 1);
                    itemExpl.setText(ssb);

                    openPopupWithEdittext(ssb.toString().substring(start,end),ssb.toString().substring(0,start-1),ssb.toString().substring(end,ssb.toString().length()),""+cellData.listoryId);

                    return true;


            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }

        public void onDestroyActionMode(android.support.v7.view.ActionMode mode) {
        }
    }


  public void   openPopupWithEdittext(final String message,final String start,final String end, final String lid){

          AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
          builder.setTitle("Annotate!");
          builder.setMessage(message);

          final EditText input = new EditText(mainActivity);
          LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                  LinearLayout.LayoutParams.MATCH_PARENT,
                  LinearLayout.LayoutParams.MATCH_PARENT);
          input.setLayoutParams(lp);
          builder.setView(input);

          // Set up the buttons
          builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {

                  // do something here on OK
if(input.getText().toString().trim().length()>0) {
    sendTextAnnotation(input.getText().toString(),message,start,end,lid);
}
              }
          });
          builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                  dialog.cancel();
              }
          });
          builder.show();

  }

  public void sendTextAnnotation(String detailedText, String annotatedText, String start, String end, String lid){

    AnnotateTextApi annotateTextApi = retrofit.create(AnnotateTextApi.class);


      Body2 body= new Body2();
      body.link="";
      body.message=detailedText;


      Selector2 selector= new Selector2();


      SelectorTextLayer text = new SelectorTextLayer();

      text.selection=annotatedText;

      text.startsWith=start;
      text.endsWith=end;
      selector.text=text;


      AnnotateData annotateData= new AnnotateData(lid,body,selector);



      String auth= Remember.getString("token","oops");




      Call<ResponseBody> call= annotateTextApi.annotate("application/json", auth,lid,annotateData);
      call.enqueue(new Callback<ResponseBody>() {
          @Override
          public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


              if(response.isSuccessful()){


                  Toast.makeText(mainActivity, "Annotation başarılı", Toast.LENGTH_SHORT).show();
              }

          }

          @Override
          public void onFailure(Call<ResponseBody> call, Throwable t) {

          }
      });

  }




}
