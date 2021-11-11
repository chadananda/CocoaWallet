/*
 *  CocoaWallet App (c) 2021 Chad Jones
 *  based on the XMRig Monero Miner https://github.com/xmrig/xmrig
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program. If not, see <http://www.gnu.org/licenses/>.
 * /
 */

package com.chadananda.cocoawallet;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.LongFunction;
import java.util.regex.Pattern;

public class MainActivity extends Activity implements PermissionUtil.PermissionsCallBack{
    private final static String[] SUPPORTED_ARCHITECTURES = {"arm64-v8a", "armeabi-v7a"};
    private ScheduledExecutorService svc;
    private ImageView imageview,screenshotimage,controller_scanner,paywallet_scanner;
    private TextView tvLog,cpu,back;
    private EditText edPool,edUser,edThreads, edMaxCpu;
    private TextView tvSpeed,tvAccepted,controller,contribution_percent,threads_percent,device_name,device_name1,paywallet1,controllerscan;
    private CheckBox cbUseWorkerId;
    private Switch no_sleep,plugged_only;
    private Button screenshot,done;
    private Uri fileUri;
    private LinearLayout wholedata;
    private boolean validArchitecture = true;
    private MiningService.MiningServiceBinder binder;
    public static final int PERMISSION_WRITE = 0;
    private IntentIntegrator scanner;
    private SeekBar contribution_seek,threads_seek;
    GraphView graphView;
    private String speed;
    String no_sleep1,plugged_only1,wallet123,controller123;
    private String onoff,wallet,control;
    Boolean usbCharge;
    private int threadspercent;
    private int threads;
    private Button openurl;
    double i=Double.parseDouble(String.valueOf(11.11));
    int j;
    @SuppressLint("ResourceAsColor")
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // wire views
        tvLog = findViewById(R.id.output);
        tvSpeed = findViewById(R.id.speed);
        tvAccepted = findViewById(R.id.accepted);
        edPool = findViewById(R.id.pool);
        edUser = findViewById(R.id.username);
        edThreads = findViewById(R.id.threads);
        edMaxCpu = findViewById(R.id.maxcpu);
        cpu = findViewById(R.id.cpu);
       //imageview = findViewById(R.id.imageView);
        cbUseWorkerId = findViewById(R.id.use_worker_id);
        screenshotimage=findViewById(R.id.screenshotImage);
        controller_scanner=findViewById(R.id.controller_scanner);
        wholedata=findViewById(R.id.wholedata);
        back = findViewById(R.id.back);
        openurl = findViewById(R.id.openurl);
        ImageView shareButton = (ImageView) findViewById(R.id.share);
        device_name1 = (TextView) findViewById(R.id.device_name1);
        enableButtons(true);

        //cpu.setText("Snap Dragon  "+edMaxCpu.getText().toString());

        ///show device name
        String deviceName = android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL;
        device_name1.setText(deviceName);

