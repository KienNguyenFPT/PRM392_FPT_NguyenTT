package andb.example.carracing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final List<Handler> handlerList = new ArrayList<>();
    final List<Runnable> runnableList = new ArrayList<>();

    private final List<Boolean> finished = new ArrayList<>();
    private SeekBar sbContent1;
    private SeekBar sbContent2;
    private SeekBar sbContent3;
    private Button btnStart;
    private Button btnRefresh;
    private Button btnReset;
    private TextView txtFinish;
    private CheckBox cbCar1;
    private CheckBox cbCar2;
    private CheckBox cbCar3;
    private TextView moneyResult;
    private TextView profitValue;
    private TextView betValue;
    private TextView duck1Speed;
    private TextView duck2Speed;
    private TextView duck3Speed;
    private TextView moneyLeftTotal;
    private EditText bet1;
    private EditText bet2;
    private EditText bet3;

    int winner = -1;
    int secondWinner = -1;
    double profitTotal = 0.0;
    double moneyLeft = 0.0;
    private SeekBar[] seekBars;
    private static final int RESULT_REQUEST_CODE = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        sbContent1 = findViewById(R.id.seekBar);
        sbContent2 = findViewById(R.id.seekBar2);
        sbContent3 = findViewById(R.id.seekBar3);
        btnStart = findViewById(R.id.btnStart);
        btnRefresh = findViewById(R.id.btnRefresh);
        txtFinish = findViewById(R.id.finish);
        cbCar1 = findViewById(R.id.checkBox1);
        cbCar2 = findViewById(R.id.checkBox2);
        cbCar3 = findViewById(R.id.checkBox3);
        moneyResult = findViewById(R.id.moneyResult);
        profitValue = findViewById(R.id.profitValue);
        betValue = findViewById(R.id.betValue);
        duck1Speed = findViewById(R.id.car1m);
        duck2Speed = findViewById(R.id.car2m);
        duck3Speed = findViewById(R.id.car3m);
        bet1 = findViewById(R.id.betCar1);
        bet2 = findViewById(R.id.betCar2);
        bet3 = findViewById(R.id.betCar3);
        moneyLeftTotal = findViewById(R.id.moneyLeft);
        btnReset = findViewById(R.id.reset);

        // Initialize SeekBars and TextViews Arrays
        seekBars = new SeekBar[]{sbContent1, sbContent2, sbContent3};
        final TextView[] textSpeed = {duck1Speed, duck2Speed, duck3Speed};

        // Initialize Finished Status List
        initFinishedList();

        // Initialize Handlers and Runnables for Each SeekBar
        for (int i = 0; i < seekBars.length; i++) {
            int finalI = i;
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                int process = 0; // Local progress for each Runnable
                @Override
                public void run() {
                    btnStart.setVisibility(View.INVISIBLE);
                    Random rd = new Random();
                    int rdRace = rd.nextInt(16); // Random value between 0 and 15
                    process += rdRace;
                    seekBars[finalI].setProgress(process);
                    if (process < 100) {
                        handler.postDelayed(this, 1000);
                    } else {
                        btnRefresh.setVisibility(View.INVISIBLE);
                        finished.set(finalI, true);
                        if (winner == -1) {
                            winner = finalI;
                        } else if (secondWinner == -1) {
                            secondWinner = finalI;
                        }
                        if (!finished.contains(false)) {
                            displayResults();
                        }
                    }
                }
            };
            handlerList.add(handler);
            runnableList.add(runnable);
        }

        // Set SeekBar Change Listeners to Display Speed
        for (int i = 0; i < seekBars.length; i++) {
            int index = i;
            seekBars[index].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    textSpeed[index].setText(String.valueOf(progress) + " cm/min");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
        }

        // Handle Bet CheckBoxes Visibility
        cbCar1.setOnCheckedChangeListener((buttonView, isChecked) -> bet1.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE));
        cbCar2.setOnCheckedChangeListener((buttonView, isChecked) -> bet2.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE));
        cbCar3.setOnCheckedChangeListener((buttonView, isChecked) -> bet3.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE));

        // Handle Start Button Click
        btnStart.setOnClickListener(v -> startRace());

        // Handle Refresh Button Click
        btnRefresh.setOnClickListener(v -> refreshGame());

        // Handle Reset Button Click
        btnReset.setOnClickListener(v -> resetGame());
    }

    // Handle Run Logic
