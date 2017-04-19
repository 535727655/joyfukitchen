package edu.ayd.joyfukitchen.Fragment;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import edu.ayd.joyfukitchen.activity.R;
import edu.ayd.joyfukitchen.service.BluetoothService;
import edu.ayd.joyfukitchen.util.EmptyUtils;
import edu.ayd.joyfukitchen.view.FontSizeAutoAdjustTextView;

/**
 * Created by Administrator on 2017/4/12.
 */

public class WeightFragment extends Fragment {
    private View view;
    private Button btn_next;
    private Context context;
    private FontSizeAutoAdjustTextView ftv_weight_value;
    private FontSizeAutoAdjustTextView ftv_unit;

    //蓝牙所需
    private final static String TAG = "WeightFragment";
    //private TextView result;
    private BluetoothAdapter.LeScanCallback lazyCallback;
    private BluetoothAdapter mBluetoothAdapter;
    private String mDeviceAddress ="";
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics = new ArrayList();
    private BluetoothGattCharacteristic mNotifyCharacteristic;
    private BluetoothService mBluetoothLeService;
    private static final int REQUEST_ENABLE_BT = 0;


    public WeightFragment(Context context) {
        super();
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(EmptyUtils.isEmpty(view)) {
            view = inflater.inflate(R.layout.layout_weight, container, false);
        }
        init();
//        setListener();

        return view;
    }


    /**初始化控件和数据*/
    private void init() {
        ftv_weight_value = (FontSizeAutoAdjustTextView) view.findViewById(R.id.ftv_weight_value);
        ftv_unit = (FontSizeAutoAdjustTextView) view.findViewById(R.id.ftv_unit);
    }
    /**给控件设置监听事件*/
//    private void setListener() {
//        btn_next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ToastUtil.show(context, "click next");
//                ftv_weight_value.setText("0000");
//                ftv_unit.setText("毫升");
//            }
//        });
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("a", "onDestroyView: WeightFragment DestoryView");

        Log.i(TAG,"onDestroyView--1");
        ((ViewGroup)view.getParent()).removeView(view);
    }