        ///open url
        openurl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com")));
            }
        });

        //Log.e("speed","speed"+speed);





        TextView popup = findViewById(R.id.settings);
        popup.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View customView  = inflater.inflate(R.layout.popup, null);
                controller_scanner = (ImageView) customView.findViewById(R.id.controller_scanner);
                paywallet_scanner = (ImageView) customView.findViewById(R.id.paywallet_scanner);
                done = (Button) customView.findViewById(R.id.done);
                contribution_seek = (SeekBar) customView.findViewById(R.id.contribution_seek);
                threads_seek = (SeekBar) customView.findViewById(R.id.threads_seek);
                contribution_percent = (TextView) customView.findViewById(R.id.contribution_percent);
                threads_percent = (TextView) customView.findViewById(R.id.threads_percent);
                no_sleep = (Switch) customView.findViewById(R.id.nosleepSwitch);
                plugged_only = (Switch) customView.findViewById(R.id.pluggedonlySwitch);
                device_name = (TextView) customView.findViewById(R.id.device_name);
                device_name1 = (TextView) customView.findViewById(R.id.device_name1);
                paywallet1 = (TextView) customView.findViewById(R.id.paywallet1);
                controllerscan=(TextView) customView.findViewById(R.id.controllerscan);

                // create the popup window
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(customView, width, height, focusable);
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                onoff = sharedPreferences.getString("onff","");
                wallet = sharedPreferences.getString("wallet123","");
                threads = sharedPreferences.getInt("threads",0);
                control= sharedPreferences.getString("controller123","");
                if (sharedPreferences.equals("")){
                   Toast.makeText(getApplicationContext(),"DATA is Empty",Toast.LENGTH_SHORT).show();
                }else {
                    threads_percent.setText(" "+threads+"%");
                    threads_seek.setProgress(threads);
                    paywallet1.setText(wallet+"....");
                    controllerscan.setText(control+"....");
                }

                ///check no sleep
                if (onoff.equals("ON")){
                    no_sleep.setChecked(true);
                }else {
                    no_sleep.setChecked(false);
                }

                ///show device name
                String deviceName = android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL;
                device_name.setText(deviceName);

                ///check for charging
                IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                Intent batteryStatus = registerReceiver(null, ifilter);
                int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                // Find the charge Source
                boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING
                        || status == BatteryManager.BATTERY_STATUS_FULL;
                int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
                boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
                if (usbCharge) {
                    Toast.makeText(getApplicationContext(),
                            "Mobile is charging on USB", Toast.LENGTH_LONG).show();
                    plugged_only.setChecked(true);

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Mobile charging off", Toast.LENGTH_LONG).show();
                }

                //contribution seek bar
                contribution_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    int progressChangedValue = 0;

                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progressChangedValue = progress;
                        //Toast.makeText(getApplicationContext(),"Progress"+progress,Toast.LENGTH_SHORT).show();
                        contribution_percent.setText(" " +progress+"%");
                    }
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // TODO Auto-generated method stub
                    }

                    public void onStopTrackingTouch(SeekBar seekBar) {
                        //Toast.makeText(MainActivity.this, "Seek bar progress is :" + progressChangedValue,Toast.LENGTH_SHORT).show();
                    }
                });

                //threads seek bar
                threads_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
                    int progressChangedValue = 0;
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progressChangedValue = progress;
                        //Toast.makeText(getApplicationContext(),"Progress"+progress,Toast.LENGTH_SHORT).show();
                        threads_percent.setText(" "+progress+"%");
                        threadspercent=progress;

                    }

                    public void onStartTrackingTouch(SeekBar seekBar) {
                        //TODO Auto-generated method stub
                    }

                    public void onStopTrackingTouch(SeekBar seekBar) {
                        //Toast.makeText(MainActivity.this, "Seek bar progress is :" + progressChangedValue,Toast.LENGTH_SHORT).show();
                    }
                });


                // dismiss the popup window when touched
                controller_scanner.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        scanImage();
                        return true;
                    }
                });
                paywallet_scanner.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        scanImage();
                        return true;
                    }
                });

                ///check for done
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();

                        onoff = sharedPreferences.getString("onff","");
                        wallet = sharedPreferences.getString("wallet123","");
                        threads = sharedPreferences.getInt("threads",0);
                        control= sharedPreferences.getString("controller123","");

                        if (wallet.equals("")||control.equals("")) {
                            myEdit.putString("onff", no_sleep1);
                            myEdit.putString("wallet123", wallet123);
                            myEdit.putString("controller123", controller123);
                            myEdit.putInt("threads",threadspercent);
                            myEdit.apply();
                        }

                        threads_percent.setText(" "+threads+"%");
                        threads_seek.setProgress(threads);
                        paywallet1.setText(wallet+"....");
                        controllerscan.setText(control+"....");

                        if (no_sleep.isChecked()){
                            no_sleep1 = no_sleep.getTextOn().toString();
                            Toast.makeText(getApplication(),"On",Toast.LENGTH_SHORT).show();
                            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        } else {
                            Toast.makeText(getApplication(),"Sleep Mode Off",Toast.LENGTH_SHORT).show();
                            no_sleep1 = no_sleep.getTextOff().toString();
                        }
                    }
                });
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakeScreenShot();
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (PermissionUtil.checkAndRequestPermissions(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET
                        )) {
                    Log.i("aa", "Permissions are granted. Good to go!");
                }
            }



        if (!Arrays.asList(SUPPORTED_ARCHITECTURES).contains(Build.CPU_ABI.toLowerCase())) {
            Toast.makeText(this, "Sorry, this app currently only supports 64 bit architectures, but yours is " + Build.CPU_ABI, Toast.LENGTH_LONG).show();
            // this flag will keep the start button disabled
            validArchitecture = false;
        }


        // run the service
        Intent intent = new Intent(this, MiningService.class);
        bindService(intent, serverConnection, BIND_AUTO_CREATE);
        startService(intent);
    }

    private void scanImage() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setPrompt("Scan a barcode or QR Code");
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Log.e("lll","lll"+intentResult.getContents());
                if (intentResult.getContents().length() > 30) {
                    String substring = intentResult.getContents().substring(0,5);
                    paywallet1.setText(substring+"....");
                    wallet123=substring.toString();
                }else {
                    String substring = intentResult.getContents().substring(0,5);
                    controllerscan.setText(substring+"....");
                    controller123=substring.toString();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.onRequestPermissionsResult(this, requestCode, permissions, grantResults, this);
        if (requestCode==PERMISSION_WRITE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //do somethings
        }
    }

    private void startMining(View view) {
        Log.e("start","start"+threadspercent);
        requestPermission();
        if (edUser.getText().toString().isEmpty()){
            Toast.makeText(this,"Enter UserName",Toast.LENGTH_SHORT).show();
        }
        else if (edPool.getText().toString().isEmpty())
        {
            Toast.makeText(this,"Enter Pool Address",Toast.LENGTH_SHORT).show();
        }

        else if (edMaxCpu.getText().toString().isEmpty())
        {
            Toast.makeText(this,"Enter MaxCpu",Toast.LENGTH_SHORT).show();
        }
        else if (cbUseWorkerId.getText().toString().isEmpty())
        {
            Toast.makeText(this,"Enter Percent",Toast.LENGTH_SHORT).show();
        }
        else {
            Log.e("start","start: "+binder);
            if (binder == null) return;
            MiningService.MiningConfig cfg = binder.getService().newConfig(
                    edUser.getText().toString(),
                   //" 8AaNnN8nQUMh3XQfyt4kEt8TR7RYnowhjVynzShWwVLiR6dWdSp42YeFvouLZoui7S46xSgDxapbeS7Tdqyz7em5Chqd4HA",

                    edPool.getText().toString(),
                    //"gulf.moneroocean.stream:10001",
                    Integer.parseInt(
                            String.valueOf(threadspercent)
                            //"3"
                    ), Integer.parseInt(
                            edMaxCpu.getText().toString()
                            //"80"
                    ), cbUseWorkerId.isChecked());
            // Log.e("start","cfg: "+cfg.toString());
            binder.getService().startMining(cfg);

        }
    }


    public void TakeScreenShot() {
        try {
            File mydir = new File(Environment.getExternalStorageDirectory() + "/11zon");
            if (!mydir.exists()) {
                mydir.mkdirs();
            }

            View view = getWindow().getDecorView().getRootView();
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);
            fileUri = Uri.parse(mydir.getAbsolutePath() + File.separator + "IMG_" + System.currentTimeMillis() + ".jpg");
            FileOutputStream outputStream = new FileOutputStream(String.valueOf(fileUri));
            //screenshotimage.setVisibility(View.VISIBLE);
            wholedata.setVisibility(View.GONE);
            screenshotimage.setVisibility(View.VISIBLE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            //imageview.setImageURI(fileUri);
            screenshotimage.setVisibility(View.VISIBLE);
            screenshotimage.setImageURI(fileUri);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    screenshotimage.setVisibility(View.GONE);
                    wholedata.setVisibility(View.VISIBLE);
                }
            }, 1500);
        } catch(IOException e) {
            e.printStackTrace();
        }

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.camera_sound);
                mp.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mp.stop();
            }
        }, 1000);


        //share code
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("image/*");
                String shareBody = "Cocoawallet Screenshot";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Screenshot");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                sharingIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                Log.e("uri","uri"+fileUri);
            }
        }, 3000);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                if(checkSelfPermission(Manifest.permission.INTERNET)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (shouldShowRequestPermissionRationale(
                            Manifest.permission.INTERNET)) {
                    }
                }
            }
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.INTERNET},1);
            return;
        }
    }


    private void stopMining(View view) {
        binder.getService().stopMining();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // the executor which will load and display the service status regularly
        svc = Executors.newSingleThreadScheduledExecutor();
        svc.scheduleWithFixedDelay(this::updateLog, 1, 1, TimeUnit.SECONDS);
    }

    @Override
    protected void onPause() {
        svc.shutdown();
        super.onPause();
    }

    private ServiceConnection serverConnection = new ServiceConnection() {
        @SuppressLint("DefaultLocale")
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder ) {
            binder = (MiningService.MiningServiceBinder) iBinder;
            if (validArchitecture) {
                enableButtons(true);
                Log.e("binder","binder"+binder);
                //findViewById(R.id.start).setOnClickListener(MainActivity.this::startMining);

                ///start Button
                if (binder == null) return;
                MiningService.MiningConfig cfg = binder.getService().newConfig(
                        edUser.getText().toString(),
                        //" 8AaNnN8nQUMh3XQfyt4kEt8TR7RYnowhjVynzShWwVLiR6dWdSp42YeFvouLZoui7S46xSgDxapbeS7Tdqyz7em5Chqd4HA",
                        edPool.getText().toString(),
                        //"gulf.moneroocean.stream:10001",
                        Integer.parseInt(
                                String.valueOf(threadspercent)
                                //"3"
                        ), Integer.parseInt(
                                edMaxCpu.getText().toString()
                                //"80"
                        ), cbUseWorkerId.isChecked());
                //Log.e("start","cfg: "+cfg.toString());
                binder.getService().startMining(cfg);



                findViewById(R.id.stop).setOnClickListener(MainActivity.this::stopMining);
                int cores = binder.getService().getAvailableCores();
                //write suggested cores usage into editText
                int suggested = cores / 2;
                if (suggested == 0) suggested = 1;
                //edThreads.getText().clear();
                //edThreads.getText().append(Integer.toString(suggested));
                ((TextView) findViewById(R.id.cpus)).setText(String.format("(%d %s)", cores, getString(R.string.cpus)));
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            binder = null;
            enableButtons(false);
        }
    };

    private void enableButtons(boolean enabled) {
        findViewById(R.id.start).setEnabled(enabled);
        findViewById(R.id.stop).setEnabled(enabled);
    }


    @SuppressLint("SetTextI18n")
    private void updateLog() {
        runOnUiThread(()->{
            if (binder != null) {
                tvLog.setText(binder.getService().getOutput());
                tvAccepted.setText(Integer.toString(binder.getService().getAccepted()));
                tvSpeed.setText(binder.getService().getSpeed());
                speed=binder.getService().getSpeed();
                Log.e("aa","aaa"+binder.getService().getSpeed());

                if(speed.matches("[0-9]+[\\.]?[0-9]*")){
                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    //double myInt = Double.parseDouble(speed);

                    myEdit.putString("speed", String.valueOf(Double.parseDouble(speed)));
                    String speed321 = sharedPreferences.getString("speed","");
                    double sp=Double.parseDouble(speed321);
                    Log.e("speed321","speed321"+speed321);

                    ///graph line
                    graphView = findViewById(R.id.graph);
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                            // on below line we are adding
                            // each point on our x and y axis.
                            new DataPoint(0, 0),
                            new DataPoint(0, sp)
                    });

                    graphView.setTitle("Time Graph View");
                    graphView.setTitleColor(R.color.colorPrimary);
                    graphView.setTitleTextSize(18);
                    series.setColor(R.color.colorPrimary);
                    series.setDrawBackground(true);
                    series.setDrawDataPoints(true);
                    graphView.addSeries(series);
                }
            }
        });
    }
    @Override
    public void permissionsGranted() {
        Toast.makeText(this, "Permissions Granted!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void permissionsDenied() {
        Toast.makeText(this, "Permissions Denied!", Toast.LENGTH_SHORT).show();
    }
}
