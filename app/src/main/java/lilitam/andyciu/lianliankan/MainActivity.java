package lilitam.andyciu.lianliankan;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends AppCompatActivity implements ImageView.OnClickListener{

    int[] num1=new int[16];
    int[] num2=new int[16];
    int numyet,numnotyet;
    final int numFinalStar=8;
    int tempivinum=0;
    int numtime=0;

    TextView txt_time;
    TextView txt_finish;
    TextView txt_Xfinish;
    Button btn_restart;
    private Handler mIncHandler = new Handler();
    private boolean mCounterEnable = true;
    private Runnable mIncRunner = new Runnable() {
        @Override
        public void run() {
            //累加計數值
            txt_time.setText(String.format("%d",++numtime));
            //若計時器仍啟動中，設定下次執行（延遲）時間
            if(mCounterEnable) {
                mIncHandler.postDelayed(mIncRunner, 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_restart = (Button)findViewById(R.id.btn_restart);
        txt_time = (TextView)findViewById(R.id.txt_time);
        txt_finish = (TextView)findViewById(R.id.txt_finish);
        txt_Xfinish = (TextView)findViewById(R.id.txt_Xfinish);
        txt_finish.setText(String.format("%d",numyet));
        txt_Xfinish.setText(String.format("%d",numnotyet));
        btn_restart.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                stratFuc();
            }
        });
        stratFuc();

        //mIncRunner.run();

    }

    @Override
    public void onClick(View v) {
        final ImageView ivi= (ImageView) findViewById(v.getId());
        String ivistr=ivi.toString();
        ivistr=ivistr.substring(ivistr.length()-3,ivistr.length()-1);
        //Toast.makeText(this,ivistr,Toast.LENGTH_LONG).show();
        //Toast.makeText(this,ivistr.substring(ivistr.length()-3,ivistr.length()-1),Toast.LENGTH_LONG).show();
        int ivinum=Integer.parseInt(ivistr);
        //========================================
        switch (num1[ivinum-1]){
            case 1:
                ivi.setImageResource(android.R.drawable.presence_audio_online);
                break;
            case 2:
                ivi.setImageResource(android.R.drawable.presence_audio_away);
                break;
            case 3:
                ivi.setImageResource(android.R.drawable.presence_audio_busy);
                break;
            case 4:
                ivi.setImageResource(android.R.drawable.presence_video_online);
                break;
            case 5:
                ivi.setImageResource(android.R.drawable.presence_video_away);
                break;
            case 6:
                ivi.setImageResource(android.R.drawable.presence_video_busy);
                break;
            case 7:
                ivi.setImageResource(android.R.drawable.presence_away);
                break;
            case 8:
                ivi.setImageResource(android.R.drawable.presence_busy);
                break;
        }
        //========================================
        if(num2[ivinum-1]==0 && tempivinum!=ivinum){
            if(tempivinum==0){
                tempivinum=ivinum;
            }
            else if(tempivinum>0){
                if(num1[tempivinum-1]==num1[ivinum-1]){
                    num2[tempivinum-1]=1;
                    num2[ivinum-1]=1;
                    tempivinum=0;
                    //===================
                    txt_finish.setText(String.format("%d",++numyet));
                    txt_Xfinish.setText(String.format("%d",--numnotyet));
                    //===================
                    int numyn=0;
                    for(int i=0;i<16;i++){
                        if(num2[i]!=1){
                            numyn=1;
                            break;
                        }
                    }
                    if(numyn==0){
                        String strend="congratulations!";
                        Toast.makeText(this,strend,Toast.LENGTH_LONG).show();
                        mCounterEnable=false;
                    }
                }
                else{
                    new CountDownTimer(300, 300) {
                        @Override
                        public void onTick(long millisUntilFinished){}
                        @Override
                        public void onFinish() {
                            String str1="imageView"+String.format("%02d",tempivinum);
                            int resID1=getResources().getIdentifier(str1,"id",getPackageName());
                            ImageView ivi2= (ImageView) findViewById(resID1);
                            ivi2.setImageResource(android.R.drawable.btn_star_big_on);
                            ivi.setImageResource(android.R.drawable.btn_star_big_on);
                            tempivinum=0;
                        }
                    }.start();

                }
            }
        }
    }
    public void stratFuc(){
        numyet=0;
        numnotyet=numFinalStar;
        numtime=0;

        mIncHandler.removeCallbacks(mIncRunner);
        mCounterEnable=true;
        mIncRunner.run();

        txt_finish.setText(String.format("%d",numyet));
        txt_Xfinish.setText(String.format("%d",numnotyet));
        for(int i=1;i<=16;i++){
            num1[i-1]=0;
            num2[i-1]=0;
        }
        for(int i=1;i<=16;i++){
            String str1="imageView"+String.format("%02d",i);
            int resID=getResources().getIdentifier(str1,"id",getPackageName());
            ImageView iva= (ImageView) findViewById(resID);
            iva.setImageResource(android.R.drawable.btn_star_big_on);
            iva.setOnClickListener(this);
        }

        Random rn=new Random();
        for(int i=1;i<=8;i++){
            for(int j=1;j<=2;j++){
                while(true){
                    int n1=rn.nextInt(16);
                    if(num1[n1]==0){
                        num1[n1]=i;
                        break;
                    }
                }
            }
        }
    }
}