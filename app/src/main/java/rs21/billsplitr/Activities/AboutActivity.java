package rs21.billsplitr.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import rs21.billsplitr.R;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView text = findViewById(R.id.about);
        text.setText(Html.fromHtml(
                "Have you ever had a relaxing Saturday brunch with friends only to end your meal with the most stressful math problem of the day: How to split the bill?<br />" +
                        "Fortunately, billsplitR has the answer. Whether you’re splitting lunch, dinner, coffee, IOUs or even your rent, it comes to your rescue!<br /><br />" +
                        "Stay on top of all your expenses!<br />" +
                        "Settle up in an easy, relaxed way and make sure that everyone gets paid back!<br /><br />" +
                        "Features at a glance:<br />" +
                        "\u2713 Clean interface that's easy to use.<br />" +
                        "\u2713 Add multiple bills with a description to remain hassle free.<br />" +
                        "\u2713 Work offline (no sign-up needed).<br />" +
                        "\u2713 Handles complicated transactions (adding multiple payees, splitting bills unequally, etc.).<br />" +
                        "\u2713 Easily update bills.<br />" +
                        "\u2713 Find out how much everyone in your group has spent in total.<br />" +
                        "\u2713 Work as a single member to keep track of your personal bills.<br /><br />" +
                        "Developer:<br />" +
                        "<a href=\"https://github.com/21RachitShukla\">Just Another Mediocre</a><br /><br />" +
                        "License:<br />" +
                        "<a href=\"https://opensource.org/licenses/MIT\">MIT</a>"
        ));
        text.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
