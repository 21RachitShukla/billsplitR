package rs21.billsplitr.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import rs21.billsplitr.R;

public class HelpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        TextView text = findViewById(R.id.help);
        text.setText(Html.fromHtml(
                "How to use?<br /><br />" +
                        "\u2713 Click on + to set up your first bill.<br />" +
                        "\u2713 Click on the name of the bill.<br />" +
                        "\u2713 Add a description to it (date, time, venue, etc.).<br />" +
                        "\u2713 Swipe left and add item details (name, price, quantity).<br />" +
                        "\u2713 Click on + to add more items to your bill.<br />" +
                        "\u2713 Swipe left and add member details (name, paid amount).<br />" +
                        "\u2713 Click on + to add more members to your bill.<br />" +
                        "\u2713 Click on view to set percentage of each item consumed by member.<br />" +
                        "\u2713 Finally, click on save to add your bill to billsplitR.<br /><br />" +
                        "NOTE: Long press on any bill, item, member to delete it.<br /><br />" +
                        "Developer:<br />" +
                        "<a href=\"https://github.com/21RachitShukla\">Just Another Mediocre</a><br /><br />" +
                        "License:<br />" +
                        "<a href=\"https://opensource.org/licenses/MIT\">MIT</a>"
        ));
        text.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
