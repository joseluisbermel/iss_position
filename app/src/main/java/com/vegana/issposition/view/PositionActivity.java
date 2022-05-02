package com.vegana.issposition.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.vegana.issposition.Constants;
import com.vegana.issposition.databinding.ActivityPositionBinding;
import com.vegana.issposition.presenter.RandomNumberPresenter;

import java.util.Objects;

public class PositionActivity extends AppCompatActivity implements RandomNumberView{
    private ActivityPositionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPositionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        binding.textAddress.setText(Constants.address);

        Long now = System.currentTimeMillis() / 1000;
        Long diff = Constants.selected.getRisetime() - now;

        binding.textDescription2.setText(String.format("%d", diff));

        String text = "El sobre vuelo durará %d minutos y %d segundos";
        int minutes = Constants.selected.getDuration() / 60;
        int seconds = Constants.selected.getDuration() - (minutes * 60);

        binding.textDescription4.setText(String.format(text, minutes, seconds));

        RandomNumberPresenter presenter = new RandomNumberPresenter(this);
        presenter.getRandomNumber();
    }

    @Override
    public void getRandomNumber(String response) {
        String textData = "%d son los segundos que durará el sobrevuelo y además, %s";
        binding.textData.setText(String.format(textData, Constants.selected.getDuration(), response));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}