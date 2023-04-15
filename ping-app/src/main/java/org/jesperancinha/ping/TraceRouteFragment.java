package org.jesperancinha.ping;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.jesperancinha.ping.R;
import org.jesperancinha.ping.util.SystemUiHider;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 *
 */
public class TraceRouteFragment extends Fragment {

    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    private EditText editText = null;

    private Button btnCalculate = null;

    private TextView textView = null;

    private TextView textResult = null;

    private TextView textComment = null;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TraceRouteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TraceRouteFragment newInstance(String param1, String param2) {
        TraceRouteFragment fragment = new TraceRouteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View mainView = inflater.inflate(R.layout.fragment_trace_route_layout, container, false);
        final View contentView = mainView.findViewById(R.id.fullscreen_content);



        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        editText = (EditText) mainView.findViewById(R.id.editDNS);
        btnCalculate = (Button) mainView.findViewById(R.id.btnCalculate);
        textView = (TextView) mainView.findViewById(R.id.textView);
        textResult = (TextView) mainView.findViewById(R.id.txtResult);
        textComment = (TextView) mainView.findViewById(R.id.textComment);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                long nowStamp = System.currentTimeMillis();
                String ipAddress = editText.getText().toString();

                try {
                    InetAddress[] inets = InetAddress.getAllByName(ipAddress);
                    final StringBuffer sb = new StringBuffer();
                    sb.append(String.format("Hostname:%s",ipAddress));
                    for(InetAddress inet : inets){
                        sb.append(String.format("\n%s", inet.getHostAddress()));
                    }

                    textResult.setText(sb.toString());

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    textResult.setText(String.format("Host not found!\n%s", e.getMessage()));
                }
                long endStamp = System.currentTimeMillis();
                textComment.setText(String.format("Ping lasted %d miliseconds", (endStamp - nowStamp)));
            }
        });

        // Inflate the layout for this fragment
        return mainView;
    }
}
