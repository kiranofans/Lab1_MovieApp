package com.example.user.lab1_movie;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.*;
import java.util.*;
import android.app.Dialog;
public class MainActivity extends AppCompatActivity {
    private static ImageView imgs;
    private static Spinner spin;
    private static EditText tixEt;
    private static int pos; private double retail=8.50;
    private static int numOftix; private static double subtotal,total;
    private static Button btn;  private static RadioGroup radioGroup;
    private static int choosenId;   private static String valueTag="VALUE";
    private static AlertDialog.Builder rbDialog;
    private static double hd=3.50; private static double sd=0.50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tixEt=(EditText)findViewById(R.id.tixEditTxt);
        spin=(Spinner)findViewById(R.id.spinner);
        imgs=(ImageView)findViewById(R.id.imgs);
        btn=(Button)findViewById(R.id.priceBtn);


        btn();
        selectedMovie();

    }
    private void btn(){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((!(selectedMovie()!=0)) && isEmpty()==false) {
                    //input validation
                    Toast t=Toast.makeText(MainActivity.this,
                            "Oops! You didn't do anything.",Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.CENTER_VERTICAL|
                            Gravity.CENTER,0,360);  t.show();
                }else{
                    Intent intent=new Intent(v.getContext(),display.class);

                    int imgs=0;//variable to hold R.drawable.imgs;
                    int position = 0;
                    if(selectedMovie()==1){
                        imgs=R.drawable.showtimes_peter_rabit;
                        position = 0;
                    } else if(selectedMovie()==2){
                        imgs=R.drawable.showtimes_life_of_party;
                        position = 1;
                    }else if(selectedMovie()==3){
                        imgs=R.drawable.showtimes_deadpool2;
                        position = 2;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putDouble("CHOICE_PRICE",calTotalPrice());
                    bundle.putInt("USERCHOICE_IMG",imgs);
                    bundle.putInt("VALUE", position);

                    startActivity(intent.putExtras(bundle));

                }
            }
        });
    }
    private int selectedMovie(){
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView,View view, int i, long l) {
                pos=i;//Assign value of i to pos
                switch(pos){
                    case 1:
                        imgs.setImageResource(R.drawable.peter_rabit);
                        break;
                    case 2:
                        imgs.setImageResource(R.drawable.life_of_the_party);
                        break;
                    case 3:
                        imgs.setImageResource(R.drawable.deadpool);
                        break;
                    case 0:
                        isEnabled(0);
                        break;
                }
                if(pos!=0){
                    radioGroupAlertDialog();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        return pos;
    }
    public boolean isEnabled(int position){
        if(position==0){
            return false;
        }
        return true;
    }
    public int radioBtnId(int which){
        if (which == 0 ) {
            choosenId = which+1;

        } else if (which == 1) {
            choosenId = which+1;
        }
        return choosenId;
    }
    private boolean isEmpty(){
        String editTxt=tixEt.getText().toString();
        if(editTxt.equals("")){
            return false;
        }else{
            numOftix=Integer.parseInt(editTxt);
            return true;
        }
    }
    private void radioGroupAlertDialog() {
        /** Customizing title,radioGroup and buttons
         using AlertDialog.Builder.
         Add radio buttons using array adapter, and use the select_dialog_singleChoice provided by
         android studio for layout.
         Alternatively, you can SIMPLY call setSingleChoiceItems of alertDialog.Buider with a
         CharSequence array for adding radio buttons to the alertDialog.
         In addition, if you want you can add your customized radioGroup and buttons
         with Inflater and View classes */
        final CharSequence[] str = {"HD", "SD"};

        rbDialog = new AlertDialog.Builder(this);
        rbDialog.setTitle("Please choose a quality");
        rbDialog.setSingleChoiceItems(str, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                radioBtnId(which);
            }
        });
        rbDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                subtotal = 0;
                Toast toast = Toast.makeText(MainActivity.this,
                        "You've canceled the dialog,\n"
                                + "so no transaction's been proceeded.",
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL |
                        Gravity.CENTER, 0, 360);

                toast.show();   dialog.cancel();
            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (radioBtnId(which)>0) {
                    if(radioBtnId(which)==1){
                        subtotal=retail+hd;
                        Toast toast = Toast.makeText(MainActivity.this,
                                "Your've selected "+str[(choosenId-1)]+" $"+hd
                                        +", which will be added to the movie's price, $"
                                + retail+" Subtotal: "+subtotal, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL |
                                Gravity.CENTER, 0, 360);
                        toast.show();
                    }else if(radioBtnId(which)==2){
                        subtotal=retail+sd;
                        Toast toast = Toast.makeText(MainActivity.this,
                                "Your've selected "+ str[choosenId-1]+" $"+sd+
                                ", which will be added to the movie's price, $"
                                        +retail+" Subtotal: "+subtotal,
                                Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL |
                                Gravity.CENTER, 0, 360);
                        toast.show();

                    }
                    dialog.dismiss();
                } else {
                    subtotal = 0;
                    Toast toast = Toast.makeText(MainActivity.this,
                            "Oops! You didn't choose anything.",
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER,
                            0, 360);
                    toast.show();    rbDialog.show();
                }
             }
        });
        rbDialog.show();
    }
    private int radioGroupDialog(){
        /*For freely customizing, and positioning buttons, radioGroup,
        and title using Dialog class; add radio buttons using list<String> array*/
        //Custom dialog
        int i;
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.radiobtns);
        List<String> strList=new ArrayList<>();
        for(i=0;i<2;i++){
            if((i+1)==1){
                strList.add("HD"+"   ");
                subtotal=retail+4.50;
            }else if((i+1)==2){
                strList.add("SD");
                subtotal=retail+4.50;
            }
        }
        radioGroup=(RadioGroup)dialog.findViewById(R.id.radioGroup);
        for(i=0;i<strList.size();i++){
            // dynamically creating RadioButton and adding to RadioGroup.
            RadioButton rBtn=new RadioButton(this);
            rBtn.setText(strList.get(i));
            radioGroup.addView(rBtn);
        }
        dialog.show();
        return i;
    }
    private Double calTotalPrice(){
        String txt=tixEt.getText().toString();
        if(!TextUtils.isEmpty(txt)&&TextUtils.isDigitsOnly(txt)){
            numOftix=Integer.parseInt(txt);
        }
        total=subtotal*numOftix;
        Log.d(valueTag,"Total "+numOftix+"");
        return total;
    }
}