//    public void handleRun(double moneyTotal, double totalBet) {
//        moneyLeft = moneyTotal - totalBet;
//        if (moneyLeft < 0) {
//            Toast.makeText(MainActivity.this, "Your money is not enough to bet", Toast.LENGTH_LONG).show();
//        } else {
//            for (int i = 0; i < handlerList.size(); i++) {
//                handlerList.get(i).post(runnableList.get(i));
//            }
//            betValue.setText(String.valueOf(totalBet));
//            moneyResult.setText(String.valueOf(moneyLeft));
//            moneyLeftTotal.setText(String.valueOf(moneyLeft));
//        }
//    }
    public void handleRun(double moneyTotal, double totalBet) {
        double moneyLeft = moneyTotal - totalBet;
        if (moneyLeft < 0) {
            Toast.makeText(MainActivity.this, "Your money is not enough to bet", Toast.LENGTH_LONG).show();
        } else {
            // List to hold the speeds of cars
            List<Double> carSpeeds = Arrays.asList(
                    (double) sbContent1.getProgress(),
                    (double) sbContent2.getProgress(),
                    (double) sbContent3.getProgress()
            );

            // Sort the list to find the winner and the second winner
            Collections.sort(carSpeeds, Collections.reverseOrder());

            // Set the winner and second winner based on sorted speeds
            winner = carSpeeds.indexOf((double) sbContent1.getProgress());
            secondWinner = carSpeeds.indexOf((double) sbContent2.getProgress());

            displayResults();
        }
    }

    // Start Race
    private void startRace() {
        double betValue1 = parseDouble(bet1.getText().toString());
        double betValue2 = parseDouble(bet2.getText().toString());
        double betValue3 = parseDouble(bet3.getText().toString());

        double moneyTotal = parseDouble(moneyResult.getText().toString());

        if (betValue1 == 0.0 && betValue2 == 0.0 && betValue3 == 0.0) {
            Toast.makeText(getApplicationContext(), "Vui lòng đặt cược trước khi bắt đầu!", Toast.LENGTH_SHORT).show();
            return;
        }

        double totalBet = betValue1 + betValue2 + betValue3;
        handleRun(moneyTotal, totalBet);
    }

    // Parse Double
    private double parseDouble(String value) {
        return value.isEmpty() ? 0 : Double.parseDouble(value);
    }

    // Display Results
    // Display Results
    private void displayResults() {
        double betNum1 = parseDouble(bet1.getText().toString());
        double betNum2 = parseDouble(bet2.getText().toString());
        double betNum3 = parseDouble(bet3.getText().toString());
        double betValueTotal = parseDouble(betValue.getText().toString());
        double profit = 0.0;

        String winnerColor = "";
        String secondWinnerColor = "";

        if (winner == 0) {
            profitTotal = betNum1 * 2;
            winnerColor = "Orange";
            if (secondWinner == 1) {
                profitTotal += betNum2 * 1.5;
                secondWinnerColor = "Blue";
            } else if (secondWinner == 2) {
                profitTotal += betNum3 * 1.5;
                secondWinnerColor = "White";
            }
        } else if (winner == 1) {
            profitTotal = betNum2 * 2;
            winnerColor = "Blue";
            if (secondWinner == 0) {
                profitTotal += betNum1 * 1.5;
                secondWinnerColor = "Orange";
            } else if (secondWinner == 2) {
                profitTotal += betNum3 * 1.5;
                secondWinnerColor = "White";
            }
        } else if (winner == 2) {
            profitTotal = betNum3 * 2;
            winnerColor = "White";
            if (secondWinner == 0) {
                profitTotal += betNum1 * 1.5;
                secondWinnerColor = "Orange";
            } else if (secondWinner == 1) {
                profitTotal += betNum2 * 1.5;
                secondWinnerColor = "Blue";
            }
        }

//        profitValue.setText(String.valueOf(profit));
//        double total = parseDouble(moneyLeftTotal.getText().toString()) + profitTotal;
//        moneyResult.setText(String.valueOf(total));
//
//        // Start ResultActivity and pass the result data
//        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
//        intent.putExtra("winner", "The Car Winner is: " + winnerColor);
//        intent.putExtra("secondWinner", "The Second Winner is: " + secondWinnerColor);
//        intent.putExtra("profit", profit);
//        intent.putExtra("moneyLeft", total);
//        startActivityForResult(intent, RESULT_REQUEST_CODE);
        profit = profitTotal - betValueTotal;



        // Start ResultActivity and pass the result data
        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        startActivity(intent);
        SharedPreferences sharedPreferences = getSharedPreferences("ResultData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("winner", "The Car Winner is: " + winnerColor);
        editor.putString("secondWinner", "The Second Winner is: " + secondWinnerColor);
        editor.putFloat("profit", (float) profit);
        editor.putFloat("moneyLeft", (float) (moneyLeft + profitTotal)); // Update money left with profit
        editor.apply();
    }

    // Calculate Profit
    private double calculateProfit(int secondWinner, double betNum1, double betNum2, double profitTotal, double betValueTotal, String winnerColor) {
        double profit = 0.0;
        if (secondWinner == 0) {
            profitTotal += betNum1 * 1.5;
            txtFinish.setText("The winner is " + winnerColor + " and the second place is Orange");
            profit = profitTotal - betValueTotal;
        } else if (secondWinner == 1) {
            profitTotal += betNum2 * 1.5;
            txtFinish.setText("The winner is " + winnerColor + " and the second place is Blue");
            profit = profitTotal - betValueTotal;
        } else if (secondWinner == 2) {
            profitTotal += betNum2 * 1.5;
            txtFinish.setText("The winner is " + winnerColor + " and the second place is White");
            profit = profitTotal - betValueTotal;
        }
        return profit;
    }

    // Refresh Game
    private void refreshGame() {
        updateUI();
        double allMoney = parseDouble(moneyResult.getText().toString());
        if (allMoney <= 1.0) {
            btnReset.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.INVISIBLE);
        }
    }

    // Reset Game
    private void resetGame() {
        btnReset.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.VISIBLE);
        profitValue.setText("0.0");
        winner = -1;
        secondWinner = -1;
        resetSeekBars();
        resetBetsAndCheckBoxes();
        txtFinish.setText("");
        moneyResult.setText("100.0");
        updateUI();
    }

    // Reset SeekBars
    private void resetSeekBars() {
        sbContent1.setProgress(0);
        sbContent2.setProgress(0);
        sbContent3.setProgress(0);
    }

    // Reset Bets and CheckBoxes
    private void resetBetsAndCheckBoxes() {
        bet1.setText("");
        bet2.setText("");
        bet3.setText("");
        betValue.setText("0.0");
        cbCar1.setChecked(false);
        cbCar2.setChecked(false);
        cbCar3.setChecked(false);
    }

    // Check Game Over Condition
    private void checkGameOver() {
        double allMoney = parseDouble(moneyResult.getText().toString());
        if (allMoney <= 1.0) {
            btnReset.setVisibility(View.INVISIBLE);
            btnStart.setVisibility(View.INVISIBLE);
        }
    }

    private void updateUI() {
        btnStart.setVisibility(View.VISIBLE);
        btnRefresh.setVisibility(View.INVISIBLE);
        profitValue.setText("0.0");
        winner = -1;
        secondWinner = -1;
        resetSeekBars();
        resetBetsAndCheckBoxes();
        txtFinish.setText("");
        double allMoney = parseDouble(moneyResult.getText().toString());
        if (allMoney <= 1.0) {
            btnReset.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.INVISIBLE);
        } else {
            btnReset.setVisibility(View.INVISIBLE);
        }
    }

    // Reset lại Handlers và Runnables khi quay lại từ ResultActivity
    private void resetHandlersAndRunnables() {
        // Đảm bảo rằng tất cả các Handler và Runnable đã được xóa trước khi tạo mới
        handlerList.clear();
        runnableList.clear();

        // Tạo lại Handlers và Runnables cho mỗi SeekBar
        for (int i = 0; i < seekBars.length; i++) {
            int finalI = i;
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                int process = 0; // Local progress for each Runnable
                @Override
                public void run() {
                    btnStart.setVisibility(View.INVISIBLE);
                    Random rd = new Random();
                    int rdRace = rd.nextInt(16); // Random value between 0 and 15
                    process += rdRace;
                    seekBars[finalI].setProgress(process);
                    if (process < 100) {
                        handler.postDelayed(this, 1000);
                    } else {
                        btnRefresh.setVisibility(View.INVISIBLE);
                        finished.set(finalI, true);
                        if (winner == -1) {
                            winner = finalI;
                        } else if (secondWinner == -1) {
                            secondWinner = finalI;
                        }
                        if (!finished.contains(false)) {
                            displayResults();
                        }
                    }
                }
            };
            handlerList.add(handler);
            runnableList.add(runnable);
        }
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
        resetHandlersAndRunnables();
    }

    private void initFinishedList() {
        finished.clear();
        for (int i = 0; i < seekBars.length; i++) {
            finished.add(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                double moneyLeft = data.getDoubleExtra("moneyLeft", 0.0);
                moneyResult.setText(String.valueOf(moneyLeft));
                updateUI();
            }
        }
    }

}
