package com.example.hbms;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hbms.api.ApiUtilities;
import com.example.hbms.api.CountryData;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class Covid extends AppCompatActivity {
    private TextView totalConfirm,totalActive,totalRecovered,totalDeath,totalTests;
    private TextView todayConfirm,todayRecovered,todayDeath,dateTV;
    private List<CountryData> list;
    private PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid);
        list=new ArrayList<>();
        init();
        ApiUtilities.getApiInterface().getCountryData()
                .enqueue(new Callback<List<CountryData>>() {
                    @Override
                    public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {
                        list.addAll(response.body());
                        for(int i=0;i<list.size();i++)
                        {
                            if(list.get(i).getCountry().equals("India"))
                            {
                                int confirm=Integer.parseInt(list.get(i).getCases());
                                int active=Integer.parseInt(list.get(i).getActive());
                                int recovered=Integer.parseInt(list.get(i).getRecovered());
                                int deaths=Integer.parseInt(list.get(i).getDeaths());
                                totalConfirm.setText(NumberFormat.getInstance().format(confirm));
                                totalActive.setText(NumberFormat.getInstance().format(active));
                                totalRecovered.setText(NumberFormat.getInstance().format(recovered));
                                totalDeath.setText(NumberFormat.getInstance().format(deaths));
                                totalTests.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTests())));

                                int tdeath=Integer.parseInt(list.get(i).getTodayDeaths());
                                todayDeath.setText(NumberFormat.getInstance().format(tdeath));
                                todayConfirm.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayCases())));
                                todayRecovered.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayRecovered())));

                                setText(list.get(i).getUpdated());

                                pieChart.addPieSlice(new PieModel("Confirm",confirm,getResources().getColor(R.color.yellow1)));
                                pieChart.addPieSlice(new PieModel("Active",active,getResources().getColor(R.color.blue)));
                                pieChart.addPieSlice(new PieModel("Recovered",recovered,getResources().getColor(R.color.green)));
                                pieChart.addPieSlice(new PieModel("Deaths",deaths,getResources().getColor(R.color.red3)));



                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CountryData>> call, Throwable t) {
                        Toast.makeText(Covid.this,"Error : "+t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void setText(String updated)
    {
        DateFormat format=new SimpleDateFormat("MMM dd,yyyy");
        long milli=Long.parseLong(updated);
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(milli);
        dateTV.setText("Updated at"+format.format(calendar.getTime()));
    }
    void init()
    {
        totalConfirm=findViewById(R.id.totalConfirm);
        totalActive=findViewById(R.id.totalActive);
        totalRecovered=findViewById(R.id.totalRecovered);
        totalDeath=findViewById(R.id.totalDeaths);
        totalTests=findViewById(R.id.totalTests);
        todayConfirm=findViewById(R.id.todayConfirm);
        todayRecovered=findViewById(R.id.todayRecovered);
        todayDeath=findViewById(R.id.todayDeaths);
        pieChart=findViewById(R.id.piechart);
        dateTV=findViewById(R.id.date);
    }
}