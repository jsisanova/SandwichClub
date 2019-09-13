package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

// DetailActivity displays all received Sandwich data
public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView sandwichImage;
    private TextView alsoKnownAsTextView;
    private TextView descriptionTextView;
    private TextView ingredientsTextView;
    private TextView placeOfOriginTextView;

    private TextView alsoKnownAsLabel;
    private TextView placeOfOriginLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        sandwichImage = findViewById(R.id.image_iv);
        alsoKnownAsTextView = findViewById(R.id.also_known_tv);
        descriptionTextView = findViewById(R.id.description_tv);
        ingredientsTextView = findViewById(R.id.ingredients_tv);
        placeOfOriginTextView = findViewById(R.id.origin_tv);

        alsoKnownAsLabel = findViewById(R.id.also_known_label);
        placeOfOriginLabel = findViewById(R.id.origin_label);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(sandwichImage);
                // Use the error() and placeholder() methods to avoid empty ImageViews while the image loads or if the load fails
                // .placeholder(R.drawable.something);

        setTitle(sandwich.getMainName());

        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich1) {

        // Get 'also known as' as a list
        List<String> alsoKnownAsList = sandwich1.getAlsoKnownAs();
        // If there is nothing to show in 'also known as' list, do not display it
        if (alsoKnownAsList.size() == 0) {
            alsoKnownAsLabel.setVisibility(View.GONE);
            alsoKnownAsTextView.setVisibility(View.GONE);
        } else {
            StringBuilder akaNames = new StringBuilder();

            for (String akaName : alsoKnownAsList) {
                akaNames.append(akaName).append(", ");
            }
            // Get rid of the 2 last characters, in this case ", "
            akaNames.setLength(akaNames.length() - 2);
            alsoKnownAsTextView.setText(akaNames);
        }

        descriptionTextView.setText(sandwich1.getDescription());

        // Get 'ingredients' as a list
        List<String> ingredientsList = sandwich1.getIngredients();
        StringBuilder ingredients = new StringBuilder();

        for (String ingredient : ingredientsList) {
            ingredients.append(ingredient).append(", ");
        }
        ingredients.setLength(ingredients.length() - 2);
        ingredientsTextView.setText(ingredients);

        // If there is not known place of origin, do not display it
        if (sandwich1.getPlaceOfOrigin().isEmpty()) {
            placeOfOriginLabel.setVisibility(View.GONE);
            placeOfOriginTextView.setVisibility(View.GONE);
        } else {
            placeOfOriginTextView.setText(sandwich1.getPlaceOfOrigin());
        }
    }
}