//
//
//
//
//    /*-----------------------------------分割线---------------------------------------------*/
//    // 代码管理服务生命周期
//    private final ServiceConnection mServiceConnection = new ServiceConnection() {
//
//        //服务保持连接
//        @Override
//        public void onServiceConnected(ComponentName componentName, IBinder service) {
//            mBluetoothLeService = ((BluetoothService.LocalBinder) service).getService();
//            if (!mBluetoothLeService.initialize()) {
//                Log.i(TAG, "无法初始化蓝牙");
//            }
//
//            Toast.makeText(context, "连接设备", Toast.LENGTH_LONG).show();
//            // 自动连接到设备成功启动初始化。
//            mBluetoothLeService.connect(mDeviceAddress);
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName componentName) {
//            Log.i(TAG, "无法连接");
//            mBluetoothLeService = null;
//        }
//    };
//
//    //广播
//    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (BluetoothService.ACTION_GATT_CONNECTED.equals(action)) {
//                Toast.makeText(context, "连接成功", Toast.LENGTH_LONG).show();
//            } else if (BluetoothService.ACTION_GATT_DISCONNECTED.equals(action)) {
//                Toast.makeText(context, "断开连接", Toast.LENGTH_LONG).show();
//            } else if (BluetoothService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
//                // 显示所有用户界面上的支持服务和特色
//                displayGattServices(mBluetoothLeService.getSupportedGattServices());
//            } else if (BluetoothService.ACTION_DATA_AVAILABLE.equals(action)) {
//                String data = intent.getStringExtra(BluetoothService.EXTRA_DATA);
//                //textView.setText(data);
//                Log.i("--数据----------------", data);
//            }
//        }
//    };
//
//    private void displayGattServices(List<BluetoothGattService> gattServices) {
//        if (gattServices != null) {
//            ArrayList<HashMap<String, String>> gattServiceData = new ArrayList();
//            //ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData = new ArrayList();
//            mGattCharacteristics = new ArrayList();
//            for (BluetoothGattService gattService : gattServices) {
//                HashMap<String, String> currentServiceData = new HashMap();
//                String uuid = gattService.getUuid().toString();
//                if (Objects.equals(uuid, "0000fff0-0000-1000-8000-00805f9b34fb")) {
//                    currentServiceData.put("UUID", uuid);
//                    gattServiceData.add(currentServiceData);
//                    ArrayList<HashMap<String, String>> gattCharacteristicGroupData = new ArrayList();
//                    List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
//                    ArrayList<BluetoothGattCharacteristic> charas = new ArrayList();
//                    for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
//                        charas.add(gattCharacteristic);
//                        HashMap<String, String> currentCharaData = new HashMap();
//                        currentCharaData.put("UUID", gattCharacteristic.getUuid().toString());
//                        gattCharacteristicGroupData.add(currentCharaData);
//                    }
//                    mGattCharacteristics.add(charas);
//                    if (mGattCharacteristics != null) {
//                        BluetoothGattCharacteristic characteristic = (BluetoothGattCharacteristic) ((ArrayList) this.mGattCharacteristics.get(0)).get(3);
//                        int charaProp = characteristic.getProperties();
//                        if ((charaProp | 2) > 0) {
//                            if (mNotifyCharacteristic != null) {
//                                mBluetoothLeService.setCharacteristicNotification(this.mNotifyCharacteristic, false);
//                                mNotifyCharacteristic = null;
//                            }
//                            mBluetoothLeService.readCharacteristic(characteristic);
//                        }
//                        if ((charaProp | 16) > 0) {
//                            mNotifyCharacteristic = characteristic;
//                            mBluetoothLeService.setCharacteristicNotification(characteristic, true);
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    //懒加载回调
//    private class LazyCallback implements BluetoothAdapter.LeScanCallback {
//        @Override
//        public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
//            //Log.i("开始查询","LazyCallback");
//            String name = bluetoothDevice.getName();
//            String address = bluetoothDevice.getAddress();
//            if (address != null) {
//                Log.i("查到的地址:", address);
//                //7C:EC:79:55:63:29
//            }
//            if (name != null && "BIGCARE_BC301".equals(name)) {
//                //String address2 = bluetoothDevice.getAddress();
//                mDeviceAddress = bluetoothDevice.getAddress();
//                Log.i("---查到的名字-----:", mDeviceAddress);
//
//                mBluetoothAdapter.stopLeScan(lazyCallback);
//
//                //Intent gattServiceIntent = new Intent(getActivity(), BluetoothService.class);
//                boolean ble = context.bindService(new Intent(context, BluetoothService.class), mServiceConnection, Context.BIND_AUTO_CREATE);//绑定服务
//
//                if (ble) {
//                    Log.i("绑定成功", "dd");
//                } else {
//                    Log.i("绑定失败", "dd");
//                }
//
//                context.registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
//
//            }
//
//            /*try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }*/
//        }
//    }
//
//    private static IntentFilter makeGattUpdateIntentFilter() {
//        final IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(BluetoothService.ACTION_GATT_CONNECTED);
//        intentFilter.addAction(BluetoothService.ACTION_GATT_DISCONNECTED);
//        intentFilter.addAction(BluetoothService.ACTION_GATT_SERVICES_DISCOVERED);
//        intentFilter.addAction(BluetoothService.ACTION_DATA_AVAILABLE);
//        return intentFilter;
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Log.i(TAG,"onCreate");
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Log.i(TAG,"onStart-1");
//
//        if (mDeviceAddress != null) {
//            Intent gattServiceIntent = new Intent(getActivity(), BluetoothService.class);
//            if(mBluetoothLeService ==null)
//                getActivity().bindService(gattServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);//绑定服务
//            getActivity().registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
//        }
//
//    }

//
//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.i(TAG,"onResume--1");
//
//        Log.i("onresume","什么事都没做");
//
//        if (!mBluetoothAdapter.isEnabled()) {
//            Toast.makeText(context, "打开蓝牙成功", Toast.LENGTH_LONG).show();
//            Log.i("打开蓝牙成功", "BluetoothConnection");
//            Intent enableBtIntent = new Intent( BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
//        }
//
//        Toast.makeText(context,"打开蓝牙后",Toast.LENGTH_LONG).show();
//
//        if (lazyCallback == null) {
//            Log.i("lazyCallback","lazyCallback  new前");
//            lazyCallback = new LazyCallback();
//        }
//        Log.i("lazyCallback","new 后");
//
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    boolean d = mBluetoothAdapter.startLeScan(lazyCallback);
//                    Log.i("扫描状态：",d+"");
//                }catch (Exception e){
//                    Log.i("异常：",e.toString());
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();
//
//        if (mBluetoothLeService != null) {
//            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
//            Log.i(TAG, "连接请求的结果=" + result);
//        }
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        Log.i(TAG,"onAttach--1");
//        //打开蓝牙
//        BluetoothManager bluetoothManager =(BluetoothManager)context.getSystemService(Context.BLUETOOTH_SERVICE);
//        mBluetoothAdapter = bluetoothManager.getAdapter();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        Log.i(TAG,"onPause--1");
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        Log.i(TAG,"onStop--1");
//        if (mDeviceAddress != null) {
//            if (mBluetoothLeService.isRestricted()){
//                if(mServiceConnection != null) {
//                    if(mBluetoothLeService != null)
//                        getActivity().unbindService(mServiceConnection);
//                }
//            }
//        }
//
//
//        if (mDeviceAddress != null && mGattCharacteristics != null) {
//            getActivity().unregisterReceiver(mGattUpdateReceiver);
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.i(TAG,"onDestroy--1");
//
//
//        if (mBluetoothAdapter != null) {
//            mBluetoothAdapter.disable();
//        }
//
//        mBluetoothLeService = null;
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        Log.i(TAG,"onDetach--1");
//
//        mBluetoothAdapter.stopLeScan(lazyCallback);
//
//    }
//
}
